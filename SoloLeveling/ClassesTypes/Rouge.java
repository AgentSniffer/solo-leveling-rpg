package SoloLeveling.ClassesTypes;

import SoloLeveling.Entity.ClassType;

public class Rogue extends ClassType {
    public Rogue() {
        name = "Rogue";
        bonusStrength = 1.0;
        bonusIntelligence = 1.0;
        bonusDexterity = 1.3;
        bonusConstitution = 0.9;
        healthPerLevel = 0.9;
        manaPerLevel = 0.9;
    }
}