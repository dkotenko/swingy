package org.example.model.hero;

import lombok.Data;

@Data
public class Rogue extends Hero{
    public Rogue(String name) {
        this.name = name;
        type = HeroTypes.ROGUE;
        classModifierAttack = 1.2;
        classModifierDefence = 0.8;
        classModifierHitPoints = .2;
    }
}
