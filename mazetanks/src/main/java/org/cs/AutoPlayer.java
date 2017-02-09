package org.cs;

public class AutoPlayer implements Player {

	private String		name;
	private GameSpace	space;
	private GuidedDevice	tank = null;

	AutoPlayer (String name, GameSpace space) {
		this.name = name;
		this.space = space;
	}

	public void deviceCreated (GuidedDevice tank) {
		this.tank = tank;
	} // tankCreated

	public void deviceKilled () {
		tank = null;
	} // tankCreated

	public String getName () {
		return name;
	} // getName

	public void step () {
		if (tank == null) {
			return;
		}
	} // tankStep

	public void bulletsChanged (int number) {
	}

	public void bigBulletsChanged (int number) {
	}

	public void nukeBulletsChanged (int number) {
	}

	public void shieldChanged (int shield) {
	}


}

