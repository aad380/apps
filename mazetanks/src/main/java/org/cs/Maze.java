package org.cs;

import java.util.*;

class MazeLine {
	final static int	NORTH = 1,
				SOUTH = 2,
				WEST = 3,
				EAST = 4;

	int		x1, y1, x2, y2, direction;

	MazeLine (int x1, int y1, int x2, int y2, int direction) throws IllegalArgumentException {
		if (x1 == x2) {
			if (y1 <= y2) {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
			} else {
				this.x1 = x1;
				this.y1 = y2;
				this.x2 = x2;
				this.y2 = y1;
			}
		} else if ( y1 == y2) {
			if (x1 <= x2) {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
			} else {
				this.x1 = x2;
				this.y1 = y1;
				this.x2 = x1;
				this.y2 = y2;
			}
		} else {
			throw new IllegalArgumentException ();
		}
		this.direction = direction;
	} // org.cs.MazeLine

} // class org.cs.MazeLine

public class Maze {

	private int		width, height;
	private boolean		maze [][];

	public Maze (int width, int height, int lines) {
		this.width = width;
		this.height = height;
		maze = generateMaze (width, height, lines);
	} // org.cs.Maze

	public boolean getCell (int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return true;
		}
		return maze [x][y];
	} // getMaze

	public void setCell (int x, int y, boolean value) {
		try {
			maze [x][y] = value;
		} catch (ArrayIndexOutOfBoundsException e) {}
	} // setCell

	public int getWidth () {
		return width;
	} // getWidth

	public int getHeight () {
		return height;
	} // getHeight

	public static boolean[][] generateMaze (int width, int height, int lines) {
		int	x1, y1, x2, y2, spaceLength;
		Random	random = new Random ();
		Vector	mazeLines = new Vector ();
		// init maze
		boolean	mazeSpace[][] = new boolean [width][height];
		for (int x = 0; x < width; ++ x) {
			for (int y = 0; y < height; ++ y) {
				mazeSpace [x][y] = false;
			}
		}
		// add border lines
		addMazeLine (mazeSpace, mazeLines, new MazeLine (0, 0, width - 1, 0, MazeLine.SOUTH));
		addMazeLine (mazeSpace, mazeLines, new MazeLine (0, height - 1, width - 1, height - 1, MazeLine.NORTH));
		addMazeLine (mazeSpace, mazeLines, new MazeLine (0, 1, 0, height - 2, MazeLine.EAST));
		addMazeLine (mazeSpace, mazeLines, new MazeLine (width - 1, 1, width - 1, height - 2, MazeLine.WEST));
		// add other lines
		for (int lineCounter = 4; lineCounter < lines;) {
			int		lineIndex = Math.abs (random.nextInt ()) % mazeLines.size ();
			MazeLine	line = (MazeLine) mazeLines.elementAt (lineIndex);
			if (line.x1 == line.x2) {
				switch (line.direction) {
				case MazeLine.EAST:
					if (Math.abs (line.y2 - line.y1) < 6) {
						continue;
					}
					x1 = x2 = line.x1 + 1;
					y1 = y2 = line.y1 + Math.abs (random.nextInt ()) % Math.abs (line.y2 - line.y1);
					spaceLength = 0;
					while (isGoodFreeCell (mazeSpace, x2, y2, MazeLine.EAST)) {
						++ x2;
						++ spaceLength;
					}
					if (spaceLength < 6) {
						continue;
					}
					x2 = x1 + 1 + Math.abs (random.nextInt () % (spaceLength - 4));
					addMazeLine (mazeSpace, mazeLines, new MazeLine (line.x1, y1 + 1, line.x2, line.y2, MazeLine.EAST));
					line.y2 = y1;	// patch original line, must be second
					addMazeLine (mazeSpace, mazeLines, new MazeLine (x1, y1, x2, y2, MazeLine.NORTH));
					addMazeLine (mazeSpace, mazeLines, line = new MazeLine (x1, y1, x2, y2, MazeLine.SOUTH));
					++ lineCounter;
					break;
				case MazeLine.WEST:
					if (Math.abs (line.y2 - line.y1) < 6) {
						continue;
					}
					x1 = x2 = line.x1 - 1;
					y1 = y2 = line.y1 + Math.abs (random.nextInt ()) % Math.abs (line.y2 - line.y1);
					spaceLength = 0;
					while (isGoodFreeCell (mazeSpace, x1, y1, MazeLine.WEST)) {
						-- x1;
						++ spaceLength;
					}
					if (spaceLength < 6) {
						continue;
					}
					x1 = x2 - 1 - Math.abs (random.nextInt () % (spaceLength - 4));
					addMazeLine (mazeSpace, mazeLines, new MazeLine (line.x1, y1 + 1, line.x2, line.y2, MazeLine.WEST));
					line.y2 = y1;	// patch original line, must be second
					addMazeLine (mazeSpace, mazeLines, new MazeLine (x1, y1, x2, y2, MazeLine.NORTH));
					addMazeLine (mazeSpace, mazeLines, line = new MazeLine (x1, y1, x2, y2, MazeLine.SOUTH));
					++ lineCounter;
					break;
				} // switch
			} else {
				switch (line.direction) {
				case MazeLine.SOUTH:
					if (Math.abs (line.x2 - line.x1) < 6) {
						continue;
					}
					x1 = x2 = line.x1 + Math.abs (random.nextInt ()) % Math.abs (line.x2 - line.x1);
					y1 = y2 = line.y1 + 1;
					spaceLength = 0;
					while (isGoodFreeCell (mazeSpace, x2, y2, MazeLine.SOUTH)) {
						++ y2;
						++ spaceLength;
					}
					if (spaceLength < 6) {
						continue;
					}
					y2 = y1 + 1 + Math.abs (random.nextInt () % (spaceLength - 4));
					addMazeLine (mazeSpace, mazeLines, new MazeLine (x1 + 1, line.y1, line.x2, line.y2, MazeLine.SOUTH));
					line.x2 = x1;	// patch original line, must be second
					addMazeLine (mazeSpace, mazeLines, new MazeLine (x1, y1, x2, y2, MazeLine.EAST));
					addMazeLine (mazeSpace, mazeLines, line = new MazeLine (x1, y1, x2, y2, MazeLine.WEST));
					++ lineCounter;
					break;
				case MazeLine.NORTH:
					if (Math.abs (line.x2 - line.x1) < 6) {
						continue;
					}
					x1 = x2 = line.x1 + Math.abs (random.nextInt ()) % Math.abs (line.x2 - line.x1);
					y1 = y2 = line.y1 - 1;
					spaceLength = 0;
					while (isGoodFreeCell (mazeSpace, x1, y1, MazeLine.NORTH)) {
						-- y1;
						++ spaceLength;
					}
					if (spaceLength < 6) {
						continue;
					}
					y1 = y2 - 1 - Math.abs (random.nextInt () % (spaceLength - 4));
					addMazeLine (mazeSpace, mazeLines, new MazeLine (x1 + 1, line.y1, line.x2, line.y2, MazeLine.NORTH));
					line.x2 = x1;	// patch original line, must be second
					addMazeLine (mazeSpace, mazeLines, new MazeLine (x1, y1, x2, y2, MazeLine.EAST));
					addMazeLine (mazeSpace, mazeLines, line = new MazeLine (x1, y1, x2, y2, MazeLine.WEST));
					++ lineCounter;
					break;
				} // switch

			} // if
		} // while
		return mazeSpace;
	} // generateMaze

	private static void addMazeLine (boolean mazeSpace[][], Vector mazeLines, MazeLine line) {
		mazeLines.addElement (line);
		if (line.x1 == line.x2) {
			for (int y = line.y1; y <= line.y2; ++ y) {
				mazeSpace [line.x1][y] = true;
			}
		} else {
			for (int x = line.x1; x <= line.x2; ++ x) {
				mazeSpace [x][line.y1] = true;
			}
		}
	} // addMazeLine

	private static boolean isGoodFreeCell (boolean mazeSpace[][], int x, int y, int direction) {
		try {
			switch (direction) {
			case MazeLine.EAST:
			case MazeLine.WEST:
				return ! (
					mazeSpace[x][y] ||
					mazeSpace[x][y+1] || mazeSpace[x][y-1] ||
					mazeSpace[x][y+2] || mazeSpace[x][y-2] ||
					mazeSpace[x][y+3] || mazeSpace[x][y-3]
				);
			case MazeLine.SOUTH:
			case MazeLine.NORTH:
				return ! (
					mazeSpace[x][y] ||
					mazeSpace[x+1][y] || mazeSpace[x-1][y] ||
					mazeSpace[x+2][y] || mazeSpace[x-2][y] ||
					mazeSpace[x+3][y] || mazeSpace[x-3][y]
				);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return false;
	} // isGoodFreeCell

} // class org.cs.Maze

