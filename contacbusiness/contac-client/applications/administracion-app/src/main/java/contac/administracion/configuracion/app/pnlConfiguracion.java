   /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//
/*
 * pnlConfiguracion.java
 *
 * Created on 01-15-2012, 10:09:15 PM
 */
package contac.administracion.configuracion.app;

/**
 *
 * @author EMontenegro
 */
public class pnlConfiguracion extends javax.swing.JPanel {

    /** Creates new form pnlConfiguracion */
    public pnlConfiguracion() {
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

        almacenHeader = new org.jdesktop.swingx.JXHeader();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlDatabaseConfig = new javax.swing.JPanel();
        txtServidor = new javax.swing.JTextField();
        lblServidor = new javax.swing.JLabel();
        lblPuerto = new javax.swing.JLabel();
        txtPuerto = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasenia = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        almacenHeader.setForeground(new java.awt.Color(255, 153, 0));
        almacenHeader.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/administracion/app/mensajes/Mensajes_es"); // NOI18N
        almacenHeader.setTitle(bundle.getString("CONTAC.FORM.ADMINISTRACONFIGURACION.TITTLE")); // NOI18N
        almacenHeader.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(almacenHeader, java.awt.BorderLayout.NORTH);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        txtServidor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblServidor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServidor.setText(bundle.getString("CONTAC.FORM.ADMINISTRACONFIGURACION.HOST")); // NOI18N
        lblServidor.setPreferredSize(new java.awt.Dimension(43, 20));

        lblPuerto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPuerto.setText(bundle.getString("CONTAC.FORM.ADMINISTRACONFIGURACION.PUERTO")); // NOI18N
        lblPuerto.setPreferredSize(new java.awt.Dimension(43, 20));

        txtPuerto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsuario.setText(bundle.getString("CONTAC.FORM.ADMINISTRACONFIGURACION.USERNAME")); // NOI18N
        lblUsuario.setPreferredSize(new java.awt.Dimension(43, 20));

        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblContrasenia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContrasenia.setText(bundle.getString("CONTAC.FORM.ADMINISTRACONFIGURACION.PASSWORD")); // NOI18N
        lblContrasenia.setPreferredSize(new java.awt.Dimension(43, 20));

        txtContrasenia.setText("jPasswordField1");

        javax.swing.GroupLayout pnlDatabaseConfigLayout = new javax.swing.GroupLayout(pnlDatabaseConfig);
        pnlDatabaseConfig.setLayout(pnlDatabaseConfigLayout);
        pnlDatabaseConfigLayout.setHorizontalGroup(
            pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatabaseConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatabaseConfigLayout.createSequentialGroup()
                        .addComponent(lblServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                    .addGroup(pnlDatabaseConfigLayout.createSequentialGroup()
                        .addComponent(lblPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtPuerto, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                    .addGroup(pnlDatabaseConfigLayout.createSequentialGroup()
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                    .addGroup(pnlDatabaseConfigLayout.createSequentialGroup()
                        .addComponent(lblContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlDatabaseConfigLayout.setVerticalGroup(
            pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatabaseConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatabaseConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("CONTAC.FORM.ADMINISTRACONFIGURACION.DATASOURCE"), pnlDatabaseConfig); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(412, 40));

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N

        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(134, 134, 134))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXHeader almacenHeader;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblPuerto;
    private javax.swing.JLabel lblServidor;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlDatabaseConfig;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtPuerto;
    private javax.swing.JTextField txtServidor;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
