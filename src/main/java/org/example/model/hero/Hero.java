package org.example.model.hero;

import lombok.Data;
import org.example.controller.GameController;
import org.example.model.GameModel;
import org.example.model.creature.Creature;
import org.example.model.hero.dto.HeroDTO;

import java.beans.PropertyChangeSupport;

@Data
public abstract class Hero extends Creature {
    protected String type;
    protected int currExp;
    protected int nextLevelExp;
    protected int attackLevelUpBonus;
    protected int defenceLevelUpBonus;
    protected int hpLevelUpBonus;
    private int heroId;
    GameModel game;
    GameController gameController;
    PropertyChangeSupport propertyChangeSupport;

    public Hero(String name){
        super();
        this.name = name;
        nextLevelExp = countLevelExp(2);
        propertyChangeSupport = new PropertyChangeSupport(this);
        //propertyChangeSupport.addPropertyChangeListener(gameController);

    }

    public Hero(HeroDTO heroDTO) {
        super();
        setLevel(heroDTO.getLevel());
        currExp =  heroDTO.getExp();
        helm = heroDTO.getHelm();
        armor = heroDTO.getArmor();
        weapon = heroDTO.getWeapon();
    }

    public Hero(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        nextLevelExp = countLevelExp(level);
    }

    private int countLevelExp(int level) {

        return (level * 1000 + (level - 1) * (level - 1) * 450);
    }

    void gainExp(int exp) {

    }


}
