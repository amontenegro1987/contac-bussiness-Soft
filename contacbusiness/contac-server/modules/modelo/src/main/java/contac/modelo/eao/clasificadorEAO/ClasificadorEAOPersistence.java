package contac.modelo.eao.clasificadorEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Clasificador;
import contac.modelo.entity.Clasificador_;
import contac.modelo.entity.Constantes;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;
import org.apache.log4j.Logger;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-09-2010
 * Time: 10:47:09 PM
 */
public class ClasificadorEAOPersistence extends GenericPersistenceEAO<Clasificador, Integer> implements ClasificadorEAO {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ClasificadorEAOPersistence.class);


    @Override
    public Clasificador buscarPorCodigoCbs(long cbs) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Creating criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Clasificador> cq = cb.createQuery(Clasificador.class);
        Root<Clasificador> clasificador = cq.from(Clasificador.class);
        cq.select(clasificador).where(cb.equal(clasificador.get(Clasificador_.cbs), cbs));

        Clasificador entity = em.createQuery(cq).getSingleResult();

        return entity;
    }

    @Override
    public List<Clasificador> buscarPorCodigoCbsDescripcion(long cbs, String descripcion) throws GenericPersistenceEAOException {
        
        //Iniciar servicio
        initService();
        
        String fromClause = "Select c From Clasificador c ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro de codigo cbs
        querySolver.add(new QueryFragment((cbs > 0), "", " c.cbs = :cbs ",
                "cbs", cbs));
        //2. Agregando parametro de descripcion
        querySolver.add(new QueryFragment(((descripcion != null) && (!descripcion.equals(""))), "", " c.descripcion like :descripcion ",
                "descripcion", "%".concat(descripcion).concat("%")));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<Clasificador> listClasificador = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listClasificador;
    }

    @Override
    public List<Clasificador> buscarPorClasificacion(long clasificacion) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        String qryStr = "select c from Clasificador c where ";

        if (clasificacion != Constantes.INT_NO_DEFINE)
            qryStr += "c.clasificacion = ?1";

        logger.info("Query: " + qryStr);

        //Creando listado de parametros
        List<Long> parametros = new ArrayList<Long>();
        parametros.add(clasificacion);

        return (List<Clasificador>) findByCriteria(qryStr, parametros);
    }

    @Override
    public List<Clasificador> buscarPorTipoClasificador(int tipoClasificador) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Creating criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Clasificador> cq = cb.createQuery(Clasificador.class);
        Root<Clasificador> clasificador = cq.from(Clasificador.class);
        cq.select(clasificador).where(cb.equal(clasificador.get(Clasificador_.tipoClasificador), tipoClasificador));

        return em.createQuery(cq).getResultList();

    }
}
