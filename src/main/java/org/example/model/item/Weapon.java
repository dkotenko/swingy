package org.example.model.item;

public class Weapon extends Item{
    public Weapon(int level) {
        super(level, ItemTypes.WEAPON);
        type = ItemTypes.WEAPON;
        attack = level + 2;
    }
}
