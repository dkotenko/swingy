package org.example.model.item;

public class Helm extends Item{
    public Helm(int level) {
        super(level, ItemTypes.HELM);
        type = ItemTypes.HELM;
        hp = level * 2;
    }
}
