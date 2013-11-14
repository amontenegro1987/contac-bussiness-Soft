package contac.servicio.autenticacion;

/**
 * Excepcion del servicio de autenticacion.  Es lanzada cuando no fue posible
 * autenticar exitosamente a un usuario que realiza una peticion de autenticacion
 * en el sistema.
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:08:23 PM
 */
public class ContacAutenticacionServiceException extends Exception{

    /**
     * Exception con nombre de usuario
     * @param username, nombre de usuario
     */
    public ContacAutenticacionServiceException(String username){
        super("Usuario : " + username + " no es un usuario valido del sistema.");
    }

    /**
     * Exception con nombre de usuario y causa
     * @param message, message
     * @param cause, causa del error
     */
    public ContacAutenticacionServiceException(String message, Throwable cause){
        super(message, cause);
    }

}
