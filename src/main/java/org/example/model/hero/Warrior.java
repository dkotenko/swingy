package org.example.model.hero;

import org.example.model.hero.Hero;
import org.example.model.hero.HeroTypes;

public class Warrior extends Hero {
    public Warrior(String name){
        this.name = name;
        type = HeroTypes.WARRIOR;
        attackModifier = 0.8;
        defenceModifier = 1.2;
        hpModifier = 1.2;
    }
}
