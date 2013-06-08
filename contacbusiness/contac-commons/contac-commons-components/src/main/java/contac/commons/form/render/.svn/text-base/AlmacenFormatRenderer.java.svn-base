/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.form.render;

import contac.modelo.entity.Almacen;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Contac Business Software Corporation. All rights reserved 2011.
 * User: EMontenegro
 * Date: 12-13-11
 * Time: 03:05 PM
 */
public class AlmacenFormatRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        //Getting almacen value object
        Almacen almacen = (Almacen) value;

        //Return value
        return super.getTableCellRendererComponent(table, almacen.getDescripcion(), isSelected, hasFocus, row, column);
    }
}
