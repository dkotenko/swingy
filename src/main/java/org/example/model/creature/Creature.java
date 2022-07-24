package org.example.model.creature;

import lombok.Data;
import org.example.model.Position;
import org.example.model.entity.Entity;

@Data
public abstract class Creature extends Entity {
    protected int attack = 0;
    protected int defence = 0;
    protected int hitPoints = 0;
    protected int Weapon = 0;
    protected int Armor = 0;
    protected int Helm = 0;
    protected Position position;
}
