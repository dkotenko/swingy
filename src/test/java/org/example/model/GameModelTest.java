package org.example.model;

import org.example.model.creature.Creature;
import org.example.model.hero.*;
import org.example.model.monster.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class GameModelTest {

    static HeroFactory heroFactory = new HeroFactory();
    static MonsterFactory monsterFactory = new MonsterFactory();

    @Test
    public void hero_check() {
        Warrior warrior = (Warrior) heroFactory.create(HeroTypes.WARRIOR);
        System.out.println(warrior);
        Assertions.assertTrue(warrior.getHp() >= 0);
    }

    private void printCreatureStats(Creature creature, int times) {
        System.out.println("\n" + creature.getType());
        for (int i = 0; i < times; i ++) {
            System.out.println((i + 1) + " level: " + creature.statsToString());
            creature.setLevel(i + 2);
        }
    }

    @Test
    public void monster_stats_per_level() {
        Skeleton skeleton = (Skeleton) monsterFactory.create(MonsterTypes.SKELETON);
        HeavyBandit heavyBandit = (HeavyBandit) monsterFactory.create(MonsterTypes.HEAVY_BANDIT);
        Mushroom mushroom = (Mushroom) monsterFactory.create(MonsterTypes.MUSHROOM);
        int iterationNumber = 10;

        printCreatureStats(skeleton, iterationNumber);
        printCreatureStats(heavyBandit, iterationNumber);
        printCreatureStats(mushroom, iterationNumber);
        Assertions.assertTrue(skeleton.getHp() >= 0);
    }

    @Test
    public void hero_stats_per_level() {
        Warrior warrior = (Warrior) heroFactory.create(HeroTypes.WARRIOR);
        Rogue rogue = (Rogue) heroFactory.create(HeroTypes.ROGUE);
        Mage mage = (Mage) heroFactory.create(HeroTypes.MAGE);
        int iterationNumber = 10;

        printCreatureStats(warrior, iterationNumber);
        printCreatureStats(rogue, iterationNumber);
        printCreatureStats(mage, iterationNumber);
    }
}

