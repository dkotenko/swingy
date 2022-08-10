package org.example.model.monster;

import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;

public class MonsterTypes {
    public static String HEAVY_BANDIT = "HeavyBandit";
    public static String MUSHROOM = "Mushroom";
    public static String SKELETON = "Skeleton";



    public static ArrayList<String> getMonsterTypes() {
        ArrayList<String> monsterTypes = new ArrayList<>();

        ReflectionUtils.doWithFields(
                MonsterTypes .class,
                (field -> monsterTypes.add((String) field.get(null)))
        );
        return monsterTypes;
    }
}
