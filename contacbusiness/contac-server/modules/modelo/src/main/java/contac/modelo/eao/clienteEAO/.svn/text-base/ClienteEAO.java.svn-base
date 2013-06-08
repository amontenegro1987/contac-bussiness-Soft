/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.clienteEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Cliente;

import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emortiz
 * Date: 01-17-12
 * Time: 02:46 PM
 */
public interface ClienteEAO extends GenericEAO<Cliente, Integer> {

    /**
     * Generar codigo cliente automatico
     * @return long
     * @throws GenericPersistenceEAOException, Exception
     */
    public long generarCodigoCliente() throws GenericPersistenceEAOException;

    /**
     * Buscar cliente por su codigo
     * @param codigo, Codigo identificador del cliente
     * @return Cliente
     * @throws GenericPersistenceEAOException, Exception
     * @throws PersistenceClassNotFoundException, Exception
     */
    public Cliente findByCodigo(long codigo) throws GenericPersistenceEAOException, PersistenceClassNotFoundException;

    /**
     * Buscar cliente por su codigo nit
     * @param nit, Codigo NIT de registro tributario
     * @return Cliente
     * @throws GenericPersistenceEAOException, Exception
     * @throws PersistenceClassNotFoundException, Exception
     */
    public Cliente findByCodigoNit(String nit) throws GenericPersistenceEAOException, PersistenceClassNotFoundException;

    /**
     * Buscar clientes por parametros
     * @param nit, Numero de registro tributario
     * @param razonSocial, Razon social registrada
     * @return List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<Cliente> findByParams(String nit, String razonSocial) throws GenericPersistenceEAOException;
}
