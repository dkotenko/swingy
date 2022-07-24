package org.example.model.item;

public enum ItemTypes {
    HELMET("Helmet"),
    ARMOR("Armor"),
    WEAPON("Weapon");

    private final String text;

    ItemTypes(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
