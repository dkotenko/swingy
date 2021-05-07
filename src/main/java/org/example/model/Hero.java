package org.example.model;

public abstract class Hero extends Creature {
    protected long experience = 0;
    protected double classModifierAttack = 1;
    protected double classModifierDefence = 1;
    protected double classModifierHitPoints = 1;

    public void recalcAttributes()
    {
        attack = (long)(classModifierAttack * (level + getWeapon()));
        defence = (long)(classModifierDefence * (level + getArmor()));
        hitPoints = (long)(classModifierHitPoints * (level + getHelm() * 5));
    }
}
