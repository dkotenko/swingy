package org.example;

public class Handlers {
    public static int getRandom(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static int getRandom(int max)
    {
        return (int) (Math.random() * ++max);
    }
}
