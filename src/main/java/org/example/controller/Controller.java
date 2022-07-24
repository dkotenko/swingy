package org.example.controller;

import org.example.model.GameModel;
import org.example.view.SwingyView;

public class Controller {
    SwingyView swingyView;
    GameModel gameModel;

    public Controller(SwingyView swingyView, GameModel gameModel)
    {
        this.swingyView = swingyView;
        this.gameModel = gameModel;
    }
}
