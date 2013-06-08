package contac.servicio.implementacion;

import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.aplicacion.AplicacionBaseRemote;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.clientes.ManagerClientesServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.proveedores.ManagerProveedoresServiceBusiness;
import contac.servicio.seguridad.ManagerSeguridadServiceBusiness;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface para accesar los diferentes servicios de implementacion
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-11-2010
 * Time: 11:07:11 PM
 */
public interface ManagerServiceFactory extends Remote {

    /**
     * Return user authenticated name
     *
     * @return String
     * @throws RemoteException, Exception
     */
    public String getUserName() throws RemoteException;

    /**
     * Retorna servicio application base
     *
     * @return AplicacionBaseRemote
     * @throws RemoteException, Exception
     */
    public AplicacionBaseRemote getApplicationBaseRemote() throws RemoteException;

    /**
     * Retorna un ManagerSeguridadServiceBusines
     *
     * @return ManagerSeguridadServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerSeguridadServiceBusiness getManagerSeguridadServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerCatalogoServiceBusiness
     *
     * @return ManagerCatalogoServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerCatalogoServiceBusiness getManagerCatalogoServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerAdministracionServiceBusiness
     *
     * @return ManagerAdministracionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerAdministracionServiceBusiness getManagerAdministracionServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerProveedoresServiceBusiness
     *
     * @return ManagerProveedoresServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerProveedoresServiceBusiness getManagerProveedoresServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerProductoServiceBusiness
     *
     * @return ManagerProductoServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerProductoServiceBusiness getManagerProductoServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerInventarioServiceBusiness
     *
     * @return ManagerInventarioServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerInventarioServiceBusiness getManagerInventarioServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerClientesServiceBusiness
     *
     * @return ManagerClientesServiceBusiness
     * @throws RemoteException, Exception|
     */
    public ManagerClientesServiceBusiness getManagerClientesServiceBusiness() throws RemoteException;

    /**
     * Retorna un ManagerFacturacionServiceBusiness
     *
     * @return ManagerFacturacionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerFacturacionServiceBusiness getManagerFacturacionServiceBusiness() throws RemoteException;
}
