package org.cs.mazetanks;

public class Wall extends GameObject {

    public Wall(int x, int y, GameManager manager) {
        super(x, y, 1, 1, NO_DIRECTION, STOP_STATE, 10, manager);
    }

    /**
     * Sets wall direction (disable)
     */
    public void setDirection(int direction) {
    }

    /**
     * Chenge object position (disable space mooving)
     */
    public void move() {
    }

    public void move(int direction) {
    }

    public boolean isSolid() {
        return shield > 5;
    }

    /**
     * Check object ability to move to default direction
     */
    public boolean checkMove() {
        return false;
    }

    /**
     * Check object ability to move to specified direction
     */
    public boolean checkMove(int direction) {
        return false;
    }

}

