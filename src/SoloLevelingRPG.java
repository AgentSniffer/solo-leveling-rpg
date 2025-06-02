
import java.util.*;

public class SoloLevelingRPG {

    static Scanner sc = new Scanner(System.in);

    static class Account {

        String name;
        String password;
        String role;
        int level = 1;
        int maxHP = 100;
        int currentHP = 100;
        int exp = 0;
        List<String> shadows = new ArrayList<>();
        Map<Integer, DungeonProgress> progress = new HashMap<>();

        Account(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }
    }

    static class DungeonProgress {

        int enemiesDefeated = 0;
        boolean gateClosed = false;
    }

    static class Enemy {

        String name;
        int hp;
        int maxHp;
        int damage;
        boolean isBoss;

        Enemy(String name, int hp, int damage, boolean isBoss) {
            this.name = name;
            this.hp = hp;
            this.maxHp = hp;
            this.damage = damage;
            this.isBoss = isBoss;
        }
    }

    static Account account = null;

    static String[] roles = {
        "Fighter", "Healer", "Tanker", "Assassin", "Ranger", "Mage"
    };

    static String[] dungeonNames = {
        "Forest of Shadows",
        "Ice Cavern",
        "Volcanic Crater",
        "Ancient Ruins",
        "Dark Abyss"
    };

    static Enemy[][] dungeonEnemies = {
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

    // For simplicity, shadows count limits usage per fight (start 0)
    static int shadowUses = 0;

    // Recovery timer
    static long defeatTime = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to Solo Leveling RPG!");
        while (true) {
            System.out.println("\n1. Sign In\n2. Create Account\nChoose: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                if (signIn()) {
                    break;
                }
            } else if (choice.equals("2")) {
                createAccount();
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }

        chooseRole();

        mainMenu();
    }

    static boolean signIn() {
        System.out.print("Enter your hunter name: ");
        String name = sc.nextLine();
        System.out.print("Enter your password: ");
        String pass = sc.nextLine();

        // In this demo, only one account active (no DB), so fail if no match
        if (account != null && account.name.equals(name) && account.password.equals(pass)) {
            System.out.println("Welcome back, " + name + "!");
            return true;
        } else {
            System.out.println("Account not found or incorrect password.");
            return false;
        }
    }

    static void createAccount() {
        System.out.print("Enter your hunter name: ");
        String name = sc.nextLine();
        System.out.print("Create a password: ");
        String pass = sc.nextLine();

        account = new Account(name, pass, null);
        System.out.println("\nAccount created successfully!");
    }

    static void chooseRole() {
        System.out.println("\nChoose your role:");
        for (int i = 0; i < roles.length; i++) {
            System.out.printf("%d. %s\n", i + 1, roles[i]);
        }
        while (true) {
            System.out.print("Choose: ");
            String input = sc.nextLine();
            try {
                int roleNum = Integer.parseInt(input);
                if (roleNum >= 1 && roleNum <= roles.length) {
                    account.role = roles[roleNum - 1];
                    System.out.println("\nCongrats, you are now classified as an " + account.role + " hunter.");
                    System.out.println("You have a task to defeat all the enemies and close the dungeon gates.");
                    break;
                }
            } catch (Exception e) {
            }
            System.out.println("Invalid input, try again.");
        }
    }

