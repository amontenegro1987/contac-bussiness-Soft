/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.eao.ordenLevantamientoFisicoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenLevantamientoFisico;
import contac.modelo.entity.OrdenTraslado;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emortiz
 * Date: 01-06-12
 * Time: 03:26 PM
 */
public interface OrdenLevantamientoFisicoEAO extends GenericEAO<OrdenLevantamientoFisico, Integer> {

    /**
     * Obtener Numero de levantamiento fisico
     * @return long
     * @throws GenericPersistenceEAOException, Exception
     */
    public long obtenerNoLevantamiento() throws GenericPersistenceEAOException;

    /**
     * Find Ordenes de Levantamiento fisico por estados
     * @param estados, List
     * @return List<OrdenLevantamientoFisico>
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenLevantamientoFisico> findByEstados(List<Integer> estados) throws GenericPersistenceEAOException;

    /**
     * Borrar detalle levantamiento fisico
     *
     * @param idLevantamientoFisico, Identificador de levantamiento fisico
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException,
     *          Exception
     */
    public void deleteDetalleLevantamientoFisico(Integer idLevantamientoFisico) throws GenericPersistenceEAOException;

    /**
     * Buscar ordenes de traslado por rango de fechas
     * @param fechaInicio, Date
     * @param fechaFin, Date
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenLevantamientoFisico> findByFechas(Date fechaInicio, Date fechaFin, Integer idAlmacen) throws GenericPersistenceEAOException;
}
