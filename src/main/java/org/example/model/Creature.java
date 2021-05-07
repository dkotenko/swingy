package org.example.model;

public abstract class Creature {
    protected long id;
    protected String name;
    protected String className;
    protected long level = 0;
    protected long attack = 0;
    protected long defence = 0;
    protected long hitPoints = 0;
    protected long Weapon = 0;
    protected long Armor = 0;
    protected long Helm = 0;
    protected Position position;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public long getLevel() {
        return level;
    }

    public long getAttack() {
        return attack;
    }

    public long getDefence() {
        return defence;
    }

    public long getHitPoints() {
        return hitPoints;
    }

    public long getWeapon() {
        return Weapon;
    }

    public long getArmor() {
        return Armor;
    }

    public long getHelm() {
        return Helm;
    }

    public Position getPosition() {
        return position;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public void setDefence(long defence) {
        this.defence = defence;
    }

    public void setHitPoints(long hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setWeapon(long weapon) {
        Weapon = weapon;
    }

    public void setArmor(long armor) {
        Armor = armor;
    }

    public void setHelm(long helm) {
        Helm = helm;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
