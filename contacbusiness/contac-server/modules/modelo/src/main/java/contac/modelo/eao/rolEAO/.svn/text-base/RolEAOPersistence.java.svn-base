package contac.modelo.eao.rolEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Rol;

/**
 * Rol DAO implementacion base Java SE Adapter
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-08-2010
 * Time: 01:59:39 AM
 */
public class RolEAOPersistence extends GenericPersistenceEAO<Rol, Integer> implements RolEAO {

    @Override
    public Rol findByNombre(String nombre) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar el servicio
        initService();

        Rol rol = (Rol) em.createQuery("Select r from Rol r where r.nombre = :nombre").
                setParameter("nombre", nombre).getSingleResult();

        if (rol == null)
            throw new PersistenceClassNotFoundException(nombre);

        return rol;
    }
}
