package org.example.model.hero;

import org.example.model.creature.AbstractFactory;

public class HeroFactory implements AbstractFactory<Hero> {

    public Hero create(String name, HeroTypes type) {
        if (type == HeroTypes.WARRIOR)
            return new Warrior(name);
        else if (type == HeroTypes.ROGUE)
            return new Rogue(name);
        else if (type == HeroTypes.MAGE)
            return new Mage(name);
        return null;
    }
}
