package org.cs.mazetanks;

import org.cs.mazetanks.player.Player;

class Tank extends Cannon implements GuidedDevice {

    protected static int[] backMap = new int[4];

    static {
        // init backMap
        backMap[UP_DIRECTION] = DOWN_DIRECTION;
        backMap[DOWN_DIRECTION] = UP_DIRECTION;
        backMap[RIGHT_DIRECTION] = LEFT_DIRECTION;
        backMap[LEFT_DIRECTION] = RIGHT_DIRECTION;
    }

    // variables for using on tank movement
    protected Bonus[] savedBonuses;
    protected Bullet[] savedBullets;
    protected int savedBonusesCounter = 0,
            savedBulletsCounter = 0;
    private int stepState = STOP;

    public Tank(int x, int y, int direction, GameManager manager, Player player) {
        super(x, y, 3, 3, direction, 100, 20, 10, BULLET, 3, manager, player);
        int maxSide = Math.max(width, height);
        savedBonuses = new Bonus[maxSide];
        savedBullets = new Bullet[maxSide];
    }

    /**
     * Move object in specified direction
     */
    public void move(int direction) {
        if (checkMove(direction)) {
            switch (direction) {
                case UP_DIRECTION:
                    --y;
                    saveBulletsAndBonuses(x, y, width, 1);
                    hideSavedBulletsAndBonuses();    // hook for VisualTank
                    manager.positionChanged(this, x, y + 1);
                    activateSavedBulletsAndBonuses();
                    return;
                case LEFT_DIRECTION:
                    --x;
                    saveBulletsAndBonuses(x, y, 1, height);
                    hideSavedBulletsAndBonuses();    // hook for VisualTank
                    manager.positionChanged(this, x + 1, y);
                    activateSavedBulletsAndBonuses();
                    return;
                case DOWN_DIRECTION:
                    ++y;
                    saveBulletsAndBonuses(x, y + height - 1, width, 1);
                    hideSavedBulletsAndBonuses();    // hook for VisualTank
                    manager.positionChanged(this, x, y - 1);
                    activateSavedBulletsAndBonuses();
                    return;
                case RIGHT_DIRECTION:
                    ++x;
                    saveBulletsAndBonuses(x + width - 1, y, 1, height);
                    hideSavedBulletsAndBonuses();    // hook for VisualTank
                    manager.positionChanged(this, x - 1, y);
                    activateSavedBulletsAndBonuses();
                    return;
            }
        }
        return;
    } // move

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
                    GameObject o = manager.getObject(x, y);
                    if (o instanceof Wall || o instanceof Tank) {
                        return false;
                    }
                }
                return true;
            case LEFT_DIRECTION:
                x = this.x - 1;
                lastY = this.y + height;
                for (y = this.y; y < lastY; ++y) {
                    GameObject o = manager.getObject(x, y);
                    if (o instanceof Wall || o instanceof Tank) {
                        return false;
                    }
                }
                return true;
            case DOWN_DIRECTION:
                y = this.y + height;
                lastX = this.x + width;
                for (x = this.x; x < lastX; ++x) {
                    GameObject o = manager.getObject(x, y);
                    if (o instanceof Wall || o instanceof Tank) {
                        return false;
                    }
                }
                return true;
            case RIGHT_DIRECTION:
                x = this.x + width;
                lastY = this.y + height;
                for (y = this.y; y < lastY; ++y) {
                    GameObject o = manager.getObject(x, y);
                    if (o instanceof Wall || o instanceof Tank) {
                        return false;
                    }
                }
                return true;
        }
        return false;
    } // checkMove

    private void saveBulletsAndBonuses(int x, int y, int width, int height) {
        int maxX = x + width,
                maxY = y + height;
        // clear arrays (locked Objects will be saved avtomaticaly)
        if (savedBulletsCounter != 0) {
            for (int i = 0; i < savedBulletsCounter; ++i) {
                savedBullets[i] = null;
            }
            savedBulletsCounter = 0;
        }
        if (savedBonusesCounter != 0) {
            for (int i = 0; i < savedBonusesCounter; ++i) {
                savedBonuses[i] = null;
            }
            savedBonusesCounter = 0;
        }
        // lock overloped objects
        for (int i = x; i < maxX; ++i) {
            for (int j = y; j < maxY; ++j) {
                GameObject o = manager.getObject(i, j);
                if (o.getState() != KILLED_STATE) {
                    if (o instanceof Bullet) {
                        savedBullets[savedBulletsCounter++] = (Bullet) o;
                    } else if (o instanceof Bonus) {
                        savedBonuses[savedBonusesCounter++] = (Bonus) o;
                    }
                }
            } // for j
        } // for i
    } // saveBulletsAndBonuses

    protected void hideSavedBulletsAndBonuses() {
        for (int i = 0; i < savedBulletsCounter; ++i) {
            manager.hideObject(savedBullets[i]);
        }
        for (int i = 0; i < savedBonusesCounter; ++i) {
            manager.hideObject(savedBonuses[i]);
        }
    } // hideSavedBulletsAndBonuses

    private void activateSavedBulletsAndBonuses() {
        for (int i = 0; i < savedBulletsCounter; ++i) {
            savedBullets[i].explode();
            if (state == KILLED_STATE) {
                manager.killed(this, savedBullets[i]);
                savedBullets[i] = null;
                return;
            }
            savedBullets[i] = null;
        }
        savedBulletsCounter = 0;
        for (int i = 0; i < savedBonusesCounter; ++i) {
            useBonus(savedBonuses[i]);
            savedBonuses[i].die(this);
            savedBonuses[i] = null;
        }
        savedBulletsCounter = 0;
    } // activateSavedBulletsAndBonuses

    private void useBonus(Bonus bonus) {
        if (bonus instanceof BulletBonus) {
            bullets += bonus.getCount();
            player.bulletsChanged(bullets);
        } else if (bonus instanceof BigBulletBonus) {
            bigBullets += bonus.getCount();
            player.bigBulletsChanged(bigBullets);
        } else if (bonus instanceof NukeBulletBonus) {
            nukeBullets += bonus.getCount();
            player.nukeBulletsChanged(nukeBullets);
        } else if (bonus instanceof ShieldBonus) {
            shield += bonus.getCount();
            player.shieldChanged(shield);
        }
    } // useBonus

