package SoloLeveling.Races;

import SoloLeveling.Entity.Race;

public class Human extends Race {
    public Human() {
        name = "Human";
        baseStrength = 10;
        baseIntelligence = 10;
        baseDexterity = 10;
        baseConstitution = 10;

        healthMultiplier = 1.0;
        manaMultiplier = 1.0;
    }
}