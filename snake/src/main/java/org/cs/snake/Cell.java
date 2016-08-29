package org.cs.snake;

import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by aad on 29.08.2016.
 */
class Cell {

    // CLASS PART

    public final static int BLANK_TYPE = 0,
            SNAKE_TYPE = 1,
            LIFE_TYPE = 2,
            DEATH_TYPE = 3,
            GOLD_TYPE = 4,
            SILVER_TYPE = 5,
            BORDER_TYPE = 6,
            DID_SNAKE_TYPE = 7,

    BLANK = 0,

    SNAKE_HEAD_R = 1,
            SNAKE_HEAD_L = 2,
            SNAKE_HEAD_U = 3,
            SNAKE_HEAD_D = 4,
            SNAKE_BODY_H = 5,
            SNAKE_BODY_V = 6,
            SNAKE_BODY_RU = 7,
            SNAKE_BODY_RD = 8,
            SNAKE_BODY_LU = 9,
            SNAKE_BODY_LD = 10,
            SNAKE_TAIL_R = 11,
            SNAKE_TAIL_L = 12,
            SNAKE_TAIL_U = 13,
            SNAKE_TAIL_D = 14,

    LIFE = 15,
            DEATH = 16,
            GOLD = 17,
            SILVER = 18,
            BORDER_R = 19,
            BORDER_L = 20,
            BORDER_U = 21,
            BORDER_D = 22,
            BORDER_LU = 23,
            BORDER_RU = 24,
            BORDER_RD = 25,
            BORDER_LD = 26,
            DID_SNAKE_HEAD_R = 27,
            DID_SNAKE_HEAD_L = 28,
            DID_SNAKE_HEAD_U = 29,
            DID_SNAKE_HEAD_D = 30,
            DID_SNAKE_BODY_H = 31,
            DID_SNAKE_BODY_V = 32,
            DID_SNAKE_BODY_RU = 33,
            DID_SNAKE_BODY_RD = 34,
            DID_SNAKE_BODY_LU = 35,
            DID_SNAKE_BODY_LD = 36,
            DID_SNAKE_TAIL_R = 37,
            DID_SNAKE_TAIL_L = 38,
            DID_SNAKE_TAIL_U = 39,
            DID_SNAKE_TAIL_D = 40;

    private static final String separator = "/"; // File.separator doesn't work with jars :(

    private static String imagesPath = ".";

    private static Vector cells;

    private static void addCell(int id, int type, String fileName, Applet applet) {
        Image image;
        if (applet != null) {
            image = applet.getImage(applet.getDocumentBase(), imagesPath + separator + fileName);
        } else {
            URL url = Cell.class.getClassLoader().getResource(imagesPath + separator + fileName);
            image = Toolkit.getDefaultToolkit().getImage(url);
        }
        cells.setElementAt(new Cell(image, type, id), id);
    }

    public static void initCells(String imagesPath, Component component) {
        initCells(imagesPath, component, null);
    }

