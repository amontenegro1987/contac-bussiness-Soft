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
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 10-01-12
 * Time: 10:53 AM
 */
@Entity

@EntityListeners(Audit.class)
@Table(name = "FAC_CAJA")
public class Caja implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Identification Id
     */
    private Integer id;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Fecha de apertura caja
     */
    private Date fechaApertura;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Fecha de cierre caja
     */
    private Date fechaCierre;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Cajero asignado
     */
    private Cajero cajero;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto exonerado contado
     */
    private BigDecimal montoExoneradoContado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto exento contado
     */
    private BigDecimal montoExentoContado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto retencion fuente contado
     */
    private BigDecimal montoRetencionFuenteContado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto retencion municipal contado
     */
    private BigDecimal montoRetencionMunicipalContado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto facturado contado
     */
    private BigDecimal montoNetoFacturadoContado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Subtotal factura contado
     */
    private BigDecimal subTotalFacturaContado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto exonerado credito
     */
    private BigDecimal montoExoneradoCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto exento credito
     */
    private BigDecimal montoExentoCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto retencion fuente credito
     */
    private BigDecimal montoRetencionFuenteCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto retencion municipal credito
     */
    private BigDecimal montoRetencionMunicipalCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto facturado credito
     */
    private BigDecimal montoNetoFacturadoCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Subtotal factura de credito
     */
    private BigDecimal subtotalFacturaCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto facturado por tarjetas de credito
     */
    private BigDecimal montoFacturadoTarjetasCredito;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto facturado efectivo
     */
    private BigDecimal montoFacturadoEfectivo;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto facturado cheques
     */
    private BigDecimal montoFacturadoCheques;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto facturado canjes
     */
    private BigDecimal montoFacturadoCanjes;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto neto recibos de caja
     */
    private BigDecimal montoRecibosCaja;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Nombre usuario arquea y entrega
     */
    private String nombreUsuarioAutorizaApertura;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Nombre usuario arquea y recibe
     */
    private String nombreUsuarioAutorizaCierre;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Almacen de asignacion de la caja
     */
    private Almacen almacen;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto de apertura de caja
     */
    private AperturaCaja aperturaCaja;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Estado caja
     */
    private EstadoCaja estado;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Pagos realizados
     */
    private List<Pago> pagos;
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
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @Column(name = "C_NOMBRE_USUARIO_AUTORIZA_CIERRE", nullable = false)
    public String getNombreUsuarioAutorizaCierre() {
        return nombreUsuarioAutorizaCierre;
    }

    public void setNombreUsuarioAutorizaCierre(String nombreUsuarioAutorizaCierre) {
        this.nombreUsuarioAutorizaCierre = nombreUsuarioAutorizaCierre;
    }

    @Column(name = "C_NOMBRE_USUARIO_AUTORIZA_APERTURA", nullable = false)
    public String getNombreUsuarioAutorizaApertura() {
        return nombreUsuarioAutorizaApertura;
    }

    public void setNombreUsuarioAutorizaApertura(String nombreUsuarioAutorizaApertura) {
        this.nombreUsuarioAutorizaApertura = nombreUsuarioAutorizaApertura;
    }

    @Column(name = "N_MONTO_EXONERADO_CONTADO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoExoneradoContado() {
        return montoExoneradoContado;
    }

    public void setMontoExoneradoContado(BigDecimal montoExoneradoContado) {
        this.montoExoneradoContado = montoExoneradoContado;
    }

    @Column(name = "N_MONTO_EXENTO_CONTADO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoExentoContado() {
        return montoExentoContado;
    }

    public void setMontoExentoContado(BigDecimal montoExentoContado) {
        this.montoExentoContado = montoExentoContado;
    }

    @Column(name = "N_MONTO_RETENCION_FUENTE_CONTADO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoRetencionFuenteContado() {
        return montoRetencionFuenteContado;
    }

    public void setMontoRetencionFuenteContado(BigDecimal montoRetencionFuenteContado) {
        this.montoRetencionFuenteContado = montoRetencionFuenteContado;
    }

    @Column(name = "N_MONTO_RETENCION_MUNICIPAL_CONTADO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoRetencionMunicipalContado() {
        return montoRetencionMunicipalContado;
    }

    public void setMontoRetencionMunicipalContado(BigDecimal montoRetencionMunicipalContado) {
        this.montoRetencionMunicipalContado = montoRetencionMunicipalContado;
    }

    @Column(name = "N_MONTO_NETO_FACTURADO_CONTADO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoNetoFacturadoContado() {
        return montoNetoFacturadoContado;
    }

    public void setMontoNetoFacturadoContado(BigDecimal montoNetoFacturadoContado) {
        this.montoNetoFacturadoContado = montoNetoFacturadoContado;
    }

    @Column(name = "N_MONTO_EXONERADO_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoExoneradoCredito() {
        return montoExoneradoCredito;
    }

    public void setMontoExoneradoCredito(BigDecimal montoExoneradoCredito) {
        this.montoExoneradoCredito = montoExoneradoCredito;
    }

    @Column(name = "N_MONTO_EXENTO_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoExentoCredito() {
        return montoExentoCredito;
    }

    public void setMontoExentoCredito(BigDecimal montoExentoCredito) {
        this.montoExentoCredito = montoExentoCredito;
    }

    @Column(name = "N_MONTO_RETENCION_FUENTE_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoRetencionFuenteCredito() {
        return montoRetencionFuenteCredito;
    }

    public void setMontoRetencionFuenteCredito(BigDecimal montoRetencionFuenteCredito) {
        this.montoRetencionFuenteCredito = montoRetencionFuenteCredito;
    }

    @Column(name = "N_MONTO_RETENCION_MUNICIPAL_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoRetencionMunicipalCredito() {
        return montoRetencionMunicipalCredito;
    }

    public void setMontoRetencionMunicipalCredito(BigDecimal montoRetencionMunicipalCredito) {
        this.montoRetencionMunicipalCredito = montoRetencionMunicipalCredito;
    }

    @Column(name = "N_MONTO_NETO_FACTURADO_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoNetoFacturadoCredito() {
        return montoNetoFacturadoCredito;
    }

    public void setMontoNetoFacturadoCredito(BigDecimal montoNetoFacturadoCredito) {
        this.montoNetoFacturadoCredito = montoNetoFacturadoCredito;
    }

    @Column(name = "N_SUBTOTAL_FACTURA_CONTADO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getSubTotalFacturaContado() {
        return subTotalFacturaContado;
    }

    public void setSubTotalFacturaContado(BigDecimal subTotalFacturaContado) {
        this.subTotalFacturaContado = subTotalFacturaContado;
    }

    @Column(name = "N_SUBTOTAL_FACTURA_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getSubtotalFacturaCredito() {
        return subtotalFacturaCredito;
    }

    public void setSubtotalFacturaCredito(BigDecimal subtotalFacturaCredito) {
        this.subtotalFacturaCredito = subtotalFacturaCredito;
    }

    @Column(name = "N_MONTO_FACTURADO_TARJETAS_CREDITO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoFacturadoTarjetasCredito() {
        return montoFacturadoTarjetasCredito;
    }

    public void setMontoFacturadoTarjetasCredito(BigDecimal montoFacturadoTarjetasCredito) {
        this.montoFacturadoTarjetasCredito = montoFacturadoTarjetasCredito;
    }

    @Column(name = "N_MONTO_FACTURADO_EFECTIVO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoFacturadoEfectivo() {
        return montoFacturadoEfectivo;
    }

    public void setMontoFacturadoEfectivo(BigDecimal montoFacturadoEfectivo) {
        this.montoFacturadoEfectivo = montoFacturadoEfectivo;
    }

    @Column(name = "N_MONTO_FACTURADO_CHEQUES", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoFacturadoCheques() {
        return montoFacturadoCheques;
    }

    public void setMontoFacturadoCheques(BigDecimal montoFacturadoCheques) {
        this.montoFacturadoCheques = montoFacturadoCheques;
    }

    @Column(name = "N_MONTO_FACTURADO_CANJES", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoFacturadoCanjes() {
        return montoFacturadoCanjes;
    }

    public void setMontoFacturadoCanjes(BigDecimal montoFacturadoCanjes) {
        this.montoFacturadoCanjes = montoFacturadoCanjes;
    }

    @Column(name = "N_MONTO_RECIBOS_CAJA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoRecibosCaja() {
        return montoRecibosCaja;
    }

    public void setMontoRecibosCaja(BigDecimal montoRecibosCaja) {
        this.montoRecibosCaja = montoRecibosCaja;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_CAJERO", referencedColumnName = "N_ID", nullable = false)
    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    @Column(name = "D_FECHA_CIERRE", nullable = true)
    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    @Column(name = "D_FECHA_APERTURA", nullable = false)
    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caja")
    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_APERTURA_CAJA", referencedColumnName = "N_ID", nullable = false)
    public AperturaCaja getAperturaCaja() {
        return aperturaCaja;
    }

    public void setAperturaCaja(AperturaCaja aperturaCaja) {
        this.aperturaCaja = aperturaCaja;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ESTADO", nullable = false)
    public EstadoCaja getEstado() {
        return estado;
    }

    public void setEstado(EstadoCaja estado) {
        this.estado = estado;
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
