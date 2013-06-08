package contac.servicio.seguridad;

/**
 * Indica que un Usuario no existe
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 12:19:09 AM
 */
public class UsuarioNoEncontradoException extends ManagerSeguridadServiceBusinessException {

    /**
     * Usuario con identificador no encontrado
     * @param id, identificador
     */
    public UsuarioNoEncontradoException(int id) {
        super("No se encontro un usuario con id " + id);
    }

    /**
     * Usuario con criterio no encontrado
     * @param criterio, critero de busqueda
     */
    public UsuarioNoEncontradoException(String criterio) {
        super("No se encontro un usuario con el criterio " + criterio);
    }
}
