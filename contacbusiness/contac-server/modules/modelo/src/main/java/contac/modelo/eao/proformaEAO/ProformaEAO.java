/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.proformaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.entity.Proforma;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import java.util.Date;
import java.util.List;
/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 02-16-12
 * Time: 11:20 AM
 */
public interface ProformaEAO extends GenericEAO<Proforma, Integer> {
    /**
     * Buscar Proforma por Numero de Proforma y Almacen de Facturacion
     *
     * @param noProforma, Numero de Proforma
     * @param idAlmacen, Codigo de Almacen
     * @return Proforma
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public Proforma findByNumero(long noProforma, Integer idAlmacen) throws GenericPersistenceEAOException,
            PersistenceClassNotFoundException;
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
    public List<Proforma> findByFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException;

}
