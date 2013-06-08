/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy
 * Date: 10-23-2008
 * Time: 11:44:23 AM
 */

/**
 * ProductoExistencia Contac Business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_PRODUCTOEXISTENCIA")
public class ProductoExistencia implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private ProductoExistenciaPK id;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cantidad
     */
    private long cantidad;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Minimo existencia bodega
     */
    private long minimo;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Maximo existencia bodega
     */
    private long maximo;
    /**
     * <!-- begin-user-doc-->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Cantidad ingresada mes
     */
    private long ingreso;
    /**
     * <!-- begin-user-doc-->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Cantidad salida mes
     */
    private long salida;
    /**
     * <!-- begin-user-doc-->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Existencia total
     */
    private long existencia;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (ctime)
     */
    private Date ctime;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (cuser)
     */
    private String cuser;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (mtime)
     */
    private Date mtime;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (muser)
     */
    private String muser;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return Integer
     */
    @EmbeddedId
    public ProductoExistenciaPK getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->a
     *
     * @param id, nro. de registro del objeto
     */
    public void setId(ProductoExistenciaPK id) {
        this.id = id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->a
     *
     * @return, long
     */
    @Column(name = "N_CANTIDAD", nullable = false)
    public long getCantidad() {
        return cantidad;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param cantidad, cantidad de existencia
     */
    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "N_CANTIDAD_MINIMO", nullable = false)
    public long getMinimo() {
        return minimo;
    }

    public void setMinimo(long minimo) {
        this.minimo = minimo;
    }

    @Column(name = "N_CANTIDAD_MAXIMO", nullable = false)
    public long getMaximo() {
        return maximo;
    }

    public void setMaximo(long maximo) {
        this.maximo = maximo;
    }

    @Transient
    public long getIngreso() {
        return ingreso;
    }

    public void setIngreso(long ingreso) {
        this.ingreso = ingreso;
    }

    @Transient
    public long getSalida() {
        return salida;
    }

    public void setSalida(long salida) {
        this.salida = salida;
    }

    @Transient
    public long getExistencia() {
        return existencia;
    }

    public void setExistencia(long existencia) {
        this.existencia = existencia;
    }

    // ==================================<METODOS DE AUDITORIA>========================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la fecha de creacion del objeto Usuario Garsa Business
     *
     * @return Date
     */
    @Column(name = "D_CTIME", nullable = false)
    public Date getCtime() {
        return this.ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la fecha de creacion del Objeto Usuario Garsa Business
     *
     * @param ctime, fecha de creacion
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de creacion del Objeto Usuario Garsa Business
     *
     * @return String
     */
    @Column(name = "C_CUSER", nullable = false)
    public String getCuser() {
        return this.cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear usuario de creacion del Objeto Usuario Garsa Business
     *
     * @param cuser, usuario de creacion
     */
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener fecha de modificacion del Objeto Usuario Garsa Business
     *
     * @return Date
     */
    @Column(name = "D_MTIME", nullable = false)
    public Date getMtime() {
        return this.mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear fecha de modificacion del Objeto Usuario Garsa Business
     *
     * @param mtime, fecha de modificacion
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de modificacion del Objeto Usuario Garsa Business
     *
     * @return String
     */
    @Column(name = "C_MUSER", nullable = false)
    public String getMuser() {
        return this.muser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear usuario de modificacion del Objeto Usuario Garsa Business
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }
}
