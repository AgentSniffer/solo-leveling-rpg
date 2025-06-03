package game;

import db.GameDB;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import models.PlayerData;
import models.User;

// Manages game mechanics and dungeon system
public class GameManager {

    // Available roles with their bonuses
    public static final String[] ROLES = {
        "Fighter", "Healer", "Tank", "Assassin", "Ranger", "Mage"
    };

    // Role-specific bonuses (attack, defense, special)
    private static final Map<String, int[]> ROLE_BONUSES = new HashMap<>() {{
        put("Fighter", new int[]{5, 0, 0});    // +5% attack
        put("Healer", new int[]{0, 5, 10});     // +5% defense, +10% healing
        put("Tank", new int[]{0, 10, 0});       // +10% defense
        put("Assassin", new int[]{10, -5, 0});   // +10% attack, -5% defense
        put("Ranger", new int[]{7, 3, 0});       // +7% attack, +3% defense
        put("Mage", new int[]{8, 0, 5});         // +8% attack, +5% healing
    }};

    // Dungeon names
    public static final String[] DUNGEON_NAMES = {
        "Forest of Shadows",
        "Ice Cavern",
        "Volcanic Crater",
        "Ancient Ruins",
        "Dark Abyss"
    };

    // Predefined enemies for each dungeon
    public static final Enemy[][] DUNGEON_ENEMIES = {
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

    // Apply role bonuses to player stats
    private void applyRoleBonuses() {
        if (player.role == null || !ROLE_BONUSES.containsKey(player.role)) {
            return;
        }
        
        int[] bonuses = ROLE_BONUSES.get(player.role);
        player.attackBonus = (int)(player.attackBonus * (1 + bonuses[0] / 100.0));
        player.defenseBonus = (int)(player.defenseBonus * (1 + bonuses[1] / 100.0));
        player.healingBonus = (int)(player.healingBonus * (1 + bonuses[2] / 100.0));
    }

    // Current player in the game
    private final PlayerData player;
    private final User user;

    // Time when player was defeated (for recovery cooldown)
    private long defeatTime = 0;

    // Shadow uses are now handled by CombatSystem

    // Track dungeon progress using HashMap
    private final HashMap<Integer, DungeonProgress> dungeonProgress = new HashMap<>();

    // Scanner for player input
    private final Scanner scanner;

    // Random number generator
    private final Random random = new Random();

    private final CombatSystem combatSystem;

    public GameManager(PlayerData player, User user, Scanner scanner) {
        this.player = player;
        this.user = user;
        this.scanner = scanner;
        this.combatSystem = new CombatSystem(player);
        applyRoleBonuses();
        loadProgress();
    }

    // Load progress from database
    private void loadProgress() {
        // Initialize all dungeons with empty progress
        for (int i = 0; i < DUNGEON_NAMES.length; i++) {
            dungeonProgress.put(i, new DungeonProgress());
        }

        // Load saved progress if user exists
        if (user != null) {
            HashMap<Integer, DungeonProgress> savedProgress = GameDB.loadDungeonProgress(user);
            if (savedProgress != null) {
                // Update only the dungeons that have saved progress
                for (var entry : savedProgress.entrySet()) {
                    int dungeonId = entry.getKey();
                    if (dungeonId >= 0 && dungeonId < DUNGEON_NAMES.length) {
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
        for (int i = 0; i < DUNGEON_NAMES.length; i++) {
            DungeonProgress progress = dungeonProgress.get(i);
            String status = progress.gateClosed ? "[CLOSED]" : "[OPEN]";
            System.out.printf("%d. %s %s - Progress: %d/4\n", i + 1, DUNGEON_NAMES[i], status, progress.enemiesDefeated);
        }

        System.out.print("\nSelect a dungeon (1-" + DUNGEON_NAMES.length + ") or 0 to return: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 0) {
                return;
            }

            if (choice > 0 && choice <= DUNGEON_NAMES.length) {
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

    // Handle combat with an enemy
    public void enterDungeon(int dungeonIndex) {
        if (dungeonIndex < 0 || dungeonIndex >= DUNGEON_NAMES.length) {
            System.out.println("Invalid dungeon number!");
            return;
        }

        // Check if player is in recovery
        if (isRecovering()) {
            System.out.println("You are still recovering from your last defeat. Please wait " + 
                getRecoveryTimeRemaining() + " seconds before entering a dungeon.");
            return;
        }

        // Get or create dungeon progress
        DungeonProgress progress = dungeonProgress.computeIfAbsent(dungeonIndex, k -> new DungeonProgress());
        
        // Check if we need to spawn the boss
        boolean isBossFight = progress.enemiesDefeated >= 5 && !progress.gateClosed;
        
        // Create enemy for this encounter
        Enemy enemy;
        if (isBossFight) {
            // Boss fight
            enemy = DUNGEON_ENEMIES[dungeonIndex][3];
            System.out.println("\n=== BOSS BATTLE: " + enemy.name.toUpperCase() + " ===");
            System.out.println("The Gatekeeper appears! Defeat it to close the dungeon gate!");
        } else {
            // Regular enemy
            enemy = DUNGEON_ENEMIES[dungeonIndex][random.nextInt(DUNGEON_ENEMIES[dungeonIndex].length - 1)];
            // Create a fresh copy of the enemy
            enemy = new Enemy(enemy);
            System.out.println("\nA wild " + enemy.name + " appears!");
        }

        // Start combat
        boolean playerWon = combatSystem.combat(enemy, scanner);

        // Handle combat result
        if (playerWon) {
            if (isBossFight) {
                // Player defeated the boss
                progress.gateClosed = true;
                System.out.println("\nYou have closed the gate of " + DUNGEON_NAMES[dungeonIndex] + "!");
                System.out.println("The dungeon has been cleared!");
                
                // Grant bonus XP for boss defeat
                int xpGain = 100 + (dungeonIndex * 20);
                player.gainExp(xpGain);
                System.out.println("You gained " + xpGain + " XP!");
                
                // Add a shadow from the boss
                String shadowName = "Shadow " + enemy.name;
                player.shadows.add(shadowName);
                System.out.println("You gained a new shadow: " + shadowName);
                
                // Save progress
                saveProgress();
            } else {
                // Player defeated a regular enemy
                progress.enemiesDefeated++;
                
                // Grant XP based on dungeon level
                int xpGain = 10 + (dungeonIndex * 2);
                player.gainExp(xpGain);
                System.out.println("You gained " + xpGain + " XP!");
                
                // Random chance to get a shadow (20% chance)
                if (random.nextDouble() < 0.2) {
                    String shadowName = "Shadow " + enemy.name;
                    player.shadows.add(shadowName);
                    System.out.println("You gained a new shadow: " + shadowName);
                }
                
                // Save progress after each enemy
                saveProgress();
            }
            
            // Heal player after battle (25% of max HP, up to max)
            int healAmount = (int)(player.getMaxHealth() * 0.25);
            int newHealth = Math.min(player.getHealth() + healAmount, player.getMaxHealth());
            player.setHealth(newHealth);
            System.out.println("You recovered " + (newHealth - player.getHealth() + healAmount) + " HP after battle.");
        } else {
            // Player was defeated
            System.out.println("\nYou were defeated in " + DUNGEON_NAMES[dungeonIndex] + "...");
            
            // Set recovery cooldown (1 minute)
            defeatTime = System.currentTimeMillis();
            
            // Reset player HP to 1 after defeat
            player.setHealth(1);
            
            // Save progress
            saveProgress();
        }
    }

    // Handle combat with an enemy
    public boolean combat(Enemy enemy) {
        if (player == null || enemy == null) {
            System.out.println("Combat error: Player or enemy is null!");
            return false;
        }
        return combatSystem.combat(enemy, scanner);
    }
}
