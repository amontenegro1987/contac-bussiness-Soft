package contac.servicio.proveedores;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.monedaEAO.MonedaEAO;
import contac.modelo.eao.monedaEAO.MonedaEAOPersistence;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.ordenCompraEAO.OrdenCompraEAO;
import contac.modelo.eao.ordenCompraEAO.OrdenCompraEAOPersistence;
import contac.modelo.eao.proveedorEAO.ProveedorEAO;
import contac.modelo.eao.proveedorEAO.ProveedorEAOPersistence;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAO;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAOPersistence;
import contac.modelo.entity.*;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * ManagerProveedoresServiceBusinessImpl . Implementa todas las operaciones soportadas para el proveedor
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-16-2010
 * Time: 11:23:10 PM
 */
public class ManagerProveedoresServiceBusinessImpl extends UnicastRemoteObject implements ManagerProveedoresServiceBusiness {

    /**
     * Servicio de log4j
     */
    private static final Logger logger = Logger.getLogger(ManagerProveedoresServiceBusinessImpl.class);

    /**
     * Acceso EAO
     */
    private ProveedorEAO proveedorEAO;

    /**
     * Acceso al Manager Autorizacion Service
     */
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;

    /**
     * Acceso EAO
     */
    protected MonedaEAO monedaEAO;

    /**
     * Acceso EAO
     */

    protected EstadoMovimientoEAO estadoMovimientoEAO;

    /**
     * Acceso EAO
     */
    protected OrdenCompraEAO ordenCompraEAO;
    /**
     * Constructor default         *
     *
     * @throws RemoteException, Exception
     */
    public ManagerProveedoresServiceBusinessImpl() throws RemoteException {
        proveedorEAO = new ProveedorEAOPersistence();
        ordenCompraEAO = new OrdenCompraEAOPersistence();
        monedaEAO = new MonedaEAOPersistence();
        estadoMovimientoEAO = new EstadoMovimientoEAOPersistence();
    }

