/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.inventario;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.almacenEAO.AlmacenEAO;
import contac.modelo.eao.almacenEAO.AlmacenEAOPersistence;
import contac.modelo.eao.articuloEntradaEAO.ArticuloEntradaEAO;
import contac.modelo.eao.articuloEntradaEAO.ArticuloEntradaEAOPersistence;
import contac.modelo.eao.articuloLevantamientoFisicoEAO.ArticuloLevantamientoFisicoEAO;
import contac.modelo.eao.articuloLevantamientoFisicoEAO.ArticuloLevantamientoFisicoEAOPersistence;
import contac.modelo.eao.articuloSalidaEAO.ArticuloSalidaEAO;
import contac.modelo.eao.articuloSalidaEAO.ArticuloSalidaEAOPersistence;
import contac.modelo.eao.articuloTrasladoEAO.ArticuloTrasladoEAO;
import contac.modelo.eao.articuloTrasladoEAO.ArticuloTrasladoEAOPersistence;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAO;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAOPersistence;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.monedaEAO.MonedaEAO;
import contac.modelo.eao.monedaEAO.MonedaEAOPersistence;
import contac.modelo.eao.movimientoInventarioEAO.MovimientoInventarioEAO;
import contac.modelo.eao.movimientoInventarioEAO.MovimientoInventarioEAOPersistence;
import contac.modelo.eao.ordenEntradaEAO.OrdenEntradaEAO;
import contac.modelo.eao.ordenEntradaEAO.OrdenEntradaEAOPersistence;
import contac.modelo.eao.ordenLevantamientoFisicoEAO.OrdenLevantamientoFisicoEAO;
import contac.modelo.eao.ordenLevantamientoFisicoEAO.OrdenLevantamientoFisicoEAOPersistence;
import contac.modelo.eao.ordenSalidaEAO.OrdenSalidaEAO;
import contac.modelo.eao.ordenSalidaEAO.OrdenSalidaEAOPersistence;
import contac.modelo.eao.ordenTrasladoEAO.OrdenTrasladoEAO;
import contac.modelo.eao.ordenTrasladoEAO.OrdenTrasladoEAOPersistence;
import contac.modelo.eao.productoEAO.ProductoEAO;
import contac.modelo.eao.productoEAO.ProductoEAOPersistence;
import contac.modelo.entity.*;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import contac.servicio.catalogo.ManagerProductoServiceBusinessImpl;
import contac.servicio.seguridad.*;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Manager de Implementacion de Inventario Service
 * User: EMontenegro
 * Date: 11-02-11
 * Time: 09:41 PM
 */
