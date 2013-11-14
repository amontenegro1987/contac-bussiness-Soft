package contac.modelo.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-08-2010
 * Time: 11:14:49 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_ESTADO_MOVIMIENTO")
public class EstadoMovimiento extends Estado implements java.io.Serializable{
    
    public String toString() {
        return getNombre();
    }
}
