package org.cs;

import java.awt.*;

public class GameSpaceCanvas extends Canvas implements GameSpace {

	// window properties
	private int	windowWidth,			// cell collumns in window
			windowHeight,			// cell rows in window
			windowX,			// horizontal window position in maze
			windowY,			// vertical window position in maze
			windowPixelWidth,		// window (canvas) width in pixels
			windowPixelHeight,		// window (canvas) height in pixels
			cellWidth,			// cell width in pixels
			cellHeight;			// cell height in pixels
	private Dimension	windowDimension;	// preffered window dimention

	// draw properties
	private Color		wallColor,		// color for wall drawing
				spaceColor;		// color for space drawing
	Graphics		spaceGraphics,		// graphics with spaceColor
				wallGraphics;		// graphics with wallColor
	int			drawId;			// draw sequence number

	// maze properties
	private GameMaze	maze = null;
	private int		width,			// game space width
				height;			// game space height

	private GameObject	mainObject = null;	// main object
	private boolean		started = false;	// indicate thet all initet properly

	private static boolean isConjunction (
		// 1st rectangle
		int	x11,
		int	y11,
		int	w1,
		int	h1,
		// 2nd rectangle
		int	x21,
		int	y21,
		int	w2,
		int	h2
	){
		// Main idea is:
		//	int	x12 = x11 + w1 - 1,
		//		y12 = y11 + h1 - 1,
		//		x22 = x21 + w2 - 1,
		//		y22 = y21 + h2 - 1;
		//	int	x31 = (x11 > x21) ? x11 : x21,
		//		y31 = (y11 > y21) ? y11 : y21,
		//		x32 = (x12 < x22) ? x12 : x22,
		//		y32 = (y12 < y22) ? y12 : y22;
		//	if (x31 <= x32 && y31 <= y32) {
		//		return true;
		//	}
		//	return false;
		/*
		if (w2 == 1 && h2 == 1 && x21 >= x11 && x21 < x11 + w1 && y21 >= y11 && y21 < y11 + h1) {
			return true;
		}
		*/
		int	x12 = x11 + w1 - 1,
			y12 = y11 + h1 - 1,
			x22 = x21 + w2 - 1,
			y22 = y21 + h2 - 1;
		return (
			((x11 > x21) ? x11 : x21) <= ((x12 < x22) ? x12 : x22) &&
			((y11 > y21) ? y11 : y21) <= ((y12 < y22) ? y12 : y22)
		);
	} // isConjunction

	private void updateWindowParameters (int width, int height, int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.windowWidth = width / cellWidth;
		this.windowHeight = height / cellHeight;
		this.windowPixelWidth = windowWidth * cellWidth;
		this.windowPixelHeight = windowHeight * cellHeight;
		this.windowDimension = new Dimension (windowPixelWidth, windowPixelHeight);
	} // updateWindowParameters

	GameSpaceCanvas (int width, int height, int cellSize, Color wallColor, Color spaceColor) {
		super ();
		updateWindowParameters (width * cellSize, height * cellSize, cellSize, cellSize);
		this.wallColor = wallColor;
		this.spaceColor = spaceColor;
	}

	public Dimension preferredSize () {
		return windowDimension;
	}

	public void reshape (int  x, int  y, int  width, int height) {
		updateWindowParameters (width, height, cellWidth, cellHeight);
		if (started) {
			if (mainObject != null) {
				windowX = mainObject.x - 1 - (windowWidth - mainObject.getWidth ()) / 2;
				windowY = mainObject.y - 1 - (windowHeight - mainObject.getHeight ()) / 2;
			} else {
				windowX = windowY = 0;
			}
			super.reshape (x, y, this.windowPixelWidth, this.windowPixelHeight);
			//refreshWindow ();
		} else {
			super.reshape (x, y, width, height);
		}
	}

	public void setCellSize (int cellSize) {
		updateWindowParameters (windowPixelWidth, windowPixelHeight, cellSize, cellSize);
		if (started) {
			if (mainObject != null) {
				windowX = mainObject.getX () - 1 - (windowWidth - mainObject.getWidth ()) / 2;
				windowY = mainObject.getY () - 1 - (windowHeight - mainObject.getHeight ()) / 2;
			} else {
				windowX = windowY = 0;
			}
			refreshWindow ();
		}
	} // setCellSize

	public void paint (Graphics g) {
		update (g);
	} // paint

