package org.example.service.battle;

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

    public static double getHeroChanceToWin(Hero hero, Monster monster) {
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
    public static BattleResult doFight(Hero hero, Monster monster, boolean print) {
        Creature temp = null;
        BattleResult battleResult = new BattleResult();
        int damage;
        int loops = 0;
        int timerDamage = 0;

        while (hero.getHp() > 0 && monster.getHp() > 0)
        {
            damage = monster.attack(hero) + timerDamage;
            hero.inflictDamage(timerDamage);
            battleResult.setMonsterDamage(battleResult.getMonsterDamage() + damage);
            if (print) {
                System.out.println(String.format("%s caused (%d) damage to %s",
                        monster.getName(), damage, hero.getName()));
            }
            if (hero.isDead()) {
                break ;
            }

            damage = hero.attack(monster) + timerDamage;
            monster.inflictDamage(timerDamage);
            battleResult.setHeroDamage(battleResult.getHeroDamage() + damage);
            if (print) {
                System.out.println(String.format("%s caused (%d) damage to %s",
                        hero.getName(), damage, monster.getName()));
            }
            if (monster.isDead()) {
                break ;
            }if (++loops % 100 == 0) {
                timerDamage++;
            }
        }

        battleResult.setHeroWon(!hero.isDead());
        return battleResult;
    }

    public static BattleResult doBattle(Hero hero, Monster monster, boolean print)
    {
        BattleResult battleResult = doFight(hero, monster, print);

        if (hero.getHp() <= 0) {
            if (print) {
                System.out.println("Hero died :(");
            }
        } else {
            hero.restoreHp();
            monster.restoreHp();
            hero.gainExp(monster.getExp());
            battleResult.setGainedExp(monster.getExp());
            battleResult.setRewards(getRewards(monster));

        }
        return battleResult;
    }

    public static ArrayList<Item> getRewards(Monster monster)
    {
        ArrayList<Item> rewards = new ArrayList<>();
        double rewardChance = 1. / (monster.getLevel());

        double chance = RandomGenerator.getRandom().nextDouble();
        if (chance < rewardChance) {
            rewards.add(new Helm(monster.getLevel()));
        }

        chance = RandomGenerator.getRandom().nextDouble();
        if (chance < rewardChance) {
            rewards.add(new Armor(monster.getLevel()));
        }

        chance = RandomGenerator.getRandom().nextDouble();
        if (chance < rewardChance) {
            rewards.add(new Weapon(monster.getLevel()));
        }
        return rewards;
    }
}
