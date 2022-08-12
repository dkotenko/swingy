package org.example.model.map;

import lombok.Data;
import org.example.controller.VisibleMap;
import org.example.model.hero.Hero;
import org.example.model.monster.Monster;
import org.example.model.monster.MonsterFactory;
import org.example.service.RandomGenerator;

import java.util.ArrayList;

@Data
public class GameMap {
    private int size;
    private int level;
    private GameMapCell[][] cells;
    private ArrayList<Monster> monsters;
    private Hero hero;
    private final static int visibleSize = 9;

    public GameMap(Hero hero)
    {
        this.level = hero.getLevel();
        this.hero = hero;
        size = countSize(level);
        cells = new GameMapCell[size][size];
        hero.setPosition(new Position(size / 2, size / 2));
        initCells();
        populate();
    }

    private void initCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new GameMapCell();
                cells[i][j].setPosition(new Position(i, j));
                if (i == size / 2 && j == size / 2) {
                    cells[i][j].setCenter(true);
                }
            }
        }
    }

    public static int countSize(int level) {
        return (level - 1) * 5 + 10 - 1;
    }

    public void populate() {
        int monstersHpPerMap =  Hero.countLevelExp(level);
        int defaultAttempts = 10;
        MonsterFactory monsterFactory = new MonsterFactory();
        while (monstersHpPerMap > 0) {
            boolean isSet = false;
            Monster monster = monsterFactory.create(
                    RandomGenerator.getRandomMonsterType()
            );
            int attempts = defaultAttempts;
            while (attempts-- > 0) {
                int x = RandomGenerator.getRandom().nextInt(size);
                int y = RandomGenerator.getRandom().nextInt(size);
                if (!cells[y][x].containsMonster() && !isCenter(x, y)) {
                    cells[y][x].setMonster(monster);
                    monster.setPosition(new Position(y, x));
                    isSet = true;
                    break ;
                }
            }
            if (!isSet) {
                System.err.println(String.format("Map creation error: monster position not set after %d attempts", defaultAttempts));
            }
            monstersHpPerMap -= monster.getHp();
        }
    }

    private boolean isCenter(int x, int y) {
        return x == size / 2 && y == size / 2;
    }

    public VisibleMap createVisibleMap() {
        VisibleMap visibleMap = new VisibleMap(visibleSize);
        int minY = hero.getPosition().getY() - visibleSize / 2;
        int maxY = hero.getPosition().getY() + visibleSize / 2;
        int minX = hero.getPosition().getX() - visibleSize / 2;
        int maxX = hero.getPosition().getX() + visibleSize / 2;
        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; i <= maxX; j++) {
                visibleMap.getCells()[i][j].setPosition(new Position(i, j));
                if (i < 0 || i >= size) {
                    continue;
                }
                if (j < 0 || j >= size) {
                    continue;
                }
                if (cells[i][j].containsMonster()) {
                    visibleMap.getCells()[i][j].setType(
                            cells[i][j].getMonster().getType()
                    );
                } else if (cells[i][j].containsHero()) {
                    visibleMap.getCells()[i][j].setType(
                            cells[i][j].getHero().getType()
                    );
                }
            }
        }
        return visibleMap;
    }
}
