package org.example.model.monster;

import org.example.model.creature.Creature;

public class Monster extends Creature {
    public Monster()
    {
        super(0, 0, 10);
        setStats();
        updateAttributes();
    }

    protected void setStats() {
        attackBonusPerLevel = 3;
        defenceBonusPerLevel = 3;
        hpBonusPerLevel = 3;
    }
}
