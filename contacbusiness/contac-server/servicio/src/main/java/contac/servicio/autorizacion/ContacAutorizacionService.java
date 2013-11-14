package contac.servicio.autorizacion;

import contac.modelo.entity.Rol;
import contac.servicio.autenticacion.ContacAutenticacionService;
import contac.servicio.implementacion.ManagerServiceFactory;
import contac.servicio.implementacion.ManagerServiceFactoryImpl;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessException;
import org.apache.log4j.Logger;

import javax.security.auth.kerberos.ServicePermission;
import java.rmi.RemoteException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-15-2010
 * Time: 07:10:48 AM
 */
public class ContacAutorizacionService {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ContacAutorizacionService.class);

    /**
     * Find All Permissions associated to a Rol (Principal)
     *
     * @param principalsId, Set principal's identifier
     * @return List<java.security.Permission>
     */
    static public List<Permission> findPermissions(Set<Integer> principalsId) {
        List<java.security.Permission> permissions = new ArrayList<Permission>();

        for (Integer id : principalsId) {
            permissions.add(findPermissions(id));
        }

        return permissions;
    }

    /**
     * Find a Permission for associated to a Rol (Principal)
     *
     * @param principalId, Rol
     * @return java.security.Permission
     */
    static public java.security.Permission findPermissions(Integer principalId) {

        logger.info("Iniciando el servicio de permisos con identificador: [principalId]: " + principalId);

        try {

            //Getting a ManagerServiceFactory
            ManagerServiceFactory mgrFactory = new ManagerServiceFactoryImpl(ContacAutenticacionService.getSubjectAutenticacionService());
            //Getting a ManagerSecurityService
            Rol rol = mgrFactory.getManagerSeguridadServiceBusiness().buscarRolPorId(principalId);

            //List all actions for permission
            String actions = "";                                  
            for (int i = 0; i < rol.getPermisos().size(); i++) {
                actions = rol.getPermisos().get(i).getNombre();
                int position = i - rol.getPermisos().size();
                if (position != -1)
                    actions.concat(", ");
            }

            //Create ServicePermission
            ServicePermission permission = null;
            if (!actions.equals("")) {
                permission = new ServicePermission(rol.getServicio().getNombre(), actions);
            }

            return permission;

        } catch (RemoteException e) {
            logger.error("Error trying to perform the operations for RolPermission", e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error("Error trying to perform the operations for RolPermission", e);
        }
        return null;
    }
}
