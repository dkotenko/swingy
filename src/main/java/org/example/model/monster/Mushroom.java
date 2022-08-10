package org.example.model.monster;

public class Mushroom extends Monster{
    public Mushroom(){
        super();
        setStats();
    }

    protected void setStats() {
        super.setStats();
        type = MonsterTypes.MUSHROOM;
        attackModifier = 1.2;
        defenceModifier = 0.2;
        hpModifier = 1.6;
    }
}
