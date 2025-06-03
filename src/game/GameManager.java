package game;

import db.GameDB;
import java.util.HashMap;

import java.util.Random;
import java.util.Scanner;
import models.PlayerData;
import models.User;

// Manages game mechanics and dungeon system
public class GameManager {

    // Dungeon names
    public static String[] dungeonNames = {
        "Forest of Shadows",
        "Ice Cavern",
        "Volcanic Crater",
        "Ancient Ruins",
        "Dark Abyss"
    };

    // Predefined enemies for each dungeon
    public static Enemy[][] dungeonEnemies = {
        {new Enemy("Forest Goblin", 30, 10, false),
            new Enemy("Dark Wolf", 40, 15, false),
            new Enemy("Shadow Spider", 50, 18, false),
            new Enemy("Shadow Beast", 80, 25, true)
        },
        {new Enemy("Ice Troll", 40, 12, false),
            new Enemy("Frozen Specter", 45, 17, false),
            new Enemy("Glacier Serpent", 55, 20, false),
            new Enemy("Ice King", 90, 28, true)
        },
        {new Enemy("Lava Bat", 35, 14, false),
            new Enemy("Magma Golem", 50, 20, false),
            new Enemy("Fire Drake", 60, 22, false),
            new Enemy("Volcanic Lord", 85, 30, true)
        },
        {new Enemy("Ancient Skeleton", 30, 10, false),
            new Enemy("Cursed Archer", 45, 15, false),
            new Enemy("Phantom Knight", 55, 20, false),
            new Enemy("Ruins Guardian", 88, 27, true)
        },
        {new Enemy("Dark Wraith", 40, 16, false),
            new Enemy("Void Assassin", 50, 19, false),
            new Enemy("Nightmare Beast", 60, 23, false),
            new Enemy("Abyssal Overlord", 95, 32, true)
        }
    };

    // Current player in the game
    private final PlayerData player;
    private final User user;

    // Time when player was defeated (for recovery cooldown)
    private long defeatTime = 0;

    // Shadow uses per battle
    private int shadowUses = 0;

    // Track dungeon progress using HashMap
    private final HashMap<Integer, DungeonProgress> dungeonProgress = new HashMap<>();

    // Scanner for player input
    private final Scanner scanner;

    // Random number generator
    private final Random random = new Random();

    public GameManager(PlayerData player, User user, Scanner scanner) {
        this.player = player;
        this.user = user;
        this.scanner = scanner;
        loadProgress();
    }

    // Load progress from database
    private void loadProgress() {
        // Initialize all dungeons with empty progress
        for (int i = 0; i < dungeonNames.length; i++) {
            dungeonProgress.put(i, new DungeonProgress());
        }

        // Load saved progress if user exists
        if (user != null) {
            HashMap<Integer, DungeonProgress> savedProgress = GameDB.loadDungeonProgress(user);
            if (savedProgress != null) {
                // Update only the dungeons that have saved progress
                for (var entry : savedProgress.entrySet()) {
                    int dungeonId = entry.getKey();
                    if (dungeonId >= 0 && dungeonId < dungeonNames.length) {
                        dungeonProgress.put(dungeonId, entry.getValue());
                    }
                }
            }
        }
    }

    // Save progress to database
    public void saveProgress() {
        if (user != null) {
            // Save player data
            GameDB.saveGameState(user, player);

            // Save dungeon progress
            for (var entry : dungeonProgress.entrySet()) {
                int dungeonId = entry.getKey();
                DungeonProgress progress = entry.getValue();
                GameDB.saveDungeonProgress(user, dungeonId, progress.enemiesDefeated, progress.gateClosed);
            }

            System.out.println("Game progress saved!");
        }
    }

    // Check if player is in recovery period
    public boolean isRecovering() {
        if (defeatTime > 0) {
            long passedSeconds = (System.currentTimeMillis() - defeatTime) / 1000;
            return passedSeconds < 60;
        }
        return false;
    }

    // Get recovery time remaining in seconds
    public long getRecoveryTimeRemaining() {
        if (defeatTime > 0) {
            long passedSeconds = (System.currentTimeMillis() - defeatTime) / 1000;
            return Math.max(0, 60 - passedSeconds);
        }
        return 0;
    }

    // End recovery period
    public void endRecovery() {
        defeatTime = 0;
        player.setHealth(player.getMaxHealth());
    }

