package contac.modelo.eao.productoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Producto;

import java.util.List;

/**
 * Interfaz de acceso a datos para manejar Producto Entity
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-09-2010
 * Time: 11:21:51 PM
 */
public interface ProductoEAO extends GenericEAO<Producto, Integer> {

    /**
     * Busca producto con parametros
     *
     * @param codigo,           Codigo del producto
     * @param nombre,           Nombre del producto
     * @param codigoFabricante, Codigo del fabricante
     * @return List<Producto>
     * @throws PersistenceClassNotFoundException,
     *          Exception
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Producto> find(String codigo, String nombre, String codigoFabricante) throws PersistenceClassNotFoundException,
            GenericPersistenceEAOException;

    /**
     * Buscar productos con parametros
     *
     * @param codigoDesde,  Codigo Desde
     * @param codigoHasta,  Codigo Hasta
     * @param idLinea,      Linea de Producto
     * @param idAlmacen,    Codigo de Almacen
     * @param codProveedor, Codigo Proveedor
     * @return List
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<Producto> find(String codigoDesde, String codigoHasta, Integer idLinea, Integer idAlmacen,
                               Long codProveedor) throws GenericPersistenceEAOException;

    /**
     * Busca producto por su codigo
     *
     * @param codigo, codigo del producto
     * @return Producto
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException,
     *          Exception
     */
    public Producto findByCodigo(String codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Busca productos correspondientes a su clasificador
     *
     * @param idClasificador, identificador de clasificador
     * @return List<Producto>
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException,
     *          Exception
     */
    public List<Producto> findByClasificador(Integer idClasificador) throws GenericPersistenceEAOException;
}
