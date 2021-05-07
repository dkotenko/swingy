package org.example.controller;
import org.example.Handlers;
import org.example.model.Monster;
import org.example.model.Hero;

public class Battle {
    private static boolean isBattle(Controller controller)
    {
        return controller.model;
    }

    public static void checkBattle(Controller controller)
    {
        if ()
            ;
    }

    public static void doBattle(Hero hero, Monster monster)
    {
        long damageToMonster = hero.getAttack() - monster.getDefence();
        long damageToHero = monster.getAttack() - hero.getDefence();

        while (hero.getHitPoints() > 0 && monster.getHitPoints() > 0)
        {
            if (damageToMonster > 0)
                monster.setHitPoints(monster.getHitPoints() - damageToMonster);
            if (damageToHero > 0)
                hero.setHitPoints(hero.getHitPoints() - damageToHero);
        }

        if (hero.getHitPoints() <= 0) {
            invokeEndGame();
            return;
        }

        if (monster.getHitPoints() <= 0) {
            getReward(hero, monster);
        }
    }

    public static String getReward(Hero hero, Monster monster)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (Handlers.getRandom(100) > 70) {
            stringBuilder.append(String.format("Helmet tier%d looted!\n", monster.getHelm()));
            if (monster.getHelm() > hero.getHelm()) {
                hero.setHelm(monster.getHelm());
                hero.recalcAttributes();
                stringBuilder.append(String.format("Helmet tier%d equipped! Current hit points value = %d\n",
                        hero.getHelm(),
                        hero.getHitPoints()));
            }
        }

        if (Handlers.getRandom(100) > 70) {
            stringBuilder.append(String.format("Armor tier%d looted!\n", monster.getArmor()));
            if (monster.getArmor() > hero.getArmor()) {
                hero.setArmor(monster.getArmor());
                hero.recalcAttributes();
                stringBuilder.append(String.format("Armor tier%d equipped! Current defence value = %d\n",
                        hero.getArmor(),
                        hero.getDefence()));
            }
        }

        if (Handlers.getRandom(100) > 70) {
            stringBuilder.append(String.format("Weapon tier%d looted!\n", monster.getWeapon()));
            if (monster.getWeapon() > hero.getWeapon()) {
                hero.setWeapon(monster.getWeapon());
                hero.recalcAttributes();
                stringBuilder.append(String.format("Weapon tier%d equipped! Current attack value = %d\n",
                        hero.getWeapon(),
                        hero.getAttack()));
            }
        }

        if (stringBuilder.toString().equals("")) {
            stringBuilder.append("There is no reward\n");
        }
        return stringBuilder.toString();

        }

    }
}
