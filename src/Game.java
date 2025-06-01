import java.util.Random;

// Simple Game class - runs the game
public class Game {
    static Player currentPlayer;
    static Random rand = new Random();


    // ─────────────────────────────────────────────────────────────
    // ▼ Where the game starts
    // ─────────────────────────────────────────────────────────────
    
    public static void startGame() {
        String heroName = inputHeroName();
        createPlayer(heroName);

        boolean inGame = true;
        while (inGame) {
            int choice = UI.getMenuInput(4, Messages.GAME_MENU_OPTIONS);  // Already handles invalid input internally
            inGame = handleGameMenuChoice(choice);
        }
    }

    public static void goToDungeon() {
        System.out.println("\n🏰 Entering dungeon...");
        Monster enemy = makeRandomMonster();
        enemy.showMonster();
        
        boolean stillFighting = true;
        while (stillFighting) {
            // Check if both are alive first
            if (!currentPlayer.stillAlive()) {
                break;
            }
            if (!enemy.stillAlive()) {
                break;
            }
            
            System.out.println("\n⚔️ BATTLE TIME! ⚔️");
            System.out.println("Your HP: " + currentPlayer.hp + " | " + enemy.name + " HP: " + enemy.hp);
            System.out.println("1. Attack the monster");
            System.out.println("2. Run away");
            
            int choice = UI.getMenuInput(2);
            
            if (choice == 1) {
                // Player attacks first
                currentPlayer.attack(enemy);
                // Check if monster died
                if (!enemy.stillAlive()) {
                    System.out.println("\n🎉 You killed the " + enemy.name + "!");
                    int xpReward = 30 + (currentPlayer.level * 8);
                    currentPlayer.gainXP(xpReward);
                    stillFighting = false;
                } else {
                    // Monster attacks back
                    enemy.attackPlayer(currentPlayer);
                }
            } else if (choice == 2) {
                System.out.println("🏃 You run away from the " + enemy.name + "!");
                stillFighting = false;
            }
        }
        
        // Check if player died after battle
        if (!currentPlayer.stillAlive()) {
            System.out.println("\n💀 " + currentPlayer.name + " died! Returning to main menu...");
            Display.delay(3);
        }
    }

    public static void createPlayer(String heroName) {
        currentPlayer = new Player(heroName);
    }

    public static Monster makeRandomMonster() {
        String[] monsterTypes = {"Goblin", "Orc", "Giant Spider", "Skeleton", "Troll"};
        
        int randomNumber = rand.nextInt(monsterTypes.length);
        String pickedMonster = monsterTypes[randomNumber];

        Monster Monster1 = new Monster(pickedMonster, currentPlayer.level);

        return Monster1;
    }

    public static String inputHeroName() {
        String heroName = UI.getHeroName();
        return heroName;
    }

    // ─────────────────────────────────────────────────────────────
    // ▼ Handle Game Menu Choices
    // ─────────────────────────────────────────────────────────────
    public static boolean handleGameMenuChoice(int choice) {
        Display.clear();
        switch (choice) {
            case 1:
                System.out.println("Starting battle...");
                Display.delay(0.5);
                Display.clear();
                goToDungeon();
                Display.delay(2);
                return true;
            case 2:
                System.out.println("Opening inventory...");
                Display.delay(2);
                return true;
            case 3:
                System.out.println("Viewing character stats...");
                if (currentPlayer != null) {
                    currentPlayer.showMyStats();
                }
                Display.delay(2);
                UI.waitForEnter();
                Display.clear();
                return true;
            case 4:
                System.out.println("Returning to main menu...");
                Display.delay(2);
                return false;
            default:
                return true;
        }
    }
}