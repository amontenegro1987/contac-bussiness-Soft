/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package contac.servicio.app.mdi;

import contac.commons.app.ManagerServiceClientApp;
import contac.commons.form.panel.GenericFrame;
import contac.modelo.entity.Compania;
import contac.servicio.app.menu.*;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessException;
import org.apache.log4j.Logger;
import org.pushingpixels.flamingo.common.*;
import org.pushingpixels.flamingo.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.ribbon.RibbonApplicationMenuEntrySecondary;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import test.svg.transcoded.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.ProtectionDomain;
import java.util.ResourceBundle;

/**
 * MAIN class for MDIForm Contac Business Client
 */
public class MDIForm extends GenericFrame {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(MDIForm.class);

    //Message url
    protected static final String messageUrl = "contac.servicio.app.mensajes.Mensajes";

    //Message Bundle
    protected ResourceBundle resourceBundle;

    //Ribbon modules configurados
    protected MenuFacturacion menuFacturacion;
    protected MenuInventario menuInventario;
    protected MenuProveedores menuProveedores;
    protected MenuClientes menuClientes;
    protected MenuAdministracion menuAdministracion;

    /**
     * Creates new form MDIForm
     *
     * @throws java.io.IOException, Exception
     */
    public MDIForm() throws IOException {

        //******************************************************************
        // 0. Call super constructor
        //******************************************************************
        super();

        //******************************************************************
        // 1. Init resource bundle
        //******************************************************************
        resourceBundle = ResourceBundle.getBundle(messageUrl, currLocale);

        //******************************************************************
        // 2. Config logo and software name
        //******************************************************************
        setApplicationIcon(ImageWrapperResizableIcon.getIcon(getClass().
                getResource("/contac/resources/images/InventoryLogo.png"), new Dimension(32, 32)));
        setTitle(resourceBundle.getString("CONTAC.SOFTWARE.NAME"));

        //******************************************************************
        // 3. Init Style Frame
        //******************************************************************
        style = new Style(this);

        //******************************************************************
        // 4. Config menu
        //******************************************************************
        configMenu();
        configTaskBar();
        configApplicationMenu();
        initComponents();

        //******************************************************************
        // 5. Connection and User information
        //******************************************************************
        this.activeCompanyLbl.setText(resourceBundle.getString("CONTAC.SOFTWARE.COMPANY") + ": ");
        this.activeUserLbl.setText(resourceBundle.getString("CONTAC.SOFTWARE.USER") + ": ");

    }

