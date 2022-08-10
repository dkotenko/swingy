package org.example.model.hero.dto;

import lombok.Data;
import org.example.model.hero.Hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class HeroDTO {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String type;
    @Column(name="curr_level")
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
        this.helm = hero.getHelm();
        this.armor = hero.getArmor();
        this.weapon = hero.getWeapon();
    }

    @Override
    public String toString() {
        return String.format("name: %12.12s | class: %10.10s | level: %3d",
                name, type, level);
    }
}
