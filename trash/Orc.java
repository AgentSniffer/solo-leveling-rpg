package sololeveling.races;

import sololeveling.entities.Race;

public class Orc extends Race {
    public Orc() {
        name = "Orc";
        baseStrength = 14;
        baseIntelligence = 6;
        baseDexterity = 8;
        baseConstitution = 12;

        healthMultiplier = 1.2;
        manaMultiplier = 0.8;
    }
}