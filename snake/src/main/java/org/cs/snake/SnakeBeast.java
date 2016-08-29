package org.cs.snake;

import java.awt.*;
import java.util.Random;

/**
 * Created by aad on 29.08.2016.
 */
class SnakeBeast extends Thread {

	private final static int	UP = 0,
					RIGHT = 1,
					DOWN = 2,
					LEFT = 3;

	final static int		TWO_ARROWS = 0,
					FOUR_ARROWS = 1;

	public boolean	gameOver;

	GameSpace	gameSpace;
	KeyQueue	keyQueue;
	SnakeGame	manager;
	Random random;
	int		deltaX[][], deltaY[][],
			xSize, ySize, level, steps, bonuses, lives, arrowMode,
			headX, headY, tailX, tailY, direction, length, score;
	boolean		eatMySelf, pause;

	SnakeBeast (
		int		startLength,
		int		level,
		int		arrowMode,
		GameSpace	gameSpace,
		KeyQueue	keyQueue,
		SnakeGame	manager
	){
		Dimension gameSpaceDimension = gameSpace.getGameSpaceSize ();
		xSize = gameSpaceDimension.width;
		ySize = gameSpaceDimension.height;
		this.gameSpace = gameSpace;
		this.keyQueue = keyQueue;
		this.manager = manager;
		this.level = level;
		this.arrowMode = arrowMode;
		random = new Random ();
		deltaX = new int [xSize][ySize];
		deltaY = new int [xSize][ySize];
		length = startLength;
		gameOver = false;
	}

	public void init () {
		if (isAlive ()) {
			System.err.println ("attempt to init runned " + this);
			System.exit (1);
		}
		score = steps = bonuses = lives = 0;
		direction = LEFT;
		headX = (xSize - length) / 2;
		tailX = headX + length -1;
		headY = tailY = ySize / 2;
		gameSpace.addCell (headX, headY, Cell.get (Cell.SNAKE_HEAD_L));
		for (int x = headX + 1; x < tailX; ++ x) {
			gameSpace.addCell (x, headY, Cell.get (Cell.SNAKE_BODY_H));
			deltaX [x][headY] = -1;
			deltaY [x][headY] = 0;
		}
		gameSpace.addCell (tailX, headY, Cell.get (Cell.SNAKE_TAIL_L));
		deltaX [tailX][headY] = -1;
		deltaY [tailX][headY] = 0;
		pause = false;
	}

	public void setLevel (int level) {
		this.level = level;
	}

	public void setArrowMode (int mode) {
		this.arrowMode = mode;
	}

	public boolean onDirection (int x, int y) {
		switch (direction) {
		case LEFT:
			return headY == y && headX >= x;
		case RIGHT:
			return headY == y && headX <= x;
		case UP:
			return headX == x && headY >= y;
		case DOWN:
			return headX == x && headY <= y;
		}
		return false;
	}

	public void run () {
		while (!gameOver) {
			if (pause) {
				try {
					sleep (250);
				} catch (InterruptedException e) {}
			} else {
				try {
					sleep ((11 - level) * 25);
				} catch (InterruptedException e) {}
				if (!pause) {
					step ();
					manager.printStatus (length, score, lives, steps);
				}
			}
		}
		killSnake ();
		manager.gameOver ();
		// not reached
	}

