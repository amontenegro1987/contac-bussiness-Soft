/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.commons.form.render;

import contac.modelo.entity.TipoActualizacionFisico;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Contac Business Software Corporation. All rights reserved 2012.
 * User: emortiz
 * Date: 07-09-12
 * Time: 10:50 AM
 */
public class TipoActualizacionInventarioRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        //Format cell value
        int tipoActualizacion = ((Integer)value);

        //Return value
        return super.getTableCellRendererComponent(table, TipoActualizacionFisico.PARCIAL.getStringValue(tipoActualizacion),
                isSelected, hasFocus, row, column);
    }
}
