package org.example.model.item;

import org.example.model.entity.AbstractFactory;

public class ItemFactory implements AbstractFactory<Item> {
    public Item create(String name, String type) {
        return null;
    }
    public Item create(String type) {
        return null;
    }
}
