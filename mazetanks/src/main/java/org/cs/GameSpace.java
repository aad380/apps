package org.cs;

public interface GameSpace {
	public void start ();
	public void stop ();
	public void setMaze (GameMaze maze);
	public void setMainObject (GameObject object);
	public int getWidth ();
	public int getHeight ();
	public boolean isSpace (int x, int y);
	public GameObject getObject (int x, int y);
	public void addObject (GameObject object);
	public void deleteObject (GameObject object);
	public void hideObject (GameObject object);
	public void showObject (GameObject object);
	public void positionChanged (GameObject object, int oldX, int oldY);
	public void directionChanged (GameObject object, int oldDirection);
}

