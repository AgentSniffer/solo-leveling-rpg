package sololeveling.classtypes;

import sololeveling.entities.ClassType;

public class Mage extends ClassType {
    public Mage() {
        name = "Mage";
        bonusStrength = 0.7;
        bonusIntelligence = 1.4;
        bonusDexterity = 1.0;
        bonusConstitution = 0.8;
        healthPerLevel = 0.8;
        manaPerLevel = 1.4;
    }
}