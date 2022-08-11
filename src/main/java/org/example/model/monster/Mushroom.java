package org.example.model.monster;

import org.example.model.entity.EntityNameGenerator;

public class Mushroom extends Monster{
    public Mushroom(){
        super();
        setStats();
    }

    protected void setStats() {
        super.setStats();
        type = MonsterTypes.MUSHROOM;
        name = EntityNameGenerator.generate(this);
        attackModifier = 1.2;
        defenceModifier = 0.2;
        hpModifier = 1.6;
        updateAttributes();
    }
}
