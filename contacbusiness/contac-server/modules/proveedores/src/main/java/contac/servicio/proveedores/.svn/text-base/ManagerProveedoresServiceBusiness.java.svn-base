package contac.servicio.proveedores;

import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Direccion;
import contac.modelo.entity.Proveedor;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

/**
 * ManagerProveedoresServiceBusiness . Soporta todas las operaciones relacionadas con proveedores
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-16-2010
 * Time: 11:22:55 PM
 */
public interface ManagerProveedoresServiceBusiness extends ManagerProveedoresServiceRemote {

    /**
     * Buscar todos los proveedores
     *
     * @return List<Proveedor>
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Proveedor> buscarProveedores() throws ManagerProveedoresServiceBusinessException, RemoteException;

    /**
     * Buscar proveedores por parametros
     *
     * @return List<Proveedor>
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Proveedor> buscarProveedores(long codigo, String razonSocial) throws ManagerProveedoresServiceBusinessException,
            RemoteException;

    /**
     * Busca proveedor por codigo
     *
     * @param codigo, Codigo
     * @return Proveedor
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Proveedor buscarProveedorPorCodigo(long codigo) throws ManagerProveedoresServiceBusinessException, RemoteException;

    /**
     * Registrar nuevo proveedor
     *
     * @param codigo,             Codigo del proveedor
     * @param razonSocial,        Razon Social
     * @param nombreComercial,    Nombre comercial
     * @param nit,                Numero de registro tributario
     * @param cuenta,             Numero de cuenta
     * @param descuento,          Descuento otorgado
     * @param plazoCredito,       Plazo de credito
     * @param limiteCredito,      Limite de credito
     * @param tipoPersona,        Tipo de persona
     * @param actividadEconomica, Actividad economica
     * @param direccion,          Direccion
     * @return Proveedor
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Proveedor registrarProveedor(long codigo, String razonSocial, String nombreComercial, String nit, String cuenta,
                                        BigDecimal descuento, int plazoCredito, BigDecimal limiteCredito, byte tipoPersona,
                                        ActividadEconomica actividadEconomica, Direccion direccion)
            throws ManagerProveedoresServiceBusinessException, RemoteException;

    /**
     * Modificar proveedor
     *
     * @param id,                 Identificador del proveedor
     * @param codigo,             Codigo del proveedor
     * @param razonSocial,        Razon Social
     * @param nombreComercial,    Nombre Comercial
     * @param nit,                Numero de registro tributario
     * @param cuenta,             Numero de cuenta
     * @param descuento,          Descuento otorgado
     * @param plazoCredito,       Plazo de credito
     * @param limiteCredito,      Limite de credito
     * @param tipoPersona,        Tipo de persona
     * @param actividadEconomica, Actividad Economica
     * @param direccion,          Direccion
     * @return Proveedor
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Proveedor modificarProveedor(Integer id, long codigo, String razonSocial, String nombreComercial, String nit,
                                        String cuenta, BigDecimal descuento, int plazoCredito, BigDecimal limiteCredito,
                                        byte tipoPersona, ActividadEconomica actividadEconomica, Direccion direccion)
            throws ManagerProveedoresServiceBusinessException, RemoteException;

    /**
     * Remover proveedor
     *
     * @param id, Identificador del proveedor
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void removerProveedor(Integer id) throws ManagerProveedoresServiceBusinessException, RemoteException;


    /**
     * Valida si un proveedor ya se encuentra registrado
     *
     * @param codigo, long
     * @return boolean
     * @throws ManagerProveedoresServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public boolean isProveedorParaRegistro(long codigo) throws RemoteException;

}
