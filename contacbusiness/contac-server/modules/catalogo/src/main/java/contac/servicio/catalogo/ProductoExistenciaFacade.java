package contac.servicio.catalogo;

import contac.modelo.entity.Almacen;
import contac.modelo.entity.Producto;

import java.io.Serializable;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-15-12
 * Time: 11:10 PM
 */
public class ProductoExistenciaFacade implements Serializable {

    /**
     * PROPERTY NAME: Producto existencia
     */
    private Producto producto;
    /**
     * PROPERTY NAME: Almacen de existencia
     */
    private Almacen almacen;
    /**
     * PROPERTY NAME: Existencia inicial
     */
    private long existenciaInicial;
    /**
     * PROPERTY NAME: Ingreso del mes
     */
    private long ingreso;
    /**
     * PROPERTY NAME: Salida del mes
     */
    private long salida;
    /**
     * PROPERTY NAME: Existencia final
     */
    private long existenciaFinal;

    //********************************SETTERS & GETTERS*****************************************

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public long getExistenciaInicial() {
        return existenciaInicial;
    }

    public void setExistenciaInicial(long existenciaInicial) {
        this.existenciaInicial = existenciaInicial;
    }

    public long getIngreso() {
        return ingreso;
    }

    public void setIngreso(long ingreso) {
        this.ingreso = ingreso;
    }

    public long getSalida() {
        return salida;
    }

    public void setSalida(long salida) {
        this.salida = salida;
    }

    public long getExistenciaFinal() {
        return existenciaFinal;
    }

    public void setExistenciaFinal(long existenciaFinal) {
        this.existenciaFinal = existenciaFinal;
    }

}
