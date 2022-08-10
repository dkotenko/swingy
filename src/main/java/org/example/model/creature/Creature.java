package org.example.model.creature;

import lombok.Builder;
import lombok.Data;
import org.example.model.entity.Entity;
import org.example.model.item.Item;
import org.example.model.map.Position;
import org.example.service.RandomGenerator;

@Data
@Builder
public abstract class Creature extends Entity {



    protected int basicAttack;
    protected int basicDefence;
    protected int basicHp;

    protected double attackModifier;
    protected double defenceModifier;
    protected double hpModifier;
    protected int weapon;
    protected int armor;
    protected int helm;
    protected Position position;


    public Creature() {
        super();
    }

    public Creature(int basicAttack, int basicDefence, int basicHp) {
        super();
        this.basicAttack = basicAttack;
        this.basicDefence = basicDefence;
        this.basicHp = basicHp;
    }

    /**
     * ADHP - attack + defence + hp
     */

    protected void setupBasics(int _attack, int _defence, int _hp) {
        basicAttack = _attack;
        basicDefence = _defence;
        basicHp = _hp;
        calculateAttributes();
    }

    public void calculateAttributes()
    {
        attack = (int)(attackModifier * (basicAttack + attackBonusPerLevel * level));
        defence = (int)(defenceModifier * (basicDefence + defenceBonusPerLevel * level));
        hp = (int)(hpModifier * basicHp + (basicHp + hpBonusPerLevel * level));
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
        calculateAttributes();
    }

    /*
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
    */

    public void unequipItem(Item item) {
        basicAttack -= item.getAttack();
        basicDefence -= item.getDefence();
        basicHp -= item.getHp();

    }

    /*
    public void equipItem(Item item) {
        //Item oldItem = getItemByType(getType());
        if (oldItem != null) {
            unequipItem(oldItem);
        }

        basicAttack += item.getBasicAttack();
        basicDefence += item.getBasicDefence();
        basicHp += item.getBasicHp();
    }
    */

    public int attack(Creature creature) {
        int damage = (int)(attack * RandomGenerator.getRandomCoef()) - creature.getDefence();
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
