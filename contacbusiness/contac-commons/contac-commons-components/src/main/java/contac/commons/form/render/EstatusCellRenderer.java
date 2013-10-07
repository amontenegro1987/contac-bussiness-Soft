package contac.commons.form.render;

import contac.text.TextUtil;
import org.pushingpixels.substance.api.renderers.SubstanceDefaultTableCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Contac Business Corporation.  All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-08-12
 * Time: 10:16 PM
 */
public class EstatusCellRenderer extends SubstanceDefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        //Format cell value
        long qty = (Long)value;
        
        JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (qty > 0) {
            label.setForeground(Color.BLUE);
        } else if (qty < 0) {
            label.setForeground(Color.RED);
        } else if (qty == 0) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        }

        //Return value
        return label;
    }

}
