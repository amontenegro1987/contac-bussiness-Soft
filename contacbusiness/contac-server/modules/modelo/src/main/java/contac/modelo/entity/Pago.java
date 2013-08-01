/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 10-01-12
 * Time: 11:35 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_PAGO")
public class Pago implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Identification Id
     */
    private Integer id;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Tipo de pago
     */
    private TipoPago tipoPago;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Tarjeta de credito
     */
    private TarjetaCredito tarjetaCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Moneda
     */
    private Moneda moneda;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Tasa de cambio
     */
    private BigDecimal tasaCambio;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto a cancelar
     */
    private BigDecimal montoCancelar;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto recibido
     */
    private BigDecimal montoRecibido;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto conversion
     */
    private BigDecimal montoConversion;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto devuelto
     */
    private BigDecimal montoDevuelto;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto comision bancaria
     */
    private BigDecimal montoComision;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto retencion impuestos
     */
    private BigDecimal montoRetencion;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto deposito banco
     */
    private BigDecimal montoNetoBanco;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (mtime)
     */
    private Factura factura;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Caja de cobro
     */
    private Caja caja;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_TIPO_PAGO", referencedColumnName = "N_ID", nullable = false)
    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_TARJETA_CREDITO", referencedColumnName = "N_ID", nullable = true)
    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_MONEDA", referencedColumnName = "N_ID", nullable = false)
    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    @Column(name = "N_TASA_CAMBIO", nullable = false)
    public BigDecimal getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(BigDecimal tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    @Column(name = "N_MONTO_CANCELAR", nullable = false)
    public BigDecimal getMontoCancelar() {
        return montoCancelar;
    }

    public void setMontoCancelar(BigDecimal montoCancelar) {
        this.montoCancelar = montoCancelar;
    }

    @Column(name = "N_MONTO_RECIBIDO", nullable = false)
    public BigDecimal getMontoRecibido() {
        return montoRecibido;
    }

    public void setMontoRecibido(BigDecimal montoRecibido) {
        this.montoRecibido = montoRecibido;
    }

    @Column(name = "N_MONTO_CONVERSION", nullable = false)
    public BigDecimal getMontoConversion() {
        return montoConversion;
    }

    public void setMontoConversion(BigDecimal montoConversion) {
        this.montoConversion = montoConversion;
    }

    @Column(name = "N_MONTO_DEVUELTO", nullable = false)
    public BigDecimal getMontoDevuelto() {
        return montoDevuelto;
    }

    public void setMontoDevuelto(BigDecimal montoDevuelto) {
        this.montoDevuelto = montoDevuelto;
    }

    @Column(name = "N_MONTO_COMISION", nullable = false)
    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    @Column(name = "N_MONTO_RETENCION", nullable = false)
    public BigDecimal getMontoRetencion() {
        return montoRetencion;
    }

    public void setMontoRetencion(BigDecimal montoRetencion) {
        this.montoRetencion = montoRetencion;
    }

    @Column(name = "N_MONTO_NETO_BANCO", nullable = false)
    public BigDecimal getMontoNetoBanco() {
        return montoNetoBanco;
    }

    public void setMontoNetoBanco(BigDecimal montoNetoBanco) {
        this.montoNetoBanco = montoNetoBanco;
    }

    @OneToOne(mappedBy = "pago", fetch = FetchType.LAZY)
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_CAJA", nullable = false)
    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
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