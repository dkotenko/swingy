package org.example.model;

import org.example.model.hero.HeroFactory;
import org.example.model.hero.HeroTypes;
import org.example.model.hero.Warrior;
import org.example.model.monster.Monster;
import org.example.model.monster.MonsterFactory;
import org.example.service.RandomGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class GameModelTest {

    @Test
    public void monster_check() {
        Monster monster = new MonsterFactory().create(RandomGenerator.getRandomMonsterType());
        System.out.println(monster.toString());
        Assert.assertTrue(monster.getHp() >= 0);
    }

    @Test
    public void hero_check() {
        HeroFactory heroFactory = new HeroFactory();
        Warrior warrior = (Warrior) heroFactory.create(HeroTypes.WARRIOR);
        System.out.println(warrior);
        Assert.assertTrue(warrior.getHp() >= 0);


    }


}
