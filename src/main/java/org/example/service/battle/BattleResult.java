package org.example.service.battle;

import lombok.Getter;
import lombok.Setter;
import org.example.model.item.Item;

import java.util.ArrayList;

@Getter
@Setter
public class BattleResult {
    private boolean isHeroWon;
    private ArrayList<Item> rewards;
    private int heroDamage;
    private int monsterDamage;
    private int gainedExp;
    private int newLvl;
    private String heroName;

    public boolean hasRewards() {
        return (rewards != null && rewards.size() > 0);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Battle result:\n");
        if (newLvl != 0) {
            builder.append(String.format("Congratulations! %s has reached level %d\n", heroName, newLvl));
        }
        builder.append("Hero inflicted " + heroDamage + " damage\n");
        builder.append("Monster inflicted " + monsterDamage + " damage\n");
        builder.append((isHeroWon ? "Hero has won" : "Hero died") + "\n");
        if (isHeroWon) {
            builder.append("Hero gained " + gainedExp + " experience points\n");
            if (hasRewards()) {
                builder.append("Hero gained rewards:\n");
                rewards.forEach(x-> builder.append(x.toString() + "\n"));
            } else {
                builder.append("Hero gained no rewards\n");
            }
        }
        return builder.toString();
    }
}
