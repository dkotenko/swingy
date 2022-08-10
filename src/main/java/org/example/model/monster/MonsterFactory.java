package org.example.model.monster;

import org.example.model.entity.AbstractFactory;

public class MonsterFactory implements AbstractFactory<Monster> {

    public Monster create(String name, String type) {
        return create(type);
    }

    public Monster create(String type) {
        if (MonsterTypes.HEAVY_BANDIT.equals(type))
            return new HeavyBandit();
        else if (MonsterTypes.MUSHROOM.equals(type))
            return new Mushroom();
        else if (MonsterTypes.SKELETON.equals(type))
            return new Skeleton();
        return null;
    }
}
