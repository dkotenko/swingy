package org.example.model.hero.dto;

import lombok.Data;
import org.example.model.hero.Hero;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class HeroDTO {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
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
    @Transient
    private int hp;

    public HeroDTO() {}

    public HeroDTO(Hero hero) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.type = hero.getType();
        this.level = hero.getLevel();
        this.exp = hero.getCurrExp();
        this.expToLevel = hero.getNextLevelExp() - hero.getCurrExp();
        this.helm = hero.getHelm();
        this.armor = hero.getArmor();
        this.weapon = hero.getWeapon();
        this.hp = hero.getHp();
    }

    @Override
    public String toString() {
        return String.format("id: %4d | name: %12.12s | class: %10.10s | level: %3d", id,
                name, type, level);
    }

    public static String [] provideColumnNames() {
        return new String [] {"ID", "Name", "Class", "Level"};
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
        list.add(String.format("%10s: %10d", "hp", hp));
        return list;
    }

}
