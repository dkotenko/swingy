package org.example.model.hero;

import org.example.model.hero.Hero;
import org.example.model.hero.HeroTypes;

public class Warrior extends Hero {
    public Warrior(String name){
        this.name = name;
        type = HeroTypes.WARRIOR;
        classModifierAttack = 0.8;
        classModifierDefence = 1.2;
        classModifierHitPoints = 1.2;
    }
}
