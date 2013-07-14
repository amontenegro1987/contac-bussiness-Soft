package contac.commons.models.tables;

import contac.modelo.entity.Clasificador;
import test.svg.transcoded.system_log_out;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy
 * Date: 7/10/11
 * Time: 10:41 PM
 */
public class ClasificadorTableModel extends DefaultTableModel {


    //Instance Variables
    //private Vector dataVector;
    private Map<Integer, Clasificador> dataMap;
    //private Vector columnIdentifiers;

    /**
     * List of listeners
     */
    protected EventListenerList listenerList = new EventListenerList();

    //Constructors

    /**
     * Construct a default <code>BranchTableModel</code>
     * which is a table with zero rows and zero columns
     */
    public ClasificadorTableModel() {
        this(0, 0);
    }

    /**
     * Constructs a <code>DefaultTableModel</code> with
     * <code>rowCount</code> and <code>columnCount</code> of
     * <code>null</code> object values.
     *
     * @param rowCount,    the number of rows of table holds
     * @param columnCount, the number of columns of table holds
     */
    public ClasificadorTableModel(int rowCount, int columnCount) {
        this(newVector(columnCount), rowCount);
    }

    /**
     * Constructs a <code>BranchTableModel</code> with as many columns
     * as there are elements in <code>columnNames</code>
     * and <code>rowCount</code> of <code>null</code>
     * object values.  Each column's name will be taken from
     * the <code>columnNames</code> vector.
     *
     * @param columnNames, containing the names of the new columns
     * @param rowCount,    the number of rows the table holds
     */
    public ClasificadorTableModel(Vector columnNames, int rowCount) {
        setDataVector(newVector(rowCount), columnNames);
    }

    /**
     * Construct a <code>BranchTableModel</code> with a List<?> of
     * elements generating the columnheaders customized.
     *
     * @param data, List<?> of branches
     */
    public ClasificadorTableModel(List<Clasificador> data) {
        setDataVector(convertToDataVector(data), convertToHeaderVector());
    }

    public Clasificador getEntityValueAt(int rowIndex) {
        return dataMap.get(rowIndex);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnIdentifiers.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Vector rowVector = (Vector) dataVector.elementAt(rowIndex);
        return rowVector.elementAt(columnIndex);
    }

    public void addTableModelListener(TableModelListener listener) {
        listenerList.add(TableModelListener.class, listener);
    }

    public String getColumnName(int column) {
        Object id = null;
        // This test is to cover the case when
        // getColumnCount has been subclassed by mistake ...
        if (column < columnIdentifiers.size() && (column >= 0)) {
            id = columnIdentifiers.elementAt(column);
        }
        return (id == null) ? super.getColumnName(column)
                : id.toString();
    }

    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //TODO: Implement only if the JTable is editable
    }

    public void removeTableModelListener(TableModelListener listener) {
        listenerList.remove(TableModelListener.class, listener);
    }

    /**
     * Insert a new Row
     * @param object, Clasificador
     */
    public void insertRow(Clasificador object) {

        Vector row = new Vector();
        row.addElement(object.getCbs());
        row.addElement(object.getClasificacion());
        row.addElement(object.getIdentificador());
        row.addElement(object.getDescripcion());

        System.out.println("ROW COUNT: " + getRowCount());

        dataMap.put(getRowCount() + 1, object);

        addRow(row);

        fireTableRowsInserted(dataVector.size() -1, dataVector.size() -1);
    }

    //Utility methos

    /**
     * Convert a List<?> to Vector
     *
     * @param data, List<?>
     * @return Vector<?>
     */
    private Vector convertToDataVector(List<Clasificador> data) {

        Vector v = new Vector();

        //DataMap relation a vector of datas with his identifier
        dataMap = new HashMap<Integer, Clasificador>();

        for (int i = 0; i < data.size(); i++) {
            //Every branch object is a row
            Vector row = new Vector();
            row.addElement(data.get(i).getCbs());
            row.addElement(data.get(i).getClasificacion());
            row.addElement(data.get(i).getIdentificador());
            row.addElement(data.get(i).getDescripcion());

            //Add row to Vector
            v.addElement(row);

            //Add entity to a map
            dataMap.put(i, data.get(i));

        }

        return v;
    }

    /**
     * Generate a column headers with Object String
     *
     * @return Vector<?>
     */
    private Vector convertToHeaderVector() {

        Vector v = new Vector();
        v.addElement("CBS");
        v.addElement("Clasificacion");
        v.addElement("Identificador");
        v.addElement("Descripcion");

        return v;
    }

    /**
     * Create a new Vector with a specified size
     *
     * @param size, size of vector elements
     * @return Vector
     */
    private static Vector newVector(int size) {
        Vector v = new Vector(size);
        v.setSize(size);
        return v;
    }

    /**
     * Replace the current <code>dataVector</code> instance variable
     * with the new <code>Vector</code> of rows.
     *
     * @param dataVector,       the new data Vector
     * @param columnIdentifier, the names of the columns
     */
    public void setDataVector(Vector dataVector, Vector columnIdentifier) {
        this.dataVector = dataVector;
        this.columnIdentifiers = columnIdentifier;
        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    private void justifyRows(int from, int to) {
        // Sometimes the DefaultTableModel is subclassed
        // instead of the AbstractTableModel by mistake.
        // Set the number of rows for the case when getRowCount
        // is overridden.
        dataVector.setSize(getRowCount());

        for (int i = from; i < to; i++) {
            if (dataVector.elementAt(i) == null) {
                dataVector.setElementAt(new Vector(), i);
            }
            ((Vector) dataVector.elementAt(i)).setSize(getColumnCount());
        }
    }
}
