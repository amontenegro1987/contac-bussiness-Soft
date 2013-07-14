/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.models.tables;

import javax.swing.table.DefaultTableModel;

/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * User: EMontenegro
 * Date: 11-22-12
 * Time: 11:13 PM
 */
public class NotEditableTableModel extends DefaultTableModel {

    /**
     * Constructs a <code>DefaultTableModel</code> with as many
     * columns as there are elements in <code>columnNames</code>
     * and <code>rowCount</code> of <code>null</code>
     * object values.  Each column's name will be taken from
     * the <code>columnNames</code> array.
     *
     * @param columnNames <code>array</code> containing the names
     *                    of the new columns; if this is
     *                    <code>null</code> then the model has no columns
     * @param rowCount    the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public NotEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    /**
     * Returns true regardless of parameter values. Not Editable
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true
     * @see #setValueAt
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