	public void update (Graphics g) {
		if (started) {
			Rectangle	r = g.getClipRect ();
			refreshWindow (
				r.x / cellWidth,
				r.y / cellHeight,
				(r.x + r.width - 1) / cellWidth,
				(r.y + r.height - 1) / cellHeight
			);
		} else {
			String message = "Press <Start> for new game.";
			//g.setFont (new Font ("Curier", Font.BOLD, 10));
			FontMetrics fm = g.getFontMetrics ();
			int	fontHeight = fm.getHeight (),
				fontWidth = fm.stringWidth (message);
			Dimension d = size ();
			g.setColor (Color.black);
			g.fillRect (0, 0, d.width, d.height);
			g.setColor (Color.red);
			g.drawString (message, (d.width - fontWidth) / 2, (d.height - fontHeight) / 2);
		}
	}

	private void refreshWindow () {
		refreshWindow (0, 0, windowWidth - 1, windowHeight - 1);
	} // refreshWindow

	private void refreshWindow (int minX, int minY, int maxX, int maxY) {
		for (int y = minY; y <= maxY; ++ y) {
			refreshHLine (y, minX, maxX);
		}
	} // refreshWindow

	private void refreshVLine (int x, int minY, int maxY) {
		int	wallCells,
			realX = windowX + x;
		if (realX < 0 || realX >= width) {
			wallGraphics.fillRect (
				x * cellWidth, minY * cellHeight,
				cellWidth, (maxY - minY + 1) * cellHeight
			);
			return;
		}
		int	realMinY = windowY + minY,
			realMaxY = windowY + maxY;
		if (realMinY < 0) {
			wallGraphics.fillRect (
				x * cellWidth, minY * cellHeight,
				cellWidth, (-realMinY) * cellHeight
			);
			minY -= realMinY;
		}
		if (realMaxY >= height) {
			wallCells = realMaxY - height + 1;
			wallGraphics.fillRect (
				x * cellWidth, (maxY + 1 - wallCells) * cellHeight,
				cellWidth, wallCells * cellHeight
			);
			maxY -= wallCells;
		}

		spaceGraphics.fillRect (
				x * cellWidth, minY * cellHeight,
				cellWidth, (maxY - minY + 1) * cellHeight
		);
		wallCells = 0;
		for (int y = minY; y <= maxY; ++ y) {
			GameObject o = maze.fastGetCell (windowX + x, windowY + y);
			if (
				(o instanceof Wall) && ((Wall)o).isSolid ()
			){
				((Visable)o).setHiden (false);
				++ wallCells;
			} else {
				if (wallCells > 0) {
					wallGraphics.fillRect (
						x * cellWidth, (y - wallCells) * cellHeight,
						cellWidth, wallCells * cellHeight
					);
					wallCells = 0;
				} 
				if (!(o instanceof Space)) {
					((Visable)o).show (
						(o.getX () - windowX) * cellWidth,
						(o.getY () - windowY) * cellHeight,
						cellWidth, spaceGraphics, drawId
					);
				}
			}
		} // for
		if (wallCells > 0) {
			wallGraphics.fillRect (
				x * cellWidth, (maxY + 1 - wallCells) * cellHeight,
				cellWidth, wallCells * cellHeight
			);
		} 
		++ drawId;
	} // refreshVLine

	private void refreshHLine (int y, int minX, int maxX) {
		int	wallCells,
			realY = windowY + y;
		if (realY < 0 || realY >= height) {
			wallGraphics.fillRect (
				minX * cellWidth, y * cellHeight,
				(maxX - minX + 1) * cellWidth, cellHeight
			);
			return;
		}
		int	realMinX = windowX + minX,
			realMaxX = windowX + maxX;
		if (realMinX < 0) {
			wallGraphics.fillRect (
				minX * cellWidth, y * cellHeight,
				(-realMinX) * cellWidth, cellHeight
			);
			minX -= realMinX;
		}
		if (realMaxX >= width) {
			wallCells = realMaxX - width + 1;
			wallGraphics.fillRect (
				(maxX + 1 - wallCells) * cellWidth, y * cellHeight,
				wallCells * cellWidth, cellHeight
			);
			maxX -= wallCells;
		}

		spaceGraphics.fillRect (
			minX * cellWidth, y * cellHeight,
			(maxX - minX + 1) * cellWidth, cellHeight
		);
		wallCells = 0;
		for (int x = minX; x <= maxX; ++ x) {
			GameObject o = maze.fastGetCell (windowX + x, windowY + y);
			if (
				(o instanceof Wall) && ((Wall)o).isSolid ()
			){
				((Visable)o).setHiden (false);
				++ wallCells;
			} else {
				if (wallCells > 0) {
					wallGraphics.fillRect (
						(x - wallCells) * cellWidth, y * cellHeight,
						wallCells * cellWidth, cellHeight
					);
					wallCells = 0;
				} 
				if (!(o instanceof Space)) {
					((Visable)o).show (
						(o.getX () - windowX) * cellWidth,
						(o.getY () - windowY) * cellHeight,
						cellWidth, spaceGraphics, drawId
					);
				}
			}
		} // for
		if (wallCells > 0) {
			wallGraphics.fillRect (
				(maxX + 1 - wallCells) * cellWidth, y * cellHeight,
				wallCells * cellWidth, cellHeight
			);
		} 
		++ drawId;
	} // refreshHLine

