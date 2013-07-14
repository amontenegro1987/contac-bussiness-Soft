package contac.modelo.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-07-2010
 * Time: 09:53:13 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_ORDEN_SALIDA")
@AttributeOverride(name = "noMovimiento", column = @Column(name = "N_NOSALIDA"))
public class OrdenSalida extends OrdenMovimiento implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private String personaAutoriza;

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
    private Set<ArticuloSalida> articulos;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================


    @Column(name = "C_PERSONAAUTORIZA", nullable = false)
    public String getPersonaAutoriza() {
        return personaAutoriza;
    }

    public void setPersonaAutoriza(String personaAutoriza) {
        this.personaAutoriza = personaAutoriza;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ordenSalida")
    public Set<ArticuloSalida> getArticulos() {
       return articulos;
    }

    public void setArticulos(Set<ArticuloSalida> articulos) {
        this.articulos = articulos;
    }
}
