/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.tasaCambioEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.TasaCambio;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: hmurbina
 * Date: Oct 12, 2010
 * Time: 10:25:23 PM
 */
public interface TasaCambioEAO extends GenericEAO<TasaCambio, Integer> {

    /**
     * Buscar tasa de cambio activa para facturacion
     *
     * @return TasaCambio
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public TasaCambio findByActivaFacturacion() throws GenericPersistenceEAOException;

    /**
     * Obtener tasa de cambio por banco y fecha de conversion
     *
     * @param banco,           Banco
     * @param fechaConversion, Fecha de conversion
     * @return List
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List getTasaCambioByBancoAndFecha(String banco, String fechaConversion) throws GenericPersistenceEAOException;

    /**
     * Buscar tasas de cambio por fechas
     *
     * @param fechaInicio, Fecha de Inicio del rango
     * @param fechaFinal,  Fecha final del rango
     * @return List<TasaCambio>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<TasaCambio> findByFechas(Date fechaInicio, Date fechaFinal) throws GenericPersistenceEAOException;

    /**
     * Buscar tasas de cambio para facturacion
     *
     * @return List<TasaCambio>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<TasaCambio> findByFacturacion() throws GenericPersistenceEAOException;

}
