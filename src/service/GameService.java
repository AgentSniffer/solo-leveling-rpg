package service;

import cli.GameCli;
import java.util.HashMap;
import java.util.Scanner;
import model.PlayerModel;
import model.RaceModel;

public class GameService {

    public PlayerModel player;
    public static Scanner sc = new Scanner(System.in);

    public void startGame() {
        String username = GameCli.promptForInput("Enter username: ");
        String playerClass = selectClass();
        String race = selectRace();
        player = new PlayerModel(username, playerClass, race);
        displayStartingScene();
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

    private String getClassSkills(String playerClass) {
        return switch (playerClass) {
            case "Warrior" -> "Slash, Block, Taunt";
            case "Mage" -> "Fireball, Ice Shield, Teleport";
            case "Rogue" -> "Backstab, Stealth, Poison";
            case "Archer" -> "Precision Shot, Rapid Fire, Evade";
            default -> "Basic Attack";
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

    private void displayStartingScene() {
        System.out.println("\n> Olden Castle Town");
        System.out.println("HP: " + player.hp + "/" + player.hp + "   MP: " + player.mp + "/" + player.mp);
        System.out.println("> Welcome to OldenRPG! You are a level 1 " + player.race + ".");
        System.out.println("\nGame started successfully!");
    }
}