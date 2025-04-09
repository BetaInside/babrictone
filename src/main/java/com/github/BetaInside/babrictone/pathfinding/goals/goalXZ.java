package com.github.BetaInside.babrictone.pathfinding.goals;

import com.github.BetaInside.babrictone.pathfinding.actions.action;
import net.minecraft.src.Vec3D;

public class goalXZ implements goal {

    final int x;
    final int z;
    public goalXZ(int x, int z) {
        this.x = x;
        this.z = z;
    }
    @Override
    public boolean isInGoal(Vec3D pos) {
        return pos.xCoord == x && pos.zCoord == z;
    }
    @Override
    public double heuristic(Vec3D pos) {//mostly copied from GoalBlock
        double xDiff = pos.xCoord - this.x;
        double zDiff = pos.zCoord - this.z;
        return calculate(xDiff, zDiff);
    }
    public static double calculate(double xDiff, double zDiff) {
        return calculate(xDiff, zDiff, 0);
    }
    /*
     public static double calculate(double xDiff, double zDiff) {
     double pythaDist = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
     return calculate(xDiff, zDiff, pythaDist);
     }
     public static double calculateOld(double xDiff, double zDiff, double pythaDist) {
     double heuristic = 0;
     heuristic += Math.abs(xDiff) * Action.WALK_ONE_BLOCK_COST * 1.1;//overestimate
     heuristic += Math.abs(zDiff) * Action.WALK_ONE_BLOCK_COST * 1.1;
     heuristic += pythaDist / 10 * Action.WALK_ONE_BLOCK_COST;
     return heuristic;
     }
     */
    static final double sq = Math.sqrt(2);
    public static double calculate(double xDiff, double zDiff, double pythaDist) {
        double x = Math.abs(xDiff);
        double z = Math.abs(zDiff);
        double straight;
        double diagonal;
        if (x < z) {
            straight = z - x;
            diagonal = x;
        } else {
            straight = x - z;
            diagonal = z;
        }
        diagonal *= sq;
        return (diagonal + straight) * action.WALK_ONE_BLOCK_COST;
    }
    @Override
    public String toString() {
        return "Goal{x=" + x + ",z=" + z + "}";
    }

}
