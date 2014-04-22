/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.catalogo;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.almacenEAO.AlmacenEAO;
import contac.modelo.eao.almacenEAO.AlmacenEAOPersistence;
import contac.modelo.eao.articuloEntradaEAO.ArticuloEntradaEAO;
import contac.modelo.eao.articuloEntradaEAO.ArticuloEntradaEAOPersistence;
import contac.modelo.eao.articuloFacturaEAO.ArticuloFacturaEAO;
import contac.modelo.eao.articuloFacturaEAO.ArticuloFacturaEAOPersistence;
import contac.modelo.eao.articuloLevantamientoFisicoEAO.ArticuloLevantamientoFisicoEAO;
import contac.modelo.eao.articuloLevantamientoFisicoEAO.ArticuloLevantamientoFisicoEAOPersistence;
import contac.modelo.eao.articuloProformaEAO.ArticuloProformaEAO;
import contac.modelo.eao.articuloProformaEAO.ArticuloProformaEAOPersistence;
import contac.modelo.eao.articuloSalidaEAO.ArticuloSalidaEAO;
import contac.modelo.eao.articuloSalidaEAO.ArticuloSalidaEAOPersistence;
import contac.modelo.eao.articuloTrasladoEAO.ArticuloTrasladoEAO;
import contac.modelo.eao.articuloTrasladoEAO.ArticuloTrasladoEAOPersistence;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAO;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAOPersistence;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAO;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAOPersistence;
import contac.modelo.eao.estadoProductoEAO.EstadoProductoEAO;
import contac.modelo.eao.estadoProductoEAO.EstadoProductoEAOPersistence;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.lineaEAO.LineaEAO;
import contac.modelo.eao.lineaEAO.LineaEAOPersistence;
import contac.modelo.eao.movimientoInventarioEAO.MovimientoInventarioEAO;
import contac.modelo.eao.movimientoInventarioEAO.MovimientoInventarioEAOPersistence;
import contac.modelo.eao.productoEAO.ProductoEAO;
import contac.modelo.eao.productoEAO.ProductoEAOPersistence;
import contac.modelo.eao.productoModificacionEAO.ProductoModificacionEAO;
import contac.modelo.eao.productoModificacionEAO.ProductoModificacionEAOPersistence;
import contac.modelo.eao.proveedorEAO.ProveedorEAO;
import contac.modelo.eao.proveedorEAO.ProveedorEAOPersistence;
import contac.modelo.eao.unidadMedidaEAO.UnidadMedidaEAO;
import contac.modelo.eao.unidadMedidaEAO.UnidadMedidaEAOPersistence;
import contac.modelo.entity.*;
import contac.servicio.seguridad.*;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Manager producto servicio implementacion
 * User: Eddy Montenegro
 * Date: 7/29/11
 * Time: 9:46 AM
 */
