package net.biscuits310.redwoodforests.entity.custom;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;

public class CeilingPathNavigation extends WallClimberNavigation {
    public CeilingPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }
}
