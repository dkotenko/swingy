package org.example.controller;

import lombok.Data;
import org.example.model.GameModel;
import org.example.model.GameState;
import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.item.Item;
import org.example.model.map.Directions;
import org.example.model.map.GameMapCell;
import org.example.model.map.Position;
import org.example.service.RandomGenerator;
import org.example.service.battle.Battle;
import org.example.service.battle.BattleResult;
import org.example.view.SwingyView;
import org.example.view.ViewTypes;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class GameController {
    private ArrayList<SwingyView> views;
    private SwingyView currView;
    private GameModel gameModel;
    private ApplicationArguments args;
    private double lastBattleProbability;
    private boolean running;
    private boolean uiChanged;


    public GameController(List<SwingyView> views, GameModel gameModel, ApplicationArguments args) {
        this.views = (ArrayList<SwingyView>)views;
        this.gameModel = gameModel;
        this.args = args;
        running = true;
        uiChanged = false;
    }

    public boolean isHeroChosen() {
        return gameModel.getCurrentHero() != null;
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
        gameModel.updateGameState(GameState.START_MENU);
        while (isRunning()) {
            updateViewState();
        }

    }

    public void updateViewState() {
        views.forEach(x -> {
            x.updateGameState(gameModel.getCurrentState())  ;
        } );
    }

    public void propertyChange(PropertyChangeEvent evt) {
        int newLevel = (int)evt.getNewValue();
        //invoke View
    }

    public String createHero(String name, String type) {
        return gameModel.createHero(name, type);
    }

    public void updateHero() {
        gameModel.updateHero();
    }

    public boolean isHeroExists(String name) {
        return gameModel.isHeroExists(name);
    }

    public void deleteHero(HeroDTO heroDTO) {
        gameModel.deleteHeroFromDb(heroDTO);
    }

    public ArrayList<HeroDTO> getHeroes() {
        return gameModel.getHeroes();
    }

    public void updateGameState(GameState state) {
        gameModel.updateGameState(state);

    }

    public void retreatToSafeCell() {
        gameModel.retreatToSafeCell();
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

    public void loadMap() {
        gameModel.loadMap();
    }

    public void startGame() {
        gameModel.loadMap();
        updateGameState(GameState.GAME_MAIN);
    }

    public VisibleMap provideVisibleMap() {

        return gameModel.getGameMap().createVisibleMap();
    }

    public boolean moveNorth() {return moveHero(Directions.NORTH); }
    public boolean moveSouth() {return moveHero(Directions.SOUTH); }
    public boolean moveEast() {return moveHero(Directions.EAST); }
    public boolean moveWest() {return moveHero(Directions.WEST); }

    private boolean moveHero(Directions direction) {
        if (gameModel.moveHero(direction).equals(gameModel.MOVE_EXIT)) {
            updateGameState(GameState.EXIT_MAP_QUESTION);
            return true;
        }
        return false;
    }

    public Hero getCurrHero() {
        return gameModel.getCurrentHero();
    }

    public VisibleMap getVisibleMap() {
        return gameModel.getGameMap().createVisibleMap();
    }

    public boolean isBattle() {
        Position heroPosition = gameModel.getCurrentHero().getPosition();
        GameMapCell activeCell =  gameModel.getGameMap().getCellByPosition(heroPosition);

        if (activeCell.containsMonster() && activeCell.containsHero()) {
            lastBattleProbability = Battle.getHeroChanceToWin(
                    activeCell.getHero(), activeCell.getMonster());
            updateGameState(GameState.BEFORE_BATTLE);
            return true;
        }
        return false;
    }

    public String getCurrentMonsterInfo() {
       return gameModel.getCurrentMonster().toString();
    }

    public ArrayList<String> provideHeroInfo() {
        return new HeroDTO(gameModel.getCurrentHero()).toList();
    }

    public String [][] provideHeroInfoArray() {
        return new HeroDTO(gameModel.getCurrentHero()).toArray();
    }

    public void startBattle() {
        updateGameState(GameState.START_BATTLE);
    }

    public void rollRetreat() {
        updateGameState(GameState.RETREAT);
    }

    public void doRetreat() {
        retreatToSafeCell();
        updateGameState(GameState.GAME_MAIN);
    }

    public void cleanHeroExp() {
        gameModel.getCurrentHero().setCurrExp(0);
    }

    public BattleResult doBattle() {
        BattleResult result = Battle.doBattle(gameModel.getCurrentHero(), gameModel.getCurrentMonster(), false);
        if (result.isHeroWon()) {
            gameModel.deleteCurrentMonster();
        }
        return result;
    }

    public double getRetreatProbability() {
        return RandomGenerator.getRandom().nextDouble() * 100;
    }

    public void equipItem(Item item) {
        gameModel.getCurrentHero().equipItem(item);
    }

    public void killHero() {
        cleanHeroExp();
        updateHero();
        updateGameState(GameState.START_MENU);
    }

    public boolean isRetreatSuccessful() {
        return getRetreatProbability() < 50.;
    }

    public void returnToStartMenu() {
        updateHero();
        updateGameState(GameState.START_MENU);
    }

    public void loadNextMap() {
        loadMap();
        updateHero();
        updateGameState(GameState.GAME_MAIN);
    }


}
