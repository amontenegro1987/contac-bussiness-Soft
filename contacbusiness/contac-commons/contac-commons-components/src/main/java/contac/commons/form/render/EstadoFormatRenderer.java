/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.form.render;

import contac.modelo.entity.EstadoMovimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Contac Software Business Corporation. All rights reserved 2011.
 * User: EMontenegro
 * Date: 12-13-11
 * Time: 03:10 PM
 */
public class EstadoFormatRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                      int row, int column) {

        //Getting almacen value object
        EstadoMovimiento estado = (EstadoMovimiento) value;

        //Return value
        return super.getTableCellRendererComponent(table, estado.getNombre(), isSelected, hasFocus, row, column);

    }

}
