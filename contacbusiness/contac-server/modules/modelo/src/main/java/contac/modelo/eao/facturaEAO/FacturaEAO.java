/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.facturaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.EstadoMovimiento;
import contac.modelo.entity.Factura;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 02-16-12
 * Time: 11:03 AM
 */
public interface FacturaEAO extends GenericEAO<Factura, Integer> {

    /**
     * Buscar Factura por Numero de Factura y Almacen de Facturacion
     *
     * @param noFactura, Numero de factura
     * @param idAlmacen, Codigo de Almacen
     * @return Factura
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public Factura findByNumero(long noFactura, Integer idAlmacen) throws GenericPersistenceEAOException,
            PersistenceClassNotFoundException;

    /**
     * Buscar facturas por estado
     *
     * @param idEstado,  Estado de la factura
     * @param idAlmacen, Identificador de almacen
     * @return List<Factura>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Factura> findByEstado(Integer idEstado, Integer idAlmacen) throws GenericPersistenceEAOException;

    /**
     * Buscar Cobros facturas por rangos de fecha por numero
     *
     * @param numeroFactura, Numero de Factura
     * @return List<Factura>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Factura> findByFechasCobrosNo(Long numeroFactura) throws GenericPersistenceEAOException;

    /**
     * Buscar Cobros facturas por rangos de fecha
     *
     * @param fechaDesde,        Fecha Desde
     * @param fechaHasta,        Fecha Hasta
     * @param idAlmacen,         Identificador de almacen
     * @param idTipoFactura,     Identificador de Tipo de Factura
     * @param estadosMovimiento, Listado de Movimientos
     * @return List<Factura>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Factura> findByFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idTipoFactura,
                                      List<EstadoMovimiento> estadosMovimiento) throws GenericPersistenceEAOException;


    /**
     * Buscar Cobros facturas por rangos de fecha Sin Tipo de Factura
     *
     * @param fechaDesde,        Fecha Desde
     * @param fechaHasta,        Fecha Hasta
     * @param idAlmacen,         Identificador de almacen
     * @param estadosMovimiento, Listado de Movimientos
     * @return List<Factura>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Factura> findByFechasCobro(Date fechaDesde, Date fechaHasta, Integer idAlmacen, /*Integer idTipoFactura,*/
                                      List<EstadoMovimiento> estadosMovimiento) throws GenericPersistenceEAOException;

    /**
     * Buscar facturas por rangos de fecha
     *
     * @param fechaDesde,    Fecha desde
     * @param fechaHasta,    Fecha hasta
     * @param idAlmacen,     Identificador de almacen
     * @param idTipoFactura, Identificador de Tipo de Factura
     * @return List<Factura>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Factura> findByFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idTipoFactura)
            throws GenericPersistenceEAOException;

}
