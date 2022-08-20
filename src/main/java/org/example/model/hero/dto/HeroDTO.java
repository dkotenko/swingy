package org.example.model.hero.dto;

import lombok.Data;
import org.example.model.hero.Hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

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
    private int expToLevel;
    private int helm;
    private int armor;
    private int weapon;

    public HeroDTO() {}

    public HeroDTO(Hero hero) {
        this.name = hero.getName();
        this.type = hero.getType();
        this.level = hero.getLevel();
        this.exp = hero.getCurrExp();
        this.expToLevel = hero.getNextLevelExp() - hero.getCurrExp();
        this.helm = hero.getHelm();
        this.armor = hero.getArmor();
        this.weapon = hero.getWeapon();
    }

    @Override
    public String toString() {
        return String.format("name: %12.12s | class: %10.10s | level: %3d",
                name, type, level);
    }

    public ArrayList<String> toList() {
        ArrayList<String> list = new ArrayList<>();

        HeroDTO obj = new HeroDTO();
        list.add(String.format("%10s: %10s", "name", name));
        list.add(String.format("%10s: %10s", "type", type));
        list.add(String.format("%10s: %10d", "level", level));
        list.add(String.format("%10s: %10d", "exp to lvl", expToLevel));
        list.add(String.format("%10s: %10d", "helm tier", helm));
        list.add(String.format("%10s: %10d", "armor tier", armor));
        list.add(String.format("%10s: %10d", "weapon tier", weapon));
        return list;
    }

}