    public static void initCells(String imagesPath, Component component, Applet applet) {
        Cell.imagesPath = imagesPath;
        cells = new Vector();
        cells.setSize(50);
        if (applet != null) {
            applet.showStatus("Load images.");
        }
        addCell(BLANK, BLANK_TYPE, "Blank.gif", applet);
        addCell(SNAKE_HEAD_R, SNAKE_TYPE, "SnakeHead_r.gif", applet);
        addCell(SNAKE_HEAD_L, SNAKE_TYPE, "SnakeHead_l.gif", applet);
        addCell(SNAKE_HEAD_U, SNAKE_TYPE, "SnakeHead_u.gif", applet);
        addCell(SNAKE_HEAD_D, SNAKE_TYPE, "SnakeHead_d.gif", applet);
        addCell(SNAKE_BODY_H, SNAKE_TYPE, "SnakeBody_h.gif", applet);
        addCell(SNAKE_BODY_V, SNAKE_TYPE, "SnakeBody_v.gif", applet);
        addCell(SNAKE_BODY_RU, SNAKE_TYPE, "SnakeBody_ru.gif", applet);
        addCell(SNAKE_BODY_RD, SNAKE_TYPE, "SnakeBody_rd.gif", applet);
        addCell(SNAKE_BODY_LU, SNAKE_TYPE, "SnakeBody_lu.gif", applet);
        addCell(SNAKE_BODY_LD, SNAKE_TYPE, "SnakeBody_ld.gif", applet);
        addCell(SNAKE_TAIL_R, SNAKE_TYPE, "SnakeTail_r.gif", applet);
        addCell(SNAKE_TAIL_L, SNAKE_TYPE, "SnakeTail_l.gif", applet);
        addCell(SNAKE_TAIL_U, SNAKE_TYPE, "SnakeTail_u.gif", applet);
        addCell(SNAKE_TAIL_D, SNAKE_TYPE, "SnakeTail_d.gif", applet);
        addCell(LIFE, LIFE_TYPE, "Life.gif", applet);
        addCell(DEATH, DEATH_TYPE, "Death.gif", applet);
        addCell(GOLD, GOLD_TYPE, "Gold.gif", applet);
        addCell(SILVER, SILVER_TYPE, "Silver.gif", applet);
        addCell(BORDER_R, BORDER_TYPE, "Border_r.gif", applet);
        addCell(BORDER_L, BORDER_TYPE, "Border_l.gif", applet);
        addCell(BORDER_U, BORDER_TYPE, "Border_u.gif", applet);
        addCell(BORDER_D, BORDER_TYPE, "Border_d.gif", applet);
        addCell(BORDER_LU, BORDER_TYPE, "Border_lu.gif", applet);
        addCell(BORDER_RU, BORDER_TYPE, "Border_ru.gif", applet);
        addCell(BORDER_RD, BORDER_TYPE, "Border_rd.gif", applet);
        addCell(BORDER_LD, BORDER_TYPE, "Border_ld.gif", applet);
        addCell(DID_SNAKE_HEAD_R, DID_SNAKE_TYPE, "DidSnakeHead_r.gif", applet);
        addCell(DID_SNAKE_HEAD_L, DID_SNAKE_TYPE, "DidSnakeHead_l.gif", applet);
        addCell(DID_SNAKE_HEAD_U, DID_SNAKE_TYPE, "DidSnakeHead_u.gif", applet);
        addCell(DID_SNAKE_HEAD_D, DID_SNAKE_TYPE, "DidSnakeHead_d.gif", applet);
        addCell(DID_SNAKE_BODY_H, DID_SNAKE_TYPE, "DidSnakeBody_h.gif", applet);
        addCell(DID_SNAKE_BODY_V, DID_SNAKE_TYPE, "DidSnakeBody_v.gif", applet);
        addCell(DID_SNAKE_BODY_RU, DID_SNAKE_TYPE, "DidSnakeBody_ru.gif", applet);
        addCell(DID_SNAKE_BODY_RD, DID_SNAKE_TYPE, "DidSnakeBody_rd.gif", applet);
        addCell(DID_SNAKE_BODY_LU, DID_SNAKE_TYPE, "DidSnakeBody_lu.gif", applet);
        addCell(DID_SNAKE_BODY_LD, DID_SNAKE_TYPE, "DidSnakeBody_ld.gif", applet);
        addCell(DID_SNAKE_TAIL_R, DID_SNAKE_TYPE, "DidSnakeTail_r.gif", applet);
        addCell(DID_SNAKE_TAIL_L, DID_SNAKE_TYPE, "DidSnakeTail_l.gif", applet);
        addCell(DID_SNAKE_TAIL_U, DID_SNAKE_TYPE, "DidSnakeTail_u.gif", applet);
        addCell(DID_SNAKE_TAIL_D, DID_SNAKE_TYPE, "DidSnakeTail_d.gif", applet);
        MediaTracker mediaTracker = new MediaTracker(component);
        for (Enumeration e = cells.elements(); e.hasMoreElements(); ) {
            Cell cell = (Cell) e.nextElement();
            if (cell == null) {
                continue;
            }
            mediaTracker.addImage(cell.image, 0);
        }
        try {
            mediaTracker.waitForAll();
        } catch (InterruptedException e) {
            System.err.println("image load interrupted.");
            System.exit(1);
        }
        if (mediaTracker.isErrorAny()) {
            System.err.println("image load error.");
            System.exit(1);
        }
        if (applet != null) {
            applet.showStatus("");
        }
    }

    public static Cell get(int id) {
        return (Cell) cells.elementAt(id);
    }

    // OBJECT PART

    public Image image;
    public int type, id;

    Cell(Image image, int type, int id) {
        this.image = image;
        this.type = type;
        this.id = id;
    }

} // class Cell
