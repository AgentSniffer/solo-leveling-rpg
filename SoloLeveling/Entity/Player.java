package SoloLeveling.Entity;

import SoloLeveling.Utils.DateUtil;

public class Player {
    private String name;
    private String race;
    private String gender;
    private String classType;

    private int level;
    private int experience;
    private int gold;

    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;

    private int strength;
    private int intelligence;
    private int dexterity;
    private int constitution;

    private int physicalDefense;
    private int magicDefense;

    private String createdAt;
    private String lastPlayedAt;
        
    Player(String s1, String s2, String s3, String s4, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, String s5, String s6){
        name = s1;
        race = s2;
        gender = s3;
        classType = s4;

        level = i1;
        experience = i2;
        gold = i3;

        health = i4;
        maxHealth = i5;
        mana = i6;
        maxMana = i7;

        strength = i8;
        intelligence = i9;
        dexterity = i10;
        constitution = i11;

        physicalDefense = i12;
        magicDefense = i13;

        createdAt = s5;
        lastPlayedAt = s6;
    }   
     
    public void displayPlayerInfo() {
        System.out.println("Name: " + name);
        System.out.println("Race: " + race);
        System.out.println("Gender: " + gender);
        System.out.println("Class Type: " + classType);
        System.out.println("Level: " + level);
        System.out.println("Experience: " + experience);
        System.out.println("Gold: " + gold);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Mana: " + mana + "/" + maxMana);
        System.out.println("Strength: " + strength);
        System.out.println("Intelligence: " + intelligence);
        System.out.println("Dexterity: " + dexterity);
        System.out.println("Constitution: " + constitution);
        System.out.println("Physical Defense: " + physicalDefense);
        System.out.println("Magic Defense: " + magicDefense);
        System.out.println("Created At: " + createdAt);
        System.out.println("Last Played At: " + lastPlayedAt);
    }

    public void updateLastPlayed() {
        lastPlayedAt = DateUtil.getCurrentDate() + " " + DateUtil.getCurrentTime();
    }

    // // get methods for debugging 
    // public String getName() {
    // return name;
    // }

    // public String getRace() {
    //     return race;
    // }

    // public String getGender() {
    //     return gender;
    // }

    // public String getClassType() {
    //     return classType;
    // }

    // public int getLevel() {
    //     return level;
    // }

    // public int getExperience() {
    //     return experience;
    // }

    // public int getGold() {
    //     return gold;
    // }

    // public int getHealth() {
    //     return health;
    // }

    // public int getMaxHealth() {
    //     return maxHealth;
    // }

    // public int getMana() {
    //     return mana;
    // }

    // public int getMaxMana() {
    //     return maxMana;
    // }

    // public int getStrength() {
    //     return strength;
    // }

    // public int getIntelligence() {
    //     return intelligence;
    // }

    // public int getDexterity() {
    //     return dexterity;
    // }

    // public int getConstitution() {
    //     return constitution;
    // }

    // public int getPhysicalDefense() {
    //     return physicalDefense;
    // }

    // public int getMagicDefense() {
    //     return magicDefense;
    // }

    // public String getCreatedAt() {
    //     return createdAt;
    // }

    // public String getLastPlayedAt() {
    //     return lastPlayedAt;
    // }

    // private ClassType getClassType(String classType) {
    //     switch (classType.toLowerCase()) {
    //         case "warrior":
    //             return new Warrior();
    //         case "paladin":
    //             return new Paladin();
    //         case "mage":
    //             return new Mage();
    //         case "Rogue":
    //             return new Rogue();
    //     }
    // }

    // private Race getRace(String race) {
    //     switch (race.toLowerCase()) {
    //         case "human":
    //             return new Human();
    //         case "orc":
    //             return new Orc();
    //         case "undead":
    //             return new Undead();
    //         case "night elf":
    //             return new NightElf();
    //     }
    // }
}
