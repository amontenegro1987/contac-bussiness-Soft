package contac.commons.form.render;

import contac.text.TextUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Contac Business Corporation.  All rights reserved 2011.
 * User: Eddy Montenegro
 * Date: 16/10/11
 * Time: 21:25
 * Class to handle the formatting of the double values
 */
public class DecimalFormatRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        //Format cell value
        value = TextUtil.formatCurrency(((BigDecimal)value).doubleValue());

        //Return value
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
