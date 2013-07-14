package contac.modelo.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-07-2010
 * Time: 08:44:38 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_ORDEN_ENTRADA")
@AttributeOverride(name = "noMovimiento", column = @Column(name = "N_NOENTRADA"))
public class OrdenEntrada extends OrdenMovimiento implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private Date fechaSolicitud;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipo de entrada
     */
    private int tipoEntrada;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacen
     */
    private Almacen almacen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articulos
     */
    private Set<ArticuloEntrada> articulos;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    @Column(name = "D_FECHA_SOLICITUD", nullable = true)
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    @Column(name = "N_TIPO_ENTRADA", nullable = false)
    public int getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(int tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    /**
     * Obtener almacen movimiento
     *
     * @return Almacen
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * Setear almacen
     *
     * @param almacen, Almacen
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    /**
     * Obtener articulos
     *
     * @return Set<ArticuloEntrada>
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ordenEntrada")
    public Set<ArticuloEntrada> getArticulos() {
       return articulos;
    }

    /**
     * Setear articulos
     *
     * @param articulos, Set<ArticuloEntrada>
     */
    public void setArticulos(Set<ArticuloEntrada> articulos) {
        this.articulos = articulos;
    }
}
