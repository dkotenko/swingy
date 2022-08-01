package org.example.model.creature;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.controller.RandomGenerator;
import org.example.model.Position;
import org.example.model.entity.Entity;
import org.example.model.item.Armor;
import org.example.model.item.Helm;
import org.example.model.item.Item;
import org.example.model.item.Weapon;

@Data
public abstract class Creature extends Entity {

    protected int attack;
    protected int defence;
    protected int hp;

    protected double attackModifier;
    protected double defenceModifier;
    protected double hpModifier;
    protected Weapon weapon;
    protected Armor armor;
    protected Helm helm;
    protected Position position;

    private RandomGenerator randomGenerator;

    public Creature() {
        super();
    }

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

    public void recalcAttributes()
    {
        attack = (int)(attackModifier * basicAttack);
        defence = (int)(defenceModifier * basicDefence);
        hp = (int)(hpModifier * basicHp);
    }

    public int attack(Creature creature) {
        int damage = (int)(attack * randomGenerator.getRandomCoef()) - creature.getDefence();
        if (damage <= 0) {
            return 0;
        }
        creature.inflictDamage(damage);
        return damage;
    }

    public void inflictDamage(int damage) {
        hp -= damage;
    }
}
