package contac.servicio.seguridad;

import contac.autenticacion.principals.ContacPrincipal;
import contac.autenticacion.principals.RolPrincipal;
import contac.modelo.entity.EntityAuditInformationManager;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Set;

/**
 * Manager para servicio de autorizacion del sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 12:37:42 AM
 */
public class ManagerAutorizacionServiceBusinessImpl extends UnicastRemoteObject implements ManagerAutorizacionServiceBusiness {

    /**
     * Servicio de log4j
     */
    private static final Logger logger = Logger.getLogger(ManagerAutorizacionServiceBusinessImpl.class);

    /**************************************
     * Usuario autenticado
     */
    Subject userAuth;


    /**
     * Constructor default
     * @throws RemoteException, Exception
     */
    public ManagerAutorizacionServiceBusinessImpl() throws RemoteException {}

    /**
     * Constructor de Manager Autorizacion con un usuario autenticado
     * @param userAutenticado, Subject
     * @throws RemoteException, Exception
     */
    public ManagerAutorizacionServiceBusinessImpl(Subject userAutenticado) throws RemoteException {
        this.userAuth = userAutenticado;
    }

    @Override
    public void checkUserPermission(Method method) throws ManagerAutorizacionServiceBusinessException, RemoteException {
        //TODO: Implementar metodo
        throw new UnsupportedOperationException();
    }

    @Override
    public void checkPermission(Method method) throws ManagerAutorizacionServiceBusinessException, RemoteException {
        //TODO: Implementar metodo
        throw new UnsupportedOperationException();
    }

    @Override
    public void isUserInRole(String rolname) throws ManagerAutorizacionServiceBusinessException, RemoteException {

        boolean success = true;

        //RolPrincipal asociado
        RolPrincipal rolPrincipal = null;
        ContacPrincipal contacPrincipal = null;

        //Evaluar si el usuario tiene ContacPrincipal asociado
       Set<ContacPrincipal> contacPrincipals = userAuth.getPrincipals(ContacPrincipal.class);

        for (Iterator it = contacPrincipals.iterator(); it.hasNext();){

            ContacPrincipal principal = (ContacPrincipal)it.next();

            if (principal instanceof ContacPrincipal){

                if (principal.getName().equals(rolname)){
                    contacPrincipal = principal;
                    break;
                }
            }
        }

        //Evaluar si el usuario tiene RolPrincipal asociado
        Set<RolPrincipal> rolPrincipals = userAuth.getPrincipals(RolPrincipal.class);

        for (Iterator it = rolPrincipals.iterator(); it.hasNext();){

            RolPrincipal principal = (RolPrincipal)it.next();

            if (principal instanceof RolPrincipal){

                if (principal.getName().equals(rolname)){
                    rolPrincipal = principal;
                    break;
                }
            }
        }

        if (contacPrincipal == null && rolPrincipal == null)
                logger.info("Principal no encontrado");

        //Evaluar rol success
        if (contacPrincipal == null && rolPrincipal == null)
            throw new ManagerAutorizacionServiceBusinessException("Usted no tiene los permisos apropiados para ejecutar esta accion.");


        //Asignar usuario autenticado al Entity Audit Information Manager
        EntityAuditInformationManager.setCallerPrincipal(userAuth);
    }

    @Override
    public void isUserInRole(String rolname, Subject subject) throws ManagerAutorizacionServiceBusinessException,
            RemoteException {
        //Setting user authenticated
        this.userAuth = subject;
        //Call method name for authentication
        this.isUserInRole(rolname);
    }

    @Override
    public boolean checkUserInRole(String rolname) throws ManagerAutorizacionServiceBusinessException, RemoteException {

        boolean success = true;

        //RolPrincipal asociado
        RolPrincipal rolPrincipal = null;
        ContacPrincipal contacPrincipal = null;

        //Evaluar si el usuario tiene ContacPrincipal asociado
        Set<ContacPrincipal> contacPrincipals = userAuth.getPrincipals(ContacPrincipal.class);

        for (Iterator it = contacPrincipals.iterator(); it.hasNext();){

            ContacPrincipal principal = (ContacPrincipal)it.next();

            if (principal instanceof ContacPrincipal){

                if (principal.getName().equals(rolname)){
                    contacPrincipal = principal;
                    break;
                }
            }
        }

        //Evaluar si el usuario tiene RolPrincipal asociado
        Set<RolPrincipal> rolPrincipals = userAuth.getPrincipals(RolPrincipal.class);

        for (Iterator it = rolPrincipals.iterator(); it.hasNext();){

            RolPrincipal principal = (RolPrincipal)it.next();

            if (principal instanceof RolPrincipal){

                if (principal.getName().equals(rolname)){
                    rolPrincipal = principal;
                    break;
                }
            }
        }

        if (contacPrincipal == null && rolPrincipal == null) {
            success = false;
        }

        return success;
    }

    @Override
    public String getUsername() throws ManagerAutorizacionServiceBusinessException, RemoteException {
        return EntityAuditInformationManager.getCallerPrincipal();
    }
}
