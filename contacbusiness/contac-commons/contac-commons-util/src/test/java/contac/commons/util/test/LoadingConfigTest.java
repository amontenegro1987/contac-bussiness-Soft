package contac.commons.util.test;

import contac.config.ContacConfigInitService;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.Properties;

/**
 * Copyright (c) 2012, OpenJanela. All rights reserved.
 * User: EMontenegro
 * Date: 08-04-12
 * Time: 07:35 PM
 */
public class LoadingConfigTest {

    public static void main(String args[]) {

        ContacConfigInitService contacConfig = new ContacConfigInitService();

        try {

            Properties properties = new Properties();
            properties.load(contacConfig.getPersistenceConfigPath());

            System.out.println(properties.getProperty("hibernate.connection.username"));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
