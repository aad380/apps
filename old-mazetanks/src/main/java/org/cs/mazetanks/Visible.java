package org.cs.mazetanks;

import java.awt.*;

/**
 * Cell interface
 *
 * @author Alexander Dudarenko
 */
interface Visible {

    /**
     * Gets horisontal coordinate
     */
    int getX();

    /**
     * Gets vertical coordinate
     */
    int getY();

    /**
     * Gets width in cells
     */
    int getWidth();

    /**
     * Gets height in cells
     */
    int getHeight();

    /**
     * Get Image for current image position and state
     */
    Image getImage(int cellSize);

    /**
     * show object in graphics context
     */
    void show(int x, int y, int cellSize, Graphics graphics, int sequenceId);

    /**
     * show object in graphics context
     */
    void show(int x, int y, int cellSize, Graphics graphics);

    void hide(int x, int y, int cellSize, Graphics graphics, int sequenceId);

    void hide(int x, int y, int cellSize, Graphics graphics);

    boolean isHidden();

    void setHidden(boolean flag);

    int getDrawSequenceId();

}

