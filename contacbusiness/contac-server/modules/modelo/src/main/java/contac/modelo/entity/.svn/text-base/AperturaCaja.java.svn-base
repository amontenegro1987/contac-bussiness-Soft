/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 10-01-12
 * Time: 03:19 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_APERTURA_CAJA")
public class AperturaCaja implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Identification Id
     */
    private Integer id;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Listado de denominaciones apertura
     */
    private List<DenominacionBilletes> denominacionApertura;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Listado de denominaciones cierre
     */
    private List<DenominacionBilletes> denominacionCierre;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto apertura moneda base
     */
    private BigDecimal montoAperturaMonedaBase;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto cierre moneda base
     */
    private BigDecimal montoCierreMonedaBase;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto apertura moneda referencia
     */
    private BigDecimal montoAperturaMonedaReferencia;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto cierre moneda referencia
     */
    private BigDecimal montoCierreMonedaReferencia;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto faltante moneda base
     */
    private BigDecimal montoFaltanteMonedaBase;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto sobrante moneda base
     */
    private BigDecimal montoSobranteMonedaBase;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto faltante moneda referencia
     */
    private BigDecimal montoFaltanteMonedaReferencia;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto sobrante moneda referencia
     */
    private BigDecimal montoSobranteMonedaReferencia;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "aperturaCaja")
    public List<DenominacionBilletes> getDenominacionApertura() {
        return denominacionApertura;
    }

    public void setDenominacionApertura(List<DenominacionBilletes> denominacionApertura) {
        this.denominacionApertura = denominacionApertura;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cierreCaja")
    public List<DenominacionBilletes> getDenominacionCierre() {
        return denominacionCierre;
    }

    public void setDenominacionCierre(List<DenominacionBilletes> denominacionCierre) {
        this.denominacionCierre = denominacionCierre;
    }

    @Column(name = "N_MONTO_FALTANTE_MONEDA_BASE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoFaltanteMonedaBase() {
        return montoFaltanteMonedaBase;
    }

    public void setMontoFaltanteMonedaBase(BigDecimal montoFaltanteMonedaBase) {
        this.montoFaltanteMonedaBase = montoFaltanteMonedaBase;
    }

    @Column(name = "N_MONTO_SOBRANTE_MONEDA_BASE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoSobranteMonedaBase() {
        return montoSobranteMonedaBase;
    }

    public void setMontoSobranteMonedaBase(BigDecimal montoSobranteMonedaBase) {
        this.montoSobranteMonedaBase = montoSobranteMonedaBase;
    }

    @Column(name = "N_MONTO_APERTURA_MONEDA_BASE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoAperturaMonedaBase() {
        return montoAperturaMonedaBase;
    }

    public void setMontoAperturaMonedaBase(BigDecimal montoAperturaMonedaBase) {
        this.montoAperturaMonedaBase = montoAperturaMonedaBase;
    }

    @Column(name = "N_MONTO_CIERRE_MONEDA_BASE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoCierreMonedaBase() {
        return montoCierreMonedaBase;
    }

    public void setMontoCierreMonedaBase(BigDecimal montoCierreMonedaBase) {
        this.montoCierreMonedaBase = montoCierreMonedaBase;
    }

    @Column(name = "N_MONTO_APERTURA_MONEDA_REFERENCIA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoAperturaMonedaReferencia() {
        return montoAperturaMonedaReferencia;
    }

    public void setMontoAperturaMonedaReferencia(BigDecimal montoAperturaMonedaReferencia) {
        this.montoAperturaMonedaReferencia = montoAperturaMonedaReferencia;
    }

    @Column(name = "N_MONTO_CIERRE_MONEDA_REFERENCIA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoCierreMonedaReferencia() {
        return montoCierreMonedaReferencia;
    }

    public void setMontoCierreMonedaReferencia(BigDecimal montoCierreMonedaReferencia) {
        this.montoCierreMonedaReferencia = montoCierreMonedaReferencia;
    }

    @Column(name = "N_MONTO_SOBRANTE_MONEDA_REFERENCIA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoSobranteMonedaReferencia() {
        return montoSobranteMonedaReferencia;
    }

    public void setMontoSobranteMonedaReferencia(BigDecimal montoSobranteMonedaReferencia) {
        this.montoSobranteMonedaReferencia = montoSobranteMonedaReferencia;
    }

    @Column(name = "N_MONTO_FALTANTE_MONEDA_REFERENCIA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoFaltanteMonedaReferencia() {
        return montoFaltanteMonedaReferencia;
    }

    public void setMontoFaltanteMonedaReferencia(BigDecimal montoFaltanteMonedaReferencia) {
        this.montoFaltanteMonedaReferencia = montoFaltanteMonedaReferencia;
    }

    // ==================================<METODOS DE AUDITORIA>========================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la fecha de creacion del objeto
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
     * Setear la fecha de creacion del Objeto
     *
     * @param ctime, fecha de creacion
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de creacion del Objeto
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
     * Setear usuario de creacion del Objeto
     *
     * @param cuser, usuario de creacion
     */
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener fecha de modificacion del Objeto
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
     * Setear fecha de modificacion del Objeto
     *
     * @param mtime, fecha de modificacion
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de modificacion del Objeto
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
     * Setear usuario de modificacion del Objeto
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }
}
