package org.example.model;

import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.model.hero.HeroTypes;
import org.example.model.map.Directions;
import org.example.model.map.GameMap;
import org.example.model.map.GameMapCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameMapTest {

    @Test
    public void move_check() {
        Hero hero = new HeroFactory().create(HeroTypes.WARRIOR);
        GameModel gameModel = new GameModel(null, null);
        gameModel.setCurrentHero(hero);
        gameModel.setGameMap(new GameMap(hero));
        gameModel.moveHero(Directions.EAST);
        GameMapCell cell = gameModel.getGameMap().getCellByPosition(gameModel.getCurrentHero().getPosition());
        Assertions.assertEquals(cell.getPosition().getX(), gameModel.getCurrentHero().getPosition().getX());
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
