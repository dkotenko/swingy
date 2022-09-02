package org.example.model.item;

public class Armor extends Item{
    public Armor(int level) {
        super(level, ItemTypes.ARMOR);
        type = ItemTypes.ARMOR;
        defence = level + 2;
    }
}
