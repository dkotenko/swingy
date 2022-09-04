package org.example.view.console;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.example.controller.GameController;
import org.example.controller.VisibleMap;
import org.example.model.GameState;
import org.example.model.hero.dto.HeroDTO;
import org.example.service.battle.BattleResult;
import org.example.view.Messages;
import org.example.view.SwingyView;
import org.example.view.ViewTypes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;

@Service
@Getter
@Setter
public class ConsoleView implements SwingyView {

    private interface ShowAction {
        void show();
    }

    GameController gameController;
    private ShowAction[] showActions;
    private GameState gameState;
    private boolean active;
    private String viewType;
    private VisibleMap map;

    public ConsoleView() {
        viewType = ViewTypes.CONSOLE;
        initActionsArray();
    }

    private void initActionsArray() {
        showActions = new ShowAction[GameState.GAME_STATE_NUM.ordinal()];
        showActions[GameState.START_MENU.ordinal()] = this::showStartMenu;
        showActions[GameState.GAME_MAIN.ordinal()] = this::showGameMain;
        showActions[GameState.BEFORE_BATTLE.ordinal()] = this::showBeforeBattle;
        showActions[GameState.EXIT_MAP_QUESTION.ordinal()] = this::showExitMapQuestion;
        showActions[GameState.START_BATTLE.ordinal()] = this::showBattle;
        showActions[GameState.RETREAT.ordinal()] = this::showRetreat;
        //showActions[GameState.CHOOSE_HERO.ordinal()] = () -> showChooseHero();
    }

    public void showBattle() {
        BattleResult result = gameController.doBattle();
        System.out.println(result.toString());
        if (result.isHeroWon() == false) {
            System.out.println(Messages.HERO_DIED);
            gameController.killHero();
        } else if (result.hasRewards()) {
            result.getRewards().forEach(item -> {

                System.out.println(item.toString());

                while (true) {
                    System.out.println("1. " + Messages.REWARD_PICK);
                    System.out.println("2. " + Messages.REWARD_LEAVE);

                    String choice = readLine();
                    if ("1".equals(choice)) {
                        gameController.equipItem(item);
                        System.out.println("Item equipped!");
                        break ;
                    } else if ("2".equals(choice)) {
                        break ;
                    }
                    System.out.println(Messages.INVALID_INPUT);
                }
            });
            gameController.continueGame();
        }
    }

    public void showRetreat() {
        if (gameController.isRetreatSuccessful()) {
            System.out.println(Messages.RETREAT_SUCCESSFULLY);
            gameController.doRetreat();
        } else {
            System.out.println(Messages.RETREAT_FAILURE);
            gameController.startBattle();
        }
    }

