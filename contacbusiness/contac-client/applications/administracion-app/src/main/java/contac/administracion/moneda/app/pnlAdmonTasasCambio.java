/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlAdmonTasasCambio.java
 *
 * Created on 02-16-2012, 11:23:27 PM
 */
package contac.administracion.moneda.app;

import contac.administracion.controller.TasasCambioController;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.models.comboBox.BancoComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.MonedaComboBoxModel;
import contac.commons.models.comboBox.TipoTasaCambioComboBoxModel;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.Banco;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.TasaCambio;
import contac.modelo.entity.TiposTasaCambio;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlAdmonTasasCambio extends JDialog {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlAdmonBancos.class);

    //Message Resource Bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/administracion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private TasasCambioController controller;

    /**
     * Creates new form pnlAdmonTasasCambio
     */
    public pnlAdmonTasasCambio(GenericFrame frame, boolean modal, TasaCambio entity) {

        //Call super constructor
        super(frame, modal);

        //Setting controller
        this.controller = new TasasCambioController();

        //Init controller
        this.controller.init();

        if (entity != null) {
            this.controller.setTasaCambio(entity);
            this.controller.initModificacion();
        }

        //Init components
        initComponents();

        //Init values
        initTasaCambioComponents();

        //Register listeners
        registerListeners();

        //Show panel
        showPanel();
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

        titleHeader = new org.jdesktop.swingx.JXHeader();
        frmAdmonBancos = new javax.swing.JPanel();
        tbAcciones = new javax.swing.JToolBar();
        btnNuevo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnGuardar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnRemover = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnLimpiar = new javax.swing.JButton();
        frmDatosGenerales = new javax.swing.JPanel();
        lblMonedaReferencia = new javax.swing.JLabel();
        lblTasaCambio = new javax.swing.JLabel();
        txtTasaCambio = new javax.swing.JTextField();
        lblTipoCambio = new javax.swing.JLabel();
        cmbTipoCambio = new javax.swing.JComboBox();
        lblBanco = new javax.swing.JLabel();
        cmbBanco = new javax.swing.JComboBox();
        dpFechaConversion = new org.jdesktop.swingx.JXDatePicker();
        lblFechaConversion = new javax.swing.JLabel();
        cmbMonedaReferencia = new javax.swing.JComboBox();
        cmbMonedaConversion = new javax.swing.JComboBox();
        lblMonedaConversion1 = new javax.swing.JLabel();
        chkActivaFacturacion = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        titleHeader.setBackground(new java.awt.Color(204, 204, 204));
        titleHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        titleHeader.setPreferredSize(new java.awt.Dimension(51, 40));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/administracion/app/mensajes/Mensajes_es"); // NOI18N
        titleHeader.setTitle(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO")); // NOI18N
        titleHeader.setTitleForeground(new java.awt.Color(255, 153, 0));
        getContentPane().add(titleHeader, java.awt.BorderLayout.NORTH);

        frmAdmonBancos.setLayout(new java.awt.BorderLayout());

        tbAcciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbAcciones.setMaximumSize(new java.awt.Dimension(124, 32));
        tbAcciones.setMinimumSize(new java.awt.Dimension(124, 32));

        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png")));
        btnNuevo.setToolTipText(bundle.getString("CONTAC.FORM.BTNNUEVO")); // NOI18N
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevo.setFocusable(false);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo.setMaximumSize(new java.awt.Dimension(40, 32));
        btnNuevo.setMinimumSize(new java.awt.Dimension(40, 32));
        btnNuevo.setPreferredSize(new java.awt.Dimension(40, 32));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        tbAcciones.add(btnNuevo);
        tbAcciones.add(jSeparator1);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/save.png")));
        btnGuardar.setToolTipText(bundle.getString("CONTAC.FORM.BTNGUARDAR")); // NOI18N
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnGuardar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnGuardar.setName(""); // NOI18N
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        tbAcciones.add(btnGuardar);
        tbAcciones.add(jSeparator2);

        btnRemover.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove.png")));
        btnRemover.setToolTipText(bundle.getString("CONTAC.FORM.BTNREMOVER")); // NOI18N
        btnRemover.setEnabled(false);
        btnRemover.setFocusable(false);
        btnRemover.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRemover.setMaximumSize(new java.awt.Dimension(40, 32));
        btnRemover.setMinimumSize(new java.awt.Dimension(40, 32));
        btnRemover.setName(""); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        tbAcciones.add(btnRemover);
        tbAcciones.add(jSeparator3);

        btnLimpiar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/reload.png")));
        btnLimpiar.setToolTipText(bundle.getString("CONTAC.FORM.BTNACTUALIZAR")); // NOI18N
        btnLimpiar.setFocusable(false);
        btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnLimpiar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnLimpiar.setName(""); // NOI18N
        btnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        tbAcciones.add(btnLimpiar);

        frmAdmonBancos.add(tbAcciones, java.awt.BorderLayout.PAGE_START);

        frmDatosGenerales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMonedaReferencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonedaReferencia.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.MONEDAREFERENCIA")); // NOI18N
        frmDatosGenerales.add(lblMonedaReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 137, 120, 20));

        lblTasaCambio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTasaCambio.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.TASACAMBIO")); // NOI18N
        frmDatosGenerales.add(lblTasaCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 79, 120, 20));

        txtTasaCambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTasaCambio.setToolTipText("");
        txtTasaCambio.setMinimumSize(new java.awt.Dimension(6, 25));
        txtTasaCambio.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtTasaCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 78, 121, 23));

        lblTipoCambio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoCambio.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.TIPOSCAMBIO")); // NOI18N
        frmDatosGenerales.add(lblTipoCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 120, 20));

        cmbTipoCambio.setModel(new TipoTasaCambioComboBoxModel(TiposTasaCambio.values()));
        cmbTipoCambio.setPreferredSize(new java.awt.Dimension(56, 23));
        frmDatosGenerales.add(cmbTipoCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 11, 123, -1));

        lblBanco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBanco.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.BANCO")); // NOI18N
        frmDatosGenerales.add(lblBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 108, 120, 20));

        cmbBanco.setModel(new BancoComboBoxModel(controller.getBancos()));
        cmbBanco.setPreferredSize(new java.awt.Dimension(56, 23));
        frmDatosGenerales.add(cmbBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 107, 232, -1));

        dpFechaConversion.setBackground(new java.awt.Color(255, 255, 255));
        frmDatosGenerales.add(dpFechaConversion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 44, 123, 23));

        lblFechaConversion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaConversion.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.FECHACONVERSION")); // NOI18N
        frmDatosGenerales.add(lblFechaConversion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, 120, 20));

        cmbMonedaReferencia.setModel(new MonedaComboBoxModel(controller.getMonedasReferencia()));
        cmbMonedaReferencia.setPreferredSize(new java.awt.Dimension(56, 23));
        frmDatosGenerales.add(cmbMonedaReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 136, 232, -1));

        cmbMonedaConversion.setModel(new MonedaComboBoxModel(controller.getMonedas()));
        cmbMonedaConversion.setPreferredSize(new java.awt.Dimension(56, 23));
        frmDatosGenerales.add(cmbMonedaConversion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 168, 232, -1));

        lblMonedaConversion1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonedaConversion1.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.MONEDACONVERSION")); // NOI18N
        frmDatosGenerales.add(lblMonedaConversion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 171, 120, 20));

        chkActivaFacturacion.setText(bundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.FACTURACION")); // NOI18N
        frmDatosGenerales.add(chkActivaFacturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, -1, -1));

        frmAdmonBancos.add(frmDatosGenerales, java.awt.BorderLayout.CENTER);

        getContentPane().add(frmAdmonBancos, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method is called from within the constructor to
     * show the JDialog form
     */
    private void showPanel() {
        setTitle(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO"));
        this.setLocation(400, 300);
        this.setSize(new Dimension(600, 400));
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to
     * initialize the components of the form
     */
    private void initTasaCambioComponents() {

        //Init format dates
        dpFechaConversion.setFormats("dd/MM/yyyy");

        //<Tipo de tasa de cambio>
        ListCellRenderer rendererTipoCambio = new ComboBoxEmptySelectionRenderer(cmbTipoCambio, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getTipoTasaCambio() != null) {
            TipoTasaCambioComboBoxModel tipoTasaCambioModel = (TipoTasaCambioComboBoxModel) cmbTipoCambio.getModel();
            cmbTipoCambio.setRenderer(rendererTipoCambio);
            cmbTipoCambio.setSelectedItem(tipoTasaCambioModel.searchSelectedItem(controller.getTipoTasaCambio().getValue()));
        } else {
            cmbTipoCambio.setRenderer(rendererTipoCambio);
            cmbTipoCambio.setSelectedIndex(-1);
        }
        //<Fecha de conversion>
        dpFechaConversion.setDate(controller.getFechaConversion());
        //<Tasa de cambio>
        txtTasaCambio.setText(TextUtil.formatCurrency(controller.getTasaConversion().doubleValue()));
        //<Banco>
        ListCellRenderer rendererBanco = new ComboBoxEmptySelectionRenderer(cmbBanco, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getBanco() != null) {
            BancoComboBoxModel bancoComboBoxModel = (BancoComboBoxModel) cmbBanco.getModel();
            cmbBanco.setRenderer(rendererBanco);
            cmbBanco.setSelectedItem(bancoComboBoxModel.searchSelectedItem(controller.getBanco().getId()));
        } else {
            cmbBanco.setRenderer(rendererBanco);
            cmbBanco.setSelectedIndex(-1);
        }
        //<Moneda referencia>
        ListCellRenderer rendererMonedaReferencia = new ComboBoxEmptySelectionRenderer(cmbMonedaReferencia, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getMonedaReferencia() != null) {
            MonedaComboBoxModel monedaComboBoxModel = (MonedaComboBoxModel) cmbMonedaReferencia.getModel();
            cmbMonedaReferencia.setRenderer(rendererMonedaReferencia);
            cmbMonedaReferencia.setSelectedItem(monedaComboBoxModel.searchSelectedItem(controller.getMonedaReferencia().getId()));
        } else {
            cmbMonedaReferencia.setRenderer(rendererMonedaReferencia);
            cmbMonedaReferencia.setSelectedIndex(-1);
        }
        //<Moneda conversion>
        ListCellRenderer rendererMonedaConversion = new ComboBoxEmptySelectionRenderer(cmbMonedaConversion, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getMonedaConversion() != null) {
            MonedaComboBoxModel monedaComboBoxModel = (MonedaComboBoxModel) cmbMonedaConversion.getModel();
            cmbMonedaConversion.setRenderer(rendererMonedaReferencia);
            cmbMonedaConversion.setSelectedItem(monedaComboBoxModel.searchSelectedItem(controller.getMonedaConversion().getId()));
        } else {
            cmbMonedaConversion.setRenderer(rendererMonedaReferencia);
            cmbMonedaConversion.setSelectedIndex(-1);
        }

        //Activa en facturacion
        chkActivaFacturacion.setSelected(controller.isActivaFacturacion());
    }

    /**
     * This method is called from within the constructor to
     * initialize the behavior of the components of the form
     */
    private void registerListeners() {

        //<Tipo de cambio>
        cmbTipoCambio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.VK_ENTER == e.getKeyChar()) {
                    dpFechaConversion.requestFocusInWindow();
                }
            }
        });

        //<Fecha conversion>
        dpFechaConversion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.VK_ENTER == e.getKeyChar()) {
                    txtTasaCambio.requestFocusInWindow();
                }
            }
        });

        //<Tasa de cambio>
        txtTasaCambio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    cmbBanco.requestFocusInWindow();
                }
            }
        });

        txtTasaCambio.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!txtTasaCambio.getText().equals("")) {
                    txtTasaCambio.setText(String.valueOf(TextUtil.parseCurrency(txtTasaCambio.getText())));
                    txtTasaCambio.selectAll();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!txtTasaCambio.getText().equals("")) {
                    txtTasaCambio.setText(TextUtil.formatCurrency(Double.parseDouble(txtTasaCambio.getText())));
                }
            }
        });

        //<Moneda referencia>
        cmbMonedaReferencia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    cmbMonedaConversion.requestFocusInWindow();
                }
            }
        });
    }

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        //Init properties values
        controller.init();

        //Init tasas cambio components
        initTasaCambioComponents();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {

            //Validar datos form
            validarDatosForm();

            //Setting properties
            controller.setTipoTasaCambio((TiposTasaCambio) ((TipoTasaCambioComboBoxModel) cmbTipoCambio.getModel()).
                    getSelectedItem().getObject());
            controller.setFechaConversion(dpFechaConversion.getDate());
            controller.setTasaConversion(new BigDecimal(TextUtil.parseCurrency(txtTasaCambio.getText())));
            controller.setBanco((Banco) ((BancoComboBoxModel) cmbBanco.getModel()).getSelectedItem().getObject());
            controller.setMonedaReferencia((Moneda) ((MonedaComboBoxModel) cmbMonedaReferencia.getModel()).getSelectedItem().getObject());
            controller.setMonedaConversion((Moneda) ((MonedaComboBoxModel) cmbMonedaConversion.getModel()).getSelectedItem().getObject());
            controller.setActivaFacturacion(chkActivaFacturacion.isSelected());

            if (!controller.is_edit()) {

                //Registrar tasa de cambio
                controller.registrarTasaCambio();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.TASACAMBIO.INGRESO.EXITOSO"));
            }

            if (controller.is_edit()) {

                //Modificar tasa de cambio
                controller.modificarTasaCambio();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.TASACAMBIO.MODIFICACION.EXITOSO"));
            }

            //Init tasas de cambio components
            initTasaCambioComponents();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed

        try {

            boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                    messageBundle.getString("CONTAC.FORM.MSG.TASACAMBIO.REMOVER.CONFIRMA"));

            if (confirmation) {

                //Remover tasa de cambio
                controller.removerTasaCambio();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.TASACAMBIO.REMOVER.EXITOSO"));

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed

    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * Valida datos obligatorios para registro y modificacion
     */
    private void validarDatosForm() throws Exception {

        //Tipo de cambio
        if (cmbTipoCambio.getSelectedIndex() == -1) {
            //Request focus
            cmbTipoCambio.requestFocus();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.TIPOCAMBIO.VALIDA"));
        }

        //Fecha de conversion
        if (dpFechaConversion.getDate() == null) {
            //Request focus
            dpFechaConversion.requestFocus();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.FECHACONVERSION.VALIDA"));
        }

        //Tasa de cambio
        if (txtTasaCambio.getText().equals("")) {
            //Request focus
            txtTasaCambio.requestFocus();
            txtTasaCambio.selectAll();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.TASACAMBIO.VALIDA"));
        }

        //Banco
        if (cmbBanco.getSelectedIndex() == -1) {
            //Request focus
            cmbBanco.requestFocus();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.BANCO.VALIDA"));
        }

        //Moneda referencia
        if (cmbMonedaReferencia.getSelectedIndex() == -1) {
            //Request focus
            cmbMonedaReferencia.requestFocus();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.MONEDAREFERENCIA.VALIDA"));
        }

        //Moneda conversion
        if (cmbMonedaConversion.getSelectedIndex() == -1) {
            //Request focus
            cmbMonedaConversion.requestFocus();
            //Throw erroe message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRATASASCAMBIO.MONEDACONVERSION.VALIDA"));
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRemover;
    private javax.swing.JCheckBox chkActivaFacturacion;
    private javax.swing.JComboBox cmbBanco;
    private javax.swing.JComboBox cmbMonedaConversion;
    private javax.swing.JComboBox cmbMonedaReferencia;
    private javax.swing.JComboBox cmbTipoCambio;
    private org.jdesktop.swingx.JXDatePicker dpFechaConversion;
    private javax.swing.JPanel frmAdmonBancos;
    private javax.swing.JPanel frmDatosGenerales;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JLabel lblBanco;
    private javax.swing.JLabel lblFechaConversion;
    private javax.swing.JLabel lblMonedaConversion1;
    private javax.swing.JLabel lblMonedaReferencia;
    private javax.swing.JLabel lblTasaCambio;
    private javax.swing.JLabel lblTipoCambio;
    private javax.swing.JToolBar tbAcciones;
    private org.jdesktop.swingx.JXHeader titleHeader;
    private javax.swing.JTextField txtTasaCambio;
    // End of variables declaration//GEN-END:variables
}
