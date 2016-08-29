package org.cs.mazetanks;

public interface GameSpace {
    void start();
    void stop();
    void setMaze(GameMaze maze);
    void setMainObject(GameObject object);
    int getWidth();
    int getHeight();
    boolean isSpace(int x, int y);
    GameObject getObject(int x, int y);
    void addObject(GameObject object);
    void deleteObject(GameObject object);
    void hideObject(GameObject object);
    void showObject(GameObject object);
    void positionChanged(GameObject object, int oldX, int oldY);
    void directionChanged(GameObject object, int oldDirection);
}

