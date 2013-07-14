package contac.servicio.proveedores;

/**
 * Contac Business Inc. 2011 all rights reserved.
 * User: emontenegro
 * Date: 8/19/11
 * Time: 10:06 PM
 */
public class ProveedorNoEncontradoException extends ManagerProveedoresServiceBusinessException {

    /**
     * Constructor error message with UID
     * @param id, Identifier
     */
    public ProveedorNoEncontradoException(int id) {
        super("No se encontro proveedor con id: " + id);
    }

    /**
     * Constructor error message with Criteria
     * @param criterio, Criteria
     */
    public ProveedorNoEncontradoException(String criterio) {
        super("No se encontro proveedor con criterio: " + criterio);
    }
}
