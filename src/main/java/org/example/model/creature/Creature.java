package org.example.model.creature;

import lombok.Data;
import lombok.ToString;
import org.example.model.entity.Entity;
import org.example.model.item.Item;
import org.example.model.item.ItemTypes;
import org.example.model.map.Position;
import org.example.service.RandomGenerator;

@Data
@ToString(callSuper = true)
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
    protected Position prevPosition;
    protected int hpToRestore;


    public Creature() {
        super();
    }

    public Creature(int basicAttack, int basicDefence, int basicHp) {
        super();
        this.basicAttack = basicAttack;
        this.basicDefence = basicDefence;
        this.basicHp = basicHp;
    }

    public void updateAttributes()
    {
        attack = (int)(attackModifier * (basicAttack + attackBonusPerLevel * level + weapon));
        defence = (int)(defenceModifier * (basicDefence + defenceBonusPerLevel * level + armor));
        hp = (int)(hpModifier * (basicHp + hpBonusPerLevel * level + helm));
        hpToRestore = hp;
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
        updateAttributes();
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

    public void restoreHp() {
        hp = hpToRestore;
    }

    public void equipItem(Item item) {
        if (item.getType().equals(ItemTypes.ARMOR)) {
            armor = item.getLevel();
        } else if (item.getType().equals(ItemTypes.WEAPON)) {
            weapon = item.getLevel();
        } else if (item.getType().equals(ItemTypes.HELM)) {
            helm = item.getLevel();
        }
        updateAttributes();
    }

    /*
    public void unequipItem(Item item) {
        basicAttack -= item.getAttack();
        basicDefence -= item.getDefence();
        basicHp -= item.getHp();
    }




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

    public String statsToString() {
        return String.format("(attack=%d, defence=%d, hp=%d)", attack, defence, hp);
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
