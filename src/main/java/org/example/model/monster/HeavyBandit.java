package org.example.model.monster;

import org.example.model.entity.EntityNameGenerator;

public class HeavyBandit extends Monster{

    public HeavyBandit(){
        super();
        setStats();
    }

    protected void setStats() {
        super.setStats();
        type = MonsterTypes.HEAVY_BANDIT;
        name = EntityNameGenerator.generate(this);
        attackModifier = 1.2;
        defenceModifier = 0.9;
        hpModifier = 0.9;
        updateAttributes();
    }
}
