package contac.commons.app;

import contac.config.ContacConfigInitService;
import contac.servicio.autenticacion.login.ContacLoginManager;
import contac.servicio.autenticacion.login.ContacLoginManagerJNDIConstant;
import contac.servicio.implementacion.ManagerServiceFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-13-2010
 * Time: 02:26:56 PM
 */
public class ManagerServiceClientApp {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ManagerServiceClientApp.class);

    /**
     * ManagerServiceClientApp
     */
    private static ManagerServiceClientApp instance;

    /**
     * Servicio de login en el server
     */
    private ContacLoginManager login;

    /**
     * ManagerServiceFactory
     */
    private ManagerServiceFactory managerServiceFactory;

    /**
     * Return a ManagerServiceClientApp instance
     *
     * @return ManagerServiceClient
     */
    public static ManagerServiceClientApp getInstance() {
        if (instance == null)
            instance = new ManagerServiceClientApp();

        return instance;
    }

    /**
     * Return Manager Service factory
     *
     * @return ManagerServiceFactory
     */
    public ManagerServiceFactory getManagerServiceFactory() {

        if (managerServiceFactory == null)
            throw new RuntimeException("Error trying to obtain the manager service from server");

        return managerServiceFactory;
    }

    /**
     * Return authenticated username
     *
     * @return String
     */
    public String getUsername() {

        if (managerServiceFactory != null) {

            try {

                return managerServiceFactory.getUserName();

            } catch (RemoteException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return "";
    }

    /**
     * Servicio de login to server
     *
     * @param username, nombre de usuario
     * @param password, contrasenia de usuario
     */
    public void LoginServer(String username, String password) {

        try {

            //Connecting to server
            connectToServer();

            //Authenticating user
            managerServiceFactory = login.login(username, password);

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException(e.getMessage(), e);
        }
    }


    //********************************************************************************************
    //UTILITY METHODS
    //********************************************************************************************

    /**
     * Connecting the server
     *
     * @throws ConnectionException, RuntimeException
     */
    private void connectToServer() throws ConnectionException {

        try {

            //Contac config service
            ContacConfigInitService contacConfig = new ContacConfigInitService();

            Properties properties = new Properties();
            properties.load(contacConfig.getRmiConfigPath());

            //Servicio de RMI Connection
            Remote obj = Naming.lookup(properties.getProperty("service_host"));

            //Login
            login = (ContacLoginManager) obj;

        } catch (NotBoundException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException(e.getMessage(), e);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException(e.getMessage(), e);
        }

    }

}
