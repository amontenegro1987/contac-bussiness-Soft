/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.inventarios.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.MovimientoInventario;
import contac.modelo.entity.ProductoExistencia;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 11-09-12
 * Time: 11:53 AM
 */
public class MovimientoInventarioController extends InventarioBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(MovimientoInventarioController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    private String codigo;
    private Integer idAlmacen;
    private Date fechaDesde;
    private Date fechaHasta;

    private List<ProductoExistencia> existencias;
    private List<MovimientoInventario> movimientoInventarios;

    public List<ProductoExistencia> getExistencias() {
        return existencias;
    }

    public void setExistencias(List<ProductoExistencia> existencias) {
        this.existencias = existencias;
    }

    public List<MovimientoInventario> getMovimientoInventarios() {
        return movimientoInventarios;
    }

    public void setMovimientoInventarios(List<MovimientoInventario> movimientoInventarios) {
        this.movimientoInventarios = movimientoInventarios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    //Init values
    public void init() {
        setExistencias(new ArrayList<ProductoExistencia>());
        setMovimientoInventarios(new ArrayList<MovimientoInventario>());
    }

    /**
     * Buscar producto existencias
     *
     * @throws Exception, Exception
     */
    public void buscarProductoExistencias() throws Exception {

        try {

            //Obtener manager de inventario
            List<ProductoExistencia> existencias = getMgrProductosService().buscarProductoExistencias(this.codigo);

            //Setting values to arraylist
            getExistencias().clear();
            getExistencias().addAll(existencias);

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar movimientos inventario
     *
     * @throws Exception, Exception
     */
    public void buscarMovimientosInventario() throws Exception {
        
        try {

            //Obtener manager service business
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Buscar movimientos inventario para producto
            List<MovimientoInventario> movimientosInventario = mgrInventario.buscarMovimientosInventario(this.codigo,
                    this.idAlmacen, this.fechaDesde, this.fechaHasta);
            
            //Adding movimientos inventario
            getMovimientoInventarios().clear();
            getMovimientoInventarios().addAll(movimientosInventario);
       
        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }


}
