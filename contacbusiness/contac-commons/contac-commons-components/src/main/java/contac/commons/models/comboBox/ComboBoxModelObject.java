package contac.commons.models.comboBox;

/**
 * Class implemented ComboBox model object
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 01-04-2011
 * Time: 09:43:52 AM
 */
public class ComboBoxModelObject {

    //CAMPOS
    private Integer id;
    private String descripcion;
    private Object object;

    public Integer getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Object getObject() {
        return object;
    }

    public ComboBoxModelObject(Integer id, String descripcion, Object object) {
        this.id = id;
        this.descripcion = descripcion;
        this.object = object;
    }

    public String toString() {
        return this.descripcion; 
    }
}
