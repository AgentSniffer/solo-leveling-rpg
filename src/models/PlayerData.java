
class PlayerData {

    String playerName;
    int level;
    int exp;

    PlayerData(String name) {
        playerName = name;
        level = 1;
        exp = 0;
    }

    PlayerData(String name, int lvl, int xp) {
        playerName = name;
        level = lvl;
        exp = xp;
    }

    void gainExp(int amount) {
        exp += amount;
        if (exp >= 100) {
            level++;
            exp = 0;
            System.out.println("\nLevel up! Now level " + level);
        }
    }

    public String getStatus() {
        return "=== " + playerName + "'s Game ===\n"
                + "Level: " + level + "\n"
                + "Exp: " + exp + "/100\n";
    }
}
