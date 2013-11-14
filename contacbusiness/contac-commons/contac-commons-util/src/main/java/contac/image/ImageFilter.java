/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 */

package contac.image;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Image Filter selection
 * User: Eddy Montenegro
 * Date: 7/20/11
 * Time: 3:42 PM
 */
public class ImageFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;

        String extension = ImageUtils.getExtension(f);

        if (extension != null) {
            if (extension.equals(ImageUtils.tiff) ||
                    extension.equals(ImageUtils.tif) ||
                    extension.equals(ImageUtils.gif) ||
                    extension.equals(ImageUtils.jpeg) ||
                    extension.equals(ImageUtils.jpg) ||
                    extension.equals(ImageUtils.png)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Just Images";
    }

}
