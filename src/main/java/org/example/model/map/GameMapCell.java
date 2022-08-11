package org.example.model.map;

import lombok.Data;
import org.example.model.hero.Hero;
import org.example.model.monster.Monster;

@Data
public class GameMapCell {
    private Position position;
    private Monster monster;
    private boolean center;
    private Hero hero;

    public boolean containsMonster() {
        return monster != null;
    }
    public boolean containsHero() { return hero != null; }

    public void clean() {
        monster = null;
    }

}
