package contac.modelo.entity;

import org.apache.log4j.Logger;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Servicio de auditoria para almacenar fechas de transacciones.
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-31-2008
 * Time: 11:38:47 PM
 */
public class Audit {

    /**
     * Logger
     */
    public static final Logger logger = Logger.getLogger(Audit.class);

    /**
     * Servicio implementado para guardar fechas de registros
     * @param entity, Object
     */
    @PrePersist
    public void createAuditInformation(Object entity) {
        try {
            java.util.Date cTime = new java.util.Date();
            String cUser = getCurrentPrincipal();

            Method setCtime = entity.getClass().getMethod("setCtime", java.util.Date.class);
            Method setMtime = entity.getClass().getMethod("setMtime", java.util.Date.class);
            setCtime.invoke(entity, cTime);
            setMtime.invoke(entity, cTime);

            Method setCuser = entity.getClass().getMethod("setCuser", java.lang.String.class);
            Method setMuser = entity.getClass().getMethod("setMuser", java.lang.String.class);
            setCuser.invoke(entity, cUser);
            setMuser.invoke(entity, cUser);

        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Servicio implementado para actualizar fechas de registros
     * @param entity, Object
     */
    @PreUpdate
    public void updateAuditInformation(Object entity) {
        try {
            java.util.Date mTime = new java.util.Date();
            String mUser = getCurrentPrincipal();

            Method setMtime = entity.getClass().getMethod("setMtime", java.util.Date.class);
            setMtime.invoke(entity, mTime);

            Method setMuser = entity.getClass().getMethod("setMuser", java.lang.String.class);
            setMuser.invoke(entity, mUser);

        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Obtiene al llamador principal.
     * @return String
     */
    private static String getCurrentPrincipal() {

        //Obtener caller principal
        String callerPrincipal = EntityAuditInformationManager.getCallerPrincipal();

        return callerPrincipal;
    }


}
