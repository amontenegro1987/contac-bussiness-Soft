/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.login;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author hmurbina
 *
 * Created on 24 de octubre de 2008, 10:54 AM
 */
public class frm_Login extends javax.swing.JFrame {

    private pnl_Login login;

    /**
     * Constructor
     */
    public frm_Login() {
        initComponents();
        this.initLoginPanel();
        this.setLoginBounds();
        this.setVisible(true);
    }

    /**
     * Init login panel
     */
    private void initLoginPanel() {
        this.login = new pnl_Login(this);
        getContentPane().add(login, java.awt.BorderLayout.CENTER);
    }

    /**
     * Config login bounds
     */
    private void setLoginBounds() {
        Dimension maxSize = new Dimension(450, 320);
        Dimension minSize = new Dimension(450, 320);
        this.setMaximumSize(maxSize);
        this.setMaximumSize(minSize);
    }

    /**
     * Close login frame
     */
    public void closeLoginFrame() {
        this.dispose();
    }

    /**
     * Close frame
     * @return boolean
     */
    public boolean closeFrame() {

        boolean b = false;

        int r = JOptionPane.showConfirmDialog(this, "Desea salir de Contac Business ?", "Confirmación - Contac", JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) {
            b = true;
            System.exit(0);
        } else {
            b = false;
        }
        return b;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Auntentificación Contac");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagenes/InventoryLogo.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 467) / 2, (screenSize.height - 360) / 2, 467, 360);
    }

    /**
     * Clsoe window form
     * @param evt, WindowEvent
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
        this.closeFrame();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new frm_Login();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(frm_Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(frm_Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(frm_Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(frm_Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
