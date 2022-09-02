package org.example.view.console;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class ConsoleMapLegend {
    private ArrayList<String> legend;
    private HashMap<String, String> dict;
    public static int OFFSET = 20;

    public ConsoleMapLegend() {
        dict = new HashMap<>();
        dict.put("Hero", "@");
        dict.put("HeavyBandit", "h");
        dict.put("Mushroom", "m");
        dict.put("Skeleton", "s");
        dict.put("Ground", ".");
        dict.put("Void", "#");
        /*
        dict.put("Warrior", "@");
        dict.put("Rogue", "@");
        dict.put("Mage", "@");
        */

        legend = new ArrayList<>();

        dict.entrySet().forEach(x -> legend.add(
                String.format("%s\t%s", x.getValue(), x.getKey() )));



    }
}
