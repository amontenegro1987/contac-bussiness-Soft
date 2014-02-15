package contac.modelo.eao.ordenEntradaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenEntrada;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-02-11
 * Time: 09:34 PM
 */
public class OrdenEntradaEAOPersistence extends GenericPersistenceEAO<OrdenEntrada, Integer> implements OrdenEntradaEAO {

    @Override
    public long obtenerNoEntrada() throws GenericPersistenceEAOException {

        //Init service
        initService();

        String query = "Select MAX(o.noMovimiento) From OrdenEntrada o";

        //Obtener numero maximo de movimiento
        Number noMovimiento = (Number)em.createQuery(query).getSingleResult();

        //El primer numero de la entrada siempre inicia con 11
        if (noMovimiento == null)
            return 11;

        //Construyendo numero compuesto como cadena
        StringBuilder sb = new StringBuilder(String.valueOf(noMovimiento.longValue()));

        //Obteniendo subcadena del numero sin el primer indicador mas uno para registro siguiente
        long codigo = Long.parseLong(sb.substring(1)) + 1;
        String noEntrada = 1 + String.valueOf(codigo);

        return Long.parseLong(noEntrada);
    }

    @Override
    public List<OrdenEntrada> findByTipoEntrada(int tipoEntrada) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Query JPA String
        String query = "Select o From OrdenEntrada o Where o.tipoEntrada = :tipoEntrada Order by o.fechaAlta Desc";

        //Creating query JPA
        return em.createQuery(query).setParameter("tipoEntrada", tipoEntrada).getResultList();
    }

    @Override
    public List<OrdenEntrada> findByEstadosAlmacen(List<Integer> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException {
        //Init service
        initService();

        //Creating query
        String fromClause = "Select o from OrdenEntrada o ";
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

        List<OrdenEntrada> listOrdenesEntrada = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listOrdenesEntrada;
    }


    @Override
    public List<OrdenEntrada> findByEstados(List<Integer> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws GenericPersistenceEAOException {
         System.out.println("Fecha Desde: " + fechaDesde + " Fecha Hasta: " + fechaHasta + " Id Almacen: " + idAlmacen);
        //Init service
        initService();

        //Creating query
        String fromClause = "Select o from OrdenEntrada o ";
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

        List<OrdenEntrada> listOrdenesEntrada = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listOrdenesEntrada;
    }

    @Override
    public List<OrdenEntrada> findByFechas(Date fechaInicio, Date fechaFin) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Creating query
        String query = "Select o From OrdenEntrada o Where o.fechaAlta >= :fechaInicio and o.fechaAlta <= :fechaFin";

        //Creating query JPA
        return em.createQuery(query).setParameter("fechaInicio", fechaInicio).setParameter("fechaFin", fechaFin).getResultList();
    }
}
