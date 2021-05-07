package org.example.model;

import org.example.Handlers;

public class MonsterName {
    private static final String[] names = {
            "Red Sea Centipede",
            "Dreaming Smasher-dissector",
            "Lesser Runner-blaster",
            "Infernal Mold",
            "Unholy Cactus Whale",
            "First Spectre Grotesque",
            "Emperess-blasphemer",
            "Primordial Shimmer Eel",
            "Spectral Witch",
            "Time Leech",
            "Chaotic Venom Titan",
            "Squid Snapper"
    };

    public static String getName()
    {
        return names[Handlers.getRandom(names.length)];
    }
}
