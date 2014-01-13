/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.inventarios.controller;

import contac.commons.app.BaseController;
import contac.modelo.entity.Almacen;
import contac.modelo.entity.Producto;
import contac.modelo.entity.ProductoExistencia;
import contac.reports.JRPrintReport;
import contac.reports.JRXReportGenerated;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 01-08-12
 * Time: 12:08 AM
 */
public abstract class InventarioBaseController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(InventarioBaseController.class);

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
     * Buscar Existencias por Almacen
     *
     * @throws Exception, Existencia
     */

    /**
     * Buscar producto existencias
     *
     * @param codigo, String
     * @return Producto
     * @throws Exception, Existencia
     */
    public ProductoExistencia buscarProductoExistencia(String codigo, Integer idAlmacen) throws Exception {

        logger.debug("Buscando producto con codigo: " + codigo);

        try {

            //Obtener manager de producto
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();

            //Buscar existencia del producto
            return mgrProducto.buscarProductoExistenciaPorAlmacen(codigo, idAlmacen);

        } catch (ManagerProductoServiceBusinessException e) {
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
     * Imprimir reporte a Jasper reports
     *
     * @throws Exception, Exception
     */
    public JasperPrint imprimirReporte(String reportName, Map parameters, JRBeanCollectionDataSource dataSource) throws Exception {
//        return JRPrintReport.fillReport(getReportGenerated(reportName, parameters, dataSource));
        return null;
    }

    //******************************************************************************************************************
    // Reports utility methods
    //******************************************************************************************************************

    /**
     * Get jasper report name for printing
     *
     * @return JRXReportGenerated
     */
    private JRXReportGenerated getReportGenerated(String reportName, Map parameters, JRBeanCollectionDataSource dataSource)
            throws Exception {

        //Load jasper reports
        JasperReport jasperReport = loadJasperReport(reportName);

        //Generated JRXReportGenerated
//        return new JRXReportGenerated(jasperReport, parameters, dataSource);
        return null;
    }


    /**
     * Load jasper report and compile
     *
     * @param reportName, String
     * @return JasperReport
     * @throws Exception, Exception
     */
    private JasperReport loadJasperReport(String reportName) throws Exception {

        try {

            //Getting report from resources
            JasperDesign jasperDesign = JRXmlLoader.load("/reports/" + reportName);

            //Compile and return jasper report
            return JasperCompileManager.compileReport(jasperDesign);

        } catch (JRException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

}
