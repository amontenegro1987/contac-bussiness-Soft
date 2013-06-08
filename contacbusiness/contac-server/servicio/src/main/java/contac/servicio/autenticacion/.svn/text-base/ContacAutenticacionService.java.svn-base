package contac.servicio.autenticacion;

import contac.modelo.entity.Roles;
import contac.servicio.autenticacion.login.ContacCallbackHandler;
import contac.servicio.autenticacion.login.ContacLoginConfig;
import contac.autenticacion.principals.ContacPrincipal;
import contac.servicio.implementacion.ManagerServiceFactory;
import contac.servicio.implementacion.ManagerServiceFactoryImpl;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.HashSet;

/**
 * Servicio de autenticacion del sistema.  Encargado de inicializar la configuracion
 * de autenticacion y de invocar el servicio de autenticacion para asignar los perfiles
 * y servicios a los cuales podra acceder el usuario acreditado.
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:08:10 PM
 */
public class ContacAutenticacionService {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ContacAutenticacionService.class);

    /**
     * Representa un usuario autenticado
     */
    private Subject usuarioAuth = null;

    /**
     * Representa un factory de servicios del sistema
     */
    private ManagerServiceFactory mgrServiceFactory;

    /**
     * Inicializa modulos de autenticacion
     */
    public void initLoginModule(){
        ContacLoginConfig.init();
    }

    /**
     * Autentica un usuario con credenciales de nombre de usuario en el sistema y contrasenia
     * @param username, nombre de usuario en el sistema
     * @param password, clave de usuario en ele sistema
     * @throws ContacAutenticacionServiceException, Exception
     */
    public void loginUser(String username, String password) throws ContacAutenticacionServiceException{

        try{

            String appName = "CONTACSERVICE";

            LoginContext ctx = new LoginContext(appName, new ContacCallbackHandler(username, password));
            ctx.login();

            usuarioAuth = ctx.getSubject();

            //Create el Manager Service Factory
            mgrServiceFactory = new ManagerServiceFactoryImpl(usuarioAuth);

        } catch (LoginException e){
            logger.error("Error intentando autenticar usuario: " + username);
            throw new ContacAutenticacionServiceException(e.getMessage(), e);
        } catch (RemoteException e){
            logger.error("Error intentando acceder al servicio remoto: " + e.getMessage(), e);
            throw new ContacAutenticacionServiceException(e.getMessage(), e);
        }

    }

    /**
     * Retorna factory de servicios
     * @return ManagerServiceFactory
     * @throws RemoteException, Exception
     */
    public ManagerServiceFactory createManagerServiceFactory() throws RemoteException{
        return mgrServiceFactory;
    }

    /**
     * Obtiene un usuario autenticado para el servicio de administracion de autenticacion
     *
     * @return Subject
     */
    static public Subject getSubjectAutenticacionService(){

        //Crea un Subject
        Subject contacSubject = new Subject();

        //Agregando rol para administracion de seguridad
        Set principals = new HashSet();

        //Agregar contac principal el usuario del sistema para servicio de autenticacion
        ContacPrincipal principalAuth = new ContacPrincipal(Roles.ROLCONTACAUTHSERVICEADMIN.toString());
        ContacPrincipal principalService = new ContacPrincipal(Roles.ROLCONTACSERVICEAPP.toString());

        principals.add(principalAuth);
        principals.add(principalService);

        contacSubject.getPrincipals().addAll(principals);
        
        return contacSubject;
    }

}
