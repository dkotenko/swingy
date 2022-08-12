package org.example.model;

import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.model.hero.HeroTypes;
import org.example.model.map.GameMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GameMapTest {

    @Test
    public void monster_number_per_map_check() {
        Hero hero = new HeroFactory().create(HeroTypes.WARRIOR);

        for (int level = 1; level < 11; level++) {
            hero.setLevel(level);
            GameMap gameMap = new GameMap(hero);
            long monsterNum = Arrays.stream(gameMap.getCells()).flatMap(Arrays::stream)
                    .collect(Collectors.toList())
                    .stream()
                    .filter(x -> x.containsMonster())
                    .count();
            System.out.println(String.format("Number of monsters for level %d = %d", hero.getLevel(), (int)monsterNum));
            Assertions.assertTrue(monsterNum >= 0);
        }
    }

    @Test
    public void count_size_check() {
        for (int i = 0; i < 10; i++) {
            int size = GameMap.countSize(i + 1);
            System.out.println(String.format(
                    "Map size for level %2d: %2d | cell number: %4d",
                    i + 1, size, size * size - 1
            ));
        }
    }
}
