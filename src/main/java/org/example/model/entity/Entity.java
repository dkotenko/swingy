package org.example.model.entity;

import lombok.Data;

@Data
public abstract class Entity {
    protected int id;
    protected String name;
    protected String typeName;
    protected int level;
    protected String type;
    EntityIdGenerator idGenerator;

    public Entity() {
        id = idGenerator.generateId();
    }
}
