/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 */

package contac.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Manage utils for image handler
 */
public class ImageUtils {

    //Logger
    private static final Logger logger = Logger.getLogger(ImageUtils.class);

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    /**
     * Get the extension of a file
     * @param f, File
     * @return String
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }

        return ext;
    }

    /**
     * Convertir image to byte array
     * @param image, Image
     * @return byte[]
     */
    public static byte[] convertoToByteArray(Image image) {

        //Transform to BufferedImage
        BufferedImage bffImage = null;

        try {
            GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration();
            bffImage = graphicsConfiguration.createCompatibleImage(image.getWidth(null), image.getHeight(null));
            Graphics g = bffImage.getGraphics();
            g.drawImage(image, 0, 0, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
            encoder.encode(bffImage);

            return baos.toByteArray();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Convert array to Image
     * @param array, byte[]
     * @return Image
     */
    public static Image convertToImage(byte[] array) {
        if (array != null)
            return Toolkit.getDefaultToolkit().createImage(array);

        return null;
    }

    /**
     * Return an ImageIcon, or null if the path was invalid.
     * @param path, String
     * @return ImageIcon
     */
    protected static ImageIcon createImage(String path) {
        java.net.URL imgURL = ImageUtils.class.getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