    /**
     * Constructor de un Manager de Proveedores con un Manager de autorizacion
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerProveedoresServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Call constructor
        this();

        //Setting manager autorizacion
        this.mgrAutorizacion = mgrAutorizacion;

    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerProveedoresServiceBusinessException {

        try {

            //Iniciar servicio de autorizacion
            if (mgrAutorizacion == null)
                logger.error("Servicio de autenticacion inactivo");

            //Autorizar usuario
            mgrAutorizacion.isUserInRole(rolname);

            //Iniciar servicio transaccional
            return PersistenceManagementServiceFactory.beginTransaction();

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerProveedoresServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerProveedoresServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<Proveedor> buscarProveedores() throws ManagerProveedoresServiceBusinessException, RemoteException {

        logger.debug("Buscando proveedores");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return this.proveedorEAO.findAllOrderByCodigo();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Proveedor> buscarProveedores(long codigo, String razonSocial) throws ManagerProveedoresServiceBusinessException, RemoteException {

        logger.debug("Buscando proveedores con parametros: [codigo]: " + codigo + ", [razonSocial]: " + razonSocial);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return this.proveedorEAO.find(codigo, razonSocial);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Proveedor buscarProveedorPorCodigo(long codigo) throws ManagerProveedoresServiceBusinessException, RemoteException {

        logger.debug("Buscando proveedor con parametros: [Codigo]: " + codigo);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return this.proveedorEAO.findByCodigo(codigo);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ProveedorNoEncontradoException(e.getMessage());
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Proveedor registrarProveedor(long codigo, String razonSocial, String nombreComercial, String nit, String cuenta,
                                        BigDecimal descuento, int plazoCredito, BigDecimal limiteCredito, byte tipoPersona,
                                        ActividadEconomica actividadEconomica, Direccion direccion)
            throws ManagerProveedoresServiceBusinessException, RemoteException {

        logger.debug("Registrando proveedor con parametros: [codigo]: " + codigo + ", [razonSocial]: " + razonSocial +
                ", [nombreComercial]: " + nombreComercial + ", [nit]: " + nit + ", [No. cuenta]:" + cuenta + ", [descuento]: " +
                descuento + ", [plazoCredito]: " + plazoCredito + ", [limiteCredito]: " + limiteCredito + ", [tipoPersona]: " +
                tipoPersona);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLPROVEEDORADMIN.toString());

        try {

            //<Validar proveedor no se encuentra registrado>
            try {

                buscarProveedorPorCodigo(codigo);

                //Lanzar exception de proveedor encontrado
                throw new ManagerProveedoresServiceBusinessException("Proveedor con codigo: " + codigo + ", se encuentra registrado");

            } catch (ProveedorNoEncontradoException e) {
                //No mostrar error de proveedor no encontrado, correcto!!!
                logger.info("CORRECTO, PROVEEDOR NO ENCONTRADO SE PROCEDE CON EL REGISTRO!!!");
            }

            //<Preparar el contexto de ejecucion>
            Proveedor proveedor = new Proveedor();
            proveedor.setCodigo(codigo);
            proveedor.setTipoPersona(tipoPersona);
            proveedor.setNombreComercial(nombreComercial);
            proveedor.setRazonSocial(razonSocial);
            proveedor.setActividadEconomica(actividadEconomica);
            proveedor.setCuenta(cuenta);
            proveedor.setPlazoCredito(plazoCredito);
            proveedor.setLimiteCredito(limiteCredito);
            proveedor.setDescuento(descuento);
            proveedor.setDireccion(direccion);
            proveedor.setEstatus(EstadosActivacion.ACTIVO.getValue());

            //<Persist entity>
            proveedor = proveedorEAO.create(proveedor);

            //<Return entity>
            return proveedor;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Proveedor modificarProveedor(Integer id, long codigo, String razonSocial, String nombreComercial, String nit,
                                        String cuenta, BigDecimal descuento, int plazoCredito, BigDecimal limiteCredito,
                                        byte tipoPersona, ActividadEconomica actividadEconomica, Direccion direccion)
            throws ManagerProveedoresServiceBusinessException, RemoteException {

        logger.debug("Modificando proveedor con parametros: [id]: " + id + ", [codigo]: " + codigo + ", [razonSocial]: " +
                razonSocial + ", [nombreComercial]: " + nombreComercial + ", [nit]: " + nit + ", [cuenta]: " + cuenta +
                ", [descuento]: " + descuento + ", [plazoCredito]: " + plazoCredito + ", [limiteCredito]: " + limiteCredito +
                ", [tipoPersona]: " + tipoPersona);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLPROVEEDORADMIN.toString());

        try {

            //<Preparar el contexto>
            Proveedor proveedor = proveedorEAO.findById(id);

            //<Modificar datos>
            proveedor.setCodigo(codigo);
            proveedor.setRazonSocial(razonSocial);
            proveedor.setNombreComercial(nombreComercial);
            proveedor.setNit(nit);
            proveedor.setCuenta(cuenta);
            proveedor.setDescuento(descuento);
            proveedor.setPlazoCredito(plazoCredito);
            proveedor.setLimiteCredito(limiteCredito);
            proveedor.setTipoPersona(tipoPersona);
            proveedor.setActividadEconomica(actividadEconomica);
            proveedor.setDireccion(direccion);

            return proveedorEAO.update(proveedor);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void removerProveedor(Integer id) throws ManagerProveedoresServiceBusinessException, RemoteException {

        logger.debug("Remover proveedor con parametros: [id]: " + id);

        //Init business service
        boolean transaction = initBusinessService(Roles.ROLPROVEEDORADMIN.toString());

        try {

            //TODO: Validar datos requeridos donde se encuentre el proveedor

            //<Eliminar proveedor>
            proveedorEAO.remove(id);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    /**
     * Buscar Orden de Compra con numero
     *
     * @param noOrdenCompra, Numero de Orden de Compra
     * @throws ManagerProveedoresServiceBusinessException,
     *          Exception
     */

