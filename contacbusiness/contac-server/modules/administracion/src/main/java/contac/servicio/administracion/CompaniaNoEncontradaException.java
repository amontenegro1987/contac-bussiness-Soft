package contac.servicio.administracion;

/**
 * Indica que una compania buscada no existe
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-30-2010
 * Time: 11:02:02 PM
 */
public class CompaniaNoEncontradaException extends ManagerAdministracionServiceBusinessException{

    /**
     * Constructor con un identificador
     * @param id, codigo de compania buscado
     */
    public CompaniaNoEncontradaException(int id){
        super("No se encontro Compania con id " + id);
    }

    /**
     * Constructor con un criterio de busqueda
     * @param criterio, criterio de busqueda
     */
    public CompaniaNoEncontradaException(String criterio){
        super("No se encontro Compania con criterio " + criterio);
    }


}
