package contac.servicio.seguridad;

import javax.security.auth.Subject;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

/**
 * Manager Autenticacion Service Business .  Define todas las operaciones soportadas por este servicio
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 12:37:30 AM
 */
public interface ManagerAutorizacionServiceBusiness extends ManagerAutorizacionServiceBusinessRemote {

    /**
     * Validate user permission to access method
     *
     * @param method, Method Object
     * @throws ManagerAutorizacionServiceBusinessException,
     *          Exception
     */
    public void checkUserPermission(Method method) throws ManagerAutorizacionServiceBusinessException, RemoteException;

    /**
     * Validate a permission to do a task
     *
     * @param method, Method
     * @throws ManagerAutorizacionServiceBusinessException,
     *          Exception
     */
    public void checkPermission(Method method) throws ManagerAutorizacionServiceBusinessException, RemoteException;

    /**
     * Validate is a rol permission is propietary of a user Authenticated
     *
     * @param rolname, String
     * @throws ManagerAutorizacionServiceBusinessException,
     *          Exception
     */
    public void isUserInRole(String rolname) throws ManagerAutorizacionServiceBusinessException, RemoteException;

    /**
     * Validate is a rol permission is propietary of a user Authenticated
     *
     * @param rolname, String
     * @param subject, Subject
     * @throws ManagerAutorizacionServiceBusinessException,
     *          Exception
     */
    public void isUserInRole(String rolname, Subject subject) throws ManagerAutorizacionServiceBusinessException,
            RemoteException;

    /**
     * Check if authenticated user has the role pass to verify actions
     *
     * @param rolname, String
     * @return boolean
     * @throws ManagerAutorizacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public boolean checkUserInRole(String rolname) throws ManagerAutorizacionServiceBusinessException, RemoteException;

    /**
     * Obtener nombre de usuario del servicio de auditoria
     *
     * @return String
     * @throws ManagerAutorizacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public String getUsername() throws ManagerAutorizacionServiceBusinessException, RemoteException;
}
