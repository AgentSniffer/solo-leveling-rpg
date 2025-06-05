package model;

public class RaceModel {

    public String name;
    public String lore;
    public int[] baseStats; // [strength, endurance, agility, intelligence, luck]
    public int hpMod;
    public int mpMod;
    public String effects;

    public RaceModel(String newName, String newLore, int[] newBaseStats, int newHpMod, int newMpMod, String newEffects) {
        name = newName;
        lore = newLore;
        baseStats = newBaseStats;
        hpMod = newHpMod;
        mpMod = newMpMod;
        effects = newEffects;
    }

    public String getStatsAsString() {
        return "Stats:\n"      +
               "Strength: "    + baseStats[0] + "\n" +
               "Endurance: "   + baseStats[1] + "\n" +
               "Agility: "     + baseStats[2] + "\n" +
               "Intelligence:" + baseStats[3] + "\n" +
               "Luck: "        + baseStats[4] + "\n\n" +
               "Effects:\n- "  + effects + "\n" +
               "- HP: +"       + hpMod + ", MP: +" + mpMod;
    }
}
