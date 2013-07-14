package contac.commons.models.comboBox;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * @author JRobertoCarlos
 * @version 1.0
 * @since 08-08-2011 Lun 04:00 PM
 */
public class ComboBoxEmptySelectionRenderer extends DefaultListCellRenderer {
    /**
     * Mensaje
     */
    private String selectionPrompt;

    /**
     * Combobox
     */
    private JComboBox comboBox;


    /**
     * Crea una nueva instancia
     */
    public ComboBoxEmptySelectionRenderer(JComboBox comboBox, String selectionPrompt) {
        this.comboBox = comboBox;
        this.selectionPrompt = selectionPrompt;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean focus) {
        super.getListCellRendererComponent(list, value, index, isSelected, focus);

        if (index == -1 && comboBox.getSelectedIndex() == -1) {
            setText(selectionPrompt);
        }

        return this;
    }
}

