package org.cs.mazetanks;

import org.cs.mazetanks.player.Player;

/**
 * Base class for game objects (abstract)
 *
 * @author Alexander Dudarenko
 */
public abstract class GameObject {

    public final static int NO_DIRECTION = -1,
            UP_DIRECTION = 0,
            RIGHT_DIRECTION = 1,
            DOWN_DIRECTION = 2,
            LEFT_DIRECTION = 3,

    KILLED_STATE = 0,
            STOP_STATE = 1,
            MOVED_STATE = 2;

    protected int x,        // horizontal coordinate
            y,        // vertical coordinate
            width,        // object width in cells
            height,        // object height in cells
            direction,    // object direction
            state,        // object state
            shield;        // current object shield

    protected GameManager manager;    // all objects manager (interface)
    protected Player player;        // object's owner (interface)

    /**
     * Create GameObject
     */
    protected GameObject(
            int x, int y, int width, int height, int direction, int state, int shield,
            GameManager manager, Player player
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.state = state;
        this.shield = shield;
        this.manager = manager;
        this.player = player;
    }

    /**
     * Create GameObject (owner is not specified)
     */
    protected GameObject(
            int x, int y, int width, int height, int direction, int state, int shield,
            GameManager manager
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.state = state;
        this.shield = shield;
        this.manager = manager;
        this.player = null;
    }

    protected static int getNextX(int x, int direction) {
        switch (direction) {
            case LEFT_DIRECTION:
                return x - 1;
            case RIGHT_DIRECTION:
                return x + 1;
        }
        return x;
    } // getNextX

    protected static int getNextY(int y, int direction) {
        switch (direction) {
            case UP_DIRECTION:
                return y - 1;
            case DOWN_DIRECTION:
                return y + 1;
        }
        return y;
    } // getNextY

    /**
     * Gets player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets horisontal coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets horisontal coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets vertical coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets vertical coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets width in cells
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height in cells
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets object direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets object direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Gets object state
     */
    public int getState() {
        return state;
    }

    /**
     * Sets object state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Sets positions
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Hit object
     */
    public int hit(int strength, GameObject object) {
        if (state != KILLED_STATE) {
            shield -= strength;
            if (shield < 0) {
                state = KILLED_STATE;
                if (manager != null) {
                    manager.killed(this, object);
                }
            }
        }
        return shield;
    }

    public void die(GameObject object) {
        if (state != KILLED_STATE) {
            state = KILLED_STATE;
            if (manager != null) {
                manager.killed(this, object);
            }
        }
    }

    /**
     * Move object in default direction
     */
    public void move() {
        move(direction);
    }

    /**
     * Move object in specified direction
     */
    public void move(int direction) {
        if (state == MOVED_STATE && checkMove(direction)) {
            switch (direction) {
                case UP_DIRECTION:
                    --y;
                    manager.positionChanged(this, x, y + 1);
                    return;
                case LEFT_DIRECTION:
                    --x;
                    manager.positionChanged(this, x + 1, y);
                    return;
                case DOWN_DIRECTION:
                    ++y;
                    manager.positionChanged(this, x, y - 1);
                    return;
                case RIGHT_DIRECTION:
                    ++x;
                    manager.positionChanged(this, x - 1, y);
                    return;
            }
        }
    } // move

    /**
     * Check object ability to move to default direction
     */
    public boolean checkMove() {
        return checkMove(direction);
    }

    /**
     * Check object ability to move to specified direction
     */
    public boolean checkMove(int direction) {
        int x, y, lastX, lastY;
        switch (direction) {
            case UP_DIRECTION:
                y = this.y - 1;
                lastX = this.x + width;
                for (x = this.x; x < lastX; ++x) {
                    if (!(manager.getObject(x, y) instanceof Space)) {
                        return false;
                    }
                }
                return true;
            case LEFT_DIRECTION:
                x = this.x - 1;
                lastY = this.y + height;
                for (y = this.y; y < lastY; ++y) {
                    if (!(manager.getObject(x, y) instanceof Space)) {
                        return false;
                    }
                }
                return true;
            case DOWN_DIRECTION:
                y = this.y + height;
                lastX = this.x + width;
                for (x = this.x; x < lastX; ++x) {
                    if (!(manager.getObject(x, y) instanceof Space)) {
                        return false;
                    }
                }
                return true;
            case RIGHT_DIRECTION:
                x = this.x + width;
                lastY = this.y + height;
                for (y = this.y; y < lastY; ++y) {
                    if (!(manager.getObject(x, y) instanceof Space)) {
                        return false;
                    }
                }
                return true;
        }
        return false;
    } // checkMove

    public void step() {
        if (state == MOVED_STATE) {
            move();
        }
    } // step
}

