package sololeveling.races;

import sololeveling.entities.Race;

public class Undead extends Race {
    public Undead() {
        name = "Undead";
        baseStrength = 8;
        baseIntelligence = 12;
        baseDexterity = 10;
        baseConstitution = 10;

        healthMultiplier = 0.9;
        manaMultiplier = 1.1;
    }
}