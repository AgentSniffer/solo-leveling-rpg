package SoloLeveling.Entity;

public class ClassType {
    String name;

    int bonusStrength;
    int bonusIntelligence;
    int bonusDexterity;
    int bonusConstitution;

    double healthPerLevel;
    double manaPerLevel;

    public ClassType(String s1, int i1, int i2, int i3, int i4, double d1, double d2) {
        name = s1;
        bonusStrength = i1;
        bonusIntelligence = i2;
        bonusDexterity = i3;
        bonusConstitution = i4;
        healthPerLevel = d1;
        manaPerLevel = d2;
    }
}
