package org.cs.mazetanks;

public class BulletBonus extends Bonus {

    public BulletBonus(int x, int y, int count, GameManager manager) {
        super(x, y, 2, 2, count, manager);
    }

    public BulletBonus(int x, int y, GameManager manager) {
        super(x, y, 2, 2, 10, manager);
    }

}

