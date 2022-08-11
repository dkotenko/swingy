package org.example.model.monster;

public class Skeleton extends Monster{
    public Skeleton(){
        super();
        setStats();
    }

    protected void setStats() {
        super.setStats();
        type = MonsterTypes.SKELETON;
        attackModifier = 0.75;
        defenceModifier = 1.5;
        hpModifier = 0.75;
        updateAttributes();
    }


}
