package org.example.controller;

import lombok.Data;

@Data
public class VisibleMap {
    private VisibleMapCell[][] cells;
    private int visibleSize;

    public VisibleMap(int size) {
        visibleSize = size;
        cells = new VisibleMapCell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new VisibleMapCell();
            }
        }
    }
}
