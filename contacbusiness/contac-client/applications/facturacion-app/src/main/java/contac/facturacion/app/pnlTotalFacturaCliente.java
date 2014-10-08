/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlTotalFacturaCliente.java
 *
 * Created on 10-15-2012, 10:52:32 AM
 */
package contac.facturacion.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.facturacion.controller.FacturaClienteController;
import contac.internationalization.LanguageLocale;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * @author emortiz
 */
public class pnlTotalFacturaCliente extends JDialog {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlTotalFacturaCliente.class);

    //Controller
    private FacturaClienteController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlTotalFacturaCliente
     */
    public pnlTotalFacturaCliente(GenericFrame frame, FacturaClienteController controller, boolean modal) {

        //Constructor
        super(frame, modal);

        try {

            //Controller
            this.controller = controller;

            //Init components
            initComponents();

            //Init components values
            initComponentsValues();

            //Register listeners
            registerListeners();

            //Set visible
            this.setTitle(messageBundle.getString("CONTAC.FORM.TOTALFACTURACION.TITTLE"));
            this.setLocation(400, 300);
            this.setSize(new Dimension(550, 300));
            this.setVisible(true);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                    e.getMessage());
        }
    }

    //Init components values
    private void initComponentsValues() throws Exception {

        //Iniciar valores de calculo factura
        txtSubtotal.setText(TextUtil.formatCurrency(controller.getMontoBruto().doubleValue()));
        txtSubtotalAntesImpuesto.setText(TextUtil.formatCurrency(controller.getMontoAntesImpuesto().doubleValue()));
        txtDescuento.setText(TextUtil.formatCurrency(controller.getDescuento().doubleValue()));
        txtRetencion_Fuente.setText(TextUtil.formatCurrency(controller.getMontoRetFuente().doubleValue()));
        txtRetencion_munc.setText(TextUtil.formatCurrency(controller.getMontoRetMunicipal().doubleValue()));
        txtIVA.setText(TextUtil.formatCurrency(controller.getIva().doubleValue()));
        txtTotalFactura.setText(TextUtil.formatCurrency(controller.getMontoTotal().doubleValue()));

        chkRetencionFuente.setSelected(controller.isRetFuente());
        chkRetencionMunicipal.setSelected(controller.isRetMunicipal());

        if (controller.isExonerada()) {
            rbExonerado.setSelected(true);
            rbCalcularIVA.setSelected(false);
        } else {
            rbExonerado.setSelected(false);
            rbCalcularIVA.setSelected(true);
        }

        //Repaint this UI Form
        this.repaint();
    }

    //Register listeners
    private void registerListeners() {

        txtDescuento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    try {
                        //Obteniendo el descuento
                        double porcDescuento = Double.parseDouble(txtDescuento.getText());

                        //Validar porcentaje de descuento
                        if (porcDescuento > 100) {
                            //Show Error message
                            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.DESCUENTO"));
                        }

                        //Setting descuento global
                        controller.setPorcDescuento(new BigDecimal(porcDescuento));

                        //Recalcular factura
                        controller.calcularTotalFactura();

                        //Init components values
                        initComponentsValues();

                        //Setting focus txtTotalFactura
                        txtTotalFactura.requestFocusInWindow();

                    } catch (NumberFormatException ex) {
                        logger.error(ex.getMessage(), ex);
                        //Show confirmation message
                        JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                                ex.getMessage());
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                        //Show confirmation message
                        JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                                ex.getMessage());
                    }
                }
            }
        });

        txtDescuento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtDescuento.selectAll();
            }
        });

        txtCodeDescuento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    try {
                        //Getting password Descuento
                        char[] contraseniaDescuento = txtCodeDescuento.getPassword();

                        if (String.valueOf(contraseniaDescuento) == "") {
                            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.DESCUENTOPASSWORD"));
                        } else {
                            //Buscar contrasenia del Password por Usuario
                            boolean valid = controller.buscarContraseniaDescuento(String.valueOf(contraseniaDescuento));

                            if (valid) {
                                txtDescuento.setEditable(true);
                            }
                        }

                    } catch (NumberFormatException ex) {
                        logger.error(ex.getMessage(), ex);
                        //Show confirmation message
                        JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                                ex.getMessage());
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                        //Show confirmation message
                        JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                                ex.getMessage());
                    }
                }
            }
        });

        txtDescuento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtDescuento.selectAll();
            }
        });

        chkRetencionFuente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox checkBox = (JCheckBox) event.getSource();

                try {

                    if (checkBox.isSelected()) {
                        //Setting retencion fuente true
                        controller.setRetFuente(true);
                    } else {
                        //Setting retencion fuente false
                        controller.setRetFuente(false);
                    }

                    //Recalcular factura
                    controller.calcularTotalFactura();

                    //Init components values
                    initComponentsValues();

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    //Show confirmation message
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                            e.getMessage());
                }
            }
        });

        chkRetencionMunicipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox checkBox = (JCheckBox) event.getSource();

                try {

                    if (checkBox.isSelected()) {
                        //Setting retencion fuente true
                        controller.setRetMunicipal(true);
                    } else {
                        //Setting retencion fuente false
                        controller.setRetMunicipal(false);
                    }

                    //Recalcular factura
                    controller.calcularTotalFactura();

                    //Init components values
                    initComponentsValues();

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    //Show confirmation message
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                            e.getMessage());
                }
            }
        });

        rbCalcularIVA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JRadioButton radioButton = (JRadioButton) event.getSource();

                try {

                    if (radioButton.isSelected()) {
                        //Setting exonerada false
                        controller.setExonerada(false);

                        //Setting checkBox exonerado false
                        rbCalcularIVA.setSelected(true);
                        rbExonerado.setSelected(false);

                        //Recalcular factura
                        controller.calcularTotalFactura();

                        //Init components values
                        initComponentsValues();
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    //Show confirmation message
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                            e.getMessage());
                }

            }
        });

        rbExonerado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JRadioButton radioButton = (JRadioButton) event.getSource();

                try {

                    if (radioButton.isSelected()) {
                        //Setting exonerada true
                        controller.setExonerada(true);

                        //Setting checkBox calcular IVA false
                        rbCalcularIVA.setSelected(false);
                        rbExonerado.setSelected(true);

                        //Recalcular factura
                        controller.calcularTotalFactura();

                        //Init components values
                        initComponentsValues();
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    //Show confirmation message
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                            e.getMessage());
                }
            }
        });

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
        totalFacturaFrm = new javax.swing.JPanel();
        lblSubtotal = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        lblDescuento = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtCodeDescuento = new javax.swing.JPasswordField();
        lblRetencionFuente = new javax.swing.JLabel();
        txtRetencion_Fuente = new javax.swing.JTextField();
        lblRetencionMunicipal = new javax.swing.JLabel();
        txtRetencion_munc = new javax.swing.JTextField();
        chkRetencionFuente = new javax.swing.JCheckBox();
        lblIVA = new javax.swing.JLabel();
        txtIVA = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        txtTotalFactura = new javax.swing.JTextField();
        jXRadioGroup1 = new org.jdesktop.swingx.JXRadioGroup();
        rbCalcularIVA = new javax.swing.JRadioButton();
        rbExonerado = new javax.swing.JRadioButton();
        chkRetencionMunicipal = new javax.swing.JCheckBox();
        txtSubtotalAntesImpuesto = new javax.swing.JTextField();
        lblSubtotal2 = new javax.swing.JLabel();

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.TOTALFACTURACION.TITTLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        getContentPane().add(header, java.awt.BorderLayout.PAGE_START);

        lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSubtotal.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.SUBTOTAL")); // NOI18N

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblDescuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescuento.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.DESCUENTO")); // NOI18N

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setEditable(false);

        txtCodeDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblRetencionFuente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRetencionFuente.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.IMPUESTO_1_IR")); // NOI18N

        txtRetencion_Fuente.setEditable(false);
        txtRetencion_Fuente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblRetencionMunicipal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRetencionMunicipal.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.IMPUESTO_1_MUNC")); // NOI18N

        txtRetencion_munc.setEditable(false);
        txtRetencion_munc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        chkRetencionFuente.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.CALCULAR")); // NOI18N

        lblIVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIVA.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.IMPUESTO_IVA")); // NOI18N

        txtIVA.setEditable(false);
        txtIVA.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.TOTAL")); // NOI18N

        txtTotalFactura.setEditable(false);
        txtTotalFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jXRadioGroup1Layout = new javax.swing.GroupLayout(jXRadioGroup1);
        jXRadioGroup1.setLayout(jXRadioGroup1Layout);
        jXRadioGroup1Layout.setHorizontalGroup(
                jXRadioGroup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 184, Short.MAX_VALUE)
        );
        jXRadioGroup1Layout.setVerticalGroup(
                jXRadioGroup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 30, Short.MAX_VALUE)
        );

        rbCalcularIVA.setSelected(true);
        rbCalcularIVA.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.CALCULAR_IVA")); // NOI18N

        rbExonerado.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.EXONERADO")); // NOI18N

        chkRetencionMunicipal.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.CALCULAR")); // NOI18N

        txtSubtotalAntesImpuesto.setEditable(false);
        txtSubtotalAntesImpuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblSubtotal2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSubtotal2.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.SUBTOTAL_ANTES_IMPUESTO")); // NOI18N

        javax.swing.GroupLayout totalFacturaFrmLayout = new javax.swing.GroupLayout(totalFacturaFrm);
        totalFacturaFrm.setLayout(totalFacturaFrmLayout);
        totalFacturaFrmLayout.setHorizontalGroup(
                totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalFacturaFrmLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblSubtotal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblRetencionFuente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblDescuento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblRetencionMunicipal, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblIVA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSubtotalAntesImpuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addComponent(txtTotalFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                                .addComponent(txtDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                        .addComponent(txtIVA, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addComponent(txtRetencion_munc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addComponent(txtRetencion_Fuente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtCodeDescuento)
                                                        .addComponent(chkRetencionFuente)
                                                        .addComponent(chkRetencionMunicipal))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jXRadioGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                                .addComponent(rbCalcularIVA)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbExonerado)))
                                .addGap(21, 21, 21))
        );
        totalFacturaFrmLayout.setVerticalGroup(
                totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCodeDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, totalFacturaFrmLayout.createSequentialGroup()
                                                .addGap(58, 58, 58)
                                                .addComponent(jXRadioGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtSubtotalAntesImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblSubtotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtRetencion_Fuente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(chkRetencionFuente)
                                                        .addComponent(lblRetencionFuente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtRetencion_munc, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblRetencionMunicipal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(chkRetencionMunicipal))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtIVA, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                        .addComponent(rbCalcularIVA)
                                        .addComponent(rbExonerado)
                                        .addComponent(lblIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTotalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36))
        );

        getContentPane().add(totalFacturaFrm, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkRetencionFuente;
    private javax.swing.JCheckBox chkRetencionMunicipal;
    private org.jdesktop.swingx.JXHeader header;
    private org.jdesktop.swingx.JXRadioGroup jXRadioGroup1;
    private javax.swing.JLabel lblIVA;
    private javax.swing.JLabel lblRetencionFuente;
    private javax.swing.JLabel lblRetencionMunicipal;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblSubtotal2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JRadioButton rbCalcularIVA;
    private javax.swing.JRadioButton rbExonerado;
    private javax.swing.JPanel totalFacturaFrm;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JPasswordField txtCodeDescuento;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtRetencion_Fuente;
    private javax.swing.JTextField txtRetencion_munc;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtSubtotalAntesImpuesto;
    private javax.swing.JTextField txtTotalFactura;
    // End of variables declaration//GEN-END:variables
}