    private void ordenCompraValidaParaRegistro(long noOrdenCompra) throws ManagerProveedoresServiceBusinessException {
        logger.debug("Busca Orden Compra por numero de Orden de Compra: [noProforma]: " + noOrdenCompra);
        try {

            //Buscar Orden de Compra por número de Orden de Compra
            OrdenCompra ordenCompra = ordenCompraEAO.findByNumero(noOrdenCompra);

            throw new ManagerProveedoresServiceBusinessException("Proforma con numero: [" + noOrdenCompra + "]" +
                    "se encuentra registrada, favor ingresar otro numero consecutivo de Orden de Compra");

        } catch (PersistenceClassNotFoundException e) {
            logger.info("Número de orden de Compra no se encuentra registrado. Proceder con el registro!!!");
        } catch (GenericPersistenceEAOException e) {
            logger.info("Numero de Orden de Compra no se encuentra registrado. Proceder con el registro!!!");
        }
    }

    @Override
    public List<ArticuloOrdenCompra> buscarArticulosOrdenCompra(Integer idOrdenCompra) throws ManagerProveedoresServiceBusinessException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OrdenCompra crearOrdenCompra(long noOrdenCompra, Integer idProveedor,
                                  BigDecimal porcDescuento, BigDecimal porcIva,
                                  BigDecimal porcRetFuente, BigDecimal porcRetMunicipal,
                                  BigDecimal tasaCambio,    String nombreProveedor, Integer idMoneda, Direccion direccionEntrega,
                                 boolean exonerada, boolean retencionFuente, boolean retencionMunicipal,
                                  List<ArticuloOrdenCompra> articulos, Date fechaAlta, Date fechaRequerida, String descripcionCompra, String facturaCompraProveedor)
            throws ManagerProveedoresServiceBusinessException, RemoteException {
         System.out.println("Id Moneda: " + idMoneda);
        logger.debug("Creando Orden de Compra con parametros: [noOrdenCompra]: " + noOrdenCompra  +
                "[idProveedor]: " + idProveedor +  ", [fechaAlta]: " + fechaAlta +  ", [fechaRequerida]: " + fechaRequerida +
                ", [porcRetFuente]: " + porcRetFuente + ", [porcRetMunicipal]: " + porcRetMunicipal +
                ", [idMoneda]: " + idMoneda + ", [direccionEntrega]: " +  direccionEntrega +
                ", [exonerada]: " + exonerada + ", [retencionFuente]" + retencionFuente +
                ", [retencionMunicipal]: " + retencionMunicipal + "[nombreProveedor]: " + nombreProveedor +
               ", [tasaCambio]: " + tasaCambio + "[descripcioncompra]: " + descripcionCompra + "[facturaCompraProveedor]: " + facturaCompraProveedor);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLFACTURACION.toString());

        try {
            //Preparar el contexto de ejecucion
            Proveedor proveedor = proveedorEAO.findById(idProveedor);
            //Cliente cliente = clienteEAO.findById(idCliente);
            Moneda moneda = monedaEAO.findById(idMoneda);
            EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());

            // EstadoMovimiento estado = estadoMovimientoEAO.findByAlias(EstadosMovimiento.INGRESADO.getEstado());
            Date fecha1 = new Date();

            //Actualizar consecutivo de Orden de Compra en el almacen
            boolean update_consecutivo = true;

            //Validar consistencia de datos
            if (fechaAlta.after(new Date()))
                throw new ManagerProveedoresServiceBusinessException("Fecha de alta no puede ser posterior al dia de hoy.");

            if (articulos.isEmpty())
                throw new ManagerProveedoresServiceBusinessException("Debes ingresar al menos un articulo");


