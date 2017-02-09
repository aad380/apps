package org.cs;

public class GameMaze {

	protected int		width, height;
	protected GameObject	maze[][], space, wall;

	public GameMaze () {}	// for org.cs.LocalGameMaze

	public GameMaze (Maze maze, GameManager manager) {
		space = new Space (-1, -1, manager);
		wall = new SolidWall (-1, -1, manager);
		width = maze.getWidth ();
		height = maze.getHeight ();
		this.maze = new GameObject [width][height];
		for (int i = 0; i < width; ++ i) {
			for (int j = 0; j < height; ++ j) {
				if (maze.getCell (i, j)) {
					this.maze [i][j] = new Wall (i, j, manager);
				} else {
					this.maze [i][j] = space;
				}
			}
		}
	} // org.cs.GameMaze

	public GameObject fastGetCell (int x, int y) {
		return maze [x][y];
	} // fastGetCell

	public GameObject getCell (int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return wall;
		}
		return maze [x][y];
	} // getCell

	public void setCell (GameObject object, int x, int y) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			maze [x][y] = object;
		}
	} // setCell

	public void addObject (GameObject object) {
		addObject (object, object.getX (), object.getY ());
	} // addObject

	public void deleteObject (GameObject object) {
		deleteObject (object, object.getX (), object.getY ());
	} // deleteObject

	public void addObject (GameObject object, int x, int y) {
		int	lastX = x + object.getWidth (),
			lastY = y + object.getHeight ();
		try {
			for (int i = x; i < lastX; ++ i) {
				for (int j = y; j < lastY; ++ j) {
					maze [i][j] = object;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	} // addObject

	public void deleteObject (GameObject object, int x, int y) {
		int	lastX = x + object.getWidth (),
			lastY = y + object.getHeight ();
		try {
			for (int i = x; i < lastX; ++ i) {
				for (int j = y; j < lastY; ++ j) {
					maze [i][j] = space;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	} // deleteObject

	public int getWidth () {
		return width;
	} // getWidth

	public int getHeight () {
		return height;
	} // getHeight

}

