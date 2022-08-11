package org.example.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.example.controller.GameController;
import org.example.model.GameState;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.hero.Hero;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;

@Service
@Getter
@Setter
public class ConsoleView implements SwingyView {
    GameController gameController;
    private GuiView.ShowAction[] showActions;
    private GameState gameState;
    private boolean active;
    private String viewType;


    interface ShowAction {
        void show();
    }

    public ConsoleView() {
        viewType = ViewTypes.CONSOLE;
        initActionsArray();
    }

    private void initActionsArray() {
        showActions = new GuiView.ShowAction[GameState.GAME_STATE_NUM.ordinal()];
        showActions[GameState.START_MENU.ordinal()] = () -> showStartMenu();
        //showActions[GameState.CREATE_HERO.ordinal()] = () -> showNewHero();
        //showActions[GameState.CHOOSE_HERO.ordinal()] = () -> showChooseHero();
    }

    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if ("exit".equals(line)) {
                System.exit(0);
            } else if ("return".equals(line)) {
                gameController.returnToParentGameState();
            } else if (StringUtils.isBlank(line)) {
                continue;
            }
            return line;
        }
    }

    public int readInt() {
        while (true) {
            String s = readLine();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.err.println(String.format("invalid number \'%s\'. Please try again", s));
                continue;
            }
        }
    }

    public void updateGameState(GameState gameState) {
        if (gameState != this.gameState) {
            this.gameState = gameState;
            if (isActive()) {
                showActions[gameState.ordinal()].show();
            }
        }
    }

    // TODO Validate hero name
    private void createHero() {
        String name = "";
        int n = 0;
        while (true) {
            System.out.println("Enter new hero name:");
            name = readLine();
            if (gameController.isHeroExists(name)) {
                System.out.println(String.format(
                        "Hero with name %s already exists. Please choose another one",
                        name));
                continue;
            }
            break;
        }

        String type = "";
        while (n < 1 || n > 3) {
            System.out.println("Choose new hero type:");
            System.out.println("1. Warrior");
            System.out.println("2. Rogue");
            System.out.println("3. Mage");
            n = readInt();
            if (n == 1 || n == 2 || n == 3) {
                break ;
            }
            System.out.println(String.format("Invalid type \'%d\', please try again\n", n));
        }
        String [] types = new String[]{"", "Warrior", "Rogue", "Mage"};
        gameController.createHero(name, types[n]);
        if (gameController.isHeroExists(name)) {
            System.out.println(String.format("New hero created: %s, the %s\n", name, types[n]));
        }
    }

    private void chooseHero() {
        ArrayList<HeroDTO> heroes = gameController.getHeroes();

        int choice = 0;
        if (heroes.size() == 0) {
            System.out.println("Heroes hasn't been created yet. Return and create a new one");
            return;
        }

        while (choice < 1 || choice > heroes.size()) {
            System.out.println("List of existing heroes:");
            int i = 0;
            while (i < heroes.size()) {
                System.out.println(String.format("%d) %s", i + 1, heroes.get(i).toString()));
                i++;
            }
            System.out.println(String.format("\n%d. return to previous menu", i + 1));
            choice = readInt();
            if (choice < 1 || choice > heroes.size()) {
                if (choice == heroes.size() + 1) {
                    return;
                }
                System.out.println(String.format(
                        "Invalid hero number, please input value in range [1:%d]",
                        heroes.size()));
            } else {
                break ;
            }
        }
        HeroDTO heroDTO = heroes.get(choice - 1);
        System.out.println(String.format("The game for %s (%s) is starting now",
                heroDTO.getName(),
                heroDTO.getType()));
        gameController.chooseHero(heroDTO);
        gameController.startGame();
    }

    public void showStartMenu() {
        System.out.println(Messages.START_LOGO);
        System.out.println(Messages.START_MESSAGE);
        System.out.println(Messages.START_CREATE_OR_CHOOSE);
        while (true) {
            System.out.println("\nAvailable actions:");
            System.out.println("1. " + Messages.START_CREATE_HERO);
            System.out.println("2. " + Messages.START_CHOOSE_HERO);

            int n = readInt();
            if (n == 1) {
                createHero();
            } else if (n == 2) {
                chooseHero();
            } else {
                System.out.println("Invalid action, please try again");
            }
        }
    }

    public void showStatus() {

    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
