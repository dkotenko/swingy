package org.example.model.entity;

import org.example.model.entity.Entity;

import java.util.Random;

public class EntityNameGenerator {

    private static final String[] prefixes = {
            "wooden",
            "glass",
            "fury",
            "infernal",
            "unholy",
            "spectral",
            "primordial",
            "dark iron",
            "chaotic",
            "ebony",
            "steel"
    };

    public String generate(Entity entity) {
        StringBuilder builder = new StringBuilder();
        builder.append(getExpertise(entity.getLevel()));
        builder.append(" ");
        builder.append(getRandomPrefix());
        builder.append(" ");
        builder.append(entity.getTypeName());
        return builder.toString();
    }

    private String getExpertise(int level) {
        switch (level) {
            case 1:
                return "Newbie";
            case 2:
                return "Veteran";
            case 3:
                return "Master";
            case 4:
                return "Elite";
            case 5:
                return "Legendary";
        }
        return "Default";
    }

    private String getRandomPrefix() {
        int rnd = new Random().nextInt(prefixes.length);
        return prefixes[rnd];
    }
}
