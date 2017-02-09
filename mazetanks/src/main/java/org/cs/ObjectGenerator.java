package org.cs;

import java.util.Random;

public class ObjectGenerator {

	private Random		random = new Random ();
	private GameManager	manager;
	private GameSpace	space;
	private boolean		visableObjectMode;
	private int		spaceWidth;
	private int		spaceHeight;

	ObjectGenerator (GameManager manager, GameSpace space, boolean visableObjectMode) {
		this.manager = manager;
		this.space = space;
		this.visableObjectMode = visableObjectMode;
		spaceWidth = space.getWidth ();
		spaceHeight = space.getHeight ();
	}

	/**
	* Unlimited if tryCounter <= 0
	*/
	public Tank makeTank (Player player, int tryCounter) {
		Tank	tank = null;
		if (visableObjectMode) {
			tank = new VisableTank (-1, -1, GameObject.NO_DIRECTION, manager, player, (GameSpaceCanvas) space);
		} else {
			tank = new Tank (-1, -1, GameObject.NO_DIRECTION, manager, player);
		}
		if (setPositions (tank, tryCounter)) {
			setDirection (tank);
			return tank;
		}
		return null;
	} // makeTank

/**
* Unlimited if tryCounter <= 0
*/
public BigBullet makeBigBullet (Player player, int tryCounter) {
	int	x = rand (spaceWidth),
		y = rand (spaceHeight);
	if (space.getObject (x, y) instanceof Space) {
		BigBullet	bullet = null;
		if (visableObjectMode) {
			bullet = new VisableBigBullet (x, y, GameObject.NO_DIRECTION, manager, player, (GameSpaceCanvas) space);
		} else {
			bullet = new BigBullet (x, y, GameObject.NO_DIRECTION, manager, player);
		}
		if (setPositions (bullet, tryCounter)) {
			setDirection (bullet);
			return bullet;
		}
	}
	return null;
} // makeBullet

	/**
	* Unlimited if tryCounter <= 0
	*/
	public BulletBonus makeBulletBonus (int tryCounter) {
		BulletBonus	bonus = null;
		if (visableObjectMode) {
			bonus = new VisableBulletBonus (-1, -1, manager, (GameSpaceCanvas) space);
		} else {
			bonus = new BulletBonus (-1, -1, manager);
		}
		if (setPositions (bonus, tryCounter)) {
			return bonus;
		}
		return null;
	} // makeBulletBonus

	/**
	* Unlimited if tryCounter <= 0
	*/
	public BigBulletBonus makeBigBulletBonus (int tryCounter) {
		BigBulletBonus	bonus = null;
		if (visableObjectMode) {
			bonus = new VisableBigBulletBonus (-1, -1, manager, (GameSpaceCanvas) space);
		} else {
			bonus = new BigBulletBonus (-1, -1, manager);
		}
		if (setPositions (bonus, tryCounter)) {
			return bonus;
		}
		return null;
	} // makeBigBulletBonus

	/**
	* Unlimited if tryCounter <= 0
	*/
	public NukeBulletBonus makeNukeBulletBonus (int tryCounter) {
		NukeBulletBonus	bonus = null;
		if (visableObjectMode) {
			bonus = new VisableNukeBulletBonus (-1, -1, manager, (GameSpaceCanvas) space);
		} else {
			bonus = new NukeBulletBonus (-1, -1, manager);
		}
		if (setPositions (bonus, tryCounter)) {
			return bonus;
		}
		return null;
	} // makeNukeBulletBonus

	/**
	* Unlimited if tryCounter <= 0
	*/
	public ShieldBonus makeShieldBonus (int tryCounter) {
		ShieldBonus	bonus = null;
		if (visableObjectMode) {
			bonus = new VisableShieldBonus (-1, -1, manager, (GameSpaceCanvas) space);
		} else {
			bonus = new ShieldBonus (-1, -1, manager);
		}
		if (setPositions (bonus, tryCounter)) {
			return bonus;
		}
		return null;
	} // makeShieldBonus

	/**
	* Unlimited if tryCounter <= 0
	*/
	private boolean setPositions (GameObject object, int tryCounter) {
		mainLoop: while (true) {
			int	x = rand (spaceWidth),
				y = rand (spaceHeight),
				maxX = x + object.getWidth (),
				maxY = y + object.getHeight ();
			for (int i = x; i < maxX; ++ i) {
				for (int j = y; j < maxY; ++ j) {
					if (!space.isSpace(i, j)) {
						if (tryCounter > 0) {
							-- tryCounter;
							if (tryCounter == 0) {
								return false;
							}
						}
						continue mainLoop;
					} // if
				} // for j
			} // for i
			object.setX (x);
			object.setY (y);
			return true;
		} // while
	} // setPositions

	/**
	* Unlimited if tryCounter <= 0
	*/
	private void setDirection (GameObject object) {
		switch (rand (4)) {
		case 0:
			object.setDirection (GameObject.UP_DIRECTION);
			break;
		case 1:
			object.setDirection (GameObject.RIGHT_DIRECTION);
			break;
		case 2:
			object.setDirection (GameObject.DOWN_DIRECTION);
			break;
		case 3:
			object.setDirection (GameObject.LEFT_DIRECTION);
			break;
		}
		return;
	} // makeTank

	private int rand (int max) {
		return Math.abs (random.nextInt ()) % max;
	} // rand

}

