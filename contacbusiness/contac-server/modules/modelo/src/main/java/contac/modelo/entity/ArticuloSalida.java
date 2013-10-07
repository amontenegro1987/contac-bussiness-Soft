package contac.modelo.entity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 02-23-2010
 * Time: 10:27:08 PM
 */

/**
 * Articulo Salida
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_DETALLE_SALIDA")
public class ArticuloSalida extends Articulo implements java.io.Serializable {

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo es uno nuevo
     */
    private boolean create;

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo ha sido modificado de su version original
     */
    private boolean update;

    /**
     * PROPERTY NAME: Numero de documento de salida de inventario
     */
    private long noDocumento;

    /**
     * PROPERTY NAME: Orden de Salida
     */
    private OrdenSalida ordenSalida;

    /**
     * PROPERTY NAME: Movimiento inventario
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

    @Column(name = "N_NODOCUMENTO", nullable = false)
    public long getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(long noDocumento) {
        this.noDocumento = noDocumento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_SALIDA", nullable = false)
    public OrdenSalida getOrdenSalida() {
        return ordenSalida;
    }

    public void setOrdenSalida(OrdenSalida ordenSalida) {
        this.ordenSalida = ordenSalida;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "articuloSalida")
    public MovimientoInventario getMovimientoInventario() {
        return movimientoInventario;
    }

    public void setMovimientoInventario(MovimientoInventario movimientoInventario) {
        this.movimientoInventario = movimientoInventario;
    }
}
