package org.example.model.entity;

import org.springframework.stereotype.Component;

@Component
public class EntityIdGenerator {
    int currId = 0;

    public int generateId() {
        return currId++;
    }
}
