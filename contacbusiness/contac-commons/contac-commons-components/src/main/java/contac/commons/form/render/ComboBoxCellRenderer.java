package contac.commons.form.render;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: EMontenegro
 * Date: 01-21-12
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComboBoxCellRenderer implements ListCellRenderer {

    private static final Logger logger = Logger.getLogger(ComboBoxCellRenderer.class);

    //Default renderer
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        //Getting renderer
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        return renderer;
    }
}
