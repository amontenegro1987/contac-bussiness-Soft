/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.eao.agenteVentasEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.AgenteVentas;

import java.util.List;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 09-11-12
 * Time: 02:29 PM
 */
public interface AgenteVentasEAO extends GenericEAO<AgenteVentas, Integer> {

    /**
     * Buscar Agentes de Ventas Ordenados por Codigo
     *
     * @return List
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<AgenteVentas> findAllOrderByCodigo() throws GenericPersistenceEAOException;

    /**
     * Buscar Agentes de Ventas por Almacen
     *
     * @param idAlmacen, Identificador del almacen
     * @return List<AgenteVentas>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<AgenteVentas> findByAlmacen(Integer idAlmacen) throws GenericPersistenceEAOException;

    /**
     * Buscar Agente de Ventas por codigo
     *
     * @param codigo, Codigo identificador de Agente de Ventas
     * @return AgenteVentas
     * @throws PersistenceClassNotFoundException,
     *          Exception
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public AgenteVentas findByCodigo(long codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Buscar Agentes de Ventas por Almacen y Codigo
     *
     * @param codigo,    Codigo identificador de Agente de Ventas
     * @param idAlmacen, Identificador de Almacen
     * @return AgenteVentas
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public AgenteVentas findByCodigo(long codigo, Integer idAlmacen) throws PersistenceClassNotFoundException,
            GenericPersistenceEAOException;
}
