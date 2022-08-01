package org.example.view;

import lombok.Setter;
import org.example.ProfileTypes;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Profile(ProfileTypes.CONSOLE)
public class ConsoleReader {
    @Setter
    ConsoleView consoleView;
    Scanner scanner;

    int intVal;

    public void readLine() {
        scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if ("exit".equals(line)) {
            System.exit(0);
        } else if ("r".equals(line) || "return".equals(line)) {
            consoleView.gameController.
            ;
        }
    }

    public void readInt() {



    }
}
