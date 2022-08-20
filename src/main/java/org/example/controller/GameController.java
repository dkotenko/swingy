package org.example.controller;

import lombok.Data;
import org.example.model.GameModel;
import org.example.model.GameState;
import org.example.model.hero.HeroFactory;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.map.Directions;
import org.example.model.map.GameMapCell;
import org.example.model.map.Position;
import org.example.service.RandomGenerator;
import org.example.view.SwingyView;
import org.example.view.ViewTypes;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

@Component
@Data
public class GameController {
    private ArrayList<SwingyView> views;
    private SwingyView currView;
    private GameModel gameModel;
    private ApplicationArguments args;
    private double lastBattleProbability;


    public GameController(List<SwingyView> views, GameModel gameModel, ApplicationArguments args) {
        this.views = (ArrayList<SwingyView>)views;
        this.gameModel = gameModel;
        this.args = args;
    }

    private SwingyView getInitialCurrViev() {
        SwingyView guiView = null;
        SwingyView consoleView = null;

        for (SwingyView view: views) {
            if (ViewTypes.GUI.equals(view.getViewType())) {
                guiView = view;
            }
            if (ViewTypes.CONSOLE.equals(view.getViewType())) {
                consoleView = view;
            }
        }

        if (args.getSourceArgs().length > 0 &&
                "console".equals(args.getSourceArgs()[0])) {
            currView = consoleView;
        } else {
            currView = guiView;
        }
        return currView;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void initGame() {
        views.forEach(x-> x.setGameController(this));
        currView = getInitialCurrViev();
        currView.activate();
        updateGameState(GameState.START_MENU);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        int newLevel = (int)evt.getNewValue();
        //invoke View
    }

    public void createHero(String name, String type) {
        gameModel.createHero(name, type);
    }

    public boolean isHeroExists(String name) {
        return gameModel.isHeroExists(name);
    }

    public ArrayList<HeroDTO> getHeroes() {
        return gameModel.getHeroes();
    }

    public void updateGameState(GameState state) {
        gameModel.updateGameState(state);
        views.forEach(x -> {
            x.updateGameState(state)  ;
        } );
    }

    public void returnToParentGameState() {
        updateGameState(gameModel.getPreviousState());
    }

    public void changeView(SwingyView oldView) {
        oldView.deactivate();
        for (SwingyView view: views) {
            if (!view.equals(oldView)) {
                view.activate();
                break ;
            }
        }
    }

    public void chooseHero(HeroDTO heroDTO) {
        gameModel.setCurrentHero(new HeroFactory().create(heroDTO));
    }

    public void startGame() {
        gameModel.startGame();
        updateGameState(GameState.GAME_MAIN);
    }

    public VisibleMap provideVisibleMap() {

        return gameModel.getGameMap().createVisibleMap();
    }

    public void moveNorth() { gameModel.moveHero(Directions.NORTH); }
    public void moveSouth() { gameModel.moveHero(Directions.SOUTH); }
    public void moveEast() { gameModel.moveHero(Directions.EAST); }
    public void moveWest() { gameModel.moveHero(Directions.WEST); }


    public void checkBattle() {
        Position heroPosition = gameModel.getCurrentHero().getPosition();
        GameMapCell activeCell =  gameModel.getGameMap().getCellByPosition(heroPosition);

        if (activeCell.containsMonster() && activeCell.containsHero()) {
            lastBattleProbability = Battle.getHeroChanceToWin(
                    activeCell.getHero(), activeCell.getMonster());
            updateGameState(GameState.BEFORE_BATTLE);
        }
    }

    public String getCurrentMonsterName() {
       return gameModel.getCurrentMonster().getName();
    }

    public ArrayList<String> provideHeroInfo() {
        return new HeroDTO(gameModel.getCurrentHero()).toList();
    }

    public void startBattle() {
        updateGameState(GameState.START_BATTLE);
    }

    public void doRetreat() {
        updateGameState(GameState.RETREAT);
    }

    public double getRetreatProbability() {
        return RandomGenerator.getRandom().nextDouble() * 100;
    }
}
