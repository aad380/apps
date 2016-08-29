package org.cs.snake;

import java.awt.*;
import java.applet.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Snake extends Applet {

	static SnakeGame	manager;
	static String		infoFile = "readme.txt";
	static String		imagesPath = "images";

	static TextArea		aboutTextArea;

	public static void main (String args[]) {
		aboutTextArea = new TextArea ();
		aboutTextArea.setEditable (false);
		(new LoadInfo (aboutTextArea, infoFile, null)).start ();
		Frame	f = new Frame ("Snake");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		Cell.initCells (imagesPath, f, null);
		manager = new SnakeGame (aboutTextArea);
		f.setLayout (new CardLayout ());
		f.add (manager, "manager");
		f.resize (600, 460);
		f.show ();
	}

	public void init () {
		if (getParameter ("Info") != null) {
			infoFile = getParameter ("Info");
		}
		if (getParameter ("ImagesPath") != null) {
			imagesPath = getParameter ("ImagesPath");
		}
		Cell.initCells (imagesPath, this, this);
		aboutTextArea = new TextArea ();
		aboutTextArea.setEditable (false);
		(new LoadInfo (aboutTextArea, infoFile, this)).start ();
	}

	public void start () {
		manager = new SnakeGame (aboutTextArea, this);
		setLayout (new FlowLayout ());
		add (manager);
		resize (600, 460);
		show ();
	}

	public void stop () {
		removeAll ();
		manager = null;
	}

	public void destroy () {
		removeAll ();
		manager = null;
	}

} // class Snake

