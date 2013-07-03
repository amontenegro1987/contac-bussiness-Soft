package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Representa una llave compuesta para producto
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-24-2010
 * Time: 09:49:20 AM
 */
@Embeddable
public class ProductoCompaniaPK implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: idProducto
     */
    private Integer idProducto;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: compania
     */
    private Compania compania;

    /**
     * Default Constructor
     */
    public ProductoCompaniaPK(){}

    /**
     * Constructor
     * @param compania, codigo identificador de la compania
     */
    public ProductoCompaniaPK(Compania compania){
       this.compania = compania;
    }

    /**
     * Obtiene el id del producto generado
      * @return Integer
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * Setea el id del producto generado
     * @param idProducto, Integer
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la compania del producto
     * @return Compania
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_COMPANIA", nullable = false)
    public Compania getCompania() {
        return compania;
    }

    /**
     * Setea la compania del producto
     * @param compania, Compania
     */
    public void setCompania(Compania compania) {
        this.compania = compania;
    }
}
