package org.cs;

public class Space extends GameObject {

	/**
	* Constructor for default object
	*/
	public Space (GameManager manager) {
		super (-1, -1, 1, 1, NO_DIRECTION, STOP_STATE, 0, manager);
	}

	public Space (int x, int y, GameManager manager) {
		super (x, y, 1, 1, NO_DIRECTION, STOP_STATE, 0, manager);
	}

	/**
	* Sets space direction (disable)
	*/
	public void setDirection (int direction) {}

	/**
	* Hit object (disable)
	*/
	public int hit (int strength, GameObject object) {
		return 0;
	}

	/**
	* Chenge object position (disable)
	*/
	public void move () {}
	public void move (int direction) {}

	/**
	* Check object ability to move to default direction
	*/
	public boolean checkMove () {
		return false;
	}

	/**
	* Check object ability to move to specified direction
	*/
	public boolean checkMove (int direction) {
		return false;
	}

}

