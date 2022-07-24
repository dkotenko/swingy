package org.example.model.hero;

import org.example.model.creature.Creature;

public abstract class Hero extends Creature {
    protected String className;
    protected int currExp = 0;
    protected int nextLevelExp;
    protected double classModifierAttack = 1;
    protected double classModifierDefence = 1;
    protected double classModifierHitPoints = 1;

    public void recalcAttributes()
    {
        attack = (int)(classModifierAttack * (level + getWeapon()));
        defence = (int)(classModifierDefence * (level + getArmor()));
        hitPoints = (int)(classModifierHitPoints * (level + getHelm() * 5));
    }

    private int countExpByLevel(int level) {
        return (level * 1000 + (level - 1) * (level - 1) * 450);
    }
}
