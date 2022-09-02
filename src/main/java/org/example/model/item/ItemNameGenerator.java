package org.example.model.item;

public class ItemNameGenerator {
    private static final String [] prefixes = {
            "Wooden",
            "Copper",
            "Iron",
            "Steel",
            "Adamantium"
    };

    public static String generateName(int level, String type) {
        if (level > 5) {
            level = 5;
        }
        return prefixes[level - 1] + " " + type;
    }
}
