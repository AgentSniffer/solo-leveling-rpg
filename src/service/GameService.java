package service;

import cli.GameCli;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import model.EnemyModel;
import model.ItemModel;
import model.PlayerModel;
import model.RaceModel;

public class GameService {

    public PlayerModel player;
    public static Scanner sc = new Scanner(System.in);
    public static Random random = new Random();
    public static boolean gameRunning = true;
    public static int currentArea = 1;

    public void loadGame(String username) {
        GameCli.loadingScreen(5);
        GameCli.clearScreen();

        // TODO: Load game data from database using the username
        // For now, we'll just create a new player as a placeholder
        // Replace this with actual database loading logic
        player = new PlayerModel(username, "Warrior", "Human");

        GameCli.clearScreen();
        System.out.println();
        GameCli.printBox("GAME LOADED\n\nWelcome back, " + username + "!\nYour adventure continues...");
        GameCli.pause();
        showOpeningScene();
    }

    public void startGame() {
        GameCli.loadingScreen(5);
        String username = GameCli.promptForInput("Enter username: ");
        String playerClass = selectClass();
        String race = selectRace();
        player = new PlayerModel(username, playerClass, race);
        showOpeningScene();

        while (gameRunning) {
            displayMainMenu();
        }
    }

    private void displayMainMenu() {
        GameCli.printBox("--- Main Menu ---\n1. Explore\n2. Check Stats\n3. View Inventory\n4. Rest (Heal)\n5. Quit Game");
        System.out.print(GameCli.YELLOW + "Choose an option: " + GameCli.RESET);

        String input = GameCli.promptForInput("> ");
        switch (input) {
            case "1" ->
                huntInDungeon();
            case "2" ->
                player.displayStats();
            case "3" ->
                player.viewInventory(sc);
            case "4" ->
                takeRest();
            case "5" -> {
                gameRunning = false;
                System.out.println(GameCli.GREEN + "Thanks for playing SoloLeveling!" + GameCli.RESET);
            }
            default ->
                System.out.println(GameCli.RED + "Invalid choice!" + GameCli.RESET);
        }
    }