	private void step () {
		int	dx = 0,
			dy = 0;
		Cell	newHeadCell = null,
			newBodyCell = null,
			oldCell = null;
		// move tail
		if (bonuses <= 0) {
			gameSpace.deleteCell (tailX, tailY);
			dx = deltaX [tailX][tailY];
			dy = deltaY [tailX][tailY];
			tailX += dx;
			tailY += dy;
			dx = deltaX [tailX][tailY];
			dy = deltaY [tailX][tailY];
			if (dx != 0) {
				switch (dx) {
				case 1:
					gameSpace.addCell (tailX, tailY, Cell.get (Cell.SNAKE_TAIL_R));
					break;
				case -1:
					gameSpace.addCell (tailX, tailY, Cell.get (Cell.SNAKE_TAIL_L));
					break;
				}
			} else {
				switch (dy) {
				case 1:
					gameSpace.addCell (tailX, tailY, Cell.get (Cell.SNAKE_TAIL_D));
					break;
				case -1:
					gameSpace.addCell (tailX, tailY, Cell.get (Cell.SNAKE_TAIL_U));
					break;
				}
			}
		} else {
			-- bonuses;
			++ length;
		}
		// move head
		analiseKey: while (true) {
			if (arrowMode == TWO_ARROWS) {
				switch (keyQueue.getKey ()) {
				case KeyQueue.LEFT:
					switch (direction) {
					case LEFT:
						direction = DOWN;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_D);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RD);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = 1;
						break analiseKey;
					case RIGHT:
						direction = UP;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_U);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LU);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = -1;
						break analiseKey;
					case UP:
						direction = LEFT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_L);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LD);
						deltaX [headX][headY] = dx = -1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					case DOWN:
						direction = RIGHT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_R);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RU);
						deltaX [headX][headY] = dx = 1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					}
				case KeyQueue.RIGHT:
					switch (direction) {
					case LEFT:
						direction = UP;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_U);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RU);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = -1;
						break analiseKey;
					case RIGHT:
						direction = DOWN;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_D);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LD);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = 1;
						break analiseKey;
					case UP:
						direction = RIGHT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_R);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RD);
						deltaX [headX][headY] = dx = 1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					case DOWN:
						direction = LEFT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_L);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LU);
						deltaX [headX][headY] = dx = -1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					}
				} // switch key
			} else {
				switch (keyQueue.getKey ()) {
				case KeyQueue.LEFT:
					switch (direction) {
					case UP:
						direction = LEFT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_L);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LD);
						deltaX [headX][headY] = dx = -1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					case DOWN:
						direction = LEFT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_L);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LU);
						deltaX [headX][headY] = dx = -1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					}
					break;
				case KeyQueue.RIGHT:
					switch (direction) {
					case UP:
						direction = RIGHT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_R);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RD);
						deltaX [headX][headY] = dx = 1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					case DOWN:
						direction = RIGHT;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_R);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RU);
						deltaX [headX][headY] = dx = 1;
						deltaY [headX][headY] = dy = 0;
						break analiseKey;
					}
					break;
				case KeyQueue.UP:
					switch (direction) {
					case LEFT:
						direction = UP;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_U);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RU);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = -1;
						break analiseKey;
					case RIGHT:
						direction = UP;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_U);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LU);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = -1;
						break analiseKey;
					}
					break;
				case KeyQueue.DOWN:
					switch (direction) {
					case LEFT:
						direction = DOWN;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_D);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_RD);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = 1;
						break analiseKey;
					case RIGHT:
						direction = DOWN;
						newHeadCell = Cell.get (Cell.SNAKE_HEAD_D);
						newBodyCell = Cell.get (Cell.SNAKE_BODY_LD);
						deltaX [headX][headY] = dx = 0;
						deltaY [headX][headY] = dy = 1;
						break analiseKey;
					}
					break;
				} // switch key
			} // if
			switch (direction) {
			case LEFT:
				newHeadCell = Cell.get (Cell.SNAKE_HEAD_L);
				newBodyCell = Cell.get (Cell.SNAKE_BODY_H);
				deltaX [headX][headY] = dx = -1;
				deltaY [headX][headY] = dy = 0;
				break analiseKey;
			case RIGHT:
				newHeadCell = Cell.get (Cell.SNAKE_HEAD_R);
				newBodyCell = Cell.get (Cell.SNAKE_BODY_H);
				deltaX [headX][headY] = dx = 1;
				deltaY [headX][headY] = dy = 0;
				break analiseKey;
			case UP:
				newHeadCell = Cell.get (Cell.SNAKE_HEAD_U);
				newBodyCell = Cell.get (Cell.SNAKE_BODY_V);
				deltaX [headX][headY] = dx = 0;
				deltaY [headX][headY] = dy = -1;
				break analiseKey;
			case DOWN:
				newHeadCell = Cell.get (Cell.SNAKE_HEAD_D);
				newBodyCell = Cell.get (Cell.SNAKE_BODY_V);
				deltaX [headX][headY] = dx = 0;
				deltaY [headX][headY] = dy = 1;
				break analiseKey;
			}
			break analiseKey;
		} // analiseKey
		gameSpace.addCell (headX, headY, newBodyCell);
		headX += dx;
		headY += dy;
		oldCell = gameSpace.getCell (headX, headY);	// should be analized in future
		gameSpace.addCell (headX, headY, newHeadCell);
		// alanlize beast
		if (oldCell != null) {
			switch (oldCell.type) {
			case Cell.DEATH_TYPE:
				if (lives <= 0) {
					eatMySelf = false;
					gameOver = true;
					return;
				}
				score += 50;
				bonuses += 5;
				-- lives;
				break;
			case Cell.SNAKE_TYPE:
				eatMySelf = true;
				gameOver = true;
				return;
			case Cell.BORDER_TYPE:
				eatMySelf = false;
				gameOver = true;
				return;
			case Cell.LIFE_TYPE:
				++ lives;
				break;
			case Cell.GOLD_TYPE:
				score += 100;
				bonuses += 10;
				break;
			case Cell.SILVER_TYPE:
				score += 10;
				bonuses += 1;
				break;
			}
		}
		++ steps;
		if (steps % 10 == 0) {
			++ bonuses;
			++ score;
		}
	} // step

	void killSnake () {
		int	x, y, dx, dy, id;
		try {sleep (1000);} catch (InterruptedException e) {}
		x = tailX;
		y = tailY;
		while (true) {
			dx = deltaX [x][y];
			dy = deltaY [x][y];
			if  (eatMySelf && x == headX && y == headY) {
				eatMySelf = false;
				x += dx;
				y += dy;
				continue;
			}
			id = gameSpace.getCell(x, y).id;
			switch (id) {
				case Cell.SNAKE_HEAD_R: id = Cell.DID_SNAKE_HEAD_R; break;
				case Cell.SNAKE_HEAD_L: id = Cell.DID_SNAKE_HEAD_L; break;
				case Cell.SNAKE_HEAD_U: id = Cell.DID_SNAKE_HEAD_U; break;
				case Cell.SNAKE_HEAD_D: id = Cell.DID_SNAKE_HEAD_D; break;
				case Cell.SNAKE_BODY_H: id = Cell.DID_SNAKE_BODY_H; break;
				case Cell.SNAKE_BODY_V: id = Cell.DID_SNAKE_BODY_V; break;
				case Cell.SNAKE_BODY_RU: id = Cell.DID_SNAKE_BODY_RU; break;
				case Cell.SNAKE_BODY_RD: id = Cell.DID_SNAKE_BODY_RD; break;
				case Cell.SNAKE_BODY_LU: id = Cell.DID_SNAKE_BODY_LU; break;
				case Cell.SNAKE_BODY_LD: id = Cell.DID_SNAKE_BODY_LD; break;
				case Cell.SNAKE_TAIL_R: id = Cell.DID_SNAKE_TAIL_R; break;
				case Cell.SNAKE_TAIL_L: id = Cell.DID_SNAKE_TAIL_L; break;
				case Cell.SNAKE_TAIL_U: id = Cell.DID_SNAKE_TAIL_U; break;
				case Cell.SNAKE_TAIL_D: id = Cell.DID_SNAKE_TAIL_D; break;
			}
			gameSpace.addCell(x, y, Cell.get (id));
			if  (x == headX && y == headY) {
				break;
			}
			x += dx;
			y += dy;
		}
	}

} // class SnakeBeast
