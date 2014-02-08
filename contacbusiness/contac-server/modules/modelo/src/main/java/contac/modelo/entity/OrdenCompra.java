package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * Created with IntelliJ IDEA.
 * User: Alejandro Montenegro
 * Date: 02-04-14
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * OrdenCompra Business Entity
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_ORDEN_COMPRA")
@AttributeOverride(name = "noDocumento", column = @Column(name = "N_NO_ORDEN_COMPRA"))
public class OrdenCompra extends DocumentoOrdenCompra implements Serializable{

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Datos de pago
     */
    private Pago pago;

    private Set<ArticuloOrdenCompra> articulos = new HashSet<ArticuloOrdenCompra>();

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    /**
     * Obtener articulos ordenCompra
     *
     * @return Set<ArticuloOrdenCompra>
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ordenCompra")
    public Set<ArticuloOrdenCompra> getArticulos() {
        return articulos;
    }

    /**
     * Setear articulos factura
     *
     * @param articulos, Set<ArticuloFacura>
     */
    public void setArticulos(Set<ArticuloOrdenCompra> articulos) {
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


