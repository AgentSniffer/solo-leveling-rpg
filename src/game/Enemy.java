package game;

public class Enemy {
    String name;
    int hp;
    int maxHp;
    int damage;
    boolean isBoss;

    public Enemy(String name, int hp, int damage, boolean isBoss) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.damage = damage;
        this.isBoss = isBoss;
    }

    // Copy constructor
    public Enemy(Enemy source) {
        this.name = source.name;
        this.hp = source.maxHp; // Reset HP to max
        this.maxHp = source.maxHp;
        this.damage = source.damage;
        this.isBoss = source.isBoss;
    }
}
