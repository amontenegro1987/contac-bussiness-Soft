package contac.commons.models.comboBox;

import contac.modelo.entity.ActividadEconomica;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.List;
import java.util.Vector;

/**
 * Contac Business Inc. 2011 all rights reserved.
 * User: emontenegro
 * Date: 8/11/11
 * Time: 10:56 PM
 */
public class ActividadEconComboBoxModel implements ComboBoxModel {

    private Vector<ComboBoxModelObject> data = new Vector<ComboBoxModelObject>();
    private Vector<ListDataListener> list = new Vector<ListDataListener>();
    private ComboBoxModelObject selectedItem;

    /**
     * Constructor
     *
     * @param actividadesEcon, List<ActividadEconomica>
     */
    public ActividadEconComboBoxModel(List<ActividadEconomica> actividadesEcon) {
        for (ActividadEconomica actividadEcon : actividadesEcon) {
            data.add(new ComboBoxModelObject(actividadEcon.getId(), actividadEcon.getNombre(), actividadEcon));
        }

        if (data.size() > 0)
            setSelectedItem(data.get(0));
    }

    /**
     * Search ComboBoxModelObject by Id
     *
     * @param id, object identifier
     * @return ComboBoxModelObject
     */
    public ComboBoxModelObject searchSelectedItem(Integer id) {
        for (ComboBoxModelObject object : data) {
            if (id.equals(object.getId())) {
                return object;
            }
        }
        return null;
    }

    /**
     * Search ComboBoxModelObject by description
     *
     * @param description, object description
     * @return ComboBoxModelObject
     */
    public ComboBoxModelObject searchSelectedItem(String description) {
        for (ComboBoxModelObject object : data) {
            if (description.equals(object.getDescripcion())) {
                return object;
            }
        }
        return null;
    }

    public void setSelectedItem(Object item) {
        selectedItem = item instanceof ComboBoxModelObject ? (ComboBoxModelObject) item : null;

        for (ListDataListener dataList : list) {
            dataList.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0));
        }
    }

    public ComboBoxModelObject getSelectedItem() {
        return selectedItem;
    }

    public int getSize() {
        return data.size();
    }

    public ComboBoxModelObject getElementAt(int index) {
        return data.get(index);
    }

    public void addListDataListener(ListDataListener listener) {
        list.add(listener);
    }

    public void removeListDataListener(ListDataListener listener) {
        list.remove(listener);
    }

    public Integer getSelectedId() {
        return selectedItem == null ? null : selectedItem.getId();
    }

    public String getSelectedDescription() {
        return selectedItem == null ? null : selectedItem.getDescripcion();
    }

}
