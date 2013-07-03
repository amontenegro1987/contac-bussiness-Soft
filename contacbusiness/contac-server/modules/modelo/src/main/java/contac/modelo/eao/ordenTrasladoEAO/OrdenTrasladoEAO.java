/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.ordenTrasladoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenTraslado;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-03-12
 * Time: 11:35 AM
 */
public interface OrdenTrasladoEAO extends GenericEAO<OrdenTraslado, Integer> {

    /**
     * Obtener numero de traslado
     * @return long
     * @throws GenericPersistenceEAOException, Exception
     */
    public long obtenerNoTraslado() throws GenericPersistenceEAOException;

    /**
     * Buscar ordenes de traslado por estados
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenTraslado> findByEstados(List<Integer> estados) throws GenericPersistenceEAOException;

    /**
     * Buscar ordenes de traslado por rango de fechas
     * @param fechaInicio, Date
     * @param fechaFin, Date
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenTraslado> findByFechas(Date fechaInicio, Date fechaFin) throws GenericPersistenceEAOException;
}
