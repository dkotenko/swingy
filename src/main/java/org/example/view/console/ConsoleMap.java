package org.example.view.console;

import org.example.controller.VisibleMap;
import org.example.controller.VisibleMapCell;

import java.util.ArrayList;

public class ConsoleMap {
    VisibleMap visibleMap;
    ArrayList<String> map;
    ConsoleMapLegend consoleMapLegend;
    ArrayList<String> legend;

    public ConsoleMap(VisibleMap visibleMap) {
        map = new ArrayList<>();
        consoleMapLegend = new ConsoleMapLegend();
        legend = consoleMapLegend.getLegend();
        this.visibleMap = visibleMap;
    }

    public void print() {

        VisibleMapCell startCell = visibleMap.getCells()[1][1];
        int startY = startCell.getPosition().getY();
        int startX = startCell.getPosition().getX();
        int endY = startY + visibleMap.getVisibleSize();
        int endX = startX + visibleMap.getVisibleSize();
        int legendRowsCounter = 0;

        //construct header
        System.out.print(String.format("%4s", " "));
        for (int i = startX; i < endX; i++) {
            System.out.print(String.format("%4d", i));
        }
        if (legendRowsCounter < legend.size()) {
            System.out.print(String.format("%" + ConsoleMapLegend.OFFSET + "s", legend.get(legendRowsCounter++)));
        }
        System.out.println();

        for (int i = startY; i < endY; i++) {
            System.out.print(String.format("%4d", visibleMap.getCells()[i][1].getPosition().getY()));
            for (int j = startX; j < endX; j++) {
                VisibleMapCell currCell = visibleMap.getCells()[i][j];
                System.out.print(String.format("%4s",  consoleMapLegend.getDict().get(currCell.getType())));
                //System.out.print(String.format("%4d", visibleMap.getCells()[i][1].getPosition().getY()));
            }
            if (legendRowsCounter < legend.size()) {
                System.out.print(String.format("\t\t%-" + ConsoleMapLegend.OFFSET + "s", legend.get(legendRowsCounter++)));
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