            //Persistir Proforma
            OrdenCompra ordenCompra = new OrdenCompra();
            ordenCompra.setNoDocumento(noOrdenCompra);
            ordenCompra.setFechaAlta(fechaAlta);
            ordenCompra.setFechaRequerida(fechaRequerida);
            ordenCompra.setNombreProveedor(nombreProveedor);
            ordenCompra.setTasaCambio(tasaCambio);
            ordenCompra.setMoneda(moneda);
            ordenCompra.setProveedor(proveedor);
            ordenCompra.setDescripcionCompra(descripcionCompra);
            ordenCompra.setFacturaCompraProveedor(facturaCompraProveedor);
            //ordenCompra.setNombreCliente(cliente.getRazonSocial());
            ordenCompra.setExonerada(exonerada);
            ordenCompra.setRetencionF(retencionFuente);
            ordenCompra.setRetencionM(retencionMunicipal);
            ordenCompra.setPorcDescuento(porcDescuento);
            ordenCompra.setPorcIVA(porcIva);
            ordenCompra.setPorcRetFuente(porcRetFuente);
            ordenCompra.setPorcRetMunicipal(porcRetMunicipal);
            ordenCompra.setMontoBruto(new BigDecimal("0.00"));
            ordenCompra.setMontoDescuento(new BigDecimal("0.00"));
            ordenCompra.setMontoNeto(new BigDecimal("0.00"));
            ordenCompra.setMontoIVA(new BigDecimal("0.00"));
            ordenCompra.setRetencionFuente(new BigDecimal("0.00"));
            ordenCompra.setRetencionMunicipal(new BigDecimal("0.00"));
            ordenCompra.setEstadoMovimiento(estado);

            if (direccionEntrega != null)
                ordenCompra.setDireccionEntrega(direccionEntrega);
            else
                ordenCompra.setDireccionEntrega(proveedor.getDireccion());

            //<Persistir articulos de Orden de Compra>
            BigDecimal montoTotalAntesImpuesto = new BigDecimal("0.00");
            BigDecimal montoTotalNeto = new BigDecimal("0.00");
            BigDecimal montoTotalIVA = new BigDecimal("0.00");
            BigDecimal montoTotalDescuento = new BigDecimal("0.00");
            BigDecimal montoTotalRetFuente = new BigDecimal("0.00");
            BigDecimal montoTotalRetMunicipal = new BigDecimal("0.00");

            int renglon = 1;

            for (Iterator it = articulos.iterator(); it.hasNext(); ) {

                //Obteniendo articulo Orden de Compra

                ArticuloOrdenCompra articuloP = (ArticuloOrdenCompra) it.next();

                //Borrar articulos con cantidad menor o igual a zero
                if (articuloP.getCantidad() <= 0) {
                    it.remove();
                    continue;
                }

                if (articuloP.isCreate()) {

                    articuloP.setOrdenCompra(ordenCompra);
                    articuloP.setNoOrdenCompra(ordenCompra.getNoDocumento());
                }

                if (articuloP.isUpdate()) {
                    articuloP.setOrdenCompra(ordenCompra);
                    articuloP.setNoOrdenCompra(ordenCompra.getNoDocumento());

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

            //<Persist articulos Orden de Compra>
            ordenCompra.setMontoBruto(montoTotalAntesImpuesto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenCompra.setMontoDescuento(montoTotalDescuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenCompra.setMontoIVA(montoTotalIVA.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenCompra.setRetencionFuente(montoTotalRetFuente.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenCompra.setRetencionMunicipal(montoTotalRetMunicipal.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenCompra.setMontoNeto(montoTotalNeto.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            ordenCompra.setArticulos(new HashSet<ArticuloOrdenCompra>(articulos));

            //Persistir nueva Orden de Compra
            ordenCompra = ordenCompraEAO.create(ordenCompra);

            //Retornar Orden de Compra guardada
            return ordenCompra;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerProveedoresServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }


    @Override
    public boolean isProveedorParaRegistro(long codigo) throws RemoteException {

        logger.debug("Validando proveedor registrado con parametros: [codigo]: " + codigo);

        try {

            //Buscar Proveedor por codigo
            buscarProveedorPorCodigo(codigo);

            //Return false proveedor encontrado
            return false;

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.info("PROVEEDOR NO ENCONTRADO!!!...");
            return true;
        }
    }
}
