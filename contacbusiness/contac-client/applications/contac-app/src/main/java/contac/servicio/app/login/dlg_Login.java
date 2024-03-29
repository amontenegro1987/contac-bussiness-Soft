/*
 * Copyright 2010 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.login;

import contac.servicio.app.mdi.MDIForm;

/**
 * @author hmhurbina
 *
 * Created on 24 de octubre de 2008, 10:54 AM
 */
public class dlg_Login extends javax.swing.JDialog {

    private pnl_Login login;
    private MDIForm mdi;

    /**
     * Constructor
     * @param mdi, MNI form
     */
    public dlg_Login(MDIForm mdi) {
        super(mdi, true);
        initComponents();
        this.initLoginPanel();
        this.mdi = mdi;
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
     * Close login dialog
     */
    public void closeLoginDialog() {
        this.dispose();
    }

    /**
     * return MDIForm
     * @return MDI
     */
    public MDIForm getMDI() {
        return this.mdi;
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
        setTitle("Autenticaci\u00f3n Contac Business");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(null);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 460) / 2, (screenSize.height - 357) / 2, 460, 357);
    }

    /**
     * Window closing
     * @param evt, WindowEvent
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {

        if (this.getMDI().closeMDI()) {
            this.closeLoginDialog();
        }
    }

}
