/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 */

package contac.image;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Image preview class for thumnails
 */
public class ImagePreview extends JComponent implements PropertyChangeListener {

    ImageIcon thumbnail = null;
    File file = null;

    public ImagePreview(JFileChooser jfc) {
        setPreferredSize(new Dimension(100, 50));
        jfc.addPropertyChangeListener(this);
    }

    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconHeight() > 90) {
                thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
            } else {
                thumbnail = tmpIcon;
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        boolean update = false;
        String prop = evt.getPropertyName();

        //If the directory changed, don't show an image
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;
        }

        //If a file became selected, find out which one
        else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)){
            file = (File) evt.getNewValue();
            update = true;
        }

        //Update the preview accordingly
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }

        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }

            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
