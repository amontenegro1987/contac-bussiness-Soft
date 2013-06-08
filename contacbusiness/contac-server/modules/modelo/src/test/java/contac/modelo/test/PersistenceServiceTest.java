package contac.modelo.test;

import contac.logger.ContacApacheLog4j;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.rolEAO.RolEAO;
import contac.modelo.eao.rolEAO.RolEAOPersistence;
import contac.modelo.eao.usuarioEAO.UsuarioEAO;
import contac.modelo.eao.usuarioEAO.UsuarioEAOPersistence;
import contac.modelo.entity.Rol;
import contac.modelo.entity.Usuario;
import org.apache.log4j.Logger;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Testing para el servicio de persitencia
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-22-2010
 * Time: 08:59:43 PM
 */
@Test(groups = {"debug"})
public class PersistenceServiceTest {

    //Logger
    private static final Logger logger = Logger.getLogger(PersistenceServiceTest.class);

    //Entities access objects
    private RolEAO rolEAO;
    private UsuarioEAO usuarioEAO;

    @Test
    @Configuration(beforeTestClass = true)
    public void setUpClass() throws Throwable {

        //Configurar servicio de logger
        new ContacApacheLog4j();

        //Init EAO's Services
        rolEAO = new RolEAOPersistence();
        usuarioEAO = new UsuarioEAOPersistence();
    }

    @Test(enabled = false)
    public void buscarRoles() throws PersistenceManagementServiceFactoryException {

        try {

            logger.info("Iniciando test de DAO Layer.");

            List<Rol> roles = rolEAO.findAll();

            if (roles.isEmpty()) {
                logger.info("Listado de roles esta vacio");
            }

            for (Rol rol : roles) {

                logger.info("ROL ID: " + rol.getId());
                logger.info("ROL NAME: " + rol.getNombre());

            }

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new PersistenceManagementServiceFactoryException(e.getMessage(), e);
        }
    }

    @Test(enabled = false)
    public void buscarUsuario() throws PersistenceManagementServiceFactoryException {

        logger.info("Iniciando test busqueda usuario con parametros");

        try {

             logger.info("Iniciando test de USUARIO DAO Layer");

            Usuario usuario = usuarioEAO.findByLogin("SYSADMIN");

            logger.info("USERNAME: " + usuario.getUsername());
            logger.info("ESTADO: " + usuario.getEstado());

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new PersistenceManagementServiceFactoryException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new PersistenceManagementServiceFactoryException(e.getMessage(), e);
        }
    }
}
