package org.example.model.map;

import lombok.Data;
import org.example.model.monster.Monster;

@Data
public class GameMapCell {
    private Position position;
    private Monster monster;
    private boolean center;

    public boolean isEmpty() {
        return monster == null;
    }

    public void clean() {
        monster = null;
    }

}
