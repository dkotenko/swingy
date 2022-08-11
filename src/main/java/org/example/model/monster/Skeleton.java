package org.example.model.monster;

import org.example.model.entity.EntityNameGenerator;

public class Skeleton extends Monster{
    public Skeleton(){
        super();
        setStats();
    }

    protected void setStats() {
        super.setStats();
        type = MonsterTypes.SKELETON;
        name = EntityNameGenerator.generate(this);
        attackModifier = 0.75;
        defenceModifier = 1;
        hpModifier = 0.75;
        updateAttributes();
    }


}
