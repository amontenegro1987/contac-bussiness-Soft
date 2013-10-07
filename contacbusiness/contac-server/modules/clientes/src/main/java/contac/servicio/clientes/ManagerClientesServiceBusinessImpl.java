/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.clientes;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.actividadEconomicaEAO.ActividadEconomicaEAO;
import contac.modelo.eao.actividadEconomicaEAO.ActividadEconomicaEAOPersistence;
import contac.modelo.eao.clienteEAO.ClienteEAO;
import contac.modelo.eao.clienteEAO.ClienteEAOPersistence;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.*;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-17-12
 * Time: 10:46 AM
 */
public class ManagerClientesServiceBusinessImpl extends UnicastRemoteObject implements ManagerClientesServiceBusiness {

    /**
     * Servicio de log4j
     */
    private static final Logger logger = Logger.getLogger(ManagerClientesServiceBusinessImpl.class);

    /**
     * Access EAO
     */
    private ClienteEAO clienteEAO;
    private ActividadEconomicaEAO actividadEconomicaEAO;

    /**
     * Acceso al Manager Autorizacion Service
     */
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;

    /**
     * Constructor default
     *
     * @throws RemoteException, Exception
     */
    public ManagerClientesServiceBusinessImpl() throws RemoteException {
        //Init properties
        clienteEAO = new ClienteEAOPersistence();
        actividadEconomicaEAO = new ActividadEconomicaEAOPersistence();
    }

