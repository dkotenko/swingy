package org.example.controller;

import org.example.model.creature.Creature;
import org.example.model.hero.Hero;
import org.example.model.item.Armor;
import org.example.model.item.Helm;
import org.example.model.item.Item;
import org.example.model.item.Weapon;
import org.example.model.monster.Monster;
import org.example.service.RandomGenerator;

import java.util.ArrayList;

public class Battle {
    static int simTimes = 100;

    public double getHeroChanceToWin(Hero hero, Monster monster) {
        int winTimes = 0;

        for (int i = 0; i < simTimes; i++) {
            doFight(hero, monster, false);
            if (hero.getHp() > 0) {
                winTimes++;
            }
            hero.restoreHp();
            monster.restoreHp();
        }
        return (double) winTimes / (double) simTimes;
    }

    /*
     * Monster strikes first
     */
    public void doFight(Hero hero, Monster monster, boolean isNotSimulation) {
        Creature attacker = hero;
        Creature defender = monster;
        Creature temp = null;

        while (attacker.getHp() > 0 && defender.getHp() > 0)
        {
            temp = defender;
            defender = attacker;
            attacker = temp;

            int damage = attacker.attack(defender);
            if (isNotSimulation) {
                System.out.println(String.format("%s caused (%d) damage to %s",
                        attacker.getName(), damage, defender.getName()));
            }
        }
    }

    public void doBattle(Hero hero, Monster monster)
    {
        doFight(hero, monster, true);

        if (hero.getHp() <= 0) {
            System.out.println("Hero died :(");

            //invokeEndGame();
            return;
        }
        hero.restoreHp();
        monster.restoreHp();
    }

    public ArrayList<Item> getRewards(Monster monster)
    {
        ArrayList<Item> rewards = new ArrayList<>();
        double rewardChance = 1 / (monster.getLevel() * 3);

        if (RandomGenerator.getRandom().nextDouble() < rewardChance) {
            rewards.add(new Helm(monster.getLevel()));
        }
        if (RandomGenerator.getRandom().nextDouble() < rewardChance) {
            rewards.add(new Armor(monster.getLevel()));
        }
        if (RandomGenerator.getRandom().nextDouble() < rewardChance) {
            rewards.add(new Weapon(monster.getLevel()));
        }
        return rewards;
    }
}
