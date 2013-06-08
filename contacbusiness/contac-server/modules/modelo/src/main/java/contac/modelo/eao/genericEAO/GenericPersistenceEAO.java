package contac.modelo.eao.genericEAO;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * An implementation servicio persistence  CRUB
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-04-2010
 * Time: 11:59:45 PM
 */
public abstract class GenericPersistenceEAO<T, ID extends Serializable> implements GenericEAO<T, ID> {

    /**
     * Apache Log4j
     */
    private static final Logger logger = Logger.getLogger(GenericPersistenceEAO.class);

    /**
     * clase persistente
     */
    private Class<T> persistenceClass;

    /**
     * Manager de servicio de persistencia
     */
    protected EntityManager em;

    /**
     * Constructor
     */
    public GenericPersistenceEAO() {

        //Instanciando clase parametrizada
        this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Inicializar servicio de persistencia
     *
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    protected void initService() throws GenericPersistenceEAOException {

        try {

            //Obteniendo servicio de persistencia
            em = PersistenceManagementServiceFactory.getEntityManager();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new GenericPersistenceEAOException(e.getMessage(), e);
        }
    }


    @Override
    public T create(T object) throws GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        try {

            em.persist(object);

            return object;

        } catch (Exception e) {
            logger.error("Error insertando entidad: " + e.getMessage(), e);
            throw new GenericPersistenceEAOException("Error insertando entidad: " + e.getMessage(), e);
        }
    }

    @Override
    public T update(T object) throws GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        try {

            //Actualizar
            return em.merge(object);

        } catch (Exception e) {
            logger.error("Error actualizando entidad: " + e.getMessage(), e);
            throw new GenericPersistenceEAOException(e.getMessage(), e);
        }
    }

    @Override
    public T findById(ID id) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        //Find entity
        T entity = em.find(persistenceClass, id);

        if (entity == null)
            throw new PersistenceClassNotFoundException(String.valueOf(id));

        return entity;
    }

    @Override
    public List<T> findAll() throws GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        //Buscar todos
        String query = "Select t From " + persistenceClass.getName() + " t";

        List<T> entities = em.createQuery(query).getResultList();

        return entities;
    }

    @Override
    public void remove(ID id) throws GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        try {

            em.remove(findById(id));

        } catch (Exception e) {
            logger.error("Error eliminando entidad: " + e.getMessage(), e);
            throw new GenericPersistenceEAOException("Error eliminando entidad: " + e.getMessage(), e);
        }
    }

    /**
     * Find a List of entities with a query and criteria of searching
     *
     * @param namedQuery
     * @return List
     * @throws GenericPersistenceEAOException
     */
    protected List<T> namedQuery(String namedQuery) throws GenericPersistenceEAOException {
        return namedQuery(namedQuery, null);
    }

    /**
     * Find a List of entities with a namedQuery
     *
     * @param namedQuery, parameters
     * @param parameters, list of filters
     * @return List
     * @throws GenericPersistenceEAOException
     * @author hmurbina
     */

    protected List<T> namedQuery(String namedQuery, HashMap parameters) throws GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        em.getTransaction().begin();
        Query q = em.createNamedQuery(namedQuery);

        if (parameters != null) {
            Iterator entries = parameters.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry e = (Map.Entry) entries.next();
                q.setParameter(e.getKey().toString(), e.getValue());
            }
        }

        em.getTransaction().commit();
        return q.getResultList();
    }

    /**
     * Find a Collection<T> entities with a namedQuery
     *
     * @param query,      String
     * @param parameters, list of filters
     * @return Collection<T>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    protected Collection<T> findByCriteria(String query, List parameters) throws GenericPersistenceEAOException {

        //iniciar el servicio
        initService();

        //Create query fragment
        Query queryFragment = em.createQuery(query);

        //Adding parameters
        int parameter = 0;

        for (Object object : parameters) {

            //count parameter
            parameter++;

            logger.debug("parameter: " + parameter);

            //Setting parameter
            queryFragment.setParameter(parameter, object);
        }

        Collection<T> entities = queryFragment.getResultList();

        return entities;
    }
}
