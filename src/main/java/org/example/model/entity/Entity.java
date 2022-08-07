package org.example.model.entity;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public abstract class Entity {
    protected int id;
    @Size(min=2, message = "Invalid name, must be at least 2 symbols")
    protected String name;
    protected int level;
    protected String type;
    protected int basicAttack;
    protected int basicDefence;
    protected int basicHp;

    public Entity() {
        id = EntityIdGenerator.generateId();
    }
}