	void moveWindow (int dx, int dy) {
		windowX += dx;
		windowY += dy;
		if (dy == 0) {
			if (dx == 1) {
				//
				// <-- copy
				// 
				// ===================
				// copy region to left side
				spaceGraphics.copyArea (
					cellWidth, 0,
					windowPixelWidth - cellWidth, windowPixelHeight,
					-cellWidth, 0
				);
				if (windowX + windowWidth < width + 1) {
					refreshVLine (windowWidth - 1, 0, windowHeight - 1);
				} else if (windowX + windowWidth == width + 1) {
					//wallGraphics.setColor (Color.blue);
					wallGraphics.fillRect (
						(windowWidth - 1) * cellWidth, 0,
						cellWidth, windowHeight * cellHeight
					);
					//wallGraphics.setColor (wallColor);
				}
			} else if (dx == -1) {
				// 
				// -->
				// 
				// ===================
				// copy region to right side
				spaceGraphics.copyArea (
					0, 0,
					windowPixelWidth - cellWidth, windowPixelHeight,
					cellWidth, 0
				);
				if (windowX >= 0) {
					refreshVLine (0, 0, windowHeight - 1);
				} else if (windowX == -1) {
					//wallGraphics.setColor (Color.red);
					wallGraphics.fillRect (0, 0, cellWidth, windowHeight * cellHeight);
					//wallGraphics.setColor (wallColor);
				}
			} else {
				System.err.println ("org.cs.GameSpaceCanvas: moveWindow: bad parameters");
				System.exit (1);
			}
		} else if (dx == 0) {
			if (dy == 1) {
				//  ^
				//  |
				//  |
				// ===================
				// copy region to up
				spaceGraphics.copyArea (
					0, cellHeight,
					windowPixelWidth, windowPixelHeight - cellHeight,
					0, -cellHeight
				);
				if (windowY + windowHeight < height + 1) {
					refreshHLine (windowHeight - 1, 0, windowWidth -1);
				} else if (windowY + windowHeight == height + 1) {
					//wallGraphics.setColor (Color.green);
					wallGraphics.fillRect (
						0, (windowHeight - 1) * cellHeight,
						windowWidth * cellWidth, cellHeight
					);
					//wallGraphics.setColor (wallColor);
				}
			} else if (dy == -1) {
				//  |
				//  |
				//  v
				// ===================
				// copy region to down
				spaceGraphics.copyArea (0, 0, windowPixelWidth, windowPixelHeight - cellHeight, 0, cellHeight);
				if (windowY >= 0) {
					refreshHLine (0, 0, windowWidth - 1);
				} else if (windowY == -1) {
					//wallGraphics.setColor (Color.yellow);
					wallGraphics.fillRect (
						0, 0,
						windowWidth * cellWidth, cellHeight
					);
					//wallGraphics.setColor (wallColor);
				}
			} else {
				System.err.println ("org.cs.GameSpaceCanvas: moveWindow: bad parameters");
				System.exit (1);
			}
		} else {
			System.err.println ("org.cs.GameSpaceCanvas: moveWindow: bad parameters");
			System.exit (1);
		} // if
	} // moveWindow

	public void showObject (GameObject object) {
		showObject (object, object.getX (), object.getY ());
	} // showObject

	public void hideObject (GameObject object) {
		hideObject (object, object.getX (), object.getY ());
	} // hideObject

	public void showObject (GameObject object, int x, int y) {
		((Visable) object).show (
			(x - windowX) * cellWidth,
			(y - windowY) * cellHeight,
			cellWidth, spaceGraphics
		);
	} // showObject

	public void hideObject (GameObject object, int x, int y) {
		((Visable) object).hide (
			(x - windowX) * cellWidth,
			(y - windowY) * cellHeight,
			cellWidth, spaceGraphics
		);
	} // hideObject

	// org.cs.GameSpace interface
	// ~~~~~~~~~~~~~~~~~~~

