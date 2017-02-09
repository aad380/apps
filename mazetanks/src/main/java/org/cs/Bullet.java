package org.cs;

class Bullet extends GameObject {
	protected int	strength = 1;

	public Bullet (int x, int y, int direction, GameManager manager, Player player) {
		super (x, y, 1, 1, direction, MOVED_STATE, 0, manager, player);
		// if created on target
		if (hitLocalTargets ()) {
			state = KILLED_STATE;
		}
	}

	/**
	* Constructor for child objects with not 1x1 geometry
	*/
	protected Bullet (int x, int y, int width, int height, int direction, int strength, GameManager manager, Player player) {
		super (x, y, width, height, direction, MOVED_STATE, 0, manager, player);
		this.strength = strength;
		// if created on target
		if (hitLocalTargets ()) {
			state = KILLED_STATE;
		}
	}

	public void step () {
		if (state != KILLED_STATE) {
			move ();
		}
	} // step

	/**
	* Hit overloped objects at bullet create period,
	* Good only for 1x1 bullets, must be overloaded for another bullet geometry
	*/
	protected boolean hitLocalTargets () {
		GameObject o = manager.getObject(x, y);
		if (o instanceof Space) {
			return false;
		}
		explode (x, y);
		return true;
	} // hitLocalTargets

	/**
	* Hit overloped objects.
	* Good only for 1x1 bullets, must be overloaded for another bullet geometry
	*/
	public void explode () {
		state = KILLED_STATE;
		manager.killed (this);
		explode (x, y);
	} // explode

	/**
	* Hit overloped objects.
	* Good only for 1x1 bullets, must be overloaded for another bullet geometry
	*/
	protected void explode (int x, int y) {
		GameObject	target = manager.getObject(x, y);
		if (target != this) {
			target.hit(strength, this);
		}
	} // explode

	/**
	* Move object in specified direction
	*/
	public void move (int direction) {
		GameObject target = getTarget (direction);
		if (target instanceof Space) {
			super.move (direction);
		} else {
			state = KILLED_STATE;
			manager.killed (this);
			explode (GameObject.getNextX (x, direction), GameObject.getNextY (y, direction));
		}
	} // move

	protected GameObject getTarget (int direction) {
		switch (direction) {
		case UP_DIRECTION:
			return manager.getObject (x, y - 1);
		case LEFT_DIRECTION:
			return manager.getObject (x - 1, y);
		case DOWN_DIRECTION:
			return manager.getObject (x, y + height);
		case RIGHT_DIRECTION:
			return manager.getObject (x + width, y);
		}
		// not reached
		return null;
	} // getTarget

}

