package contac.modelo.eao.genericEAO;

import java.io.Serializable;
import java.util.List;

/**
 * Super interface for GENERIC CRUD funcionality
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-04-2010
 * Time: 11:49:28 PM
 */
public interface GenericEAO<T, ID extends Serializable> {

    /**
     * Create an entity
     * @param object, Generic object
     * @return T
     */
    T create(T object) throws GenericPersistenceEAOException;

    /**
     * Update an entity
     * @param object, Generic object
     * @return T
     */
    T update(T object) throws GenericPersistenceEAOException;

    /**
     * find an entity by his identifier
     * @param id, identifier
     * @return T
     */
    T findById(ID id) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Find all entities persistence
     * @return List<T>
     */
    List<T> findAll() throws GenericPersistenceEAOException;

    /**
     * Remove entity
     * @param id, identifier
     */
    void remove(ID id) throws GenericPersistenceEAOException;
    
}
