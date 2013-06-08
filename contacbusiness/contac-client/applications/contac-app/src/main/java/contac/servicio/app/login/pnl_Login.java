/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.login;


import contac.commons.app.ManagerServiceClientApp;
import contac.commons.form.label.JOptionErrorPane;
import contac.internationalization.LanguageLocale;
import contac.servicio.app.controller.LoginController;
import contac.servicio.app.mdi.MDIForm;
import contac.servicio.app.mdi.clsCBAService;
import org.jdesktop.swingx.JXBusyLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hmHurbina
 */
public class pnl_Login extends JPanel {

    private int caller_object;
    private static final int JFRAME = 0;
    private static final int JDIALOG = 1;
    private frm_Login frm;
    private dlg_Login dlg;
    private Thread hilo;
    private boolean isConnected = false;

    MDIForm mdi = null;

    private JButton aceptarBtn;
    private JButton cancelarBtn;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel jLabel3;
    private JPanel jPanel1;
    private JPasswordField txtContrasenia;
    private JTextField txtUsuario;
    private JXBusyLabel jXBusyLabel1;

    /**
     * Controlador
     */
    private LoginController controller;

    //Message Resource Bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/servicio/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());


    /**
     * Creates new form pnl_Login
     */
    public pnl_Login(frm_Login frm_Login) {
        this.caller_object = pnl_Login.JFRAME;
        this.frm = frm_Login;
        initComponents();
        this.txtUsuario.requestFocus();

        //Init controller
        controller = new LoginController();

    }

    public pnl_Login(dlg_Login dlg_Login) {
        this.caller_object = pnl_Login.JDIALOG;
        this.dlg = dlg_Login;
        initComponents();
        this.txtUsuario.requestFocus();

        //Init controller
        controller = new LoginController();

    }

    public int caller() {
        return this.caller_object;
    }

    public MDIForm getMDI() {
        return this.dlg.getMDI();
    }

    public void removeActionListeners() {
        ActionListener[] action1 = this.txtUsuario.getActionListeners();
        ActionListener[] action2 = this.txtContrasenia.getActionListeners();
        this.txtUsuario.removeActionListener(action1[0]);
        this.txtContrasenia.removeActionListener(action2[0]);
    }

