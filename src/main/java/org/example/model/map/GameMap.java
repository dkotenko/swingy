package org.example.model.map;

import lombok.Data;
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

    public GameMap(Hero hero)
    {
        this.level = hero.getLevel();
        size = (level - 1) * 5 + 10 - 1;
        cells = new GameMapCell[size][size];
        hero.setPosition(new Position(size / 2, size / 2));
        initCells();
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
                int x = RandomGenerator.getRandom().nextInt();
                int y = RandomGenerator.getRandom().nextInt();
                if (cells[y][x].isEmpty()) {
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

    private boolean isCenter(Position position) {
        return position.getX() == size / 2 && position.getY() == size / 2;
    }
}
