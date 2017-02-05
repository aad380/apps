package org.cs.mazetanks;

public class NukeBulletBonus extends Bonus {

    public NukeBulletBonus(int x, int y, int count, GameManager manager) {
        super(x, y, 2, 2, count, manager);
    }

    public NukeBulletBonus(int x, int y, GameManager manager) {
        super(x, y, 2, 2, 1, manager);
    }

}

