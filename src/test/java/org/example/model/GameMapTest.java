package org.example.model;

import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.model.hero.HeroTypes;
import org.example.model.map.GameMap;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GameMapTest {

    @Test
    public void GetMonsterNumber() {
        Hero hero = new HeroFactory().create(HeroTypes.WARRIOR);
        GameMap gameMap = new GameMap(hero);
        long monsterNum = Arrays.stream(gameMap.getCells()).flatMap(Arrays::stream)
                .collect(Collectors.toList())
                .stream()
                .filter(x -> !x.isEmpty())
                .count();
        System.out.println(String.format("Number of monsters for level %d = %d", hero.getLevel(), (int)monsterNum));
        Assert.assertTrue(monsterNum >= 0);
    }
}
