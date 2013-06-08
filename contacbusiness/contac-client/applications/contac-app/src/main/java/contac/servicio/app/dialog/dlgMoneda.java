/*
 * dlg_Moneda.java
 *
 * Created on 04 de febrero de 2009, 12:11 AM
 */
package contac.servicio.app.dialog;

import contac.commons.form.panel.GenericFrame;

/**
 * @author hmurbina
 */
public class dlgMoneda extends javax.swing.JDialog {

    GenericFrame mdi;

    /**
     * Creates new form dlg_Moneda
     */
    public dlgMoneda(GenericFrame mdi, boolean modal) {
        super(mdi, modal);
        this.mdi = mdi;
        initComponents();

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jXHeader1 = new org.jdesktop.swingx.JXHeader();
        pnlCentral = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        txtNombre = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Moneda - CBA Inventory");
        setMinimumSize(new java.awt.Dimension(356, 200));

        jXHeader1.setDescription("");
        jXHeader1.setTitle("Registro de moneda");
        jXHeader1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jXHeader1.setPreferredSize(new java.awt.Dimension(578, 40));
        getContentPane().add(jXHeader1, java.awt.BorderLayout.NORTH);

        pnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos moneda"));

        lblNombre.setText("Nombre");

        chkActivo.setText("Activo");

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
                pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlDatosLayout.createSequentialGroup()
                                        .addComponent(lblNombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                                .addComponent(chkActivo))
                        .addContainerGap())
        );
        pnlDatosLayout.setVerticalGroup(
                pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombre)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkActivo))
        );

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/button_okx16.png"))); // NOI18N
        btnAceptar.setText("Aceptar");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/button_cancelx16.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCentralLayout = new javax.swing.GroupLayout(pnlCentral);
        pnlCentral.setLayout(pnlCentralLayout);
        pnlCentralLayout.setHorizontalGroup(
                pnlCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlCentralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pnlDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCentralLayout.createSequentialGroup()
                                .addComponent(btnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)))
                        .addContainerGap())
        );
        pnlCentralLayout.setVerticalGroup(
                pnlCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlCentralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAceptar)
                                .addComponent(btnCancelar))
                        .addContainerGap())
        );

        getContentPane().add(pnlCentral, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 372) / 2, (screenSize.height - 200) / 2, 372, 200);
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }


    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chkActivo;
    private org.jdesktop.swingx.JXHeader jXHeader1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPanel pnlCentral;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JTextField txtNombre;

}
