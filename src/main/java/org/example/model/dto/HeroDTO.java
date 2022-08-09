package org.example.model.dto;

import lombok.Data;
import org.example.model.hero.Hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class HeroDTO {
    @Id
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
}
