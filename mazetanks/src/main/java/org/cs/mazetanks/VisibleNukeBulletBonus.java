package org.cs.mazetanks;

import java.awt.*;

class VisibleNukeBulletBonus extends NukeBulletBonus implements Visible {
    protected static Image image_4x4, image_8x8;

    static {
        image_4x4 = ImageManager.getImage("4x4/NukeBulletBonus.gif");
        image_8x8 = ImageManager.getImage("8x8/NukeBulletBonus.gif");
    }

    protected int sequenceId = -1;
    protected boolean hiden = true;
    protected GameSpaceCanvas space;

    public VisibleNukeBulletBonus(int x, int y, int count, GameManager manager, GameSpaceCanvas space) {
        super(x, y, count, manager);
        this.space = space;
    }

    public VisibleNukeBulletBonus(int x, int y, GameManager manager, GameSpaceCanvas space) {
        super(x, y, manager);
        this.space = space;
    }

    public Image getImage(int cellSize) {
        switch (cellSize) {
            case 8:
                return image_8x8;
            case 4:
                return image_4x4;
            default:
                System.err.println("VisibleNukeBulletBonus: unsupported cell size.");
                System.exit(1);
        }
        // not reached
        return null;
    } // getImage

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

