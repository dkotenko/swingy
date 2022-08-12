package org.example.controller;

import lombok.Data;

@Data
public class VisibleMap {
    private VisibleMapCell[][] cells;
    private int visibleSize;

    public VisibleMap(int size) {
        visibleSize = size;
        cells = new VisibleMapCell[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                cells[i][j] = new VisibleMapCell();
            }
        }
    }
}
