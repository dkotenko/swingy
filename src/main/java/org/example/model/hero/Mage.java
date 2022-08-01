package org.example.model.hero;

import lombok.Data;

@Data
public class Mage extends Hero{
    public Mage(String name) {
        this.name = name;
        type = HeroTypes.MAGE;
        attackModifier = 1.6;
        defenceModifier = 0.6;
        hpModifier = 0.8;
    }
}
