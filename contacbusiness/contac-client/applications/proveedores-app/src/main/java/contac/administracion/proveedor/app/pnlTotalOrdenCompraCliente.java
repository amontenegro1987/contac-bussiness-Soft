/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlTotalOrdenCompraCliente.java
 *
 * Created on 29-07-2013, 09:18:32 PM
 */
package contac.administracion.proveedor.app;

import contac.administracion.controller.OrdenCompraController;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.internationalization.LanguageLocale;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * @author amortiz
 */
public class pnlTotalOrdenCompraCliente extends JDialog {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlTotalOrdenCompraCliente.class);

    //Controller
    private OrdenCompraController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlTotalOrdenCompraCliente
     */
    public pnlTotalOrdenCompraCliente(GenericFrame frame, OrdenCompraController controller, boolean modal) {

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
            this.setTitle(messageBundle.getString("CONTAC.FORM.TOTALORDENCOMPRAT.TITTLE"));
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

        //Iniciar valores de calculo Orden de Compra
        txtSubtotal.setText(TextUtil.formatCurrency(controller.getMontoBruto().doubleValue()));
        txtSubtotalAntesImpuesto.setText(TextUtil.formatCurrency(controller.getMontoAntesImpuesto().doubleValue()));
        txtDescuento.setText(TextUtil.formatCurrency(controller.getDescuento().doubleValue()));
        txtRetencion_Fuente.setText(TextUtil.formatCurrency(controller.getMontoRetFuente().doubleValue()));
        txtRetencion_munc.setText(TextUtil.formatCurrency(controller.getMontoRetMunicipal().doubleValue()));
        txtIVA.setText(TextUtil.formatCurrency(controller.getIva().doubleValue()));
        txtTotalOrdencompra.setText(TextUtil.formatCurrency(controller.getMontoTotal().doubleValue()));

        chkRetencionFuente.setSelected(controller.isRetFuente());
        chkRetencionMunicipal.setSelected(controller.isRetMunicipal());

        if (controller.isExonerada()) {
            rbExonerado.setSelected(true);
            rbCalcularIVA.setSelected(false);
        } else {
            rbExonerado.setSelected(false);
            rbCalcularIVA.setSelected(true);
        }
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
                            throw new Exception(messageBundle.getString("CONTAC.FORM.PROFORMA.VALIDA.DESCUENTO"));
                        }

                        //Setting descuento global
                        controller.setPorcDescuento(new BigDecimal(porcDescuento));

                        //Recalcular Orden de Compra
                        controller.calcularTotalOrdenCompra();

                        //Init components values
                        initComponentsValues();

                        //Setting focus txtTotalOrdenCompra
                        txtTotalOrdencompra.requestFocusInWindow();

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

        chkRetencionFuente.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JCheckBox checkBox = (JCheckBox) changeEvent.getSource();

                try {

                    if (checkBox.isSelected()) {
                        //Setting retencion fuente true
                        controller.setRetFuente(true);
                    } else {
                        //Setting retencion fuente false
                        controller.setRetFuente(false);
                    }

                    //Recalcular Proforma
                    controller.calcularTotalOrdenCompra();

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

        chkRetencionMunicipal.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JCheckBox checkBox = (JCheckBox) changeEvent.getSource();

                try {

                    if (checkBox.isSelected()) {
                        //Setting retencion fuente true
                        controller.setRetMunicipal(true);
                    } else {
                        //Setting retencion fuente false
                        controller.setRetMunicipal(false);
                    }

                    //Recalcular Orden de Compra
                    controller.calcularTotalOrdenCompra();

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

        rbCalcularIVA.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JRadioButton radioButton = (JRadioButton) changeEvent.getSource();

                try {
                    if (radioButton.isSelected()) {
                        //Setting exonerada false
                        controller.setExonerada(false);

                        //Setting checkBox exonerado false
                        rbCalcularIVA.setSelected(true);
                        rbExonerado.setSelected(false);

                        //Recalcular Orden de Compra
                        controller.calcularTotalOrdenCompra();

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

        rbExonerado.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JRadioButton radioButton = (JRadioButton) changeEvent.getSource();

                try {

                    if (radioButton.isSelected()) {
                        //Setting exonerada true
                        controller.setExonerada(true);

                        //Setting checkBox calcular IVA false
                        rbCalcularIVA.setSelected(false);
                        rbExonerado.setSelected(true);

                        //Recalcular Proforma
                        controller.calcularTotalOrdenCompra();

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
        totalOrdenCompraFrm = new JPanel();
        lblSubtotal = new JLabel();
        txtSubtotal = new JTextField();
        lblSubtotal1 = new JLabel();
        txtDescuento = new JTextField();
        lblRetencionFuente = new JLabel();
        txtRetencion_Fuente = new JTextField();
        lblRetencionMunicipal = new JLabel();
        txtRetencion_munc = new JTextField();
        chkRetencionFuente = new JCheckBox();
        lblIVA = new JLabel();
        txtIVA = new JTextField();
        lblTotal = new JLabel();
        txtTotalOrdencompra = new JTextField();
        jXRadioGroup1 = new org.jdesktop.swingx.JXRadioGroup();
        rbCalcularIVA = new JRadioButton();
        rbExonerado = new JRadioButton();
        chkRetencionMunicipal = new JCheckBox();
        txtSubtotalAntesImpuesto = new JTextField();
        lblSubtotal2 = new JLabel();

        header.setForeground(new Color(255, 153, 0));
        header.setPreferredSize(new Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        ResourceBundle bundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRAT.TITTLE")); // NOI18N
        header.setTitleForeground(new Color(255, 153, 0));
        getContentPane().add(header, BorderLayout.PAGE_START);

        lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubtotal.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRASUBTOTAL.SUBTOTAL")); // NOI18N

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(JTextField.RIGHT);

        lblSubtotal1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubtotal1.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRADESC.DESCUENTO")); // NOI18N

        txtDescuento.setHorizontalAlignment(JTextField.RIGHT);

        lblRetencionFuente.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRetencionFuente.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRAIMP.IMPUESTO_1_IR")); // NOI18N

        txtRetencion_Fuente.setEditable(false);
        txtRetencion_Fuente.setHorizontalAlignment(JTextField.RIGHT);

        lblRetencionMunicipal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRetencionMunicipal.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRAIMPMUN.IMPUESTO_1_MUNC")); // NOI18N

        txtRetencion_munc.setEditable(false);
        txtRetencion_munc.setHorizontalAlignment(JTextField.RIGHT);

        chkRetencionFuente.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRACALC.CALCULAR")); // NOI18N

        lblIVA.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIVA.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRAII.IMPUESTO_IVA")); // NOI18N

        txtIVA.setEditable(false);
        txtIVA.setHorizontalAlignment(JTextField.RIGHT);

        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRATOTAL.TOTAL")); // NOI18N

        txtTotalOrdencompra.setEditable(false);
        txtTotalOrdencompra.setHorizontalAlignment(JTextField.RIGHT);

        GroupLayout jXRadioGroup1Layout = new GroupLayout(jXRadioGroup1);
        jXRadioGroup1.setLayout(jXRadioGroup1Layout);
        jXRadioGroup1Layout.setHorizontalGroup(
                jXRadioGroup1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 184, Short.MAX_VALUE)
        );
        jXRadioGroup1Layout.setVerticalGroup(
                jXRadioGroup1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 30, Short.MAX_VALUE)
        );

        rbCalcularIVA.setSelected(true);
        rbCalcularIVA.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRACALCULARIVA.CALCULAR_IVA")); // NOI18N

        rbExonerado.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRAEXO.EXONERADO")); // NOI18N

        chkRetencionMunicipal.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRACALC.CALCULAR")); // NOI18N

        txtSubtotalAntesImpuesto.setEditable(false);
        txtSubtotalAntesImpuesto.setHorizontalAlignment(JTextField.RIGHT);

        lblSubtotal2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubtotal2.setText(bundle.getString("CONTAC.FORM.TOTALORDENCOMPRA.SUBTOTAL_ANTES_IMPUESTO")); // NOI18N

        GroupLayout totalFacturaFrmLayout = new GroupLayout(totalOrdenCompraFrm);
        totalOrdenCompraFrm.setLayout(totalFacturaFrmLayout);
        totalFacturaFrmLayout.setHorizontalGroup(
                totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, totalFacturaFrmLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblSubtotal2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblRetencionFuente, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblSubtotal, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblSubtotal1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblTotal, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblRetencionMunicipal, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                        .addComponent(lblIVA, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSubtotalAntesImpuesto, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addComponent(txtTotalOrdencompra, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtSubtotal, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                                .addComponent(txtDescuento, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                        .addComponent(txtIVA, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addComponent(txtRetencion_munc, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                        .addComponent(txtRetencion_Fuente, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(chkRetencionFuente)
                                                        .addComponent(chkRetencionMunicipal))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jXRadioGroup1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                                .addComponent(rbCalcularIVA)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbExonerado)))
                                .addGap(21, 21, 21))
        );
        totalFacturaFrmLayout.setVerticalGroup(
                totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSubtotal, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSubtotal, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDescuento, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSubtotal1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(GroupLayout.Alignment.LEADING, totalFacturaFrmLayout.createSequentialGroup()
                                                .addGap(58, 58, 58)
                                                .addComponent(jXRadioGroup1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(totalFacturaFrmLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtSubtotalAntesImpuesto, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblSubtotal2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtRetencion_Fuente, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(chkRetencionFuente)
                                                        .addComponent(lblRetencionFuente, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtRetencion_munc, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblRetencionMunicipal, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(chkRetencionMunicipal))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtIVA, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                        .addComponent(rbCalcularIVA)
                                        .addComponent(rbExonerado)
                                        .addComponent(lblIVA, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(totalFacturaFrmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTotalOrdencompra, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36))
        );

        getContentPane().add(totalOrdenCompraFrm, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBox chkRetencionFuente;
    private JCheckBox chkRetencionMunicipal;
    private org.jdesktop.swingx.JXHeader header;
    private org.jdesktop.swingx.JXRadioGroup jXRadioGroup1;
    private JLabel lblIVA;
    private JLabel lblRetencionFuente;
    private JLabel lblRetencionMunicipal;
    private JLabel lblSubtotal;
    private JLabel lblSubtotal1;
    private JLabel lblSubtotal2;
    private JLabel lblTotal;
    private JRadioButton rbCalcularIVA;
    private JRadioButton rbExonerado;
    private JPanel totalOrdenCompraFrm;
    private JTextField txtDescuento;
    private JTextField txtIVA;
    private JTextField txtRetencion_Fuente;
    private JTextField txtRetencion_munc;
    private JTextField txtSubtotal;
    private JTextField txtSubtotalAntesImpuesto;
    private JTextField txtTotalOrdencompra;
    // End of variables declaration//GEN-END:variables
}
