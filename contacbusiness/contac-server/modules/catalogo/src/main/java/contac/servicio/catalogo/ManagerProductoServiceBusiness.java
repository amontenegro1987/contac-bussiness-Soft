/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.catalogo;

import contac.modelo.entity.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Manager producto Servicio
 * User: Eddy Montenegro
 * Date: 7/29/11
 * Time: 9:45 AM
 */
public interface ManagerProductoServiceBusiness extends ManagerProductoServiceBusinessRemote {

    /**
     * Buscar producto por codigo
     *
     * @param codigo, Codigo de producto
     * @return Producto
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Producto buscarProductoPorCodigo(String codigo) throws ManagerProductoServiceBusinessException, RemoteException;

    /**
     * Buscar producto por parametros
     *
     * @param codigo,           Codigo de producto
     * @param nombre,           Nombre de producto
     * @param codigoFabricante, Codigo fabricante del producto
     * @return List<Producto></Producto>
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Producto> buscarProducto(String codigo, String nombre, String codigoFabricante) throws ManagerProductoServiceBusinessException,
            RemoteException;

    /**
     * Buscar producto por parametros
     *
     * @param codigoDesde,  Codigo desde
     * @param codigoHasta,  Codigo hasta
     * @param idLinea,      Linea de Productos
     * @param codProveedor, Codigo de Proveedor
     * @param idAlmacen,    Codigo de Almacen
     * @param existencia,   Requiere Existencias
     * @return List
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Producto> buscarProducto(String codigoDesde, String codigoHasta, Integer idLinea, Long codProveedor,
                                         Integer idAlmacen, boolean existencia) throws ManagerProductoServiceBusinessException,
            RemoteException;

    /**
     * Buscar existencias del producto
     *
     * @param codigo,    long
     * @param idAlmacen, Identificador de almacen
     * @return ProductoExistencia
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public ProductoExistencia buscarProductoExistenciaPorAlmacen(String codigo, Integer idAlmacen) throws ManagerProductoServiceBusinessException,
            RemoteException;

    /**
     * Buscar producto existencia
     *
     * @param codigo, Codigo del producto
     * @return List<ProductoExistencia>
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<ProductoExistencia> buscarProductoExistencias(String codigo) throws ManagerProductoServiceBusinessException,
            RemoteException;

    /**
     * Verifica si un producto se encuentra registrado
     *
     * @param codigo, String
     * @return boolean
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public boolean isProductoParaRegistro(String codigo) throws RemoteException;

    /**
     * Registra nuevo producto
     *
     * @param codigo,           Codigo de producto
     * @param compuesto,        Producto compuesto
     * @param nombre,           nombre producto
     * @param codigoFabricante, Codigo fabricante
     * @param alias,            Alias de producto
     * @param marca,            Marca registrada
     * @param modelo,           Modelo registrado
     * @param observaciones,    Observaciones
     * @param minimo,           Minimo
     * @param maximo,           Maximo
     * @param costoUND,         Costo Unitario Inventario
     * @param costoCIF,         Costo Unitario con transporte y seguro
     * @param costoFOB,         Costo en factura proveedor
     * @param descuento,        Tasa de descuento precio
     * @param exento,           Exento pagar impuesto
     * @param precioEstandar,   Precio estandar
     * @param unidadMedida,     Unidad de medida
     * @param proveedor,        Proveedor
     * @param clasificador,     Clasificador CBS
     * @param linea,            Linea de producto
     * @param fotografia,       Fotografia de producto
     * @param productos,        Listado de productos compuestos
     * @return Producto
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */

    public Producto crearProducto(String codigo, boolean compuesto, String nombre, String codigoFabricante, String alias,
                                  String marca, String modelo, String observaciones, long minimo, long maximo, BigDecimal costoUND,
                                  BigDecimal costoCIF, BigDecimal costoFOB, BigDecimal descuento, boolean exento, BigDecimal precioEstandar,
                                  UnidadMedida unidadMedida, Proveedor proveedor, Clasificador clasificador, Linea linea, Pais paisOrigen,
                                  byte[] fotografia, Set<ProductoCompuesto> productos) throws ManagerProductoServiceBusinessException, RemoteException;

    /**
     * Modificar producto
     *
     * @param id,               Clave de producto
     * @param codigo,           Codigo de producto
     * @param compuesto,        Producto compuesto
     * @param nombre,           nombre producto
     * @param codigoFabricante, Codigo fabricante
     * @param alias,            Alias de producto
     * @param marca,            Marca registrada
     * @param modelo,           Modelo registrado
     * @param observaciones,    Observaciones
     * @param minimo,           Minimo
     * @param maximo,           Maximo
     * @param costoUND,         Costo Unitario Inventario
     * @param costoCIF,         Costo Unitario con transporte y seguro
     * @param costoFOB,         Costo en factura proveedor
     * @param costoPROM,        Costo Promedio Inventario
     * @param descuento,        Tasa de descuento precio
     * @param exento,           Exento pagar impuesto
     * @param precioEstandar,   Precio estandar
     * @param unidadMedida,     Unidad de medida
     * @param proveedor,        Proveedor
     * @param clasificador,     Clasificador CBS
     * @param linea,            Linea de producto
     * @param fotografia,       Fotografia de producto
     * @param productos,        Listado de productos compuestos
     * @return Producto
     * @throws ManagerProductoServiceBusinessException
     *
     * @throws RemoteException
     */
    public Producto modificarProducto(Integer id, String codigo, boolean compuesto, String nombre, String codigoFabricante,
                                      String alias, String marca, String modelo, String observaciones, long minimo,
                                      long maximo, BigDecimal costoUND, BigDecimal costoCIF, BigDecimal costoFOB,
                                      BigDecimal costoPROM, BigDecimal descuento, boolean exento, BigDecimal precioEstandar,
                                      UnidadMedida unidadMedida, Proveedor proveedor, Clasificador clasificador, Linea linea, Pais paisOrigen,
                                      byte[] fotografia, Set<ProductoCompuesto> productos) throws ManagerProductoServiceBusinessException, RemoteException;

    /**
     * Recodificar producto
     *
     * @param codigoActual, Codigo producto actual
     * @param codigoNuevo,  Codigo producto nuevo
     * @return Producto
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Producto recodificarProducto(String codigoActual, String codigoNuevo) throws ManagerProductoServiceBusinessException,
            RemoteException;

    /**
     * Eliminar Producto verificando si no tiene movimientos de inventario
     *
     * @param idProducto, Codigo Identificador de Producto
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void eliminarProducto(Integer idProducto) throws ManagerProductoServiceBusinessException, RemoteException;

    /**
     * Anular Lista de Productos seleccionados
     *
     * @param productos, List<Integer>
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void anularProductos(List<Integer> productos) throws ManagerProductoServiceBusinessException, RemoteException;

}
