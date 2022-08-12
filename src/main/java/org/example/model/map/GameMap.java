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
        cells = new GameMapCell[size + 1][size + 1];
        hero.setPosition(new Position(size / 2, size / 2));
        initCells();
        populate();
    }

    private void initCells() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                cells[i][j] = new GameMapCell();
                cells[i][j].setPosition(new Position(i, j));
            }
        }
        cells[size / 2][size / 2].setHero(hero);
    }

    public static int countSize(int level) {
        return (level - 1) * 5 + 10 - 1;
    }

    public void populate() {
        int numMonsters = level * 5;
        int expPerMonster = (int)Math.ceil((double)hero.countLevelExp(level) / level);
        int defaultAttempts = 10;
        MonsterFactory monsterFactory = new MonsterFactory();
        while (numMonsters-- > 0) {
            boolean isSet = false;
            Monster monster = monsterFactory.create(
                    RandomGenerator.getRandomMonsterType()
            );
            monster.setExp(expPerMonster);
            int attempts = defaultAttempts;
            while (attempts-- > 0) {
                int x = RandomGenerator.getRandom().nextInt(size) + 1; //map array[size+1][size+1]
                int y = RandomGenerator.getRandom().nextInt(size) + 1;
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
        }
    }

    private boolean isCenter(int x, int y) {
        return x == size / 2 && y == size / 2;
    }

    public VisibleMap createVisibleMap() {
        VisibleMap visibleMap = new VisibleMap(visibleSize);

        int minY = hero.getPosition().getY() - visibleSize / 2;
        int minX = hero.getPosition().getX() - visibleSize / 2;

        for (int i = 1; i <= visibleSize; i++) {
            for (int j = 1; j <= visibleSize; j++) {
                int currY = i + minY;
                int currX = j + minX;
                visibleMap.getCells()[i][j].setPosition(new Position(currX, currY));
                if (currY < 1 || currY > size || currX < 1 || currX > size) {
                    continue;
                }
                if (cells[currY][currX].containsMonster()) {
                    visibleMap.getCells()[i][j].setType(
                            cells[currY][currX].getMonster().getType()
                    );
                } else if (cells[currY][currX].containsHero()) {
                    visibleMap.getCells()[i][j].setType("Hero");
                } else {
                    visibleMap.getCells()[i][j].setType("Ground");
                }
            }
        }
        return visibleMap;
    }

    public void moveTo(Position newPosition) {
        Position currPosition = hero.getPosition();
        cells[currPosition.getY()][currPosition.getX()].setHero(null);
        cells[newPosition.getY()][newPosition.getX()].setHero(hero);
    }
}
