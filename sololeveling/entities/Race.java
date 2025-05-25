package sololeveling.entities;

public class Race {
    String name;

    int baseStrength;
    int baseIntelligence;
    int baseDexterity;
    int baseConstitution;

    double healthMultiplier;
    double manaMultiplier;

    public Race(String s1, int i1, int i2, int i3, int i4, double d1, double d2) {
        name = s1;
        baseStrength = i1;
        baseIntelligence = i2;
        baseDexterity = i3;
        baseConstitution = i4;
        healthMultiplier = d1;
        manaMultiplier = d2;
    }
}
