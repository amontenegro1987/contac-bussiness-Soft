package contac.modelo.eao.ordenEntradaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenEntrada;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-02-11
 * Time: 09:34 PM
 */
public interface OrdenEntradaEAO extends GenericEAO<OrdenEntrada, Integer> {

    /**
     * Obtener numero entrada
     * @return long
     * @throws GenericPersistenceEAOException, Exception
     */
    public long obtenerNoEntrada() throws GenericPersistenceEAOException;

    /**
     * Obtener ordenes entrada por tipo
     * @param tipoEntrada, tipo de orden de entrada
     * @return List<OrdenEntrada></OrdenEntrada>
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenEntrada> findByTipoEntrada(int tipoEntrada) throws GenericPersistenceEAOException;

    /**
     * Find Ordenes de Entrada por estados
     * @param estados, List
     * @return List<OrdenEntrada>
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenEntrada> findByEstados(List<Integer> estados) throws GenericPersistenceEAOException;

    /**
     * Buscar ordenes de entrada por fechas de inicio y fecha fin
     * @param fechaInicio, Fecha de inicio del rango
     * @param fechaFin, Fecha de fin del rango
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<OrdenEntrada> findByFechas(Date fechaInicio, Date fechaFin) throws GenericPersistenceEAOException;
}
