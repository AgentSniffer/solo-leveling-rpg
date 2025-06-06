package model;

import java.util.Random;

public class EnemyModel extends CharacterModel {
    public static Random ra = new Random();

    public EnemyModel(String type, int areaLevel) {
        this.name = type;
        this.level = Math.max(1, areaLevel + ra.nextInt(3) - 1); // -1 to +1 level variation

        // Base stats based on enemy type
        switch (type) {
            case "Goblin" -> {
                strength = 10 + level;
                endurance = 8 + level;
                agility = 12 + level;
                intelligence = 5;
            }
            case "Skeleton" -> {
                strength = 12 + level;
                endurance = 15 + level;
                agility = 8 + level;
                intelligence = 3;
            }
            case "Orc" -> {
                strength = 15 + level;
                endurance = 12 + level;
                agility = 7 + level;
                intelligence = 6;
            }
            case "Wolf" -> {
                strength = 8 + level;
                endurance = 10 + level;
                agility = 15 + level;
                intelligence = 4;
            }
            default -> {
                // Generic enemy
                strength = 10 + level;
                endurance = 10 + level;
                agility = 10 + level;
                intelligence = 5;
            }
        }

        maxHp = 30 + (endurance * 2) + (level * 10);
        hp = maxHp;
    }
}
