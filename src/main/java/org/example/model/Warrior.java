package org.example.model;

public class Warrior extends Hero{
    public Warrior(String name){
        this.name = name;
        className = HeroClass.WARRIOR.toString();
        classModifierAttack = 1.2;
        classModifierDefence = 1.2;
        classModifierHitPoints = 0.7;
    }
}
