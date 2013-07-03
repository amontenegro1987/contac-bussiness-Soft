package contac.modelo.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 02-23-2010
 * Time: 10:27:18 PM
 */

/**
 * Articulo Traslado Garsa Business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_DETALLE_TRASLADO")
public class ArticuloTraslado extends Articulo implements java.io.Serializable {

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo es uno nuevo
     */
    private boolean create;

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo ha sido modificado de su version original
     */
    private boolean update;

    /**
     * PROPERTY NAME: Numero de documento de traslado inventario
     */
    private long noDocumento;

    /**
     * PROPERTY NAME: Orden de traslado inventario
     */
    private OrdenTraslado ordenTraslado;

    /**
     * PROPERTY NAME: Movimiento inventario
     */
    private Set<MovimientoInventario> movimientosInventario;

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

    @Column(name = "N_NODOCUMENTO", nullable = false)
    public long getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(long noDocumento) {
        this.noDocumento = noDocumento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_TRASLADO", nullable = false)
    public OrdenTraslado getOrdenTraslado() {
        return ordenTraslado;
    }

    public void setOrdenTraslado(OrdenTraslado ordenTraslado) {
        this.ordenTraslado = ordenTraslado;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "articuloTraslado")
    public Set<MovimientoInventario> getMovimientosInventario() {
        return movimientosInventario;
    }

    public void setMovimientosInventario(Set<MovimientoInventario> movimientosInventario) {
        this.movimientosInventario = movimientosInventario;
    }
}