public class ManagerProductoServiceBusinessImpl extends UnicastRemoteObject implements ManagerProductoServiceBusiness {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ManagerCatalogoServiceBusinessImpl.class);

    //Acceso capa EAO
    private ProductoEAO productoEAO;
    private ProductoModificacionEAO productoModificacionEAO;
    private ProveedorEAO proveedorEAO;
    private ClasificadorEAO clasificadorEAO;
    private LineaEAO lineaEAO;
    private UnidadMedidaEAO unidadMedidaEAO;
    private EstadoProductoEAO estadoProductoEAO;
    private MovimientoInventarioEAO movimientoInventarioEAO;
    private EstadoMovimientoEAO estadoMovimientoEAO;
    private AlmacenEAO almacenEAO;

    private ArticuloEntradaEAO articuloEntradaEAO;
    private ArticuloSalidaEAO articuloSalidaEAO;
    private ArticuloLevantamientoFisicoEAO articuloLevantamientoFisicoEAO;
    private ArticuloTrasladoEAO articuloTrasladoEAO;
    private ArticuloFacturaEAO articuloFacturaEAO;
    private ArticuloProformaEAO articuloProformaEAO;

    /**
     * Acceso al Manager Autorizacion Service
     */
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;
    protected ManagerSeguridadServiceBusiness mgrSeguridad;

    /**
     * Default Constructor
     *
     * @throws RemoteException, Exception
     */
    public ManagerProductoServiceBusinessImpl() throws RemoteException {

        logger.debug("Creando servicio de catalogo de producctos");

        //Inicializando accesos DAO
        productoEAO = new ProductoEAOPersistence();
        productoModificacionEAO = new ProductoModificacionEAOPersistence();
        proveedorEAO = new ProveedorEAOPersistence();
        clasificadorEAO = new ClasificadorEAOPersistence();
        lineaEAO = new LineaEAOPersistence();
        unidadMedidaEAO = new UnidadMedidaEAOPersistence();
        estadoProductoEAO = new EstadoProductoEAOPersistence();
        movimientoInventarioEAO = new MovimientoInventarioEAOPersistence();
        estadoMovimientoEAO = new EstadoMovimientoEAOPersistence();
        almacenEAO = new AlmacenEAOPersistence();

        articuloEntradaEAO = new ArticuloEntradaEAOPersistence();
        articuloSalidaEAO = new ArticuloSalidaEAOPersistence();
        articuloLevantamientoFisicoEAO = new ArticuloLevantamientoFisicoEAOPersistence();
        articuloTrasladoEAO = new ArticuloTrasladoEAOPersistence();
        articuloFacturaEAO = new ArticuloFacturaEAOPersistence();
        articuloProformaEAO = new ArticuloProformaEAOPersistence();
    }

    /**
     * Constructor de Manager Catalogo con un usuario autenticado
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws java.rmi.RemoteException, Exception
     */
    public ManagerProductoServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Llamar al constructor padre
        this();

        //Inicializar servicio de autorizacion
        this.mgrAutorizacion = mgrAutorizacion;
        mgrSeguridad = new ManagerSeguridadServiceBusinessImpl(this.mgrAutorizacion);
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerProductoServiceBusinessException {

        try {

            //Iniciar servicio de autorizacion
            if (mgrAutorizacion == null)
                logger.error("Servicio de autenticacion inactivo");

            //Check authentication
            mgrAutorizacion.isUserInRole(rolname);

            //Iniciar servicio transaccional
            return PersistenceManagementServiceFactory.beginTransaction();

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerProductoServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerProductoServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public Producto buscarProductoPorCodigo(String codigo) throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Buscando producto con parametros: [codigo]: " + codigo);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());

        try {

            //Buscando producto por codigo
            Producto entity = this.productoEAO.findByCodigo(codigo);

            //Asegurarse de recuperar la fotografia del producto por default LAZY INITIALIZATION
            Hibernate.initialize(entity.getFotografia());
            Hibernate.initialize(entity.getUnidadMedida());
            Hibernate.initialize(entity.getProveedor());
            Hibernate.initialize(entity.getClasificador());
            Hibernate.initialize(entity.getLinea());
            Hibernate.initialize(entity.getEstado());
            Hibernate.initialize(entity.getPaisOrigen());
            Hibernate.initialize(entity.getProductosCompuestos());

            return entity;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ProductoNoEncontradoException(e.getMessage());
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Producto> buscarProducto(String codigo, String nombre, String codigoFabricante) throws ManagerProductoServiceBusinessException,
            RemoteException {

        logger.debug("Buscando producto con parametros: [codigo]: " + codigo + ", [nombre]: " + nombre + ", [codigoFabricante]: " +
                codigoFabricante);

        //init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Buscando productos por parametros
            List<Producto> productos = this.productoEAO.find(codigo, nombre, codigoFabricante);

            //Initialize Unidad de Medida del listaoo de productos
            for (Producto producto : productos) {
                Hibernate.initialize(producto.getUnidadMedida());
            }

            return productos;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Producto> buscarExistenciasPorAlmacen(Integer idAlmacen) throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Buscar Existencias Almacen con parametros: [Almacen]: " + idAlmacen);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Find collection of productos
            List<Producto> productos = productoEAO.findByAlmacen(idAlmacen);

            return productos;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        }  finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Producto> buscarProducto(String codigoDesde, String codigoHasta, Integer idLinea, Long codProveedor,
                                         Integer idAlmacen, boolean existencia) throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Buscar productos con parametros: [codigoDesde]: " + codigoDesde + ", [codigoHasta]: " + codigoHasta +
            ", [Linea]: " + idLinea + ", [idProveedor]: " + codProveedor + ", [existencia]: " + existencia);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Find collection of productos
            List<Producto> productos = productoEAO.find(codigoDesde, codigoHasta, idLinea, idAlmacen, codProveedor);

            return productos;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        }  finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public ProductoExistencia buscarProductoExistenciaPorAlmacen(String codigo, Integer idAlmacen)
            throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Buscar existencia de producto con parametros: [codigo]: " + codigo + ", [idAlmacen]: " + idAlmacen);

        //init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Preparar el contexto
            Producto producto = productoEAO.findByCodigo(codigo);
            Almacen almacen = almacenEAO.findById(idAlmacen);
            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            EstadoMovimiento estadoAplicado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.APLICADO.getEstado());

            //Buscar movimientos de inventario
            List<MovimientoInventario> movimientosIngresados = movimientoInventarioEAO.findByProducto(codigo, idAlmacen, estadoIngresado.getId(), new Date());
            List<MovimientoInventario> movimientosAplicados = movimientoInventarioEAO.findByProducto(codigo, idAlmacen, estadoAplicado.getId(), new Date());

            //Load lazy datas
            Hibernate.initialize(producto.getExistencias());
            ProductoExistencia productoExistencia = null;

            for (ProductoExistencia existencia : producto.getExistencias()) {

                if (existencia.getId().getAlmacen().getId().compareTo(idAlmacen) == 0) {
                    productoExistencia = existencia;
                }
            }

            //*************************************************
            //Sumar cantidades ingresadas
            //*************************************************
            long cantIngreso = 0;
            long cantSalida = 0;

            for (MovimientoInventario movimientoInventario : movimientosIngresados) {
                if (movimientoInventario.getAfectacion() == TiposAfectacion.POSITIVA.getValue())
                    cantIngreso += movimientoInventario.getAfectacion() * movimientoInventario.getCantidad();
                if (movimientoInventario.getAfectacion() == TiposAfectacion.NEGATIVA.getValue())
                    cantSalida += movimientoInventario.getAfectacion() * movimientoInventario.getCantidad();
            }

            //*************************************************
            //Sumar cantidades aplicadas
            //*************************************************
            for (MovimientoInventario movimientoInventario : movimientosAplicados) {
                if (movimientoInventario.getAfectacion() == TiposAfectacion.POSITIVA.getValue())
                    cantIngreso += movimientoInventario.getAfectacion() * movimientoInventario.getCantidad();
                if (movimientoInventario.getAfectacion() == TiposAfectacion.NEGATIVA.getValue())
                    cantSalida += movimientoInventario.getAfectacion() * movimientoInventario.getCantidad();
            }

            if (productoExistencia == null) {
                productoExistencia = new ProductoExistencia();
                ProductoExistenciaPK id = new ProductoExistenciaPK(producto, almacen);
                productoExistencia.setId(id);
                productoExistencia.setIngreso(cantIngreso);
                productoExistencia.setSalida(cantSalida);
                productoExistencia.setExistencia(productoExistencia.getCantidad() + productoExistencia.getIngreso() +
                        productoExistencia.getSalida());
            } else {
                productoExistencia.setIngreso(cantIngreso);
                productoExistencia.setSalida(cantSalida);
                productoExistencia.setExistencia(productoExistencia.getCantidad() + productoExistencia.getIngreso() +
                        productoExistencia.getSalida());
            }

            return productoExistencia;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<ProductoExistencia> buscarProductoExistencias(String codigo) throws ManagerProductoServiceBusinessException,
            RemoteException {

        logger.debug("Buscando existencias de productos con parametros: [codigo]: " + codigo);

        //init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Buscar almacenes de la compania
            List<Almacen> almacenes = almacenEAO.findAlmacenesPorCompania(mgrSeguridad.
                    buscarCompaniaUsuario(mgrAutorizacion.getUsername()).getId());

            //Listado de productos existencias del producto
            List<ProductoExistencia> existencias = new ArrayList<ProductoExistencia>();

            for (Almacen almacen : almacenes) {

                //Producto existencia
                ProductoExistencia productoExistencia = buscarProductoExistenciaPorAlmacen(codigo, almacen.getId());
                existencias.add(productoExistencia);
            }

            return existencias;

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public boolean isProductoParaRegistro(String codigo) throws RemoteException {

        logger.debug("Buscando producto para comprobar que se encuentra registrado, [codigo]: " + codigo);

        try {

            //Buscar producto por codigo
            buscarProductoPorCodigo(codigo);

            //Retorna false producto encontrado
            return false;

        } catch (ManagerProductoServiceBusinessException e) {
            logger.info("PRODUCTO NO ENCONTRADO!!!...");
            return true;
        }
    }

    @Override
    public Producto crearProducto(String codigo, boolean compuesto, String nombre, String codigoFabricante, String alias,
                                  String marca, String modelo, String observaciones, long minimo, long maximo, BigDecimal costoUND,
                                  BigDecimal costoCIF, BigDecimal costoFOB, BigDecimal descuento, boolean exento, BigDecimal precioEstandar,
                                  UnidadMedida unidadMedida, Proveedor proveedor, Clasificador clasificador, Linea linea, Pais paisOrigen,
                                  byte[] fotografia, Set<ProductoCompuesto> productos)
            throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Creando producto con parametros: [codigo]: " + codigo + ", [compuesto]: " + compuesto + ", [nombre]: " +
                nombre + ", [codigoFabricante]: " + codigoFabricante + ", [alias]: " + alias + ", [marca]: " + marca +
                ", [modelo]: " + modelo + ", [observaciones]: " + observaciones + ", [minimo]: " + minimo + ", [maximo]: " +
                maximo + ", [costoUND]: " + costoUND + ", [costoCIF]: " + costoCIF + ", [costoFOB]: " + costoFOB + ", [descuento]: " +
                descuento + ", [exento]: " + exento + ", [precioEstandar]: " + precioEstandar);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());


        try {

            //<Preparar el contexto>
            linea = lineaEAO.findById(linea.getId());
            unidadMedida = unidadMedidaEAO.findById(unidadMedida.getId());
            proveedor = proveedorEAO.findById(proveedor.getId());
            clasificador = clasificadorEAO.findById(clasificador.getId());

            //<Obtener compania del usuario>
            String username = mgrAutorizacion.getUsername();
            Compania compania = mgrSeguridad.buscarUsuarioPorLogin(username).getCompania();

            //<Buscar estado producto por nombre>
            EstadoProducto estadoProducto = buscarEstadoProductoPorNombre(EstadosProducto.VIGENTE.getNombre());

            //<Evaluar si el usuario tiene una compania asociada para proceder al registro>
            if (compania == null)
                throw new ManagerProductoServiceBusinessException(username + ", no tiene una compania asociada.");

            //<Evaluar si es un producto compuesto el listado de productos no puede ser empty>
            if (compuesto && productos.isEmpty())
                throw new ManagerProductoServiceBusinessException("Debes registrar los productos compuestos.");

            //<Registrar producto>
            Producto entity = new Producto();
            entity.setCodigo(codigo);
            entity.setCompuesto(compuesto);
            entity.setNombre(nombre);
            entity.setCodigoFabricante(codigoFabricante);
            entity.setAlias(alias);
            entity.setMarca(marca);
            entity.setModelo(modelo);
            entity.setCodigoCbs(clasificador.getCbs());
            entity.setObservaciones(observaciones);
            entity.setMinimo(minimo);
            entity.setMaximo(maximo);
            entity.setCostoUND(costoUND.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setCostoCIF(costoCIF.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setCostoFOB(costoFOB.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setCostoPROM(costoUND.setScale(4, BigDecimal.ROUND_HALF_EVEN)); //Creacion de producto por primera vez su costo PROM es igual a costo UND
            entity.setDescuento(descuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setExento(exento);
            entity.setPrecioESTANDAR(precioEstandar.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setUnidadMedida(unidadMedida);
            entity.setProveedor(proveedor);
            entity.setClasificador(clasificador);
            entity.setLinea(linea);
            entity.setPaisOrigen(paisOrigen);
            entity.setCompania(compania);
            entity.setEstatus(EstadosActivacion.ACTIVO.getValue());
            entity.setEstado(estadoProducto);
            entity.setProductosCompuestos(null);
            entity.setFotografia(null);

            //Crear imagen del producto
            if (fotografia != null) {
                ProductoImageLOB image = new ProductoImageLOB();
                image.setImage(fotografia);
                entity.setFotografia(image);
            }

            //Persistir producto
            entity = productoEAO.create(entity);

            //******************************************************************
            //Actualizar listado de productos compuestos si es compuesto
            //******************************************************************
            if (compuesto) {

                double totalCostoFOB = 0.0;
                double totalCostoCIF = 0.0;
                double totalCostoUND = 0.0;
                double totalCostoPROM = 0.0;
                double totalPrecio = 0.0;

                for (ProductoCompuesto producto : productos) {

                    //Sumar total costoFOB
                    totalCostoFOB += (producto.getProducto().getCostoFOB().doubleValue() * producto.getCantidad());
                    //Sumar total costoCIF
                    totalCostoCIF += (producto.getProducto().getCostoCIF().doubleValue() * producto.getCantidad());
                    //Sumar total costoUND
                    totalCostoUND += (producto.getProducto().getCostoUND().doubleValue() * producto.getCantidad());
                    //Sumar total costoPROM
                    totalCostoPROM += (producto.getProducto().getCostoPROM().doubleValue() * producto.getCantidad());
                    //Sumar total precio
                    totalPrecio += producto.getPrecioTotal().doubleValue();
                }

                //Setting listado de productos compuestos y actualizando datos producto principal
                entity.setProductosCompuestos(productos);
                entity.setCostoFOB(new BigDecimal(totalCostoFOB).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setCostoCIF(new BigDecimal(totalCostoCIF).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setCostoUND(new BigDecimal(totalCostoUND).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setCostoPROM(new BigDecimal(totalCostoPROM).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setPrecioESTANDAR(new BigDecimal(totalPrecio).setScale(4, BigDecimal.ROUND_HALF_EVEN));

                //Actualizar producto
                entity = productoEAO.update(entity);

            }

            return entity;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage());
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Producto modificarProducto(Integer id, String codigo, boolean compuesto, String nombre, String codigoFabricante,
                                      String alias, String marca, String modelo, String observaciones, long minimo,
                                      long maximo, BigDecimal costoUND, BigDecimal costoCIF, BigDecimal costoFOB, BigDecimal
            costoPROM, BigDecimal descuento, boolean exento, BigDecimal precioEstandar,
                                      UnidadMedida unidadMedida, Proveedor proveedor, Clasificador clasificador,
                                      Linea linea, Pais paisOrigen, byte[] fotografia, Set<ProductoCompuesto> productos)
            throws ManagerProductoServiceBusinessException, RemoteException {
        System.out.println(" prueba 4: " + fotografia);
        logger.info("Modificando producto con parametros: [codigo]: " + codigo + ", [compuesto]: " + compuesto + ", [nombre]: " +
                nombre + ", [codigoFabricante]: " + codigoFabricante + ", [alias]: " + alias + ", [marca]: " + marca +
                ", [modelo]: " + modelo + ", [observaciones]: " + observaciones + ", [minimo]: " + minimo + ", [maximo]: " +
                maximo + ", [costoUND]: " + costoUND + ", [costoCIF]: " + costoCIF + ", [costoFOB]: " + costoFOB + ", [costoPROM]: " +
                costoPROM + ", [descuento]: " + descuento + ", [exento]: " + exento + ", [precioEstandar]: " + precioEstandar);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());

        try {

            //<Preparar el contexto>
            Producto entity = productoEAO.findById(id);
            linea = lineaEAO.findById(linea.getId());
            unidadMedida = unidadMedidaEAO.findById(unidadMedida.getId());
            proveedor = proveedorEAO.findById(proveedor.getId());
            clasificador = clasificadorEAO.findById(clasificador.getId());

            //<Evaluar si es un producto compuesto el listado de productos no puede ser empty>
            if (compuesto && productos.isEmpty())
                throw new ManagerProductoServiceBusinessException("Debes registrar los productos compuestos.");
            System.out.println("Entre Aqui 2");
            //<Registrar producto>
            entity.setCodigo(codigo);
            entity.setCompuesto(compuesto);
            entity.setNombre(nombre);
            entity.setCodigoFabricante(codigoFabricante);
            entity.setAlias(alias);
            entity.setMarca(marca);
            entity.setModelo(modelo);
            entity.setCodigoCbs(clasificador.getCbs());
            entity.setObservaciones(observaciones);
            entity.setMinimo(minimo);
            entity.setMaximo(maximo);
            entity.setCostoUND(costoUND.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setCostoCIF(costoCIF.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setCostoFOB(costoFOB.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setCostoPROM(costoPROM.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setDescuento(descuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setExento(exento);
            entity.setPrecioESTANDAR(precioEstandar.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            entity.setUnidadMedida(unidadMedida);
            entity.setProveedor(proveedor);
            entity.setClasificador(clasificador);
            entity.setLinea(linea);
            entity.setPaisOrigen(paisOrigen);
            entity.setProductosCompuestos(null);
            entity.setFotografia(null);

            //Metodo utilizado anteriormente para modificar Producto -- Se reparo para que permita modificar la fotografia
            //TODO = Actualmente esta insertando una imagen nueva en vez de modificar la actual, estaba dando problemas al
            //       Modificar la imagen del producto (Revisar el metodo entity.getFotografia() vuelve null

            /*if (fotografia != null) {
                ProductoImageLOB image = entity.getFotografia();
                image.setImage(fotografia);
                entity.setFotografia(image);
            }*/

            if (fotografia != null) {
                ProductoImageLOB image = new ProductoImageLOB();
                image.setImage(fotografia);
                entity.setFotografia(image);
            }

            //Actualizar entidad
            entity = productoEAO.update(entity);

            //******************************************************************
            //Actualizar listado de productos compuestos si es compuesto
            //******************************************************************

            if (compuesto) {

                double totalCostoFOB = 0.0;
                double totalCostoCIF = 0.0;
                double totalCostoUND = 0.0;
                double totalCostoPROM = 0.0;
                double totalPrecio = 0.0;

                for (ProductoCompuesto producto : productos) {

                    //Sumar total costoFOB
                    totalCostoFOB += (producto.getProducto().getCostoFOB().doubleValue() * producto.getCantidad());
                    //Sumar total costoCIF
                    totalCostoCIF += (producto.getProducto().getCostoCIF().doubleValue() * producto.getCantidad());
                    //Sumar total costoUND
                    totalCostoUND += (producto.getProducto().getCostoUND().doubleValue() * producto.getCantidad());
                    //Sumar total costoPROM
                    totalCostoPROM += (producto.getProducto().getCostoPROM().doubleValue() * producto.getCantidad());
                    //Sumar total precio
                    totalPrecio += producto.getPrecioTotal().doubleValue();
                }

                //Setting listado de productos compuestos y actualizando datos producto principal
                entity.setProductosCompuestos(productos);
                entity.setCostoFOB(new BigDecimal(totalCostoFOB).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setCostoCIF(new BigDecimal(totalCostoCIF).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setCostoUND(new BigDecimal(totalCostoUND).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setCostoPROM(new BigDecimal(totalCostoPROM).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                entity.setPrecioESTANDAR(new BigDecimal(totalPrecio).setScale(4, BigDecimal.ROUND_HALF_EVEN));

                //Actualizar producto
                entity = productoEAO.update(entity);

            }

            return entity;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Producto recodificarProducto(String codigoActual, String codigoNuevo) throws ManagerProductoServiceBusinessException,
            RemoteException {

        logger.debug("Recodificar producto con parametros: [codigoActual]: " + codigoActual + ", [codigoNuevo]: " +
                codigoNuevo);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Producto producto = productoEAO.findByCodigo(codigoActual);

            //Validar que codigo de recodificacion no se encuentre ocupado
            if (!isProductoParaRegistro(codigoNuevo))
                throw new ManagerProductoServiceBusinessException("Error - codigo de recodificacion ya se encuentra registrado");

            //**************************************************************
            //Crear producto modificacion
            //**************************************************************
            ProductoModificacion productoModificacion = crearProductoModificacion(producto);
            productoModificacionEAO.create(productoModificacion);

            //**************************************************************
            //Recodificar producto por detalles de grabacion
            //**************************************************************
            articuloEntradaEAO.recodificarProducto(producto.getId(), codigoNuevo);
            articuloSalidaEAO.recodificarProducto(producto.getId(), codigoNuevo);
            articuloTrasladoEAO.recodificarProducto(producto.getId(), codigoNuevo);
            articuloLevantamientoFisicoEAO.recodificarProducto(producto.getId(), codigoNuevo);
            articuloProformaEAO.recodificarProducto(producto.getId(), codigoNuevo);
            articuloFacturaEAO.recodificarProducto(producto.getId(), codigoNuevo);

            //Setting codigo nuevo
            producto.setCodigo(codigoNuevo);

            //Persist producto
            return productoEAO.update(producto);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void eliminarProducto(Integer idProducto) throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Eliminando producto con identificador: [IDPRODUCTO]: " + idProducto);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());

        try {

            //Buscando producto por su identificador
            Producto producto = productoEAO.findById(idProducto);

            //Buscando movimientos de inventario para evaluar si cumple con la solicitud.
            int cantMov = movimientoInventarioEAO.findCantidadMovimientosInventario(producto.getId());

            if (cantMov > 0) {
                throw new ManagerProductoServiceBusinessException("C\u00f3digo [" + producto.getCodigo() + "] " +
                        "tiene movimientos de inventario. No se puede proceder con la eliminaci\u00f3n");
            }

            //Eliminar producto
            productoEAO.remove(producto.getId());

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void anularProductos(List<Integer> productos) throws ManagerProductoServiceBusinessException, RemoteException {

        logger.debug("Anular producto listado de productos");

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());

        try {

            //Get Estado Producto
            EstadoProducto estadoProducto = estadoProductoEAO.findByNombre(EstadosProducto.DEBAJA.getNombre());

            for (Integer idProducto : productos) {
                Producto producto = productoEAO.findById(idProducto);


                //Change producto STATE
                producto.setEstado(estadoProducto);

                //Update Producto
                productoEAO.update(producto);
            }

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    //====================================================================================
    //Buscar estado producto
    //====================================================================================

    /**
     * Busca estado producto por nombre
     *
     * @param nombre, Nombre del estado del producto
     * @return EstadoProducto
     * @throws ManagerProductoServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    private EstadoProducto buscarEstadoProductoPorNombre(String nombre) throws ManagerProductoServiceBusinessException,
            RemoteException {

        logger.debug("Buscando estado del producto con parametros: [nombre]: " + nombre);

        //Init servicio
        boolean transaction = initBusinessService(Roles.ROLCATALOGOPRODUCTOADMIN.toString());

        try {

            return estadoProductoEAO.findByNombre(nombre);

        } catch (PersistenceClassNotFoundException e) {
            logger.debug(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.debug(e.getMessage(), e);
            throw new ManagerProductoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    /**
     * Create Producto Modificacion desde un Producto
     *
     * @param producto, Producto
     * @return ProductoModificacion
     * @throws Exception, Exception
     */
    private ProductoModificacion crearProductoModificacion(Producto producto) throws ManagerProductoServiceBusinessException {

        ProductoModificacion productoModificacion = new ProductoModificacion();
        productoModificacion.setCodigo(producto.getCodigo());
        productoModificacion.setCodigoCbs(producto.getCodigoCbs());
        productoModificacion.setCodigoBarra(producto.getCodigoBarra());
        productoModificacion.setNombre(producto.getNombre());
        productoModificacion.setCodigoFabricante(producto.getCodigoFabricante());
        productoModificacion.setAlias(producto.getAlias());
        productoModificacion.setMarca(producto.getMarca());
        productoModificacion.setModelo(producto.getModelo());
        productoModificacion.setObservaciones(producto.getObservaciones());
        productoModificacion.setMinimo(producto.getMinimo());
        productoModificacion.setMaximo(producto.getMaximo());
        productoModificacion.setFechaVencimiento(producto.getFechaVencimiento());
        productoModificacion.setCostoUND(producto.getCostoUND());
        productoModificacion.setCostoCIF(producto.getCostoCIF());
        productoModificacion.setCostoFOB(producto.getCostoFOB());
        productoModificacion.setCostoPROM(producto.getCostoPROM());
        productoModificacion.setDescuento(producto.getDescuento());
        productoModificacion.setExento(producto.isExento());
        productoModificacion.setPrecioESTANDAR(producto.getPrecioESTANDAR());
        productoModificacion.setPrecioPROMOCION(producto.getPrecioPROMOCION());
        productoModificacion.setUnidadMedida(producto.getUnidadMedida());
        productoModificacion.setProveedor(producto.getProveedor());
        productoModificacion.setClasificador(producto.getClasificador());
        productoModificacion.setLinea(producto.getLinea());
        productoModificacion.setEstado(producto.getEstado());
        productoModificacion.setCompuesto(producto.isCompuesto());
        productoModificacion.setEstatus(producto.getEstatus());
        productoModificacion.setProducto(producto);

        Set<ProductoCompuestoMod> prod_compuestos = new HashSet<ProductoCompuestoMod>();
        for (ProductoCompuesto prodCompuesto : producto.getProductosCompuestos()) {
            ProductoCompuestoMod prodCompuestoMod = new ProductoCompuestoMod();
            prodCompuestoMod.setProducto(prodCompuesto.getProducto());
            prodCompuestoMod.setCantidad(prodCompuesto.getCantidad());
            prodCompuestoMod.setPrecio(prodCompuesto.getPrecio());
            prodCompuestoMod.setPrecioTotal(prodCompuesto.getPrecioTotal());

            prod_compuestos.add(prodCompuestoMod);
        }
        productoModificacion.setProductosCompuestos(prod_compuestos);

        return productoModificacion;
    }
}
