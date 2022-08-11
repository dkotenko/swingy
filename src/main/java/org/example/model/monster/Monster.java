package org.example.model.monster;

import org.example.model.creature.Creature;

public class Monster extends Creature {
    public Monster()
    {
        super(4, 4, 40);
        setStats();
        updateAttributes();
    }

    protected void setStats() {
        attackBonusPerLevel = 4;
        defenceBonusPerLevel = 4;
        hpBonusPerLevel = 4;
    }
}
