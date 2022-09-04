package org.example.model.hero;

import lombok.Data;
import lombok.ToString;
import org.example.controller.GameController;
import org.example.model.GameModel;
import org.example.model.creature.Creature;
import org.example.model.hero.dto.HeroDTO;

import java.beans.PropertyChangeSupport;

@Data
@ToString(callSuper = true)
public abstract class Hero extends Creature {
    protected String type;
    protected int currExp;
    protected int nextLevelExp;
    private int heroId;
    GameModel game;
    GameController gameController;
    PropertyChangeSupport propertyChangeSupport;

    public Hero(String name){
        super(1, 1, 10);
        this.name = name;
        nextLevelExp = countLevelExp(level);
        propertyChangeSupport = new PropertyChangeSupport(this);
        updateAttributes();
    }

    public Hero(HeroDTO heroDTO) {
        super(1, 1, 10);
        setLevel(heroDTO.getLevel());
        id = heroDTO.getId();
        name = heroDTO.getName();
        type = heroDTO.getType();
        currExp =  heroDTO.getExp();
        helm = heroDTO.getHelm();
        armor = heroDTO.getArmor();
        weapon = heroDTO.getWeapon();
        updateAttributes();
    }



    public static int countLevelExp(int level) {
        return (level * 1000 + (level - 1) * (level - 1) * 450);
    }



    public Hero(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
        nextLevelExp = countLevelExp(level);
    }

    public void gainExp(int exp) {
        currExp += exp;
        while (currExp >= nextLevelExp) {
            level++;
            currExp -= nextLevelExp;
            nextLevelExp = countLevelExp(level);
        }
    }



}
