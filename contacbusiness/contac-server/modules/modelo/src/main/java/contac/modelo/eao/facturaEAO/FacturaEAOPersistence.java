/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.facturaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Factura;
import contac.modelo.entity.Producto;
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
 * Time: 11:03 AM.
 */
public class FacturaEAOPersistence extends GenericPersistenceEAO<Factura, Integer> implements FacturaEAO {


    @Override
    public Factura findByNumero(long noFactura, Integer idAlmacen) throws GenericPersistenceEAOException,
            PersistenceClassNotFoundException {

        //Init service
        initService();

        List<Factura> facturas = em.createQuery("select f from Factura f where f.noDocumento = :noDocumento and f.almacen.id = :idAlmacen").
                setParameter("noDocumento", noFactura).setParameter("idAlmacen", idAlmacen).getResultList();

        if (facturas ==  null || facturas.size() < 1) {
            throw new PersistenceClassNotFoundException(noFactura + "");
        }

        return facturas.get(0);
    }

    @Override
    public List<Factura> findByEstado(Integer idEstado, Integer idAlmacen) throws GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select f from Factura f ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(idEstado != null, "", " f.estadoMovimiento.id = :idEstado ", "idEstado", idEstado));
        //3. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacen != null, "", " f.almacen.id = :idAlmacen", "idAlmacen", idAlmacen));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        return QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();
    }

    @Override
    public List<Factura> findByFechasCobros(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idTipoFactura, Integer idEstado, Integer idEstadoPagado)
            throws GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select f from Factura f ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaDesde != null, "", " f.fechaAlta >= :fechaDesde ", "fechaDesde", fechaDesde));
        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaHasta != null, "", " f.fechaAlta <= :fechaHasta ", "fechaHasta", fechaHasta));
        //3. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacen != null, "", " f.almacen.id = :idAlmacen", "idAlmacen", idAlmacen));
        //4. Agregando parametro tipo de factura
        querySolver.add(new QueryFragment(idTipoFactura != null, "", " f.tipoFactura = :idTipoFactura", "idTipoFactura",
                idTipoFactura != null ? idTipoFactura.byteValue() : null));
        //5. Agregado parametro Estado Movimiento
        querySolver.add(new QueryFragment(idEstado != null, "", " f.estadoMovimiento.id = :idEstado ", "idEstado",
                idEstado != null ? idEstadoPagado : idEstadoPagado));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        ejbQuery = ejbQuery + " " + "order by f.noDocumento ";

        Query query = em.createQuery(ejbQuery);

        return QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();
    }

    @Override
    public List<Factura> findByFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idTipoFactura)
            throws GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select f from Factura f ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaDesde != null, "", " f.fechaAlta >= :fechaDesde ", "fechaDesde", fechaDesde));
        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaHasta != null, "", " f.fechaAlta <= :fechaHasta ", "fechaHasta", fechaHasta));
        //3. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacen != null, "", " f.almacen.id = :idAlmacen", "idAlmacen", idAlmacen));
        //4. Agregando parametro tipo de factura
        querySolver.add(new QueryFragment(idTipoFactura != null, "", " f.tipoFactura = :idTipoFactura", "idTipoFactura",
                idTipoFactura != null ? idTipoFactura.byteValue() : null));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        ejbQuery = ejbQuery + " " + "order by f.noDocumento ";

        Query query = em.createQuery(ejbQuery);

        return QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();
    }
}
