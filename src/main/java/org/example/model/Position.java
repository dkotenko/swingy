package org.example.model;

public class Position {
    private int x;
    private int y;

    private Position(int x, int y){
        this.x = x;
        this.y = y;
    };
    private Position(){};
    private Position(Position p){
        int x = p.x;
        int y = p.y;
    };

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
