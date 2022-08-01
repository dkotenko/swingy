package org.example.model.hero;

import org.example.model.entity.AbstractFactory;

public class HeroFactory implements AbstractFactory<Hero> {

    public Hero create(String name, String type) {
        if (HeroTypes.WARRIOR.equals(type))
            return new Warrior(name);
        else if (HeroTypes.ROGUE.equals(type))
            return new Rogue(name);
        else if (HeroTypes.MAGE.equals(type))
            return new Mage(name);
        return null;
    }
}
