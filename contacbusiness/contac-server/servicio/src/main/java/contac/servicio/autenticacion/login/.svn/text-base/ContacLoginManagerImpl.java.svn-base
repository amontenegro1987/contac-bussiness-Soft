package contac.servicio.autenticacion.login;

import contac.servicio.autenticacion.ContacAutenticacionService;
import contac.servicio.autenticacion.ContacAutenticacionServiceException;
import contac.servicio.implementacion.ManagerServiceFactory;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * Clase de implementacion al servicio de autenticacion del sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-15-2010
 * Time: 12:35:01 AM
 */
public class ContacLoginManagerImpl extends UnicastRemoteObject implements ContacLoginManager{

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ContacLoginManagerImpl.class);

    /**
     * Servicio de autenticacion
     */
    private ContacAutenticacionService contacAuthService;

    /**
     * Constructor
     * @throws RemoteException, Exception
     */
    public ContacLoginManagerImpl() throws RemoteException {
        contacAuthService =  new ContacAutenticacionService();
        //Inicializar modulos de logueo
        contacAuthService.initLoginModule();
    }


    @Override
    public ManagerServiceFactory login(String username, String password) throws RemoteException, SecurityException {

        try {

            ManagerServiceFactory mgrFactory = null;

            if (contacAuthService != null){
                contacAuthService.loginUser(username, password);
                mgrFactory = contacAuthService.createManagerServiceFactory();
            }

            return mgrFactory;

        } catch (ContacAutenticacionServiceException e) {
            logger.error(e.getMessage(), e);
            throw new SecurityException(e.getMessage(), e);
        }
    }
}
