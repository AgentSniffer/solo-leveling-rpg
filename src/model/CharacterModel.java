package model;

import java.util.Random;

public class CharacterModel {
    public static Random ra = new Random();

    public String name;
    public int level = 1;
    public int strength;
    public int endurance;
    public int agility;
    public int intelligence;
    public int luck;
    public int hp; // health points
    public int maxHp;
    public int mp; // magic points
    public int maxMp;
    public int exp;
    public int gold;
    
    public int calculateDamage() {
        int baseDamage = strength / 2 + level;
        int variation = ra.nextInt(5);
        return baseDamage + variation;
    }
    
    public int nextLevelExp() {
        return level * 100;
    }
}