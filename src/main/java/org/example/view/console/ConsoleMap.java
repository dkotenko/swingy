package org.example.view.console;

import org.example.controller.VisibleMap;
import org.example.controller.VisibleMapCell;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ConsoleMap {
    private VisibleMap visibleMap;
    private ArrayList<String> map;
    private ConsoleMapLegend consoleMapLegend;
    private ArrayList<String> legend;
    private ArrayList<String> heroInfo;

    public ConsoleMap(VisibleMap visibleMap, ArrayList<String> heroInfo) {
        map = new ArrayList<>();
        consoleMapLegend = new ConsoleMapLegend();
        legend = consoleMapLegend.getLegend();
        this.visibleMap = visibleMap;
        this.heroInfo = heroInfo;
    }

    public void print(ArrayList<String> heroInfo) {
        this.heroInfo = heroInfo;
        print();
    }

    public void print() {

        VisibleMapCell startCell = visibleMap.getCells()[1][1];
        int startY = startCell.getPosition().getY();
        int startX = startCell.getPosition().getX();
        int endY = startY + visibleMap.getVisibleSize();
        int endX = startX + visibleMap.getVisibleSize();
        int legendRowsCounter = 0;
        int heroInfoRowsCounter = 0;

        //construct header
        System.out.print(String.format("%4s", " "));
        for (int i = startX; i < endX; i++) {
            System.out.print(String.format("%4d", i));
        }
        if (legendRowsCounter < legend.size()) {
            System.out.print(String.format("%" + ConsoleMapLegend.OFFSET + "s", "Map legend:"));
        }
        if (heroInfo != null && heroInfoRowsCounter < heroInfo.size()) {
            System.out.println(String.format("%" + (ConsoleMapLegend.OFFSET) + "s", "\t\tHero info:"));
        }


        for (int i = 1; i <= visibleMap.getVisibleSize(); i++) {
            System.out.print(String.format("%4d", visibleMap.getCells()[i][1].getPosition().getY()));
            for (int j = 1; j <= visibleMap.getVisibleSize(); j++) {
                VisibleMapCell currCell = visibleMap.getCells()[i][j];
                System.out.print(String.format("%4s",  consoleMapLegend.getDict().get(currCell.getType())));
                //System.out.print(String.format("%4d", visibleMap.getCells()[i][1].getPosition().getY()));
            }
            if (legendRowsCounter < legend.size()) {
                System.out.print(String.format("\t\t%-" + ConsoleMapLegend.OFFSET + "s", legend.get(legendRowsCounter++)));
            }
            if (heroInfo != null && heroInfoRowsCounter < heroInfo.size()) {
                int offset = legendRowsCounter == heroInfoRowsCounter + 1 ? ConsoleMapLegend.OFFSET : (ConsoleMapLegend.OFFSET * 2 + 12);

                System.out.print(String.format("%" + offset + "s", heroInfo.get(heroInfoRowsCounter++)));

            }
            System.out.println();
        }
        //print
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.toString();
    }
}
