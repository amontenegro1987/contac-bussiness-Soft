package contac.modelo.entity;

import contac.autenticacion.principals.CallerPrincipal;

import javax.security.auth.Subject;
import java.util.Iterator;
import java.util.Set;

/**
 * Almacena el servicio de caller llamador en la infraestructura de autenticacion
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-25-2010
 * Time: 10:20:57 PM
 */
public class EntityAuditInformationManager {

    /**
     * ThreadLocal implemented to get the user identity
     */
    public static final ThreadLocal<Subject> callerPrincipal = new ThreadLocal<Subject>();

    /**
     * Set the Caller user
     *
     * @param caller, Caller
     */
    public static void setCallerPrincipal(Subject caller) {
        callerPrincipal.set(caller);
    }

    /**
     * Get the Caller user
     *
     * @return CallerPrincipal
     */
    public static String getCallerPrincipal() {

        //Principal
        CallerPrincipal caller = null;

        Set<CallerPrincipal> principals = callerPrincipal.get().getPrincipals(CallerPrincipal.class);

        for (Iterator it = principals.iterator(); it.hasNext();) {
            CallerPrincipal callerIterator = (CallerPrincipal) it.next();
            if (callerIterator instanceof CallerPrincipal) {
                caller = callerIterator;
            }
        }

        if (caller != null)
            return caller.getName();

        return null;
    }
    
}
