/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.mdi;

import contac.administracion.moneda.app.pnlTiposCambio;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.panel.StyleGenericFrame;
import contac.internationalization.LanguageLocale;
import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;
import org.noos.xing.mydoggy.*;
import org.noos.xing.mydoggy.event.ContentManagerUIEvent;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;

import contac.servicio.app.panel.pnlClientes;
import contac.servicio.app.panel.pnlProveedores;

/**
 * @author hmurbina
 */
public class Style extends StyleGenericFrame {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(StyleGenericFrame.class);

    //Message Resource Bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/servicio/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Group constants
     */
    private static String MDIGROUP_ONE = "MAIN_GROUP_ONE";
    private static String MDIGROUP_TWO = "MAIN_GROUP_TWO";

    /**
     * Tools Panel
     */
    private int toolDescuento;

    /**
     * Default Constructor
     */
    public Style() {
        super();
    }

    /**
     * Alternative Constructor
     *
     * @param frame, GenericFrame
     */
    public Style(GenericFrame frame) {
        super(frame);

        //Configure desktop
        this.configDesk();
    }

    /**
     * Remove tools
     */
    public void removeDesktopTools() {
        this.toolWindowManager.removeToolWindowGroup(MDIGROUP_ONE);
        this.toolWindowManager.removeToolWindowGroup(MDIGROUP_TWO);
        this.toolWindowManager.unregisterAllToolWindow();
    }


    //***************************************************************************************
    //UTILITY METHODS
    //***************************************************************************************

    /**
     * Configure desk
     */
    private void configDesk() {

        //Setting principal desk properties layout
        JPopupMenu.setDefaultLightWeightPopupEnabled(true);
        principalDesk.setLayout(new TableLayout(new double[][]{{0, -1, 0}, {0, -1, 0}}));

        //Init tools
        initTools();
    }

    /**
     * Init tools
     */
    private void initTools() {

        //Setting MYDOGGY tool manager
        MyDoggyToolWindowManager mdManager = new MyDoggyToolWindowManager();
        toolWindowManager = mdManager;

        //Init desktop tools
        this.initDesktopTools();

        //Available toolWindowManager
        for (ToolWindow window : toolWindowManager.getToolWindows()) {
            logger.info("Window Id: " + window.getId());
            window.setAvailable(true);
            window.setType(ToolWindowType.DOCKED);
        }

        //Init main container
        this.initMainContainer();

        //Configure main desk
        principalDesk.add(mdManager, "1,1,");
        internalDesk.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        internalDesk.setBackground(Color.GRAY);
        internalDesk.setBorder(BorderFactory.createEmptyBorder());
        internalDesk.setLayout(null);
    }

    /**
     * Init desktop tools
     */
    private void initDesktopTools() {

        //*****************************************
        //Create panel tools
        //*****************************************
        pnlClientes proyectos = new pnlClientes(frame);

        this.toolDescuento = toolWindowManager.registerToolWindow("Tabla de Descuento", "", null, new pnlTiposCambio(frame), ToolWindowAnchor.LEFT).getIndex();
        int toolClientes = toolWindowManager.registerToolWindow("Clientes", "", null, proyectos, ToolWindowAnchor.LEFT).getIndex();
        int toolProveedores = toolWindowManager.registerToolWindow("Proveedores", "", null, new pnlProveedores(frame), ToolWindowAnchor.RIGHT).getIndex();
        int toolTasaCambio = toolWindowManager.registerToolWindow(messageBundle.getString("CONTAC.MENU.TASASCAMBIO"),
                "", null, new pnlTiposCambio(frame), ToolWindowAnchor.RIGHT).getIndex();

        //*****************************************
        //Create Group tools
        //*****************************************

        //Config group One
        ToolWindowGroup groupOne = this.toolWindowManager.getToolWindowGroup(MDIGROUP_ONE);
        groupOne.addToolWindow(this.toolWindowManager.getToolWindow(toolDescuento));
        groupOne.addToolWindow(this.toolWindowManager.getToolWindow(toolClientes));
        groupOne.setVisible(true);
        groupOne.setImplicit(true);

        //Config group Two
        ToolWindowGroup groupTwo = this.toolWindowManager.getToolWindowGroup(MDIGROUP_TWO);
        groupTwo.addToolWindow(this.toolWindowManager.getToolWindow(toolProveedores));
        groupTwo.addToolWindow(this.toolWindowManager.getToolWindow(toolTasaCambio));
        groupTwo.setVisible(true);
        groupTwo.setImplicit(true);

        //Call config style
        this.setGroupStyle();
    }

    /**
     * Configure visual style for groups
     */
    private void setGroupStyle() {

        for (ToolWindow option : toolWindowManager.getToolWindows()) {
            SlidingTypeDescriptor sliding = (SlidingTypeDescriptor) option.getTypeDescriptor(ToolWindowType.SLIDING);
            sliding.setEnabled(false);

            FloatingTypeDescriptor floating = (FloatingTypeDescriptor) option.getTypeDescriptor(ToolWindowType.FLOATING);
            floating.setEnabled(false);

            DockedTypeDescriptor docking = (DockedTypeDescriptor) option.getTypeDescriptor(ToolWindowType.DOCKED);
            docking.setDockLength(287);
        }

    }

    /**
     * Init Main container
     */
    private void initMainContainer() {

        //Obtaining content manager
        this.manager = toolWindowManager.getContentManager();
        this.showPanel.setManager(manager);

        //Adding main PaginaInicio
        addPanel("PaginaInicio", "contac.servicio.app.panel.InicioPanel");

        //Configure main container
        configureMainContainer();
    }

    /**
     * Configure main container
     */
    private void configureMainContainer() {

        //Getting tabbedContent Manager
        TabbedContentManagerUI contentManagerUI = (TabbedContentManagerUI) toolWindowManager.getContentManager().getContentManagerUI();
        contentManagerUI.setShowAlwaysTab(true);
        contentManagerUI.setDetachable(false);

        contentManagerUI.setTabPlacement(TabbedContentManagerUI.TabPlacement.TOP);
        contentManagerUI.addContentManagerUIListener(new ContentManagerUIListener() {

            //Registering content UI Remove
            public boolean contentUIRemoving(ContentManagerUIEvent event) {
                GenericPanel panel = null;
                Component comp = manager.getContent(event.getContentUI().getContent().getId()).getComponent();

                if (comp instanceof JPanel) {
                    panel = (GenericPanel) comp;
                }
                return panel.isCloseEnabled();
            }

            public void contentUIDetached(ContentManagerUIEvent arg0) {
                //TODO: Implement process
            }
        });
    }

    /**
     * Return anchor tools
     *
     * @return int
     */
    public int getToolAnchor() {
        DockedTypeDescriptor docking = (DockedTypeDescriptor) this.toolWindowManager.getToolWindow(toolDescuento).getTypeDescriptor(ToolWindowType.DOCKED);
        return docking.getDockLength();
    }

    /**
     * Clean memory....call Garbage collector
     */
    private void cleanMemory() {
        System.gc();
    }
}
 