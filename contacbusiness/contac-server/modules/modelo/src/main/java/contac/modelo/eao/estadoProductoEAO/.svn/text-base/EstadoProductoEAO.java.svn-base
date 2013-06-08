package contac.modelo.eao.estadoProductoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.EstadoProducto;

/**
 * Contac Business Software Corp. All Right reserved
 * User: Eddy Montenegro
 * Date: 17/09/11
 * Time: 15:39
 */
public interface EstadoProductoEAO extends GenericEAO<EstadoProducto, Integer> {

    /**
     * Busca estado producto por nombre
     * @param nombre, Nombre del estado
     * @return EstadoProducto
     * @throws PersistenceClassNotFoundException, Exception
     * @throws GenericPersistenceEAOException, Exception
     */
    public EstadoProducto findByNombre(String nombre) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;
}
