package org.cs;

public class CannonDriver implements Player {

	private String		name;
	private GameSpace	space;
	private GuidedDevice	cannon = null;

	CannonDriver (String name, GameSpace space) {
		this.name = name;
		this.space = space;
	}

	public void deviceCreated (GuidedDevice cannon) {
		this.cannon = cannon;
	} // tankCreated

	public void deviceKilled () {
		cannon = null;
	} // tankCreated

	public String getName () {
		return name;
	} // getName

	public void step () {
		if (cannon == null) {
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

