package game;

import models.PlayerData;
import java.util.Random;
import java.util.Scanner;

public class CombatSystem {
    private final PlayerData player;
    private final Random random;
    private int shadowUses;

    public CombatSystem(PlayerData player) {
        this.player = player;
        this.random = new Random();
        this.shadowUses = 0;
    }

    public void resetShadowUses() {
        this.shadowUses = player.shadows.size();
    }

    public int getShadowUses() {
        return shadowUses;
    }

    // Attempt to escape from combat
    public boolean attemptEscape() {
        int escapeChance = 60; // 60% chance to escape
        if (random.nextInt(100) < escapeChance) {
            System.out.println("You successfully escaped!");
            return true;
        } else {
            System.out.println("Failed to escape!");
            return false;
        }
    }

    // Calculate player damage
    private int calculatePlayerDamage() {
        int baseDamage = 10 + player.level * 2;
        if (player.role != null && player.role.equals("Hunter")) {
            baseDamage += 5;
        }
        // Add small random variation to damage (±3)
        int variation = random.nextInt(7) - 3;
        return Math.max(1, baseDamage + variation);
    }

    // Calculate shadow damage
    private int calculateShadowDamage() {
        int baseDamage = 20 + player.level * 3;
        // Add variation
        int variation = random.nextInt(11) - 5;
        return Math.max(1, baseDamage + variation);
    }

    // Process player's turn
    public boolean processPlayerTurn(Enemy enemy, String input) {
        if (input.equals("1")) {
            // Normal attack
            int damage = calculatePlayerDamage();
            enemy.hp -= damage;
            System.out.println("You dealt " + damage + " damage to " + enemy.name);
            return true;
        } else if (input.equals("2") && !player.shadows.isEmpty() && shadowUses > 0) {
            // Shadow attack
            shadowUses--;
            int shadowDamage = calculateShadowDamage();
            enemy.hp -= shadowDamage;
            
            String shadowName = player.shadows.get(random.nextInt(player.shadows.size()));
            System.out.println("You summoned " + shadowName + " who dealt " + shadowDamage + " damage!");

            // Healer bonus on shadow summon
            if (player.role != null && player.role.equals("Healer")) {
                int healAmount = 5 + player.level;
                player.health = Math.min(player.health + healAmount, player.maxHealth);
                System.out.println("Your healing powers restore " + healAmount + " HP.");
            }
            return true;
        } else if ((input.equals("2") || input.equals("3")) && 
                  (player.shadows.isEmpty() || shadowUses <= 0 || input.equals("3"))) {
            // Run option
            if (enemy.isBoss) {
                System.out.println("You cannot run from a boss fight!");
                return false;
            }
            return attemptEscape();
        }
        return false;
    }

    // Process enemy's turn
    public void processEnemyTurn(Enemy enemy) {
        int enemyDamage = enemy.damage + random.nextInt(5) - 2; // ±2 damage variation
        
        // Tank role takes half damage
        if (player.role != null && player.role.equals("Tank")) {
            enemyDamage = Math.max(1, enemyDamage / 2);
        }

        player.health -= enemyDamage;
        System.out.println(enemy.name + " dealt " + enemyDamage + " damage to you!");
    }

    // Handle combat with an enemy
    public boolean combat(Enemy enemy, Scanner scanner) {
        if (player == null || enemy == null) {
            System.out.println("Combat error: Player or enemy is null!");
            return false;
        }

        System.out.println("\nYou are fighting " + enemy.name + "!");
        if (enemy.isBoss) {
            System.out.println("*** BOSS FIGHT ***");
        }

        // Reset shadow uses for this combat
        resetShadowUses();

        while (player.health > 0 && enemy.hp > 0) {
            System.out.println("\n" + enemy.name + "'s HP: " + enemy.hp + "/" + enemy.maxHp);
            System.out.println("Your HP: " + player.health + "/" + player.maxHealth);

            // Show combat options
            System.out.println("Choose an action:");
            System.out.println("1. Attack");
            if (!player.shadows.isEmpty() && shadowUses > 0) {
                System.out.println("2. Summon Shadow (" + shadowUses + " uses left)");
                System.out.println("3. Run");
            } else {
                System.out.println("2. Run");
            }

            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Process player's turn
            boolean playerEscaped = processPlayerTurn(enemy, input);
            if (playerEscaped) {
                return false; // Player chose to run and succeeded
            }

            // Check if enemy is defeated after player's turn
            if (enemy.hp <= 0) {
                break;
            }

            // Enemy's turn if still alive
            processEnemyTurn(enemy);
        }

        // Check combat result
        if (player.health <= 0) {
            System.out.println("\n❌ You were defeated by " + enemy.name + "...");
            return false;
        }

        return true; // Player won
    }
}
