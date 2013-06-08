/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.facturaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
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
     * Buscar facturas por rangos de fecha
     *
     * @param fechaDesde, Fecha desde
     * @param fechaHasta, Fecha hasta
     * @param idAlmacen,  Identificador de almacen
     * @return List<Factura>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Factura> findByFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException;

}
