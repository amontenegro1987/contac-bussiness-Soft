/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlAdmonContrasenia.java
 *
 * Created on Jul 29, 2011, 11:37:02 AM
 */

package contac.administracion.usuario.app;

/**
 *
 * @author Eddy
 */
public class pnlAdmonContrasenia extends javax.swing.JPanel {

    /** Creates new form pnlAdmonContrasenia */
    public pnlAdmonContrasenia() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usuarioHeader = new org.jdesktop.swingx.JXHeader();
        pnlContraseniaForm = new javax.swing.JPanel();
        lblContrasenia = new javax.swing.JLabel();
        lblConfirmaContrasenia = new javax.swing.JLabel();
        txtConfirmaContrasenia = new javax.swing.JPasswordField();
        txtContrasenia = new javax.swing.JPasswordField();
        lblConfirmaContrasenia1 = new javax.swing.JLabel();
        txtConfirmaContrasenia1 = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        usuarioHeader.setForeground(new java.awt.Color(255, 153, 0));
        usuarioHeader.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/administracion/app/mensajes/Mensajes_es"); // NOI18N
        usuarioHeader.setTitle(bundle.getString("CONTAC.FORM.ADMINISTRACONTRASENIA")); // NOI18N
        usuarioHeader.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(usuarioHeader, java.awt.BorderLayout.NORTH);

        lblContrasenia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContrasenia.setText(bundle.getString("CONTAC.FORM.ADMINISTRACONTRASENIA.CONTRASENIAANTERIOR")); // NOI18N
        lblContrasenia.setPreferredSize(new java.awt.Dimension(89, 20));

        lblConfirmaContrasenia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblConfirmaContrasenia.setText(bundle.getString("CONTAC.FORM.ADMINISTRACONTRASENIA.CONTRASENIANUEVA")); // NOI18N
        lblConfirmaContrasenia.setPreferredSize(new java.awt.Dimension(89, 20));

        txtConfirmaContrasenia.setText("jPasswordField1");

        txtContrasenia.setText("jPasswordField1");

        lblConfirmaContrasenia1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblConfirmaContrasenia1.setText(bundle.getString("CONTAC.FORM.ADMINISTRAUSUARIO.CONTRASENIACONFIRMAR")); // NOI18N
        lblConfirmaContrasenia1.setPreferredSize(new java.awt.Dimension(89, 20));

        txtConfirmaContrasenia1.setText("jPasswordField1");

        btnAceptar.setText(bundle.getString("CONTAC.FORM.ADMINISTRAALMACENES.BTNACCEPTAR")); // NOI18N

        btnCancelar.setText(bundle.getString("CONTAC.FORM.ADMINISTRAALMACENES.BTNCANCELAR")); // NOI18N

        org.jdesktop.layout.GroupLayout pnlContraseniaFormLayout = new org.jdesktop.layout.GroupLayout(pnlContraseniaForm);
        pnlContraseniaForm.setLayout(pnlContraseniaFormLayout);
        pnlContraseniaFormLayout.setHorizontalGroup(
            pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlContraseniaFormLayout.createSequentialGroup()
                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlContraseniaFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnlContraseniaFormLayout.createSequentialGroup()
                                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblContrasenia, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblConfirmaContrasenia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(txtContrasenia, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                                    .add(txtConfirmaContrasenia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 217, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(pnlContraseniaFormLayout.createSequentialGroup()
                                .add(lblConfirmaContrasenia1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(txtConfirmaContrasenia1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 217, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(pnlContraseniaFormLayout.createSequentialGroup()
                        .add(111, 111, 111)
                        .add(btnAceptar)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(btnCancelar)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlContraseniaFormLayout.setVerticalGroup(
            pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlContraseniaFormLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblContrasenia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtContrasenia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblConfirmaContrasenia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtConfirmaContrasenia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblConfirmaContrasenia1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtConfirmaContrasenia1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(pnlContraseniaFormLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnAceptar)
                    .add(btnCancelar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        add(pnlContraseniaForm, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblConfirmaContrasenia;
    private javax.swing.JLabel lblConfirmaContrasenia1;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JPanel pnlContraseniaForm;
    private javax.swing.JPasswordField txtConfirmaContrasenia;
    private javax.swing.JPasswordField txtConfirmaContrasenia1;
    private javax.swing.JPasswordField txtContrasenia;
    private org.jdesktop.swingx.JXHeader usuarioHeader;
    // End of variables declaration//GEN-END:variables

}
