package SoloLeveling.Races;

import SoloLeveling.Entity.Race;

public class NightElf extends Race {
    public NightElf() {
        name = "Night Elf";
        baseStrength = 9;
        baseIntelligence = 11;
        baseDexterity = 14;
        baseConstitution = 9;

        healthMultiplier = 0.95;
        manaMultiplier = 1.2;
    }
}