package contac.modelo;

import contac.config.ContacConfigInitService;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Implementacion relativo a la infraestructura de persistencia
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-07-2010
 * Time: 01:43:48 AM
 */
public class PersistenceManagementServiceFactory {

    //Entity Manager Factory para servicio de persistencia
    private static EntityManagerFactory managerFactory = null;

    //Thread local service
    private static ThreadLocal<EntityManager> threadLocal;

    //Resource bundle persistence config
    private static Properties propertiesConfig = new Properties();

    static {

        try {

            //Properties config load
            ContacConfigInitService contacConfig = new ContacConfigInitService();
            propertiesConfig.load(contacConfig.getPersistenceConfigPath());

            //Create persistence properties
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("hibernate.connection.driver_class", propertiesConfig.getProperty("hibernate.connection.driver_class"));
            properties.put("hibernate.connection.url", propertiesConfig.getProperty("hibernate.connection.url"));
            properties.put("hibernate.connection.username", propertiesConfig.getProperty("hibernate.connection.username"));
            properties.put("hibernate.connection.password", propertiesConfig.getProperty("hibernate.connection.password"));
            properties.put("hibernate.dialect", propertiesConfig.getProperty("hibernate.dialect"));
            properties.put("hibernate.jdbc.batch_size", "20"); //Values goes between 10 and 50
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            //Create manager service factory
            managerFactory = Persistence.createEntityManagerFactory("CONTAC_MODELO_PERSISTENCE_UNIT", properties);
            threadLocal = new ThreadLocal<EntityManager>();

        } catch (IOException e) {
            //Nothing to show
        }
    }

    /**
     * Retorna un entity manager activo
     *
     * @return EntityManager
     */
    public static EntityManager getEntityManager() throws PersistenceManagementServiceFactoryException {

        EntityManager em = threadLocal.get();

        if (em == null || !em.isOpen()) {
            em = managerFactory.createEntityManager();
            threadLocal.set(em);
        }

        return em;
    }

    /**
     * Cerrar servicio de persistencia
     */
    public static void closeEntityManager(boolean value) throws PersistenceManagementServiceFactoryException {

        EntityManager em = threadLocal.get();

        if (value) {
            if (em != null) {
                em.close();
            }

            //Remove current entity manager
            threadLocal.remove();
        }
    }

    /**
     * Inicia transaction
     */
    public static boolean beginTransaction() throws PersistenceManagementServiceFactoryException {
        if (!getEntityManager().getTransaction().isActive()) {
            //Begin a new transaction
            getEntityManager().getTransaction().begin();

            return true;
        }

        return false;
    }

    /**
     * Actualizar cambios a los datos persistentes
     */
    public static void commit(boolean value) throws PersistenceManagementServiceFactoryException {

        if (value) {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().commit();
            }
        }
    }

    /**
     * Deshacer cambios
     */
    public static void rollback() throws PersistenceManagementServiceFactoryException {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().rollback();
        }
    }

    /**
     * Sincronizar entidades con datos persistentes
     */
    public static void flush() throws PersistenceManagementServiceFactoryException {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().flush();
        }
    }

    /**
     * Return a Connection Object to performed operations
     * @return Connection
     * @throws PersistenceManagementServiceFactory, Exception
     */
    public static Connection getConnectionObject() throws PersistenceManagementServiceFactoryException {
        beginTransaction();
        Connection conn = getEntityManager().unwrap(java.sql.Connection.class);
        return conn;
    }
}
