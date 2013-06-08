/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Contac Business Software. All rigths reserved 2011.
 * User: EMontenegro
 * Date: 01-03-12
 * Time: 10:01 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_DETALLE_LEVANTAMIENTO_FISICO")
public class ArticuloLevantamientoFisico extends Articulo implements java.io.Serializable {

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo es uno nuevo
     */
    private boolean create;

    /**
     * PROPERTY NAME: Propiedad que indica si el articulo ha sido modificado de su version original
     */
    private boolean update;

    /**
     * PROPERTY NAME: Cantidad de existencia
     */
    private long cantidadExistencia;

    /**
     * PROPERTY NAME: cantidad ajuste inventario fisico
     */
    private long cantidadAjuste;

    /**
     * PROPERTY NAME: monto costo articulo ajuste en inventario fisico
     */
    private BigDecimal montoAjuste;

    /**
     * PROPERTY NAME: numero de movimiento de la orden
     */
    private long noMovimiento;

    /**
     * PROPERTY NAME: Orden de Entrada inventario
     */
    private OrdenLevantamientoFisico ordenLevantamientoFisico;

    /**
     * PROPERTY NAME: Movimiento inventario registrado
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

    @Column(name = "N_CANTIDAD_EXISTENCIA", nullable = false)
    public long getCantidadExistencia() {
        return cantidadExistencia;
    }

    public void setCantidadExistencia(long cantidadExistencia) {
        this.cantidadExistencia = cantidadExistencia;
    }

    @Column(name = "N_CANTIDAD_AJUSTE", nullable = false)
    public long getCantidadAjuste() {
        return cantidadAjuste;
    }

    public void setCantidadAjuste(long cantidadAjuste) {
        this.cantidadAjuste = cantidadAjuste;
    }

    @Column(name = "N_MONTO_AJUSTE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoAjuste() {
        return montoAjuste;
    }

    public void setMontoAjuste(BigDecimal montoAjuste) {
        this.montoAjuste = montoAjuste;
    }

    @Column(name = "N_NOMOVIMIENTO", nullable = false)
    public long getNoMovimiento() {
        return noMovimiento;
    }

    public void setNoMovimiento(long noMovimiento) {
        this.noMovimiento = noMovimiento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_LEVANTAMIENTO_FISICO", nullable = false)
    public OrdenLevantamientoFisico getOrdenLevantamientoFisico() {
        return ordenLevantamientoFisico;
    }

    public void setOrdenLevantamientoFisico(OrdenLevantamientoFisico ordenLevantamientoFisico) {
        this.ordenLevantamientoFisico = ordenLevantamientoFisico;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articuloLevantamientoFisico")
    public MovimientoInventario getMovimientoInventario() {
        return movimientoInventario;
    }

    public void setMovimientoInventario(MovimientoInventario movimientoInventario) {
        this.movimientoInventario = movimientoInventario;
    }
}
