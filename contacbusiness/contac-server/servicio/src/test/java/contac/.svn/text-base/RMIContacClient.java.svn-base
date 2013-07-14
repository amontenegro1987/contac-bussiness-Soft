package contac;

import contac.logger.ContacApacheLog4j;
import contac.modelo.entity.Clasificador;
import contac.modelo.entity.Pais;
import contac.modelo.entity.TiposClasificador;
import contac.servicio.autenticacion.login.ContacLoginManager;
import contac.servicio.autenticacion.login.ContacLoginManagerJNDIConstant;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerCatalogoServiceBusinessException;
import contac.servicio.implementacion.ManagerServiceFactory;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Servicio de testing para usuario RMI
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-21-2010
 * Time: 04:30:23 PM
 */
public class RMIContacClient {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(RMIContacClient.class);

    //Servicio de login
    private static ContacLoginManager login;

    //Manager servicio
    private static ManagerServiceFactory mgrFactory;

    /**
     * Obtener una referencia JNDI del objeto de login
     */
    private static void gettingJNDILookup(){

        try {

            Remote obj = Naming.lookup(ContacLoginManagerJNDIConstant.LOGINMANAGER_SERVICE_REMOTE);

            login = (ContacLoginManager)obj;

        } catch (MalformedURLException e){
            logger.error(e.getMessage(), e);
        } catch (NotBoundException e){
            logger.error(e.getMessage(), e);
        } catch (RemoteException e){
            logger.error(e.getMessage(), e);
        }
    }


    public static void main(String args[]){

        try {

             //Configurar servicio de log4j
            new ContacApacheLog4j();

            //Obteniendo el objeto remoto
            gettingJNDILookup();

            mgrFactory = login.login("SYSADMIN", "temporal");

            //Testing 1
           findClasificaciones();

            //Testing 2
            //findPaisesApplication();

        } catch (RemoteException e){
            logger.error(e.getMessage(), e);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }

    }

    //************************************************************
    //TESTING SERVICES
    //************************************************************

    /**
     * Testing listado de paises en application
     */
    private static void findPaisesApplication() {

        logger.info("Testing listado de paises en aplicacion");

        try {

            //AplicacionBaseRemote aplicacionBase = mgrFactory.getApplicationBaseRemote();

            for (Pais pais : mgrFactory.getApplicationBaseRemote().getPaises()){

                logger.info("PAIS CODE: " + pais.getIso());
                logger.info("PAIS OFFICIAL NAME: " + pais.getNombre());
            }

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Busca listado de clasificadores
     */
    private static void findClasificaciones() {

        logger.info("Testing persistence implementation for clasification object.");

        try {

            //Obtener manager catalogo servicio
            ManagerCatalogoServiceBusiness mgrCatalogo = mgrFactory.getManagerCatalogoServiceBusiness();

            List<Clasificador> clasificadores = mgrCatalogo.buscarClasificadoresPorTipo(TiposClasificador.SEGMENTO.getValue());

            for (Clasificador clasificador : clasificadores) {
                logger.info("CODIGO CBS: " + clasificador.getCbs());
                logger.info("DESCRIPCION: " + clasificador.getDescripcion());
            }

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        } catch (ManagerCatalogoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Testing for creating a clasification
     */
    private static void createClasification() {

        logger.info("Testing persitence implementation for clasification object.");

        try {


            //Obtener manager catalogo servicio
            ManagerCatalogoServiceBusiness mgrCatalogo = mgrFactory.getManagerCatalogoServiceBusiness();

            //persistir clasificacion
            //Clasificador clasificador = mgrCatalogo.registrarClasificador("BASEBALL & SOFTBALL", "BB/SB", 1, null);

            //if (clasificador != null)
             //   logger.info("Transaction OK!!!");

        } catch (RemoteException e){
            logger.error(e.getMessage(), e);
        } //catch (ManagerCatalogoServiceBusinessException e){
            //logger.error(e.getMessage(), e);
        //}

    }

}
