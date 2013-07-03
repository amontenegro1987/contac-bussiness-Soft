package contac.modelo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 02-23-2010
 * Time: 10:27:34 PM
 */

/**
 * Representa un Articulo Factura
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_DETALLE_FACTURA")
public class ArticuloFactura extends Articulo implements java.io.Serializable {

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo es uno nuevo
     */
    private boolean create;

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo ha sido modificado de su version original
     */
    private boolean update;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: precio bruto
     */
    private BigDecimal precioBruto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private BigDecimal porcDescuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: descuento
     */
    private BigDecimal descuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: precio antes de impuesto
     */
    private BigDecimal precioAntesImpuesto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: porcentaje de IVA
     */
    private BigDecimal porcIva;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: iva
     */
    private BigDecimal iva;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: porcentaje retencion fuente
     */
    private BigDecimal porcRetencionFuente;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: retencionFuente
     */
    private BigDecimal retencionFuente;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: porcentaje de retencion municipal
     */
    private BigDecimal porcRetencionMunicipal;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: retencionMunicipal
     */
    private BigDecimal retencionMunicipal;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: monto
     */
    private BigDecimal precioNeto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Precio promocion
     */
    private boolean precioPromocion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Exento
     */
    private boolean exento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Numero de factura
     */
    private long noFactura;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Factura
     */
    private Factura factura;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Movimiento Inventario
     */
    private MovimientoInventario movimientoInventario;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    @Transient
    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    @Transient
    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
     * Obtener precio bruto del articulo
     *
     * @return BigDecimal
     */
    @Column(name = "N_PRECIOBRUTO", nullable = false)
    public BigDecimal getPrecioBruto() {
        return precioBruto;
    }

    /**
     * Setear precio bruto del articulo
     *
     * @param precioBruto, BigDecimal
     */
    public void setPrecioBruto(BigDecimal precioBruto) {
        this.precioBruto = precioBruto;
    }

    @Column(name = "N_PORC_DESCUENTO", nullable = false)
    public BigDecimal getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(BigDecimal porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    /**
     * Obtener descuento del articulo
     *
     * @return BigDecimal
     */
    @Column(name = "N_DESCUENTO", nullable = false)
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Setear descuento del articulo
     *
     * @param descuento, BigDecimal
     */
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    @Column(name = "N_PRECIO_ANTES_IMPUESTO", nullable = false)
    public BigDecimal getPrecioAntesImpuesto() {
        return precioAntesImpuesto;
    }

    public void setPrecioAntesImpuesto(BigDecimal precioAntesImpuesto) {
        this.precioAntesImpuesto = precioAntesImpuesto;
    }

    /**
     * Obtener IVA
     *
     * @return BigDecimal
     */
    @Column(name = "N_IVA", nullable = false)
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * Setear IVA
     *
     * @param iva, BigDecimal
     */
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    /**
     * Obtener retencion fuente
     *
     * @return BigDecimal
     */
    @Column(name = "N_RETENCIONFUENTE", nullable = false)
    public BigDecimal getRetencionFuente() {
        return retencionFuente;
    }

    /**
     * Setear retencion fuente
     *
     * @param retencionFuente, BigDecimal
     */
    public void setRetencionFuente(BigDecimal retencionFuente) {
        this.retencionFuente = retencionFuente;
    }

    /**
     * Obtener retencion municipal
     *
     * @return BigDecimal
     */
    @Column(name = "N_RETENCIONMUNICIPAL", nullable = false)
    public BigDecimal getRetencionMunicipal() {
        return retencionMunicipal;
    }

    public void setRetencionMunicipal(BigDecimal retencionMunicipal) {
        this.retencionMunicipal = retencionMunicipal;
    }

    /**
     * Obtener precio neto
     *
     * @return BigDecimal
     */
    @Column(name = "N_PRECIONETO", nullable = false)
    public BigDecimal getPrecioNeto() {
        return precioNeto;
    }

    /**
     * Setear precio neto
     *
     * @param precioNeto, BigDecimal
     */
    public void setPrecioNeto(BigDecimal precioNeto) {
        this.precioNeto = precioNeto;
    }

    @Column(name = "N_PORC_IVA", nullable = false)
    public BigDecimal getPorcIva() {
        return porcIva;
    }

    public void setPorcIva(BigDecimal porcIva) {
        this.porcIva = porcIva;
    }

    @Column(name = "N_PORC_RETENCIONFUENTE", nullable = false)
    public BigDecimal getPorcRetencionFuente() {
        return porcRetencionFuente;
    }

    public void setPorcRetencionFuente(BigDecimal porcRetencionFuente) {
        this.porcRetencionFuente = porcRetencionFuente;
    }

    @Column(name = "N_PORC_RETENCIONMUNICIPAL", nullable = false)
    public BigDecimal getPorcRetencionMunicipal() {
        return porcRetencionMunicipal;
    }

    public void setPorcRetencionMunicipal(BigDecimal porcRetencionMunicipal) {
        this.porcRetencionMunicipal = porcRetencionMunicipal;
    }

    @Column(name = "B_PROMOCION", nullable = false)
    public boolean isPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(boolean precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    @Column(name = "B_EXENTO", nullable = false)
    public boolean isExento() {
        return exento;
    }

    public void setExento(boolean exento) {
        this.exento = exento;
    }

    @Column(name = "N_NOFACTURA", nullable = false)
    public long getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(long noFactura) {
        this.noFactura = noFactura;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_FACTURA", nullable = false)
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "articuloFactura")
    public MovimientoInventario getMovimientoInventario() {
        return movimientoInventario;
    }

    public void setMovimientoInventario(MovimientoInventario movimientoInventario) {
        this.movimientoInventario = movimientoInventario;
    }
}
