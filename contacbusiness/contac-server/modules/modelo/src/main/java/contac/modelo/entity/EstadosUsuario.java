package contac.modelo.entity;

/**
 * Enum para los estados de un Usuario en el sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-14-2010
 * Time: 09:11:26 PM
 */
public enum EstadosUsuario {

    ACTIVO("ACTIVO"),
    DEBAJA("DEBAJA");

    public final String estado;

    EstadosUsuario(String estado){
        this.estado = estado;
    }

    public String getNombre(){
        return estado;
    }
}
