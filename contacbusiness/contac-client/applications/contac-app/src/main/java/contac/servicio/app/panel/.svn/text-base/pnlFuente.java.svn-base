/*
 * pnl_Fuente.java
 *
 * Created on 04 de febrero de 2009, 09:54 AM
 */
package contac.servicio.app.panel;

import contac.commons.form.panel.GenericFrame;
import contac.servicio.app.dialog.dlgFuente;

/**
 *
 * @author  SNOA
 */
public class pnlFuente extends pnl_Generico {


    javax.swing.ListSelectionModel mode;
    dlgFuente objFuente;

    /** Creates new form pnl_Fuente */
    public pnlFuente(GenericFrame mdi) {
        setMDI(mdi);
        initComponents();
        setTitulo("Bancos");
        setKey("Fuente");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXHeader1 = new org.jdesktop.swingx.JXHeader();
        pnlCentral = new javax.swing.JPanel();
        tbFuente = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        scrllFuente = new javax.swing.JScrollPane();
        tblFuente = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jXHeader1.setDescription("");
        jXHeader1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXHeader1.setPreferredSize(new java.awt.Dimension(578, 40));
        jXHeader1.setTitle("Bancos");
        add(jXHeader1, java.awt.BorderLayout.NORTH);

        pnlCentral.setLayout(new java.awt.BorderLayout());

        tbFuente.setFloatable(false);
        tbFuente.setPreferredSize(new java.awt.Dimension(100, 35));

        //btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cba/inventory/principal/imagenes/edit_addx16.png"))); // NOI18N
        btnAgregar.setToolTipText("Agregar");
        btnAgregar.setFocusable(false);
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        tbFuente.add(btnAgregar);

        //btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cba/inventory/principal/imagenes/edit.png"))); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.setFocusable(false);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbFuente.add(btnEditar);

        pnlCentral.add(tbFuente, java.awt.BorderLayout.NORTH);

        tblFuente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFuente.getTableHeader().setReorderingAllowed(false);
        scrllFuente.setViewportView(tblFuente);

        pnlCentral.add(scrllFuente, java.awt.BorderLayout.CENTER);

        add(pnlCentral, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
    objFuente = new dlgFuente(getMDI(),true);
    objFuente.setVisible(true);
}//GEN-LAST:event_btnAgregarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private org.jdesktop.swingx.JXHeader jXHeader1;
    private javax.swing.JPanel pnlCentral;
    private javax.swing.JScrollPane scrllFuente;
    private javax.swing.JToolBar tbFuente;
    private javax.swing.JTable tblFuente;
    // End of variables declaration//GEN-END:variables
}
