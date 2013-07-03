package contac.autenticacion.principals;

import java.security.Principal;

/**
 * Servicio de autenticacion propio de la aplicacion; implementado para el logueo
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-11-2010
 * Time: 11:42:28 PM
 */
public class ContacPrincipal implements Principal{

    String name;

    /**
     * Constructor con el nombre del principal
     * @param name, nombre del principal
     */
    public ContacPrincipal(String name){
        this.name = name;
    }
        
    @Override
    public String getName() {
        return name;
    }
}
