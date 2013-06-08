package contac.modelo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 02-23-2010
 * Time: 10:27:45 PM
 */

/**
 * Representa un Articulo Proforma
 */
@Entity
@Table(name = "FAC_DETALLE_PROFORMA")
@EntityListeners(Audit.class)
public class ArticuloProforma extends Articulo implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: precio bruto
     */
    private BigDecimal precioBruto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: descuento
     */
    private BigDecimal descuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: iva
     */
    private BigDecimal iva;
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: retencionFuente
     */
    private BigDecimal retencionFuente;

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

    // ==================================<METODOS GETTTER AND SETTERS>=================================================


    /**
     * Obtener precio bruto del articulo
     * @return BigDecimal
     */
    @Column(name = "N_PRECIOBRUTO")
    public BigDecimal getPrecioBruto() {
        return precioBruto;
    }

    /**
     * Setear precio bruto del articulo
     * @param precioBruto, BigDecimal
     */
    public void setPrecioBruto(BigDecimal precioBruto) {
        this.precioBruto = precioBruto;
    }

    /**
     * Obtener descuento del articulo
     * @return BigDecimal
     */
    @Column(name = "N_DESCUENTO")
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Setear descuento del articulo
     * @param descuento, BigDecimal
     */
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    /**
     * Obtener IVA
     * @return BigDecimal
     */
    @Column(name = "N_IVA")
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * Setear IVA
     * @param iva, BigDecimal
     */
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    /**
     * Obtener retencion fuente
     * @return BigDecimal
     */
    @Column(name = "N_RETENCIONFUENTE")
    public BigDecimal getRetencionFuente() {
        return retencionFuente;
    }

    /**
     * Setear retencion fuente
     * @param retencionFuente, BigDecimal
     */
    public void setRetencionFuente(BigDecimal retencionFuente) {
        this.retencionFuente = retencionFuente;
    }

    /**
     * Obtener retencion municipal
     * @return BigDecimal
     */
    @Column(name = "N_RETENCIONMUNICIPAL")
    public BigDecimal getRetencionMunicipal() {
        return retencionMunicipal;
    }

    public void setRetencionMunicipal(BigDecimal retencionMunicipal) {
        this.retencionMunicipal = retencionMunicipal;
    }

    /**
     * Obtener precio neto
     * @return BigDecimal
     */
    @Column(name = "N_PRECIONETO")
    public BigDecimal getPrecioNeto() {
        return precioNeto;
    }

    /**
     * Setear precio neto
     * @param precioNeto, BigDecimal
     */
    public void setPrecioNeto(BigDecimal precioNeto) {
        this.precioNeto = precioNeto;
    }
}
