package org.cs;

import java.awt.*;

//public class org.cs.LocalGameRunner extends Window {
public class LocalGameRunner extends Frame {

	TextField	width, height, lines;
	Button		ok, cancel;

	public LocalGameRunner (Frame frame) {
		setLayout (new GridLayout (4, 2));
		// buttons
		add (new Label ("maze width:"));
		add (width = new TextField ("100"));
		add (new Label ("maze height:"));
		add (height = new TextField ("100"));
		add (new Label ("maze lines:"));
		add (lines = new TextField ("100"));
		add (ok = new Button ("Ok"));
		add (cancel = new Button ("Cancel"));
		// show
		pack ();
		show ();
	}

	public boolean action (Event evt, Object obj) {
		if (evt.target instanceof Button) {
			if (evt.target == ok) {
				doStart ();
			} else if (evt.target == cancel) {
				dispose ();
			}
		}
		return true;
	} // action

	private void doStart () {
		int	width, height, lines;
		try {
			width = Integer.parseInt (this.width.getText ());
		} catch (NumberFormatException e) {
			new PopUp ("width format error");
			return;
		}
		if (width < 50 || width > 200) {
			new PopUp ("width must be between 50 and 200");
			return;
		}
		try {
			height = Integer.parseInt (this.height.getText ());
		} catch (NumberFormatException e) {
			new PopUp ("height format error");
			return;
		}
		if (height < 50 || height > 200) {
			new PopUp ("height must be between 50 and 200");
			return;
		}
		try {
			lines = Integer.parseInt (this.lines.getText ());
		} catch (NumberFormatException e) {
			new PopUp ("lines format error");
			return;
		}
		if (width < 50 || width > 200) {
			new PopUp ("lines must be between 50 and 200");
			return;
		}
		new LocalGame (width, height, lines);
		dispose ();
	} // doStart

} // class org.cs.LocalGameRunner

