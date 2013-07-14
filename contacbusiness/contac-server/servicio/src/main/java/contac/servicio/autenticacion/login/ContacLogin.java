package contac.servicio.autenticacion.login;

import contac.autenticacion.principals.CallerPrincipal;
import contac.autenticacion.principals.RolPrincipal;
import contac.modelo.entity.Rol;
import contac.modelo.entity.Usuario;
import contac.servicio.autenticacion.ContacAutenticacionService;
import contac.servicio.autenticacion.ContacUserCredential;
import contac.servicio.implementacion.ManagerServiceFactory;
import contac.servicio.implementacion.ManagerServiceFactoryImpl;
import contac.servicio.seguridad.ManagerSeguridadServiceBusiness;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessException;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.callback.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Clase para login de usuarios en el servicio de autenticacion
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:02:36 PM
 */
public class ContacLogin implements LoginModule {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ContacLogin.class);

    protected Subject subject;
    protected CallbackHandler callbackHandler;
    private Map sharedState = Collections.EMPTY_MAP;
    private Map options = Collections.EMPTY_MAP;
    protected Set principalsAdded;
    protected boolean autenticado;
    protected Usuario userAuthenticated;
    protected String username;
    protected String password;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws LoginException {

        NameCallback nameCB = new NameCallback("Username");
        PasswordCallback passwordCB = new PasswordCallback("Password", false);

        Callback[] callbacks = new Callback[]{nameCB, passwordCB};

        try {

            callbackHandler.handle(callbacks);

        } catch (IOException e){
            LoginException ex = new LoginException("IOException logging in");
            ex.initCause(e);
        } catch (UnsupportedCallbackException e){
            String className = e.getCallback().getClass().getName();
            LoginException ex = new LoginException(className + "is not a supported callback.");
            ex.initCause(e);
            throw ex;
        }

        //Valores para autenticar usuario
        username = nameCB.getName();
        password = String.valueOf(passwordCB.getPassword());

        try {

            //Obtener un usuario del modulo de logueo para procesar tarea de autenticacion
            ManagerServiceFactory mgrFactory = new ManagerServiceFactoryImpl(ContacAutenticacionService.getSubjectAutenticacionService());

            //Obtener un manager de seguridad para buscar usuario con sus permisos
            ManagerSeguridadServiceBusiness mgrSeguridad = mgrFactory.getManagerSeguridadServiceBusiness();

            Usuario usuario = mgrSeguridad.buscarUsuarioPorLoginPassword(username, password);

            //Usuario autenticado
            userAuthenticated = usuario;

            //Usuario autenticado
            autenticado = true;

        } catch (RemoteException e){
            logger.error(e.getMessage(), e);
            LoginException ex = new LoginException(e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (ManagerSeguridadServiceBusinessException e){
            logger.error(e.getMessage(), e);
            LoginException ex = new LoginException(e.getMessage());
            ex.initCause(e);
            throw ex;
        }

        return autenticado;
    }

    @Override
    public boolean commit() throws LoginException {

        if (userAuthenticated == null)
            return false;

        //Establecer las credenciales del usuario
        ContacUserCredential credential = new ContacUserCredential(userAuthenticated.getUsername(), userAuthenticated.getPassword());
        subject.getPublicCredentials().add(credential);

        //Agregar los principals
        Set principals = getPrincipals();
        subject.getPrincipals().addAll(principals);

        principalsAdded = new HashSet();
        principalsAdded.addAll(principals);

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        username = null;
        password = null;
        autenticado = false;

        return true;
    }

    @Override
    public boolean logout() throws LoginException {

        if (principalsAdded != null && !principalsAdded.isEmpty()){
            subject.getPrincipals().removeAll(principalsAdded);
        }

        return true;
    }

    //************************************************
    //Obtener los principals
    //************************************************
    private Set getPrincipals(){

        //Listado de principals
        Set principals = new HashSet();

        //Obtener los roles del usuario autenticado
        Set<Rol> roles = userAuthenticated.getRoles();
                
        for (Rol rol: roles){
            RolPrincipal rolPrincipal = new RolPrincipal(rol.getId(), rol.getNombre());
            principals.add(rolPrincipal);
        }

        //Adding a caller principal
        CallerPrincipal callerPrincipal = new CallerPrincipal(userAuthenticated.getUsername());
        //Caller principal user authenticated's username
        principals.add(callerPrincipal);

        return principals;
    }
}
