package contac.servicio.catalogo;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.actividadEconomicaEAO.ActividadEconomicaEAO;
import contac.modelo.eao.actividadEconomicaEAO.ActividadEconomicaEAOPersistence;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAO;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAOPersistence;
import contac.modelo.eao.companiaEAO.CompaniaEAO;
import contac.modelo.eao.companiaEAO.CompaniaEAOPersistence;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.lineaEAO.LineaEAO;
import contac.modelo.eao.lineaEAO.LineaEAOPersistence;
import contac.modelo.eao.productoEAO.ProductoEAO;
import contac.modelo.eao.productoEAO.ProductoEAOPersistence;
import contac.modelo.eao.productoModificacionEAO.ProductoModificacionEAO;
import contac.modelo.eao.productoModificacionEAO.ProductoModificacionEAOPersistence;
import contac.modelo.eao.proveedorEAO.ProveedorEAO;
import contac.modelo.eao.proveedorEAO.ProveedorEAOPersistence;
import contac.modelo.eao.unidadMedidaEAO.UnidadMedidaEAO;
import contac.modelo.eao.unidadMedidaEAO.UnidadMedidaEAOPersistence;
import contac.modelo.entity.Clasificador;
import contac.modelo.entity.Linea;
import contac.modelo.entity.Roles;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Manager Catalogo servicio business. Implementa todas las operaciones sobre catalogo de productos y clasificadores
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-04-2010
 * Time: 11:14:13 PM
 */
public class ManagerCatalogoServiceBusinessImpl extends UnicastRemoteObject implements ManagerCatalogoServiceBusiness {


    /**
     * Servicio de log4j
     */
    private static final Logger logger = Logger.getLogger(ManagerCatalogoServiceBusinessImpl.class);

    /**
     * Acceso capa DAO
     */
    protected ProveedorEAO proveedorEAO;
    protected UnidadMedidaEAO unidadMedidaEAO;
    protected ActividadEconomicaEAO actividadEconomicaEAO;
    protected ClasificadorEAO clasificadorEAO;
    protected ProductoEAO productoEAO;
    protected ProductoModificacionEAO productoModificacionEAO;
    protected CompaniaEAO companiaEAO;
    protected LineaEAO lineaEAO;

    /**
     * Acceso al Manager Autorizacion Service
     */
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;

    /**
     * Constructor default
     *
     * @throws java.rmi.RemoteException, Exception
     */
    public ManagerCatalogoServiceBusinessImpl() throws RemoteException {

        logger.info("Creando servicio de catalogo");

        //Inicializar accesos DAO
        proveedorEAO = new ProveedorEAOPersistence();
        unidadMedidaEAO = new UnidadMedidaEAOPersistence();
        actividadEconomicaEAO = new ActividadEconomicaEAOPersistence();
        clasificadorEAO = new ClasificadorEAOPersistence();
        productoEAO = new ProductoEAOPersistence();
        productoModificacionEAO = new ProductoModificacionEAOPersistence();
        companiaEAO = new CompaniaEAOPersistence();
        lineaEAO = new LineaEAOPersistence();

    }

    /**
     * Constructor de Manager Catalogo con un usuario autenticado
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws java.rmi.RemoteException, Exception
     */
    public ManagerCatalogoServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Llamar al constructor padre
        this();

        //Inicializar servicio de autorizacion
        this.mgrAutorizacion = mgrAutorizacion;
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerCatalogoServiceBusinessException {

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
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerCatalogoServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerCatalogoServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        }
    }


    @Override
    public List<Linea> buscarLineas() throws ManagerCatalogoServiceBusinessException, RemoteException {

        logger.debug("Buscando lineas de producto");

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return lineaEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public Clasificador buscarClasificadorPorId(Integer id) throws ManagerCatalogoServiceBusinessException, 
            RemoteException {

        logger.debug("Buscando clasificador con parametro: [id]: " + id);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return clasificadorEAO.findById(id);

        } catch (PersistenceClassNotFoundException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public Clasificador buscarClasificadorPorCodigoCBS(long cbs) throws ManagerCatalogoServiceBusinessException, 
            RemoteException {

        logger.debug("Buscando clasificador con parametros: [cbs]: " + cbs);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {
            //Retornar codigo por cbs
            return clasificadorEAO.buscarPorCodigoCbs(cbs);

        } catch (GenericPersistenceEAOException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<Clasificador> buscarClasificadores() throws ManagerCatalogoServiceBusinessException, RemoteException {
        
        logger.debug("Buscando clasificadores");

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());
        
        try {
            
            List<Clasificador> clasificadores = clasificadorEAO.findAll();
            
            return clasificadores;
            
        } catch (GenericPersistenceEAOException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<Clasificador> buscarClasificadoresPorTipo(int tipoClasificador) throws ManagerCatalogoServiceBusinessException, 
            RemoteException {

        logger.debug("Buscando clasificadores con parametros: [tipoClasificador]: " + tipoClasificador);

        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            List<Clasificador> clasificadores = clasificadorEAO.buscarPorTipoClasificador(tipoClasificador);

            return clasificadores;

        } catch (GenericPersistenceEAOException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }

    @Override
    public List<Clasificador> buscarClasificadoresCBS(long cbs, String descripcion) throws ManagerCatalogoServiceBusinessException, 
            RemoteException {
        
        logger.debug("Buscar clasificadores con parametros: [cbs]: " + cbs + ", [descripcion]: " + descripcion);
        
        //Iniciar servicio authentication
        boolean value = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());
        
        try {

            List<Clasificador> clasificadores = clasificadorEAO.buscarPorCodigoCbsDescripcion(cbs, descripcion);
            
            return clasificadores;
            
        } catch (GenericPersistenceEAOException e) {
            rollbackBusinessService();
            logger.error(e.getMessage(), e);
            throw new ManagerCatalogoServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(value);
        }
    }
}
