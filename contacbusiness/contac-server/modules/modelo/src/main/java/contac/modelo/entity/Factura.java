package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-27-2008
 * Time: 10:04:42 PM
 */

/**
 * Factura Business Entity
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_FACTURA")
@AttributeOverrides({
        @AttributeOverride(name = "noDocumento", column = @Column(name = "N_NO_FACTURA")),
        @AttributeOverride(name = "nombreReceptor", column = @Column(name = "C_NOMBRECLIENTE")),
        @AttributeOverride(name = "direccion", column = @Column(name = "C_DIRECCION_ENTREGA"))
})
public class Factura extends DocumentoComercial implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: proforma
     */
    private Proforma proforma;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoFactura
     */
    private byte tipoFactura;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Agente de ventas
     */
    private AgenteVentas agenteVentas;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Datos de pago
     */
    private Pago pago;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articulos
     */
    private Set<ArticuloFactura> articulos = new HashSet<ArticuloFactura>();

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    /**
     * Obtener proforma desde donde factura
     *
     * @return Proforma
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_PROFORMA", nullable = true)
    public Proforma getProforma() {
        return proforma;
    }

    /**
     * Setear proforma desde donde factura
     *
     * @param proforma, Proforma
     */
    public void setProforma(Proforma proforma) {
        this.proforma = proforma;
    }

    /**
     * Obtener tipo de factura
     *
     * @return byte
     */
    @Column(name = "N_TIPO_FACTURA", nullable = false)
    public byte getTipoFactura() {
        return tipoFactura;
    }

    /**
     * Setear tipo de factura
     *
     * @param tipoFactura, byte
     */
    public void setTipoFactura(byte tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_AGENTE_VENTAS", nullable = false)
    public AgenteVentas getAgenteVentas() {
        return agenteVentas;
    }

    public void setAgenteVentas(AgenteVentas agenteVentas) {
        this.agenteVentas = agenteVentas;
    }

    /**
     * Obtener articulos factura
     *
     * @return Set<ArticuloFactura>
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "factura")
    public Set<ArticuloFactura> getArticulos() {
        return articulos;
    }

    /**
     * Setear articulos factura
     *
     * @param articulos, Set<ArticuloFacura>
     */
    public void setArticulos(Set<ArticuloFactura> articulos) {
        this.articulos = articulos;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_PAGO", referencedColumnName = "N_ID", nullable = true)
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
