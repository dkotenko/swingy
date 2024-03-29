package org.example.model.hero;

import org.example.model.hero.dto.HeroDTO;

public class Rogue extends Hero{

    public Rogue(String name){
        super(name);
        setStats();
    }

    public Rogue(HeroDTO heroDTO) {
        super(heroDTO);
        setStats();
    }

    private void setStats() {
        type = HeroTypes.ROGUE;
        attackModifier = 1.4;
        defenceModifier = 0.6;
        hpModifier = 1.2;
        attackBonusPerLevel = 4;
        defenceBonusPerLevel = 2;
        hpBonusPerLevel = 4;
        updateAttributes();
    }


}
