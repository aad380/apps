package org.cs;

import java.lang.System;

class Cannon extends GameObject implements GuidedDevice {

	private final static long	GUN_RELOAD_TIME = 500L;

	protected static int[]	rightMap = new int [4],
				leftMap = new int [4];

	protected int	bullets,
			bigBullets,
			nukeBullets,
			currentWeapon;

	protected boolean fireFlag = false;

	private long	lastGunReloadTime = 0L;

	static {
		// init rightMap
		rightMap [UP_DIRECTION] = RIGHT_DIRECTION;
		rightMap [DOWN_DIRECTION] = LEFT_DIRECTION;
		rightMap [RIGHT_DIRECTION] = DOWN_DIRECTION;
		rightMap [LEFT_DIRECTION] = UP_DIRECTION;
		// init leftMap
		leftMap [UP_DIRECTION] = LEFT_DIRECTION;
		leftMap [DOWN_DIRECTION] = RIGHT_DIRECTION;
		leftMap [RIGHT_DIRECTION] = UP_DIRECTION;
		leftMap [LEFT_DIRECTION] = DOWN_DIRECTION;
	}

	public Cannon (int x, int y, int direction, GameManager manager, Player player) {
		super (x, y, 3, 3, direction, STOP_STATE, 30, manager, player);
		bullets = 50;
		bigBullets = 20;
		nukeBullets = 3;
		currentWeapon = BULLET;
	}

	protected Cannon (
		int x, int y, int width, int height, int direction,
		int bullets, int bigBullets, int nukeBullets, int currentWeapon, int shield,
		GameManager manager, Player player
	){
		super (x, y, width, height, direction, STOP_STATE, shield, manager, player);
		this.bullets = bullets;
		this.bigBullets = bigBullets;
		this.nukeBullets = nukeBullets;
		this.currentWeapon = currentWeapon;
	}

	public int hit (int strength, GameObject object) {
		player.shieldChanged (shield - strength);
		return super.hit (strength, object);
	}

	/**
	* Disable move
	*/
	public void move (int direction) {} // move

	/**
	* Check object ability to move to specified direction
	*/
	public boolean checkMove (int direction) {
		return false;
	} // checkMove

// + org.cs.GuidedDevice interface

	public void turnLeft () {
		if (state != KILLED_STATE) {
			int oldDirection = direction;
			direction = leftMap [direction];
			manager.directionChanged (this, oldDirection);
		}
	}

	public void turnRight () {
		if (state != KILLED_STATE) {
			int oldDirection = direction;
			direction = rightMap [direction];
			manager.directionChanged (this, oldDirection);
		}
	}

	public void goForward () {}

	public void goBackward () {}

	public void runForward () {}

	public void runBackward () {}

	public void stop () {}

	public void fire () {
		if (state != KILLED_STATE) {
			long	fireTime = System.currentTimeMillis ();
			if ((fireTime - lastGunReloadTime) >= GUN_RELOAD_TIME) {
				fireFlag = true;
				lastGunReloadTime = fireTime;
			}
		}
	}

	public void selectBullet () {
		if (state != KILLED_STATE) {
			if (currentWeapon != BULLET) {
				currentWeapon = BULLET;
				lastGunReloadTime = System.currentTimeMillis ();
			}
		}
	}

	public void selectBigBullet () {
		if (state != KILLED_STATE) {
			if (currentWeapon != BIG_BULLET) {
				currentWeapon = BIG_BULLET;
				lastGunReloadTime = System.currentTimeMillis ();
			}
		}
	}

	public void selectNukeBullet () {
		if (state != KILLED_STATE) {
			if (currentWeapon != NUKE_BULLET) {
				currentWeapon = NUKE_BULLET;
				lastGunReloadTime = System.currentTimeMillis ();
			}
		}
	}

	public int getCurrentWeapon () {
		return currentWeapon;
	} // getCurrentWeapon

	public int getMoveDirection () {
		return STOP;
	} // getMoveDirection

	public int getBullets () {
		return bullets;
	}

	public int getBigBullets () {
		return bigBullets;
	}

	public int getNukeBullets () {
		return nukeBullets;
	}

	public int getShield () {
		return shield;
	}

// - org.cs.GuidedDevice interface

	public void step () {
		if (state == KILLED_STATE) {
			return;
		}
		player.step ();		// hook for org.cs.CannonDriver
		if (state == KILLED_STATE) {
			return;
		}
		if (fireFlag) {
			doFire ();
			fireFlag = false;
		}
	} // step

	protected void doFire () {
		switch (currentWeapon) {
		case BULLET:
			if (bullets > 0) {
				-- bullets;
				player.bulletsChanged (bullets);
				manager.addObject (makeBullet (getBulletX (), getBulletY (), direction));
			}
			break;
		case BIG_BULLET:
			if (bigBullets > 0) {
				-- bigBullets;
				player.bigBulletsChanged (bigBullets);
				manager.addObject (makeBigBullet (getBulletX (), getBulletY (), direction));
			}
			break;
		case NUKE_BULLET:
			if (nukeBullets > 0) {
				-- nukeBullets;
				player.nukeBulletsChanged (nukeBullets);
				manager.addObject (makeNukeBullet (getBulletX (), getBulletY (), direction));
			}
		}
	} // fire

	private int getBulletX () {
		switch (direction) {
		case UP_DIRECTION:
			return x + 1;
		case RIGHT_DIRECTION:
			return x + width;
		case DOWN_DIRECTION:
			return x + 1;
		case LEFT_DIRECTION:
			return x - 1;
		}
		// not reached
		return 0;
	} // getBulletX

	private int getBulletY () {
		switch (direction) {
		case UP_DIRECTION:
			return y - 1;
		case RIGHT_DIRECTION:
			return y + 1;
		case DOWN_DIRECTION:
			return y + height;
		case LEFT_DIRECTION:
			return y + 1;
		}
		// not reached
		return 0;
	} // getBulletY

	/**
	* Reloaded in org.cs.VisableTank
	*/
	protected Bullet makeBullet (int x, int y, int direction) {
		return new Bullet (x, y, direction, manager, player);
	}

	/**
	* Reloaded in org.cs.VisableTank
	*/
	protected BigBullet makeBigBullet (int x, int y, int direction) {
		return new BigBullet (x, y, direction, manager, player);
	}

	/**
	* Reloaded in org.cs.VisableTank
	*/
	protected NukeBullet makeNukeBullet (int x, int y, int direction) {
		return new NukeBullet (x, y, direction, manager, player);
	}

}

