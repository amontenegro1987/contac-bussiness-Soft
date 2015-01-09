/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


/*
 * pnlAplicarAjusteLevantamientoInventarioFisico.java
 *
 * Created on 11-08-2012, 12:34:33 PM
 */
package contac.inventarios.movimiento.inventario.app;

import contac.commons.app.BaseController;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.form.render.EstatusCellRenderer;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.TipoActualizacionFisicoComboBoxModel;
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenLevantamientoController;
import contac.modelo.entity.Articulo;
import contac.modelo.entity.ArticuloLevantamientoFisico;
import contac.modelo.entity.TipoActualizacionFisico;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlAplicarAjusteLevantamientoInventarioFisico extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlAplicarAjusteLevantamientoInventarioFisico.class);

    //Controller
    private OrdenLevantamientoController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlAplicarAjusteLevantamientoInventarioFisico
     */
    public pnlAplicarAjusteLevantamientoInventarioFisico(GenericFrame frame, BaseController controller) {

        //Call super constructor
        super(frame, "ordenLevantamientoFisico", "Orden de Levantamiento Físico", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init Controller
        this.controller = (OrdenLevantamientoController) controller;

        //Controller init properties
        this.controller.initModificacion();

        //Init components
        initComponents();

        //Editar registros
        controller.set_edit(true);

        //Init values
        initValues();

        //Register listeners
        //registerListeners();
    }

    @Override
    public void initValues() {
        //************************************************************
        //Init data values components
        //************************************************************
        txtNoEntrada.setText(String.valueOf(controller.getOrdenLevantamiento().getNoMovimiento()));
        txtAlmacen.setText(controller.getOrdenLevantamiento().getAlmacen().getDescripcion());
        txtDescripcion.setText(controller.getOrdenLevantamiento().getDescripcion());
        dtpFechaAlta.setFormats("dd/MM/yyyy");
        dtpFechaAlta.setDate(controller.getOrdenLevantamiento().getFechaAlta());
        txtMontoTotalAjuste.setText(TextUtil.formatCurrency(controller.getOrdenLevantamiento().getMontoTotalAjuste().doubleValue()));

        //Seleccione tipo actualizacion fisico
        ListCellRenderer rendererTipoActualizacionFisico = new ComboBoxEmptySelectionRenderer(cmbTipoActualizacion, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getTipoActualizacionFisico() != null) {
            TipoActualizacionFisicoComboBoxModel tipoActualizacionModel = (TipoActualizacionFisicoComboBoxModel) cmbTipoActualizacion.getModel();
            cmbTipoActualizacion.setRenderer(rendererTipoActualizacionFisico);
            cmbTipoActualizacion.setSelectedItem(tipoActualizacionModel.searchSelectedItem(controller.getTipoActualizacionFisico().getValue()));
        } else {
            cmbTipoActualizacion.setRenderer(rendererTipoActualizacionFisico);
            cmbTipoActualizacion.setSelectedIndex(-1);
        }

        //************************************************************
        //Init articulo bean table model
        //************************************************************
        articuloBeanTableModel = new BeanTableModel<ArticuloLevantamientoFisico>(ArticuloLevantamientoFisico.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulos.setModel(articuloBeanTableModel);
        tblArticulos.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulos.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Orden Levantamiento Fisico", "Movimiento Inventario", "Codigo Fabricante",
                "Renglon", "Cantidad Anterior", "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update", "No Movimiento"};

        for (String columnLabel : articuloColumnRemove) {
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnLabel)));
        }

        //Ordering table columns
        columnModel.moveColumn(3, 0); //Codigo producto
        columnModel.moveColumn(8, 1); //Nombre producto
        //columnModel.moveColumn(5, 2); //Codigo fabricante
        columnModel.moveColumn(9, 5); //Unidad de medida
        columnModel.moveColumn(5, 4); //Cantidad existencia*/

        //Setting prefered sized
        columnModel.getColumn(0).setPreferredWidth(45);
        columnModel.getColumn(1).setPreferredWidth(130);

        //Adding cell renderer class
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(5).setCellRenderer(centerAlignment);

        //Adding cell status class
        EstatusCellRenderer estatusCellRenderer = new EstatusCellRenderer();
        estatusCellRenderer.setHorizontalAlignment(JLabel.RIGHT);

        columnModel.getColumn(5).setCellRenderer(estatusCellRenderer);

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(6).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(7).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(9).setCellRenderer(decimalFormatRenderer);

        //Ir ultimo registro tabla
        tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));
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

        header = new org.jdesktop.swingx.JXHeader();
        pnlAplicarAjuste = new javax.swing.JPanel();
        lblNoEntrada = new javax.swing.JLabel();
        txtNoEntrada = new javax.swing.JTextField();
        lblAlmacen = new javax.swing.JLabel();
        txtAlmacen = new javax.swing.JTextField();
        lblFechaAlta = new javax.swing.JLabel();
        dtpFechaAlta = new org.jdesktop.swingx.JXDatePicker();
        txtDescripcion = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulos = new org.jdesktop.swingx.JXTable();
        btnAplicar = new javax.swing.JButton();
        lblAlmacen1 = new javax.swing.JLabel();
        cmbTipoActualizacion = new javax.swing.JComboBox();
        btnCalcularAjuste = new javax.swing.JButton();
        txtMontoTotalAjuste = new javax.swing.JTextField();
        lblAlmacen2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.APLICARAJUSTELEVANTAMIENTOINVENTARIOFISICO.TITLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        lblNoEntrada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoEntrada.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.NOENTRADA")); // NOI18N

        txtNoEntrada.setEditable(false);
        txtNoEntrada.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNoEntrada.setToolTipText("");
        txtNoEntrada.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNoEntrada.setPreferredSize(new java.awt.Dimension(59, 30));

        lblAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN")); // NOI18N

        txtAlmacen.setEditable(false);
        txtAlmacen.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtAlmacen.setToolTipText("");
        txtAlmacen.setMinimumSize(new java.awt.Dimension(6, 25));
        txtAlmacen.setPreferredSize(new java.awt.Dimension(59, 30));

        lblFechaAlta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaAlta.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA")); // NOI18N

        dtpFechaAlta.setEditable(false);

        txtDescripcion.setEditable(false);
        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcion.setToolTipText("");
        txtDescripcion.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescripcion.setPreferredSize(new java.awt.Dimension(59, 30));

        lblDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescripcion.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION")); // NOI18N

        jScrollPane1.setViewportView(tblArticulos);

        btnAplicar.setText(bundle.getString("CONTAC.FORM.BTNAPLICAR")); // NOI18N
        btnAplicar.setActionCommand(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarActionPerformed(evt);
            }
        });

        lblAlmacen1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen1.setText(bundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.TIPOAJUSTE")); // NOI18N

        cmbTipoActualizacion.setModel(new TipoActualizacionFisicoComboBoxModel(TipoActualizacionFisico.values()));

        btnCalcularAjuste.setText(bundle.getString("CONTAC.FORM.BTNCALCULARAJUSTE")); // NOI18N
        btnCalcularAjuste.setActionCommand(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnCalcularAjuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularAjusteActionPerformed(evt);
            }
        });

        txtMontoTotalAjuste.setEditable(false);
        txtMontoTotalAjuste.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoTotalAjuste.setToolTipText("");
        txtMontoTotalAjuste.setMinimumSize(new java.awt.Dimension(6, 25));
        txtMontoTotalAjuste.setPreferredSize(new java.awt.Dimension(59, 30));

        lblAlmacen2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen2.setText(bundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.TOTALAJUSTE")); // NOI18N

        javax.swing.GroupLayout pnlAplicarAjusteLayout = new javax.swing.GroupLayout(pnlAplicarAjuste);
        pnlAplicarAjuste.setLayout(pnlAplicarAjusteLayout);
        pnlAplicarAjusteLayout.setHorizontalGroup(
            pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAplicarAjusteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAplicarAjusteLayout.createSequentialGroup()
                        .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlAplicarAjusteLayout.createSequentialGroup()
                                .addComponent(txtNoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtpFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlAplicarAjusteLayout.createSequentialGroup()
                            .addComponent(lblAlmacen1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbTipoActualizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnCalcularAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAlmacen2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMontoTotalAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlAplicarAjusteLayout.setVerticalGroup(
            pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAplicarAjusteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAplicarAjusteLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblNoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dtpFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAplicarAjusteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlmacen1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoActualizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAplicar)
                    .addComponent(btnCalcularAjuste)
                    .addComponent(txtMontoTotalAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAlmacen2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        add(pnlAplicarAjuste, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularAjusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularAjusteActionPerformed
        try {

            //Calcular ajuste levantamiento inventario fisico
            controller.calcularAjusteLevantamientoInventario();

            //Actualizar monto total ajuste
            txtMontoTotalAjuste.setText(TextUtil.formatCurrency((controller.getOrdenLevantamiento().getMontoTotalAjuste()).
                    doubleValue()));

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblArticulos.getModel()).fireTableDataChanged();

            //Ir al ultimo registro de la tabla
            tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));

            //Show confirmation message
            JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.PROCESAMIENTO.EXITOSO"),
                    messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.CALCULOAJUSTE.EXITOSO"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnCalcularAjusteActionPerformed

    private void btnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarActionPerformed
        try {

            //Setting tipo de actualizacion fisico
            controller.setTipoActualizacionFisico((TipoActualizacionFisico) ((TipoActualizacionFisicoComboBoxModel)
                    cmbTipoActualizacion.getModel()).getSelectedItem().getObject());

            //Aplicar ajuste levantamiento inventario fisico
            controller.aplicarAjusteLevantamientoInventario();

            //Show confirmation message
            JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.PROCESAMIENTO.EXITOSO"),
                    messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.APLICARAJUSTE.EXITOSO"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnAplicarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnCalcularAjuste;
    private javax.swing.JComboBox cmbTipoActualizacion;
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblAlmacen1;
    private javax.swing.JLabel lblAlmacen2;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblNoEntrada;
    private javax.swing.JPanel pnlAplicarAjuste;
    private org.jdesktop.swingx.JXTable tblArticulos;
    private javax.swing.JTextField txtAlmacen;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtMontoTotalAjuste;
    private javax.swing.JTextField txtNoEntrada;
    // End of variables declaration//GEN-END:variables
    private BeanTableModel<ArticuloLevantamientoFisico> articuloBeanTableModel;
}
