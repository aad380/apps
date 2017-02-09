package org.cs;

public class LocalGameMaze extends GameMaze {

	public LocalGameMaze (Maze maze, GameManager manager, GameSpaceCanvas gameSpace) {
		space = new VisableSpace (-1, -1, manager, gameSpace);
		wall = new VisableSolidWall (-1, -1, manager, gameSpace);
		width = maze.getWidth ();
		height = maze.getHeight ();
		this.maze = new GameObject [width][height];
		for (int i = 0; i < width; ++ i) {
			for (int j = 0; j < height; ++ j) {
				if (maze.getCell (i, j)) {
					this.maze [i][j] = new VisableWall (i, j, manager, gameSpace);
				} else {
					this.maze [i][j] = space;
				}
			}
		}
	} // org.cs.GameMaze

}

