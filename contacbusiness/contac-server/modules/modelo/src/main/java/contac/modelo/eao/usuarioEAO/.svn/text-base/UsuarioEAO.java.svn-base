package contac.modelo.eao.usuarioEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Usuario;

/**
 * Interfaz de acceso a datos para manejar Usuario Entity
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-08-2010
 * Time: 01:56:13 AM
 */
public interface UsuarioEAO extends GenericEAO<Usuario, Integer> {

    /**
     * Busca usuario por username y password
     * @param login, nombre de usuario en el sistema
     * @return Usuario
     * @throws PersistenceClassNotFoundException, Exception
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public Usuario findByLogin(String login) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

}
