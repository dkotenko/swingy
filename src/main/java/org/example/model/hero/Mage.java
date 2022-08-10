package org.example.model.hero;

import org.example.model.hero.dto.HeroDTO;

public class Mage extends Hero{

    public Mage(String name){
        super(name);
        setStats();
    }

    public Mage(HeroDTO heroDTO) {
        super(heroDTO);
        setStats();
    }

    private void setStats() {
        type = HeroTypes.MAGE;
        attackModifier = 1.6;
        defenceModifier = 0.6;
        hpModifier = 0.8;
    }
}
