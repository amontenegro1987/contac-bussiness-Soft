package contac.servicio.administracion;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.actividadEconomicaEAO.ActividadEconomicaEAO;
import contac.modelo.eao.actividadEconomicaEAO.ActividadEconomicaEAOPersistence;
import contac.modelo.eao.almacenEAO.AlmacenEAO;
import contac.modelo.eao.almacenEAO.AlmacenEAOPersistence;
import contac.modelo.eao.bancoEAO.BancoEAO;
import contac.modelo.eao.bancoEAO.BancoEAOPersistence;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAO;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAOPersistence;
import contac.modelo.eao.companiaEAO.CompaniaEAO;
import contac.modelo.eao.companiaEAO.CompaniaEAOPersistence;
import contac.modelo.eao.departamentoEAO.DepartamentoEAO;
import contac.modelo.eao.departamentoEAO.DepartamentoEAOPersistence;
import contac.modelo.eao.estadoMovimientoEAO.EstadoMovimientoEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.monedaEAO.MonedaEAO;
import contac.modelo.eao.monedaEAO.MonedaEAOPersistence;
import contac.modelo.eao.paisEAO.PaisEAO;
import contac.modelo.eao.paisEAO.PaisEAOPersistence;
import contac.modelo.eao.tasaCambioEAO.TasaCambioEAO;
import contac.modelo.eao.tasaCambioEAO.TasaCambioEAOPersistence;
import contac.modelo.eao.unidadMedidaEAO.UnidadMedidaEAO;
import contac.modelo.eao.unidadMedidaEAO.UnidadMedidaEAOPersistence;
import contac.modelo.entity.*;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerCatalogoServiceBusinessImpl;
import contac.servicio.seguridad.*;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-26-2010
 * Time: 10:44:16 PM
 */
public class ManagerAdministracionServiceBusinessImpl extends UnicastRemoteObject implements ManagerAdministracionServiceBusiness {

    /**
     * Servicio de log4j
     */
    private static final Logger logger = Logger.getLogger(ManagerAdministracionServiceBusinessImpl.class);

    /**
     * Acceso EAO
     */
    protected PaisEAO paisEAO;
    protected MonedaEAO monedaEAO;
    protected DepartamentoEAO departamentoEAO;
    protected CompaniaEAO companiaEAO;
    protected ClasificadorEAO clasificadorEAO;
    protected AlmacenEAO almacenEAO;
    protected EstadoMovimientoEAO estadoMovimientoEAO;
    protected ActividadEconomicaEAO actividadEconomicaEAO;
    protected UnidadMedidaEAO unidadMedidaEAO;
    protected BancoEAO bancoEAO;
    protected TasaCambioEAO tasaCambioEAO;

    /**
     * Acceso al Manager Autorizacion Service
     */
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;
    protected ManagerSeguridadServiceBusiness mgrSeguridad;
    protected ManagerCatalogoServiceBusiness mgrCatalogo;

    /**
     * Constructor default
     *
     * @throws java.rmi.RemoteException, Exception
     */
    public ManagerAdministracionServiceBusinessImpl() throws RemoteException {

        //Inicializar accesos DAO
        paisEAO = new PaisEAOPersistence();
        monedaEAO = new MonedaEAOPersistence();
        departamentoEAO = new DepartamentoEAOPersistence();
        companiaEAO = new CompaniaEAOPersistence();
        clasificadorEAO = new ClasificadorEAOPersistence();
        almacenEAO = new AlmacenEAOPersistence();
        actividadEconomicaEAO = new ActividadEconomicaEAOPersistence();
        unidadMedidaEAO = new UnidadMedidaEAOPersistence();
        bancoEAO = new BancoEAOPersistence();
        tasaCambioEAO = new TasaCambioEAOPersistence();
    }

    /**
     * Constructor de Manager Administracion con un manager de autorizacion
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws java.rmi.RemoteException, Exception
     */
    public ManagerAdministracionServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Llamar al constructor padre
        this();

