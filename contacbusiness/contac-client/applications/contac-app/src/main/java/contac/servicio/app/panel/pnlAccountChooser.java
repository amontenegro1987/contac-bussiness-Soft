/*
 * pnl_AccountChooser.java
 *
 * Created on 07 de enero de 2009, 05:35 PM
 */
package contac.servicio.app.panel;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author  KjSoftware
 */
public class pnlAccountChooser extends pnl_Generico {

    //almacena el id de la cuenta seleccionada
    private String selectedNodo;
    //almacena la variable que define si algun nodo esta seleccionado
    private boolean isSelected = false,  isDialog = false;
    //variable panel
    private JPanel panel;
    //variable dialog
    private JDialog dialog;
    private String proyectoId = null;
    private boolean loadModel = false;
    private Map<String, Integer> account = new HashMap<String, Integer>();
    private Object[] accountCategorie = {"Todas las Cuentas",
        "Activos",
        "Pasivos",
        "Patrimonio",
        "Ingresos",
        "Gastos",
        "Cuentas de Orden",
        "Cuentas no Asignadas",
        "Cuentas Inactivas",
        "Todas las Cuentas Activas"
    };
    private int categoria = 0;

    /** Creates new form pnl_AccountChooser */
    public pnlAccountChooser(pnlCatalogo panel) {
        this.setMDI(panel.getMDI());
        initComponents();
        if (!this.isDialog) {
            this.jPanel1.setVisible(false);
          
        }
        this.initCatalogo();

    }

  
    //<editor-fold defaultstate="collapsed" desc="GETTERS & SETTERS">
    public boolean hasSelected() {
        return isSelected;
    }

    //obtengo el nodo seleccionado
    public String getSelectedAccount() {
        return selectedNodo;
    }

    //defino el id del proyecto que deseo, de manera que solo las cuentas de ese proyecto se listen
    public void setDefaultProyect(String proyectoId) {
        this.proyectoId = proyectoId;
    }

    //asigno el nodo seleccionado
    public void setSelectedAccount(String selectedNodo) {
        this.selectedNodo = selectedNodo;
    }

    //asigno el valor del emisor de cuentas
    public void setStateOffEmisor(boolean b) {
//        this.catalogo.setStateOffEmisor(b);
    }

    //restringo el modelo de carga de manera que solo las cuentas no asignadas se listen
    public void strictLoadModel(int i) {
        if (i == 0) {
            this.loadModel = false;
        }
    }

    //iniciamos el catalogo de cuentas
    private void initCatalogo() {
        

    }

//</editor-fold>



//</editor-fold>
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox2 = new javax.swing.JComboBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(this.accountCategorie));
        jComboBox2.setMaximumSize(new java.awt.Dimension(130, 32767));
        jComboBox2.setPreferredSize(new java.awt.Dimension(130, 20));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        jMenuItem2.setText("Caracteristicas/Editar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);
        jPopupMenu1.add(jSeparator2);

        jMenuItem1.setText("Enviar a presupuesto");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setLayout(new java.awt.BorderLayout(10, 0));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/resources/images/button_okx16.png"))); // NOI18N
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/resources/images/button_cancelx16.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Conexión", "Preferencias" }));
        jComboBox1.setMinimumSize(new java.awt.Dimension(62, 18));
        jToolBar1.add(jComboBox1);

        jTextField1.setPreferredSize(new java.awt.Dimension(130, 20));
        jToolBar1.add(jTextField1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/resources/images/1uparrowx16.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/contac/resources/images/1downarrowx16.png"))); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator1);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
   
}//GEN-LAST:event_jButton2ActionPerformed

private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
// TODO add your handling code here:
    
}//GEN-LAST:event_jComboBox2ActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
// TODO add your handling code here:
    //edito la cuenta seleccionada

}//GEN-LAST:event_jMenuItem2ActionPerformed

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
// TODO add your handling code here:
    //envio la cuenta al presupuesto, null: evento del mouse clicked
   
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
// TODO add your handling code here:
    
}//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
