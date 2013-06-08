package contac.modelo.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-08-2010
 * Time: 10:50:24 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_ORDEN_TRASLADO")
@AttributeOverrides(@AttributeOverride(name = "noMovimiento", column = @Column(name = "N_NOTRASLADO")))
public class OrdenTraslado extends OrdenMovimiento implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private String personaRecibe;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacenSalida
     */
    private Almacen almacenSalida;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacenEntrada
     */
    private Almacen almacenEntrada;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articulos
     */
    private Set<ArticuloTraslado> articulos;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================


    @Column(name = "C_PERSONA_RECIBE", nullable = false)
    public String getPersonaRecibe() {
        return personaRecibe;
    }

    public void setPersonaRecibe(String personaRecibe) {
        this.personaRecibe = personaRecibe;
    }

    /**
     * Obtener almacen salida
     *
     * @return Almacen
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ALMACEN_SALIDA", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacenSalida() {
        return almacenSalida;
    }

    /**
     * Setear almacen salida
     *
     * @param almacenSalida, Almacen
     */
    public void setAlmacenSalida(Almacen almacenSalida) {
        this.almacenSalida = almacenSalida;
    }

    /**
     * Obtener almacen salida
     *
     * @return Almacen
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ALMACEN_ENTRADA", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacenEntrada() {
        return almacenEntrada;
    }

    /**
     * Setear almacen entrada
     *
     * @param almacenEntrada, Almacen
     */
    public void setAlmacenEntrada(Almacen almacenEntrada) {
        this.almacenEntrada = almacenEntrada;
    }

    /**
     * Obtener articulos traslado
     *
     * @return Set<ArticuloTraslado>
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ordenTraslado")
    public Set<ArticuloTraslado> getArticulos() {
        return articulos;
    }

    /**
     * Setear articulos trasaldo
     *
     * @param articulos, Set<ArticuloTraslado>
     */
    public void setArticulos(Set<ArticuloTraslado> articulos) {
        this.articulos = articulos;
    }
}
