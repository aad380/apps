package org.cs;

public interface GuidedDevice {

	public final int	BULLET = 0,		// for currentWeapon
				BIG_BULLET = 1,
				NUKE_BULLET = 2;

	public final int	FORWARD = 1,		// for stepState
				STOP = 0,
				BACKWARD = -1;

	public void turnLeft ();
	public void turnRight ();
	public void goForward ();
	public void goBackward ();
	public void runForward ();
	public void runBackward ();
	public void stop ();
	public void fire ();
	public void selectBullet ();
	public void selectBigBullet ();
	public void selectNukeBullet ();
	public int getCurrentWeapon ();
	public int getMoveDirection ();
	public int getBullets ();
	public int getBigBullets ();
	public int getNukeBullets ();
	public int getShield ();
	public int getState ();			// from org.cs.GameObject, org.cs.Tank, org.cs.Cannon, etc
	public boolean checkMove ();		// from org.cs.GameObject, org.cs.Tank, org.cs.Cannon, etc
	public boolean checkMove (int direction);	// from org.cs.GameObject, org.cs.Tank, org.cs.Cannon, etc

}

