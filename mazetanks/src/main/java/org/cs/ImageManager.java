package org.cs;

import	java.awt.Toolkit;
import	java.awt.Image;
import	java.awt.MediaTracker;
import	java.awt.Component;
import	java.util.Hashtable;
import	java.util.Enumeration;

class ImageManager {
	private static String		imageRoot = "images/";
	private static Hashtable	images = new Hashtable ();
	private static Toolkit		toolkit = Toolkit.getDefaultToolkit ();

	public static Image getImage (String imageName) {
		if (images.containsKey (imageName)) {
			return (Image) images.get (imageName);
		}
		Image	image = toolkit.getImage (imageRoot + imageName);
		images.put (imageName, image);
		return image;
	} // getImage

	public static void loadAll (Component component) {
		MediaTracker	mediaTracker = new MediaTracker (component);
		Hashtable	names = new Hashtable ();
		for (Enumeration e = images.keys (); e.hasMoreElements ();) {
			String name = (String) e.nextElement();
			Image image = (Image) images.get (name);
			names.put (image, name);
			mediaTracker.addImage (image, 0);
		}
		try {
			mediaTracker.waitForAll ();
		} catch (InterruptedException e) {
			System.err.println ("org.cs.ImageManager: images loading interrupted.");
			System.exit (1);
		}
		if (mediaTracker.isErrorAny ()) {
			Object[] badImages = (Object[]) mediaTracker.getErrorsAny ();
			if (badImages != null) {
				for (int i = 0; i < badImages.length; ++ i) {
					System.err.println ("org.cs.ImageManager: can't load image: " + names.get (badImages [i]));
				}
			} else {
				System.err.println ("org.cs.ImageManager: images loading error.");
			}
			System.exit (1);
		}
	}

}

