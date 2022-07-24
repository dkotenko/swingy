package org.example.model.hero;

public enum HeroTypes {
    WARRIOR("Warrior"),
    MAGE("Mage"),
    ROGUE("Rogue");

    private final String text;

    HeroTypes(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}