public class ManagerInventarioServiceBusinessImpl extends UnicastRemoteObject implements ManagerInventarioServiceBusiness {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ManagerInventarioServiceBusinessImpl.class);

    //Acceso Capa EAO
    private OrdenEntradaEAO ordenEntradaEAO;
    private OrdenTrasladoEAO ordenTrasladoEAO;
    private OrdenSalidaEAO ordenSalidaEAO;
    private OrdenLevantamientoFisicoEAO ordenLevantamientoFisicoEAO;
    private AlmacenEAO almacenEAO;
    private EstadoMovimientoEAO estadoMovimientoEAO;
    private MonedaEAO monedaEAO;
    private ArticuloEntradaEAO articuloEntradaEAO;
    private ArticuloTrasladoEAO articuloTrasladoEAO;
    private ArticuloSalidaEAO articuloSalidaEAO;
    private ArticuloLevantamientoFisicoEAO articuloLevantamientoFisicoEAO;
    private MovimientoInventarioEAO movimientoInventarioEAO;
    private ProductoEAO productoEAO;

    //Acceso Manager de Autorizacion
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;
    protected ManagerSeguridadServiceBusiness mgrSeguridad;
    protected ManagerProductoServiceBusiness mgrProducto;

    /**
     * Default Constructor
     *
     * @throws RemoteException, Exception
     */
    public ManagerInventarioServiceBusinessImpl() throws RemoteException {

        logger.debug("Creando servicio de Inventario Service...");

        //Inicializando accesos EAO
        ordenEntradaEAO = new OrdenEntradaEAOPersistence();
        ordenTrasladoEAO = new OrdenTrasladoEAOPersistence();
        ordenSalidaEAO = new OrdenSalidaEAOPersistence();
        ordenLevantamientoFisicoEAO = new OrdenLevantamientoFisicoEAOPersistence();
        almacenEAO = new AlmacenEAOPersistence();
        estadoMovimientoEAO = new EstadoMovimientoEAOPersistence();
        monedaEAO = new MonedaEAOPersistence();
        articuloEntradaEAO = new ArticuloEntradaEAOPersistence();
        articuloTrasladoEAO = new ArticuloTrasladoEAOPersistence();
        articuloSalidaEAO = new ArticuloSalidaEAOPersistence();
        articuloLevantamientoFisicoEAO = new ArticuloLevantamientoFisicoEAOPersistence();
        movimientoInventarioEAO = new MovimientoInventarioEAOPersistence();
        productoEAO = new ProductoEAOPersistence();
    }

    /**
     * Constructor de Manager Catalogo con un usuario de autorizacion
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerInventarioServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Llamar al constructor padre
        this();

        //Inicializar servicio de autorizacion
        this.mgrAutorizacion = mgrAutorizacion;
        this.mgrSeguridad = new ManagerSeguridadServiceBusinessImpl(this.mgrAutorizacion);
        this.mgrProducto = new ManagerProductoServiceBusinessImpl(this.mgrAutorizacion);
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerInventarioServiceBusinessException {

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
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerInventarioServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerInventarioServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<Almacen> buscarAlmacenesPorUsuario() throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar almacenes autorizados para el usuario");

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Obtener compania del usuario
            Compania compania = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getCompania();

            //Obtener listado de almacenes por compania
            return almacenEAO.findAlmacenesPorCompania(compania.getId());

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public Almacen buscarAlmacenUsuario() throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar almacen del usuario autenticado");

        //Iniciar servicio authentication
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        try {

            //Obtener almacen del usuario de autenticacion
            Usuario usuario = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername());

            return usuario.getAlmacen();

        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public OrdenEntrada buscarOrdenEntradaPorId(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar orden de entrada con parametros: [idOrdenEntrada]: " + idOrdenEntrada);

        try {

            return ordenEntradaEAO.findById(idOrdenEntrada);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<OrdenEntrada> buscarOrdenesEntradaPorTipo(int tipoEntrada) throws ManagerInventarioServiceBusinessException,
            RemoteException {

        logger.info("Buscando ordenes de entrada con parametros: [tipoEntrada]: " + tipoEntrada);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            boolean valueTipoEntrada = false;

            //Evaluar que el tipo de orden de entrada sea valido
            for (TiposEntrada tipo : TiposEntrada.values()) {
                if (tipo.getValue() == tipoEntrada)
                    valueTipoEntrada = true;
            }

            if (!valueTipoEntrada)
                throw new ManagerInventarioServiceBusinessException("Tipo de entrada no v\u00e1lido.  Favor verifique.");

            return ordenEntradaEAO.findByTipoEntrada(tipoEntrada);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<OrdenEntrada> buscarFacturasPorFechaAlmacen(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscando Ordenes de Entrada por rangos de fecha: [fechaDesde]: " + fechaDesde + ", [fechaHasta]: " +
                fechaHasta);

        //Iniciar servicio de autorizacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validar campos de la busqueda
            if (fechaDesde == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            //Buscar almacen del usuario - First check authorization for user
            boolean success = mgrAutorizacion.checkUserInRole(Roles.ROLINVENTARIOADMIN.toString());

            Almacen almacen = null;
            if (success) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }

            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaDesde);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaDesde = gc.getTime();

            gc.setTime(fechaHasta);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaHasta = gc.getTime();

            //Preparar el contexto
            List<Integer> idEstados = new ArrayList<Integer>();

            for (String estado : estados) {
                idEstados.add(estadoMovimientoEAO.findByAlias(estado).getId());
            }

            return ordenEntradaEAO.findByEstadosAlmacen(idEstados, fechaDesde, fechaHasta, almacen.getId());

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<OrdenEntrada> buscarOrdenesEntradaPorEstados(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.info("Buscar ordenes de entrada por estados");

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {
            //Validar campos de la busqueda
            if (fechaDesde == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            Almacen almacen = null;
            if (value) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }
            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaDesde);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaDesde = gc.getTime();

            gc.setTime(fechaHasta);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaHasta = gc.getTime();

            //Preparar el contexto
            List<Integer> idEstados = new ArrayList<Integer>();

            for (String estado : estados) {
                idEstados.add(estadoMovimientoEAO.findByAlias(estado).getId());
            }

            return ordenEntradaEAO.findByEstados(idEstados, fechaDesde, fechaHasta, almacen.getId());

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<OrdenEntrada> buscarOrdenesEntradaPorRangoFechas(Date fechaInicio, Date fechaFin) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de entrada por rangos de fecha con parametros: [fechaInicio]: " + fechaInicio + ", " +
                " [fechaFin]: " + fechaFin);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validar datos
            if (fechaInicio == null)
                throw new ManagerInventarioServiceBusinessException("Debes ingresar una fecha de inicio.");

            if (fechaFin == null)
                throw new ManagerInventarioServiceBusinessException("Debes ingresar una fecha fin.");

            //Buscar ordenes de entrada por rangos de fechas
            return ordenEntradaEAO.findByFechas(fechaInicio, fechaFin);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenSalida buscarOrdenSalidaPorId(Integer idOrdenSalida) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de salida con parametros: [idOrdenSalida]: " + idOrdenSalida);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            return ordenSalidaEAO.findById(idOrdenSalida);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<OrdenSalida> buscarOrdenesSalidaPorEstados(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de salida por estados");

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {
            //Validar campos de la busqueda
            if (fechaDesde == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            Almacen almacen = null;
            if (value) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }

            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaDesde);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaDesde = gc.getTime();

            gc.setTime(fechaHasta);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaHasta = gc.getTime();

            //Preparar el contexto
            List<Integer> idEstados = new ArrayList<Integer>();

            for (String estado : estados) {
                idEstados.add(estadoMovimientoEAO.findByAlias(estado).getId());
            }

            return ordenSalidaEAO.findByEstados(idEstados, fechaDesde, fechaHasta, almacen.getId());

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }


    @Override
    public List<OrdenSalida> buscarOrdnesSalidaPorRangosFechas(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de salida por rangos de fecha con parametros: [fechaDesde]: " + fechaDesde + ", " +
                " [fechaHasta]: " + fechaHasta);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validar datos
            if (fechaDesde == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            Almacen almacen = null;
            if (value) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }

            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaDesde);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaDesde = gc.getTime();

            gc.setTime(fechaHasta);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaHasta = gc.getTime();

            //Preparar el contexto
            List<Integer> idEstados = new ArrayList<Integer>();

            for (String estado : estados) {
                idEstados.add(estadoMovimientoEAO.findByAlias(estado).getId());
            }

            //Buscar ordenes de salida por rangos de fechas
            return ordenSalidaEAO.findByEstados(idEstados,fechaDesde, fechaHasta,idAlmacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }
    @Override
    public OrdenTraslado buscarOrdenTrasladoPorId(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de traslado con parametros: [idOrdenTraslado]: " + idOrdenTraslado);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            return ordenTrasladoEAO.findById(idOrdenTraslado);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<OrdenTraslado> buscarOrdenesTrasladoPorEstados(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de traslado por estados: [estados] " + " fechaDesde: " + fechaDesde + " fechaHasta: " + fechaHasta + " Almacen: " + idAlmacen);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validar campos de la busqueda
            if (fechaDesde == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerInventarioServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            //Buscar almacen del usuario - First check authorization for user
            boolean success = mgrAutorizacion.checkUserInRole(Roles.ROLINVENTARIOADMIN.toString());

            Almacen almacen = null;
            if (success) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }

            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaDesde);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaDesde = gc.getTime();

            gc.setTime(fechaHasta);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaHasta = gc.getTime();

            //Preparar el contexto
            List<Integer> idEstados = new ArrayList<Integer>();

            for (String estado : estados) {
                idEstados.add(estadoMovimientoEAO.findByAlias(estado).getId());
            }

            return ordenTrasladoEAO.findByEstados(idEstados, fechaDesde, fechaHasta, idAlmacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public void buscarEstadoAjuste(Integer idLevantamiento) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar estado de Orden de Levantamiento Inventario Físico: [idLevantamiento]: " + idLevantamiento);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenLevantamientoFisico ordenLevantamiento = ordenLevantamientoFisicoEAO.findById(idLevantamiento);

            //Validar datos generales del Levantamiento Inventario Fisico
            if (ordenLevantamiento.getEstado().getAlias().equals(EstadosMovimiento.APLICADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Esta Orden ya fue aplicada.");

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<OrdenLevantamientoFisico> buscarOrdenesLevantamientoFisicoPorFechas(Date fechaInicio, Date fechaFin, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException {

        logger.debug("Buscar ordenes de Levantamiento de Inventario por rangos de fecha con parametros: [fechaInicio]: " + fechaInicio + ", " +
                " [fechaFin]: " + fechaFin + " [idAlmacen]: " + idAlmacen);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validar datos
            if (fechaInicio == null)
                throw new ManagerInventarioServiceBusinessException("Debes ingresar una fecha de inicio.");

            if (fechaFin == null)
                throw new ManagerInventarioServiceBusinessException("Debes ingresar una fecha fin.");

            //Buscar almacen del usuario - First check authorization for user
            boolean success = mgrAutorizacion.checkUserInRole(Roles.ROLINVENTARIOADMIN.toString());

            Almacen almacen = null;
            if (success) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }

            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaInicio);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaInicio = gc.getTime();

            gc.setTime(fechaFin);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaFin = gc.getTime();

            //Buscar ordenes de traslado por rangos de fechas
            return ordenLevantamientoFisicoEAO.findByFechas(fechaInicio, fechaFin, idAlmacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<OrdenTraslado> buscarOrdenesTrasladoPorRangosFechas(Date fechaInicio, Date fechaFin, Integer idAlmacen, Integer idAlmacenSalida) throws ManagerInventarioServiceBusinessException,
            RemoteException {

        logger.debug("Buscar ordenes de traslado por rangos de fecha con parametros: [fechaInicio]: " + fechaInicio + ", " +
                " [fechaFin]: " + fechaFin + " [idAlmacen]: " + idAlmacen + " [idAlmacenSalida]: " + idAlmacenSalida);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validar datos
            if (fechaInicio == null)
                throw new ManagerInventarioServiceBusinessException("Debes ingresar una fecha de inicio.");

            if (fechaFin == null)
                throw new ManagerInventarioServiceBusinessException("Debes ingresar una fecha fin.");

            //Buscar almacen del usuario - First check authorization for user
            boolean success = mgrAutorizacion.checkUserInRole(Roles.ROLINVENTARIOADMIN.toString());

            Almacen almacen = null;
            if (success) {
                almacen = almacenEAO.findById(idAlmacen);
            } else {
                almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            }

            //Preparar fechas para busquedas
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(fechaInicio);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaInicio = gc.getTime();

            gc.setTime(fechaFin);
            gc.set(Calendar.HOUR_OF_DAY, 0);
            gc.set(Calendar.MINUTE, 0);
            gc.set(Calendar.SECOND, 0);
            gc.set(Calendar.MILLISECOND, 0);
            fechaFin = gc.getTime();

            //Buscar ordenes de traslado por rangos de fechas
            return ordenTrasladoEAO.findByFechas(fechaInicio, fechaFin, idAlmacen, idAlmacenSalida);


        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }


    @Override
    public OrdenEntrada crearOrdenEntrada(int tipoEntrada, Date fechaAlta, Date fechaSolicitud, Integer idAlmacenIngreso,
                                          String personaEntrega, String descripcion, List<ArticuloEntrada> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Creando orden de entrada con parametros: [tipoEntrada]:" + tipoEntrada + ", [fechaAlta]:" + fechaAlta +
                ", [almacenIngreso]:" + idAlmacenIngreso + ", [personaEntrega]: " + personaEntrega + ", [descripcion]:" +
                descripcion);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Almacen almacenIngreso = almacenEAO.findById(idAlmacenIngreso);
            Moneda moneda = almacenIngreso.getCompania().getMonedaReferencia();

            //Orden de entrada
            OrdenEntrada ordenEntrada = new OrdenEntrada();

            //Validar consistencia de datos.
            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior al dia de hoy.");

            if (articulos.isEmpty())
                throw new ManagerInventarioServiceBusinessException("Debes ingresar al menos un articulo.");

            //Evaluar tipo de orden de entrada
            if (TiposEntrada.ENTRADA_ORDINARIA.getValue() == tipoEntrada) {
                ordenEntrada = crearOrdenEntradaOrdinaria(fechaAlta, almacenIngreso, moneda, personaEntrega, descripcion, articulos);
            } else if (TiposEntrada.FACTURA_COMERCIAL.getValue() == tipoEntrada) {
                ordenEntrada = crearOrdenEntradaFacturaComercial(fechaAlta, almacenIngreso, personaEntrega, descripcion, articulos);
            } else if (TiposEntrada.POLIZA_IMPORTACION.getValue() == tipoEntrada) {
            }

            return ordenEntrada;

        } catch (PersistenceClassNotFoundException e) {
            value = false;
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            value = false;
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenEntrada modificarOrdenEntrada(Integer idOrdenEntrada, int tipoEntrada, Date fechaAlta, Date fechaSolicitud,
                                              String personaEntrega, String descripcion, List<ArticuloEntrada> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.info("Modificar orden de entrada con parametros: [idOrdenEntrada]: " + idOrdenEntrada + ", [tipo entrada]: " +
                tipoEntrada + ", [fecha alta]: " + fechaAlta + ", [fecha solicitud]: " + fechaSolicitud + ", [persona entrega]: " +
                personaEntrega + ", [descripcion]: " + descripcion);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenEntrada ordenEntrada = ordenEntradaEAO.findById(idOrdenEntrada);

            //Validar consistencia de datos
            if (!ordenEntrada.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("ESTADO no consistente para realizar una modificaci\u00f3n.");

            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior al dia de hoy.");

            if (articulos.isEmpty())
                throw new ManagerInventarioServiceBusinessException("Debes ingresar al menos un articulo.");

            //Actualizar datos generales de la orden de entrada
            ordenEntrada.setFechaAlta(fechaAlta);
            ordenEntrada.setFechaSolicitud(fechaSolicitud);
            ordenEntrada.setPersonaEntrega(personaEntrega);
            ordenEntrada.setDescripcion(descripcion);

            //Evaluar tipo de orden de entrada
            if (TiposEntrada.ENTRADA_ORDINARIA.getValue() == tipoEntrada) {
                ordenEntrada = modificarOrdenEntradaOrdinaria(ordenEntrada, articulos);
            } else if (TiposEntrada.FACTURA_COMERCIAL.getValue() == tipoEntrada) {
                //TODO: Implementar tipo de entrada factura comercial
            } else if (TiposEntrada.POLIZA_IMPORTACION.getValue() == tipoEntrada) {
                //TODO: Implementar tipo de entrada poliza de importacion
            }

            return ordenEntrada;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenEntrada anularOrdenEntrada(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Anulando orden de entrada con parametros: [id]: " + idOrdenEntrada);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenEntrada ordenEntrada = ordenEntradaEAO.findById(idOrdenEntrada);
            EstadoMovimiento estadoMovimiento = estadoMovimientoEAO.findByAlias(EstadosMovimiento.ANULADO.getEstado());

            //Evaluar si se encuentra en estado INGRESADO - APLICADO
            if (!ordenEntrada.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado())) {
                throw new ManagerInventarioServiceBusinessException("Orden de Entrada se encuentra en un estado no válido. No se puede anular.");
            }

            //Obtener movimientos de inventario y eliminarlos
            for (ArticuloEntrada articulo : ordenEntrada.getArticulos()) {

                MovimientoInventario movimiento = articulo.getMovimientoInventario();

                if (movimiento != null) {
                    movimientoInventarioEAO.remove(movimiento.getId());
                    articulo.setMovimientoInventario(null);
                }
            }

            //Actulizar estado de la orden de entrada de inventario
            ordenEntrada.setEstado(estadoMovimiento);

            //Persistir orden de entrada
            return ordenEntradaEAO.update(ordenEntrada);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public void eliminarOrdenEntrada(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Eliminando orden de entrada con parametros: [id]: " + idOrdenEntrada);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenEntrada ordenEntrada = ordenEntradaEAO.findById(idOrdenEntrada);

            //Evaluar si se encuentra en estado ingresado
            if (!ordenEntrada.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado())) {
                throw new ManagerInventarioServiceBusinessException("Orden de Entrada se encuentra en un estado no válido. No se puede eliminar.");
            }

            ordenEntradaEAO.remove(idOrdenEntrada);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenTraslado crearOrdenTraslado(Date fechaAlta, Date fechaSolicitud, Integer idAlmacenSalida,
                                            Integer idAlmacenIngreso, String personaEntrega, String personaRecibe,
                                            String descripcion, List<ArticuloTraslado> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Creando orden de traslado con parametros: [fechaAlta]: " + fechaAlta + ", [fechaSolicitud]: " + fechaSolicitud +
                ", [idAlmacenSalida]:" + idAlmacenSalida + ", [idAlmacenIngreso]: " + idAlmacenIngreso + ", [personaEntrega]: " +
                ", [personaRecibe]: " + personaRecibe + ", [descripcion]: " + descripcion);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Almacen almacenSalida = almacenEAO.findById(idAlmacenSalida);
            Almacen almacenIngreso = almacenEAO.findById(idAlmacenIngreso);
            //EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.PENDIENTE.getEstado());

            //Obtener numero consecutivo de traslado
            long noTraslado = ordenTrasladoEAO.obtenerNoTraslado();

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior al dia de hoy.");

            if (personaEntrega.equals(""))
                throw new ManagerInventarioServiceBusinessException("Debes registrar a una persona que entrega.");

            if (personaRecibe.equals(""))
                throw new ManagerInventarioServiceBusinessException("Debes registrar a una persona que recibe.");

            if (descripcion.equals(""))
                throw new ManagerInventarioServiceBusinessException("Debes registrar una descripción para este traslado.");

            if (articulos.isEmpty())
                throw new ManagerInventarioServiceBusinessException("Debes ingresar al menos un articulo.");

            //Crear orden de traslado en estado preliminar PENDIENTE
            OrdenTraslado ordenTraslado = new OrdenTraslado();
            ordenTraslado.setNoMovimiento(noTraslado);
            ordenTraslado.setAlmacenSalida(almacenSalida);
            ordenTraslado.setAlmacenEntrada(almacenIngreso);
            ordenTraslado.setPersonaEntrega(personaEntrega);
            ordenTraslado.setPersonaRecibe(personaRecibe);
            ordenTraslado.setDescripcion(descripcion);
            ordenTraslado.setFechaAlta(fechaAlta);
            ordenTraslado.setEstado(estado);
            ordenTraslado.setMoneda(almacenIngreso.getCompania().getMonedaReferencia());
            ordenTraslado.setMontoTotal(new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_EVEN));

            //Crear movimientos de inventario
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obtener articulo traslado
                ArticuloTraslado articulo = (ArticuloTraslado) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Creating movimientos de inventario de salida y entrada

                    //<Movimiento de Salida de inventario>
                    MovimientoInventario movimientoSalida = new MovimientoInventario();
                    movimientoSalida.setFechaAlta(fechaAlta);
                    movimientoSalida.setAfectacion(new Integer(-1).shortValue());
                    movimientoSalida.setCantidad(articulo.getCantidad());
                    movimientoSalida.setAlmacen(almacenSalida);
                    movimientoSalida.setArticuloTraslado(articulo);
                    movimientoSalida.setEstado(estado);
                    movimientoSalida.setProducto(articulo.getProducto());

                    //<Movimiento de ingreso de inventario>
                    MovimientoInventario movimientoIngreso = new MovimientoInventario();
                    movimientoIngreso.setFechaAlta(fechaAlta);
                    movimientoIngreso.setAfectacion(new Integer(1).shortValue());
                    movimientoIngreso.setCantidad(articulo.getCantidad());
                    movimientoIngreso.setAlmacen(almacenIngreso);
                    movimientoIngreso.setArticuloTraslado(articulo);
                    movimientoIngreso.setEstado(estado);
                    movimientoIngreso.setProducto(articulo.getProducto());

                    Set<MovimientoInventario> setMovimientos = new HashSet<MovimientoInventario>();
                    setMovimientos.add(movimientoSalida);
                    setMovimientos.add(movimientoIngreso);

                    articulo.setMovimientosInventario(setMovimientos);
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    if (articulo.getMovimientosInventario() == null || articulo.getMovimientosInventario().isEmpty()) {
                        //Creating movimientos de inventario de salida y entrada

                        //<Movimiento de Salida de inventario>
                        MovimientoInventario movimientoSalida = new MovimientoInventario();
                        movimientoSalida.setFechaAlta(fechaAlta);
                        movimientoSalida.setAfectacion(new Integer(-1).shortValue());
                        movimientoSalida.setCantidad(articulo.getCantidad());
                        movimientoSalida.setAlmacen(almacenSalida);
                        movimientoSalida.setArticuloTraslado(articulo);
                        movimientoSalida.setEstado(estado);
                        movimientoSalida.setProducto(articulo.getProducto());

                        //<Movimiento de ingreso de inventario>
                        MovimientoInventario movimientoIngreso = new MovimientoInventario();
                        movimientoIngreso.setFechaAlta(fechaAlta);
                        movimientoIngreso.setAfectacion(new Integer(1).shortValue());
                        movimientoIngreso.setCantidad(articulo.getCantidad());
                        movimientoIngreso.setAlmacen(almacenIngreso);
                        movimientoIngreso.setArticuloTraslado(articulo);
                        movimientoIngreso.setEstado(estado);
                        movimientoIngreso.setProducto(articulo.getProducto());

                        Set<MovimientoInventario> setMovimientos = new HashSet<MovimientoInventario>();
                        setMovimientos.add(movimientoSalida);
                        setMovimientos.add(movimientoIngreso);

                        articulo.setMovimientosInventario(setMovimientos);
                    } else {
                        //Actulizar cantidad movimiento inventario
                        for (MovimientoInventario movimientoInventario : articulo.getMovimientosInventario()) {
                            movimientoInventario.setCantidad(articulo.getCantidad());
                        }
                    }
                }

                //Calcular costo del traslado
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();
                articulo.setOrdenTraslado(ordenTraslado);
                articulo.setNoDocumento(ordenTraslado.getNoMovimiento());
                articulo.setRenglon(renglon);

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Actualizar renglon
                renglon += 1;
            }

            //Setting monto total inventario
            ordenTraslado.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenTraslado.setArticulos(new HashSet<ArticuloTraslado>(articulos));

            //Persist orden de traslado
            return ordenTrasladoEAO.create(ordenTraslado);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public void validarImpresionOrdenEntrada(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Validar Impresión de Orden de Entrada: [idOrdenEntrada]: " + idOrdenEntrada);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenEntrada ordenEntradaAplicar = ordenEntradaEAO.findById(idOrdenEntrada);

            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //Validar datos generales de la Orden de Traslado
            if (ordenEntradaAplicar.getEstado().getAlias().equals(EstadosMovimiento.ANULADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de de Entrada no se encuentra en un estado valido para poder imprimir.");
        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void validarImpresionOrdenBaja(Integer idOrdenSalida) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Validar Impresión de Orden de Traslado: [idOrdenSalida]: " + idOrdenSalida);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenSalida ordenSalidaAplicar = ordenSalidaEAO.findById(idOrdenSalida);

            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //Validar datos generales de la Orden de Traslado
            if (ordenSalidaAplicar.getEstado().getAlias().equals(EstadosMovimiento.ANULADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de de Baja no se encuentra en un estado valido para poder imprimir.");
        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }


    @Override
    public void validarImpresionOrdenLevantamiento(Integer idOrdenLevantamiento) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Validar Impresión de Orden de Levantamiento de Inventario: [idOrdenLevantamiento]: " + idOrdenLevantamiento);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenLevantamientoFisico ordenLevantamitoFisicoAplicar = ordenLevantamientoFisicoEAO.findById(idOrdenLevantamiento);

            EstadoMovimiento estadoAplicado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.APLICADO.getEstado());

            //Validar datos generales de la Orden de Traslado
            if (!ordenLevantamitoFisicoAplicar.getEstado().getAlias().equals(EstadosMovimiento.APLICADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Levantamiento de Inventario no se encuentra en un estado valido para poder imprimir.");


        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void validarImpresionOrdenTraslado(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Validar Impresión de Orden de Traslado: [idOrdenTraslado]: " + idOrdenTraslado);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenTraslado ordenTrasladoAplicar = ordenTrasladoEAO.findById(idOrdenTraslado);

            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //Validar datos generales de la Orden de Traslado
            if (ordenTrasladoAplicar.getEstado().getAlias().equals(EstadosMovimiento.ANULADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Traslado no se encuentra en un estado valido para poder imprimir.");


        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void aplicarTraslado(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Cambiar Estado Orden de Traslado a INGRESADA con parametros: [idOrdenTraslado]: " + idOrdenTraslado);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenTraslado ordenTrasladoAplicar = ordenTrasladoEAO.findById(idOrdenTraslado);

            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //Validar datos generales de la Orden de Traslado
            if (!ordenTrasladoAplicar.getEstado().getAlias().equals(EstadosMovimiento.PENDIENTE.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Traslado no se encuentra en un estado valido para poder Aplicar.");

            //Eliminar movimientos de inventario de los articulos
            for (ArticuloTraslado articulo : ordenTrasladoAplicar.getArticulos()) {
                for (MovimientoInventario movimiento : articulo.getMovimientosInventario()) {
                    movimiento.setEstado(estadoIngresado);
                 }

               }

            //Setting Estado Movimiento Impreso
            ordenTrasladoAplicar.setEstado(estadoIngresado);

            //Update OrdenTraslado
            ordenTrasladoEAO.update(ordenTrasladoAplicar);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public OrdenTraslado modificarOrdenTraslado(Integer idOrdenTraslado, Date fechaAlta, Date fechaSolicitud,
                                                String personaEntrega, String personaRecibe, String descripcion,
                                                List<ArticuloTraslado> articulos) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Modificando orden de traslado con parametros: [idOrdenTraslado]: " + idOrdenTraslado + ", [fechaAlta]: " +
                fechaAlta + ", [fechaSolicitud]: " + fechaSolicitud + ", [personaEntrega]: " + personaEntrega + ", [personaRecibe]: " +
                personaRecibe + ", [descripcion]: " + descripcion);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar contexto de ejecucion
            OrdenTraslado ordenTraslado = ordenTrasladoEAO.findById(idOrdenTraslado);

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior al dia de hoy.");

            if (personaEntrega.equals(""))
                throw new ManagerInventarioServiceBusinessException("Debes registrar a una persona que entrega.");

            if (personaRecibe.equals(""))
                throw new ManagerInventarioServiceBusinessException("Debes registrar a una persona que recibe.");

            if (descripcion.equals(""))
                throw new ManagerInventarioServiceBusinessException("Debes registrar una descripción para este traslado.");

            if (articulos.isEmpty())
                throw new ManagerInventarioServiceBusinessException("Debes ingresar al menos un articulo.");

            //Modificar orden de traslado
            ordenTraslado.setFechaAlta(fechaAlta);
            ordenTraslado.setPersonaEntrega(personaEntrega);
            ordenTraslado.setPersonaRecibe(personaRecibe);
            ordenTraslado.setDescripcion(descripcion);

            //Crear movimientos de inventario
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obtener articulo traslado
                ArticuloTraslado articulo = (ArticuloTraslado) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Creating movimientos de inventario de salida y entrada

                    //<Movimiento de Salida de inventario>
                    MovimientoInventario movimientoSalida = new MovimientoInventario();
                    movimientoSalida.setFechaAlta(fechaAlta);
                    movimientoSalida.setAfectacion(new Integer(-1).shortValue());
                    movimientoSalida.setCantidad(articulo.getCantidad());
                    movimientoSalida.setAlmacen(ordenTraslado.getAlmacenSalida());
                    movimientoSalida.setArticuloTraslado(articulo);
                    movimientoSalida.setEstado(ordenTraslado.getEstado());
                    movimientoSalida.setProducto(articulo.getProducto());

                    //<Movimiento de ingreso de inventario>
                    MovimientoInventario movimientoIngreso = new MovimientoInventario();
                    movimientoIngreso.setFechaAlta(fechaAlta);
                    movimientoIngreso.setAfectacion(new Integer(1).shortValue());
                    movimientoIngreso.setCantidad(articulo.getCantidad());
                    movimientoIngreso.setAlmacen(ordenTraslado.getAlmacenEntrada());
                    movimientoIngreso.setArticuloTraslado(articulo);
                    movimientoIngreso.setEstado(ordenTraslado.getEstado());
                    movimientoIngreso.setProducto(articulo.getProducto());

                    Set<MovimientoInventario> setMovimientos = new HashSet<MovimientoInventario>();
                    setMovimientos.add(movimientoSalida);
                    setMovimientos.add(movimientoIngreso);

                    articulo.setMovimientosInventario(setMovimientos);
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {

                        if (articulo.getId() != null)
                            articuloTrasladoEAO.remove(articulo.getId());

                        it.remove();
                        continue;
                    }

                    if (articulo.getMovimientosInventario() == null || articulo.getMovimientosInventario().isEmpty()) {

                        //Creating movimientos de inventario de salida y entrada

                        //<Movimiento de Salida de inventario>
                        MovimientoInventario movimientoSalida = new MovimientoInventario();
                        movimientoSalida.setFechaAlta(fechaAlta);
                        movimientoSalida.setAfectacion(new Integer(-1).shortValue());
                        movimientoSalida.setCantidad(articulo.getCantidad());
                        movimientoSalida.setAlmacen(ordenTraslado.getAlmacenSalida());
                        movimientoSalida.setArticuloTraslado(articulo);
                        movimientoSalida.setEstado(ordenTraslado.getEstado());
                        movimientoSalida.setProducto(articulo.getProducto());

                        //<Movimiento de ingreso de inventario>
                        MovimientoInventario movimientoIngreso = new MovimientoInventario();
                        movimientoIngreso.setFechaAlta(fechaAlta);
                        movimientoIngreso.setAfectacion(new Integer(1).shortValue());
                        movimientoIngreso.setCantidad(articulo.getCantidad());
                        movimientoIngreso.setAlmacen(ordenTraslado.getAlmacenEntrada());
                        movimientoIngreso.setArticuloTraslado(articulo);
                        movimientoIngreso.setEstado(ordenTraslado.getEstado());
                        movimientoIngreso.setProducto(articulo.getProducto());

                        Set<MovimientoInventario> setMovimientos = new HashSet<MovimientoInventario>();
                        setMovimientos.add(movimientoSalida);
                        setMovimientos.add(movimientoIngreso);

                        articulo.setMovimientosInventario(setMovimientos);
                    } else {

                        //Actulizar cantidad movimiento inventario
                        for (MovimientoInventario movimientoInventario : articulo.getMovimientosInventario()) {
                            movimientoInventario.setCantidad(articulo.getCantidad());
                        }
                    }
                }

                //Setting valor del renglon
                articulo.setRenglon(renglon);

                //Setting orden de traslado
                articulo.setOrdenTraslado(ordenTraslado);
                articulo.setNoDocumento(ordenTraslado.getNoMovimiento());

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Actualizar renglon
                renglon += 1;
            }

            //Setting monto total inventario
            ordenTraslado.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenTraslado.setArticulos(new HashSet<ArticuloTraslado>(articulos));

            //actualizar articulos de orden de traslado
            return ordenTrasladoEAO.update(ordenTraslado);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public void anularOrdenTraslado(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Anular orden de traslado con parametros: [idOrdenTraslado]: " + idOrdenTraslado);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar contexto de ejecucion
            OrdenTraslado ordenTraslado = ordenTrasladoEAO.findById(idOrdenTraslado);
            EstadoMovimiento estadoAnulado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.ANULADO.getEstado());

            //Validar estado INGRESADO
            if (!ordenTraslado.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Traslado se encuentra en un estado no válido. " +
                        "No se puede anular.");

            //Eliminar movimientos de inventario de los articulos
            for (ArticuloTraslado articulo : ordenTraslado.getArticulos()) {
                for (MovimientoInventario movimiento : articulo.getMovimientosInventario()) {
                    movimientoInventarioEAO.remove(movimiento.getId());
                }

                //Setting movimientos de inventario
                articulo.setMovimientosInventario(null);
            }

            //Actualizar orden de traslado
            ordenTraslado.setEstado(estadoAnulado);

            ordenTrasladoEAO.update(ordenTraslado);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenSalida crearOrdenSalida(Date fechaAlta, Date fechaSolicitud, Integer idAlmacenSalida, String personaAutoriza,
                                        String descripcion, List<ArticuloSalida> articulos) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Crear orden de salida con parametros: [fechaAlta]: " + fechaAlta + ", [fechaSolicitud]: " + fechaSolicitud +
                ", [idAlmacenSalida]: " + idAlmacenSalida + ", [personaAutoriza]: " + personaAutoriza + ", [descripcion]: " +
                descripcion);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto
            Almacen almacenSalida = almacenEAO.findById(idAlmacenSalida);
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //Obtener numero consecutivo de salida
            long noSalida = ordenSalidaEAO.obtenerNoSalida();

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior al dia de hoy.");

            if (personaAutoriza.equals(""))
                throw new ManagerInventarioServiceBusinessException("PERSONA AUTORIZA es un dato requerido.");

            if (descripcion.equals(""))
                throw new ManagerInventarioServiceBusinessException("DESCRIPCION es un dato requerido.");

            if (articulos == null || articulos.isEmpty())
                throw new ManagerInventarioServiceBusinessException("ARTICULOS DE SALIDA deben contener al menos un registro.");

            //Crear encabezado de orden de salida y persistirlo
            OrdenSalida ordenSalida = new OrdenSalida();
            ordenSalida.setNoMovimiento(noSalida);
            ordenSalida.setFechaAlta(fechaAlta);
            ordenSalida.setDescripcion(descripcion);
            ordenSalida.setPersonaAutoriza(personaAutoriza);
            ordenSalida.setAlmacen(almacenSalida);
            ordenSalida.setEstado(estado);
            ordenSalida.setMoneda(almacenSalida.getCompania().getMonedaReferencia());
            ordenSalida.setMontoTotal(new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_EVEN));

            //Crear detalles de orden de salida
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obtener articulo salida
                ArticuloSalida articulo = (ArticuloSalida) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Creando movimiento de inventario por articulo
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setAlmacen(ordenSalida.getAlmacen());
                    movimientoInventario.setFechaAlta(ordenSalida.getFechaAlta());
                    movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidad());
                    movimientoInventario.setEstado(estado);
                    movimientoInventario.setArticuloSalida(articulo);
                    movimientoInventario.setProducto(articulo.getProducto());

                    articulo.setMovimientoInventario(movimientoInventario);
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    if (articulo.getMovimientoInventario() == null) {

                        //Creando movimiento de inventario por articulo
                        MovimientoInventario movimientoInventario = new MovimientoInventario();
                        movimientoInventario.setAlmacen(ordenSalida.getAlmacen());
                        movimientoInventario.setFechaAlta(ordenSalida.getFechaAlta());
                        movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                        movimientoInventario.setCantidad(articulo.getCantidad());
                        movimientoInventario.setEstado(estado);
                        movimientoInventario.setArticuloSalida(articulo);
                        movimientoInventario.setProducto(articulo.getProducto());

                        articulo.setMovimientoInventario(movimientoInventario);
                    } else {
                        //Actulizar cantidad movimiento inventario
                        articulo.getMovimientoInventario().setCantidad(articulo.getCantidad());
                    }
                }

                //Setting renglon articulo
                articulo.setRenglon(renglon);

                //Setting orden de salida
                articulo.setOrdenSalida(ordenSalida);
                articulo.setNoDocumento(ordenSalida.getNoMovimiento());

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Calculando el costo total por item
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();

                //Actualizar renglon
                renglon += 1;
            }

            //Setting monto total de inventario
            ordenSalida.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenSalida.setArticulos(new HashSet<ArticuloSalida>(articulos));

            //Actualizar orden de traslado
            return ordenSalidaEAO.create(ordenSalida);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenSalida modificarOrdenSalida(Integer idOrdenSalida, Date fechaAlta, Date fechaSolicitud, String personaAutoriza,
                                            String descripcion, List<ArticuloSalida> articulos) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Modificar orden de salida con parametros: [idOrdenSalida]: " + idOrdenSalida + ", [fechaAlta]: " + fechaAlta +
                ", [fechaSolicitud]: " + fechaSolicitud + ", [personaAutoriza]: " + personaAutoriza + ", [descripcion]: " +
                descripcion);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto
            OrdenSalida ordenSalida = ordenSalidaEAO.findById(idOrdenSalida);

            //Validar consistencia de datos
            if (!ordenSalida.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Salida se encuentra en un estado no válido. No se puede modificar.");

            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior al dia de hoy.");

            if (personaAutoriza.equals(""))
                throw new ManagerInventarioServiceBusinessException("PERSONA AUTORIZA es un dato requerido.");

            if (descripcion.equals(""))
                throw new ManagerInventarioServiceBusinessException("DESCRIPCION es un dato requerido.");

            if (articulos == null || articulos.isEmpty())
                throw new ManagerInventarioServiceBusinessException("ARTICULOS DE SALIDA deben contener al menos un registro.");

            //Calcular nuevo costo neto de salida de inventario
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obtener articulos de salida
                ArticuloSalida articulo = (ArticuloSalida) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Creando movimiento de inventario por articulo
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setAlmacen(ordenSalida.getAlmacen());
                    movimientoInventario.setFechaAlta(ordenSalida.getFechaAlta());
                    movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidad());
                    movimientoInventario.setEstado(ordenSalida.getEstado());
                    movimientoInventario.setArticuloSalida(articulo);
                    movimientoInventario.setProducto(articulo.getProducto());

                    articulo.setMovimientoInventario(movimientoInventario);
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {

                        if (articulo.getId() != null)
                            articuloSalidaEAO.remove(articulo.getId());

                        it.remove();
                        continue;
                    }

                    if (articulo.getMovimientoInventario() == null) {

                        //Creando movimiento de inventario por articulo
                        MovimientoInventario movimientoInventario = new MovimientoInventario();
                        movimientoInventario.setAlmacen(ordenSalida.getAlmacen());
                        movimientoInventario.setFechaAlta(ordenSalida.getFechaAlta());
                        movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                        movimientoInventario.setCantidad(articulo.getCantidad());
                        movimientoInventario.setEstado(ordenSalida.getEstado());
                        movimientoInventario.setArticuloSalida(articulo);
                        movimientoInventario.setProducto(articulo.getProducto());

                        articulo.setMovimientoInventario(movimientoInventario);
                    } else {
                        //Actulizar cantidad movimiento inventario
                        articulo.getMovimientoInventario().setCantidad(articulo.getCantidad());
                    }
                }

                //Setting orden de salida
                articulo.setOrdenSalida(ordenSalida);
                articulo.setNoDocumento(ordenSalida.getNoMovimiento());

                //Setting renglon articulo
                articulo.setRenglon(renglon);
                articulo.setNoDocumento(ordenSalida.getNoMovimiento());

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Calculando el costo total por item
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();

                //Actualizar renglon
                renglon += 1;
            }

            //Setting monto total de inventario
            ordenSalida.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenSalida.setArticulos(new HashSet<ArticuloSalida>(articulos));

            return ordenSalidaEAO.update(ordenSalida);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenSalida anularOrdenSalida(Integer idOrdenSalida) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Anulando orden de salida con parametros: [idOrdenSalida]: " + idOrdenSalida);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto
            OrdenSalida ordenSalida = ordenSalidaEAO.findById(idOrdenSalida);
            EstadoMovimiento estadoAnulado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.ANULADO.getEstado());

            //Validar estado para anular
            if (!ordenSalida.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Salida se encuentra en un estado no válido. No se puede anular");


            //Eliminar movimientos de inventario
            for (ArticuloSalida articulo : ordenSalida.getArticulos()) {
                movimientoInventarioEAO.remove(articulo.getMovimientoInventario().getId());
                articulo.setMovimientoInventario(null);
            }

            //Actualizar orden de salida
            ordenSalida.setEstado(estadoAnulado);

            return ordenSalidaEAO.update(ordenSalida);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenLevantamientoFisico crearOrdenLevantamientoFisico(Date fechaAlta, Date fechaSolicitud, Integer idAlmacenIngreso,
                                                                  String descripcion, List<ArticuloLevantamientoFisico> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Creando orden de levantamiento fisico con parametros: [fechaAlta]: " + fechaAlta + ", [fechaSolicitud]:" +
                fechaSolicitud + ", [idAlmacenIngreso]: " + idAlmacenIngreso + ", [descripcion]: " + descripcion);


        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //1. PREPARAR EL CONTEXTO DE EJECUCION
            long noMovimiento = ordenLevantamientoFisicoEAO.obtenerNoLevantamiento();
            Almacen almacen = almacenEAO.findById(idAlmacenIngreso);
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //2. VALIDAR DATOS DE LA ORDEN
            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior a la fecha actual.");

            if (descripcion.equals(""))
                throw new ManagerInventarioServiceBusinessException("DESCRIPCION es un dato requerido.");

            if (articulos == null || articulos.size() <= 0)
                throw new ManagerInventarioServiceBusinessException("ARTICULOS en levantamiento físico deben contener al menos un registro.");


            //3. CREAR LEVANTAMIENTO INVENTARIO
            OrdenLevantamientoFisico ordenLevantamiento = new OrdenLevantamientoFisico();
            ordenLevantamiento.setNoMovimiento(noMovimiento);
            ordenLevantamiento.setDescripcion(descripcion);
            ordenLevantamiento.setTipoActualizacion(TipoActualizacionFisico.PARCIAL.getValue());
            ordenLevantamiento.setFechaAlta(fechaAlta);
            ordenLevantamiento.setAlmacen(almacen);
            ordenLevantamiento.setFechaSolicitud(fechaSolicitud);
            ordenLevantamiento.setMoneda(almacen.getCompania().getMonedaReferencia());
            ordenLevantamiento.setMontoTotal(new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenLevantamiento.setEstado(estado);

            //4. CREAR DETALLE LEVANTAMIENTO FISICO
            double costoNeto = 0.0;
            long renglon = 1;

            for (ArticuloLevantamientoFisico articulo : articulos) {

                //Borrar articulos con cantidad menor o igual a zero
                /*if (articulo.getCantidad() <= 0) {
                    articulos.remove(articulo);
                    continue;
                }*/
                //Se quitó esta validación el 22/02/2014 para que aceptara Productos con cantidad 0

                //Setting orden de levantamiento
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();
                articulo.setOrdenLevantamientoFisico(ordenLevantamiento);
                articulo.setNoMovimiento(ordenLevantamiento.getNoMovimiento());
                articulo.setCantidadAjuste(0);
                articulo.setMontoAjuste(new BigDecimal("0.00"));

                articulo.setRenglon(renglon);
                articulo.setNoMovimiento(noMovimiento);

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Actualizar renglon
                renglon += 1;
            }

            //5. ACTUALIZAR DETALLE Y DATOS DE ENCABEZADO
            ordenLevantamiento.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenLevantamiento.setMontoTotalAjuste(new BigDecimal("0.0000"));
            ordenLevantamiento.setArticulos(new HashSet<ArticuloLevantamientoFisico>(articulos));

            //Persistir encabezado de levantamiento fisico
            ordenLevantamiento = ordenLevantamientoFisicoEAO.create(ordenLevantamiento);

            return ordenLevantamiento;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenLevantamientoFisico modificarOrdenLevantamientoFisico(Integer idOrdenLevantamiento, Date fechaAlta,
                                                                      Date fechaSolicitud, String descripcion,
                                                                      List<ArticuloLevantamientoFisico> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Modificar orden de lavantamiento fisico con parametros: [idOrdenLevantamiento]: " + idOrdenLevantamiento +
                ", [fechaAlta]: " + fechaAlta + ", [fechaSolicitud]: " + fechaSolicitud + ", [descripcion]: " + descripcion);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //1. PREPARAR EL CONTEXTO DE EJECUCION
            OrdenLevantamientoFisico ordenLevantamiento = ordenLevantamientoFisicoEAO.findById(idOrdenLevantamiento);

            //2. VALIDAR DATOS DE LA ORDEN
            if (!ordenLevantamiento.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden Levantamiento Físico se encuentra en un estado no válido. " +
                        "no se puede proceder a la modificación.");

            if (fechaAlta.after(new Date()))
                throw new ManagerInventarioServiceBusinessException("FECHA ALTA no puede ser posterior a la fecha actual.");

            if (descripcion.equals(""))
                throw new ManagerInventarioServiceBusinessException("DESCRIPCION es un dato requerido.");

            if (articulos == null || articulos.size() <= 0)
                throw new ManagerInventarioServiceBusinessException("ARTICULOS en levantamiento físico deben contener al menos un registro.");

            //3. MODIFICAR DETALLE LEVANTAMIENTO FISICO
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                ArticuloLevantamientoFisico articulo = (ArticuloLevantamientoFisico) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {

                        //Remover el articulo si su Id es distinto de nulo
                        if (articulo.getId() != null)
                            articuloLevantamientoFisicoEAO.remove(articulo.getId());

                        it.remove();
                        continue;
                    }

                }

                //Setting orden de entrada
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();
                articulo.setOrdenLevantamientoFisico(ordenLevantamiento);
                articulo.setNoMovimiento(ordenLevantamiento.getNoMovimiento());
                articulo.setCantidadAjuste(0);
                articulo.setMontoAjuste(new BigDecimal("0.00"));

                articulo.setRenglon(renglon);
                articulo.setNoMovimiento(ordenLevantamiento.getNoMovimiento());

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Actualizar renglon
                renglon += 1;
            }

            //4. ACTUALIZAR DATOS LEVANTAMIENTO FISICO Y PERSISTIR
            ordenLevantamiento.setFechaAlta(fechaAlta);
            ordenLevantamiento.setFechaSolicitud(fechaSolicitud);
            ordenLevantamiento.setDescripcion(descripcion);
            ordenLevantamiento.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenLevantamiento.setMontoTotalAjuste(new BigDecimal("0.00"));
            ordenLevantamiento.setArticulos(new HashSet<ArticuloLevantamientoFisico>(articulos));

            //Actualizar orden levantamiento fisico
            ordenLevantamiento = ordenLevantamientoFisicoEAO.update(ordenLevantamiento);

            return ordenLevantamiento;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenLevantamientoFisico anularOrdenLevantamientoFisico(Integer idOrdenLevantamientoFisico) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Anular orden de levantamiento fisico con parametros: [idOrdenLevantamiento]: " + idOrdenLevantamientoFisico);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //1. PREPARAR CONTEXTO DE EJECUCION
            OrdenLevantamientoFisico ordenLevantamientoFisico = ordenLevantamientoFisicoEAO.findById(idOrdenLevantamientoFisico);
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.ANULADO.getEstado());

            //2. VALIDAR DATOS DE LA ORDEN
            if (!ordenLevantamientoFisico.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()) ||
                    !ordenLevantamientoFisico.getEstado().getAlias().equals(EstadosMovimiento.APLICADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden Levantamiento F\u00edsico se encuentra en un estado no v\u00e1lido. " +
                        "no se puede proceder a la anulaci\u00f3n.");

            //3. ELIMINAR REGISTROS DE MOVIMIENTOS DE INVENTARIO DE LA ORDEN
            if (ordenLevantamientoFisico.getEstado().getAlias().equals(EstadosMovimiento.APLICADO.getEstado())) {

                for (ArticuloLevantamientoFisico articulo : ordenLevantamientoFisico.getArticulos()) {
                    movimientoInventarioEAO.remove(articulo.getMovimientoInventario().getId());
                    articulo.setMovimientoInventario(null);
                }
            }

            //4. ACTUALIZAR ESTADO DE LA ORDEN LEVANTAMIENTO FISICO
            ordenLevantamientoFisico.setEstado(estado);

            return ordenLevantamientoFisicoEAO.update(ordenLevantamientoFisico);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public void eliminarOrdenLevantamientoFisico(Integer idOrdenLevantamientoFisico) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Eliminar orden de levantamiento fisico con parametros: [idOrdenLevantamiento]: " + idOrdenLevantamientoFisico);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //1. PREPARAR CONTEXTO DE EJECUCION
            OrdenLevantamientoFisico ordenLevantamientoFisico = ordenLevantamientoFisicoEAO.findById(idOrdenLevantamientoFisico);

            //2. VALIDAR DATOS DE LA ORDEN
            if (!ordenLevantamientoFisico.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden Levantamiento F\u00edsico se encuentra en un estado no v\u00e1lido. " +
                        "no se puede proceder a la eliminaci\u00f3n.");

            //3. BORRAR ORDEN DE LEVANTAMIENTO FISICO
            ordenLevantamientoFisicoEAO.remove(idOrdenLevantamientoFisico);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenLevantamientoFisico aplicarAjusteLevantamientoInventario(Integer idOrdenLevantamiento, int tipoAjuste)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Aplicar ajuste levantamiento inventario con parametros: [id]: " + idOrdenLevantamiento);

        //Iniciar servicio de autenticacion
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //TODO: Implementar actualizacion por tipo de ajuste - Pendiente en la ejecucion de este metodo solo PARCIAL.

            //Preparar el contexto de ejecucion
            OrdenLevantamientoFisico ordenLevantamientoFisico = ordenLevantamientoFisicoEAO.findById(idOrdenLevantamiento);
            EstadoMovimiento estadoAplicado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.APLICADO.getEstado());

            //Evaluar si se encuentra en estado calculado
            if (!ordenLevantamientoFisico.getEstado().getAlias().equals(EstadosMovimiento.CALCULADO.getEstado())) {
                throw new ManagerInventarioServiceBusinessException("Orden de Levantamiento F\u00edsico se encuentra en un " +
                        "estado no v\u00e1lido. No se puede aplicar ajuste.");
            }

            //Evaluar si la fecha de alta no es posterior a la fecha actual del sistema
            if (ordenLevantamientoFisico.getFechaAlta().after(new Date()))
                throw new ManagerInventarioServiceBusinessException("Orden de Levantamiento F\u00edsico tiene fecha de alta" +
                        " posterior a la fecha actual del sistema. No se puede aplicar ajuste.");

            //Monto total ajuste
            BigDecimal montoTotalAjuste = new BigDecimal("0.00");

            //Evaluar ajuste de existencias por articulo ingresado
            for (ArticuloLevantamientoFisico articulo : ordenLevantamientoFisico.getArticulos()) {

                //Actualizar monto ajuste
                montoTotalAjuste.add(articulo.getMontoAjuste());

                if (articulo.getCantidadAjuste() != 0) {

                    MovimientoInventario movimientoInventario = null;

                    if (articulo.getMovimientoInventario() != null) {
                        movimientoInventario = articulo.getMovimientoInventario();
                    } else {
                        movimientoInventario = new MovimientoInventario();
                    }

                    //Creating Movimiento inventario
                    movimientoInventario.setAlmacen(ordenLevantamientoFisico.getAlmacen());
                    movimientoInventario.setArticuloLevantamientoFisico(articulo);
                    movimientoInventario.setEstado(estadoAplicado);
                    movimientoInventario.setFechaAlta(ordenLevantamientoFisico.getFechaAlta());
                    movimientoInventario.setAfectacion(articulo.getCantidadAjuste() < 0 ? new Integer(-1).shortValue() : new Integer(1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidadAjuste() * movimientoInventario.getAfectacion());
                    movimientoInventario.setProducto(articulo.getProducto());


                    //Setting movimiento inventario al articulo
                    articulo.setMovimientoInventario(movimientoInventario);
                }
            }

            //Actualizar costo ajuste movimiento inventario
            ordenLevantamientoFisico.setMontoTotalAjuste(montoTotalAjuste);

            //Actualizar estado de la orden de entrada de recuento fisico APLICADO!
            ordenLevantamientoFisico.setEstado(estadoAplicado);

            //Persistir orden de entrada
            return ordenLevantamientoFisicoEAO.update(ordenLevantamientoFisico);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public OrdenLevantamientoFisico calcularAjusteLevantamientoInventario(Integer idOrdenLevantamiento) throws ManagerInventarioServiceBusinessException,
            RemoteException {

        logger.debug("Calcular ajuste levantamiento inventario con parametros: [idOrdenLevantamiento]: " + idOrdenLevantamiento);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            OrdenLevantamientoFisico ordenLevantamientoFisico = ordenLevantamientoFisicoEAO.findById(idOrdenLevantamiento);
            EstadoMovimiento estadoCalculado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.CALCULADO.getEstado());
            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            EstadoMovimiento estadoAplicado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.APLICADO.getEstado());

            //Evaluar si se encuentra en estado ingresado
            if (!ordenLevantamientoFisico.getEstado().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerInventarioServiceBusinessException("Orden de Levantamiento F\u00edsico se encuentra en un " +
                        "estado no v\u00e1lido. No se puede aplicar ajuste.");

            //Evaluar si la fecha de alta no es posterior a la fecha actual del sistema
            if (ordenLevantamientoFisico.getFechaAlta().after(new Date()))
                throw new ManagerInventarioServiceBusinessException("Orden de Levantamiento F\u00edsico tiene fecha de alta" +
                        " posterior a la fecha actual del sistema. No se puede calcular ajuste.");

            //Monto total ajuste
            BigDecimal montoTotalAjuste = new BigDecimal("0.00");

            //Evaluar ajuste de existencias por articulo ingresado
            for (ArticuloLevantamientoFisico articulo : ordenLevantamientoFisico.getArticulos()) {

                //**************************************************************************
                //1. Obtener existencias para el almacen procesado en la orden de entrada
                //**************************************************************************
                long cantExistencia = 0;

                for (ProductoExistencia productoExistencia : articulo.getProducto().getExistencias()) {

                    if (productoExistencia.getId().getAlmacen().getId().longValue() == ordenLevantamientoFisico.
                            getAlmacen().getId().longValue())
                        cantExistencia = productoExistencia.getCantidad();
                }

                //**************************************************************************
                //2. Obtener movimientos registrados hasta la fecha de alta inventario fisico
                //**************************************************************************

                //Buscar movimientos inventario en estado INGRESADO
                List<MovimientoInventario> movimientos = movimientoInventarioEAO.findByProducto(articulo.getCodigo(),
                        ordenLevantamientoFisico.getAlmacen().getId(), estadoIngresado.getId(), ordenLevantamientoFisico.getFechaAlta());

                //Buscar movimientos inventario en estado APLICADO
                movimientos.addAll(movimientoInventarioEAO.findByProducto(articulo.getCodigo(), ordenLevantamientoFisico.getAlmacen().getId(),
                        estadoAplicado.getId(), ordenLevantamientoFisico.getFechaAlta()));

                //**************************************************************************
                //3. Calcular cantidad en existencia actual
                //**************************************************************************
                for (MovimientoInventario movimiento : movimientos) {
                    cantExistencia += (movimiento.getAfectacion() * movimiento.getCantidad());
                }

                //**************************************************************************
                //4. Evaluar existencia fisica y crear movimiento de ajuste
                //**************************************************************************
                long cantAjuste = articulo.getCantidad() - cantExistencia;
                BigDecimal costoAjuste = articulo.getCosto().multiply(new BigDecimal(cantAjuste));

                //Setting articulo cantidad de ajuste en inventario fisico
                articulo.setCantidadExistencia(cantExistencia);
                articulo.setCantidadAjuste(cantAjuste);
                articulo.setMontoAjuste(costoAjuste);

                //Actualizar monto total ajuste
                montoTotalAjuste = montoTotalAjuste.add(articulo.getMontoAjuste());
            }

            //Actualizar monto total ajuste levantamiento inventario
            ordenLevantamientoFisico.setMontoTotalAjuste(montoTotalAjuste);
            ordenLevantamientoFisico.setEstado(estadoCalculado);

            return ordenLevantamientoFisicoEAO.update(ordenLevantamientoFisico);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<OrdenLevantamientoFisico> buscarOrdenesLevantamientoFisicoPorEstados(List<String> estados)
            throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar ordenes de levantamiento fisico por estados");

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Preparar el contexto
            List<Integer> idEstados = new ArrayList<Integer>();

            for (String estado : estados) {
                idEstados.add(estadoMovimientoEAO.findByAlias(estado).getId());
            }

            return ordenLevantamientoFisicoEAO.findByEstados(idEstados);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }

    }

    @Override
    public List<MovimientoInventario> buscarMovimientosInventario(String codigoProducto, Integer idAlmacen, Date fechaDesde,
                                                                  Date fechaHasta) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Buscar movimientos inventario con parametros: [idAlmacen]: " + idAlmacen + ", [fechaDesde]: " +
                fechaDesde + ", [fechaHasta]: " + fechaHasta);

        //Iniciar servicio authenticatin
        boolean value = initBusinessService(Roles.ROLINVENTARIOADMIN.toString());

        try {

            //Validando parametros para evaluar
            GregorianCalendar calendario = new GregorianCalendar();
            calendario.setTime(new Date());

            if (fechaDesde == null) {
                calendario.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.getActualMinimum(Calendar.DAY_OF_MONTH),
                        0, 0, 0);
                fechaDesde = calendario.getTime();
            }

            if (fechaHasta == null) {
                calendario.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.getActualMaximum(Calendar.DAY_OF_MONTH),
                        0, 0, 0);
                fechaHasta = calendario.getTime();
            }

            //Obteniendo movimientos con tipos de afectacion
            List<MovimientoInventario> movimientosInventario = new ArrayList<MovimientoInventario>();
            movimientosInventario.addAll(movimientoInventarioEAO.findByProducto(codigoProducto, idAlmacen, fechaDesde,
                    fechaHasta, TiposAfectacion.POSITIVA.getValue()));
            movimientosInventario.addAll(movimientoInventarioEAO.findByProducto(codigoProducto, idAlmacen, fechaDesde,
                    fechaHasta, TiposAfectacion.NEGATIVA.getValue()));

            //Initialize values to process
            for (MovimientoInventario movInventario : movimientosInventario) {
                Hibernate.initialize(movInventario.getAlmacen());
                Hibernate.initialize(movInventario.getProducto());
                Hibernate.initialize(movInventario.getArticuloEntrada());
                Hibernate.initialize(movInventario.getArticuloSalida());
                Hibernate.initialize(movInventario.getArticuloTraslado());
                Hibernate.initialize(movInventario.getArticuloLevantamientoFisico());
                Hibernate.initialize(movInventario.getArticuloFactura());
                Hibernate.initialize(movInventario.getEstado());
            }

            //Ordenar movimientos de inventario por fecha de alta
            Collections.sort(movimientosInventario, TiposOrdenamientoMovimientoInventario.PorFechaAlta);

            return movimientosInventario;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public void cerrarInventarioMensual(Date fechaDesde, Date fechaHasta) throws ManagerInventarioServiceBusinessException, RemoteException {

        logger.debug("Cerrar inventario mensual con parametros: [fechaDesde]: " + fechaDesde + ", [fechaHasta]: " + fechaHasta);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

    }

    @Override
    public void calcularMaximosMinimos() throws ManagerInventarioServiceBusinessException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //**************************************************************************************
    //PRIVATE BUSINESS LOGIC
    //**************************************************************************************

    /**
     * Crear orden de entrada ordinaria producto de un ingreso directo
     *
     * @param fechaAlta,      Fecha de alta del movimiento de ingreso
     * @param almacen,        Almacen de ingreso
     * @param personaEntrega, Persona que realiza entrega
     * @param articulos,      Listado de articulos a ingresar
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *          Exception
     */
    private OrdenEntrada crearOrdenEntradaOrdinaria(Date fechaAlta, Almacen almacen, Moneda moneda, String personaEntrega, String descripcion,
                                                    List<ArticuloEntrada> articulos) throws ManagerInventarioServiceBusinessException {

        logger.debug("Creando orden de entrada ordinaria con parametros: [FechaAlta]:" + fechaAlta);

        try {

            //1. OBTENER NUMERO DE ORDEN CONSECUTIVO
            long noOrden = ordenEntradaEAO.obtenerNoEntrada();
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //2. PERSISTIR ORDEN DE ENTRADA
            OrdenEntrada ordenEntrada = new OrdenEntrada();
            ordenEntrada.setTipoEntrada(TiposEntrada.ENTRADA_ORDINARIA.getValue());
            ordenEntrada.setNoMovimiento(noOrden);
            ordenEntrada.setAlmacen(almacen);
            ordenEntrada.setFechaAlta(fechaAlta);
            ordenEntrada.setMoneda(moneda);
            ordenEntrada.setEstado(estado);
            ordenEntrada.setDescripcion(descripcion);
            ordenEntrada.setPersonaEntrega(personaEntrega);
            ordenEntrada.setMontoTotal(new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_EVEN));

            //3. PERSISTIR ARTICULOS ENTRADA
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obteniendo articulo entrada
                ArticuloEntrada articulo = (ArticuloEntrada) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Creando movimiento de inventario por articulo
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setAlmacen(ordenEntrada.getAlmacen());
                    movimientoInventario.setFechaAlta(ordenEntrada.getFechaAlta());
                    movimientoInventario.setAfectacion(new Integer(1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidad());
                    movimientoInventario.setEstado(estado);
                    movimientoInventario.setArticuloEntrada(articulo);
                    movimientoInventario.setProducto(articulo.getProducto());

                    articulo.setMovimientoInventario(movimientoInventario);
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Actualizar cantidad movimiento inventario
                    if (articulo.getMovimientoInventario() == null) {

                        //Creando movimiento de inventario por articulo
                        MovimientoInventario movimientoInventario = new MovimientoInventario();
                        movimientoInventario.setAlmacen(ordenEntrada.getAlmacen());
                        movimientoInventario.setFechaAlta(ordenEntrada.getFechaAlta());
                        movimientoInventario.setAfectacion(new Integer(1).shortValue());
                        movimientoInventario.setCantidad(articulo.getCantidad());
                        movimientoInventario.setEstado(estado);
                        movimientoInventario.setArticuloEntrada(articulo);
                        movimientoInventario.setProducto(articulo.getProducto());

                        articulo.setMovimientoInventario(movimientoInventario);
                    } else {
                        articulo.getMovimientoInventario().setCantidad(articulo.getCantidad());
                    }
                }

                //Calculando el costo total por item
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();

                //Setting orden de entrada en el articulo
                articulo.setOrdenEntrada(ordenEntrada);
                articulo.setRenglon(renglon);
                articulo.setNoDocumento(noOrden);

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Actualizar renglon
                renglon += 1;
            }

            //<Persist articulos>
            ordenEntrada.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenEntrada.setArticulos(new HashSet<ArticuloEntrada>(articulos));

            //Crear orden de entrada
            ordenEntrada = ordenEntradaEAO.create(ordenEntrada);

            //Calcular costo promedio ponderado
            calcularCostoPromedioPonderado(ordenEntrada);

            return ordenEntrada;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    /**
     * @param fechaAlta,      Fecha de alta orden de entrada
     * @param almacen,        Almacen de ingreso orden de entrada
     * @param personaEntrega, Persona entrega orden entrada
     * @param articulos,      Listado de articulos en orden de entrada
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *          Exception
     */
    private OrdenEntrada crearOrdenEntradaFacturaComercial(Date fechaAlta, Almacen almacen, String personaEntrega, String descripcion,
                                                           List<ArticuloEntrada> articulos) throws ManagerInventarioServiceBusinessException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param fechaAlta,      Fecha de alta orden de entrada
     * @param almacen,        Almacen de ingreso orden de entrada
     * @param personaEntrega, Persona entrega orden entrada
     * @param articulos,      Listado de articulos en orden de entrada
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException
     *          , Exception
     */
    private OrdenEntrada crearOrdenEntradaPolizaImportacion(Date fechaAlta, Almacen almacen, String personaEntrega,
                                                            List<ArticuloEntrada> articulos) throws ManagerInventarioServiceBusinessException {
        throw new UnsupportedOperationException();
    }

    /**
     * Modificar Orden Entrada Ordinaria
     *
     * @param ordenEntrada, Orden Entrada ordinaria
     * @param articulos,    Listado de articulos
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *          Exception
     */
    private OrdenEntrada modificarOrdenEntradaOrdinaria(OrdenEntrada ordenEntrada, List<ArticuloEntrada> articulos)
            throws ManagerInventarioServiceBusinessException {

        logger.debug("Modificando orden de entrada con parametros: [noMovimiento]: " + ordenEntrada.getNoMovimiento());

        try {

            //1. PREPARAR EL CONTEXTO
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            //3. MODIFICAR DETALLE ORDEN DE ENTRADA
            double costoNeto = 0.0;
            long renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                ArticuloEntrada articulo = (ArticuloEntrada) it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //Creando movimiento de inventario por articulo
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setAlmacen(ordenEntrada.getAlmacen());
                    movimientoInventario.setFechaAlta(ordenEntrada.getFechaAlta());
                    movimientoInventario.setAfectacion(new Integer(1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidad());
                    movimientoInventario.setEstado(estado);
                    movimientoInventario.setArticuloEntrada(articulo);
                    movimientoInventario.setProducto(articulo.getProducto());

                    articulo.setMovimientoInventario(movimientoInventario);
                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {

                        //Remover el articulo si su Id es distinto de nulo
                        if (articulo.getId() != null)
                            articuloEntradaEAO.remove(articulo.getId());

                        it.remove();
                        continue;
                    }

                    //Actulizar cantidad movimiento inventario
                    if (articulo.getMovimientoInventario() == null) {

                        //Creando movimiento de inventario por articulo
                        MovimientoInventario movimientoInventario = new MovimientoInventario();
                        movimientoInventario.setAlmacen(ordenEntrada.getAlmacen());
                        movimientoInventario.setFechaAlta(ordenEntrada.getFechaAlta());
                        movimientoInventario.setAfectacion(new Integer(1).shortValue());
                        movimientoInventario.setCantidad(articulo.getCantidad());
                        movimientoInventario.setEstado(estado);
                        movimientoInventario.setArticuloEntrada(articulo);
                        movimientoInventario.setProducto(articulo.getProducto());

                        articulo.setMovimientoInventario(movimientoInventario);
                    } else {
                        articulo.getMovimientoInventario().setCantidad(articulo.getCantidad());
                    }
                }

                //Calculando el costo total por item
                costoNeto += articulo.getCosto().doubleValue() * articulo.getCantidad();

                //Setting orden de entrada en el articulo
                articulo.setOrdenEntrada(ordenEntrada);
                articulo.setRenglon(renglon);
                articulo.setNoDocumento(ordenEntrada.getNoMovimiento());

                articulo.setCreate(false);
                articulo.setUpdate(true);

                //Actualizar renglon
                renglon += 1;
            }

            //Setting nuevo costo neto de ingreso de inventario
            ordenEntrada.setMontoTotal(new BigDecimal(costoNeto).setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenEntrada.setArticulos(new HashSet<ArticuloEntrada>(articulos));

            //4. PERSISTIR ORDEN DE INGRESO DE INVENTARIO
            ordenEntrada = ordenEntradaEAO.update(ordenEntrada);
            
            //Calcular costo promedio ponderado
            calcularCostoPromedioPonderado(ordenEntrada);
            
            return ordenEntrada;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    /**
     * Calcular costo promedio ponderado
     *
     * @param ordenEntrada, OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *          Exception
     */
    private void calcularCostoPromedioPonderado(OrdenEntrada ordenEntrada) throws ManagerInventarioServiceBusinessException {

        logger.debug("Calculando costo promedio ponderado para orden de entrada con numero: [noOrden]: " + ordenEntrada.getNoMovimiento());

        try {

            for (ArticuloEntrada articuloEntrada : ordenEntrada.getArticulos()) {

                //Obtener el producto
                Producto producto = articuloEntrada.getProducto();

                //Obtener existencias del producto
                List<ProductoExistencia> existencias = mgrProducto.buscarProductoExistencias(producto.getCodigo());

                //Existencia producto
                long existencia = 0;

                for (ProductoExistencia productoExistencia : existencias) {
                    existencia += productoExistencia.getExistencia();
                }

                //Evaluar que existencias no sean negativas
                if (existencia < 0)
                    existencia = 0;

                //Costo existencia actual
                BigDecimal costoExistenciaActual = producto.getCostoPROM().multiply(new BigDecimal(existencia)).
                        setScale(4, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal cantidadActualInventario = new BigDecimal(existencia);

                //Costo existencia ingreso
                BigDecimal costoExistenciaIngreso = articuloEntrada.getCostoTotal();
                BigDecimal cantidadIngresoInventario = new BigDecimal(articuloEntrada.getCantidad());

                //Calculo del costo promedio
                BigDecimal costoTotalExistencia = costoExistenciaActual.add(costoExistenciaIngreso);
                BigDecimal cantidadTotalExistencias = cantidadActualInventario.add(cantidadIngresoInventario);
                
                BigDecimal costoPromedio = costoTotalExistencia.divide(cantidadTotalExistencias, 4, BigDecimal.ROUND_HALF_EVEN);

                //Actualizar el nuevo costo promedio al producto
                producto.setCostoPROM(costoPromedio);

                productoEAO.update(producto);
            }

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }

    /**
     * Calcular consumo promedio mensual del producto
     *
     * @param codigo, String
     * @return long
     * @throws ManagerInventarioServiceBusinessException,
     *          Exception
     */
    private long calcularConsumoPromedioMensualProducto(String codigo, Integer idAlmacen) throws ManagerInventarioServiceBusinessException {

        logger.debug("Calcular demanda producto con parametros: [codigo]: " + codigo + ", [idAlmacen]: " + idAlmacen);

        try {

            //1. PREPARAR EL CONTEXTO DE VARIABLES DE EJECUCION
            Date fechaHasta = new Date();

            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaHasta);

            //Restar 6 meses a la fecha Hasta para obtener fecha Desde
            calendario.add(Calendar.MONTH, -6);

            //Demanda total del producto del periodo a evaluar
            long demandaTotal = 0;

            int dia = calendario.getActualMinimum(Calendar.DAY_OF_MONTH);
            int mes = calendario.get(Calendar.MONTH);
            int anio = calendario.get(Calendar.YEAR);
            calendario.set(anio, mes, dia);

            Date fechaDesde = calendario.getTime();

            //2. Obtener movimientos de inventario con afectacion negativa para el almacen de busqueda
            List<MovimientoInventario> movimientosInventario = movimientoInventarioEAO.findByProducto(codigo, idAlmacen,
                    fechaDesde, fechaHasta, TiposAfectacion.NEGATIVA.getValue());

            //3. Sumar movimientos de inventario para calcular demanda total
            for (MovimientoInventario movimientoInventario : movimientosInventario) {
                demandaTotal += movimientoInventario.getCantidad();
            }

            //4. Calcular demanda promedio del producto
            long demandaProm = demandaTotal / 6;

            return demandaProm;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerInventarioServiceBusinessException(e.getMessage(), e);
        }
    }
}
