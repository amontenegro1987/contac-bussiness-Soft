/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-16-12
 * Time: 10:09 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_PRODUCTO_COMPUESTO")
public class ProductoCompuesto implements Serializable {

    /**
     * PROPERTY NAME: Identificador
     */
    private Integer id;
    /**
     * PROPERTY NAME: Producto
     */
    private Producto producto;
    /**
     * PROPERTY NAME: Cantidad
     */
    private long cantidad;
    /**
     * PROPERTY NAME: Precio
     */
    private BigDecimal precio;

    /**
     * PROPERTY NAME: Precio neto
     */
    private BigDecimal precioTotal;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID")
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Column(name = "N_CANTIDAD", nullable = false)
    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "N_PRECIO_UNITARIO", nullable = false)
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Column(name = "N_PRECIO_TOTAL", nullable = false)
    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }
}
