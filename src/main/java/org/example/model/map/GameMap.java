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
    private int center;

    public GameMap(Hero hero)
    {
        this.level = hero.getLevel();
        this.hero = hero;
        size = countSize(level);
        cells = new GameMapCell[size + 1][size + 1];
        center = size % 2 == 0 ? size / 2 : size / 2 + 1;
        hero.setPosition(new Position(center, center));
        initCells();
        populate();
    }

    private void initCells() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                cells[i][j] = new GameMapCell();
                cells[i][j].setPosition(new Position(j, i));
            }
        }
        cells[center][center].setHero(hero);
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
            monster.setLevel(RandomGenerator.getRandomFromRange(level, level + 2));
            int attempts = defaultAttempts;
            while (attempts-- > 0) {
                int x = RandomGenerator.getRandom().nextInt(size) + 1; //map array[size+1][size+1]
                int y = RandomGenerator.getRandom().nextInt(size) + 1;
                if (!cells[y][x].containsMonster() && !isCenter(x, y)) {
                    cells[y][x].setMonster(monster);
                    monster.setPosition(new Position(x, y));
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
        return x == center && y == center;
    }

    public VisibleMap createVisibleMap() {
        VisibleMap visibleMap = new VisibleMap(visibleSize, size, level);

        int minY = hero.getPosition().getY() - visibleSize / 2;
        int minX = hero.getPosition().getX() - visibleSize / 2;

        for (int i = 1; i <= visibleSize; i++) {
            for (int j = 1; j <= visibleSize; j++) {
                int currY = i + minY - 1;
                int currX = j + minX - 1;
                visibleMap.getCells()[i][j].setPosition(new Position(currX, currY));
                if (currY < 1 || currY > size || currX < 1 || currX > size) {
                    continue;
                }
                if (cells[currY][currX].containsMonster()) {
                    visibleMap.getCells()[i][j].setType(
                            cells[currY][currX].getMonster().getType()
                    );
                } else if (cells[currY][currX].containsHero()) {
                    visibleMap.getCells()[i][j].setType(hero.getType());
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
        hero.setPosition(newPosition);
        hero.setPrevPosition(currPosition);
    }

    public GameMapCell getCellByPosition(Position p) {
        return cells[p.getY()][p.getX()];
    }
}
