/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.ordenSalidaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenSalida;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-04-12
 * Time: 09:08 PM
 */
public interface OrdenSalidaEAO extends GenericEAO<OrdenSalida, Integer> {

    /**
     * Obtener numero de salida consecutivo
     * @return long
     * @throws GenericPersistenceEAOException, Exception
     */
    public long obtenerNoSalida() throws GenericPersistenceEAOException;

    /**
     * Obtener ordenes de salida por estados
     * @param estados, List
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenSalida> findByEstados(List<Integer> estados) throws GenericPersistenceEAOException;

    /**
     * Buscar ordenes de salida por rango de fechas
     * @param fechaInicio, Date
     * @param fechaFin, Date
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenSalida> findByFechas(Date fechaInicio, Date fechaFin) throws GenericPersistenceEAOException;
}
