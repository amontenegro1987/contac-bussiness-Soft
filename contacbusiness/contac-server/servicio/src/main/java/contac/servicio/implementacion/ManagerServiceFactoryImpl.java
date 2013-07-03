package contac.servicio.implementacion;

import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessImpl;
import contac.servicio.aplicacion.AplicacionBase;
import contac.servicio.aplicacion.AplicacionBaseRemote;
import contac.servicio.autenticacion.ContacUserCredential;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerCatalogoServiceBusinessImpl;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusinessImpl;
import contac.servicio.clientes.ManagerClientesServiceBusiness;
import contac.servicio.clientes.ManagerClientesServiceBusinessImpl;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessImpl;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessImpl;
import contac.servicio.proveedores.ManagerProveedoresServiceBusiness;
import contac.servicio.proveedores.ManagerProveedoresServiceBusinessImpl;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessImpl;
import contac.servicio.seguridad.ManagerSeguridadServiceBusiness;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessImpl;

import javax.security.auth.Subject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Servicio de implementacion para accesar los servicios
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-11-2010
 * Time: 11:07:21 PM
 */
public class ManagerServiceFactoryImpl extends UnicastRemoteObject implements ManagerServiceFactory {

    //*****************************************************************
    //Usuario asociado con el proxy
    private Subject userAuth;
    //*****************************************************************

    //***************************************************
    //Servicio aplicativo
    private AplicacionBaseRemote aplicacionBase;

    //***************************************************
    //Servicios Asociados con el Proxy
    private ManagerSeguridadServiceBusiness mgrSeguridad;
    private ManagerAutorizacionServiceBusiness mgrAutorizacion;
    private ManagerCatalogoServiceBusiness mgrCatalogo;
    private ManagerAdministracionServiceBusiness mgrAdministracion;
    private ManagerProveedoresServiceBusiness mgrProveedor;
    private ManagerProductoServiceBusiness mgrProducto;
    private ManagerInventarioServiceBusiness mgrInventario;
    private ManagerClientesServiceBusiness mgrCliente;
    private ManagerFacturacionServiceBusiness mgrFacturacion;
    //***************************************************

    /**
     * Default Constructor
     *
     * @param userAuthenticated, Subject
     * @throws RemoteException, Exception
     */
    public ManagerServiceFactoryImpl(Subject userAuthenticated) throws RemoteException {
        //Usuario autenticado
        this.userAuth = userAuthenticated;

        //Manager de seguridad
        this.mgrAutorizacion = new ManagerAutorizacionServiceBusinessImpl(userAuth);
    }    

    @Override
    public ManagerSeguridadServiceBusiness getManagerSeguridadServiceBusiness() throws RemoteException {
        if (mgrSeguridad == null)
            mgrSeguridad = new ManagerSeguridadServiceBusinessImpl(this.mgrAutorizacion);

        return mgrSeguridad;
    }

    @Override
    public ManagerCatalogoServiceBusiness getManagerCatalogoServiceBusiness() throws RemoteException {
        if (mgrCatalogo == null)
            mgrCatalogo = new ManagerCatalogoServiceBusinessImpl(this.mgrAutorizacion);

        return mgrCatalogo;
    }

    @Override
    public ManagerAdministracionServiceBusiness getManagerAdministracionServiceBusiness() throws RemoteException {
        if (mgrAdministracion == null)
            mgrAdministracion = new ManagerAdministracionServiceBusinessImpl(this.mgrAutorizacion);

        return mgrAdministracion;
    }

    @Override
    public ManagerProveedoresServiceBusiness getManagerProveedoresServiceBusiness() throws RemoteException {
        if (mgrProveedor == null)
            mgrProveedor = new ManagerProveedoresServiceBusinessImpl(this.mgrAutorizacion);

        return mgrProveedor;
    }

    @Override
    public ManagerProductoServiceBusiness getManagerProductoServiceBusiness() throws RemoteException {
        if (mgrProducto == null)
            mgrProducto = new ManagerProductoServiceBusinessImpl(this.mgrAutorizacion);

        return mgrProducto;
    }

    @Override
    public ManagerInventarioServiceBusiness getManagerInventarioServiceBusiness() throws RemoteException {
        if (mgrInventario == null)
            mgrInventario = new ManagerInventarioServiceBusinessImpl(this.mgrAutorizacion);

        return mgrInventario;
    }

    @Override
    public ManagerClientesServiceBusiness getManagerClientesServiceBusiness() throws RemoteException {
        if (mgrCliente == null)
            mgrCliente = new ManagerClientesServiceBusinessImpl(this.mgrAutorizacion);

        return mgrCliente;
    }

    @Override
    public ManagerFacturacionServiceBusiness getManagerFacturacionServiceBusiness() throws RemoteException {
        if (mgrFacturacion == null)
            mgrFacturacion = new ManagerFacturacionServiceBusinessImpl(this.mgrAutorizacion);

        return mgrFacturacion;
    }

    @Override
    public String getUserName() throws RemoteException {

        //Username
        String username = "";

        //Getting public credentials
        java.util.Set<Object> credentials = userAuth.getPublicCredentials();

        for (Object o : credentials) {
            //Convert to ContacUserCredential
            ContacUserCredential credential = (ContacUserCredential) o;

            //Setting username
            username = credential.getUsername();
        }

        //Validate username is not empty
        if (username.equals("")) {
            throw new RemoteException("Error trying to obtain the authenticated username.  Please contac system administrator");
        }

        return username;
    }

    @Override
    public AplicacionBaseRemote getApplicationBaseRemote() throws RemoteException {
        if (aplicacionBase == null)
            aplicacionBase = new AplicacionBase();

        return aplicacionBase;
    }
}
