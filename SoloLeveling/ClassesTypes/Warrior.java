package SoloLeveling.ClassesTypes;

import SoloLeveling.Entity.ClassType;

public class Warrior extends ClassType {
    public Warrior() {
        name = "Warrior";
        bonusStrength = 1.2;
        bonusIntelligence = 0.8;
        bonusDexterity = 1.0;
        bonusConstitution = 1.2;
        healthPerLevel = 1.2;
        manaPerLevel = 0.8;
    }
}