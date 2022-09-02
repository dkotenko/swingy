package org.example.model;

import org.example.service.battle.Battle;
import org.example.model.hero.Warrior;
import org.example.model.monster.Skeleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BattleTest {


    @Test
    public void fight_check() {
        Battle battle = new Battle();

        Warrior warrior = new Warrior("Warrior");
        Skeleton skeleton = new Skeleton();
        int heroHp = warrior.getHp();
        int monsterHp = skeleton.getHp();

        double res = battle.getHeroChanceToWin(warrior, skeleton);
        System.out.println(String.format("Hero chance to win: %.0f%%", res * 100));

        Assertions.assertEquals(heroHp, warrior.getHp());
        Assertions.assertEquals(monsterHp, skeleton.getHp());
    }
}