	public void start () {
		if (mainObject == null) {
			System.err.println ("org.cs.GameSpaceCanvas: start when mainObject is not defined.");
			System.exit (1);
		} else if (maze == null) {
			System.err.println ("org.cs.GameSpaceCanvas: start when maze is not defined.");
			System.exit (1);
		}
		// wait to awt initialization
		while (getGraphics () == null) {
			try {
				Thread.sleep (500);
			} catch (InterruptedException e) {}
		}
		spaceGraphics = getGraphics ();
		spaceGraphics.setColor (spaceColor);
		wallGraphics = getGraphics ();
		wallGraphics.setColor (wallColor);
		drawId = 0;
		started = true;
		super.resize (this.windowPixelWidth, this.windowPixelHeight);
		repaint ();
	}

	public void stop () {
		maze = null;
		mainObject = null;
		started = false;
		repaint ();
	}

	public void setMaze (GameMaze maze) {
		if (started) {
			System.err.println ("org.cs.GameSpaceCanvas: maze addad when started.");
			System.exit (1);
		}
		this.maze = maze;
		width = maze.getWidth ();
		height = maze.getHeight ();
	}

	public void setMainObject (GameObject object) {
		if (started) {
			System.err.println ("org.cs.GameSpaceCanvas: attemnpt to setMainObject when SameSpace is started.");
			System.exit (1);
		} else if (maze == null) {
			System.err.println ("org.cs.GameSpaceCanvas: attempt to steMainObject when maze is not defined.");
			System.exit (1);
		} else if (mainObject != null) {
			System.err.println ("org.cs.GameSpaceCanvas: attempt to setMainObject when mainObject is defined.");
			System.exit (1);
		}
		maze.addObject (object);
		windowX = object.getX () - 1 - (windowWidth - object.getWidth ()) / 2;
		windowY = object.getY () - 1 - (windowHeight - object.getHeight ()) / 2;
		mainObject = object;
	}

	public int getWidth () {
		if (maze == null) {
			System.err.println ("org.cs.GameSpaceCanvas: attept to getWidth when maze is not defined.");
			System.exit (1);
		}
		return width;
	}

	public int getHeight () {
		if (maze == null) {
			System.err.println ("org.cs.GameSpaceCanvas: attept to getWidth when maze is not defined.");
			System.exit (1);
		}
		return height;
	}

	public boolean isSpace (int x, int y) {
		if (maze == null) {
			System.err.println ("org.cs.GameSpaceCanvas: attept to isSpace when org.cs.GameSpace is not defined.");
			System.exit (1);
		}
		return (maze.getCell (x, y) instanceof Space);
	}

	public GameObject getObject (int x, int y) {
		if (maze == null) {
			System.err.println ("org.cs.GameSpaceCanvas: attept to getObject when org.cs.GameSpace is not defined.");
			System.exit (1);
		}
		return maze.getCell (x, y);
	}

	public void addObject (GameObject object) {
		int	x = object.getX (),
			y = object.getY ();
		maze.addObject (object, x, y);
		if (
			isConjunction (
				windowX, windowY, windowWidth, windowHeight,
				x, y, object.getWidth (), object.getHeight ()
			)
		){
			showObject (object, x, y);
		}
	}

	public void deleteObject (GameObject object) {
		int	x = object.getX (),
			y = object.getY ();
		maze.deleteObject (object);
		if (
			isConjunction (
				windowX, windowY, windowWidth, windowHeight,
				x, y, object.getWidth (), object.getHeight ()
			)
		){
			hideObject (object, x, y);
		}
	} // deleteObject

	public void positionChanged (GameObject object, int oldX, int oldY) {
		int	x = object.getX (),
			y = object.getY ();
		if (object == mainObject) {
			hideObject (object, oldX, oldY);
			maze.deleteObject (object, oldX, oldY);
			moveWindow (x - oldX, y - oldY);
			maze.addObject (object, x, y);
			showObject (object, x, y);
		} else {
			maze.deleteObject (object, oldX, oldY);
			if (
				isConjunction (
					windowX, windowY, windowWidth, windowHeight,
					oldX, oldY, object.getWidth (), object.getHeight ()
				)
			){
				hideObject (object, oldX, oldY);
			}
			maze.addObject (object, x, y);
			if (
				isConjunction (
					windowX, windowY, windowWidth, windowHeight,
					x, y, object.getWidth (), object.getHeight ()
				)
			){
				showObject (object, x, y);
			}
		}
	} // changeObjectPosition

	public void directionChanged (GameObject object, int oldDirection) {
		int	x = object.getX (),
			y = object.getY ();
		if (
			isConjunction (
				windowX, windowY, windowWidth, windowHeight,
				x, y, object.getWidth (), object.getHeight ()
			)
		){
			showObject (object, x, y);
		}
	}

}

