package contac.modelo.eao.movimientoInventarioEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.MovimientoInventario;

import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 12-20-11
 * Time: 11:40 AM
 */
public interface MovimientoInventarioEAO extends GenericEAO<MovimientoInventario, Integer> {

    /**
     * Buscar Movimientos Inventario por su estado
     *
     * @param idEstado,   Identificador de estado de movimiento
     * @param idAlmacen,  Identificador de almacen de registro
     * @param fechaHasta, Fecha maxima de aplicacion del movimiento
     * @return List
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<MovimientoInventario> findByEstadoMovimiento(Integer idEstado, Integer idAlmacen, Date fechaHasta) throws GenericPersistenceEAOException;

    /**
     * Buscar Movimientos Inventario para un producto
     *
     * @param codigoProducto, Codigo del producto a buscar movimientos
     * @param idAlmacen,      Identificador de almacen
     * @param idEstado,       Identificador de estado de movimiento
     * @param fechaHasta,     Fecha maxima de aplicacion del movimiento
     * @return List
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<MovimientoInventario> findByProducto(String codigoProducto, Integer idAlmacen, Integer idEstado, Date fechaHasta)
            throws GenericPersistenceEAOException;

    /**
     * Buscar Movimientos Inventario para un producto por su tipo de afectacion
     *
     * @param codigoProducto, Codigo del producto a buscar movimientos
     * @param fechaDesde,     Fecha desde donde se inicia busqueda
     * @param fechaHasta,     Fecha hasta donde finaliza la busqueda
     * @param tipoAfectacion, Identificador del tipo de afectacion
     * @return List
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<MovimientoInventario> findByProducto(String codigoProducto, Date fechaDesde, Date fechaHasta, int tipoAfectacion)
            throws GenericPersistenceEAOException;

    /**
     * Buscar Movimientos Inventario para un producto y almacen para un rango de fechas
     *
     * @param codigoProducto, Codigo del producto a buscar movimientos
     * @param idAlmacen,      Identificador de almacen
     * @param fechaDesde,     Fecha inicio de busqueda de movimientos
     * @param fechaHasta,     Fecha hasta de busqueda de movimientos
     * @return List<MovimientoInventario>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<MovimientoInventario> findByProducto(String codigoProducto, Integer idAlmacen, Date fechaDesde, Date fechaHasta,
                                                     int tipoAfectacion) throws GenericPersistenceEAOException;
}