    public void addActionListener() {
        txtUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        txtContrasenia.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseniaActionPerformed(evt);
            }
        });
    }


    public void conectar() throws IOException {
        if (isConnected) {
            this.jXBusyLabel1.setText("Conexi\u00F3n satisfactoria...");
            switch (caller()) {
                case 0: {
                    closeCaller();
                    break;
                }
                case 1: {
                    closeCaller();
                    break;
                }
            }
        } else {
            this.jXBusyLabel1.setBusy(false);
            this.jXBusyLabel1.setText("");
            this.addActionListener();
        }
    }


    public void closeCaller() {
        switch (this.caller_object) {
            case pnl_Login.JFRAME: {
                frm.closeLoginFrame();
                break;
            }
            case pnl_Login.JDIALOG: {
                dlg.closeLoginDialog();
                break;
            }
        }
    }

    public void initConnection() {

        try {

            this.aceptarBtn.setEnabled(false);
            this.cancelarBtn.setEnabled(false);
            this.txtUsuario.setEditable(false);
            this.txtUsuario.setBackground(java.awt.SystemColor.info);
            this.txtContrasenia.setEditable(false);
            this.txtContrasenia.setBackground(java.awt.SystemColor.info);
            jXBusyLabel1.setBusy(true);
            jXBusyLabel1.setText("Conectando...");
            this.startConexion();

        } finally {
            System.gc();
        }
    }

    public void stopConexion() {

        this.aceptarBtn.setEnabled(true);
        this.cancelarBtn.setEnabled(true);
        this.txtUsuario.setEditable(true);
        this.txtUsuario.setBackground(Color.WHITE);
        this.txtContrasenia.setEditable(true);
        this.txtContrasenia.setBackground(Color.WHITE);
        this.jXBusyLabel1.setBusy(false);
        this.jXBusyLabel1.setText("");
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblUsuario = new javax.swing.JLabel();
        lblContrasenia = new javax.swing.JLabel();
        txtUsuario = new JTextField();
        jXBusyLabel1 = new JXBusyLabel();
        txtContrasenia = new JPasswordField();
        jPanel1 = new JPanel();
        cancelarBtn = new JButton();
        aceptarBtn = new JButton();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(466, 313));
        setMinimumSize(new java.awt.Dimension(466, 313));
        setPreferredSize(new java.awt.Dimension(466, 313));
        setLayout(null);

        lblUsuario.setText("Nombre de Usuario");
        add(lblUsuario);
        lblUsuario.setBounds(20, 140, 100, 20);

        lblContrasenia.setText("Contrase\u00F1a");
        add(lblContrasenia);
        lblContrasenia.setBounds(20, 170, 100, 20);

        txtUsuario.setText("");
        txtUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        add(txtUsuario);
        txtUsuario.setBounds(130, 140, 140, 20);
        add(jXBusyLabel1);
        jXBusyLabel1.setBounds(20, 230, 250, 40);

        txtContrasenia.setText("");
        txtContrasenia.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseniaActionPerformed(evt);
            }
        });
        add(txtContrasenia);
        txtContrasenia.setBounds(130, 170, 140, 20);

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        aceptarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/servicio/app/images/button_okx16.png"))); // NOI18N
        aceptarBtn.setText("Aceptar");
        aceptarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarBtnActionPerformed(evt);
            }
        });

        jPanel1.add(aceptarBtn);

        cancelarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/servicio/app/images/button_cancelx16.png"))); // NOI18N
        cancelarBtn.setText("Cancelar");
        cancelarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBtnActionPerformed(evt);
            }
        });

        jPanel1.add(cancelarBtn);

        add(jPanel1);
        jPanel1.setBounds(10, 280, 270, 35);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/servicio/app/images/LoginInventory.png"))); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(0, -10, 470, 350);
    }

    private void cancelarBtnActionPerformed(java.awt.event.ActionEvent evt) {

        System.out.println("Caller object: " + this.caller_object);

        switch (this.caller_object) {
            case 0: {
                if (this.frm.closeFrame()) {
                    this.frm.closeLoginFrame();
                }
                break;
            }
            case 1: {
                if (this.dlg.getMDI().closeMDI()) {
                    this.dlg.closeLoginDialog();
                    this.getMDI().closeFrame();
                }
                break;
            }
        }
    }

    public void startConexion() {
        hilo = new loadThread();
        hilo.start();
    }

    public class loadThread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
            try {
                conectar();
                //new clsCBAService();
            } catch (IOException ex) {
                Logger.getLogger(pnl_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void aceptarBtnActionPerformed(java.awt.event.ActionEvent evt) {
        //Login to System
        loginToSystem();
    }

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {
        this.removeActionListeners();
        this.initConnection();
    }

    private void txtContraseniaActionPerformed(java.awt.event.ActionEvent evt) {
        //Login to System
        loginToSystem();
    }

    //GEN_: Login to System with username and password
    private void loginToSystem() {

        //Obteniendo valores
        String username = txtUsuario.getText();
        String contrasenia = new String(txtContrasenia.getPassword());

        //Setting valores en el controlador
        controller.setUsername(username);
        controller.setPassword(contrasenia);

        try {

            //Autenticar usuario en el servidor
            controller.login();

            //Iniciando connection
            initConnection();

            //conexion exitosa
            this.isConnected = true;

            //Setting MDIForm visible
            getMDI().getGlassPane().setVisible(false);

            //Removing action listeners and set MDI enabled
            removeActionListeners();
            getMDI().setEnabled(true);

            //Setting user information in status bar
            getMDI().showUserInformation(ManagerServiceClientApp.getInstance().getUsername());

        } catch (Exception e) {
            //Show confirmation message
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.LOGIN.ERRORMESSAGE"),
                    e.getMessage());
        }
    } //END_ : Login to System.
}