    private String selectClass() {
        String[] classes = {"Warrior", "Mage", "Rogue", "Archer"};
        HashMap<String, String> classAscii = new HashMap<>();
        classAscii.put("Warrior", "  /\\\n /  \\\n|    |\n@----@");
        classAscii.put("Mage", "  ^\n / \\\n|   |\n \\_/");
        classAscii.put("Rogue", "  O\n /|\\\n / \\\n^^^^");
        classAscii.put("Archer", ">->\n(' )\n / \\\n^^^^");

        while (true) {
            String menu = "Choose your class:\n\n";
            for (int i = 0; i < classes.length; i++) {
                menu += (i + 1) + ". " + classes[i] + "\n";
            }

            GameCli.clearScreen();
            System.out.println();
            System.out.println(classAscii.get(classes[0]) + "\n");
            GameCli.printBox(menu.trim());

            String input = GameCli.promptForInput("> ");

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    String selected = classes[choice - 1];

                    GameCli.clearScreen();
                    System.out.println(classAscii.get(selected));
                    System.out.println("\nClass: " + selected);
                    System.out.println("Skills: " + getClassSkills(selected));

                    if (confirmSelection("class")) {
                        return selected;
                    }
                } else {
                    System.out.println("\nInvalid choice! Please enter 1-4");
                    GameCli.pause();
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please enter 1-4");
                GameCli.pause();
            }
        }
    }




    private void showOpeningScene() {
        System.out.println(GameCli.CYAN + "\n> Shadow Dungeon Entrance");
        System.out.println("HP: " + player.hp + "/" + player.maxHp + "   MP: " + player.mp + "/" + player.maxMp);
        System.out.println("> Welcome to Solo Leveling! You are a level 1 " + player.race + ".");
        System.out.println(GameCli.GREEN + "Game started successfully!" + GameCli.RESET);

        // Give starting items
        player.inventory.addItem(new ItemModel("Health Potion", "Restores 100 HP", "consumable", 100));
        player.inventory.addItem(new ItemModel("Mana Potion", "Restores 50 MP", "consumable", 50));
        player.inventory.addItem(new ItemModel("Rusty Dagger", "Basic weapon", "weapon", 5));
    }

    private void huntInDungeon() {
        System.out.println(GameCli.CYAN + "\n> Hunting in Floor " + currentArea + "...");

        // 60% chance of monster encounter
        if (random.nextInt(100) < 60) {
            fightMonster();
        } else {
            // 40% chance of finding loot or nothing
            if (random.nextInt(100) < 50) {
                discoverLoot();
            } else {
                System.out.println(GameCli.BLUE + "You search the floor but find nothing useful." + GameCli.RESET);
            }
        }

        // Small chance to access next floor
        if (random.nextInt(100) < 20) {
            currentArea++;
            System.out.println(GameCli.PURPLE + "> You've unlocked Floor " + currentArea + "!" + GameCli.RESET);
        }
    }

    private void fightMonster() {
        // Spawn monster based on current floor
        String[] monsterTypes = {"Shadow Beast", "Corrupted Spider", "Iron Golem", "Dark Wolf", "Poison Lizard", "Rogue Hunter"};
        String monsterType = monsterTypes[random.nextInt(monsterTypes.length)];
        EnemyModel monster = new EnemyModel(monsterType, currentArea);

        System.out.println(GameCli.RED + "A " + monsterType + " (Lvl " + monster.level + ") blocks your path!" + GameCli.RESET);

        boolean battleActive = true;
        while (battleActive && player.hp > 0 && monster.hp > 0) {
            System.out.println("\n" + GameCli.CYAN + "Your HP: " + player.hp + "/" + player.maxHp
                    + "  |  Monster HP: " + monster.hp + "/" + monster.maxHp + GameCli.RESET);
            System.out.println("1. Attack");
            System.out.println("2. Use Skill");
            System.out.println("3. Use Item");
            System.out.println("4. Try to Retreat");
            System.out.print(GameCli.YELLOW + "Choose action: " + GameCli.RESET);

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> { // Attack
                    int playerDamage = player.calculateDamage();
                    monster.hp -= playerDamage;
                    System.out.println(GameCli.GREEN + "You strike the " + monsterType + " for " + playerDamage + " damage!" + GameCli.RESET);
                }
                case "2" -> { // Use Skill
                    if (player.useSkill(monster)) {
                        // Skill executed successfully
                    } else {
                        continue;
                    }
                }
                case "3" -> { // Use Item
                    player.viewInventory(sc);
                    System.out.print(GameCli.YELLOW + "Choose item to use (0 to cancel): " + GameCli.RESET);
                    try {
                        int itemIndex = Integer.parseInt(sc.nextLine());
                        if (itemIndex == 0) {
                            continue;
                        }
                        player.useItem(itemIndex - 1);
                    } catch (Exception e) {
                        System.out.println(GameCli.RED + "Invalid selection!" + GameCli.RESET);
                        continue;
                    }
                }
                case "4" -> { // Retreat
                    if (random.nextInt(100) < 40) { // 40% success rate
                        System.out.println(GameCli.BLUE + "You successfully retreated from battle!" + GameCli.RESET);
                        return;
                    }
                    System.out.println(GameCli.RED + "Retreat failed!" + GameCli.RESET);
                }
                default -> {
                    System.out.println(GameCli.RED + "Invalid choice!" + GameCli.RESET);
                    continue;
                }
            }

            // Monster attacks back
            if (monster.hp > 0) {
                int monsterDamage = monster.calculateDamage();
                player.hp -= monsterDamage;
                System.out.println(GameCli.RED + "The " + monsterType + " attacks you for " + monsterDamage + " damage!" + GameCli.RESET);
            }
        }

        if (player.hp <= 0) {
            System.out.println(GameCli.RED + "You have been slain..." + GameCli.RESET);
            gameRunning = false;
        } else if (monster.hp <= 0) {
            System.out.println(GameCli.GREEN + "You defeated the " + monsterType + "!" + GameCli.RESET);
            collectRewards(monster);
        }
    }

    private void collectRewards(EnemyModel monster) {
        int expEarned = monster.level * 25;
        int goldEarned = monster.level * 10 + random.nextInt(20);

        System.out.println(GameCli.YELLOW + "You earned " + expEarned + " EXP and " + goldEarned + " Gold!" + GameCli.RESET);
        player.exp += expEarned;
        player.gold += goldEarned;

        // Random loot drop (30% chance)
        if (random.nextInt(100) < 30) {
            String[] lootItems = {"Health Potion", "Mana Potion", "Shadow Armor", "Steel Blade", "Magic Crystal"};
            String lootName = lootItems[random.nextInt(lootItems.length)];
            ItemModel droppedItem = new ItemModel(lootName, "A valuable item", "misc", 0);
            player.inventory.addItem(droppedItem);
            System.out.println(GameCli.GREEN + "The monster dropped a " + lootName + "!" + GameCli.RESET);
        }

        // Check for level up
        if (player.exp >= player.nextLevelExp()) {
            player.levelUp();
            System.out.println(GameCli.PURPLE + "\n*** LEVEL UP! You are now Level " + player.level + "! ***" + GameCli.RESET);
        }
    }

    private void discoverLoot() {
        String[] foundItems = {"Health Potion", "Mana Potion", "Gold Coin", "Ancient Artifact", "Magic Herb"};
        String foundItemName = foundItems[random.nextInt(foundItems.length)];
        ItemModel discoveredItem = new ItemModel(foundItemName, "A useful item", "misc", 0);

        System.out.println(GameCli.GREEN + "You discovered a " + foundItemName + "!" + GameCli.RESET);
        player.inventory.addItem(discoveredItem);
    }

    private void takeRest() {
        int healingAmount = player.maxHp / 2;
        player.hp = Math.min(player.maxHp, player.hp + healingAmount);
        player.mp = player.maxMp;

        System.out.println(GameCli.BLUE + "You rest and recover " + healingAmount + " HP and full MP." + GameCli.RESET);
        System.out.println("HP: " + player.hp + "/" + player.maxHp + "   MP: " + player.mp + "/" + player.maxMp);
    }











    private String getClassSkills(String playerClass) {
        return switch (playerClass) {
            case "Warrior" ->
                "Slash, Block, Taunt";
            case "Mage" ->
                "Fireball, Ice Shield, Teleport";
            case "Rogue" ->
                "Backstab, Stealth, Poison";
            case "Archer" ->
                "Precision Shot, Rapid Fire, Evade";
            default ->
                "Basic Attack";
        };
    }

    private String selectRace() {
        HashMap<String, RaceModel> races = new HashMap<>() {
            {
                put("Human", new RaceModel("Human", "Humans are adaptable...", new int[]{50, 50, 50, 50, 50}, 0, 0, "No special resistances"));
                put("Elf", new RaceModel("Elf", "Elves are pointy-eared...", new int[]{40, 30, 50, 50, 40}, 16, 8, "Immune to poisons"));
                put("Gnome", new RaceModel("Gnome", "Gnomes are small...", new int[]{30, 40, 40, 60, 50}, 10, 20, "Resistant to illusions"));
                put("Orc", new RaceModel("Orc", "Orcs are strong...", new int[]{60, 60, 40, 30, 30}, 30, 5, "Berserker rage"));
            }
        };

        return selectRace(races);
    }

    private String selectRace(HashMap<String, RaceModel> races) {
        while (true) {
            String raceMenu = "Choose your race:\n\n";
            int index = 1;
            for (String raceName : races.keySet()) {
                raceMenu += index + ". " + raceName + "\n";
                index++;
            }

            GameCli.clearScreen();
            System.out.println();
            GameCli.printBox(raceMenu.trim());

            String input = GameCli.promptForInput("> ");

            try {
                int choice = Integer.parseInt(input);

                if (choice < 1 || choice > races.size()) {
                    System.out.println("\nInvalid choice! Please enter 1-" + races.size());
                    GameCli.pause();
                    continue;
                }

                String selected = null;
                int currentIndex = 1;
                for (String raceName : races.keySet()) {
                    if (currentIndex == choice) {
                        selected = raceName;
                        break;
                    }
                    currentIndex++;
                }

                RaceModel race = races.get(selected);
                String raceDetails = race.lore + "\n\n" + race.getStatsAsString();

                GameCli.clearScreen();
                System.out.println();
                GameCli.printBox(raceDetails);

                if (confirmSelection("race")) {
                    return selected;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please enter a number.");
                GameCli.pause();
            }
        }
    }

    private boolean confirmSelection(String type) {
        while (true) {
            String input = GameCli.promptForInput("Is this your " + type + "? (yes/no): ");

            if (input.equals("yes")) {
                return true;
            }
            if (input.equals("no")) {
                return false;
            }

            System.out.println("\nPlease enter 'yes' or 'no'");
            GameCli.pause();
        }
    }

}
