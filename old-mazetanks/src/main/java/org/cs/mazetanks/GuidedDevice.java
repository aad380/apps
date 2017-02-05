package org.cs.mazetanks;

public interface GuidedDevice {
    int BULLET = 0,        // for currentWeapon
            BIG_BULLET = 1,
            NUKE_BULLET = 2;
    int FORWARD = 1,        // for stepState
            STOP = 0,
            BACKWARD = -1;
    void turnLeft();
    void turnRight();
    void goForward();
    void goBackward();
    void runForward();
    void runBackward();
    void stop();
    void fire();
    void selectBullet();
    void selectBigBullet();
    void selectNukeBullet();
    int getCurrentWeapon();
    int getMoveDirection();
    int getBullets();
    int getBigBullets();
    int getNukeBullets();
    int getShield();
    int getState();            // from GameObject, Tank, Cannon, etc
    boolean checkMove();        // from GameObject, Tank, Cannon, etc
    boolean checkMove(int direction);    // from GameObject, Tank, Cannon, etc
}