    /**
     * Constructor de un Manager de Clientes con un manager de Autorizacion
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerClientesServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Call constructor
        this();

        this.mgrAutorizacion = mgrAutorizacion;
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerClientesServiceBusinessException {

        try {

            //Iniciar servicio de autorizacion
            if (mgrAutorizacion == null)
                logger.error("Servicio de autenticacion inactivo");

            //Autorizar usuario
            mgrAutorizacion.isUserInRole(rolname);

            //Iniciar servicio transaccional
            return PersistenceManagementServiceFactory.beginTransaction();

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerClientesServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerClientesServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<Cliente> buscarClientes() throws ManagerClientesServiceBusinessException, RemoteException {

        logger.debug("Buscando clientes");

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            //Devuelve todos los clientes
            return clienteEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Cliente buscarClientePorCodigo(long codigo) throws ManagerClientesServiceBusinessException, RemoteException {

        logger.debug("Buscando cliente con parametros: [codigo]: " + codigo);

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return clienteEAO.findByCodigo(codigo);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ClienteNoEncontradoException(codigo + "");
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Cliente buscarClientePorCodigoNit(String nit) throws ManagerClientesServiceBusinessException, RemoteException {

        logger.debug("Buscar clientes con parametros: [nit]: " + nit);

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return clienteEAO.findByCodigoNit(nit);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ClienteNoEncontradoException(nit + "");
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Cliente> buscarClientes(String nit, String razonSocial) throws ManagerClientesServiceBusinessException, RemoteException {

        logger.debug("Buscanco clientes con parametros: [nit]: " + nit + ", [razonSocial]: " + razonSocial);

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCATALOGOCONSULTA.toString());

        try {

            return clienteEAO.findByParams(nit, razonSocial);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Cliente registrarCliente(String razonSocial, String nombreComercial, String nit, Integer idActividadEconomica,
                                    Direccion direccion, List<Contacto> contactos, int plazoCredito, BigDecimal limiteCredito, BigDecimal descuento,
                                    byte tipoPersona) throws ManagerClientesServiceBusinessException, RemoteException {

        logger.info("Registrando cliente con parametros: [razonSocial]: " + razonSocial + ", [nombreComercial]: " + nombreComercial +
                ", [nit]: " + nit + ", [idActividadEconomica]: " + idActividadEconomica + ", [plazoCredigo]: " + plazoCredito +
                ", [limiteCredito]: " + limiteCredito + ", [descuento]: " + descuento + ", [tipoPersona]: " + tipoPersona);

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCLIENTEADMIN.toString());

        try {

            //Preparar el contexto de ejecucion
            ActividadEconomica actividadEconomica = actividadEconomicaEAO.findById(idActividadEconomica);
            long codCliente = clienteEAO.generarCodigoCliente();

            //Validar si cliente se encuentra registrado
            if (!isClienteParaRegistro(nit))
                throw new ManagerClientesServiceBusinessException("Cliente con c\u00f3digo NIT: " + nit + ", se encuentra registrado.");

            //Crear Cliente
            Cliente cliente = new Cliente();
            cliente.setActividadEconomica(actividadEconomica);
            cliente.setCodigo(codCliente);
            if (contactos == null)
                cliente.setContactos(null);
            else
                cliente.setContactos(new HashSet<Contacto>(contactos));
            cliente.setCuenta(String.valueOf(codCliente));
            cliente.setDescuento(descuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            cliente.setDireccion(direccion);
            cliente.setEstatus(EstadosActivacion.ACTIVO.getValue());
            cliente.setLimiteCredito(limiteCredito.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            cliente.setNit(nit);
            cliente.setNombreComercial(nombreComercial);
            cliente.setPlazoCredito(plazoCredito);
            cliente.setRazonSocial(razonSocial);
            cliente.setTipoPersona(tipoPersona);

            //<Persist cliente>
            cliente = clienteEAO.create(cliente);

            return cliente;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Cliente modificarCliente(Integer idCliente, String razonSocial, String nombreComercial, String nit,
                                    Integer idActividadEconomica, Direccion direccion, List<Contacto> contactos, int plazoCredito,
                                    BigDecimal limiteCredito, BigDecimal descuento, byte tipoPersona) throws ManagerClientesServiceBusinessException,
            RemoteException {

        logger.debug("Modificando datos del cliente con parametros: [idCliente]: " + idCliente + ", [razonSocial]: " + razonSocial +
                ", [nombreComercial]: " + nombreComercial + ", [nit]: " + nit + ", [idActividadEconomica]: " + idActividadEconomica +
                ", [plazoCredito]: " + plazoCredito + ", [limiteCredito]: " + limiteCredito + ", [descuento]: " + descuento +
                ", [tipoPersona]: " + tipoPersona);

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCLIENTEADMIN.toString());

        try {

            //Preparar el contexto
            Cliente cliente = clienteEAO.findById(idCliente);
            ActividadEconomica actividadEconomica = actividadEconomicaEAO.findById(idActividadEconomica);

            //Modificar datos del cliente
            cliente.setActividadEconomica(actividadEconomica);
            if (contactos == null)
                cliente.setContactos(null);
            else
                cliente.setContactos(new HashSet<Contacto>(contactos));
            cliente.setDescuento(descuento.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            cliente.setLimiteCredito(limiteCredito.setScale(4, BigDecimal.ROUND_HALF_EVEN));
            cliente.setNit(nit);
            cliente.setNombreComercial(nombreComercial);
            cliente.setPlazoCredito(plazoCredito);
            cliente.setRazonSocial(razonSocial);
            cliente.setTipoPersona(tipoPersona);

            //Actualizar direccion
            cliente.getDireccion().setCelular(direccion.getCelular());
            cliente.getDireccion().setCiudad(direccion.getCiudad());
            cliente.getDireccion().setCodigoPostal(direccion.getCodigoPostal());
            cliente.getDireccion().setDireccion(direccion.getDireccion());
            cliente.getDireccion().setEmail(direccion.getEmail());
            cliente.getDireccion().setPais(direccion.getPais());
            cliente.getDireccion().setFax(direccion.getFax());
            cliente.getDireccion().setTelefono(direccion.getTelefono());
            cliente.getDireccion().setWeb(direccion.getWeb());

            //<Persist cliente>
            return clienteEAO.update(cliente);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public void removerCliente(Integer idCliente) throws ManagerClientesServiceBusinessException, RemoteException {

        logger.debug("Remover cliente con parametros: [idCliente]: " + idCliente);

        //Init servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCLIENTEADMIN.toString());

        try {

            //Validar si cliente tiene registros asociados de factura
            //TODO: Incluir validacion

            //Eliminar cliente
            clienteEAO.remove(idCliente);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage());
            throw new ManagerClientesServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public boolean isClienteParaRegistro(String nit) throws ManagerClientesServiceBusinessException, RemoteException {

        logger.debug("Validando cliente registrado con parametros: [nit]: " + nit);

        try {

            //Buscar cliente por codigo
            buscarClientePorCodigoNit(nit);

            //Return false cliente encontrado
            return false;

        } catch (ManagerClientesServiceBusinessException e) {
            logger.info("CLIENTE NO ENCONTRADO!!!");
            return true;
        }
    }


}
