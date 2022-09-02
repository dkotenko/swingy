package org.example.model.monster;

import lombok.Data;
import org.example.model.creature.Creature;

@Data
public class Monster extends Creature {

    private int exp;

    public Monster()
    {
        super(0, 0, 20);
        setStats();
        updateAttributes();
    }

    protected void setStats() {
        attackBonusPerLevel = 3;
        defenceBonusPerLevel = 3;
        hpBonusPerLevel = 3;
    }

    @Override
    public String toString() {
        return String.format("%s, level - %d, attack - %d, defence - %d, hp - %d",
                name,
                level,
                attack,
                defence,
                hp);
    }
}
