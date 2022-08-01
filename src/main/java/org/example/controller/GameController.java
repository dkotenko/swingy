package org.example.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.GameModel;
import org.example.model.GameState;
import org.example.view.SwingyView;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class GameController {
    ArrayList<SwingyView> views;
    GameModel gameModel;

    public GameController(List<SwingyView> views, GameModel gameModel) {
        this.views = (ArrayList)views;
        this.gameModel = gameModel;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void startGame() {
        views.forEach(x-> x.setGameController(this));
        updateGameState(GameState.START_MENU);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        int newLevel = (int)evt.getNewValue();
        //invoke View
    }

    public void createNewHero(String name, String type) {

    }

    public void updateGameState(GameState state) {
        gameModel.updateGameState(state);
        int i = 0;
        views.forEach(x -> {
            System.out.println(i);
            x.updateGameState(state)  ;
        } );
    }

    public void returnToParentGameState() {

    }

}
