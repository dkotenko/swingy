package org.example.model;
import java.util.Random;

public class Map {
    private long size;
    private long level;
    private boolean[] positions;

    public Map(long level)
    {
        this.level = level;
        size = (level - 1) * 5 + 10 - 1;
        positions = new boolean[(int)size * (int)size];
    }

    public int getEmptyPosition()
    {
        while (true)
        {
            int r = (int)Math.floor(Math.random() * size * size);
            if (positions[r] == false)
                return r;
        }
    }

    public void setPosition(Position p)
    {
        positions[p.getY() * p.getX()] = true;
    }
}
