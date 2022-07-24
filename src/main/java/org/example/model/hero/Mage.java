package org.example.model.hero;

import lombok.Data;

@Data
public class Mage extends Hero{
    public Mage(String name) {
        this.name = name;
        type = HeroTypes.MAGE;
        classModifierAttack = 1.6;
        classModifierDefence = 0.6;
        classModifierHitPoints = 0.8;
    }
}