        //Inicializar servicio de autorizacion
        this.mgrAutorizacion = mgrAutorizacion;
        mgrSeguridad = new ManagerSeguridadServiceBusinessImpl(this.mgrAutorizacion);
        mgrCatalogo = new ManagerCatalogoServiceBusinessImpl(this.mgrAutorizacion);
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerAdministracionServiceBusinessException {

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
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerAdministracionServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerAdministracionServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public void eliminarAlmacen(Integer idAlmacen) throws ManagerAdministracionServiceBusinessException, RemoteException {
        logger.debug("Eliminar almacen con parametros: [idAlmacen]: " + idAlmacen);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLCOMPANIAADMIN.toString());
        try{
            //Preparar el contexto de ejecucion
            Almacen almacen = almacenEAO.findById(idAlmacen);

            //<Validar Almacen tiene estado ACTIVO>---
            if (almacen.getEstatus() == EstadosActivacion.ACTIVO.getValue()){
                throw new ManagerAdministracionServiceBusinessException("El Almacen se encuentra en Estado Activo.");
            }
            //<Persistir cambios>
            almacenEAO.remove(idAlmacen);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
        throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (Exception e) {
        logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally{
          stopBusinessService(transaction);
        }
    }


    @Override
    public Almacen anularAlmacen(Integer idAlmacen) throws ManagerAdministracionServiceBusinessException, RemoteException {
        logger.debug("Anular almacen con parametros: [idAlmacen]: " + idAlmacen);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLCOMPANIAADMIN.toString());
        try{
            //Preparar el contexto de ejecucion
            Almacen almacen = buscarAlmacenPorId(idAlmacen);

            //<Validar Almacen tiene estado ACTIVO>---
            if (almacen.getEstatus() == EstadosActivacion.INACTIVO.getValue())
                throw new ManagerAdministracionServiceBusinessException("El Almacen se encuentra en Estado Inactivo.");

            //<Cambiar estado a INACTIVO>---
            almacen.setEstatus(EstadosActivacion.INACTIVO.getValue());
            almacen.setEstatusDesc("INACTIVO");

            //<Persistir cambios>---
            return almacenEAO.update(almacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Almacen activarAlmacen(Integer idAlmacen) throws ManagerAdministracionServiceBusinessException, RemoteException {
        logger.debug("Activar almacen con parametros: [idAlmacen]: " + idAlmacen);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLCOMPANIAADMIN.toString());
        try{
            //Preparar el contexto de ejecucion
            Almacen almacen = buscarAlmacenPorId(idAlmacen);

            //<Validar Almacen tiene estado INACTIVO>---
            if (almacen.getEstatus() == EstadosActivacion.ACTIVO.getValue())
                throw new ManagerAdministracionServiceBusinessException("El Almacen se encuentra en Estado ACTIVO.");

            //<Cambiar estado a ACTIVO>---
            almacen.setEstatus(EstadosActivacion.ACTIVO.getValue());
            almacen.setEstatusDesc("ACTIVO");

            //<Persistir cambios>---
            return almacenEAO.update(almacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }


    @Override
    public Compania registrarCompania(String nit, String razonSocial, String nombreComercial, Date fechaConstitucion,
                                      Date fechaAltaContribuyente, String eslogan, Direccion direccion, Set<Clasificador> clasificaciones,
                                      Moneda monedaReferencia, Set<Almacen> almacenes, byte tipoPersona, String tipoPersonaDesc, byte[] logotipo)
            throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Registrando compania con parametros: [nit]: " + nit + ", [razonSocial]: " + razonSocial +
                ", [nombreComercial]:" + nombreComercial + ", [fechaConstitucion]: " + fechaConstitucion +
                ", [fechaAltaContribuyente]: " + fechaAltaContribuyente + "[eslogan]: " + eslogan + ", [tipoPersona]:" +
                tipoPersona);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCOMPANIAADMIN.toString());

        try {

            //<Validar compania no se encuentre registrada>
            try {

                //Buscar compania por codigo NIT
                buscarCompaniaPorCodigoNIT(nit);

                //Lanzar exception de compania encontrada
                throw new ManagerAdministracionServiceBusinessException("Compania con numero NIT: " + nit + " se encuentra registrada.");

            } catch (CompaniaNoEncontradaException e) {
                //..No mostrar error de compania no encontrada correcto!!!
                logger.info("CORRECTO!!!...COMPANIA NO ENCONTRADA SE CONTINUA CON EL REGISTRO...");
            }

            //<Preparar el contexto de ejecuci�n>
            Compania compania = new Compania();
            compania.setNit(nit);
            compania.setRazonSocial(razonSocial);
            compania.setNombreComercial(nombreComercial);
            compania.setFechaConstitucion(fechaConstitucion);
            compania.setFechaAltaContribuyente(fechaAltaContribuyente);
            compania.setEslogan(eslogan);
            compania.setDireccion(direccion);
            compania.setClasificaciones(clasificaciones);
            compania.setMonedaReferencia(monedaReferencia);
            compania.setTipoPersona(tipoPersona);
            compania.setTipoPersonaDesc(tipoPersonaDesc);
            compania.setEstatus(EstadosActivacion.ACTIVO.getValue()); //<El registro se crea activado>...
            compania.setEstatusDesc(EstadosActivacion.ACTIVO.toString());

            CompaniaImageLOB image = new CompaniaImageLOB();
            image.setImage(logotipo);
            compania.setLogotipo(image);

            //1. <Persist compania>
            compania = companiaEAO.create(compania);

            //2. <Persist almacenes>
            for (Almacen almacen : almacenes) {
                almacen.setCompania(compania);
            }

            compania.setAlmacenes(almacenes);
            compania = companiaEAO.update(compania);

            //<Return entity>
            return compania;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Compania modificarCompania(Integer id, String nit, String razonSocial, String nombreComercial, Date fechaConstitucion,
                                      Date fechaAltaContribuyente, String eslogan, Direccion direccion, Set<Clasificador> clasificaciones,
                                      Moneda monedaReferencia, Set<Almacen> almacenes, byte tipoPersona, String tipoPersonaDesc, byte[] logotipo)
            throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Modificando compania con parametros: [id]: " + id + "[nit]: " + nit + ", [razonSocial]: " + razonSocial +
                ", [nombreComercial]:" + nombreComercial + ", [fechaConstitucion]: " + fechaConstitucion + ", [fechaAltaContribuyente]: " +
                fechaAltaContribuyente + "[eslogan]: " + eslogan + "[tipoPersona]: " + tipoPersona);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCOMPANIAADMIN.toString());

        try {

            //<Buscar compania a modificar>---
            Compania compania = buscarCompaniaPorId(id);

            //<Modificar datos>---
            compania.setNit(nit);
            compania.setRazonSocial(razonSocial);
            compania.setNombreComercial(nombreComercial);
            compania.setFechaConstitucion(fechaConstitucion);
            compania.setFechaAltaContribuyente(fechaAltaContribuyente);
            compania.setEslogan(eslogan);
            compania.setDireccion(direccion);
            compania.setClasificaciones(clasificaciones);
            compania.setMonedaReferencia(monedaReferencia);
            //compania.setAlmacenes(almacenes);
            compania.setTipoPersona(tipoPersona);
            compania.setTipoPersonaDesc(tipoPersonaDesc);

            CompaniaImageLOB image = compania.getLogotipo();
            image.setImage(logotipo);
            compania.setLogotipo(image);

            //<1. Update compania>---
            compania = companiaEAO.update(compania);

            //<2. Update almacenes>---
            for (Almacen almacen : almacenes) {
                almacen.setCompania(compania);
            }

            compania.setAlmacenes(almacenes);
            compania = companiaEAO.update(compania);

            return compania;

        } catch (CompaniaNoEncontradaException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Compania inactivarCompania(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Inactivar compania con parametros: [id]: " + id);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //<Buscar compania a inactivar>---
            Compania compania = buscarCompaniaPorId(id);

            //<Validar compania tiene estado ACTIVO>---
            if (compania.getEstatus() == EstadosActivacion.INACTIVO.getValue())
                throw new ManagerAdministracionServiceBusinessException("La compania se encuentra inactiva.");

            //<Cambiar estado a INACTIVO>---
            compania.setEstatus(EstadosActivacion.INACTIVO.getValue());

            //<Persistir cambios>---
            return companiaEAO.update(compania);

        } catch (CompaniaNoEncontradaException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }


    public Almacen buscarAlmacenPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscando Almacen con parametros: [id]: " + id);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            return this.almacenEAO.findById(id);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Compania buscarCompaniaPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscando compania con parametros: [id]: " + id);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            return this.companiaEAO.findById(id);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new CompaniaNoEncontradaException("id= " + id);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Compania buscarCompaniaPorCodigoNIT(String nit) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscando compania con parametros: [nit]: " + nit);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            return this.companiaEAO.findByCodigoNIT(nit);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new CompaniaNoEncontradaException("nit = " + nit);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Compania> buscarCompanias() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar todas las companias");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            return this.companiaEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Compania> buscarCompaniasActivas() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar companias activas");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            return this.companiaEAO.findByActiva();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Almacen registrarAlmacen(Integer idCompania, int codigo, String descripcion, String referencia, Direccion direccion,
                                    byte tipoAlmacen, String tipoAlmacenDesc, char serie, long consecutivo, String noAutorizacionComercial)
            throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Registrando almacen con parametros: [codigo]:" + codigo + ", [descripcion]:" + descripcion +
                ", [referencia]: " + referencia + ", [tipoAlmacen]: " + tipoAlmacen + ", [serie]: " + serie + ", [consecutivo]: " +
                consecutivo + ", [noAutorizacionComercial]: " + noAutorizacionComercial);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCOMPANIAADMIN.toString());

        try {

            //<Preparar el contexto>
            Compania compania = companiaEAO.findById(idCompania);

            //<Registrar almacen>
            Almacen almacen = new Almacen();
            almacen.setCodigo(codigo);
            almacen.setDescripcion(descripcion);
            almacen.setReferencia(referencia);
            almacen.setDireccion(direccion);
            almacen.setTipoAlmacen(tipoAlmacen);
            almacen.setTipoAlmacenDesc(tipoAlmacenDesc);
            almacen.setSerie(serie);
            almacen.setConsecutivo(consecutivo);
            almacen.setNoAutorizacionComercial(noAutorizacionComercial);
            almacen.setEstatus(EstadosActivacion.ACTIVO.getValue());
            almacen.setEstatusDesc(EstadosActivacion.ACTIVO.toString());

            return this.almacenEAO.create(almacen);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Banco registrarBanco(String nombreComercial, String razonSocial) throws ManagerAdministracionServiceBusinessException,
            RemoteException {

        logger.debug("Registrando nuevo banco con parametros: [nombreComercial]: " + nombreComercial + ", [razonSocial]: " +
                razonSocial);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Preparing execution context
            String username = mgrAutorizacion.getUsername();
            Compania compania = mgrSeguridad.buscarUsuarioPorLogin(username).getCompania();

            if (compania == null)
                throw new ManagerAdministracionServiceBusinessException("No se puede registrar banco. Usuario no esta asignado a una compan\u00eda.");

            //Creating entity banco
            Banco banco = new Banco();
            banco.setNombreComercial(nombreComercial);
            banco.setRazonSocial(razonSocial);
            banco.setCompania(compania);

            return this.bancoEAO.create(banco);

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Banco modificarBanco(Integer id, String nombreComercial, String razonSocial) throws ManagerAdministracionServiceBusinessException,
            RemoteException {

        logger.debug("Modificando registro banco con parametros: [id]: " + id + ", [nombreComercial]: " + nombreComercial +
                ", [razonSocial]: " + razonSocial);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Preparar el contexto
            Banco banco = bancoEAO.findById(id);

            //Actualizar datos
            banco.setNombreComercial(nombreComercial);
            banco.setRazonSocial(razonSocial);

            return bancoEAO.update(banco);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void removerBanco(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Remover registro de banco con parametros: [id]: " + id);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Preparar el contexto
            Banco banco = bancoEAO.findById(id);

            //Remove
            bancoEAO.remove(banco.getId());

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public TasaCambio registrarTasaCambio(int tipoTasaCambio, BigDecimal tasaConversion, Date fechaConversion, Integer idMonedaReferencia,
                                      Integer idMonedaConversion, Integer idBanco, boolean activaFacturacion) 
            throws ManagerAdministracionServiceBusinessException, RemoteException {


        logger.debug("Crear tasa de cambio con parametros: [tipoTasaCambio]: " + tipoTasaCambio + ", [tasaConversion]: " +
                tasaConversion + ", [fechaConversion]: " + fechaConversion + ", [monedaReferencia]: " + idMonedaReferencia
                + ", [monedaConversion]: " + idMonedaConversion + ", [banco]: " + idBanco);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Preparar el contexto
            Moneda monedaReferencia = monedaEAO.findById(idMonedaReferencia);
            Moneda monedaConversion = monedaEAO.findById(idMonedaConversion);
            Banco banco = bancoEAO.findById(idBanco);

            //Validar tasa cambio mayor que zero
            if (tasaConversion.doubleValue() <= 0.00)
                throw new ManagerAdministracionServiceBusinessException("Tasa conversion debe ser mayor que ZERO.");

            //Creating entity tasa de cambio
            TasaCambio tasaCambio = new TasaCambio();
            tasaCambio.setTipoTasaCambio(tipoTasaCambio);
            tasaCambio.setTasaConversion(tasaConversion.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            tasaCambio.setFechaConversion(fechaConversion);
            tasaCambio.setMonedaReferencia(monedaReferencia);
            tasaCambio.setMonedaConversion(monedaConversion);
            tasaCambio.setActivaFacturacion(activaFacturacion);
            tasaCambio.setBanco(banco);

            return tasaCambioEAO.create(tasaCambio);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public TasaCambio modificarTasaCambio(Integer idTasaCambio, int tipoTasaCambio, BigDecimal tasaConversion, Date fechaConversion,
                                          Integer idMonedaReferencia, Integer idMonedaConversion, Integer idBanco,
                                          boolean activaFacturacion) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Modificar tasa de cambio con parametros: [idTasaCambio]: " + idTasaCambio + ", [tipoTasaCambio]: " +
                tipoTasaCambio + ", [tasaConversion]: " + tasaConversion + ", [fechaConversion]: " + fechaConversion +
                ", [idMonedaReferencia]: " + idMonedaReferencia + ", [idMonedaConversion]: " + idMonedaConversion + ", [Banco]: " +
                idBanco + ", [activaFacturacion]: " + activaFacturacion);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Preparar el contexto
            TasaCambio tasaCambio = tasaCambioEAO.findById(idTasaCambio);
            Moneda monedaReferencia = monedaEAO.findById(idMonedaReferencia);
            Moneda monedaConversion = monedaEAO.findById(idMonedaConversion);
            Banco banco = bancoEAO.findById(idBanco);

            //Modificando entidad tasa de cambio
            tasaCambio.setTipoTasaCambio(tipoTasaCambio);
            tasaCambio.setFechaConversion(fechaConversion);
            tasaCambio.setTasaConversion(tasaConversion.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            tasaCambio.setMonedaReferencia(monedaReferencia);
            tasaCambio.setMonedaConversion(monedaConversion);
            tasaCambio.setActivaFacturacion(activaFacturacion);
            tasaCambio.setBanco(banco);

            return tasaCambioEAO.update(tasaCambio);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void removerTasaCambio(Integer idTasaCambio) throws ManagerAdministracionServiceBusinessException, Exception {

        logger.debug("Removiendo tasa de cambio con parametros: [idTasaCambio]: " + idTasaCambio);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Preparar contexto de ejecucion
            tasaCambioEAO.remove(idTasaCambio);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Banco> buscarBancos() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscando todos los bancos");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        try {

            //Retornar listado de bancos
            return bancoEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<TasaCambio> buscarTasasCambio() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscando todas las tasas de cambio");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        try {

            //Retornar listado de tasas de cambio
            return tasaCambioEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<TasaCambio> buscarTasasCambioMesActual() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscando tasas de cambio del mes actual");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Obteniendo fecha inicio y final del mes actual
            Calendar calendario = GregorianCalendar.getInstance();
            calendario.setTime(new Date());

            int firstDayOfMonth = calendario.getActualMinimum(Calendar.DAY_OF_MONTH);
            int lastDayOfMonth = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

            //Setting first date of the month with Hour-Minute-Second equals ZERO
            calendario.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), firstDayOfMonth, 0, 0, 0);
            Date firstDateOfMonth = calendario.getTime();

            //Setting last date of the month with Hour-Minute-Second equals ZERO
            calendario.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), lastDayOfMonth, 0, 0, 0);
            Date lastDateOfMonth = calendario.getTime();

            //Return tasas de cambio del mes actual
            return this.tasaCambioEAO.findByFechas(firstDateOfMonth, lastDateOfMonth);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Pais> buscarPaises() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar todos los paises");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACSERVICEAPP.toString());

        try {

            return this.paisEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Pais buscarPaisPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar pais con parametros: [Id]: " + id);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return this.paisEAO.findById(id);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Moneda> buscarMonedas() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.info("Buscar todas las monedas");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACSERVICEAPP.toString());

        try {

            return this.monedaEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Moneda> buscarMonedasReferencia() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar moneda de referencia para la compania actual");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Preparing execution context
            String username = mgrAutorizacion.getUsername();
            Compania compania = mgrSeguridad.buscarUsuarioPorLogin(username).getCompania();

            if (compania == null)
                return new ArrayList<Moneda>();

            List<Moneda> monedas = new ArrayList<Moneda>();
            monedas.add(compania.getMonedaReferencia());

            return monedas;

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Moneda buscarMonedaPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar moneda con parametros: [Id]: " + id);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return this.monedaEAO.findById(id);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<ActividadEconomica> buscarActividadesEconomicas() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar actividades economicas");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACSERVICEAPP.toString());

        try {

            return this.actividadEconomicaEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<UnidadMedida> buscarUnidadesMedida() throws ManagerAdministracionServiceBusinessException, RemoteException {

        logger.debug("Buscar unidades de medida");

        //Inicar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACSERVICEAPP.toString());

        try {

            return this.unidadMedidaEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerAdministracionServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }
}
