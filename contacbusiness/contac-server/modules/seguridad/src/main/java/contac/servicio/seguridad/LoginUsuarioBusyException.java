package contac.servicio.seguridad;

/**
 * Exception lanzada cuando un login de usuario ya se encuentra ocupado en el sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-14-2010
 * Time: 08:38:05 PM
 */
public class LoginUsuarioBusyException extends ManagerSeguridadServiceBusinessException{

    /**
     * Constructor para un parametro login
     * @param login, nombre de usuario en el sistema
     */
    public LoginUsuarioBusyException(String login) {
        super("Login: " + login + " es un usuario registrado en el sistema. Favor intente con otro login.");
    }

    /**
     * Constructor para un login y causa
     * @param login, nombre de usuario en el sistema
     * @param cause, causa del error
     */
    public LoginUsuarioBusyException(String login, Throwable cause) {
        super("Login: " + login + " es un usuario registrado en el sistema. Favor intente con otro login.", cause);
    }
}