// + GuidedDevice interface

    public void goForward() {
        if (state != KILLED_STATE) {
            stepState = FORWARD;
            state = STOP_STATE;
        }
    }

    public void goBackward() {
        if (state != KILLED_STATE) {
            stepState = BACKWARD;
            state = STOP_STATE;
        }
    }

    public void runForward() {
        if (state != KILLED_STATE) {
            stepState = FORWARD;
            state = MOVED_STATE;
        }
    }

    public void runBackward() {
        if (state != KILLED_STATE) {
            stepState = BACKWARD;
            state = MOVED_STATE;
        }
    }

    public void stop() {
        if (state != KILLED_STATE) {
            stepState = STOP;
            state = STOP_STATE;
        }
    }

    public int getMoveDirection() {
        return stepState;
    } // getMoveDirection

// - GuidedDevice interface

    public void step() {
        if (state == KILLED_STATE) {
            return;
        }
        player.step();        // hook for TankDriver
        if (state == KILLED_STATE) {
            return;
        }
        if (state == STOP_STATE) {
            switch (stepState) {
                case FORWARD:
                    move(direction);
                    stepState = STOP;
                    break;
                case BACKWARD:
                    move(backMap[direction]);
                    stepState = STOP;
            }
        } else {
            switch (stepState) {
                case FORWARD:
                    move(direction);
                    break;
                case BACKWARD:
                    move(backMap[direction]);
            }
        }
        if (state == KILLED_STATE) {
            return;
        }
        if (fireFlag) {
            doFire();
            fireFlag = false;
        }
    } // step

}

