/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.ordenTrasladoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenEntrada;
import contac.modelo.entity.OrdenTraslado;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-03-12
 * Time: 11:35 AM
 */
public class OrdenTrasladoEAOPersistence extends GenericPersistenceEAO<OrdenTraslado, Integer> implements OrdenTrasladoEAO {

    @Override
    public long obtenerNoTraslado() throws GenericPersistenceEAOException {

        //Init service
        initService();

        String query = "Select MAX(o.noMovimiento) From OrdenTraslado o";

        //Obtener numero maximo de movimiento
        Number noMovimiento = (Number) em.createQuery(query).getSingleResult();

        //El primer numero del traslado siempre inicia con 21
        if (noMovimiento == null)
            return 21;

        //Construyendo numero compuesto como cadena
        StringBuilder sb = new StringBuilder(String.valueOf(noMovimiento.longValue()));

        //Obteniendo subcadena del numero sin el primer indicador mas uno para registro siguiente
        long codigo = Long.parseLong(sb.substring(1)) + 1;
        String noTraslado = 2 + String.valueOf(codigo);

        return Long.parseLong(noTraslado);
    }

    @Override
    public List<OrdenTraslado> findByEstados(List<Integer> estados,Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Creating query
        String fromClause = "Select o from OrdenTraslado o ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();

        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaDesde != null, "", " o.fechaAlta >= :fechaDesde ", "fechaDesde", fechaDesde));

        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaHasta != null, "", " o.fechaAlta <= :fechaHasta ", "fechaHasta", fechaHasta));

        //3. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacen != null, "", " o.almacenSalida.id = :idAlmacen", "idAlmacen", idAlmacen));

        //Arma un fragmento con los diferentes estados...
        if ((estados != null) && (!estados.isEmpty())) {

            String esConditionalClause = " ( ";
            String prefijo = "";

            for (Integer estado : estados) {
                esConditionalClause += prefijo + " o.estado.id = " + estado;
                prefijo = " or ";
            }

            esConditionalClause += " ) ";

            //Solo creamos el fragmento si algun estado es distinto -1
            if (!prefijo.equals("")) {
                querySolver.add(new QueryFragment(true, "", esConditionalClause, null, null));
            }
        }

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<OrdenTraslado> listOrdenesTraslado = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listOrdenesTraslado;
    }

    @Override
    public List<OrdenTraslado> findByFechas(Date fechaInicio, Date fechaFin, Integer idAlmacen, Integer idAlmacenSalida) throws GenericPersistenceEAOException {
         System.out.println("idAlmacen [almacenEntrada] " + idAlmacen + " idAlmacenSalida " + idAlmacenSalida );
        //Init service
        initService();

        //Creating query
        String fromClause = "Select o from OrdenTraslado o ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();

        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaInicio != null, "", " o.fechaAlta >= :fechaInicio ", "fechaInicio", fechaInicio));

        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaFin != null, "", " o.fechaAlta <= :fechaFin ", "fechaFin", fechaFin));

        //3. Agregando parametro almacen Entrada
        querySolver.add(new QueryFragment(idAlmacen != null, "", " o.almacenEntrada.id = :idAlmacen", "idAlmacen", idAlmacen));

        //4. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacenSalida != null, "", " o.almacenSalida.id = :idAlmacenSalida", "idAlmacenSalida", idAlmacenSalida));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<OrdenTraslado> listOrdenesTraslado = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listOrdenesTraslado;
    }
}
