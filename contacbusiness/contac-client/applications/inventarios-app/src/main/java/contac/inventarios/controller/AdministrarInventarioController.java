/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.inventarios.controller;

import contac.modelo.entity.Almacen;
import contac.modelo.entity.Linea;
import contac.modelo.entity.Producto;
import contac.modelo.entity.Proveedor;
import contac.servicio.catalogo.ManagerCatalogoServiceBusinessException;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import contac.servicio.proveedores.ManagerProveedoresServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 *
 * @author Eddy Montenegro
 * @version 2.0.8
 *          Date: 07-29-13
 *          Time: 10:58 PM
 */
public class AdministrarInventarioController extends InventarioBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(AdministraProductoController.class);

    private String codigoDesde;
    private String codigoHasta;
    private Linea linea;
    private Proveedor proveedor;
    private Almacen almacen;

    private boolean existencias;

    private List<Producto> productos;

    /**
     * Init Values Controller
     */
    public void init() {
    }

    //***********************************************<Action Methods>***************************************************

    /**
     * Buscar Productos por parametros
     *
     * @throws Exception, Exception
     */
    public void buscarProductos() throws Exception {
        try {

            //Obtener listado de productos
            List<Producto> productos = getMgrProductosService().buscarProducto(getCodigoDesde(), getCodigoHasta(),
                    getLinea() != null ? getLinea().getId() : -1, getProveedor() != null ? getProveedor().getCodigo() : -1,
                    getAlmacen() != null ? getAlmacen().getId() : -1, isExistencias());

            setProductos(productos);

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Anular Listado de Productos
     *
     * @param idProductos, List<Integer>
     * @throws Exception, Exception
     */
    public void anularProductos(List<Integer> idProductos) throws Exception {

        try {

            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();
            mgrProducto.anularProductos(idProductos);

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    //***********************************************<Getters & Setters>************************************************

    public String getCodigoDesde() {
        return codigoDesde;
    }

    public void setCodigoDesde(String codigoDesde) {
        this.codigoDesde = codigoDesde;
    }

    public String getCodigoHasta() {
        return codigoHasta;
    }

    public void setCodigoHasta(String codigoHasta) {
        this.codigoHasta = codigoHasta;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public boolean isExistencias() {
        return existencias;
    }

    public void setExistencias(boolean existencias) {
        this.existencias = existencias;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /**
     * Obtener listado de proveedores
     *
     * @return List<Proveedor>
     */
    public List<Proveedor> getProveedores() {
        try {
            return getMgrProveedoresService().buscarProveedores();
        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Obtener listado de lineas de producto
     *
     * @return List<Linea>
     */
    public List<Linea> getLineasProducto() {
        try {
            return getMgrCatalogoService().buscarLineas();
        } catch (ManagerCatalogoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
