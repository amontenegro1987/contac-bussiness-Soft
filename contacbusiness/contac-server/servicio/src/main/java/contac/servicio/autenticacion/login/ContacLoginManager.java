package contac.servicio.autenticacion.login;

import contac.servicio.implementacion.ManagerServiceFactory;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz de acceso al servicio de autenticacion del sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-15-2010
 * Time: 12:36:11 AM
 */
public interface ContacLoginManager extends Remote {

    /**
     * Este metodo autentica un usuario en el servicio de autenticacion del sistema
     * @param username, nombre del usuario logueado en el sistema
     * @param password, clave de acceso del usuario logueado en el sistema
     * @return ManagerServiceFactory
     * @throws RemoteException, Exception
     * @throws SecurityException, Exception
     */
    public ManagerServiceFactory login(String username, String password) throws RemoteException, SecurityException;
}
