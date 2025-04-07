package com.github.BetaInside.babrictone.pathfinding.actions;

import com.github.BetaInside.babrictone.util.chatUtil;
import com.github.BetaInside.babrictone.util.toolSet;
import net.minecraft.src.*;

import static com.github.BetaInside.babrictone.babrictone.mc;

public abstract class action {

    //These costs are measured roughly in ticks btw
    public static final double WALK_ONE_BLOCK_COST = 20 / 4.317;
    public static final double WALK_ONE_IN_WATER_COST = 20 / 2.2;
    public static final double JUMP_ONE_BLOCK_COST = 5.72854;//see below calculation for fall. 1.25 blocks
    public static final double LADDER_UP_ONE_COST = 20 / 2.35;
    public static final double LADDER_DOWN_ONE_COST = 20 / 3;
    public static final double SNEAK_ONE_BLOCK_COST = 20 / 1.3;
    public static final double SPRINT_ONE_BLOCK_COST = 20 / 5.612;
    /**
     * Doesn't include walking forwards, just the falling
     *
     * Based on a sketchy formula from minecraftwiki
     *
     * d(t) = 3.92 × (99 - 49.50×(0.98^t+1) - t)
     *
     * Solved in mathematica
     */
    public static final double FALL_ONE_BLOCK_COST = 5.11354;
    public static final double FALL_TWO_BLOCK_COST = 7.28283;
    public static final double FALL_THREE_BLOCK_COST = 8.96862;
    /**
     * It doesn't actually take ten ticks to place a block, this cost is so high
     * because we want to generally conserve blocks which might be limited
     */
    public static final double PLACE_ONE_BLOCK_COST = 20;
    /**
     * Add this to the cost of breaking any block. The cost of breaking any
     * block is calculated as the number of ticks that block takes to break with
     * the tools you have. You add this because there's always a little overhead
     * (e.g. looking at the block)
     */
    public static final double BREAK_ONE_BLOCK_ADD = 4;
    public static final double COST_INF = 1000000;
    public final Vec3D from;
    public final Vec3D to;
    private Double cost;
    public boolean finished = false;

    protected action(Vec3D from, Vec3D to) {
        this.from = from;
        this.to = to;
        this.cost = null;
    }

    public static Block getBlockFromId(int blockId) {
        return Block.blocksList[blockId];
    }

    /**
     * Get the cost. It's cached
     *
     * @param ts
     * @return
     */
    public double cost(toolSet ts) {
        if (cost == null) {
            cost = calculateCost0(ts == null ? new toolSet() : ts);
        }
        if (cost < 1) {
            chatUtil.addMessage("Bad cost " + this + " " + cost);
        }
        return cost;
    }

    public double calculateCost0(toolSet ts) {
        /*if (!(this instanceof ActionPillar) && !(this instanceof ActionBridge) && !(this instanceof ActionFall)) {
            Block fromDown = getBlockFromId(mc.theWorld.getBlockId((int) from.xCoord, (int) from.yCoord, (int) from.zCoord));
            if (fromDown instanceof BlockLadder) {
                return COST_INF;
            }
        }*/
        return calculateCost(ts);
    }

    protected abstract double calculateCost(toolSet ts);
    static Block waterFlowing = Block.blocksList[8];
    static Block waterStill = Block.blocksList[9];
    static Block lavaFlowing = Block.blocksList[10];
    static Block lavaStill = Block.blocksList[11];

    /**
     * Is this block water? Includes both still and flowing
     *
     * @param b
     * @return
     */
    public static boolean isWater(Block b) {
        return waterFlowing.equals(b) || waterStill.equals(b);
    }

    public static boolean isWater(Vec3D bp) {
        return isWater(getBlockFromId(mc.theWorld.getBlockId((int) bp.xCoord, (int) bp.yCoord, (int) bp.zCoord)));
    }

    public static boolean isLiquid(Block b) {
        return b instanceof BlockFluid;
        //return b != null && (waterFlowing.equals(b) || waterStill.equals(b) || lavaFlowing.equals(b) || lavaStill.equals(b));
    }

    public static boolean isFlowing(Vec3D pos) {
        Block b = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord));
        if (b instanceof BlockFluid) {
            throw new UnsupportedOperationException("TODO");
            //return BlockLiquid.getFlow(Minecraft.getMinecraft().world, pos, state) != -1000.0D;
        }
        return false;
    }

    public static boolean isLava(Block b) {
        return lavaFlowing.equals(b) || lavaStill.equals(b);
    }

    public static boolean isLiquid(Vec3D p) {
        return isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) p.xCoord, (int) p.yCoord, (int) p.zCoord)));
    }

    public static boolean isLiquidAbove(Vec3D p) {
        return isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) p.xCoord, (int) p.yCoord + 1, (int) p.zCoord)));
    }

    public static boolean isWaterAbove(Vec3D bp) {
        return isWater(getBlockFromId(mc.theWorld.getBlockId((int) bp.xCoord, (int) bp.yCoord + 1, (int) bp.zCoord)));
    }

    public static boolean avoidBreaking(Vec3D pos) {
        Block b = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord));
        Block below = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord - 1, (int) pos.zCoord));
        return Block.ice.equals(b)//ice becomes water, and water can mess up the path
                || isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord + 1, (int) pos.zCoord)))//don't break anything touching liquid on any side
                || isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord + 1, (int) pos.yCoord, (int) pos.zCoord)))
                || isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord - 1, (int) pos.yCoord, (int) pos.zCoord)))
                || isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord + 1)))
                || isLiquid(getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord - 1)));
    }

    /**
     * Can I walk through this block? e.g. air, saplings, torches, etc
     *
     * @param pos
     * @return
     */
    public static boolean canWalkThrough(Vec3D pos) {
        Block block = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord));
        if (block instanceof BlockFire) {//you can't actually walk through a lilypad from the side, and you shouldn't walk through fire
            return false;
        }
        if (isFlowing(pos)) {
            return false;//don't walk through flowing liquids
        }
        if (isLiquidAbove(pos)) {
            return false;//you could drown
        }
        return !block.isCollidable();
    }

    public static boolean avoidWalkingInto(Vec3D pos) {
        Block block = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord));
        if (isLava(block)) {
            return true;
        }
        if (block instanceof BlockCactus) {
            return true;
        }
        return block instanceof BlockFire;
    }

    /**
     * Can I walk on this block without anything weird happening like me falling
     * through? Includes water because we know that we automatically jump on
     * lava
     *
     * @param pos
     * @return
     */
    public static boolean canWalkOn(Vec3D pos) {
        Block block = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord));
        if (block instanceof BlockLadder) {
            return true;
        }
        if (isWater(block)) {
            return isWaterAbove(pos);//you can only walk on water if there is water above it
        }
        return block.isOpaqueCube() && !isLava(block);
    }

    /**
     * Tick this action
     *
     * @return is it done
     */
    public abstract boolean tick();
}