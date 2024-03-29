/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.servicio.seguridad;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.eao.rolEAO.RolEAO;
import contac.modelo.eao.rolEAO.RolEAOPersistence;
import contac.modelo.eao.usuarioEAO.UsuarioEAO;
import contac.modelo.eao.usuarioEAO.UsuarioEAOPersistence;
import contac.modelo.entity.*;
import contac.security.Security;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * Manager Seguridad Service Business . Implementa todas las operaciones soportadas.
 * <p/>
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 12:14:36 AM
 */
public class ManagerSeguridadServiceBusinessImpl extends UnicastRemoteObject implements ManagerSeguridadServiceBusiness {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ManagerSeguridadServiceBusinessImpl.class);

    /**
     * Acceso capa DAO
     */
    protected UsuarioEAO usuarioEAO;
    protected RolEAO rolEAO;

    /**
     * Acceso al Manager Autorizacion Service
     */
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;


    /**
     * Constructor por Default
     *
     * @throws RemoteException, Exception
     */
    public ManagerSeguridadServiceBusinessImpl() throws RemoteException {

        //Inicializar accesos DAO
        usuarioEAO = new UsuarioEAOPersistence();
        rolEAO = new RolEAOPersistence();
    }

    /**
     * Constructor con un usuario autenticado
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerSeguridadServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {
        //Llamar al constructor padre
        this();

        //Inicializar servicio de autorizacion
        this.mgrAutorizacion = mgrAutorizacion;
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerSeguridadServiceBusinessException {

        try {

            //Iniciar servicio de autorizacion
            if (mgrAutorizacion == null)
                logger.error("Servicio de autenticacion inactivo");

            mgrAutorizacion.isUserInRole(rolname);

            //Iniciar servicio de transacciones
            return PersistenceManagementServiceFactory.beginTransaction();

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio
    private void stopBusinessService(boolean value) throws ManagerSeguridadServiceBusinessException {
        try {
            //Commit transaction
            PersistenceManagementServiceFactory.commit(value);
            //Close entity manager
            PersistenceManagementServiceFactory.closeEntityManager(value);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerSeguridadServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();
            //Close entity manager
            PersistenceManagementServiceFactory.closeEntityManager(true);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isUserInRole(String roleName) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.debug("Buscando usuario el rol: "+ roleName);

        try {
            return mgrAutorizacion.checkUserInRole(roleName);
        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> buscarUsuarios() throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.debug("Buscando usuarios registrados");

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Begin transaction
            PersistenceManagementServiceFactory.beginTransaction();

            return usuarioEAO.findAll();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Compania buscarCompaniaUsuario(String login) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.debug("Buscando compania del usuario con parametros: [login]: " + login);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        //Buscar usuario por login
        Usuario usuario = buscarUsuarioPorLogin(login);

        //Rollback business service
        stopBusinessService(transaction);

        //Obtener compania del usuario
        return usuario.getCompania();
    }

    @Override
    public Usuario crearUsuario(String login, String password, String contraseniaDescuento, Set<Rol> roles, Compania compania, Almacen almacen)
            throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.debug("Creando usuario con parametros: [login]: " + login + ", [roles]: " + roles);

        //Iniciar servicio de autorizacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Validar usuario en el sistema
            try {

                //Buscar usuario por login
                buscarUsuarioPorLogin(login);

                //Throw exception si existe usuario con login
                throw new ManagerSeguridadServiceBusinessException("Usuario con login: " + login + " se encuentra registrado.");

            } catch (UsuarioNoEncontradoException e) {
                //...No mostrar error de login no encontrado exception
                logger.info("LOGIN USUARIO NO ENCONTRADO...PROCEDER CON EL REGISTRO");
            }

            //Validar que el usuario no venga con un listado de roles vacios
            if (roles.size() <= 0)
                throw new ManagerSeguridadServiceBusinessException("Un rol debe ser seleccionado para guardar al usuario.");


            //Convertir password a MD-5 BASE-64
            String passwordHash = Security.createPasswordHash(password);
            //String passwordDescuentoHash = Security.createPasswordHash(contraseniaDescuento);

            //**********************************************************************************************
            //Evaluar si el usuario contiene el ROLCONTACUSER obligatorio para ejecucion general del usuario
            //**********************************************************************************************
            boolean contacUser = false;

            for (Rol rol : roles) {
                if (rol.getNombre().equals(Roles.ROLCONTACUSER.toString()))
                    contacUser = true;
            }

            if (!contacUser) {
                //Rol
                Rol rolContacUser = rolEAO.findByNombre(Roles.ROLCONTACUSER.toString());
                roles.add(rolContacUser);
            }

            //Creando usuario a persistir
            Usuario usuario = new Usuario();
            usuario.setUsername(login);
            usuario.setPassword(passwordHash);
            usuario.setPasswordDescuento(contraseniaDescuento);
            usuario.setRoles(roles);
            usuario.setCompania(compania);
            usuario.setAlmacen(almacen);
            usuario.setEstado(EstadosUsuario.ACTIVO.getNombre());

            usuario = usuarioEAO.create(usuario);

            //Stop business service
            stopBusinessService(transaction);

            return usuario;

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        }
    }

    @Override
    public Usuario modificarUsuario(String login, String password, String contraseniaDescuento, Set<Rol> roles, boolean estado, boolean cambiarContrasenia,
                                    Compania compania, Almacen almacen) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.debug("Modificando usuario con parametros: [login]: " + login + ", [roles]: " + roles + ", [estado]: " + estado);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Buscar usuario en el sistema
            Usuario usuario = buscarUsuarioPorLogin(login);

            //Validar que el usuario no venga con un listado de roles vacios
            if (roles.size() <= 0)
                throw new ManagerSeguridadServiceBusinessException("Un rol debe ser seleccionado para guardar al usuario.");

            //Validar el estado seleccionado
            String estadoString = estado ? EstadosUsuario.ACTIVO.getNombre() : EstadosUsuario.DEBAJA.getNombre();

            //Actualizar datos del usuario
            String passwordHash = Security.createPasswordHash(password);
            //String passwordDescuentoHash = Security.createPasswordHash(contraseniaDescuento);

            //**********************************************************************************************
            //Evaluar si el usuario contiene el ROLCONTACUSER obligatorio para ejecucion general del usuario
            //**********************************************************************************************
            boolean contacUser = true;

            for (Rol rol : roles) {
                if (rol.getNombre().equals(Roles.ROLCONTACUSER.toString()))
                    contacUser = false;
            }

            if (!contacUser) {
                //Rol
                Rol rolContacUser = rolEAO.findByNombre(Roles.ROLCONTACUSER.toString());
                roles.add(rolContacUser);
            }

            //Modificar y persistir datos usuario
            if (cambiarContrasenia)
                usuario.setPassword(passwordHash);
                usuario.setPasswordDescuento(contraseniaDescuento);
                usuario.setRoles(roles);
                usuario.setCompania(compania);
                usuario.setAlmacen(almacen);
                usuario.setEstado(estadoString);

            return usuarioEAO.update(usuario);

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            rollbackBusinessService();
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Usuario buscarUsuarioPorLogin(String login) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.info("Buscando usuario con parametros: [login]: " + login);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        try {

            return usuarioEAO.findByLogin(login);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new UsuarioNoEncontradoException("login: " + login);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Usuario buscarUsuarioPorLoginPassword(String login, String password) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.info("Buscando usuario con parametros: [login]: " + login);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLCONTACAUTHSERVICEADMIN.toString());

        try {

            //Convertir password de usuario a formato MD-5 Base 64
            String passwordHash = Security.createPasswordHash(password);

            //Buscar usuario por login
            Usuario usuario = usuarioEAO.findByLogin(login);

            //Verificar el password
            if (!usuario.getPassword().equals(passwordHash)) {
                throw new ManagerSeguridadServiceBusinessException("Contrase\u00f1a inv\u00e1lida.");
            }

            //Verificar usuario esta activo
            if (!usuario.getEstado().equals(EstadosUsuario.ACTIVO.getNombre())) {
                throw new ManagerSeguridadServiceBusinessException("Usuario inactivo.  Favor comuniquese con el administrador del sistema.");
            }

            return usuario;

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new UsuarioNoEncontradoException("login: " + login);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public List<Rol> buscarRoles() throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.info("Buscando roles registrados");

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            return rolEAO.findAll();

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Rol buscarRolPorId(Integer id) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.info("Buscando rol con parametros: [id]: " + id);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Validar que el id de rol no sea nulo
            if (id == null)
                throw new ManagerSeguridadServiceBusinessException("Debes ingresar un id rol valido.");

            //Buscar rol
            return rolEAO.findById(id);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    @Override
    public Rol buscarRolPorNombre(String nombre) throws ManagerSeguridadServiceBusinessException, RemoteException {

        logger.info("Buscando rol con parametros: [nombre]: " + nombre);

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLSYSTEMADMIN.toString());

        try {

            //Validar que el nombre no sea nulo o este vacio
            if (nombre == null || nombre.equals(""))
                throw new ManagerSeguridadServiceBusinessException("Debes ingresar un nombre de rol valido.");

            //Buscar rol
            return rolEAO.findByNombre(nombre);

        } catch (PersistenceClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerSeguridadServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }

    //********************************************************************************
    //METODOS UTILITARIOS
    //********************************************************************************

}
