/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.facturacion;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.agenteVentasEAO.AgenteVentasEAO;
import contac.modelo.eao.agenteVentasEAO.AgenteVentasEAOPersistence;
import contac.modelo.eao.almacenEAO.AlmacenEAO;
import contac.modelo.eao.almacenEAO.AlmacenEAOPersistence;
import contac.modelo.eao.articuloFacturaEAO.ArticuloFacturaEAO;
import contac.modelo.eao.articuloFacturaEAO.ArticuloFacturaEAOPersistence;
import contac.modelo.eao.articuloProformaEAO.ArticuloProformaEAO;
import contac.modelo.eao.articuloProformaEAO.ArticuloProformaEAOPersistence;
import contac.modelo.eao.clienteEAO.ClienteEAO;
import contac.modelo.eao.clienteEAO.ClienteEAOPersistence;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAO;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAOPersistence;
import contac.modelo.eao.facturaEAO.FacturaEAO;
import contac.modelo.eao.facturaEAO.FacturaEAOPersistence;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.monedaEAO.MonedaEAO;
import contac.modelo.eao.monedaEAO.MonedaEAOPersistence;
import contac.modelo.eao.movimientoInventarioEAO.MovimientoInventarioEAO;
import contac.modelo.eao.movimientoInventarioEAO.MovimientoInventarioEAOPersistence;
import contac.modelo.eao.proformaEAO.ProformaEAO;
import contac.modelo.eao.proformaEAO.ProformaEAOPersistence;
import contac.modelo.eao.tasaCambioEAO.TasaCambioEAO;
import contac.modelo.eao.tasaCambioEAO.TasaCambioEAOPersistence;
import contac.modelo.entity.*;
import contac.servicio.clientes.ManagerClientesServiceBusiness;
import contac.servicio.clientes.ManagerClientesServiceBusinessImpl;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessImpl;
import contac.servicio.seguridad.*;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 02-16-12
 * Time: 10:02 AM
 */
