package org.cs;

public interface Player {
	public void deviceCreated (GuidedDevice device);	// called by TankManager
	public void deviceKilled ();				// called by TankManager
	public String getName ();
	public void step ();					// called by org.cs.GuidedDevice
	public void bulletsChanged (int number);		// called by org.cs.GuidedDevice
	public void bigBulletsChanged (int number);		// called by org.cs.GuidedDevice
	public void nukeBulletsChanged (int number);		// called by org.cs.GuidedDevice
	public void shieldChanged (int shield);			// called by org.cs.GuidedDevice
}

