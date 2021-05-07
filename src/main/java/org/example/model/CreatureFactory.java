package org.example.model;

public class CreatureFactory {
    public static Creature getHero(String heroName, String heroClass)
    {
        switch (heroClass)
        {
            case "Warrior":
                return new Warrior(heroName);
            case "Monster":
                return new Monster();
            default:
                return null;
        }
    }
}
