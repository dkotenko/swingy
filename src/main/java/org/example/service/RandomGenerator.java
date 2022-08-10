package org.example.service;

import org.example.model.map.Position;
import org.example.model.monster.MonsterTypes;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator {
    @Value("${swingy.random.spread:0.2}")
    private static double spread;
    private static ArrayList<String> monsterTypes = MonsterTypes.getMonsterTypes();
    private static Random random = new Random();

    /**
     * Math.random() * (b-a) + a
     * @return
     */
    public static double getRandomCoef() {
        return (Math.random() * spread * 2) + (1 - spread);
    }

    /**
     *
     * @param low - included
     * @param high - not included
     * @return
     */
    public static int getRandomFromRange(int low, int high) {
        return (int)(Math.random() * (high - low) + low);
    }

    public static Random getRandom() {
        return random;
    }

    public static String getRandomMonsterType() {
        return monsterTypes.get(random.nextInt(monsterTypes.size()));
    }

    public static Position getRandomPosition(int size) {
        int x = size / 2;
        int y = size / 2;

        while (x == size / 2 && y == size / 2) {
            x = random.nextInt(size);
            y = random.nextInt(size);
        }
        return new Position(x, y);
    }

}
