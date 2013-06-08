package contac.modelo.eao.usuarioEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.EstadosUsuario;
import contac.modelo.entity.Usuario;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-08-2010
 * Time: 01:55:07 AM
 */
public class UsuarioEAOPersistence extends GenericPersistenceEAO<Usuario, Integer> implements UsuarioEAO {

    @Override
    public Usuario findByLogin(String login) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        List<Usuario> usuarios = em.createQuery("Select u from Usuario u where u.username = :login").
                   setParameter("login", login).getResultList();

        if ((usuarios == null) || (usuarios.size() < 1))
            throw new PersistenceClassNotFoundException("login: " + login);

        if ((usuarios.size() > 1))
            throw new PersistenceClassNotFoundException("Se encontraron " + usuarios.size() + " con login: " + login);
                
        return usuarios.get(0);

    }
}
