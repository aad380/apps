package org.cs;

public interface GameManager {
	public void start ();
	public void stop ();
	public void addObject (GameObject object);
	public void hideObject (GameObject object);
	public void showObject (GameObject object);
	public GameObject getObject (int x, int y);
	public void stateChanged (GameObject object);
	public void positionChanged (GameObject object, int oldX, int oldY);
	public void directionChanged (GameObject object, int oldDirection);
	public void killed (GameObject object);
	public void killed (GameObject object, GameObject killer);
	public GameSpace getGameSpace ();
}

