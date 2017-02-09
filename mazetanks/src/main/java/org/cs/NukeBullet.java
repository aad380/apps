package org.cs;

class NukeBullet extends Bullet {

	public NukeBullet (int x, int y, int direction, GameManager manager, Player player) {
		super (x, y, 1, 1, direction, 11, manager, player);
	}

	protected void explode (int x, int y) {
		GameObject	target = manager.getObject(x, y);
		if (target != this) target.hit (strength, this);
		int	minX = x - 1, maxX = x + 1,
			minY = y - 1, maxY = y + 1;
		for (int hitStrength = strength - 1; hitStrength > 0; -- hitStrength, -- minX, -- minY, ++ maxX, ++ maxY) {
			for (int i = minX; i <= maxX; ++ i) {
				target = manager.getObject(i, minY);
				if (target != this) target.hit (hitStrength, this);
				target = manager.getObject(i, maxY);
				if (target != this) target.hit (hitStrength, this);
			}
			for (int i = minY + 1; i < maxY; ++ i) {
				target = manager.getObject(minX, i);
				if (target != this) target.hit (hitStrength, this);
				target = manager.getObject(maxX, i);
				if (target != this) target.hit (hitStrength, this);
			}
		}
	} // explode

}

