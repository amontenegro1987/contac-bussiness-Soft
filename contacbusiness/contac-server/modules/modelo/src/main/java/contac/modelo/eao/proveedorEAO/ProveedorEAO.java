package contac.modelo.eao.proveedorEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Proveedor;

import java.util.List;

/**
 * Interfaz de acceso a datos para manejar Proveedor Entity
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-26-2010
 * Time: 10:10:38 PM
 */
public interface ProveedorEAO extends GenericEAO<Proveedor, Integer> {

    /**
     * Buscar proveedores ordenados por codigo
     *
     * @return List<Proveedor>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Proveedor> findAllOrderByCodigo() throws GenericPersistenceEAOException;

    /**
     * Buscar proveedores por parametros
     *
     * @param codigo,      Codigo de proveedor
     * @param razonSocial, Razon Social del proveedor
     * @return List
     * @throws PersistenceClassNotFoundException,
     *          Exception
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Proveedor> find(long codigo, String razonSocial) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Busca proveedor por codigo
     *
     * @param codigo, Codigo
     * @return Proveedor
     * @throws PersistenceClassNotFoundException,
     *          Exception
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public Proveedor findByCodigo(long codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;
}
