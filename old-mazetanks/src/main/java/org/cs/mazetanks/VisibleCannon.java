package org.cs.mazetanks;

import org.cs.mazetanks.player.Player;

import java.awt.*;

class VisibleCannon extends Cannon implements Visible {
    protected static Image[] images_4x4 = new Image[4],
            images_8x8 = new Image[4];

    static {
        images_4x4[UP_DIRECTION] = ImageManager.getImage("4x4/Cannon_u.gif");
        images_4x4[RIGHT_DIRECTION] = ImageManager.getImage("4x4/Cannon_r.gif");
        images_4x4[DOWN_DIRECTION] = ImageManager.getImage("4x4/Cannon_d.gif");
        images_4x4[LEFT_DIRECTION] = ImageManager.getImage("4x4/Cannon_l.gif");
        images_8x8[UP_DIRECTION] = ImageManager.getImage("8x8/Cannon_u.gif");
        images_8x8[RIGHT_DIRECTION] = ImageManager.getImage("8x8/Cannon_r.gif");
        images_8x8[DOWN_DIRECTION] = ImageManager.getImage("8x8/Cannon_d.gif");
        images_8x8[LEFT_DIRECTION] = ImageManager.getImage("8x8/Cannon_l.gif");
    }

    protected int sequenceId = -1;
    protected boolean hiden = true;
    protected GameSpaceCanvas space;

    public VisibleCannon(int x, int y, int direction, GameManager manager, Player player, GameSpaceCanvas space) {
        super(x, y, direction, manager, player);
        this.space = space;
    }

    public Image getImage(int cellSize) {
        switch (cellSize) {
            case 8:
                return images_8x8[direction];
            case 4:
                return images_4x4[direction];
            default:
                System.err.println("VisibleCannon: unsupported cell size.");
                System.exit(1);
        }
        // not reached
        return null;
    } // getImage


    protected Bullet makeBullet(int x, int y, int direction) {
        return new VisibleBullet(x, y, direction, manager, player, space);
    }

    protected BigBullet makeBigBullet(int x, int y, int direction) {
        return new VisibleBigBullet(x, y, direction, manager, player, space);
    }

    protected NukeBullet makeNukeBullet(int x, int y, int direction) {
        return new VisibleNukeBullet(x, y, direction, manager, player, space);
    }

    public void show(int x, int y, int cellSize, Graphics graphics, int sequenceId) {
        if (this.sequenceId != sequenceId) {
            graphics.drawImage(getImage(cellSize), x, y, null);
            this.sequenceId = sequenceId;
            hiden = false;
        }
    } // show

    public void show(int x, int y, int cellSize, Graphics graphics) {
        graphics.drawImage(getImage(cellSize), x, y, null);
        hiden = false;
    } // show

    public void hide(int x, int y, int cellSize, Graphics graphics, int sequenceId) {
        if (!hiden && this.sequenceId != sequenceId) {
            graphics.fillRect(x, y, width * cellSize, height * cellSize);
            this.sequenceId = sequenceId;
            hiden = true;
        }
    } // hide

    public void hide(int x, int y, int cellSize, Graphics graphics) {
        if (!hiden) {
            graphics.fillRect(x, y, width * cellSize, height * cellSize);
            this.sequenceId = sequenceId;
            hiden = true;
        }
    } // hide

    public boolean isHidden() {
        return hiden;
    }

    public void setHidden(boolean flag) {
        hiden = flag;
    }

    public int getDrawSequenceId() {
        return sequenceId;
    }

}

