package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-08-2010
 * Time: 11:33:29 PM
 */

/**
 * Entity Producto image extends ImageLOB
 */
@Entity
@EntityListeners(Audit.class)
@Table(name="INV_PRODUCTOIMAGE_LOB")
@AttributeOverride(name = "image", column = @Column(name = "FOTO", nullable = true))
public class ProductoImageLOB extends ImageLOB implements Serializable {
}
