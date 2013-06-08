package contac.logger;

import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.net.URLDecoder;

/**
 * Configura el servicio de Apache log4j
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-21-2010
 * Time: 03:31:37 PM
 */
public class ContacApacheLog4j {

    /**
     * Configurar servicio de Apache log4j
     * @throws Throwable, Exception
     */
    public ContacApacheLog4j() throws Throwable{

        //Getting path
        String path = ContacApacheLog4j.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        path = URLDecoder.decode(path, "UTF-8");

        File runJar = new File(path);
        File homeFile = runJar.getParentFile().getParentFile().getParentFile().getParentFile();
        String homeDir = homeFile.getCanonicalPath();

        //Getting log4jfile properties
        File log4jFile = new File(homeDir + "/log/config/contac-log4j.xml");

        //Getting URL file resource
        System.out.println("Absolute path log4j config: " + log4jFile.getAbsolutePath());

        //Setting DOM configurator log4j
        DOMConfigurator.configure(log4jFile.getAbsolutePath());

        finalize();
    }
}
