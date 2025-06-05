package model;

public class PlayerModel extends CharacterModel {

    public String playerClass;
    public String race;
    public InventoryModel inventory = new InventoryModel();

    public PlayerModel(String newName, String newPlayerClass, String newRace) {
        name = newName;
        playerClass = newPlayerClass;
        race = newRace;
        initializeStats();
    }

    private void initializeStats() {
        // Base stats based on class
        switch (playerClass) {
            case "Warrior" -> {
                strength = 60;
                endurance = 60;
                agility = 40;
                intelligence = 30;
                luck = 40;
                hp = 600;
                mp = 200;
            }
            case "Mage" -> {
                strength = 30;
                endurance = 40;
                agility = 50;
                intelligence = 70;
                luck = 50;
                hp = 400;
                mp = 600;
            }
            case "Rogue" -> {
                strength = 50;
                endurance = 40;
                agility = 70;
                intelligence = 50;
                luck = 60;
                hp = 500;
                mp = 300;
            }
            case "Archer" -> {
                strength = 50;
                endurance = 50;
                agility = 60;
                intelligence = 40;
                luck = 50;
                hp = 550;
                mp = 250;
            }
        }

        // Apply race modifiers
        switch (race) {
            case "Elf" -> {
                agility += 10;
                intelligence += 10;
                hp += 40;
                mp += 200;
            }
            case "Gnome" -> {
                intelligence += 20;
                luck += 10;
                hp += 40;
                mp += 100;
            }
            case "Orc" -> {
                strength += 10;
                endurance += 10;
                hp += 40;
                mp += 50;
            }
        }
    }
}
