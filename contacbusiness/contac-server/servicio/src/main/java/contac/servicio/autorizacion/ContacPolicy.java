package contac.servicio.autorizacion;

import contac.autenticacion.principals.RolPrincipal;
import org.apache.log4j.Logger;

import java.security.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:13:06 PM
 */
public class ContacPolicy extends Policy {

    private static Logger logger = Logger.getLogger(ContacPolicy.class.getName());

    public PermissionCollection getPermissions(CodeSource codesource) {
        Permissions perms = new Permissions();
        perms.add(new AllPermission());
        return perms;
    }

    public PermissionCollection getPermissions(final ProtectionDomain domain) {
        final Permissions permissions = new Permissions();

        //Look up for permissions
        Principal[] principals = domain.getPrincipals();

        final Set principalsId = new HashSet();

        if (principals != null && principals.length > 0) {
            for (int i = 0; i < principals.length; i++) {
                Principal p = principals[i];

                if (p instanceof RolPrincipal) {
                    RolPrincipal rolPrincipal = (RolPrincipal) p;
                    principalsId.add(rolPrincipal.getId());
                }
            }

            if (!principalsId.isEmpty()) {
                try {
                    List perms = (List) AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() {
                            return ContacAutorizacionService.findPermissions(principalsId);
                        }
                    });

                    for (Iterator it = perms.iterator(); it.hasNext();) {
                        Permission perm = (Permission) it.next();
                        permissions.add(perm);
                    }
                } catch (PrivilegedActionException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return permissions;
    }

    public boolean implies(final ProtectionDomain domain, final Permission permission) {
        PermissionCollection perms = getPermissions(domain);

        boolean implies = perms.implies(permission);

        return implies;
    }
}
