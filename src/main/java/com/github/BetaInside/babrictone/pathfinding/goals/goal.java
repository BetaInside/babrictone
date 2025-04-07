package com.github.BetaInside.babrictone.pathfinding.goals;

import net.minecraft.src.Vec3D;

public interface goal {

    /**
     * Does this position satisfy the goal?
     *
     * @param pos
     * @return
     */
    public boolean isInGoal(Vec3D pos);
    /**
     * Estimate the number of ticks it will take to get to the goal
     *
     * @param pos
     * @return
     */
    public double heuristic(Vec3D pos);
    @Override
    public String toString();

}
