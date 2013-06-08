package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-25-2008
 * Time: 11:36:57 PM
 */

/**
 * Proforma Business Entity
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_PROFORMA")
@AttributeOverrides({
        @AttributeOverride(name = "noDocumento", column = @Column(name = "N_NO_PROFORMA")),
        @AttributeOverride(name = "nombreReceptor", column = @Column(name = "C_NOMBRE_CLIENTE")),
        @AttributeOverride(name = "direccion", column = @Column(name = "C_DIRECCION_ENTREGA"))
})
public class Proforma extends DocumentoComercial implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fechaVecimiento
     */
    private Date fechaVencimiento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articulos
     */
    private Set<ArticuloProforma> articulos;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    /**
     * Obtener fecha vencimiento proforma
     * @return Date
     */
    @Column(name = "D_FECHA_VENCIMIENTO", nullable = false)
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Setear fecha vencimiento proforma
     * @param fechaVencimiento, Date
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Setear articulos proforma
     * @return Set<ArticuloProforma>
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_PROFORMA", nullable = false)
    public Set<ArticuloProforma> getArticulos() {
        return articulos;
    }

    /**
     * Obtener articulos proforma
     * @param articulos, Set<ArticuloProforma>
     */
    public void setArticulos(Set<ArticuloProforma> articulos) {
        this.articulos = articulos;
    }
}
