package contac.autenticacion.principals;

import java.security.Principal;

/**
 * Representa una identificacion del usuario dentro del sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:05:54 PM
 */
public class RolPrincipal implements Principal {

    private Integer idRol; //Representa el identificador del rol
    private String nombreRol; //Representa el nombre del rol

    /**
     * Contruye la identificacion del RolPrincipal
     * @param idRol, identificador del rol
     * @param nombreRol, nombre del rol
     */
    public RolPrincipal(Integer idRol, String nombreRol){

        if (idRol == null){
            throw new NullPointerException("Identificador de ROL no puede ser nulo.");
        }

        if (nombreRol == null || nombreRol.length() == 0){
            throw new NullPointerException("Nombre de ROL no puede registrarse en blanco.");
        }

        //Setting valores
        this.idRol = idRol;
        this.nombreRol = nombreRol;        
    }

    public Integer getId(){
        return idRol;
    }
    
    @Override
    public String getName() {
        return nombreRol;
    }
}
