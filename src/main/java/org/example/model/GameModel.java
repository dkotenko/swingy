package org.example.model;

import lombok.Data;
import org.example.model.hero.Hero;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
public class GameModel {
    Map map;
    ArrayList<Hero> heroList;
    Hero currentHero;
    GameState currentState;
    GameState previousState;

    public GameModel() {
        currentState = GameState.START_MENU;
        previousState = GameState.START_MENU;
    }

    public void updateGameState(GameState state) {
        previousState = currentState;
        currentState = state;
    }

    //TODO select last id from db, return id + 1
    //FIXME db generates id, when?
    public int generateHeroId() {

        return 0;
    }
}
