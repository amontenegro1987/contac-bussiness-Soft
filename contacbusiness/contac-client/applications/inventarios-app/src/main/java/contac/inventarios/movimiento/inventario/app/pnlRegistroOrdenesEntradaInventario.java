/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlOrdenEntradaInventario.java
 *
 * Created on 12-17-2011, 10:52:00 PM
 */
package contac.inventarios.movimiento.inventario.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.*;
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenEntradaController;
import contac.modelo.entity.OrdenEntrada;
import contac.modelo.entity.OrdenMovimiento;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlRegistroOrdenesEntradaInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroOrdenesEntradaInventario.class);

    //Controller
    private OrdenEntradaController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());


    /**
     * Creates new form pnlOrdenEntradaInventario
     */
    public pnlRegistroOrdenesEntradaInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "OrdenEntradaAlmacen", "Orden de Entrada en Almac\u00e9n", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Controller
        controller = new OrdenEntradaController();

        try {

            //Init registros de datos
            controller.initRegistrosOrdenesEntradaInventario();

            //Init components
            initComponents();

            //Init values
            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }

    @Override
    public void initValues() {

        //Config table model para lavantamiento inventario fisico
        ordenEntradaBeanTableModel = new BeanTableModel<OrdenEntrada>(OrdenEntrada.class, OrdenMovimiento.class,
                controller.getOrdenesEntrada());
        ordenEntradaBeanTableModel.setModelEditable(false);
        tblOrdenesEntrada.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordenEntradaBeanTableModel.sortColumnNames();
        tblOrdenesEntrada.setEditable(false);
        tblOrdenesEntrada.setModel(ordenEntradaBeanTableModel);
        tblOrdenesEntrada.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel tableColumnModel = tblOrdenesEntrada.getColumnModel();
        String[] columnsRemove = new String[]{"Persona Entrega", "Tipo Entrada", "Fecha Solicitud", "Id", "Ctime", "Cuser", "Mtime", "Muser"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }

        //Adding cell rendering claass
        tableColumnModel.getColumn(0).setCellRenderer(new AlmacenFormatRenderer());
        tableColumnModel.getColumn(2).setCellRenderer(new EstadoFormatRenderer());
        tableColumnModel.getColumn(3).setCellRenderer(new FechaFormatRenderer());
        tableColumnModel.getColumn(4).setCellRenderer(new MonedaFormatRenderer());
        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableColumnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);

        //Ordering table columns
        tableColumnModel.moveColumn(6, 0); //No Movimiento
        tableColumnModel.moveColumn(3, 6); //Estado Movimiento

        //Setting prefered size
        tableColumnModel.getColumn(0).setPreferredWidth(15);
        tableColumnModel.getColumn(2).setPreferredWidth(250);

    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerAlmacenes = new org.jdesktop.swingx.JXHeader();
        pnlRegistroInventario = new javax.swing.JPanel();
        scrollOrdenesEntrada = new javax.swing.JScrollPane();
        tblOrdenesEntrada = new org.jdesktop.swingx.JXTable();
        tbRegistroInventarios = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        separatorOne = new javax.swing.JToolBar.Separator();
        btnEditar = new javax.swing.JButton();
        separatorTwo = new javax.swing.JToolBar.Separator();
        btnAnular = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        headerAlmacenes.setForeground(new java.awt.Color(255, 153, 0));
        headerAlmacenes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        headerAlmacenes.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        headerAlmacenes.setTitle(bundle.getString("CONTAC.FORM.ORDENENTRADA.TITLE")); // NOI18N
        headerAlmacenes.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(headerAlmacenes, java.awt.BorderLayout.NORTH);

        pnlRegistroInventario.setLayout(new java.awt.BorderLayout());

        tblOrdenesEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdenesEntradaMouseClicked(evt);
            }
        });
        scrollOrdenesEntrada.setViewportView(tblOrdenesEntrada);

        pnlRegistroInventario.add(scrollOrdenesEntrada, java.awt.BorderLayout.CENTER);

        tbRegistroInventarios.setBorder(null);
        tbRegistroInventarios.setMaximumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setMinimumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setPreferredSize(new java.awt.Dimension(124, 32));

        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png")));
        btnAgregar.setToolTipText(bundle.getString("CONTAC.FORM.BTNNUEVO")); // NOI18N
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregar.setFocusable(false);
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgregar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnAgregar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnAgregar.setPreferredSize(new java.awt.Dimension(70, 20));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        tbRegistroInventarios.add(btnAgregar);
        tbRegistroInventarios.add(separatorOne);

        btnEditar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/edit.png")));
        btnEditar.setToolTipText(bundle.getString("CONTAC.FORM.BTNEDITAR")); // NOI18N
        btnEditar.setFocusable(false);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnEditar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnEditar.setName(""); // NOI18N
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        tbRegistroInventarios.add(btnEditar);
        tbRegistroInventarios.add(separatorTwo);

        btnAnular.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png")));
        btnAnular.setToolTipText(bundle.getString("CONTAC.FORM.BTNANULAR")); // NOI18N
        btnAnular.setFocusable(false);
        btnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAnular.setMaximumSize(new java.awt.Dimension(40, 32));
        btnAnular.setMinimumSize(new java.awt.Dimension(40, 32));
        btnAnular.setName(""); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        tbRegistroInventarios.add(btnAnular);

        pnlRegistroInventario.add(tbRegistroInventarios, java.awt.BorderLayout.PAGE_START);

        add(pnlRegistroInventario, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrdenesEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdenesEntradaMouseClicked

        // Getting orden de entrada selected
        rowSelected = tblOrdenesEntrada.getSelectedRow();
        ordenEntradaSelected = (OrdenEntrada) ((BeanTableModel) tblOrdenesEntrada.getModel()).getRow(rowSelected);
        controller.setOrdenEntrada(ordenEntradaSelected);
    }//GEN-LAST:event_tblOrdenesEntradaMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        //Open formulario de administracion de compania
        getMDI().getStyle().addPanel("pnlOrdenSalidaInventario", "contac.inventarios.movimiento.inventario.app.pnlOrdenEntradaInventario");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (ordenEntradaSelected != null) {
            //Open formulario de ingreso de inventario fisico
            getMDI().getStyle().addPanel("pnlOrdenEntradaInventario", "contac.inventarios.movimiento.inventario.app.pnlOrdenEntradaInventario",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed

        try {

            if (ordenEntradaSelected != null) {

                //Setting orden de entrada selected
                controller.setOrdenEntrada(ordenEntradaSelected);

                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ADVERTENCIA"), MessageFormat.
                        format(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.CONFIRMA"),
                                new Object[]{ordenEntradaSelected.getNoMovimiento()}));

                if (confirmation) {
                    //Remover orden de entrada
                    controller.removerOrdenEntrada();

                    //Show confirmation message
                    JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.EXITOSO"),
                            messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.EXITOSO"));

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblOrdenesEntrada.getModel()).fireTableDataChanged();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnEditar;
    private org.jdesktop.swingx.JXHeader headerAlmacenes;
    private javax.swing.JPanel pnlRegistroInventario;
    private javax.swing.JScrollPane scrollOrdenesEntrada;
    private javax.swing.JToolBar.Separator separatorOne;
    private javax.swing.JToolBar.Separator separatorTwo;
    private javax.swing.JToolBar tbRegistroInventarios;
    private org.jdesktop.swingx.JXTable tblOrdenesEntrada;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<OrdenEntrada> ordenEntradaBeanTableModel;
    private OrdenEntrada ordenEntradaSelected;
    private int rowSelected;
}