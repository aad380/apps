package org.cs.mazetanks;

import java.awt.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

class ImageManager {
    private static String imageRoot = "images/";
    private static Hashtable images = new Hashtable();
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static Image getImage(String imageName) {
        if (images.containsKey(imageName)) {
            return (Image) images.get(imageName);
        }
        URL url = ImageManager.class.getClassLoader().getResource(imageRoot + imageName);
        Image image = toolkit.getImage(url);
        images.put(imageName, image);
        return image;
    } // getImage

    public static void loadAll(Component component) {
        MediaTracker mediaTracker = new MediaTracker(component);
        Hashtable names = new Hashtable();
        for (Enumeration e = images.keys(); e.hasMoreElements(); ) {
            String name = (String) e.nextElement();
            Image image = (Image) images.get(name);
            names.put(image, name);
            mediaTracker.addImage(image, 0);
        }
        try {
            mediaTracker.waitForAll();
        } catch (InterruptedException e) {
            System.err.println("ImageManager: images loading interrupted.");
            System.exit(1);
        }
        if (mediaTracker.isErrorAny()) {
            Object[] badImages = (Object[]) mediaTracker.getErrorsAny();
            if (badImages != null) {
                for (int i = 0; i < badImages.length; ++i) {
                    System.err.println("ImageManager: can't load image: " + names.get(badImages[i]));
                }
            } else {
                System.err.println("ImageManager: images loading error.");
            }
            System.exit(1);
        }
    }

}

