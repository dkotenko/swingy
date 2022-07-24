package org.example.model.hero;

import org.example.model.hero.Hero;
import org.example.model.hero.HeroTypes;

public class Warrior extends Hero {
    public Warrior(String name){
        this.name = name;
        className = HeroTypes.WARRIOR.toString();
        classModifierAttack = 1.2;
        classModifierDefence = 1.2;
        classModifierHitPoints = 0.7;
    }
}
