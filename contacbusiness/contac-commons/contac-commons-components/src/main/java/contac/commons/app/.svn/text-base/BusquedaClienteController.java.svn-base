package contac.commons.app;

import contac.modelo.entity.Cliente;
import contac.servicio.clientes.ManagerClientesServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contac Business Software Inc. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-07-12
 * Time: 11:11 PM
 */
public class BusquedaClienteController extends BaseController {

    //log4j
    private static final Logger logger = Logger.getLogger(BusquedaProductoController.class);

    private String nit;
    private String nombreComercial;
    private String razonSocial;
    private List<Cliente> clientes;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * Constructor default
     */
    public BusquedaClienteController() {
        //Init listado de clientes
        setClientes(new ArrayList<Cliente>());
    }

    /**
     * Buscar clientes
     */
    public void buscarClientes() throws Exception {

        try {

            getClientes().clear();
            getClientes().addAll(getMgrClientesService().buscarClientes(this.nit, this.razonSocial));

        } catch (ManagerClientesServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
