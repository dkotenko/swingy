package org.example.model.monster;

import org.example.model.creature.Creature;

public abstract class Monster extends Creature {
    public Monster()
    {
        name = MonsterName.getName();
    }
}
