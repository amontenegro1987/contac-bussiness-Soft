/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 10-02-12
 * Time: 05:07 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_DENOMINACION")
public class Denominacion implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Identification Id
     */
    private Integer id;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Denominacion en moneda
     */
    private long denominacion;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Denominacion letras
     */
    private String denominacionLetras;
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

    @Id
    @Column(name = "N_ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "N_DENOMINACION", nullable = false)
    public long getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(long denominacion) {
        this.denominacion = denominacion;
    }

    @Column(name = "C_DENOMINACION_LETRAS", nullable = false)
    public String getDenominacionLetras() {
        return denominacionLetras;
    }

    public void setDenominacionLetras(String denominacionLetras) {
        this.denominacionLetras = denominacionLetras;
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
