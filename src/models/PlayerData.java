package models;

import java.util.*;

public class PlayerData {

    public String playerName;
    public int level;
    public int exp;
    private int health;
    private int maxHealth;
    private String role;
    private List<String> shadows;

    public PlayerData(String name) {
        playerName = name;
        level = 1;
        exp = 0;
        maxHealth = 100;
        health = 100;
        shadows = new ArrayList<>();
    }

    public PlayerData(String name, int lvl, int xp) {
        playerName = name;
        level = lvl;
        exp = xp;
        maxHealth = 100 + (lvl - 1) * 20; // 20 HP per level
        health = maxHealth;
        shadows = new ArrayList<>();
    }

    public PlayerData(String name, int lvl, int xp, int hp, int maxHp, String role, List<String> shadows) {
        playerName = name;
        level = lvl;
        exp = xp;
        health = hp;
        maxHealth = maxHp;
        this.role = role;
        this.shadows = shadows != null ? shadows : new ArrayList<>();
    }

    public void gainExp(int amount) {
        exp += amount;
        if (exp >= 100) {
            level++;
            exp = 0;
            maxHealth += 20; // Increase max HP on level up
            health = maxHealth; // Heal to full on level up
            System.out.println("\nLevel up! Now level " + level);
        }
    }

    public String getStatus() {
        return "⚔️  " + playerName + "'s Game\n"
                + "🛡️  Level: " + level + "\n"
                + "✨ Exp: " + exp + "/100";
    }

    public String getDetailedStatus() {
        StringBuilder status = new StringBuilder();
        status.append("⚔️  ").append(playerName).append("'s Game\n");
        status.append("🛡️  Level: ").append(level).append("\n");
        status.append("✨ Exp: ").append(exp).append("/100\n");
        status.append("❤️  Health: ").append(health).append("/").append(maxHealth).append("\n");

        if (role != null && !role.isEmpty()) {
            status.append("🧙  Role: ").append(role).append("\n");
        }

        status.append("👻  Shadows: ");
        if (shadows.isEmpty()) {
            status.append("None");
        } else {
            status.append(String.join(", ", shadows));
        }

        return status.toString();
    }

    // Getters and setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getShadows() {
        return shadows;
    }

    public void setShadows(List<String> shadows) {
        this.shadows = shadows;
    }
}
