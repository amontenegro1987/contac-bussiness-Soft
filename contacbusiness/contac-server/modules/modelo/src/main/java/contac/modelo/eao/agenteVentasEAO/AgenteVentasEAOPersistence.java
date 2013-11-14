/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.agenteVentasEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
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
public class AgenteVentasEAOPersistence extends GenericPersistenceEAO<AgenteVentas, Integer> implements AgenteVentasEAO {

    @Override
    public List<AgenteVentas> findAllOrderByCodigo() throws GenericPersistenceEAOException {
        //Init service
        initService();

        //Query JPA String
        String query = "Select a from AgenteVentas a order by a.codigo";

        //Creating query JPA
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<AgenteVentas> findByAlmacen(Integer idAlmacen) throws GenericPersistenceEAOException {
        
        //Init service
        initService();

        //Query JPA String
        String query = "Select a from AgenteVentas a where a.activo = true and a.almacen.id = :idAlmacen order by a.codigo";

        //Creating query JPA
        return em.createQuery(query).setParameter("idAlmacen", idAlmacen).getResultList();
    }

    @Override
    public AgenteVentas findByCodigo(long codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {
        
        //Init service
        initService();
        
        //Query JPA String
        String query = "Select a from AgenteVentas a where a.codigo = :codigo";
        
        //Creating query JPA
        List<AgenteVentas> agentesVentas = em.createQuery(query).setParameter("codigo", codigo).getResultList();
        
        if (agentesVentas == null || agentesVentas.size() < 1)
            throw new PersistenceClassNotFoundException(codigo + "");
        
        return agentesVentas.get(0);
    }

    @Override
    public AgenteVentas findByCodigo(long codigo, Integer idAlmacen) throws PersistenceClassNotFoundException,
            GenericPersistenceEAOException {
        
        //Init service
        initService();
        
        //Query JPA String
        String query = "Select a from AgenteVentas a where a.activo = true and a.almacen.id = :idAlmacen and a.codigo = :codigo";
        
        //Creating query JPA
        List<AgenteVentas> agentesVentas = em.createQuery(query).setParameter("idAlmacen", idAlmacen).
                setParameter("codigo", codigo).getResultList();
        
        if (agentesVentas == null || agentesVentas.isEmpty())
            throw new PersistenceClassNotFoundException(codigo + "");
        
        return agentesVentas.get(0);
    }
}
