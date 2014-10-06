/*
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.administracion.controller;

import contac.administracion.usuario.app.RolesActivoApp;
import contac.commons.app.BaseController;
import contac.modelo.entity.*;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import contac.servicio.seguridad.ManagerSeguridadServiceBusiness;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Controlador de administracion de usuario
 * User: emontenegro
 * Date: 7/29/11
 * Time: 11:34 AM
 */
public class AdministraUsuarioController extends BaseController {

    //Logger
    private static final Logger logger = Logger.getLogger(AdministraUsuarioController.class);

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private String nombreUsuario;
    private String contrasenia;
    private String confirmaContrasenia;
    private String contraseniaDescuento;
    private Compania compania;
    private Almacen almacen;
    private boolean activo;
    private boolean cambiarContrasenia;

    private List<Usuario> usuarios;
    private List<RolesActivoApp> roles;

    private Usuario usuario;

    //*************************************************************************************
    //INIT VALUES
    //*************************************************************************************
    public void init() throws Exception {

        logger.info("Iniciando ingreso");

        //<Nombre usuario>
        setNombreUsuario("");
        //<Contrasenia>
        setContrasenia("");
        //<ConfirmaContrasenia>
        setConfirmaContrasenia("");
        //<Compania>
        setCompania(null);
        //<Almacen>
        setAlmacen(null);
        //<Activo>
        setActivo(false);
        //<Usuarios activos>
        setUsuarios(getUsuariosActivos());
        //<Roles activos>
        List<RolesActivoApp> rolesActivosApps = new ArrayList<RolesActivoApp>();
        for (Rol rol : getRolesActivos()) {
            RolesActivoApp rolActivo = new RolesActivoApp();
            rolActivo.setId(rol.getId());
            rolActivo.setRol(rol.getNombre());
            rolActivo.setDescripcion(rol.getDescripcion());
            rolActivo.setEntity(rol);
            rolActivo.setActivo(false);

            //Adding to list
            rolesActivosApps.add(rolActivo);
        }
        setRoles(rolesActivosApps);
    }

    public void initModificacion() throws Exception {

        logger.debug("Iniciando modificacion");

        //<Nombre usuario>
        setNombreUsuario(usuario.getUsername());
        //<Compania>
        setCompania(usuario.getCompania());
        //<Almacen>
        setAlmacen(usuario.getAlmacen());
        //<Activo>
        setActivo(usuario.getEstado().equals(EstadosUsuario.ACTIVO.toString()) ? true : false);

        //<Inactivar roles>
        for (RolesActivoApp rol : getRoles()) {
            rol.setActivo(false);
        }

        //<Cargar roles usuario>
        for (RolesActivoApp rol : getRoles()) {
            for (Rol rolActivo : usuario.getRoles()) {
                if (rol.getId().equals(rolActivo.getId())) {
                    rol.setActivo(true);
                }
            }
        }
    }

    public void limpiar() {

        logger.debug("Limpiando datos formulario");

        //<Nombre usuario>
        setNombreUsuario("");
        //<Contrasenia>
        setContrasenia("");
        //<Confirmar contrasenia>
        setConfirmaContrasenia("");
        //<Activo>
        setActivo(false);
        //<Roles>
        for (RolesActivoApp rol : getRoles()) {
            rol.setActivo(false);
        }
        //<Usuario clean>
        setUsuario(null);
    }

    /**
     * Obtener listado de usuarios
     *
     * @return List<Usuario>
     * @throws Exception, Exception
     */
    public List<Usuario> getUsuariosActivos() throws Exception {

        try {
            return getMgrSeguridadService().buscarUsuarios();
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Obtener listado de roles
     *
     * @return List<Rol>
     * @throws Exception, Exception
     */
    public List<Rol> getRolesActivos() throws Exception {

        try {
            return getMgrSeguridadService().buscarRoles();
        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Obtener listado de companias
     *
     * @return List<Compania>
     */
    public List<Compania> getCompanias() {

        try {
            return getMgrAdministracionService().buscarCompaniasActivas();
        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Obtener listado de almacenes
     * @return List<Almacen>
     */
    public List<Almacen> getAlmacenes() {
        
        try {
            return getMgrInventarioService().buscarAlmacenesPorUsuario();
        
        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Registra usuario
     *
     * @throws Exception, Exception
     */
    public void registrarUsuario() throws Exception {

        logger.debug("Registrando usuario...");

        try {

            //Convert list of roles
            Set<Rol> rolesAsignados = new HashSet<Rol>();

            for (RolesActivoApp rol : roles) {
                if (rol.isActivo()) {
                    rolesAsignados.add(rol.getEntity());
                }
            }

            //Getting manager seguridad
            ManagerSeguridadServiceBusiness mgr = getMgrSeguridadService();

            //Registrar usuario
            Usuario usuario = mgr.crearUsuario(this.nombreUsuario, this.contrasenia, this.contraseniaDescuento, rolesAsignados, this.compania,
                    this.almacen);

            //Adding user to list
            getUsuarios().add(usuario);

        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificando usuario
     * @throws Exception, Exception
     */
    public void modificarUsuario() throws Exception {

        logger.debug("Modificando usuario...");

        try {

            //Convert list of roles
            Set<Rol> rolesAsignados = new HashSet<Rol>();

            for (RolesActivoApp rol : roles) {
                if (rol.isActivo()) {
                    rolesAsignados.add(rol.getEntity());
                }
            }

            //Modificar usuario
            Usuario usuarioMod = getMgrSeguridadService().modificarUsuario(this.nombreUsuario, this.contrasenia,
                    this.contraseniaDescuento, rolesAsignados, isActivo(), this.cambiarContrasenia, this.compania, this.almacen);

            //<Modificar registro de usuarios>
            for (Usuario usuario : this.usuarios) {
                if (usuario.getUsername().equals(usuarioMod.getUsername())) {
                    usuario.setPassword(usuarioMod.getPassword());
                    usuario.setPasswordDescuento(usuarioMod.getPasswordDescuento());
                    usuario.setEstado(usuarioMod.getEstado());
                    usuario.setRoles(usuarioMod.getRoles());
                }
            }

            //Actualizar registro de usuarios
            getUsuarios().clear();
            getUsuarios().addAll(getUsuariosActivos());

        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    //*************************************************************************************
    //GETTERS AND SETTERS
    //*************************************************************************************


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getConfirmaContrasenia() {
        return confirmaContrasenia;
    }

    public void setConfirmaContrasenia(String confirmaContrasenia) {
        this.confirmaContrasenia = confirmaContrasenia;
    }

    public String getContraseniaDescuento() { return contraseniaDescuento; }

    public void setContraseniaDescuento(String contraseniaDescuento) { this.contraseniaDescuento = contraseniaDescuento;}

    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isCambiarContrasenia() {
        return cambiarContrasenia;
    }

    public void setCambiarContrasenia(boolean cambiarContrasenia) {
        this.cambiarContrasenia = cambiarContrasenia;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<RolesActivoApp> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesActivoApp> rolesSelected) {
        this.roles = rolesSelected;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
