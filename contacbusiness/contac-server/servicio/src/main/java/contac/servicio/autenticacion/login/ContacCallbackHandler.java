package contac.servicio.autenticacion.login;

import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * Servicio de manejo para la autenticacion de usuario en el sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:02:55 PM
 */
public class ContacCallbackHandler implements CallbackHandler{

    private String username; //nombre de usuario
    private String password; //contrasenia de usuario

    /**
     * Constructor del Callback
     * @param username, nombre de usuario
     * @param password, contrasenia de usuario
     */
    public ContacCallbackHandler(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++){

            Callback callback = callbacks[i];

            if (callback instanceof NameCallback){
                NameCallback nameCB = (NameCallback)callback;
                nameCB.setName(username);
            }else if (callback instanceof PasswordCallback){
                PasswordCallback passwordCB = (PasswordCallback)callback;
                passwordCB.setPassword(password.toCharArray());
            }
        }
    }
}
