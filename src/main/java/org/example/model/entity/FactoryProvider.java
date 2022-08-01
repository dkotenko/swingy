package org.example.model.entity;

import org.example.model.hero.HeroFactory;
import org.example.model.hero.Hero;
import org.example.model.item.Item;
import org.example.model.item.ItemFactory;
import org.example.model.monster.Monster;

public class FactoryProvider {
    public static AbstractFactory getFactory(Entity entity) {
        if(entity instanceof Hero){
            return new HeroFactory();
        }
        else if(entity instanceof Item){
            return new ItemFactory();
        }
        return null;
    }
}
