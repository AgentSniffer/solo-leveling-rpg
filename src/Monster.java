
import java.util.Random;

// Simple monster class - handles monster stuff
public class Monster {

    String name;
    int hp;
    int strength;
    Random rand = new Random();

    public Monster(String monsterName, int level) {  // random monster stats
        name = monsterName;
        hp = 40 + (level * 15);
        strength = 8 + (level * 5);
    }

    // attack method
    public void attackPlayer(Player hero) {
        int damage = strength + rand.nextInt(10);
        System.out.println("👹 " + name + " attacks for " + damage + " damage!");
        hero.takeDamage(damage);
    }

    public boolean stillAlive() {
        return hp > 0;
    }

    // method to show monster info
    public void showMonster() {
        System.out.println("😈 A scary " + name + " appears!");
        System.out.println("👹 HP: " + hp + " | Strength: " + strength);
    }

    public void createMonster(String monsterName, int level) {
        name = monsterName;
        hp = 40 + (level * 15);
        strength = 8 + (level * 5);
    }
}
