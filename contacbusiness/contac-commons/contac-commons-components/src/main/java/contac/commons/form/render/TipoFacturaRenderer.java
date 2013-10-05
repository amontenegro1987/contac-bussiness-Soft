package contac.commons.form.render;

import contac.modelo.entity.TiposFactura;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Contac Software Business Corporation. All rights reserved 2012.
 * User: EMontenegro
 * Date: 11-05-12
 * Time: 09:39 PM
 */
public class TipoFacturaRenderer extends DefaultTableCellRenderer{

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        //Getting value object
        Integer estado = ((Byte)value).intValue();

        String nombreTipoFactura = "";
        if (TiposFactura.CONTADO.getValue() == estado)
            nombreTipoFactura = TiposFactura.CONTADO.getNombre();
        if (TiposFactura.CREDITO.getValue() == estado)
            nombreTipoFactura = TiposFactura.CREDITO.getNombre();

        //Return value
        return super.getTableCellRendererComponent(table, nombreTipoFactura, isSelected, hasFocus, row, column);

    }
}
