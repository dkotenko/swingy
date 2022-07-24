package org.example.model.hero;

import lombok.Data;

@Data
public class Mage extends Hero{
    public Mage(String name) {
        this.name = name;
    }
}
