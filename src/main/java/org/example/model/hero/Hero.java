package org.example.model.hero;

import lombok.Data;
import org.example.controller.GameController;
import org.example.model.GameModel;
import org.example.model.creature.Creature;

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

    public Hero(){
        super();
        nextLevelExp = countLevelExp(2);
        heroId = game.generateHeroId();
        propertyChangeSupport = new PropertyChangeSupport(this);
        //propertyChangeSupport.addPropertyChangeListener(gameController);

    }

    public Hero(String name, String type) {
        this.name = name;
        this.type = type;
    }

    private int countLevelExp(int level) {
        return (level * 1000 + (level - 1) * (level - 1) * 450);
    }

    void gainExp(int exp) {

    }
}
