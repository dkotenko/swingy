package org.example.model;

public enum HeroClass {
    WARRIOR("Warrior");

    private final String text;

    HeroClass(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}

