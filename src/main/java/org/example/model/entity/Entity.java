package org.example.model.entity;

import lombok.Data;

@Data
public abstract class Entity {
    protected int id;
    protected String name;
    protected int level;
    protected String type;
    protected int basicAttack;
    protected int basicDefence;
    protected int basicHp;
    EntityIdGenerator idGenerator;

    public Entity() {
        id = idGenerator.generateId();
    }
}
