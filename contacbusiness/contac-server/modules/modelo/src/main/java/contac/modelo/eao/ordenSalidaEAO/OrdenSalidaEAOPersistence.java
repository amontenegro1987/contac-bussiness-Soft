/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.ordenSalidaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenSalida;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-04-12
 * Time: 09:08 PM
 */
public class OrdenSalidaEAOPersistence extends GenericPersistenceEAO<OrdenSalida, Integer> implements OrdenSalidaEAO {

    @Override
    public long obtenerNoSalida() throws GenericPersistenceEAOException {

        //Init service
        initService();

        String query = "Select MAX(o.noMovimiento) From OrdenSalida o";

        //Obtener numero maximo de movimiento
        Number noMovimiento = (Number) em.createQuery(query).getSingleResult();

        //El primer numero de la salida siempre inicia con 31
        if (noMovimiento == null)
            return 31;

        //Construyendo numero compuesto como cadena
        StringBuilder sb = new StringBuilder(String.valueOf(noMovimiento.longValue()));

        //Obteniendo subcadena del numero sin el primer indicador mas uno para registro siguiente
        long codigo = Long.parseLong(sb.substring(1)) + 1;
        String noSalida = 3 + String.valueOf(codigo);

        return Long.parseLong(noSalida);
    }

    @Override
    public List<OrdenSalida> findByEstados(List<Integer> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Creating query
        String fromClause = "Select o from OrdenSalida o ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();

        //1. Agregando parametro fecha desde
        querySolver.add(new QueryFragment(fechaDesde != null, "", " o.fechaAlta >= :fechaDesde ", "fechaDesde", fechaDesde));

        //2. Agregando parametro fecha hasta
        querySolver.add(new QueryFragment(fechaHasta != null, "", " o.fechaAlta <= :fechaHasta ", "fechaHasta", fechaHasta));

        //3. Agregando parametro almacen
        querySolver.add(new QueryFragment(idAlmacen != null, "", " o.almacen.id = :idAlmacen", "idAlmacen", idAlmacen));

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

        List<OrdenSalida> listOrdenesSalida = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listOrdenesSalida;
    }

    @Override
    public List<OrdenSalida> findByFechas(Date fechaInicio, Date fechaFin) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Creating query
        String query = "Select o From OrdenSalida o Where o.fechaAlta >= :fechaInicio and o.fechaAlta <= :fechaFin";

        //Creating query JPA
        return em.createQuery(query).setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin).getResultList();
    }
}
