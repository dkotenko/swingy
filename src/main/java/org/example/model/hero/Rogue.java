package org.example.model.hero;

import lombok.Data;

@Data
public class Rogue extends Hero{
    public Rogue(String name) {
        this.name = name;
    }
}
