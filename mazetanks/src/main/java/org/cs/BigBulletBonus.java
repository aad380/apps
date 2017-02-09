package org.cs;

public class BigBulletBonus extends Bonus {

	public BigBulletBonus (int x, int y, int count, GameManager manager) {
		super (x, y, 2, 2, count, manager);
	}

	public BigBulletBonus (int x, int y, GameManager manager) {
		super (x, y, 2, 2, 3, manager);
	}

}

