package com.github.BetaInside.babrictone.util;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.github.BetaInside.babrictone.babrictone.mc;
import static com.github.BetaInside.babrictone.pathfinding.actions.action.getBlockFromId;

public class toolSet {

    public ArrayList<Item> tools;
    public ArrayList<Byte> slots;
    public HashMap<Block, Byte> cache = new HashMap<Block, Byte>();
    public toolSet(ArrayList<Item> tools, ArrayList<Byte> slots) {
        this.tools = tools;
        this.slots = slots;
    }
    public toolSet() {
        EntityPlayerSP p = mc.thePlayer;
        ItemStack[] inv = p.inventory.mainInventory;
        tools = new ArrayList<Item>();
        slots = new ArrayList<Byte>();
        //Out.log("inv: " + Arrays.toString(inv));
        boolean fnull = false;
        for (byte i = 0; i < 9; i++) {
            if (!fnull || (inv[i] != null && inv[i].getItem() instanceof ItemTool)) {
                tools.add(inv[i] != null ? inv[i].getItem() : null);
                slots.add(i);
                fnull |= inv[i] == null || (!inv[i].getItem().isDamagable());
            }
        }
    }
    public Item getBestTool(Block b) {
        if (cache.get(b) != null) {
            return tools.get(cache.get(b));
        }
        byte best = 0;
        //Out.log("best: " + best);
        float value = 0;
        for (byte i = 0; i < tools.size(); i++) {
            Item item = tools.get(i);
            if (item == null) {
                item = Item.appleRed;
            }
            //Out.log(inv[i]);
            float v = item.getStrVsBlock(new ItemStack(item), b);
            //Out.log("v: " + v);
            if (v > value) {
                value = v;
                best = i;
            }
        }
        //Out.log("best: " + best);
        cache.put(b, best);
        return tools.get(best);
    }
    public byte getBestSlot(Block b) {
        if (cache.get(b) != null) {
            return slots.get(cache.get(b));
        }
        byte best = 0;
        //Out.log("best: " + best);
        float value = 0;
        for (byte i = 0; i < tools.size(); i++) {
            Item item = tools.get(i);
            if (item == null) {
                item = Item.appleRed;
            }
            //Out.log(inv[i]);
            float v = item.getStrVsBlock(new ItemStack(item), b);
            //Out.log("v: " + v);
            if (v > value) {
                value = v;
                best = i;
            }
        }
        //Out.log("best: " + best);
        cache.put(b, best);
        return slots.get(best);
    }
    public double getStrVsBlock(Block b, Vec3D pos) {
        Item item = this.getBestTool(b);
        if (item == null) {
            item = Item.appleRed;
        }
        Block block = getBlockFromId(mc.theWorld.getBlockId((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord));
        float f = block.getHardness();
        return f < 0.0F ? 0.0F : (!canHarvest(b, item) ? item.getStrVsBlock(new ItemStack(item), b) / f / 100.0F : item.getStrVsBlock(new ItemStack(item), b) / f / 30.0F);
    }
    public boolean canHarvest(Block blockIn, Item item) {
        if (blockIn == Block.grass || blockIn == Block.dirt || blockIn == Block.sand) {
            return true;
        } else {
            return new ItemStack(item).canHarvestBlock(blockIn);
        }
    }

}
