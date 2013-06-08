/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.clienteEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Cliente;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Contac Busines Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-17-12
 * Time: 02:46 PM
 */
public class ClienteEAOPersistence extends GenericPersistenceEAO<Cliente, Integer> implements ClienteEAO {

    @Override
    public long generarCodigoCliente() throws GenericPersistenceEAOException {

        //Init service
        initService();

        String query = "Select MAX(c.codigo) From Cliente c";

        //Obtener codigo maximo cliente
        Number codigo = (Number) em.createQuery(query).getSingleResult();

        if (codigo == null)
            return 1000;

        return codigo.longValue() + 1;
    }

    @Override
    public Cliente findByCodigo(long codigo) throws GenericPersistenceEAOException, PersistenceClassNotFoundException {

        //Init service
        initService();

        //Obteniendo listado de clientes
        List<Cliente> clientes = em.createQuery("Select c From Cliente c Where c.codigo = :codigo").setParameter("codigo", codigo).getResultList();

        if (clientes == null || clientes.size() < 1)
            throw new PersistenceClassNotFoundException("Se encontraron " + clientes.size() + " clientes con codigo: " + codigo);

        return clientes.get(0);
    }

    @Override
    public Cliente findByCodigoNit(String nit) throws GenericPersistenceEAOException, PersistenceClassNotFoundException {

        //Init service
        initService();

        //Obteniendo listado de clientes
        List<Cliente> clientes = em.createQuery("Select c From Cliente c Where c.nit = :nit").setParameter("nit", nit).getResultList();

        if (clientes == null || clientes.size() < 1)
            throw new PersistenceClassNotFoundException("Se encontraron " + clientes.size() + " clientes con nit: " + nit);

        return clientes.get(0);
    }

    @Override
    public List<Cliente> findByParams(String nit, String razonSocial) throws GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select c From Cliente c ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();

        if (nit != null) {
            //Agregando parametro de codigo
            querySolver.add(new QueryFragment((nit.length() > 0), "", " c.nit = :nit ",
                    "nit", nit));
        } else {
            //Agregando parametro razon social
            querySolver.add(new QueryFragment(((razonSocial != null) && (!razonSocial.equals(""))), "", " c.razonSocial like :razonSocial ",
                    "razonSocial", "%".concat(razonSocial).concat("%")));
            //Agregando parametro nombre comercial
            querySolver.add(new QueryFragment(((razonSocial != null) && (!razonSocial.equals(""))), "", " c.nombreComercial like :razonSocial ",
                    "razonSocial", "%".concat(razonSocial).concat("%")));
        }

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<Cliente> listClientes = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listClientes;

    }
}
