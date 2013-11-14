/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-16-12
 * Time: 10:18 PM
 */
@Embeddable
public class ProductoCompuestoPK implements Serializable {


    /**
     * PROPERTY NAME: Producto padre
     */
    private Producto producto;
    /**
     * PROPERTY NAME> Producto hijo
     */
    private Producto productoCompuesto;

    /**
     * Default constructor
     */
    public ProductoCompuestoPK() {}

    /**
     * Constructor producto padre y producto compuesto
     * @param producto, Producto padre
     * @param productoCompuesto, Producto compuesto
     */
    public ProductoCompuestoPK(Producto producto, Producto productoCompuesto) {
        this.producto = producto;
        this.productoCompuesto = productoCompuesto;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_PRODUCTO_COMPUESTO", referencedColumnName = "N_ID")
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID")
    public Producto getProductoCompuesto() {
        return productoCompuesto;
    }

    public void setProductoCompuesto(Producto productoCompuesto) {
        this.productoCompuesto = productoCompuesto;
    }
}
