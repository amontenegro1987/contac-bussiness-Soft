package contac.commons.app;


import contac.modelo.entity.Proveedor;
import contac.servicio.proveedores.ManagerProveedoresServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-01-11
 * Time: 10:49 PM
 */
public class BusquedaProveedorController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(BusquedaProveedorController.class);

    private long codigo;
    private String razonSocial;
    private List<Proveedor> proveedores;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    /**
     * Default constructor
     */
    public BusquedaProveedorController() {
        //Init listado de proveedores
        proveedores = new ArrayList<Proveedor>();
    }

    /**
     * Buscar Proveedores por parametros
     * @throws Exception, Exception
     */
    public void buscarProveedores() throws Exception {

        try {

            getProveedores().clear();
            getProveedores().addAll(getMgrProveedoresService().buscarProveedores(this.codigo, this.razonSocial));

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }
}
