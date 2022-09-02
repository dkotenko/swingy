package org.example.controller;

import lombok.Data;

@Data
public class VisibleMap {
    private VisibleMapCell[][] cells;
    private int visibleSize;
    private int trueSize;
    private int level;

    public VisibleMap(int size, int trueSize, int level) {
        visibleSize = size;
        this.trueSize = trueSize;
        this.level = level;
        cells = new VisibleMapCell[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                cells[i][j] = new VisibleMapCell();
            }
        }
    }
}
