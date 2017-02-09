package org.cs;

public class ShieldBonus extends Bonus {

	public ShieldBonus (int x, int y, int count, GameManager manager) {
		super (x, y, 2, 2, count, manager);
	}

	public ShieldBonus (int x, int y, GameManager manager) {
		super (x, y, 2, 2, 3, manager);
	}

}

