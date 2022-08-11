package org.example.controller;

import lombok.Data;
import org.example.model.map.Position;

@Data
public class VisibleMapCell {
    String type;
    Position position;

    public VisibleMapCell() {
        type = "Void";
    }
}
