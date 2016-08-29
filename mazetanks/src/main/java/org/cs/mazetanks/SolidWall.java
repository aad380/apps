package org.cs.mazetanks;

public class SolidWall extends Wall {

    public SolidWall(int x, int y, GameManager manager) {
        super(x, y, manager);
    }

    /**
     * Hit object (disable)
     */
    public int hit(int strength) {
        return 0;
    }

}

