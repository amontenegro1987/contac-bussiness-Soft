package contac.modelo.eao.rolEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Rol;

/**
 * Interfaz de acceso a datos para manejar Rol Entity
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-08-2010
 * Time: 01:59:31 AM
 *
 * @see contac.modelo.entity.Rol
 */
public interface RolEAO extends GenericEAO<Rol, Integer> {

    /**
     * Busca rol por nombre
     * @param nombre, nombre del rol
     * @return Rol
     * @throws PersistenceClassNotFoundException, Exception
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public Rol findByNombre(String nombre) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;



}
