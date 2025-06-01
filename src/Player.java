
import java.util.Random;

// Simple Player class - handles player stuff like Jin-Woo
public class Player {

    String name;
    int level;
    int hp;
    int maxHP;
    int strength;
    int xp;
    int xpNeeded;
    Random rand = new Random();

    public Player(String newName) {
        name = newName;
        level = 1;
        hp = 100;
        maxHP = 100;
        strength = 10;
        xp = 0;
        xpNeeded = 100;
    }

    // Show player stats
    public void showMyStats() {
        System.out.println("\n=== THE SYSTEM ===");
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("HP: " + hp + "/" + maxHP);
        System.out.println("Strength: " + strength);
        System.out.println("XP: " + xp + "/" + xpNeeded);
    }

    // Damage method
    public void attack(Monster enemy) {
        int damage = strength + rand.nextInt(15);
        System.out.println("💥 You punch for " + damage + " damage!");
        enemy.hp -= damage;
    }

    // Hurt method
    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println("😵 You got hurt for " + damage + " damage!");
    }

    public void gainXP(int xpPoints) {
        xp += xpPoints;
        System.out.println("✨ You gained " + xpPoints + " XP!");

        // Check if we can level up
        if (xp >= xpNeeded) {
            levelUp();
        }
    }

    // Level up method
    public void levelUp() {
        level++;
        xp -= xpNeeded;
        xpNeeded += 75;

        // Stats increase
        maxHP += 25;          // Add 25 to max health
        hp = maxHP;           // Fill health bar
        strength += 8;        // Add 8 to strength

        System.out.println("\n🎉 LEVEL UP! 🎉");
        System.out.println("You are now level " + level + "!");
    }

    // Rest method
    public void rest() {
        hp = maxHP;
        System.out.println("\n😴 You rest and feel better...");
        System.out.println("❤️ All your health is back!");
    }

    public boolean stillAlive() {
        return hp > 0;
    }
}
