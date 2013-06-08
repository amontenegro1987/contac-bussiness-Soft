package contac.servicio.proveedores;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.proveedorEAO.ProveedorEAO;
import contac.modelo.eao.proveedorEAO.ProveedorEAOPersistence;
import contac.modelo.entity.*;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
     * Constructor default         *
     *
     * @throws RemoteException, Exception
     */
    public ManagerProveedoresServiceBusinessImpl() throws RemoteException {
        proveedorEAO = new ProveedorEAOPersistence();
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