    public void showBeforeBattle() {
        System.out.println(String.format(
                "You`ve met a monster - %s (chance to win - %.2f%%)",
                gameController.getCurrentMonsterInfo(),
                gameController.getLastBattleProbability() * 100
                ));

        while (true) {
            System.out.println("You could fight him or retreat with 50% chance. What will you choose?");
            System.out.println("1. Attack the monster!");
            System.out.println("2. Retreat (chance - 50%)");

            String choice = readLine();
            if ("1".equals(choice)) {
                //start battle
                gameController.startBattle();
                break;
            } else if ("2".equals(choice)) {
                // doRetreat;
                gameController.rollRetreat();
                break;
            } else if ("change".equals(choice)) {
                return;
            } else {
                System.out.println("Invalid action, please try again");
            }
        }
    }

    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        String line = null;
        while (true) {
            line = scanner.nextLine();
            if ("exit".equals(line)) {
                System.exit(0);
            /*
            } else if ("return".equals(line)) {
                gameController.returnToParentGameState();

             */
            } else if ("change".equals(line)) {
                gameController.changeView(this);
                break ;
            } else if (StringUtils.isBlank(line)) {
                continue;
            }
            break ;
        }
        return line;
    }

    public void updateGameState(GameState gameState) {
        this.gameState = gameState;
        if (isActive()) {
            showActions[gameState.ordinal()].show();
        }
    }

    private void createHero() {
        String name = "";
        int n = 0;
        while (true) {
            System.out.println("Enter new hero name:");
            name = readLine();
            if (gameController.isHeroExists(name)) {
                System.out.println(String.format(Messages.CREATE_HERO_ALREADY_EXISTS,
                        name));
                continue;
            }
            break;
        }

        String choice = "";
        while (n < 1 || n > 3) {
            System.out.println("Choose new hero type:");
            System.out.println("1. Warrior");
            System.out.println("2. Rogue");
            System.out.println("3. Mage");
            choice = readLine();
            if ("1".equals(choice) || "2".equals(choice) || "3".equals(choice)) {
                break ;
            }
            System.out.println(String.format("Invalid type \'%d\', please try again\n", n));
        }

        n = readInt(choice);
        String [] types = new String[]{"", "Warrior", "Rogue", "Mage"};
        String error = gameController.createHero(name, types[n]);
        if (StringUtils.isNotBlank(error)) {
            System.out.println(error);
        }
        else if (gameController.isHeroExists(name)) {
            System.out.println(String.format("New hero created: %s, the %s\n", name, types[n]));
        }
    }

    private void deleteHero() {
        ArrayList<HeroDTO> heroes = gameController.getHeroes();
        int choice = 0;
        int n = 0;
        while (choice < 1 || choice > heroes.size()) {
            System.out.println("Choose hero to delete:");

            for (int i = 0; i < heroes.size(); i++) {
                System.out.println(String.format("%d) %s", i + 1, heroes.get(i).toString()));
            }

            String line = readLine();
            n = readInt(line);
            if (n < 1 || n > heroes.size()) {
                System.out.println(String.format(
                        "Invalid hero number, please input value in range [1:%d]\n\n",
                        heroes.size()));
            } else {
                break ;
            }
        }
        gameController.deleteHero(heroes.get(n - 1));
        System.out.println("Hero deleted!\n\n");
    }
    
    public int readInt(String line) {
        int n = 0;
        try {
            n = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            n = -1;
        }
        return n;
    }

    private boolean chooseHero() {
        ArrayList<HeroDTO> heroes = gameController.getHeroes();
        int choice = 0;
        int n = 0;
        boolean isChosen = false;
        while (choice < 1 || choice > heroes.size()) {
            int i = 0;

            if (heroes.size() == 0) {
                System.out.println("Heroes hasn't been created yet. Create a new one");
            } else {
                System.out.println(Messages.CHOOSE_HERO_MESSAGE + " or choose an action:");
                while (i < heroes.size()) {
                    System.out.println(String.format("%d. %s", i + 1, heroes.get(i).toString()));
                    i++;
                }

            }
            System.out.println(String.format("\n%d. %s", i++ + 1, Messages.CREATE_HERO_MESSAGE));
            if (heroes.size() > 0) {
                System.out.println(String.format("%d. %s", i++ + 1, Messages.CHOOSE_HERO_DELETE));
            }
            System.out.println(String.format("%d. %s", i + 1, Messages.RETURN_PREV));

            String line = readLine();
            if ("change".equals(choice)) {
                return isChosen;
            }

            n = readInt(line);
            if (n < 1 || n > heroes.size()) {
                if (n == heroes.size() + 1) {
                    createHero();
                    heroes = gameController.getHeroes();
                } else if (n == heroes.size() + 2 && heroes.size() > 0) {
                    deleteHero();
                    heroes = gameController.getHeroes();
                } else if (n == heroes.size() + 2 || (n == heroes.size() + 3 && heroes.size() > 0)) {
                    return isChosen;
                } else {
                    System.out.println(String.format(
                            "Invalid hero number, please input value in range [1:%d]\n\n",
                            heroes.size() + 2));
                }

            } else {
                break ;
            }
        }
        HeroDTO heroDTO = heroes.get(n - 1);
        System.out.println(String.format("The game for %s (%s) is starting now",
                heroDTO.getName(),
                heroDTO.getType()));
        gameController.chooseHero(heroDTO);
        gameController.startGame();
        return true;
    }

    public void showStartMenu() {
        System.out.println(Messages.START_LOGO);
        System.out.println(Messages.START_WELCOME_MESSAGE);
        while (true) {
            System.out.println("\nAvailable actions:");
            System.out.println("1. " + Messages.START_START_GAME);
            System.out.println("2. " + Messages.START_QUIT + "\n");

            String choice = readLine();
            System.out.println("");
            if ("change".equals(choice)) {
                return;
            }

            int n = readInt(choice);
            if (n == 1) {
                if (chooseHero()) {
                    break;
                }
            } else if (n == 2) {
                System.exit(0);
            }
            else {
                System.out.println("Invalid action, please try again");
            }
        }
    }

    private void printMap() {
        new ConsoleMap(gameController.provideVisibleMap(),
                gameController.provideHeroInfo()).print();
    }

    public void showGameMain() {
        boolean isMoveExit = false;

        while (true) {
            new ConsoleMap(gameController.provideVisibleMap(),
                    gameController.provideHeroInfo()).print();
            if (gameController.isBattle()) {
                return;
            }

            System.out.println("\nAvailable actions:");
            System.out.println("1. " + Messages.GO_NORTH);
            System.out.println("2. " + Messages.GO_EAST);
            System.out.println("3. " + Messages.GO_SOUTH);
            System.out.println("4. " + Messages.GO_WEST);
            System.out.println("\n5. " + Messages.RETURN_PREV
            );

            String choice = readLine();
            if ("change".equals(choice)) {
                return;
            }
            int n = readInt(choice);
            if (n == 1) {
                isMoveExit = gameController.moveNorth();
            } else if (n == 2) {
                isMoveExit = gameController.moveEast();
            } else if (n == 3) {
                isMoveExit = gameController.moveSouth();
            } else if (n == 4) {
                isMoveExit = gameController.moveWest();
            } else if (n == 5) {
                gameController.updateHero();
                updateGameState(GameState.START_MENU);
                return;
            } else {
                System.out.println("Invalid action, please try again");
            }
            if (isMoveExit) {
                return;
            }
        }


    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
        gameController.setUiChanged(true);
    }

    public void showExitMapQuestion() {
        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.println(Messages.EXIT_MAP_QUESTION);
            System.out.println("1. " + Messages.EXIT_MAP_OPTIONS_GO_NEXT);
            System.out.println("2. " + Messages.EXIT_MAP_OPTIONS_EXIT);

            String line = readLine();
            if ("change".equals(line)) {
                return;
            }
            choice = readInt(line);
            if (choice != 1 && choice != 2) {
                System.err.println("Invalid input, please try again");
            }
        }
        if (choice == 1) {
            gameController.loadNextMap();
        } else {
            gameController.returnToStartMenu();
        }

    }
}
