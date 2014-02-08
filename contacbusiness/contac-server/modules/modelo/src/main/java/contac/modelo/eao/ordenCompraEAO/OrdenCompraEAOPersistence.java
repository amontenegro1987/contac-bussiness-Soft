package contac.modelo.eao.ordenCompraEAO;


import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.OrdenCompra;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Contac Business Software. All rights reserved 2012
 * User: Alejandro MOntenegro
 * Date: 02-06-14
 * Time: 11:18 PM
 */
public class OrdenCompraEAOPersistence extends GenericPersistenceEAO<OrdenCompra, Integer> implements OrdenCompraEAO {

    @Override
    public OrdenCompra findByNumero(long noOrdenCompra) throws GenericPersistenceEAOException, PersistenceClassNotFoundException {
        //Init Service
        initService();

        List<OrdenCompra> ordenesCompras = em.createQuery("select p from OrdenCompra p where p.noDocumento = :noDocumento").
                setParameter("noDocumento", noOrdenCompra).getResultList();
        if (ordenesCompras ==  null || ordenesCompras.size() < 1) {
            throw new PersistenceClassNotFoundException(noOrdenCompra + "");
        }

        return ordenesCompras.get(0);
    }

    @Override
    public List<OrdenCompra> findByFechas(Date fechaDesde, Date fechaHasta) throws GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select p from OrdenCompra p ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaDesde != null, "", " p.fechaAlta >= :fechaDesde ", "fechaDesde", fechaDesde));
        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaHasta != null, "", " p.fechaAlta <= :fechaHasta ", "fechaHasta", fechaHasta));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        ejbQuery = ejbQuery + " " + "order by p.noDocumento ";

        Query query = em.createQuery(ejbQuery);

        return QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();
    }
}