/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.clientes;

import contac.modelo.entity.Cliente;
import contac.modelo.entity.Contacto;
import contac.modelo.entity.Direccion;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-17-12
 * Time: 10:46 AM
 */
public interface ManagerClientesServiceBusiness extends ManagerClientesServiceRemote {

    /**
     * Buscar clientes
     *
     * @return List
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Cliente> buscarClientes() throws ManagerClientesServiceBusinessException, RemoteException;

    /**
     * Buscar Clientes por codigo
     *
     * @param codigo, long
     * @return Cliente
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Cliente buscarClientePorCodigo(long codigo) throws ManagerClientesServiceBusinessException, RemoteException;

    /**
     * Buscar Clientes por codigo nit
     *
     * @param nit, Numero de registro tributario
     * @return Cliente
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Cliente buscarClientePorCodigoNit(String nit) throws ManagerClientesServiceBusinessException, RemoteException;

    /**
     * Buscar clientes por parametros
     *
     * @param nit,         Numero de registro tributario
     * @param razonSocial, Razon social del cliente
     * @return List
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Cliente> buscarClientes(String nit, String razonSocial) throws ManagerClientesServiceBusinessException, RemoteException;

    /**
     * Registrar cliente
     *
     * @param razonSocial,          Razon social del cliente
     * @param nombreComercial,      Nombre comercial del cliente
     * @param nit,                  Numero de registro de contribuyente
     * @param idActividadEconomica, tipo de actividad economica a la que pertenece
     * @param direccion,            Direccion actual
     * @param contactos,            Registro de contactos del cliente
     * @param plazoCredito,         Plazo de credito cliente
     * @param limiteCredito,        Limite de credito
     * @param descuento,            Descuento asignado
     * @param tipoPersona,          Tipo de persona
     * @return Cliente
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Cliente registrarCliente(String razonSocial, String nombreComercial, String nit, Integer idActividadEconomica,
                                    Direccion direccion, List<Contacto> contactos, int plazoCredito, BigDecimal limiteCredito,
                                    BigDecimal descuento, byte tipoPersona) throws ManagerClientesServiceBusinessException, RemoteException;

    /**
     * Modificar registro de cliente
     *
     * @param idCliente,            Codigo identificador de cliente
     * @param razonSocial,          Razon social del cliente
     * @param nombreComercial,      Nombre comercial del cliente
     * @param nit,                  Numero de registro de contribuyente
     * @param idActividadEconomica, Tipo de actividad economica a la que pertenece
     * @param direccion,            Direccion actual
     * @param contactos,            Registro de contactos del cliente
     * @param plazoCredito,         Plazo de credito cliente
     * @param limiteCredito,        Limite de credito
     * @param descuento,            Descuento asignado
     * @param tipoPersona,          Tipo de persona
     * @return Cliente
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Cliente modificarCliente(Integer idCliente, String razonSocial, String nombreComercial, String nit,
                                    Integer idActividadEconomica, Direccion direccion, List<Contacto> contactos, int plazoCredito,
                                    BigDecimal limiteCredito, BigDecimal descuento, byte tipoPersona) throws ManagerClientesServiceBusinessException,
            RemoteException;

    /**
     * Remover registro de cliente
     *
     * @param idCliente, Codigo identificador del cliente
     * @throws ManagerClientesServiceBusinessException, Exception
     * @throws RemoteException, Exception
     */
    public void removerCliente(Integer idCliente) throws ManagerClientesServiceBusinessException, RemoteException;

    /**
     * Verificar si el cliente se encuentra registrado
     *
     * @param nit, Codigo nit
     * @return boolean
     * @throws ManagerClientesServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public boolean isClienteParaRegistro(String nit) throws ManagerClientesServiceBusinessException, RemoteException;
}