    static void mainMenu() {
        while (true) {
            // If player defeated and recovery timer active
            if (defeatTime > 0) {
                long passed = (System.currentTimeMillis() - defeatTime) / 1000;
                if (passed < 60) {
                    System.out.println("\nYou were defeated recently...");
                    System.out.println("You must wait " + (60 - passed) + " seconds to recover before returning.");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                    }
                    continue;
                } else {
                    System.out.println("\nYou have recovered! Your HP is fully restored.");
                    account.currentHP = account.maxHP;
                    defeatTime = 0;
                }
            }

            System.out.println("\nMain Menu:");
            System.out.println("1. Continue to Dungeon");
            System.out.println("2. View Stats");
            System.out.println("3. Exit Game");
            System.out.print("Choose: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    dungeonMenu();
                    break;
                case "2":
                    viewStats();
                    break;
                case "3":
                    System.out.println("Thanks for playing! Goodbye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    static void viewStats() {
        System.out.println("\nYour Stats:");
        System.out.println("Hunter: " + account.name);
        System.out.println("Role: " + account.role);
        System.out.println("Level: " + account.level);
        System.out.println("HP: " + account.currentHP + "/" + account.maxHP);
        System.out.println("EXP: " + account.exp);
        if (account.shadows.isEmpty()) {
            System.out.println("Shadows: None");
        } else {
            System.out.println("Shadows: " + String.join(", ", account.shadows));
        }
        System.out.println("\n1. Return to Main Menu");
        sc.nextLine();
    }

    static void dungeonMenu() {
        while (true) {
            System.out.println("\nAvailable Dungeons:");
            boolean allClosed = true;
            for (int i = 0; i < dungeonNames.length; i++) {
                DungeonProgress dp = account.progress.getOrDefault(i, new DungeonProgress());
                if (dp.gateClosed) {
                    System.out.printf("%d. %s [CLOSED]\n", i + 1, dungeonNames[i]);
                } else {
                    System.out.printf("%d. %s\n", i + 1, dungeonNames[i]);
                    allClosed = false;
                }
            }
            System.out.printf("%d. Return to Main Menu\n", dungeonNames.length + 1);
            System.out.print("Choose a dungeon (1-" + (dungeonNames.length + 1) + "): ");

            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == dungeonNames.length + 1) {
                    return;
                }
                if (choice >= 1 && choice <= dungeonNames.length) {
                    DungeonProgress dp = account.progress.getOrDefault(choice - 1, new DungeonProgress());
                    if (dp.gateClosed) {
                        System.out.println("That dungeon gate is closed. Choose another dungeon.");
                    } else {
                        startDungeon(choice - 1);
                        // Check if all closed after return
                        boolean allDone = true;
                        for (int i = 0; i < dungeonNames.length; i++) {
                            DungeonProgress d = account.progress.getOrDefault(i, new DungeonProgress());
                            if (!d.gateClosed) {
                                allDone = false;
                                break;
                            }
                        }
                        if (allDone) {
                            System.out.println("\n✨ Congrats, Hunter " + account.name + "!");
                            System.out.println("You have defeated all the enemies and closed all dungeon gates.");
                            System.out.println("The world is safe thanks to your effort!");
                            System.exit(0);
                        }
                    }
                } else {
                    System.out.println("Invalid input.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    static void startDungeon(int dungeonIndex) {
        DungeonProgress dp = account.progress.getOrDefault(dungeonIndex, new DungeonProgress());
        System.out.println(dp.enemiesDefeated == 0
                ? "\nEntering " + dungeonNames[dungeonIndex] + "..."
                : "\nResuming " + dungeonNames[dungeonIndex] + "...");

        System.out.println("You need to defeat 4 enemies to close this dungeon gate.");
        System.out.printf("[Progress: %d/4 enemies defeated]\n", dp.enemiesDefeated);

        Enemy[] enemies = dungeonEnemies[dungeonIndex];
        for (int i = dp.enemiesDefeated; i < 4; i++) {
            Enemy enemy = new Enemy(enemies[i].name, enemies[i].maxHp, enemies[i].damage, enemies[i].isBoss);
            System.out.println("\nEncountered " + enemy.name + (enemy.isBoss ? " (Boss)!" : "") + " (HP: " + enemy.hp + ")");

            shadowUses = account.shadows.size(); // Number of shadows can be used as shadow uses per fight

            boolean defeated = fight(enemy);
            if (!defeated) {
                // Defeat - save progress, set defeatTime and return to main menu
                dp.enemiesDefeated = i;
                account.progress.put(dungeonIndex, dp);
                defeatTime = System.currentTimeMillis();
                System.out.println("\nYou must wait 1 minute to recover before returning.");
                return;
            }

            // Gain exp from defeated enemy
            account.exp += enemy.isBoss ? 100 : 30;
            System.out.println("You defeated " + enemy.name + "!" + (enemy.isBoss ? " (Boss)" : "") + " +" + (enemy.isBoss ? "100" : "30") + " EXP");

            dp.enemiesDefeated = i + 1;
            account.progress.put(dungeonIndex, dp);

            // Boss defeated - summon shadow option
            if (enemy.isBoss) {
                while (true) {
                    System.out.print("Do you want to summon " + enemy.name + " to be your shadow monarch? (yes/no): ");
                    String input = sc.nextLine().trim().toLowerCase();
                    if (input.equals("yes")) {
                        account.shadows.add(enemy.name);
                        System.out.println(enemy.name + " added to your shadow list.");
                        break;
                    } else if (input.equals("no")) {
                        System.out.println("You chose not to summon the shadow.");
                        break;
                    } else {
                        System.out.println("Please answer yes or no.");
                    }
                }

                System.out.println("You need to close this gate to prevent any more monsters from escaping into the world.");
                System.out.println("Press Enter to close the gate...");
                sc.nextLine();

                dp.gateClosed = true;
                account.progress.put(dungeonIndex, dp);
                System.out.println(dungeonNames[dungeonIndex] + " gate closed!");
            }

            if (i < 3 && !enemy.isBoss) {
                System.out.println("\nDo you want to:");
                System.out.println("1. Continue to next enemy");
                System.out.println("2. Return to town and save progress");
                System.out.print("Choose (1 or 2): ");
                String choice = sc.nextLine();
                if (!choice.equals("1")) {
                    System.out.println("\nProgress saved. You defeated " + dp.enemiesDefeated + " enemies in " + dungeonNames[dungeonIndex] + ".");
                    return;
                }
            }
        }
    }

    static boolean fight(Enemy enemy) {
        while (enemy.hp > 0 && account.currentHP > 0) {
            System.out.printf("\nYour HP: %d | Enemy HP: %d\n", account.currentHP, enemy.hp);
            System.out.println("Choose an action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Shadow (" + shadowUses + ")");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                // Player attack damage random 15-25
                int damage = 15 + new Random().nextInt(11);
                enemy.hp -= damage;
                if (enemy.hp < 0) {
                    enemy.hp = 0;
                }
                System.out.println("You attack for " + damage + " damage.");
            } else if (choice.equals("2")) {
                if (shadowUses > 0 && !account.shadows.isEmpty()) {
                    shadowUses--;
                    // Shadow attack damage random 25-40
                    int damage = 25 + new Random().nextInt(16);
                    enemy.hp -= damage;
                    if (enemy.hp < 0) {
                        enemy.hp = 0;
                    }
                    System.out.println("Your shadow attacks for " + damage + " damage.");
                } else {
                    System.out.println("No shadows available to use or no shadow uses left.");
                    continue;
                }
            } else {
                System.out.println("Invalid input.");
                continue;
            }

            if (enemy.hp <= 0) {
                break;
            }

            // Enemy attack
            int enemyDamage = enemy.damage - new Random().nextInt(5);
            if (enemyDamage < 1) {
                enemyDamage = 1;
            }
            account.currentHP -= enemyDamage;
            if (account.currentHP < 0) {
                account.currentHP = 0;
            }
            System.out.println(enemy.name + " hits you for " + enemyDamage + " damage.");

            if (account.currentHP <= 0) {
                System.out.println("\n❌ You were defeated by " + enemy.name + "...");
                return false;
            }
        }
        return true;
    }
}
