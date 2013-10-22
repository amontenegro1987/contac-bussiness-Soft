/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.proformaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Proforma;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012
 * User: emortiz
 * Date: 02-16-12
 * Time: 11:20 AM
 */
public class ProformaEAOPersistence extends GenericPersistenceEAO<Proforma, Integer> implements ProformaEAO {
    @Override
    public Proforma findByNumero(long noProforma, Integer idAlmacen) throws GenericPersistenceEAOException, PersistenceClassNotFoundException {
        //Init Service
        initService();

        List<Proforma> proformas = em.createQuery("select p from Proforma p where p.noDocumento = :noDocumento and p.almacen.id = :idAlmacen").
       setParameter("noDocumento", noProforma).setParameter("idAlmacen", idAlmacen).getResultList();
        if (proformas ==  null || proformas.size() < 1) {
            throw new PersistenceClassNotFoundException(noProforma + "");
        }

        return proformas.get(0);
    }

    @Override
    public List<Proforma> findByFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select p from Proforma p ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaDesde != null, "", " p.fechaAlta >= :fechaDesde ", "fechaDesde", fechaDesde));
        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaHasta != null, "", " p.fechaAlta <= :fechaHasta ", "fechaHasta", fechaHasta));
        //3. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacen != null, "", " p.almacen.id = :idAlmacen", "idAlmacen", idAlmacen));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        ejbQuery = ejbQuery + " " + "order by p.noDocumento ";

        Query query = em.createQuery(ejbQuery);

        return QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();
    }
}
