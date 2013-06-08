package contac.commons.app;

import contac.modelo.entity.Producto;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contac Business Software Inc. All rights reserved 2011.
 * User: Eddy Montenegro
 * Date: 25/09/11
 * Time: 9:16
 */
public class BusquedaProductoController extends BaseController {

    //log4j
    private static final Logger logger = Logger.getLogger(BusquedaProductoController.class);

    private String codigo;
    private String nombre;
    private String codigoFabricante;
    private List<Producto> productos;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoFabricante() {
        return codigoFabricante;
    }

    public void setCodigoFabricante(String codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /**
     * Default constructor
     */
    public BusquedaProductoController() {
        //Init listado de productos
        setProductos(new ArrayList<Producto>());
    }

    /**
     * Buscar productos
     * @throws Exception, Exception
     */
    public void buscarProductos() throws Exception {

        try {

            //Settear listado de productos
            getProductos().clear();
            getProductos().addAll(getMgrProductosService().buscarProducto(this.codigo, this.nombre, this.codigoFabricante));

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
