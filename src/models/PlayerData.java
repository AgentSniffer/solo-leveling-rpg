package models;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    public String playerName;
    public int level;
    public int exp;
    public int health;
    public int maxHealth;
    public String role;
    public List<String> shadows;
    public int attackBonus = 0;
    public int defenseBonus = 0;
    public int healingBonus = 0;

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

    public PlayerData(String name, int lvl, int xp, int hp, int maxHp, String playerRole, List<String> playerShadows) {
        playerName = name;
        level = lvl;
        exp = xp;
        health = hp;
        maxHealth = maxHp;
        role = playerRole;
        shadows = playerShadows != null ? playerShadows : new ArrayList<>();
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
        String status = "⚔️  " + playerName + "'s Game\n"
                + "🛡️  Level: " + level + "\n"
                + "✨ Exp: " + exp + "/100\n"
                + "❤️  Health: " + health + "/" + maxHealth + "\n";

        if (role != null && !role.isEmpty()) {
            status += "🧙  Role: " + role + "\n";
        }

        status += "👻  Shadows: ";
        if (shadows.isEmpty()) {
            status += "None";
        } else {
            String result = "";
            for (String shadow : shadows) {
                result += shadow + ", ";
            }
            status += result.substring(0, result.length() - 2);
        }

        return status;
    }

    // Getters and setters
    public int getExp() {
        return exp;
    }
    
    public int getLevel() {
        return level;
    }

    public void setHealth(int healthValue) {
        health = Math.max(0, Math.min(healthValue, maxHealth));
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHp) {
        maxHealth = maxHp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String playerRole) {
        role = playerRole;
    }

    public List<String> getShadows() {
        return shadows;
    }

    public void setShadows(List<String> shadowList) {
        shadows = shadowList;
    }
}
