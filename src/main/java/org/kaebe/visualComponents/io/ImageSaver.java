package org.kaebe;

import java.awt.image.BufferedImage;

public class ImageSaver {

    public static void saveImage(BufferedImage image, String name) {
        try {
            javax.imageio.ImageIO.write(image, "png", new java.io.File(name));
        } catch (java.io.IOException e) {
            System.out.println("Failed to save image: " + e.getMessage());
        }
    }
}
