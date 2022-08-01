package org.example.model.hero;

import lombok.Data;

@Data
public class Rogue extends Hero{
    public Rogue(String name) {
        this.name = name;
        type = HeroTypes.ROGUE;
        attackModifier = 1.2;
        defenceModifier = 0.8;
        hpModifier = 1.2;
    }
}
