package org.example.model;

import org.example.model.hero.*;
import org.example.model.monster.Monster;
import org.example.model.monster.MonsterFactory;
import org.example.service.RandomGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


class GameModelTest {

    static HeroFactory heroFactory = new HeroFactory();
    static MonsterFactory monsterFactory = new MonsterFactory();

    @Test
    public void monster_check() {
        Monster monster = monsterFactory.create(RandomGenerator.getRandomMonsterType());
        System.out.println(monster.toString());
        Assertions.assertTrue(monster.getHp() >= 0);
    }

    @Test
    public void hero_check() {
        Warrior warrior = (Warrior) heroFactory.create(HeroTypes.WARRIOR);
        System.out.println(warrior);
        Assertions.assertTrue(warrior.getHp() >= 0);
    }

    @Test
    public void hero_stats_per_level() {
        Warrior warrior = (Warrior) heroFactory.create(HeroTypes.WARRIOR);
        Rogue rogue = (Rogue) heroFactory.create(HeroTypes.ROGUE);
        Mage mage = (Mage) heroFactory.create(HeroTypes.MAGE);
        int iterationNumber = 10;

        System.out.println("\nWarrior");
        for (int i = 0; i < iterationNumber; i ++) {
            System.out.println((i + 1) + " level: " + warrior.statsToString());
            warrior.setLevel(i + 2);
        }

        System.out.println("\nRogue");
        for (int i = 0; i < iterationNumber; i ++) {
            System.out.println((i + 1) + " level: " + rogue.statsToString());
            rogue.setLevel(i + 2);
        }

        System.out.println("\nMage");
        for (int i = 0; i < iterationNumber; i ++) {
            System.out.println((i + 1) + " level: " + mage.statsToString());
            mage.setLevel(i + 2);
        }
    }




}