public class ManagerFacturacionServiceBusinessImpl extends UnicastRemoteObject implements ManagerFacturacionServiceBusiness {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ManagerFacturacionServiceBusinessImpl.class);

    //Access EAO Layer
    protected ClienteEAO clienteEAO;
    protected AlmacenEAO almacenEAO;
    protected TasaCambioEAO tasaCambioEAO;
    protected FacturaEAO facturaEAO;
    protected ProformaEAO proformaEAO;
    protected ArticuloFacturaEAO articuloFacturaEAO;
    protected ArticuloProformaEAO articuloProformaEAO;
    protected EstadoMovimientoEAO estadoMovimientoEAO;
    protected MovimientoInventarioEAO movimientoInventarioEAO;
    protected AgenteVentasEAO agenteVentasEAO;
    protected MonedaEAO monedaEAO;

    //Access service manager
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;
    protected ManagerSeguridadServiceBusiness mgrSeguridad;
    protected ManagerClientesServiceBusiness mgrClientes;
    protected ManagerInventarioServiceBusiness mgrInventario;

    /**
     * Default Constructor
     *
     * @throws RemoteException, Exception
     */
    public ManagerFacturacionServiceBusinessImpl() throws RemoteException {

        logger.debug("Creando servicio de Facturacion");

        //Init EAO Layer
        clienteEAO = new ClienteEAOPersistence();
        almacenEAO = new AlmacenEAOPersistence();
        tasaCambioEAO = new TasaCambioEAOPersistence();
        facturaEAO = new FacturaEAOPersistence();
        proformaEAO = new ProformaEAOPersistence();
        articuloFacturaEAO = new ArticuloFacturaEAOPersistence();
        articuloProformaEAO = new ArticuloProformaEAOPersistence();
        estadoMovimientoEAO = new EstadoMovimientoEAOPersistence();
        movimientoInventarioEAO = new MovimientoInventarioEAOPersistence();
        agenteVentasEAO = new AgenteVentasEAOPersistence();
        monedaEAO = new MonedaEAOPersistence();
    }

    /**
     * Constructor de Manager de Facturacion con usuario de autorizacion
     *
     * @param mgrAutorizacion, Servicio de autenticacion y autorizacion
     * @throws RemoteException, Exception
     */
    public ManagerFacturacionServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Call super constructor
        this();

        //Init authorization services
        this.mgrAutorizacion = mgrAutorizacion;
        mgrSeguridad = new ManagerSeguridadServiceBusinessImpl(this.mgrAutorizacion);
        mgrClientes = new ManagerClientesServiceBusinessImpl(this.mgrAutorizacion);
        mgrInventario = new ManagerInventarioServiceBusinessImpl(this.mgrAutorizacion);
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerFacturacionServiceBusinessException {

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
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerFacturacionServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerFacturacionServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public Factura crearFactura(int tipoFactura, Integer idCliente, Integer idAlmacen, Integer idAgenteVentas,
                                BigDecimal porcDescuento, BigDecimal porcIva, BigDecimal porcRetFuente, BigDecimal porcRetMunicipal,
                                BigDecimal tasaCambio, String nombreCliente, Integer idMoneda, Direccion direccionEntrega,
                                Date fechaAlta, boolean exonerada, boolean retencionFuente, boolean retencionMunicipal,
                                Integer idProforma, List<ArticuloFactura> articulos)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Creando factura con parametros: [tipoFactura]: " + tipoFactura + ", [nombreCliente]: " + nombreCliente +
                ", [fechaAlta]: " + fechaAlta + ", [exonerada]: " + exonerada + ", [retencionFuente]" +
                retencionFuente + ", [retencionMunicipal]: " + retencionMunicipal + "[idCliente]: " + idCliente + ", [idAlmacen]: "
                + idAlmacen + ", [tasaCambio]: " + tasaCambio + ", [idProforma]: " + idProforma);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Preparar el contexto de ejecucion
            Cliente cliente = clienteEAO.findById(idCliente);
            Almacen almacen = almacenEAO.findById(idAlmacen);
            Moneda moneda = monedaEAO.findById(idMoneda);
            AgenteVentas agenteVentas = agenteVentasEAO.findById(idAgenteVentas);
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            Proforma proforma = null; //TODO: Implementar asociacion con una proforma

            //Si tiene una proforma asociada buscarla
            if (idProforma != null)
                proforma = proformaEAO.findById(idProforma);

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerFacturacionServiceBusinessException("Fecha de alta no puede ser posterior al dia de hoy.");

            if (articulos.isEmpty())
                throw new ManagerFacturacionServiceBusinessException("Debes ingresar al menos un articulo");

            //Obtener numero de factura consecutivo
            long noFactura = almacen.getConsecutivo() + 1;

            //Persistir factura
            Factura factura = new Factura();
            factura.setNoDocumento(noFactura);
            factura.setFechaAlta(fechaAlta);
            factura.setNombreCliente(nombreCliente);
            factura.setTasaCambio(tasaCambio);
            factura.setMoneda(moneda);
            factura.setProforma(proforma);
            factura.setAgenteVentas(agenteVentas);
            factura.setTipoFactura((byte) tipoFactura);
            factura.setAlmacen(almacen);
            factura.setCliente(cliente);
            factura.setNombreCliente(cliente.getRazonSocial());
            factura.setExonerada(exonerada);
            factura.setRetencionF(retencionFuente);
            factura.setRetencionM(retencionMunicipal);
            factura.setPorcDescuento(porcDescuento);
            factura.setPorcIVA(porcIva);
            factura.setPorcRetFuente(porcRetFuente);
            factura.setPorcRetMunicipal(porcRetMunicipal);
            factura.setMontoBruto(new BigDecimal("0.00"));
            factura.setMontoDescuento(new BigDecimal("0.00"));
            factura.setMontoNeto(new BigDecimal("0.00"));
            factura.setMontoIVA(new BigDecimal("0.00"));
            factura.setRetencionFuente(new BigDecimal("0.00"));
            factura.setRetencionMunicipal(new BigDecimal("0.00"));
            factura.setEstadoMovimiento(estado);

            if (direccionEntrega != null)
                factura.setDireccionEntrega(direccionEntrega);
            else
                factura.setDireccionEntrega(cliente.getDireccion());

            //<Persistir articulos de factura>
            BigDecimal montoTotalAntesImpuesto = new BigDecimal("0.00");
            BigDecimal montoTotalNeto = new BigDecimal("0.00");
            BigDecimal montoTotalIVA = new BigDecimal("0.00");
            BigDecimal montoTotalDescuento = new BigDecimal("0.00");
            BigDecimal montoTotalRetFuente = new BigDecimal("0.00");
            BigDecimal montoTotalRetMunicipal = new BigDecimal("0.00");

            int renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obteniendo articulo factura
                ArticuloFactura articulo = (ArticuloFactura) it.next();

                //Borrar articulos con cantidad menor o igual a zero
                if (articulo.getCantidad() <= 0) {
                    it.remove();
                    continue;
                }

                if (articulo.isCreate()) {

                    //*************************************************************************
                    //Creando movimiento inventario
                    //*************************************************************************
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setFechaAlta(factura.getFechaAlta());
                    movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidad());
                    movimientoInventario.setAlmacen(almacen);
                    movimientoInventario.setArticuloFactura(articulo);
                    movimientoInventario.setEstado(estado);
                    movimientoInventario.setProducto(articulo.getProducto());

                    //Setting movimiento inventario
                    articulo.setFactura(factura);
                    articulo.setNoFactura(factura.getNoDocumento());
                    articulo.setMovimientoInventario(movimientoInventario);
                }

                if (articulo.isUpdate()) {

                    //Actualizar cantidad movimiento inventario
                    if (articulo.getMovimientoInventario() == null) {

                        //Creando movimiento de inventario por articulo
                        MovimientoInventario movimientoInventario = new MovimientoInventario();
                        movimientoInventario.setFechaAlta(factura.getFechaAlta());
                        movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                        movimientoInventario.setCantidad(articulo.getCantidad());
                        movimientoInventario.setAlmacen(almacen);
                        movimientoInventario.setArticuloFactura(articulo);
                        movimientoInventario.setEstado(estado);
                        movimientoInventario.setProducto(articulo.getProducto());

                        articulo.setFactura(factura);
                        articulo.setNoFactura(factura.getNoDocumento());
                        articulo.setMovimientoInventario(movimientoInventario);
                    } else {
                        articulo.getMovimientoInventario().setCantidad(articulo.getCantidad());
                    }
                }

                //*************************************************************************
                //Acumular montos totales
                //*************************************************************************
                montoTotalAntesImpuesto = montoTotalAntesImpuesto.add(articulo.getPrecioAntesImpuesto());
                montoTotalDescuento = montoTotalDescuento.add(articulo.getDescuento());
                montoTotalIVA = montoTotalIVA.add(articulo.getIva());
                montoTotalRetFuente = montoTotalRetFuente.add(articulo.getRetencionFuente());
                montoTotalRetMunicipal = montoTotalRetMunicipal.add(articulo.getRetencionMunicipal());
                montoTotalNeto = montoTotalNeto.add(articulo.getPrecioNeto());

                //Actualizar renglon
                articulo.setRenglon(renglon);

                renglon++;
            }

            //<Persist articulos factura>
            factura.setMontoBruto(montoTotalAntesImpuesto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setMontoDescuento(montoTotalDescuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setMontoIVA(montoTotalIVA.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setRetencionFuente(montoTotalRetFuente.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setRetencionMunicipal(montoTotalRetMunicipal.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setMontoNeto(montoTotalNeto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setArticulos(new HashSet<ArticuloFactura>(articulos));

            //Persistir nueva factura comercial
            factura = facturaEAO.create(factura);

            //Actualizar numero consecutivo de facturacion
            almacen.setConsecutivo(noFactura);
            almacenEAO.update(almacen);

            //Retornar factura comercial guardada
            return factura;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Factura modificarFactura(Integer idFactura, BigDecimal tasaCambio, Direccion direccionEntrega, BigDecimal porcDescuento,
                                    BigDecimal porcIva, BigDecimal porcRetFuente, BigDecimal porcRetMunicipal,
                                    Date fechaAlta, boolean exonerada, boolean retencionFuente, boolean retencionMunicipal,
                                    Integer idProforma, List<ArticuloFactura> articulos)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Modificar factura con parametros: [idFactura]: " + idFactura + ", [tasaCambio]: " + tasaCambio +
                ", [direccionEntrega]: " + direccionEntrega + ", [exonerada]: " + exonerada + ", [retencionFuente]: " +
                retencionFuente + ", [retencionMunicipal]: " + retencionMunicipal);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Factura factura = facturaEAO.findById(idFactura);
            Proforma proforma = null; //TODO: Implementar asociacion con una proforma

            //Validar consistencia de datos
            if (!factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("El estado de la factura no es consistente para poder modificar.");

            if (articulos.isEmpty())
                throw new ManagerFacturacionServiceBusinessException("Debes ingresar al menos un articulo");
            
            factura.setTasaCambio(tasaCambio);
            factura.setDireccionEntrega(direccionEntrega);
            factura.setPorcDescuento(porcDescuento);
            factura.setPorcIVA(porcIva);
            factura.setPorcRetFuente(porcRetFuente);
            factura.setPorcRetMunicipal(porcRetMunicipal);
            factura.setFechaAlta(fechaAlta);
            factura.setExonerada(exonerada);
            factura.setRetencionF(retencionFuente);
            factura.setRetencionM(retencionMunicipal);
            factura.setProforma(proforma);

            //<Persistir articulos de factura>
            BigDecimal montoTotalAntesImpuesto = new BigDecimal("0.00");
            BigDecimal montoTotalNeto = new BigDecimal("0.00");
            BigDecimal montoTotalIVA = new BigDecimal("0.00");
            BigDecimal montoTotalDescuento = new BigDecimal("0.00");
            BigDecimal montoTotalRetFuente = new BigDecimal("0.00");
            BigDecimal montoTotalRetMunicipal = new BigDecimal("0.00");

            int renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext();) {
                
                ArticuloFactura articulo = (ArticuloFactura)it.next();

                if (articulo.isCreate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {
                        it.remove();
                        continue;
                    }

                    //*************************************************************************
                    //Creando movimiento inventario
                    //*************************************************************************
                    MovimientoInventario movimientoInventario = new MovimientoInventario();
                    movimientoInventario.setFechaAlta(factura.getFechaAlta());
                    movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                    movimientoInventario.setCantidad(articulo.getCantidad());
                    movimientoInventario.setAlmacen(factura.getAlmacen());
                    movimientoInventario.setArticuloFactura(articulo);
                    movimientoInventario.setEstado(factura.getEstadoMovimiento());
                    movimientoInventario.setProducto(articulo.getProducto());

                    //Setting movimiento inventario
                    articulo.setFactura(factura);
                    articulo.setNoFactura(factura.getNoDocumento());
                    articulo.setMovimientoInventario(movimientoInventario);

                }

                if (articulo.isUpdate()) {

                    //Borrar articulos con cantidad menor o igual a zero
                    if (articulo.getCantidad() <= 0) {

                        //Remover el articulo si su Id es distinto de nulo
                        if (articulo.getId() != null)
                            articuloFacturaEAO.remove(articulo.getId());

                        it.remove();
                        continue;
                    }

                    //Actualizar cantidad movimiento inventario
                    if (articulo.getMovimientoInventario() == null) {

                        //Creando movimiento de inventario por articulo
                        MovimientoInventario movimientoInventario = new MovimientoInventario();
                        movimientoInventario.setFechaAlta(factura.getFechaAlta());
                        movimientoInventario.setAfectacion(new Integer(-1).shortValue());
                        movimientoInventario.setCantidad(articulo.getCantidad());
                        movimientoInventario.setAlmacen(factura.getAlmacen());
                        movimientoInventario.setArticuloFactura(articulo);
                        movimientoInventario.setEstado(factura.getEstadoMovimiento());
                        movimientoInventario.setProducto(articulo.getProducto());

                        articulo.setFactura(factura);
                        articulo.setNoFactura(factura.getNoDocumento());
                        articulo.setMovimientoInventario(movimientoInventario);
                    } else {
                        articulo.getMovimientoInventario().setCantidad(articulo.getCantidad());
                    }    
                    
                    
                }

                //*************************************************************************
                //Acumular montos totales
                //*************************************************************************
                montoTotalAntesImpuesto = montoTotalAntesImpuesto.add(articulo.getPrecioAntesImpuesto());
                montoTotalDescuento = montoTotalDescuento.add(articulo.getDescuento());
                montoTotalIVA = montoTotalIVA.add(articulo.getIva());
                montoTotalRetFuente = montoTotalRetFuente.add(articulo.getRetencionFuente());
                montoTotalRetMunicipal = montoTotalRetMunicipal.add(articulo.getRetencionMunicipal());
                montoTotalNeto = montoTotalNeto.add(articulo.getPrecioNeto());

                //Actualizar renglon
                articulo.setRenglon(renglon);

                renglon++;
            }

            //<Persist articulos factura>
            factura.setMontoBruto(montoTotalAntesImpuesto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setMontoDescuento(montoTotalDescuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setMontoIVA(montoTotalIVA.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setRetencionFuente(montoTotalRetFuente.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setRetencionMunicipal(montoTotalRetMunicipal.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setMontoNeto(montoTotalNeto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            factura.setArticulos(new HashSet<ArticuloFactura>(articulos));

            factura = facturaEAO.update(factura);

            return factura;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }

    }

    @Override
    public void anularFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Anular factura con parametros: [idFactura]: " + idFactura);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Factura factura = facturaEAO.findById(idFactura);
            EstadoMovimiento estadoAnulado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.ANULADO.getEstado());

            //Validar datos generales de la factura
            if (!factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("Factura no se encuentra en un estado valido para poder anular.");

            //Setting estado anulado
            factura.setPorcDescuento(new BigDecimal("0.00"));
            factura.setPorcRetFuente(new BigDecimal("0.00"));
            factura.setPorcRetMunicipal(new BigDecimal("0.00"));
            factura.setMontoDescuento(new BigDecimal("0.00"));
            factura.setMontoBruto(new BigDecimal("0.00"));
            factura.setMontoIVA(new BigDecimal("0.00"));
            factura.setMontoNeto(new BigDecimal("0.00"));
            factura.setRetencionFuente(new BigDecimal("0.00"));
            factura.setRetencionMunicipal(new BigDecimal("0.00"));
            
            factura.setEstadoMovimiento(estadoAnulado);

            //Eliminar movimientos de inventario de los productos
            for (ArticuloFactura articulo : factura.getArticulos()) {
                
                movimientoInventarioEAO.remove(articulo.getMovimientoInventario().getId());
                articulo.setMovimientoInventario(null);
            }

            facturaEAO.update(factura);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Proforma crearProforma(Integer idCliente, Integer idAlmacen, Integer idTasaCambio, String nombreCliente,
                                  Direccion direccionEntrega, Date fechaAlta, List<ArticuloFactura> articulos) throws
            ManagerFacturacionServiceBusinessException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AgenteVentas> buscarAgentesVentas() throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscar todos los agents de ventas");

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {
            return agenteVentasEAO.findAllOrderByCodigo();
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<AgenteVentas> buscarAgentesVentasUsuario() throws ManagerFacturacionServiceBusinessException,
            RemoteException {

        logger.debug("Buscar agentes de ventas");

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Buscar usuario por username
            Usuario usuario = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername());

            //Validar que usuario tenga una compania y un almacen seleccionado
            if (usuario.getCompania() == null || usuario.getAlmacen() == null)
                return null;

            //Obtener almacen del usuario
            Almacen almacen = usuario.getAlmacen();

            return agenteVentasEAO.findByAlmacen(almacen.getId());

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public AgenteVentas crearAgenteVentas(long codigo, String nombre, Integer idAlmacen, boolean isActivo) throws
            ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Creando agente de ventas con parametros: [codigo]: " + codigo + ", [nombre]: " + nombre +
                ", [idAlmacen]: " + idAlmacen + ", [activo]: " + isActivo);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Verificar agente de ventas valido para registro
            agenteVentasValidoParaRegistro(codigo);

            //Preparar el contexto
            Almacen almacen = almacenEAO.findById(idAlmacen);

            AgenteVentas agenteVentas = new AgenteVentas();
            agenteVentas.setCodigo(codigo);
            agenteVentas.setNombre(nombre);
            agenteVentas.setAlmacen(almacen);
            agenteVentas.setActivo(isActivo);

            return agenteVentasEAO.create(agenteVentas);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public AgenteVentas modificarAgenteVentas(Integer idAgenteVentas, String nombre, Integer idAlmacen, boolean isActivo) throws
            ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Modificar agente de vetnas con parametros: [nombre]: " + nombre + ", [idAlmacen]: " + idAlmacen +
                ", [activo]: " + isActivo);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Preparar el contexto
            AgenteVentas agenteVentas = agenteVentasEAO.findById(idAgenteVentas);
            Almacen almacen = almacenEAO.findById(idAlmacen);

            agenteVentas.setNombre(nombre);
            agenteVentas.setAlmacen(almacen);
            agenteVentas.setActivo(isActivo);

            return agenteVentasEAO.update(agenteVentas);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public AgenteVentas buscarAgentesVentasPorCodigo(long codigo) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscar agentes de ventas por codigo");

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Buscar usuario por username
            Usuario usuario = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername());

            //Validar que usuario tenga una compania y un almacen seleccionado
            if (usuario.getCompania() == null || usuario.getAlmacen() == null)
                return null;

            //Obtener almacen del usuario
            Almacen almacen = usuario.getAlmacen();

            return agenteVentasEAO.findByCodigo(codigo, almacen.getId());

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Date getFechaServidor() throws ManagerFacturacionServiceBusinessException, RemoteException {
        return new Date();
    }

    @Override
    public List<TasaCambio> buscarTasasCambioFacturacion() throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscar tasas de cambio facturacion");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        try {
            return this.tasaCambioEAO.findByFacturacion();
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Factura> buscarFacturasPorEstado(String alias) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscando facturas por estados: [estado]: " + alias);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Buscar estado movimiento por alias
            EstadoMovimiento estadoMovimiento = estadoMovimientoEAO.findByAlias(alias);

            //Buscar almacen del usuario
            Almacen almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            Integer idAlmacen = almacen != null ? almacen.getId() : null;

            //Si almacen del usuario es nulo - verificar que tiene el rol de administrador para ejecutar la accion
            if (idAlmacen == null)
                mgrAutorizacion.isUserInRole(Roles.ROLSYSTEMADMIN.toString());

            return facturaEAO.findByEstado(estadoMovimiento.getId(), idAlmacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Factura> buscarFacturasPorFecha(Date fechaDesde, Date fechaHasta) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscando facturas comerciales por rangos de fecha: [fechaDesde]: " + fechaDesde + ", [fechaHasta]: " +
                fechaHasta);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Validar campos de la busqueda
            if (fechaDesde == null)
                throw new ManagerFacturacionServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerFacturacionServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            //Buscar almacen del usuario
            Almacen almacen = mgrSeguridad.buscarUsuarioPorLogin(mgrAutorizacion.getUsername()).getAlmacen();
            Integer idAlmacen = almacen != null ? almacen.getId() : null;

            //Si almacen del usuario es nulo - verificar que tiene el rol de administrador para ejecutar la accion
            if (idAlmacen == null)
                mgrAutorizacion.isUserInRole(Roles.ROLSYSTEMADMIN.toString());

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

            return facturaEAO.findByFechas(fechaDesde, fechaHasta, idAlmacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void usuarioEditaDatosFactura() throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Verificar si usuario tiene permisos de editar datos de factura");

        try {

            //Verificar si usuario tiene permiso de editar datos de factura
            mgrAutorizacion.isUserInRole(Roles.ROLFACTURACIONADMIN.toString());

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        }
    }

    //***************************************************************************************
    // UTILITY METHODS
    //***************************************************************************************

    /**
     * Buscar agente de ventas por codigo
     *
     * @param codigo, Codigo del agente de ventas
     * @return AgenteVentas
     * @throws ManagerFacturacionServiceBusinessException,
     *          Exception
     */
    private void agenteVentasValidoParaRegistro(long codigo) throws ManagerFacturacionServiceBusinessException {

        logger.debug("Buscar agente de ventas por codigo: [codigo]: " + codigo);

        try {

            //Buscando agente de ventas
            agenteVentasEAO.findByCodigo(codigo);

            throw new ManagerFacturacionServiceBusinessException("Agente de ventas se encuentra registrado, favor ingresar" +
                    " otro codigo");

        } catch (PersistenceClassNotFoundException e) {
            logger.info("Agente de Ventas no se encuentra registrado. Proceder con el registro!!!");
        } catch (GenericPersistenceEAOException e) {
            logger.info("Agente de Ventas no se encuentra registrado. Proceder con el registro!!!");
        }
    }

    /**
     * Obtener numero consecutivo facturacion
     *
     * @param idAlmacen, Identificador del almacen
     * @return long
     * @throws ManagerFacturacionServiceBusinessException,
     *          Exception
     */
    private long obtenerNoConsecutivoFacturacion(Integer idAlmacen) throws ManagerFacturacionServiceBusinessException {

        logger.debug("Obtener numero consecutivo facturacion para almacen: [idAlmacen]:" + idAlmacen);

        try {

            //Buscar numero consecutivo facturacion almacen
            Almacen almacen = almacenEAO.findById(idAlmacen);
            return almacen.getConsecutivo() + 1;


        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        }
    }

}
