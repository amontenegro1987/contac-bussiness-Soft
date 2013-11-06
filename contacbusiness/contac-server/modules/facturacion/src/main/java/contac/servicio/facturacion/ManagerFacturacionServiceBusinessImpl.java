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
import org.hibernate.Hibernate;

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
    public Factura crearFactura(long noFactura, int tipoFactura, Integer idCliente, Integer idAlmacen,
                                Integer idAgenteVentas, BigDecimal porcDescuento, BigDecimal porcIva,
                                BigDecimal porcRetFuente, BigDecimal porcRetMunicipal, BigDecimal tasaCambio,
                                String nombreCliente, Integer idMoneda, Direccion direccionEntrega,
                                Date fechaAlta, boolean exonerada, boolean retencionFuente, boolean retencionMunicipal,
                                Integer idProforma, List<ArticuloFactura> articulos)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Creando factura con parametros: [noFactura]: " + noFactura + "[tipoFactura]: " + tipoFactura +
                ", [nombreCliente]: " + nombreCliente + ", [fechaAlta]: " + fechaAlta + ", [exonerada]: " +
                exonerada + ", [retencionFuente]" + retencionFuente + ", [retencionMunicipal]: " + retencionMunicipal +
                "[idCliente]: " + idCliente + ", [idAlmacen]: " + idAlmacen + ", [tasaCambio]: " + tasaCambio +
                ", [idProforma]: " + idProforma);

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

            //Actualizar consecutivo de factura en el almacen
            boolean update_consecutivo = true;

            //Si tiene una proforma asociada buscarla
            if (idProforma != null)
                proforma = proformaEAO.findById(idProforma);

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerFacturacionServiceBusinessException("Fecha de alta no puede ser posterior al dia de hoy.");

            if (articulos.isEmpty())
                throw new ManagerFacturacionServiceBusinessException("Debes ingresar al menos un articulo");

            if (noFactura > 0) {
                facturaValidaParaRegistro(noFactura, almacen.getId()); //Verificar que numero de factura no se encuentra registrado
                update_consecutivo = false; //No actualizar consecutivo en almacen de facturacion
            } else {
                noFactura = almacen.getConsecutivo() + 1;  //Obtener numero de factura consecutivo
            }

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

            //Actualizar numero consecutivo de facturacion si el numero es generado
            if (update_consecutivo) {
                almacen.setConsecutivo(noFactura);
                almacenEAO.update(almacen);
            }

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
    public Proforma crearProforma(long noProforma, Integer idCliente, Integer idAlmacen,
                                  Integer idAgenteVentas, BigDecimal porcDescuento, BigDecimal porcIva,
                                  BigDecimal porcRetFuente, BigDecimal porcRetMunicipal, BigDecimal tasaCambio,
                                  String nombreCliente, Integer idMoneda, Direccion direccionEntrega,
                                  Date fechaAlta, boolean exonerada, boolean retencionFuente, boolean retencionMunicipal,
                                  List<ArticuloProforma> articulos, Date fechaVencimiento, String correo)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Creando Proforma con parametros: [noProforma]: " + noProforma  +
                "[idCliente]: " + idCliente + ", [nombreCliente]: " + nombreCliente + ", [fechaAlta]: " + fechaAlta +
                ", [idAgenteVentas]: " + idAgenteVentas + ", [porcRetFuente]: " + porcRetFuente +
                ", [porcRetMunicipal]: " + porcRetMunicipal + ", [idMoneda]: " + idMoneda + ", [direccionEntrega]: " +
                direccionEntrega + ", [exonerada]: " + exonerada + ", [retencionFuente]" + retencionFuente +
                ", [retencionMunicipal]: " + retencionMunicipal + "[idCliente]: " + idCliente +
                ", [idAlmacen]: " + idAlmacen + ", [tasaCambio]: " + tasaCambio + ", [fechaVencimiento]: " + fechaVencimiento +
                ", [correo]: " + correo);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {
            //Preparar el contexto de ejecucion
            Cliente cliente = clienteEAO.findById(idCliente);
            Almacen almacen = almacenEAO.findById(idAlmacen);
            Moneda moneda = monedaEAO.findById(idMoneda);
            AgenteVentas agenteVentas = agenteVentasEAO.findById(idAgenteVentas);
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            Date fecha1 = new Date();

            //Actualizar consecutivo de Proforma en el almacen
            boolean update_consecutivo = true;

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerFacturacionServiceBusinessException("Fecha de alta no puede ser posterior al dia de hoy.");

            if (articulos.isEmpty())
                throw new ManagerFacturacionServiceBusinessException("Debes ingresar al menos un articulo");

            if (noProforma > 0) {
                proformaValidaParaRegistro(noProforma, almacen.getId()); //Verificar que numero de Proforma no se encuentra registrado
                update_consecutivo = false;
            } else {
                noProforma = almacen.getConsecutivo() + 1;  //Obtener numero de proforma consecutivo
            }
            //Persistir Proforma
            Proforma proforma = new Proforma();
            proforma.setNoDocumento(noProforma);
            proforma.setFechaAlta(fechaAlta);
            proforma.setNombreCliente(nombreCliente);
            proforma.setTasaCambio(tasaCambio);
            proforma.setMoneda(moneda);
            proforma.setAgenteVentas(agenteVentas);
            proforma.setAlmacen(almacen);
            proforma.setCliente(cliente);
            proforma.setNombreCliente(cliente.getRazonSocial());
            proforma.setExonerada(exonerada);
            proforma.setRetencionF(retencionFuente);
            proforma.setRetencionM(retencionMunicipal);
            proforma.setPorcDescuento(porcDescuento);
            proforma.setPorcIVA(porcIva);
            proforma.setPorcRetFuente(porcRetFuente);
            proforma.setPorcRetMunicipal(porcRetMunicipal);
            proforma.setMontoBruto(new BigDecimal("0.00"));
            proforma.setMontoDescuento(new BigDecimal("0.00"));
            proforma.setMontoNeto(new BigDecimal("0.00"));
            proforma.setMontoIVA(new BigDecimal("0.00"));
            proforma.setRetencionFuente(new BigDecimal("0.00"));
            proforma.setRetencionMunicipal(new BigDecimal("0.00"));
            proforma.setEstadoMovimiento(estado);
            proforma.setFechaVencimiento(fecha1);
            proforma.setCorreo(correo);

            if (direccionEntrega != null)
                proforma.setDireccionEntrega(direccionEntrega);
            else
                proforma.setDireccionEntrega(cliente.getDireccion());

            //<Persistir articulos de Proforma>
            BigDecimal montoTotalAntesImpuesto = new BigDecimal("0.00");
            BigDecimal montoTotalNeto = new BigDecimal("0.00");
            BigDecimal montoTotalIVA = new BigDecimal("0.00");
            BigDecimal montoTotalDescuento = new BigDecimal("0.00");
            BigDecimal montoTotalRetFuente = new BigDecimal("0.00");
            BigDecimal montoTotalRetMunicipal = new BigDecimal("0.00");

            int renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obteniendo articulo proforma

                ArticuloProforma articuloP = (ArticuloProforma) it.next();

                //Borrar articulos con cantidad menor o igual a zero
                if (articuloP.getCantidad() <= 0) {
                    it.remove();
                    continue;
                }

                if (articuloP.isCreate()) {

                    articuloP.setProforma(proforma);
                    articuloP.setNoProforma(proforma.getNoDocumento());
                }

                if (articuloP.isUpdate()) {
                    articuloP.setProforma(proforma);
                    articuloP.setNoProforma(proforma.getNoDocumento());

                }


                //*************************************************************************
                //Acumular montos totales
                //*************************************************************************
                montoTotalAntesImpuesto = montoTotalAntesImpuesto.add(articuloP.getPrecioAntesImpuesto());
                montoTotalDescuento = montoTotalDescuento.add(articuloP.getDescuento());
                montoTotalIVA = montoTotalIVA.add(articuloP.getIva());
                montoTotalRetFuente = montoTotalRetFuente.add(articuloP.getRetencionFuente());
                montoTotalRetMunicipal = montoTotalRetMunicipal.add(articuloP.getRetencionMunicipal());
                montoTotalNeto = montoTotalNeto.add(articuloP.getPrecioNeto());

                //Actualizar renglon
                articuloP.setRenglon(renglon);

                renglon++;
            }

            //<Persist articulos proforma>
            proforma.setMontoBruto(montoTotalAntesImpuesto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setMontoDescuento(montoTotalDescuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setMontoIVA(montoTotalIVA.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setRetencionFuente(montoTotalRetFuente.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setRetencionMunicipal(montoTotalRetMunicipal.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setMontoNeto(montoTotalNeto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setArticulos(new HashSet<ArticuloProforma>(articulos));

            //Persistir nueva proforma
            proforma = proformaEAO.create(proforma);

            //Actualizar numero consecutivo de proforma si el numero es generado
            if (update_consecutivo) {
                almacen.setConsecutivo(noProforma);
                almacenEAO.update(almacen);
            }

            //Retornar proforma Guardada
            return proforma;

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

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                ArticuloFactura articulo = (ArticuloFactura) it.next();

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
    public void cobrarFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Cambiar Estado factura a PAGADO con parametros: [idFactura]: " + idFactura);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Factura factura = facturaEAO.findById(idFactura);
            EstadoMovimiento estadoPagado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.PAGADO.getEstado());

            //Validar datos generales de la factura
            if (!factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.IMPRESO.getEstado()) /*&&
                    !factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.PAGADO.getEstado())*/)
                throw new ManagerFacturacionServiceBusinessException("Factura no se encuentra en un estado valido para poder cobrar.");

            //Setting Estado Movimiento Impreso
            factura.setEstadoMovimiento(estadoPagado);

            //Update Factura
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
    public void imprimirFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Cambiar Estado factura a IMPRESA con parametros: [idFactura]: " + idFactura);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Factura factura = facturaEAO.findById(idFactura);
            EstadoMovimiento estadoImpreso = estadoMovimientoEAO.findByAlias(EstadosMovimiento.IMPRESO.getEstado());

            //Validar datos generales de la factura
            if (!factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()) &&
                    !factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.PAGADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("Factura no se encuentra en un estado valido para poder imprimir.");

            //Setting Estado Movimiento Impreso
            factura.setEstadoMovimiento(estadoImpreso);

            //Update Factura
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
    public void anularProforma(Integer idProforma) throws ManagerFacturacionServiceBusinessException, RemoteException {
        logger.debug("Anular proforma con parametros: [idProforma]: " + idProforma);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());
        try{
            //Preparar el contexto de ejecucion
            Proforma proforma = proformaEAO.findById(idProforma);
            EstadoMovimiento estadoAnulado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.ANULADO.getEstado());

            //Validar datos generales de la proforma
            if(!proforma.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("Proforma no se encuentra en estado valido para poder anular.");

            //Setting estado anulado
            proforma.setPorcDescuento(new BigDecimal("0.00"));
            proforma.setPorcRetFuente(new BigDecimal("0.00"));
            proforma.setPorcRetMunicipal(new BigDecimal("0.00"));
            proforma.setMontoDescuento(new BigDecimal("0.00"));
            proforma.setMontoBruto(new BigDecimal("0.00"));
            proforma.setMontoIVA(new BigDecimal("0.00"));
            proforma.setMontoNeto(new BigDecimal("0.00"));
            proforma.setRetencionFuente(new BigDecimal("0.00"));
            proforma.setRetencionMunicipal(new BigDecimal("0.00"));

            proforma.setEstadoMovimiento(estadoAnulado);

            proformaEAO.update(proforma);
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

                articulo.setCosto(new BigDecimal("0.00"));
                articulo.setCostoTotal(new BigDecimal("0.00"));

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
    public void eliminarProforma(Integer idProforma) throws ManagerFacturacionServiceBusinessException, RemoteException {
        logger.debug("Eliminar Proforma con parametros: [idProforma]: " + idProforma);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService((Roles.ROLFACTURACIONADMIN.toString()));

        try{
            //Preparar el contexto de ejecucion
            Proforma proforma = proformaEAO.findById(idProforma);

            //Validar Datos generales de la proforma
            if(!proforma.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()) &&
                    !proforma.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.ANULADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("Proforma no se encuentra en un estado valido para poder eliminar.");

            proformaEAO.remove(proforma.getId());
        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally{
            stopBusinessService(transaction);
        }
    }


    @Override
    public void eliminarFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException {
        logger.debug("Eliminar factura con parametros: [idFactura]: " + idFactura);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACIONADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            Factura factura = facturaEAO.findById(idFactura);

            //Validar datos generales de la factura
            if (!factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()) &&
                    !factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.ANULADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("Factura no se encuentra en un estado valido para poder eliminar.");

                //Eliminar movimientos de inventario de los productos solo si esta INGRESADO
            if (factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado())) {
                for (ArticuloFactura articulo : factura.getArticulos()) {
                    movimientoInventarioEAO.remove(articulo.getMovimientoInventario().getId());
                    articulo.setMovimientoInventario(null);
                }
            }

            facturaEAO.remove(factura.getId());

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally{
            stopBusinessService(transaction);
        }
    }

    @Override
    public Proforma modificarProforma(Integer idProforma, BigDecimal tasaCambio, Direccion direccionEntrega, BigDecimal porcDescuento,
                                      BigDecimal porcIva, BigDecimal porcRetFuente, BigDecimal porcRetMunicipal, Date fechaAlta,
                                      boolean exonerada, boolean retencionFuente, boolean retencionMunicipal, List<ArticuloProforma> articulos, String correo)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Modificar proforma con parámetros: [idProforma]: " + idProforma + ", [tasaCambio]: " + tasaCambio +
                ", [direccionEntrega]: " + direccionEntrega + ", [porcDescuento]: " + porcDescuento + ", [porcIva]: " +
                ", [porcRetFuente]: " + porcRetFuente + ", [porcRetMunicipal]: " + porcRetMunicipal + ", [fechaAlta]: " + fechaAlta +
                ", [exonerada]: " + exonerada + ", [retencionFuente]: " + retencionFuente + ", [retencionMunicipal]: " + retencionMunicipal +
                ", [correo]: " + correo);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try{

            //Preparar el contexto de ejecucion
            Proforma proforma = proformaEAO.findById(idProforma);

            if(!proforma.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()))
                throw new ManagerFacturacionServiceBusinessException("El estado de la proforma no es consistente para poder modificar");

            if(articulos.isEmpty())
                throw new ManagerFacturacionServiceBusinessException("Debes ingresar al menos un articulo");

            proforma.setTasaCambio(tasaCambio);
            proforma.setDireccionEntrega(direccionEntrega);
            proforma.setPorcDescuento(porcDescuento);
            proforma.setPorcIVA(porcIva);
            proforma.setPorcRetFuente(porcRetFuente);
            proforma.setPorcRetMunicipal(porcRetMunicipal);
            proforma.setFechaAlta(fechaAlta);
            proforma.setExonerada(exonerada);
            proforma.setRetencionF(retencionFuente);
            proforma.setRetencionM(retencionMunicipal);
            proforma.setCorreo(correo);

            //<Persistir Articulos de Proforma
            BigDecimal montoTotalAntesImpuesto = new BigDecimal("0.00");
            BigDecimal montoTotalNeto = new BigDecimal("0.00");
            BigDecimal montoTotalIVA = new BigDecimal("0.00");
            BigDecimal montoTotalDescuento = new BigDecimal("0.00");
            BigDecimal montoTotalRetFuente = new BigDecimal("0.00");
            BigDecimal montoTotalRetMunicipal = new BigDecimal("0.00");

            int renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {
                ArticuloProforma articulo = (ArticuloProforma) it.next();

                if(articulo.isCreate()){

                    //Borrar articulos con cantidad menor o igual a cero
                    if(articulo.getCantidad() <= 0){
                        it.remove();
                        continue;
                    }

                    articulo.setProforma(proforma);
                    articulo.setNoProforma(proforma.getNoDocumento());

                }
                if(articulo.isUpdate()){

                    //Borrar articulos con cantidad menor o igual a cero
                    if(articulo.getCantidad() <= 0){
                        //Remover el articulo si su Id es distinto de nulo
                        if (articulo.getId() != null)
                            articuloProformaEAO.remove(articulo.getId());
                        it.remove();
                        continue;
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

                //Actualizar Renglon
                articulo.setRenglon(renglon);
                renglon++;
            }
            //<Persist articulos proforma>
            proforma.setMontoBruto(montoTotalAntesImpuesto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setMontoDescuento(montoTotalDescuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setMontoIVA(montoTotalIVA.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setRetencionFuente(montoTotalRetFuente.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setRetencionMunicipal(montoTotalRetMunicipal.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setMontoNeto(montoTotalNeto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            proforma.setArticulos(new HashSet<ArticuloProforma>(articulos));

            proforma = proformaEAO.update(proforma);
            return proforma;

        } catch(PersistenceClassNotFoundException e){
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } catch(GenericPersistenceEAOException e){
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
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
    public Factura buscarFacturaPorId(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscar facturas por Id: [ID Factura]: " + idFactura);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Buscar factura por Identificador
            Factura factura = facturaEAO.findById(idFactura);

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
    public List<Proforma>buscarProformasPorFecha(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscando Proformas por rangos de Fecha: [fechaDesde]: " + fechaDesde + ", [fechaHasta]: " +
                fechaHasta);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try{
            //Validar campos de la busqueda
            if(fechaDesde == null)
                throw new ManagerFacturacionServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");
            if(fechaHasta == null)
                throw new ManagerFacturacionServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");
            //Buscar almacen del usuario - First Check Authorization for user
            boolean success = mgrAutorizacion.checkUserInRole(Roles.ROLFACTURACIONADMIN.toString());

            Almacen almacen = null;
            if(success) {
                almacen = almacenEAO.findById(idAlmacen);
            } else{
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

            return proformaEAO.findByFechas(fechaDesde, fechaHasta, almacen.getId());

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
    public List<Factura> buscarFacturasCobrosPorFechaNo(Long numeroFactura, Date fechaDesde, Date fechaHasta)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Return list of invoices
            List<Factura> facturas = facturaEAO.findByFechasCobrosNo(numeroFactura);

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

            for (Iterator it = facturas.iterator() ; it.hasNext();) {
                Factura factura = (Factura)it.next();

                if (factura.getFechaAlta().before(fechaDesde) || factura.getFechaAlta().after(fechaHasta)) {
                    it.remove();
                }
            }

            return facturas;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Factura> buscarFacturasCobrosPorFecha(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idTipoFactura)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

        logger.debug("Buscando facturas comerciales por rangos de fecha: [fechaDesde]: " + fechaDesde + ", [fechaHasta]: " +
                fechaHasta + ", [idAlmacen]: " + idAlmacen + ", [idTipoFactura]: " + idTipoFactura);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

           //Validar campos de la busqueda
            if (fechaDesde == null)
                throw new ManagerFacturacionServiceBusinessException("Debe seleccionar una fecha desde v\u00e1lida");

            if (fechaHasta == null)
                throw new ManagerFacturacionServiceBusinessException("Debe seleccionar una fecha hasta v\u00e1lida");

            //Get Estados Movimiento
            List<EstadoMovimiento> estadosMovimientos = new ArrayList<EstadoMovimiento>();

            EstadoMovimiento estadoIngresado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            EstadoMovimiento estadoImpreso = estadoMovimientoEAO.findByAlias(EstadosMovimiento.IMPRESO.getEstado());
            EstadoMovimiento estadoPagado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.PAGADO.getEstado());

            estadosMovimientos.add(estadoIngresado);
            estadosMovimientos.add(estadoImpreso);
            estadosMovimientos.add(estadoPagado);

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

            return facturaEAO.findByFechas(fechaDesde, fechaHasta, idAlmacen, idTipoFactura, estadosMovimientos);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Factura> buscarFacturasPorFecha(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idTipoFactura)
            throws ManagerFacturacionServiceBusinessException, RemoteException {

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

            //Buscar almacen del usuario - First check authorization for user
            boolean success = mgrAutorizacion.checkUserInRole(Roles.ROLFACTURACIONADMIN.toString());

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

            return facturaEAO.findByFechas(fechaDesde, fechaHasta, almacen.getId(), idTipoFactura);

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
    public List<ArticuloProforma>buscarArticulosProforma(Integer idProforma) throws ManagerFacturacionServiceBusinessException,
            RemoteException{
        logger.debug("Buscando articulos de Proforma con parametros: [IDPROFORMA]: " + idProforma);

        //Iniciar Servicio de Autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try{
            //Search Proforma a cliente
            Proforma proforma = proformaEAO.findById(idProforma);
            for(ArticuloProforma articulo : proforma.getArticulos()){
                Hibernate.initialize(articulo.getProducto());
                Hibernate.initialize(articulo.getProducto().getUnidadMedida());
            }
            return new ArrayList<ArticuloProforma>(proforma.getArticulos());
        } catch(GenericPersistenceEAOException e){
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        } finally{
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<ArticuloFactura> buscarArticulosFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException,
            RemoteException {

        logger.debug("Buscando articulos de factura con parametros: [IDFACTURA]: " + idFactura);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {

            //Search factura a cliente
            Factura factura = facturaEAO.findById(idFactura);

            for (ArticuloFactura articulo : factura.getArticulos()) {
                Hibernate.initialize(articulo.getProducto());
                Hibernate.initialize(articulo.getProducto().getUnidadMedida());
            }

            return new ArrayList<ArticuloFactura>(factura.getArticulos());

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
        }  finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void usuarioEditaDatosProforma() throws ManagerFacturacionServiceBusinessException, RemoteException {
        logger.debug("Verificar si Usuario tiene permisos de editar datos de proforma");

        try{
            //Verificar si usuario tiene permiso de editar datos de Proforma
            mgrAutorizacion.isUserInRole(Roles.ROLFACTURACIONADMIN.toString());
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerFacturacionServiceBusinessException(e.getMessage(), e);
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
     * Buscar Proforma con numero y almacen de facturacion
     *
     * @param noProforma, Numero de factura
     * @param idAlmacen, Identificador de Almacen
     * @throws ManagerFacturacionServiceBusinessException,
     *          Exception
     */

    private void proformaValidaParaRegistro(long noProforma, Integer idAlmacen) throws ManagerFacturacionServiceBusinessException {
        logger.debug("Busca Proforma por numero de proforma: [noProforma]: " + noProforma);
        try {

            //Buscar proforma por número de proforma
            Proforma proforma = proformaEAO.findByNumero(noProforma, idAlmacen);

            throw new ManagerFacturacionServiceBusinessException("Proforma con numero: [" + noProforma + "]" +
                    "se encuentra registrada, favor ingresar otro numero consecutivo de Proforma");

        } catch (PersistenceClassNotFoundException e) {
            logger.info("Número de proforma no se encuentra registrado. Proceder con el registro!!!");
        } catch (GenericPersistenceEAOException e) {
            logger.info("Numero de proforma no se encuentra registrado. Proceder con el registro!!!");
        }
    }

    /**
     * Buscar Factura con numero y almacen de facturacion
     *
     * @param noFactura, Numero de factura
     * @param idAlmacen, Identificador de Almacen
     * @throws ManagerFacturacionServiceBusinessException,
     *          Exception
     */
    private void facturaValidaParaRegistro(long noFactura, Integer idAlmacen) throws ManagerFacturacionServiceBusinessException {

        logger.debug("Busca factura por numero de factura: [noFactura]: " + noFactura);

        try {

            //Buscar factura por numero de factura
            Factura factura = facturaEAO.findByNumero(noFactura, idAlmacen);

            throw new ManagerFacturacionServiceBusinessException("Factura con numero: [" + noFactura + "]" +
                    "se encuentra registrada, favor ingresar otro numero consecutivo de factura");

        } catch (PersistenceClassNotFoundException e) {
            logger.info("Numero de factura no se encuentra registrado. Proceder con el registro!!!");
        } catch (GenericPersistenceEAOException e) {
            logger.info("Numero de factura no se encuentra registrado. Proceder con el registro!!!");
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
