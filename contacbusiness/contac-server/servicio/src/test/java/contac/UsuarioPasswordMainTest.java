package contac;

import contac.logger.ContacApacheLog4j;
import contac.security.Security;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;

/**
 * Servicio de prueba para obtener un password MD-5 Base-64 usuario prueba
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-21-2010
 * Time: 03:22:00 PM
 */
public class UsuarioPasswordMainTest {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(UsuarioPasswordMainTest.class);

    //Main servicio password create
    public static void main(String args[]) {


        try {

            //Configurar servicio de Logger
            new ContacApacheLog4j();

            //Password del usuario de prueba
            String password = "temporal";

            //Convertir password en formato MD-5 BASE-64
            String passwordHash = Security.createPasswordHash(password);

            logger.info("Password Base-64: " + passwordHash);

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }
}
