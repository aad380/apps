package org.cs.mazetanks.player;

import org.cs.mazetanks.GuidedDevice;

public interface Player {
    void deviceCreated(GuidedDevice device);    // called by TankManager
    void deviceKilled();                // called by TankManager
    String getName();
    void step();                    // called by GuidedDevice
    void bulletsChanged(int number);        // called by GuidedDevice
    void bigBulletsChanged(int number);        // called by GuidedDevice
    void nukeBulletsChanged(int number);        // called by GuidedDevice
    void shieldChanged(int shield);            // called by GuidedDevice
}

