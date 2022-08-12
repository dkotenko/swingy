package org.example.model.map;

import lombok.Data;

@Data
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    };

    public Position(){};
    public Position(Position p){
        int x = p.x;
        int y = p.y;
    };

    public void increaseX() { x++; }
    public void decreaseX() { x--; }
    public void increaseY() { y++; }
    public void decreaseY() { y--; }
}
