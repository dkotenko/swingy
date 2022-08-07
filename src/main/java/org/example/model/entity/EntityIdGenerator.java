package org.example.model.entity;

public class EntityIdGenerator {
    private static int currId = 0;

    public static int generateId() {
        return currId++;
    }
}
