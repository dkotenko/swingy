package org.example.model.dto;

import lombok.Data;
import org.example.model.hero.Hero;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="hero")
public class HeroDTO {
    private int id;
    private String name;
    private String type;
    private int level;
    private int exp;
    private int helm;
    private int armor;
    private int weapon;

    public HeroDTO() {}

    public HeroDTO(Hero hero) {
        this.name = hero.getName();
        this.type = hero.getType();
        this.level = hero.getLevel();
        this.exp = hero.getCurrExp();
        this.helm = hero.getHelm().getLevel();
        this.armor = hero.getArmor().getLevel();
        this.weapon = hero.getWeapon().getLevel();
    }
}
