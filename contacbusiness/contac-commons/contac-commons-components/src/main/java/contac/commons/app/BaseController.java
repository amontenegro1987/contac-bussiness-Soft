/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.app;

import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.aplicacion.AplicacionBaseRemote;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.clientes.ManagerClientesServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.proveedores.ManagerProveedoresServiceBusiness;
import contac.servicio.reportes.ManagerGeneradorReporteServiceBusiness;
import contac.servicio.seguridad.ManagerSeguridadServiceBusiness;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;

/**
 * Main Controller for implementation
 * <p/>
 * User: Eddy Montenegro
 * Date: 12-13-2010
 * Time: 10:45:04 PM
 */
public abstract class BaseController implements Constantes {

    //Log4j
    private static final Logger logger = Logger.getLogger(BaseController.class);

    //Variable de modificacion
    private boolean _edit = false;

    //***************************************************************************
    //METODOS GETTERS AND SETTERS
    //***************************************************************************

    //===========================================================================
    //VARIABLES DE ENTORNO
    //===========================================================================

    public boolean is_edit() {
        return _edit;
    }

    public void set_edit(boolean _edit) {
        this._edit = _edit;
    }

    //===========================================================================
    //OBTENER MANAGERS DE SERVICIOS
    //===========================================================================

    /**
     * Return manager service client App
     *
     * @return ManagerServiceClientApp
     */
    public ManagerServiceClientApp getManagerServiceClientApp() {
        return ManagerServiceClientApp.getInstance();
    }

    /**
     * Return AplicacionBaseApp para datos generales
     *
     * @return AplicacionBaseRemote
     */
    public AplicacionBaseRemote getAplicacionBaseRemote() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getApplicationBaseRemote();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Seguridad Service
     *
     * @return ManagerSeguridadServiceBusiness
     */
    public ManagerSeguridadServiceBusiness getMgrSeguridadService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerSeguridadServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Catalogo Service
     *
     * @return ManagerCatalogoServiceBusiness
     */
    public ManagerCatalogoServiceBusiness getMgrCatalogoService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerCatalogoServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Administracion Service
     *
     * @return ManagerAdministracionServiceBusiness
     */
    public ManagerAdministracionServiceBusiness getMgrAdministracionService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerAdministracionServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Proveedores Service
     *
     * @return ManagerProveedoresServiceBusiness
     */
    public ManagerProveedoresServiceBusiness getMgrProveedoresService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerProveedoresServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Productos Service
     *
     * @return ManagerProductosServiceBusiness
     */
    public ManagerProductoServiceBusiness getMgrProductosService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerProductoServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Inventario Service
     *
     * @return ManagerInventarioServiceBusiness
     */
    public ManagerInventarioServiceBusiness getMgrInventarioService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerInventarioServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Clientes Service
     *
     * @return ManagerClientesServiceBusiness
     */
    public ManagerClientesServiceBusiness getMgrClientesService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerClientesServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Facturacion Service
     *
     * @return ManagerFacturacionServiceBusiness
     */
    public ManagerFacturacionServiceBusiness getMgrFacturacionService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerFacturacionServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Return Manager Reportes Service
     *
     * @return ManagerReportesServiceBusiness
     */
    public ManagerGeneradorReporteServiceBusiness getMgrReportesService() {

        try {

            return ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerReporteServiceBusiness();

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
