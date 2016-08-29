package org.cs.mazetanks;

public interface GameManager {
    void start();
    void stop();
    void addObject(GameObject object);
    void hideObject(GameObject object);
    void showObject(GameObject object);
    GameObject getObject(int x, int y);
    void stateChanged(GameObject object);
    void positionChanged(GameObject object, int oldX, int oldY);
    void directionChanged(GameObject object, int oldDirection);
    void killed(GameObject object);
    void killed(GameObject object, GameObject killer);
    GameSpace getGameSpace();
}

