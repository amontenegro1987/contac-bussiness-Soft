/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.facturacion.controller;

import contac.commons.app.BaseController;
import contac.modelo.entity.*;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import contac.servicio.clientes.ManagerClientesServiceBusiness;
import contac.servicio.clientes.ManagerClientesServiceBusinessException;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2012, Contac Business Software. All rights reserved.
 * User: EMontenegro
 * Date: 02-27-12
 * Time: 10:29 PM
 */
public class FacturacionBaseController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(FacturacionBaseController.class);

    //*************************************************************************************
    //PRIVATE PROPERTIES
    //*************************************************************************************
    private List<Almacen> almacenes;

    //*************************************************************************************
    //GETTERS & SETTERS
    //*************************************************************************************

    public List<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    //*************************************************************************************
    //PRIVATE METHODS
    //*************************************************************************************

    /**
     * Check Permission User by role
     *
     * @param roleName, Role Name
     * @return boolean
     * @throws Exception, Exception
     */
    public boolean checkUserInRole(String roleName) throws Exception {

        logger.debug("Verificando si usuario tiene rol asignado: " + roleName);

        try {
            return getMgrSeguridadService().isUserInRole(roleName);
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar fecha alta facturacion
     *
     * @return Date
     * @throws Exception, Exception
     */
    public Date buscarFechaFacturacion() throws Exception {

        logger.debug("Buscando fecha de facturacion");

        try {

            return getMgrFacturacionService().getFechaServidor();
        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar Factura de Cliente
     *
     * @param idFactura, Identificador de Factura
     * @throws Exception, Exception
     */
    public Factura buscarFacturaCliente(Integer idFactura) throws Exception {
        try {

            //Getting Manager Facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            return mgrFacturacion.buscarFacturaPorId(idFactura);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar producto por su codigo
     *
     * @param codigo, String
     * @return Producto
     * @throws Exception, Exception
     */
    public Producto buscarProducto(String codigo) throws Exception {

        logger.debug("Buscando producto con codigo: " + codigo);

        try {

            //Obtener manager de producto
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();

            //Buscar producto por su codigo
            return mgrProducto.buscarProductoPorCodigo(codigo);

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Obtener listado de almacenes
     *
     * @return List
     * @throws Exception, Exception
     */
    public List<Almacen> buscarAlmacenes() throws Exception {

        logger.debug("Obteniendo listado de almacenes...!");

        try {

            //Obtener manager inventario service
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar listado de almacenes
            return mgrInventario.buscarAlmacenesPorUsuario();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar almacen del usuario
     *
     * @return Almacen
     * @throws Exception, Exception
     */
    public Almacen buscarAlmacenUsuario() throws Exception {

        logger.debug("Obteniendo almacen del usuario...!");

        try {

            //Obtener manager inventario service
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar almacen del usuario
            return mgrInventario.buscarAlmacenUsuario();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Obtener listado de agentes de ventas
     *
     * @return List
     * @throws Exception, Exception
     */
    public List<AgenteVentas> buscarAgentesVentas() throws Exception {

        logger.debug("Obteniendo listado de agentes de ventas...!");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Retornar listado de agentes de ventas
            return mgrFacturacion.buscarAgentesVentas();

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Obtener listado de agentes de ventas para el almacen del usuario
     *
     * @return List
     * @throws Exception, Exception
     */
    public List<AgenteVentas> buscarAgentesVentasUsuario() throws Exception {

        logger.debug("Obteniendo listado de agentes de ventas...!");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Retornar listado de agentes de ventas
            return mgrFacturacion.buscarAgentesVentasUsuario();

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar agente de ventas por codigo
     *
     * @param codigo, Codigo de agente de ventas
     * @return AgenteVentas
     * @throws Exception, Exception
     */
    public AgenteVentas buscarAgenteVentasPorCodigo(long codigo) throws Exception {

        logger.debug("Buscar agente de ventas con parameros: [codigo]: " + codigo);

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            return mgrFacturacion.buscarAgentesVentasPorCodigo(codigo);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar cliente por codigo
     *
     * @param codigo, Codigo de cliente
     * @return Cliente
     * @throws Exception, Exception
     */
    public Cliente buscarClientePorCodigo(long codigo) throws Exception {

        logger.debug("Buscar cliente con parametros: [codigo]: " + codigo);

        try {

            //Obtener manager de cliente
            ManagerClientesServiceBusiness mgrCliente = getMgrClientesService();

            return mgrCliente.buscarClientePorCodigo(codigo);

        } catch (ManagerClientesServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar tasa de cambio inicial
     *
     * @return TasaCambio
     * @throws Exception, Exception
     */
    public TasaCambio buscarTasaCambioInicial() throws Exception {

        logger.debug("Buscar tasa de cambio inicial facturacion");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Obtener listado de tasas de cambio validas
            List<TasaCambio> tasasCambio = mgrFacturacion.buscarTasasCambioFacturacion();

            if (tasasCambio != null)
                return tasasCambio.get(0);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }
}
