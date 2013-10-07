package contac.modelo;

/**
 * Persistence Management Service exception al servicio de EntityManagerFactory
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-08-2010
 * Time: 01:46:33 AM
 */
public class PersistenceManagementServiceFactoryException extends Exception {

    /**
     * Mensaje de error con una notificacion de error
     * @param message, mensaje de error
     */
    public PersistenceManagementServiceFactoryException(String message) {
        super("Error intentando crear el servicio de persistencia: " + message);
    }

    /**
     * Mensaje de error con una notificacion y una causa
     * @param message, mensaje de error
     * @param cause, causa del error
     */
    public PersistenceManagementServiceFactoryException(String message, Throwable cause) {
        super("Error intentando crear el servicio de persistencia: " + message, cause);
    }

    /**
     * Mensaje de error con una causa
     * @param cause, causa del error
     */
    public PersistenceManagementServiceFactoryException(Throwable cause) {
        super(cause);
    }
}
