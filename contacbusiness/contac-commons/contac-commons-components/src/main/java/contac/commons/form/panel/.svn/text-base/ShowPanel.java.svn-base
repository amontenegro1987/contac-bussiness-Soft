/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.form.panel;

import contac.commons.app.BaseController;
import org.apache.log4j.Logger;
import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ContentManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Show generic panel into MDI Form
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-15-2010
 * Time: 12:14:36 PM
 */
public class ShowPanel {

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(ShowPanel.class);

    /**
     * Error message
     */
    private static String errorMsg = "Error instantiating the configured GenericPanel.";

    /**
     * Content manager
     */
    private ContentManager manager;
    /**
     * Generic Frame
     */
    private GenericFrame frame;

    /**
     * Default Constructor
     *
     * @param frame, GenericFrame
     */
    public ShowPanel(GenericFrame frame) {
        this.frame = frame;
    }

    /**
     * Show panel into the manager content
     *
     * @param key,       String
     * @param clazzName, String
     */
    public void showPanel(String key, String clazzName) {

        //Generic panel
        GenericPanel panel;

        if (!contentExist(key)) {

            String factoryClassName = System.getProperty("contac.commons.form.panel.GenericPanel", clazzName);

            try {

                logger.info(factoryClassName);

                //Creating GenericPanel instance
                Constructor constructor = Class.forName(factoryClassName).getConstructor(GenericFrame.class);

                //Instantiation Generic panel
                panel = (GenericPanel) constructor.newInstance(this.frame);

                //Adding panel
                addPanel(panel);

            } catch (ClassNotFoundException e) {
                logger.error(errorMsg, e);
            } catch (InstantiationException e) {
                logger.error(errorMsg, e);
            } catch (IllegalAccessException e) {
                logger.error(errorMsg, e);
            } catch (NoSuchMethodException e) {
                logger.error(errorMsg, e);
            } catch (InvocationTargetException e) {
                logger.error(errorMsg, e);
            }
        }
    }

    /**
     * Show panel into the manager content
     *
     * @param key,        String
     * @param clazzName,  String
     * @param controller, BaseController
     */
    public void showPanel(String key, String clazzName, BaseController controller) {
        //Generic panel
        GenericPanel panel;

        if (!contentExist(key)) {

            String factoryClassName = System.getProperty("contac.commons.form.panel.GenericPanel", clazzName);

            try {

                logger.info(factoryClassName);

                //Creating GenericPanel instance
                Constructor constructor = Class.forName(factoryClassName).getConstructor(GenericFrame.class, BaseController.class);

                //Instantiation Generic panel
                panel = (GenericPanel) constructor.newInstance(this.frame, controller);

                //Adding panel
                addPanel(panel);

            } catch (ClassNotFoundException e) {
                logger.error(errorMsg, e);
            } catch (InstantiationException e) {
                logger.error(errorMsg, e);
            } catch (IllegalAccessException e) {
                logger.error(errorMsg, e);
            } catch (NoSuchMethodException e) {
                logger.error(errorMsg, e);
            } catch (InvocationTargetException e) {
                logger.error(errorMsg, e);
            }
        }
    }

    /**
     * Remove panel to content manager
     *
     * @param panel, GenericPanel
     */
    public void removePanel(GenericPanel panel) {

        try {

            Content[] contens = this.manager.getContents();

            for (int i = 0; i < contens.length; i++) {
                Content content = contens[i];
                if (panel.getKey() == content.getId()) {
                    this.manager.removeContent(content);
                }
            }

        } catch (Exception e) {
            logger.error(errorMsg, e);
        }
    }


    /**
     * Evaluate if a key exist in the manager content
     *
     * @param key, String
     * @return boolean
     */
    private boolean contentExist(String key) {

        boolean exist = false;

        //Getting all contents into the manager
        Content[] contents = this.manager.getContents();

        //Searching for a specific content
        for (Content content : contents) {
            String id = content.getId(); //Content identifier

            if (key.equals(id)) {
                exist = true;
            }
        }

        return exist;
    }

    /**
     * Add panel to content manager
     *
     * @param panel, GenericPanel
     */
    private void addPanel(GenericPanel panel) {

        try {

            this.manager.addContent(panel.getKey(), panel.getTitle(), null, panel).setToolTipText(panel.getTitleBar());
            this.manager.getContent(panel.getKey()).setSelected(true);

        } catch (IllegalArgumentException e) {
            logger.error(errorMsg, e);
        }
    }

    //**********************************************
    //GETTERS AND SETTERS
    //**********************************************

    public ContentManager getManager() {
        return manager;
    }

    public void setManager(ContentManager manager) {
        this.manager = manager;
    }
}
