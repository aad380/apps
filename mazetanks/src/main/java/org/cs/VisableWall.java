package org.cs;

import	java.awt.Image;
import	java.awt.Graphics;

class VisableWall extends Wall implements Visable {
	protected static Image		image_4x4_100, image_8x8_100;
	protected static Image		image_4x4_50, image_8x8_50;
	protected static Image		image_4x4_10, image_8x8_10;
	protected int			sequenceId = -1;
	protected boolean		hiden = true;
	protected GameSpaceCanvas	space;

	static {
		image_4x4_100 = ImageManager.getImage ("4x4/org.cs.Wall.gif");
		image_8x8_100 = ImageManager.getImage ("8x8/org.cs.Wall.gif");
		image_4x4_50 = ImageManager.getImage ("4x4/Wall50.gif");
		image_8x8_50 = ImageManager.getImage ("8x8/Wall50.gif");
		image_4x4_10 = ImageManager.getImage ("4x4/Wall10.gif");
		image_8x8_10 = ImageManager.getImage ("8x8/Wall10.gif");
	}

	protected Image		image_4x4, image_8x8;

	public VisableWall (int x, int y, GameManager manager, GameSpaceCanvas space) {
                super (x, y, manager);
		this.space = space;
		image_4x4 = image_4x4_100;
		image_8x8 = image_8x8_100;
        }

	public Image getImage (int cellSize) {
		switch (cellSize) {
		case 8:
			return image_8x8;
		case 4:
			return image_4x4;
		default:
			System.err.println ("org.cs.VisableWall: unsupported cell size.");
			System.exit (1);
		}
		// not reached
		return null;
	} // getImage

	/**
	* Hit object
	*/
	public int hit (int strength, GameObject object) {
		int newShield = shield - strength;
		if (newShield <= 1 && shield > 1) {
			image_4x4 = image_4x4_10;
			image_8x8 = image_8x8_10;
			if (!hiden) {
				space.showObject (this);
			}
		} else if (newShield <= 5 && shield > 5) {
			image_4x4 = image_4x4_50;
			image_8x8 = image_8x8_50;
			if (!hiden) {
				space.showObject (this);
			}
		}
		return super.hit (strength, object);
	} // hit

	public void show (int x, int y, int cellSize, Graphics graphics, int sequenceId) {
		if (this.sequenceId != sequenceId) {
			graphics.drawImage (getImage (cellSize), x, y, null);
			this.sequenceId = sequenceId;
			hiden = false;
		}
	} // show

	public void show (int x, int y, int cellSize, Graphics graphics) {
		graphics.drawImage (getImage (cellSize), x, y, null);
		hiden = false;
	} // show

	public void hide (int x, int y, int cellSize, Graphics graphics, int sequenceId) {
		if (!hiden && this.sequenceId != sequenceId) {
			graphics.fillRect (x, y, cellSize, cellSize);
			this.sequenceId = sequenceId;
			hiden = true;
		}
	} // hide

	public void hide (int x, int y, int cellSize, Graphics graphics) {
		if (!hiden) {
			graphics.fillRect (x, y, cellSize, cellSize);
			this.sequenceId = sequenceId;
			hiden = true;
		}
	} // hide

	public boolean isHiden () {
		return hiden;
	}

	public void setHiden (boolean flag) {
		hiden = flag;
	}

	public int getDrawSequenceId () {
		return sequenceId;
	}

}

