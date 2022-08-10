package org.example.model.monster;

public class HeavyBandit extends Monster{

    public HeavyBandit(){
        super();
        setStats();
    }

    protected void setStats() {
        super.setStats();
        type = MonsterTypes.HEAVY_BANDIT;
        attackModifier = 1.2;
        defenceModifier = 0.9;
        hpModifier = 0.9;
    }
}
