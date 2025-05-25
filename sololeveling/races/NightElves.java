package sololeveling.races;

import sololeveling.entities.Race;

public class NightElves extends Race {
    public NightElves() {
        name = "Night Elf";
        baseStrength = 9;
        baseIntelligence = 11;
        baseDexterity = 14;
        baseConstitution = 9;

        healthMultiplier = 0.95;
        manaMultiplier = 1.2;
    }
}