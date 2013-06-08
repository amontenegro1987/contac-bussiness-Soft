/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.ordenLevantamientoFisicoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.OrdenLevantamientoFisico;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emortiz
 * Date: 01-06-12
 * Time: 03:40 PM
 */
public class OrdenLevantamientoFisicoEAOPersistence extends GenericPersistenceEAO<OrdenLevantamientoFisico, Integer> implements OrdenLevantamientoFisicoEAO {

    @Override
    public long obtenerNoLevantamiento() throws GenericPersistenceEAOException {
        //Init service
        initService();

        String query = "Select MAX(o.noMovimiento) From OrdenLevantamientoFisico o";

        //Obtener numero maximo de movimiento
        Number noMovimiento = (Number) em.createQuery(query).getSingleResult();

        //El primer numero del levantamiento inventario siempre inicia con 41
        if (noMovimiento == null)
            return 41;

        //Construyendo numero compuesto como cadena
        StringBuilder sb = new StringBuilder(String.valueOf(noMovimiento.longValue()));

        //Obteniendo subcadena del numero sin el primer indicador mas uno para registro siguiente
        long codigo = Long.parseLong(sb.substring(1)) + 1;
        String noLevantamiento = 4 + String.valueOf(codigo);

        return Long.parseLong(noLevantamiento);
    }

    @Override
    public List<OrdenLevantamientoFisico> findByEstados(List<Integer> estados) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Creating query
        String fromClause = "Select o from OrdenLevantamientoFisico o ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();

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

        List<OrdenLevantamientoFisico> listOrdenesLevantamiento = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listOrdenesLevantamiento;
    }

    @Override
    public void deleteDetalleLevantamientoFisico(Integer idLevantamientoFisico) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Query
        String query = "delete from ArticuloLevantamientoFisico a where a.ordenLevantamientoFisico.id = :idLevantamientoFisico";

        //Execute query
        em.createQuery(query).setParameter("idLevantamientoFisico", idLevantamientoFisico).executeUpdate();
    }

}
