package contac.autenticacion.principals;

import java.security.Principal;

/**
 * Representa una identificaci�n del usuario dentro del sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:06:01 PM
 */
public class CallerPrincipal implements Principal {

    private String username; //Nombre de usuario

    /**
     * Constructor
     * @param username, nombre de usuario
     */
    public CallerPrincipal(String username){
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
