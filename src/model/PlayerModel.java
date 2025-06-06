package model;

import java.util.Scanner;

public class PlayerModel extends CharacterModel {

    public String playerClass;
    public String race;
    public InventoryModel inventory = new InventoryModel();
    public int skillCooldown = 0;

    public PlayerModel(String newName, String newPlayerClass, String newRace) {
        name = newName;
        playerClass = newPlayerClass;
        race = newRace;
        initializeStats();
        gold = 100;
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

    public void displayStats() {
        System.out.println("\n--- Character Stats ---");
        System.out.println("Name: " + name);
        System.out.println("Race: " + race);
        System.out.println("Class: " + playerClass);
        System.out.println("Level: " + level);
        System.out.println("EXP: " + exp + "/" + nextLevelExp());
        System.out.println("Gold: " + gold);
        System.out.println("\nAttributes:");
        System.out.println("Strength: " + strength);
        System.out.println("Endurance: " + endurance);
        System.out.println("Agility: " + agility);
        System.out.println("Intelligence: " + intelligence);
        System.out.println("Luck: " + luck);
        System.out.println("\nCombat Stats:");
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("MP: " + mp + "/" + maxMp);
        System.out.println("Damage: " + calculateDamage() + "-" + (calculateDamage() + 4));
    }

    public void viewInventory(Scanner sc) {
        System.out.println("\n--- Inventory ---");
        System.out.println("Gold: " + gold);

        if (inventory.items.isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }

        int index = 1;
        for (ItemModel item : inventory.items.values()) {
            System.out.println(index++ + ". " + item.name + " - " + item.description);
        }
    }

    public boolean useSkill(EnemyModel enemy) {
        if (skillCooldown > 0) {
            System.out.println("Skill is on cooldown! (" + skillCooldown + " turns remaining)");
            return false;
        }

        int mpCost = 30;
        if (mp < mpCost) {
            System.out.println("Not enough MP!");
            return false;
        }

        mp -= mpCost;
        int damage = calculateDamage() * 2;
        enemy.hp -= damage;
        skillCooldown = 3;

        System.out.println("You use a powerful skill on the enemy for " + damage + " damage!");
        return true;
    }

    public void useItem(int index) {
        if (index < 0 || index >= inventory.items.size()) {
            System.out.println("Invalid item choice!");
            return;
        }

        ItemModel item = (ItemModel) inventory.items.values().toArray()[index];
        if (item.type.equals("consumable")) {
            if (item.name.contains("Health")) {
                hp = Math.min(maxHp, hp + item.value);
                System.out.println("Restored " + item.value + " HP!");
            } else if (item.name.contains("Mana")) {
                mp = Math.min(maxMp, mp + item.value);
                System.out.println("Restored " + item.value + " MP!");
            }
            inventory.removeItem(item.name);
        } else {
            System.out.println("You use the " + item.name);
        }
    }

    public void levelUp() {
        level++;
        exp = 0;

        // Increase stats
        strength += 3;
        endurance += 3;
        agility += 2;
        intelligence += 2;
        luck += 1;

        // Increase HP/MP
        maxHp += 50;
        maxMp += 30;
        hp = maxHp;
        mp = maxMp;
    }
}
