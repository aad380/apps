package org.cs.snake;

import java.awt.*;
import java.util.Random;

/**
 * Created by aad on 29.08.2016.
 */
class BeastGenerator extends Thread {

    private final static int SILVER_LEVEL = 500;
    private final static int DEATH_LEVEL = 850;
    private final static int LIFE_LEVEL = 940;
    private final static int GOLD_LEVEL = 1000;

    private GameSpace gameSpace;
    private SnakeBeast snake;
    private Random random;
    private int xCells;
    private int yCells;

    boolean pause;

    BeastGenerator(GameSpace gameSpace, SnakeBeast snake) {
        this.gameSpace = gameSpace;
        this.snake = snake;
        random = new Random();
        Dimension d = gameSpace.getGameSpaceSize();
        xCells = d.width;
        yCells = d.height;
        pause = false;
    }

    public void run() {
        while (true) {
            if (pause) {
                try {
                    sleep(250);
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    sleep(Math.abs(random.nextLong()) % 8000L);
                } catch (InterruptedException e) {
                }
                if (!pause) {
                    addBeast();
                }
            }
        }

    }

    private void addBeast() {
        int x = Math.abs(random.nextInt()) % xCells,
                y = Math.abs(random.nextInt()) % yCells;
        if (gameSpace.getCell(x, y) != null) {
            return;
        }
        int level = Math.abs(random.nextInt()) % GOLD_LEVEL,
                id = Cell.SILVER;
        if (level <= SILVER_LEVEL) {
            id = Cell.SILVER;
        } else if (level <= DEATH_LEVEL) {
            if (snake.onDirection(x, y)) {
                return;
            }
            id = Cell.DEATH;
        } else if (level <= LIFE_LEVEL) {
            id = Cell.LIFE;
        } else {
            id = Cell.GOLD;
        }
        gameSpace.addCell(x, y, Cell.get(id));
    }

} // class BeastGenerator
