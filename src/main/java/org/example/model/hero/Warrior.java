package org.example.model.hero;

import org.example.model.hero.dto.HeroDTO;

public class Warrior extends Hero {
    public Warrior(String name){
        super(name);
        setStats();
    }

    public Warrior(HeroDTO heroDTO) {
        super(heroDTO);
        setStats();
    }

    private void setStats() {
        type = HeroTypes.WARRIOR;
        attackModifier = 0.8;
        defenceModifier = 1.2;
        hpModifier = 1.2;
    }
}
