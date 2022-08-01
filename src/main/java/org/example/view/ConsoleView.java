package org.example.view;

import lombok.Setter;
import org.example.ProfileTypes;
import org.example.controller.GameController;
import org.example.model.GameState;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Setter
@Profile(ProfileTypes.CONSOLE)
public class ConsoleView implements SwingyView {
    GameController gameController;
    private GuiView.ShowAction[] showActions;
    private GameState gameState;
    private ConsoleReader consoleReader;

    interface ShowAction {
        void show();
    }

    public ConsoleView(ConsoleReader consoleReader) {
        initActionsArray();
        consoleReader.setConsoleView(this);
    }

    private void initActionsArray() {
        showActions = new GuiView.ShowAction[GameState.GAME_STATE_NUM.ordinal()];
        showActions[GameState.START_MENU.ordinal()] = () -> showStartMenu();
        //showActions[GameState.CREATE_HERO.ordinal()] = () -> showNewHero();
        //showActions[GameState.CHOOSE_HERO.ordinal()] = () -> showChooseHero();
    }


    @Async
    public void updateGameState(GameState gameState) {
        if (gameState != this.gameState) {
            showActions[gameState.ordinal()].show();
        }
    }

    // TODO Validate hero name
    private void createNewHero() {
        System.out.println("Enter new hero name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int n = 0;
        while (n < 1 || n > 3) {
            System.out.println("Choose new hero type:");
            System.out.println("1. Warrior");
            System.out.println("2. Rogue");
            System.out.println("3. Mage");
            String type = scanner.nextLine();
            n = Integer.parseInt(type);
            if (n == 1 || n == 2 || n == 3) {
                break ;
            }
            System.out.println("Invalid type, please try again");
        }
        gameController.createNewHero(name, new String[]{"", "Warrior", "Rogue", "Mage"}[n]);
    }

    private void chooseHero() {

    }

    @Async
    public void showStartMenu() {
        System.out.println(Messages.START_LOGO);
        System.out.println(Messages.START_MESSAGE);
        System.out.println(Messages.START_CREATE_OR_CHOOSE);
        while (true) {
            System.out.println("Available actions:");
            System.out.println("1. Create a hero");
            System.out.println("2. Choose hero");

            int n = consoleReader. Integer.parseInt(line);
            if (n == 1) {
                createNewHero();
                break ;
            } else if (n == 2) {
                chooseHero();
                break ;
            } else {
                System.out.println("Invalid action, please try again");
            }
        }
        System.out.println(Messages.CHOOSE_HERO_MESSAGE);
    }

    public void showStatus() {

    }
}
