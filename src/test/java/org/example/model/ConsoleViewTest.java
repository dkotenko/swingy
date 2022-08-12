package org.example.model;

import org.example.model.hero.Warrior;
import org.example.model.map.GameMap;
import org.example.view.console.ConsoleMap;
import org.junit.jupiter.api.Test;


public class ConsoleViewTest {

    @Test
    public void console_map_check() {
        Warrior warrior =  new Warrior("test");
        GameMap gameMap = new GameMap(warrior);
        new ConsoleMap(gameMap.createVisibleMap()).print();
    }
}
