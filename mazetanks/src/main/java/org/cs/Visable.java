package org.cs;

import	java.awt.Image;
import	java.awt.Graphics;

/**
* Cell interface
*
* @author Alexander Dudarenko
*/
interface Visable {

	/**
	* Gets horisontal coordinate
	*/
	public int getX ();

	/**
	* Gets vertical coordinate
	*/
	public int getY ();

	/**
	* Gets width in cells
	*/
	public int getWidth ();

	/**
	* Gets height in cells
	*/
	public int getHeight ();

	/**
	* Get Image for current image position and state
	*/
	public Image getImage (int cellSize);

	/**
	* show object in graphics context
	*/
	public void show (int x, int y, int cellSize, Graphics graphics, int sequenceId);

	/**
	* show object in graphics context
	*/
	public void show (int x, int y, int cellSize, Graphics graphics);

	public void hide (int x, int y, int cellSize, Graphics graphics, int sequenceId);

	public void hide (int x, int y, int cellSize, Graphics graphics);

	public boolean isHiden ();

	public void setHiden (boolean flag);

	public int getDrawSequenceId ();

}

