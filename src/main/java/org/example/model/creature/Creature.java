package org.example.model.creature;

import lombok.Data;
import org.example.model.Position;
import org.example.model.entity.Entity;
import org.example.model.item.Armor;
import org.example.model.item.Helm;
import org.example.model.item.Item;
import org.example.model.item.Weapon;

import java.util.ArrayList;

@Data
public abstract class Creature extends Entity {
    protected int attack;
    protected int defence;
    protected int hp;
    protected Weapon weapon;
    protected Armor armor;
    protected Helm helm;
    protected double attackModifier = 1;
    protected double defenceModifier = 1;
    protected double hpModifier = 1;
    protected boolean strikesFirst;
    protected Position position;

    /**
     * ADHP - attack + defence + hp
     */
    private void recountAdhp() {
        attack = (int)(basicAttack * attackModifier);
        defence = (int)(basicDefence * defenceModifier);
        hp = (int)(basicHp * hpModifier);
    }

    public Item getItemByType(String type) {
        if (weapon.getType().equals(type)) {
            return weapon;
        } else if (armor.getType().equals(type)) {
            return armor;
        } else if (helm.getType().equals(type)) {
            return helm;
        }
        return null;
    }

    public void setItemByType(Item item) {
        if (weapon.getType().equals(type)) {
            weapon = (Weapon) item;
        } else if (armor.getType().equals(type)) {
            armor = (Armor) item;
        } else if (helm.getType().equals(type)) {
            helm = (Helm) item;
        }
    }

    public void unequipItem(Item item) {
        basicAttack -= item.getBasicAttack();
        basicDefence -= item.getBasicDefence();
        basicHp -= item.getBasicHp();

    }

    public void equipItem(Item item) {
        Item oldItem = getItemByType(getType());
        if (oldItem != null) {
            unequipItem(oldItem);
        }

        basicAttack += item.getBasicAttack();
        basicDefence += item.getBasicDefence();
        basicHp += item.getBasicHp();
    }
}
