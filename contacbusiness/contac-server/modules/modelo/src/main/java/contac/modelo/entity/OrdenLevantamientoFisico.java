/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Contac Business Software. All rigths reserved 2011.
 * User: EMontenegro
 * Date: 01-03-12
 * Time: 09:57 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_ORDEN_LEVANTAMIENTO_INVENTARIO")
@AttributeOverride(name = "noMovimiento", column = @Column(name = "NO_NOINVENTARIO"))
public class OrdenLevantamientoFisico extends OrdenMovimiento implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Fecha de solicitud de levantamiento
     */
    private Date fechaSolicitud;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Tipo de actualizacion --MASIVA - PARCIAL --
     */
    private int tipoActualizacion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacen
     */
    private Almacen almacen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: monto total de ajuste
     */
    private BigDecimal montoTotalAjuste;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articulos
     */
    private Set<ArticuloLevantamientoFisico> articulos;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    @Column(name = "D_FECHA_SOLICITUD", nullable = true)
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    @Column(name = "N_TIPO_ACTUALIZACION", nullable = false)
    public int getTipoActualizacion() {
        return tipoActualizacion;
    }

    public void setTipoActualizacion(int tipoActualizacion) {
        this.tipoActualizacion = tipoActualizacion;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @Column(name = "N_MONTO_TOTAL_AJUSTE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoTotalAjuste() {
        return montoTotalAjuste;
    }

    public void setMontoTotalAjuste(BigDecimal montoTotalAjuste) {
        this.montoTotalAjuste = montoTotalAjuste;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ordenLevantamientoFisico")
    public Set<ArticuloLevantamientoFisico> getArticulos() {
        return articulos;
    }

    public void setArticulos(Set<ArticuloLevantamientoFisico> articulos) {
        this.articulos = articulos;
    }
}
