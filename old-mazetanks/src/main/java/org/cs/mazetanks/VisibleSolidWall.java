package org.cs.mazetanks;

import java.awt.*;

class VisibleSolidWall extends SolidWall implements Visible {
    protected static Image image_4x4, image_8x8;

    static {
        image_4x4 = ImageManager.getImage("4x4/Wall.gif");
        image_8x8 = ImageManager.getImage("8x8/Wall.gif");
    }

    protected int sequenceId = -1;
    protected boolean hiden = true;
    protected GameSpaceCanvas space;

    public VisibleSolidWall(int x, int y, GameManager manager, GameSpaceCanvas space) {
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
                System.err.println("VisibleSolidWall: unsupported cell size.");
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
            graphics.fillRect(x, y, cellSize, cellSize);
            this.sequenceId = sequenceId;
            hiden = true;
        }
    } // hide

    public void hide(int x, int y, int cellSize, Graphics graphics) {
        if (!hiden) {
            graphics.fillRect(x, y, cellSize, cellSize);
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

