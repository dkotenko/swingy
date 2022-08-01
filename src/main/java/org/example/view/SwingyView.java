package org.example.view;

import org.example.controller.GameController;
import org.example.model.GameState;

public interface SwingyView {
    public void showStartMenu();
    public void setGameController(GameController gameController);
    public void updateGameState(GameState gameState);
    //public void showHeroStats();
    //public void showMap();
    //public void createHero();
    //public void selectHero();
    //public void showExit();
}
