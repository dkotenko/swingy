package org.example.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public abstract class Entity {
    protected int id;
    @Size(min=2, max=255, message = "Invalid name, must be at least 2 symbols, at max - 255")
    protected String name;
    protected int level;
    protected String type;
    protected String iconFileName;
    protected int attack;
    protected int defence;
    protected int hp;
    protected int attackBonusPerLevel;
    protected int defenceBonusPerLevel;
    protected int hpBonusPerLevel;

    public Entity() {
        id = EntityIdGenerator.generateId();
        level = 1;
        iconFileName = this.getClass().getSimpleName();
    }

    public void setLevel(int newLevel) {
        int prevLevel = this.level;
        this.level = newLevel;
        attack -= attackBonusPerLevel * prevLevel;
        attack += attackBonusPerLevel * newLevel;
        defence -= defenceBonusPerLevel * prevLevel;
        defence += defenceBonusPerLevel * newLevel;
        hp -= hpBonusPerLevel * prevLevel;
        hp += hpBonusPerLevel * newLevel;
    }
}