    /**
     * Configure menu application
     */
    private void configMenu() {

        //Principal menu configuration
        menuFacturacion = new MenuFacturacion(style);
        menuInventario = new MenuInventario(style);
        menuProveedores = new MenuProveedores(style);
        menuClientes = new MenuClientes(style);
        menuAdministracion = new MenuAdministracion(style);

        getRibbon().addContextualTaskGroup(menuFacturacion);
        getRibbon().addContextualTaskGroup(menuInventario);
        getRibbon().addContextualTaskGroup(menuProveedores);
        getRibbon().addContextualTaskGroup(menuClientes);

        getRibbon().setVisible(menuFacturacion, true);
        getRibbon().setVisible(menuInventario, true);
        getRibbon().setVisible(menuProveedores, true);
        getRibbon().setVisible(menuClientes, true);

        getRibbon().addTask(menuAdministracion);

        //Menu Help configuration
        this.getRibbon().configureHelp(new help_browser(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement action here
            }
        });

    }

    /**
     * Config Task bar
     */
    private void configTaskBar() {
        // taskbar components
        JCommandButton taskbarButtonPaste = new JCommandButton("", new document_save());
        taskbarButtonPaste.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);

        taskbarButtonPaste.setActionRichTooltip(new RichTooltip("Guardar", "Guarda los datos contenidos en los paneles usados"));
        taskbarButtonPaste.setPopupRichTooltip(new RichTooltip("Guardar", "Guarda los datos contenidos en los paneles usados"));
        this.getRibbon().addTaskbarComponent(taskbarButtonPaste);

        JCommandButton taskbarButtonCopy = new JCommandButton("", new system_log_out());
        taskbarButtonCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeMDI();
            }
        });

        this.getRibbon().addTaskbarComponent(taskbarButtonCopy);
        this.getRibbon().addTaskbarComponent(new JSeparator(JSeparator.VERTICAL));

        JCommandButton taskbarButtonFind = new JCommandButton("", new edit_find());
        taskbarButtonFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getStyle().addPanel("PaginaInicio", "contac.servicio.app.panel.InicioPanel");
            }
        });

        this.getRibbon().addTaskbarComponent(taskbarButtonFind);
    }

    /**
     * Config Application menu
     */
    private void configApplicationMenu() {
        RibbonApplicationMenuEntryPrimary amEntryNew = new RibbonApplicationMenuEntryPrimary(
                new document_new(), "Pagina de Inicio", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getStyle().addPanel("PaginaInicio", "contac.servicio.app.panel.InicioPanel");
            }
        }, CommandButtonKind.ACTION_ONLY);

        RibbonApplicationMenuEntryPrimary amEntryOpen = new RibbonApplicationMenuEntryPrimary(new document_open(), "Generales", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        }, CommandButtonKind.ACTION_ONLY);

        RibbonApplicationMenuEntryPrimary amEntrySaveAs = new RibbonApplicationMenuEntryPrimary(
                new document_save_as(), "Facturación", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);

        RibbonApplicationMenuEntrySecondary amEntrySaveAsWord = new RibbonApplicationMenuEntrySecondary(
                new x_office_document(), "Nueva Factura", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }, CommandButtonKind.ACTION_ONLY);

        amEntrySaveAsWord.setDescriptionText("Crear una nueva factura para clientes al por mayor.");

        RibbonApplicationMenuEntrySecondary amEntrySaveAsHtml = new RibbonApplicationMenuEntrySecondary(new text_html(), "Agregar Clientes", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }, CommandButtonKind.ACTION_ONLY);

        amEntrySaveAsHtml.setDescriptionText("Guarda la información relacionada con todos los tipos de clientes de la empresa.");
        amEntrySaveAsHtml.setActionKeyTip("H");
        RibbonApplicationMenuEntrySecondary amEntrySaveAsOtherFormats = new RibbonApplicationMenuEntrySecondary(
                new document_save_as(), "Agregar Articulos", new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        }, CommandButtonKind.ACTION_ONLY);

        amEntrySaveAs.addSecondaryMenuGroup("Guarde sus transacciones", amEntrySaveAsWord, amEntrySaveAsHtml, amEntrySaveAsOtherFormats);
        amEntrySaveAsOtherFormats.setDescriptionText("Valida a cada trabajador en la Lista Activa.");

        RibbonApplicationMenuEntryPrimary amEntryPrint = new RibbonApplicationMenuEntryPrimary(
                new document_print(), "Inventario", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        }, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);

        RibbonApplicationMenuEntrySecondary amEntryPrintSelect = new RibbonApplicationMenuEntrySecondary(new printer(), "Agregar Ordenes de Compra", null, CommandButtonKind.ACTION_ONLY);
        amEntryPrintSelect.setDescriptionText("Permite la grabacion de una nueva orden de compra.");

        RibbonApplicationMenuEntrySecondary amEntryPrintDefault = new RibbonApplicationMenuEntrySecondary(new document_print(), "Registrar Proveedor", null, CommandButtonKind.ACTION_ONLY);
        amEntryPrintDefault.setDescriptionText("Permitir el registro de los proveedores en la empresa.");

        RibbonApplicationMenuEntrySecondary amEntryPrintPreview = new RibbonApplicationMenuEntrySecondary(new document_print(), "Registrar Categorias", null, CommandButtonKind.ACTION_ONLY);
        amEntryPrintPreview.setDescriptionText("Permitir el registro de las categorias.");

        amEntryPrint.addSecondaryMenuGroup("Preview and print the document", amEntryPrintSelect, amEntryPrintDefault, amEntryPrintPreview);

        RibbonApplicationMenuEntryPrimary amEntryExit = new RibbonApplicationMenuEntryPrimary(
                new system_log_out(), "Salir de CBA Inventory", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeMDI();
            }
        }, CommandButtonKind.ACTION_ONLY);


        RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu();
        applicationMenu.addMenuEntry(amEntryNew);
        applicationMenu.addMenuEntry(amEntryOpen);
        applicationMenu.addMenuEntry(amEntrySaveAs);
        applicationMenu.addMenuEntry(amEntryPrint);
        applicationMenu.addMenuEntry(amEntryExit);

        RibbonApplicationMenuEntryFooter amFooterProps = new RibbonApplicationMenuEntryFooter(
                new document_properties(), "Opciones", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        RibbonApplicationMenuEntryFooter amFooterExit = new RibbonApplicationMenuEntryFooter(
                new system_log_out(), "Salir", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeMDI();
            }
        });

        amFooterExit.setEnabled(false);
        applicationMenu.addFooterEntry(amFooterProps);
        applicationMenu.addFooterEntry(amFooterExit);

        this.getRibbon().setApplicationMenu(applicationMenu);

        RichTooltip appMenuRichTooltip = new RichTooltip();
        appMenuRichTooltip.setTitle("Contact Business Administration Inventory");
        appMenuRichTooltip.addDescriptionSection("Presiona click aqui para realizar configuraciones generales");
        appMenuRichTooltip.addFooterSection("Presione F1 para mas ayuda");

    }


    public String getUrlLogo() {
        return getClass().getResource("/contac/resources/images/InventoryLogo.png").toString();
    }

    /**
     * Close MDI Form
     *
     * @return boolean
     */
    public boolean closeMDI() {
        return true;
    }

    private class ExpandActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }

    private AbstractCommandButton getIconButton(final Icon icon,
                                                boolean isToggle, boolean isSelected, boolean hasPopup) {
        ResizableIcon resizableIcon = new ResizableIcon() {
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            @Override
            public int getIconHeight() {
                return this.height;
            }

            @Override
            public int getIconWidth() {
                return this.width;
            }

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                icon.paintIcon(c, g, x, y);
            }

            @Override
            public void setDimension(Dimension newDimension) {
                this.width = newDimension.width;
                this.height = newDimension.height;
            }
        };
        AbstractCommandButton button = isToggle ? new JCommandToggleButton("",
                resizableIcon) : new JCommandButton("", resizableIcon);
        button.setDisplayState(CommandButtonDisplayState.SMALL);
        button.setGapScaleFactor(0.5);
        if (isSelected)
            button.getActionModel().setSelected(true);

        Insets currInsets = button.getInsets();

        return button;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    public void initComponents() {

        activeUserLbl = new JLabel();
        activeCompanyLbl = new JLabel();
        jLinkButton1 = new com.l2fprod.common.swing.JLinkButton();

        //setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(resourceBundle.getString("CONTAC.SOFTWARE.NAME"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //statusBar.setPreferredSize(new Dimension(27, 25));
        //statusBar.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        activeUserLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        activeUserLbl.setIcon(ImageWrapperResizableIcon.getIcon(getClass().
                getResource("/contac/servicio/app/images/user_lock_256x48.png"), new Dimension(18, 18))); // NOI18N
        activeUserLbl.setText(resourceBundle.getString("CONTAC.SOFTWARE.USER") + ": ");
        getStatusBar().add(activeUserLbl);

        activeCompanyLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        activeCompanyLbl.setIcon(ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/images/bank_256x48.png"),
                new Dimension(18, 18))); // NOI18N
        activeCompanyLbl.setText(resourceBundle.getString("CONTAC.SOFTWARE.COMPANY") + ": ");
        getStatusBar().add(activeCompanyLbl);

        jLinkButton1.setForeground(new Color(0, 0, 255));
        jLinkButton1.setIcon(new ImageIcon(getClass().getResource("/contac/servicio/app/images/Connectx48.png"))); // NOI18N
        jLinkButton1.setText("Desconectar/Conectar");
        jLinkButton1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLinkButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jLinkButton1ActionPerformed(evt);
            }
        });
        getStatusBar().add(jLinkButton1);

        getContentPane().add(getStatusBar(), java.awt.BorderLayout.SOUTH);
        getContentPane().add(getPrincipalDesk(), java.awt.BorderLayout.CENTER);

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 840) / 2, (screenSize.height - 649) / 2, 840, 649);
    }// </editor-fold>//GEN-END:initComponents


    @Override
    public void closeFrame() {
        //Getting response
        int response = JOptionPane.showConfirmDialog(this, resourceBundle.getString("CONTAC.MESSAGE.EXIT"),
                resourceBundle.getString("CONTAC.SOFTWARE.NAME"), JOptionPane.YES_NO_OPTION);

        //Response afirmative close application
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void jLinkButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jLinkButton1ActionPerformed

    }//GEN-LAST:event_jLinkButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel activeUserLbl;
    private JLabel activeCompanyLbl;
    private com.l2fprod.common.swing.JLinkButton jLinkButton1;
    // End of variables declaration//GEN-END:variables


    //*****************************************************************************
    //Getters and Setters
    //*****************************************************************************

    /**
     * Show user information
     *
     * @param username, String
     */
    public void showUserInformation(String username) {

        try {

            //Buscando compania del usuario por nombre de usuario
            Compania compania = ManagerServiceClientApp.getInstance().getManagerServiceFactory().getManagerSeguridadServiceBusiness().
                    buscarCompaniaUsuario(username);

            //Setting MDI Information value
            this.activeUserLbl.setText(resourceBundle.getString("CONTAC.SOFTWARE.USER") + ": " + username);

            if (compania != null)
                this.activeCompanyLbl.setText(resourceBundle.getString("CONTAC.SOFTWARE.COMPANY") + ": " + compania.getRazonSocial());

        } catch (ManagerSeguridadServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
