package org.example.controller;
import lombok.RequiredArgsConstructor;
import org.example.model.creature.Creature;
import org.example.model.hero.Hero;
import org.example.model.monster.Monster;
import org.example.service.RandomGenerator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Battle {
    RandomGenerator randomGenerator;

    private boolean isBattle(GameController gameController)
    {

        return true;//return controller.model;
    }

    public void checkBattle(GameController gameController)
    {
        if (true)
            ;
    }

    /**
     *
     * @param hero
     * @param monster
     *
     * Monster strikes first
     */

    public double getHeroChance(Hero hero, Monster monster) {
        int winTimes = 0;
        int simTimes = 100;
        for (int i = 0; i < simTimes; i++) {
            doBattle(hero, monster, false);
        }

        return winTimes / simTimes;
    }

    public void doBattle(Hero hero, Monster monster, boolean isPrinted)
    {
        Creature attacker = hero;
        Creature defender = monster;
        Creature temp = null;
        int heroHpToRestore = hero.getHp();

        while (attacker.getHp() > 0 && defender.getHp() > 0)
        {
            temp = defender;
            defender = attacker;
            attacker = temp;

            int damage = attacker.attack(defender);
            if (isPrinted) {
                System.out.println(String.format("%s caused (%d) damage to %s\n",
                        attacker.getName(), damage, defender.getName()));
            }
        }

        if (hero.getHp() <= 0) {
            System.out.println("Hero died :(");
            //invokeEndGame();
            return;
        }
        /*
        hero.restoreHp();
        if (monster.getHitPoints() <= 0) {
            getReward(hero, monster);
        }
        */
    }

    public String getReward(Hero hero, Monster monster)
    {

        return "";
    }
}