    // Check if all dungeons are cleared
    public boolean areAllDungeonsClosed() {
        for (DungeonProgress progress : dungeonProgress.values()) {
            if (!progress.gateClosed) {
                return false;
            }
        }
        return true;
    }

    // Get dungeon progress for display
    public HashMap<Integer, DungeonProgress> getDungeonProgress() {
        return dungeonProgress;
    }

    // Dungeon selection menu
    public void selectDungeon() {
        clearScreen();

        // Check if in recovery period
        if (isRecovering()) {
            long remainingSeconds = getRecoveryTimeRemaining();
            System.out.println("You are too injured to enter a dungeon!");
            System.out.println("Recovery time remaining: " + remainingSeconds + " seconds");
            return;
        }

        printBox("DUNGEONS");
        for (int i = 0; i < dungeonNames.length; i++) {
            DungeonProgress progress = dungeonProgress.get(i);
            String status = progress.gateClosed ? "[CLOSED]" : "[OPEN]";
            System.out.printf("%d. %s %s - Progress: %d/4\n", i + 1, dungeonNames[i], status, progress.enemiesDefeated);
        }

        System.out.print("\nSelect a dungeon (1-" + dungeonNames.length + ") or 0 to return: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 0) {
                return;
            }

            if (choice > 0 && choice <= dungeonNames.length) {
                int dungeonIndex = choice - 1;
                DungeonProgress progress = dungeonProgress.get(dungeonIndex);

                if (progress.gateClosed) {
                    System.out.println("\nThis dungeon gate is closed!");
                    return;
                }

                enterDungeon(dungeonIndex);
            } else {
                System.out.println("Invalid dungeon selection!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // Helper method to clear the console
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Helper method to print a box
    private void printBox(String content) {
        int width = 50;
        String line = "+" + "-".repeat(width - 2) + "+";
        System.out.println(line);

        for (String s : content.split("\n")) {
            int padding = width - s.length() - 4;
            System.out.printf("| %s%s |\n", s, " ".repeat(Math.max(0, padding)));
        }

        System.out.println(line);
    }

    // Attempt to escape from combat
    private boolean attemptEscape() {
        int escapeChance = 60; // 60% chance to escape
        if (random.nextInt(100) < escapeChance) {
            System.out.println("You successfully escaped!");
            return true;
        } else {
            System.out.println("Failed to escape!");
            return false;
        }
    }

    // Handle combat with an enemy
    public boolean combat(Enemy enemy) {
        if (player == null || enemy == null) {
            System.out.println("Combat error: Player or enemy is null!");
            return false;
        }

        System.out.println("\nYou are fighting " + enemy.name + "!");
        if (enemy.isBoss) {
            System.out.println("*** BOSS FIGHT ***");
        }

        // Reset shadow uses for this combat
        shadowUses = player.getShadows().size();

        while (player.getHealth() > 0 && enemy.hp > 0) {
            System.out.println("\n" + enemy.name + "'s HP: " + enemy.hp + "/" + enemy.maxHp);
            System.out.println("Your HP: " + player.getHealth() + "/" + player.getMaxHealth());

            // Show combat options
            System.out.println("Choose an action:");
            System.out.println("1. Attack");
            if (!player.getShadows().isEmpty() && shadowUses > 0) {
                System.out.println("2. Summon Shadow (" + shadowUses + " uses left)");
                System.out.println("3. Run");
            } else {
                System.out.println("2. Run");
            }

            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Process player action
            if (input.equals("1")) {
                // Player attacks enemy
                int playerDamage = 10 + player.level * 2;
                if (player.getRole() != null && player.getRole().equals("Hunter")) {
                    playerDamage += 5;
                }

                // Add small random variation to damage (±3)
                playerDamage += random.nextInt(7) - 3;
                if (playerDamage < 1) {
                    playerDamage = 1; // Ensure minimum 1 damage
                }
                enemy.hp -= playerDamage;
                System.out.println("You dealt " + playerDamage + " damage to " + enemy.name);
            } else if (input.equals("2") && !player.getShadows().isEmpty() && shadowUses > 0) {
                // Summon shadow to attack
                shadowUses--;
                int shadowDamage = 20 + player.level * 3;
                // Add variation
                shadowDamage += random.nextInt(11) - 5;
                if (shadowDamage < 1) {
                    shadowDamage = 1;
                }

                String shadowName = player.getShadows().get(random.nextInt(player.getShadows().size()));
                enemy.hp -= shadowDamage;
                System.out.println("You summoned " + shadowName + " who dealt " + shadowDamage + " damage!");

                // Healer bonus on shadow summon
                if (player.getRole() != null && player.getRole().equals("Healer")) {
                    int healAmount = 5 + player.level;
                    player.setHealth(Math.min(player.getHealth() + healAmount, player.getMaxHealth()));
                    System.out.println("Your healing powers restore " + healAmount + " HP.");
                }
            } else if ((input.equals("2") || input.equals("3"))
                    && (player.getShadows().isEmpty() || shadowUses <= 0 || input.equals("3"))) {
                // Run option
                if (enemy.isBoss) {
                    System.out.println("You cannot run from a boss fight!");
                    continue;
                }

                if (attemptEscape()) {
                    return false; // Combat ends, player escaped
                }
            } else {
                System.out.println("Invalid choice!");
                continue;
            }

            // Enemy's turn if still alive
            if (enemy.hp > 0) {
                int enemyDamage = enemy.damage + random.nextInt(5) - 2; // ±2 damage variation
                if (enemyDamage < 1) {
                    enemyDamage = 1;
                }

                // Tank role takes half damage
                if (player.getRole() != null && player.getRole().equals("Tank")) {
                    enemyDamage = Math.max(1, enemyDamage / 2);
                }

                player.setHealth(player.getHealth() - enemyDamage);
                System.out.println(enemy.name + " dealt " + enemyDamage + " damage to you!");
            }
        }

        // Check combat result
        if (player.getHealth() <= 0) {
            System.out.println("\n❌ You were defeated by " + enemy.name + "...");
            return false;
        }

        return true; // Player won
    }

    // Enter specified dungeon
    public void enterDungeon(int dungeonIndex) {
        DungeonProgress progress = dungeonProgress.get(dungeonIndex);
        if (progress == null) {
            progress = new DungeonProgress();
            dungeonProgress.put(dungeonIndex, progress);
        }

        if (progress.gateClosed) {
            System.out.println("This dungeon gate is already closed!");
            return;
        }

        System.out.println("\nEntering " + dungeonNames[dungeonIndex] + "...");
        System.out.println("You need to defeat 4 enemies to close this dungeon gate.");
        System.out.printf("[Progress: %d/4 enemies defeated]\n", progress.enemiesDefeated);

        Enemy[] enemies = dungeonEnemies[dungeonIndex];
        for (int i = progress.enemiesDefeated; i < 4; i++) {
            Enemy enemy = new Enemy(enemies[i]);
            System.out.println("\nEncountered " + enemy.name + (enemy.isBoss ? " (Boss)!" : "") + " (HP: " + enemy.hp + ")");

            boolean victory = combat(enemy);
            if (!victory) {
                defeatTime = System.currentTimeMillis();
                saveProgress();
                return;
            }

            // Handle victory
            System.out.println("\nYou defeated " + enemy.name + "!");

            // Check for shadow acquisition (15% chance for normal enemies, 40% for bosses)
            int shadowChance = enemy.isBoss ? 40 : 15;
            if (random.nextInt(100) < shadowChance) {
                String shadowName = enemy.name + " Shadow";
                player.getShadows().add(shadowName);
                System.out.println("\n🔥 You extracted the shadow of " + enemy.name + "! 🔥");
                System.out.println("Your new shadow monarch will aid you in battle.");
            }

            // Healing for Healer role
            if (player.getRole() != null && player.getRole().equals("Healer")) {
                int healAmount = player.getMaxHealth() / 10;
                player.setHealth(Math.min(player.getHealth() + healAmount, player.getMaxHealth()));
                System.out.println("Your healing powers restore " + healAmount + " HP.");
            }

            // Award EXP
            int expGained = 20 + enemy.damage * 2;
            if (enemy.isBoss) {
                expGained *= 3;
            }
            player.gainExp(expGained);
            System.out.printf("Gained %d EXP! (%d/%d to next level)%n",
                    expGained, player.exp, player.level * 100);

            progress.enemiesDefeated++;

            if (progress.enemiesDefeated >= 4) {
                progress.gateClosed = true;
                System.out.println("\nCongratulations! You've closed the dungeon gate!");

                // Extra rewards for completing a dungeon
                int bonusExp = 50 + dungeonIndex * 25;
                player.gainExp(bonusExp);
                System.out.println("Bonus EXP for completing dungeon: " + bonusExp);

                // Heal player after completing dungeon
                player.setHealth(player.getMaxHealth());
                System.out.println("Your health has been fully restored!");
            }

            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
        }

        saveProgress();
    }
}
