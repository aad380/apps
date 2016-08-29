package org.cs.mazetanks;

import org.cs.mazetanks.player.Player;

class BigBullet extends Bullet {

    public BigBullet(int x, int y, int direction, GameManager manager, Player player) {
        super(x, y, 1, 1, direction, 3, manager, player);
    }

}

