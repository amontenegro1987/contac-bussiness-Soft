/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.controller;

import contac.commons.app.BaseController;
import org.apache.log4j.Logger;

/**
 * Controlador para la administracion de tareas de login
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-13-2010
 * Time: 03:08:46 PM
 */
public class LoginController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(LoginController.class);

    //FIELDS
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Login user to server
     * @throws Exception, Exception
     */
    public void login() throws Exception{

        logger.info("Login to server...!");

        try {

            //Login user to server
            getManagerServiceClientApp().LoginServer(username, password);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
