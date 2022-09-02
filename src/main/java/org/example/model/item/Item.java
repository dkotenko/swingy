package org.example.model.item;

import org.example.model.entity.Entity;

public class Item extends Entity {
    public Item(){};
    public Item(int level, String type){
        this.level = level;
        this.type = type;
        name = ItemNameGenerator.generateName(level, type);
    };

    @Override
    public String toString() {
        return type + " level " + level + " \"" + name + "\"";
    }
}
