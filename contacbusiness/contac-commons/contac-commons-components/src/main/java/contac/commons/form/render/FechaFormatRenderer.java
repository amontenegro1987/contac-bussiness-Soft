/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.form.render;

import contac.internationalization.LanguageLocale;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Contac Software Business Corporation. All rights reserved 2011.
 * User: EMontenegro
 * Date: 12-13-11
 * Time: 04:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FechaFormatRenderer extends DefaultTableCellRenderer  {

    //Calendario with default config locale
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", LanguageLocale.getInstance().getLocale());

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                      int row, int column) {

        //Obtener fecha registrada
        Date fecha = (Date) value;

        //Return value
        return super.getTableCellRendererComponent(table, dateFormat.format(fecha), isSelected, hasFocus, row, column);

    }
}
