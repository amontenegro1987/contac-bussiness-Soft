package contac.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLStreamHandler;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Copyright (c) 2012, OpenJanela. All rights reserved.
 * User: EMontenegro
 * Date: 08-04-12
 * Time: 02:34 PM
 */
public class ContacConfigInitService {

    /**
     * Constructor default
     */
    public ContacConfigInitService() {
    }

    /**
     * Configurar servicio config persistencia
     *
     * @return FileInputStream
     */
    public FileInputStream getPersistenceConfigPath() {

        //InputStream object
        FileInputStream fis = null;

        try {

            //Getting path
            String path = ContacConfigInitService.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            path = URLDecoder.decode(path, "UTF-8");

            File runJar = new File(path);
            File homeFile = runJar.getParentFile().getParentFile().getParentFile().getParentFile();
            String homeDir = homeFile.getCanonicalPath();

            //Getting input stream
            fis = new FileInputStream(homeDir + "/config/persistence.properties");

        } catch (Exception e) {
            //Nothing to show
        }

        return fis;
    }

    /**
     * Configurar servicio de rmi client
     *
     * @return FileInputStream
     */
    public FileInputStream getRmiConfigPath() {

        //InputStream object
        FileInputStream fis = null;

        try {

            //Getting path
            String path = ContacConfigInitService.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            path = URLDecoder.decode(path, "UTF-8");

            File runJar = new File(path);
            File homeFile = runJar.getParentFile().getParentFile().getParentFile().getParentFile();
            String homeDir = homeFile.getCanonicalPath();

            //Getting input stream
            fis = new FileInputStream(homeDir + "/config/rmi.properties");

        } catch (Exception e) {
            //Nothing to show
        }

        return fis;
    }
}
