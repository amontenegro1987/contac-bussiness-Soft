package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-19-2010
 * Time: 12:08:57 PM
 */
@Embeddable
public class ProductoExistenciaPK implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: producto
     */
    private Producto producto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacen
     */
    private Almacen almacen;

    /**
     * Default constructor
     */
    public ProductoExistenciaPK() {}

    /**
     * Constructor with producto and almacen
     * @param producto, contac.modelo.entity.Producto
     * @param almacen, contac.modelo.entity.Almacen
     * @see contac.modelo.entity.Producto
     * @see contac.modelo.entity.Almacen
     */
    public ProductoExistenciaPK(Producto producto, Almacen almacen) {
        this.producto = producto;
        this.almacen = almacen;
    }

    /**
     * Obtener producto PRIMARY KEY
     * @return contac.modelo.entity.Producto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID")
    public Producto getProducto() {
        return producto;
    }

    /**
     * Setear producto PRIMARY KEY
     * @param producto, contac.modelo.entity.Producto
     */
    public void setProducto(Producto producto) {                                                       
        this.producto = producto;
    }

    /**
     * Obtener almacen PRIMARY KEY
     * @return contac.modelo.entity.Almacen
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID")
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * Setear almacen PRIMARY KEY
     * @param almacen, contac.modelo.entity.Almacen
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
}
