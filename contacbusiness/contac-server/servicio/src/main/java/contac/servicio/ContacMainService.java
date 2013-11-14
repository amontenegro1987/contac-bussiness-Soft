package contac.servicio;

import contac.config.ContacConfigInitService;
import contac.logger.ContacApacheLog4j;
import contac.servicio.aplicacion.AplicacionBaseApp;
import contac.servicio.autenticacion.login.ContacLoginManager;
import contac.servicio.autenticacion.login.ContacLoginManagerImpl;
import contac.servicio.autenticacion.login.ContacLoginManagerJNDIConstant;
import contac.servicio.autorizacion.ContacPolicy;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;
import java.util.Date;
import java.util.Properties;

/**
 * Clase para servicio principal de Contac Server
 */
public class ContacMainService {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ContacMainService.class);

    //Shutdown final variable for shutdown server application
    private static final String SHUTDOWN = "CTRL+C";

    /**
     * Registry
     */
    public static Registry registry;

    //******************************************
    //Configurar servicio log4j
    //******************************************

    private static void configContacLog4j() {

//        String path = ContacMainService.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        System.out.println("Path source file:");

         //String url file
        String fileName = "/contacbusiness/log/config/contac-log4j.xml";

        //Loading file
        File logFile = new File(fileName);
        String absolutePath = logFile.getPath();

        System.out.println("Absolute path log4j config: " + absolutePath);

        DOMConfigurator.configure(absolutePath);
    }

    //*****************************************
    //Creando servicio de seguridad
    //*****************************************

    private static void createSecurityPolicy() {

        //Create a Policy
        ContacPolicy policy = new ContacPolicy();

        //Setting policy
        Policy.setPolicy(policy);
    }

    /**
     * Starting aplicacion base
     */
    private static void startingAplicacionBase() {
         //Crear aplicacion base
         AplicacionBaseApp.getInstance();
    }

    //*****************************************
    //Creando servicio de JNDI LookUp
    //*****************************************

    private static void createJNDILookUp() {
        try {

            //Contac config service
            ContacConfigInitService contacConfig = new ContacConfigInitService();

            Properties properties = new Properties();
            properties.load(contacConfig.getRmiConfigPath());

            //Crear servicio de logueo
            ContacLoginManager login = new ContacLoginManagerImpl();

            //Servir el servicio de autenticacion
            Naming.rebind(properties.getProperty("service_host"), login);

        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //****************************************
    //Procesar comandos
    //****************************************

    private static void shutdown() {
        logger.info("SHUTDOWN SERVER...!!!");
        System.gc();
        System.exit(0);
    }
        
    /**
     * Inicializar servicios del servidor
     *
     * @param args, String[]
     */
    public static void main(String[] args) {

        try {

            //Inicialiazar servicio de log4j
            new ContacApacheLog4j();

            //Configurar politicas de seguridad
            createSecurityPolicy();

            //Install a Security Manager
            //System.setSecurityManager(new RMISecurityManager());

            // Assures that the identifiers generated for the server objects
            // will be secure
            System.setProperty("java.rmi.server.randomIDs", "true");

            //Iniciar el Registry Service
            registry = LocateRegistry.createRegistry(1099);

            //Starting aplicacion base
            startingAplicacionBase();

            //Create JNDI Lookup
            createJNDILookUp();

            //Setting shutdown service
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run() {
                    //Shutdown
                    shutdown();
                }
            });

            logger.info("SERVER UP AND RUNNING..." + new Date());

        } catch (Exception e) {
            logger.error("Error intentando iniciar el servidor..." + e.getMessage(), e);
            try{
                UnicastRemoteObject.unexportObject(registry, true);
            }catch(NoSuchObjectException exception){
                logger.error("Error intentando detener el servicio de registro:" + exception.getMessage());
            }
        } catch (Throwable t) {
            logger.error("Error intentando iniciar el servidor..." + t.getMessage(), t);
        }
    }
}
