package org.cs.snake;

import java.awt.*;

/**
 * Created by aad on 29.08.2016.
 */
class GameSpace extends Canvas {
    Dimension dimension;
    int xCells, yCells, imageWidth, imageHeight, borderSize,
            width, height;
    Color backgroundColor, borderColor;
    Cell cells[][];
    Graphics graphics;

    GameSpace(
            int xCells,
            int yCells,
            int imageWidth,
            int imageHeight,
            int borderSize,
            Color backgroundColor,
            Color borderColor
    ) {
        super();
        this.xCells = xCells;
        this.yCells = yCells;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.borderSize = borderSize;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        width = xCells * imageWidth + borderSize * 2;
        height = yCells * imageHeight + borderSize * 2;
        dimension = new Dimension(width, height);
        cells = new Cell[xCells][yCells];
        setBackground(backgroundColor);
        graphics = getGraphics();
    }

    public Dimension preferredSize() {
        return dimension;
    }

    public Dimension minimumSize() {
        return dimension;
    }

    public void update(Graphics g) {
        if (borderSize == 1) {
            g.setColor(borderColor);
            g.drawRect(0, 0, width - 1, height - 1);
        } else if (borderSize > 0) {
            g.setColor(borderColor);
            for (int i = 0; i < borderSize; ++i) {
                g.drawRect(i, i, width - 1 - i * 2, height - 1 - i * 2);
            }
        }

        for (int x = 0, xOffset = borderSize; x < xCells; ++x, xOffset += imageWidth) {
            for (int y = 0, yOffset = borderSize; y < yCells; ++y, yOffset += imageHeight) {
                if (cells[x][y] != null) {
                    g.drawImage(cells[x][y].image, xOffset, yOffset, backgroundColor, this);
                }
            }
        }
    } // update

    public void paint(Graphics g) {
        update(g);
    } // paint

    public synchronized void addCell(int x, int y, Cell cell) {
        cells[x][y] = cell;
        if (graphics == null) {
            graphics = getGraphics();
            if (graphics == null) {
                return;
            }
        }
        graphics.drawImage(
                cell.image, x * imageWidth + borderSize, y * imageHeight + borderSize,
                backgroundColor, this
        );
    }

    public synchronized Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public synchronized void deleteCell(int x, int y) {
        if (cells[x][y] != null) {
            cells[x][y] = null;
            if (graphics == null) {
                graphics = getGraphics();
                if (graphics == null) {
                    return;
                }
            }
            graphics.clearRect(
                    x * imageWidth + borderSize, y * imageHeight + borderSize, imageWidth, imageHeight
            );
        }
    }

    public synchronized void deleteAll() {
        cells = new Cell[xCells][yCells];
        if (graphics == null) {
            graphics = getGraphics();
            if (graphics == null) {
                return;
            }
        }
        if (borderSize == 1) {
            graphics.setColor(borderColor);
            graphics.drawRect(0, 0, width - 1, height - 1);
        } else if (borderSize > 0) {
            graphics.setColor(borderColor);
            for (int i = 0; i < borderSize; ++i) {
                graphics.drawRect(i, i, width - 1 - i * 2, height - 1 - i * 2);
            }
        }
        graphics.clearRect(borderSize, borderSize, width - borderSize * 2, height - borderSize * 2);
    }

    public Dimension getGameSpaceSize() {
        return new Dimension(xCells, yCells);
    }

} // class GameSpace
