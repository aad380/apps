package org.cs.mazetanks;

public abstract class Bonus extends GameObject {

    private int count;

    protected Bonus(
            int x, int y, int width, int height,
            int count,
            GameManager manager
    ) {
        super(x, y, width, height, NO_DIRECTION, STOP_STATE, 0, manager);
        this.count = count;
    }

    public final int getCount() {
        return count;
    }

}

