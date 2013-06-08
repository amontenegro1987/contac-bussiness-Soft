/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.commons.form.render;

import contac.modelo.entity.TiposTasaCambio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-15-12
 * Time: 11:06 PM
 */
public class TiposTasasCambioRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        //Obteniendo tipo de tasa de cambio
        Integer tipoTasaCambio = (Integer) value;

        String lblTipoTasaCambio = TiposTasaCambio.FIJO.getStringValue(tipoTasaCambio);

        //Return value
        return super.getTableCellRendererComponent(table, lblTipoTasaCambio, isSelected, hasFocus, row, column);

    }
}
