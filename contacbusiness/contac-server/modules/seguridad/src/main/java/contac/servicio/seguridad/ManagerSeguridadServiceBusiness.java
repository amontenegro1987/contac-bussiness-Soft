/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.servicio.seguridad;

import contac.modelo.entity.Almacen;
import contac.modelo.entity.Compania;
import contac.modelo.entity.Rol;
import contac.modelo.entity.Usuario;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * Manager Seguridad Service Business .  Define todas las operaciones soportadas por este servicio
 * <p/>
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 12:14:12 AM
 */
public interface ManagerSeguridadServiceBusiness extends ManagerSeguridadServiceBusinessRemote {

    /**
     * Buscar listado de usuarios
     *
     * @return List<Usuario>
     * @throws ManagerSeguridadServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Usuario> buscarUsuarios() throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Buscar compania del usuario
     *
     * @param login, nombre de usuario de logueo en el sistema
     * @return Compania
     * @throws ManagerSeguridadServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Compania buscarCompaniaUsuario(String login) throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Crear un usuario
     *
     * @param login,    nombre de usuario de logueo en el sistema
     * @param password, clave del usuario
     * @param roles,    listado de roles
     * @param compania, Compania asociada al usuario
     * @return Usuario
     * @throws ManagerSeguridadServiceBusinessException,
     *          Exception
     */
    public Usuario crearUsuario(String login, String password, Set<Rol> roles, Compania compania, Almacen almacen)
            throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Modificar datos del usuario
     *
     * @param login,    nombre de usuario de logueo en el sistema
     * @param password, clave de usuario
     * @param roles,    listado de roles
     * @param estado,   estado del usuario a modificar
     * @param compania, compania asociada al usuario
     * @return Usuario
     * @throws ManagerSeguridadServiceBusinessException,
     *          Exception
     */
    public Usuario modificarUsuario(String login, String password, Set<Rol> roles, boolean estado, boolean cambiarContrasenia,
                                    Compania compania, Almacen almacen) throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Busca usuario por login en el sistema
     *
     * @param login, usuario registrado en el sistema
     * @return Usuario
     * @throws ManagerSeguridadServiceBusinessException,
     *          Exception
     */
    public Usuario buscarUsuarioPorLogin(String login) throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Buscar usuario por username y password
     *
     * @param login,    nombre de usuario de logueo en el sistema
     * @param password, clave de usuario
     * @return Usuario
     * @throws ManagerSeguridadServiceBusinessException,
     *          Exception
     */
    public Usuario buscarUsuarioPorLoginPassword(String login, String password) throws ManagerSeguridadServiceBusinessException,
            RemoteException;

    /**
     * Buscar roles registrados
     *
     * @return List<Rol>
     * @throws ManagerSeguridadServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Rol> buscarRoles() throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Busca rol por su identificador
     *
     * @param id, identificador del rol
     * @return Rol
     * @throws ManagerSeguridadServiceBusinessException,
     *          Exception
     */

    public Rol buscarRolPorId(Integer id) throws ManagerSeguridadServiceBusinessException, RemoteException;

    /**
     * Buscar rol por su nombre
     *
     * @param nombre, nombre del rol
     * @return Rol
     * @throws ManagerSeguridadServiceBusinessException,
     *          Exception
     */
    public Rol buscarRolPorNombre(String nombre) throws ManagerSeguridadServiceBusinessException, RemoteException;
}
