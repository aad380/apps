package org.cs.snake;

import java.applet.Applet;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by aad on 29.08.2016.
 */
class LoadInfo extends Thread {

	TextArea ta;
	String		fileName;
	Applet applet;

	LoadInfo (TextArea ta, String fileName, Applet applet) {
		this.ta = ta;
		this.fileName = fileName;
		this.applet = applet;
	}

	public void run () {
		String	info = "*** Info is not yet loaded ***\n",
			newInfo = "",
			line;
		ta.appendText (info);
		DataInputStream is;
		try {
			if (applet == null) {
				is = new DataInputStream (this.getClass().getClassLoader().getResourceAsStream(fileName));
			} else {
				is = new DataInputStream ((new URL(applet.getDocumentBase (), fileName)).openStream());
			}
			while ((line = is.readLine ()) != null) {
				newInfo += line + "\n";
				yield ();
			}
		} catch (IOException e) {
			ta.appendText ("*** IOException: while reading " + fileName + " ***\n");
		}
		ta.replaceText (newInfo, 0, info.length ());
	} // run

} // class LoadInfo
