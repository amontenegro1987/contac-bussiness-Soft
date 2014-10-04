package contac.facturacion.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.models.comboBox.TipoPagoComboBoxModel;
import contac.commons.models.comboBox.TipoTarjetaComboBoxModel;
import contac.facturacion.controller.FacturaClienteController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.TiposPago;
import contac.modelo.entity.TiposTarjeta;
import contac.text.TextUtil;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * Copyright 2013 Contac Business Software. All rights reserved.
 * User: EMONTENEGRO
 * Date: 11/8/13
 * Time: 12:38 PM
 */
public class pnlPagoFactura extends JDialog {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlPagoFactura.class);

    //Controller
    private FacturaClienteController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    private JLabel lblFacturaNo;
    private JLabel lblCliente;
    private JLabel lblFecha;
    private JLabel lblTotalFactura;
    private JLabel lblImporteRecibido;
    private JLabel lblTarjetaRecibido;
    private JLabel lblTarjetaRecibidoPOS;
    private JLabel lblCambio;
    private JLabel lblTipoPago;
    private JLabel lblTipoTarjeta;
    private JLabel lblTipoTarjeta2;
    private JLabel lblAut;

    private JTextField txtFacturaNo;
    private JTextField txtCliente;
    private JTextField txtTotalFactura;
    private JTextField txtImporteRecibido;
    private JTextField txtImporteTarjeta;
    private JTextField txtImporteTarjetaPOS;
    private JTextField txtCambio;
    private JTextField txtNumeroAut;

    private JXDatePicker dtpFecha;

    private JComboBox cmbTipoPago;
    private JComboBox cmbTipoTarjeta;
    private JComboBox cmbTipoTarjeta2;

    private JButton btnAceptar;
    private JButton btnCancelar;

    private GenericFrame frameParent;

    /**
     * Constructor
     */
    public pnlPagoFactura(GenericFrame frame, boolean modal, FacturaClienteController controller) {

        //Call Super Constructor
        super(frame, modal);

        //Setting frame parent
        this.frameParent = frame;

        //Init controller
        this.controller = controller;

        //Init Components
        initComponents();

        //Init Action Listeners
        initActionListeners();

        //Init Components Values
        initComponentsValues();

        //Set visible
        this.setTitle(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TITLE"));
        this.setLocation(550, 200);
        this.setSize(new Dimension(350, 450));
        this.setVisible(true);
    }

    /**
     * Init Components GUI
     */
    private void initComponents() {

        //**************************************************************************
        //Invoice Information Panel
        //**************************************************************************
        lblFacturaNo = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.NOFACTURA"));
        lblFacturaNo.setHorizontalAlignment(JLabel.RIGHT);

        lblFecha = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.FECHAALTA"));
        lblFecha.setHorizontalAlignment(JLabel.RIGHT);

        lblCliente = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.CLIENTE"));
        lblCliente.setHorizontalAlignment(JLabel.RIGHT);

        txtFacturaNo = new JTextField();
        txtFacturaNo.setEditable(false);

        dtpFecha = new JXDatePicker();
        dtpFecha.setEditable(false);

        txtCliente = new JTextField();
        txtCliente.setEditable(false);

        JPanel pnlFactura = new JPanel(new XYLayout());

        pnlFactura.add(lblFacturaNo, new XYConstraints(5, 5, 100, 23));
        pnlFactura.add(txtFacturaNo, new XYConstraints(113, 5, 200, 23));
        pnlFactura.add(lblFecha, new XYConstraints(5, 33, 100, 23));
        pnlFactura.add(dtpFecha, new XYConstraints(113, 33, 200, 23));
        pnlFactura.add(lblCliente, new XYConstraints(5, 61, 100, 23));
        pnlFactura.add(txtCliente, new XYConstraints(113, 61, 200, 23));
        pnlFactura.add(new JSeparator(SwingConstants.HORIZONTAL), new XYConstraints(5, 86, 320, 23));

        //**************************************************************************
        //Payment Information Panel
        //**************************************************************************

        lblTipoPago = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TIPOPAGO"));
        lblTipoPago.setHorizontalAlignment(JLabel.RIGHT);

        lblTipoTarjeta = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TIPOTARJETA"));
        lblTipoTarjeta.setHorizontalAlignment(JLabel.RIGHT);

        lblTipoTarjeta2 = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TIPOTARJETA2"));
        lblTipoTarjeta2.setHorizontalAlignment(JLabel.RIGHT);

        lblAut = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.AUTORIZACION"));
        lblAut.setHorizontalAlignment(JLabel.RIGHT);

        lblTotalFactura = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TOTAL"));
        lblTotalFactura.setHorizontalAlignment(JLabel.RIGHT);

        lblImporteRecibido = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.IMPORTE"));
        lblImporteRecibido.setHorizontalAlignment(JLabel.RIGHT);

        lblTarjetaRecibido = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.IMPORTETARJETA"));
        lblTarjetaRecibido.setHorizontalAlignment(JLabel.RIGHT);

        lblTarjetaRecibidoPOS = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.IMPORTETARJETA2"));
        lblTarjetaRecibidoPOS.setHorizontalAlignment(JLabel.RIGHT);

        lblCambio = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.CAMBIO"));
        lblCambio.setHorizontalAlignment(JLabel.RIGHT);

        cmbTipoPago = new JComboBox();
        cmbTipoTarjeta = new JComboBox();
        cmbTipoTarjeta2 = new JComboBox();

        txtNumeroAut = new JTextField();
        txtNumeroAut.setHorizontalAlignment(JTextField.RIGHT);
        txtNumeroAut.setEditable(false);

        txtTotalFactura = new JTextField();
        txtTotalFactura.setHorizontalAlignment(JTextField.RIGHT);
        txtTotalFactura.setEditable(false);

        txtImporteRecibido = new JTextField();
        txtImporteRecibido.setHorizontalAlignment(JTextField.RIGHT);
        txtImporteRecibido.setEditable(false);

        txtImporteTarjeta = new JTextField();
        txtImporteTarjeta.setHorizontalAlignment(JTextField.RIGHT);
        txtImporteTarjeta.setEditable(false);

        txtImporteTarjetaPOS = new JTextField();
        txtImporteTarjetaPOS.setHorizontalAlignment(JTextField.RIGHT);
        txtImporteTarjetaPOS.setEditable(false);

        txtCambio = new JTextField();
        txtCambio.setHorizontalAlignment(JTextField.RIGHT);
        txtCambio.setEditable(false);

        JPanel pnlDatosPago = new JPanel(new XYLayout());
        pnlDatosPago.add(lblTipoPago, new XYConstraints(5, 5, 100, 23));
        pnlDatosPago.add(cmbTipoPago, new XYConstraints(113, 5, 200, 23));
        pnlDatosPago.add(lblTipoTarjeta,  new XYConstraints(5, 33, 100, 23));
        pnlDatosPago.add(cmbTipoTarjeta, new XYConstraints(113,33,200,23));
        pnlDatosPago.add(lblTipoTarjeta2,  new XYConstraints(5, 61, 100, 23));
        pnlDatosPago.add(cmbTipoTarjeta2, new XYConstraints(113,61,200,23));
        pnlDatosPago.add(lblAut, new XYConstraints(5,89,100,23));
        pnlDatosPago.add(txtNumeroAut, new XYConstraints(113,89,200,23));
        pnlDatosPago.add(lblTotalFactura, new XYConstraints(5, 117, 100, 23));
        pnlDatosPago.add(txtTotalFactura, new XYConstraints(113, 117, 200, 23));
        pnlDatosPago.add(lblImporteRecibido, new XYConstraints(5, 145, 100, 23));
        pnlDatosPago.add(txtImporteRecibido, new XYConstraints(113, 145, 200, 23));
        pnlDatosPago.add(lblTarjetaRecibido, new XYConstraints(5, 173, 100, 23));
        pnlDatosPago.add(txtImporteTarjeta, new XYConstraints(113,173,200,23));
        pnlDatosPago.add(lblTarjetaRecibidoPOS, new XYConstraints(5,201,100,23));
        pnlDatosPago.add(txtImporteTarjetaPOS, new XYConstraints(113,201,200,23));
        pnlDatosPago.add(lblCambio, new XYConstraints(5, 229, 100, 23));
        pnlDatosPago.add(txtCambio, new XYConstraints(113, 229, 200, 23));

        //**************************************************************************
        //Action Buttons Panel
        //**************************************************************************

        ImageIcon aceptarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/accept.png"));
        ImageIcon actualizarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnAceptar = new JButton();
        btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        btnAceptar.setPreferredSize(new Dimension(90, 23));
        btnAceptar.setIcon(aceptarIco);

        btnCancelar = new JButton();
        btnCancelar.setText(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setPreferredSize(new Dimension(90, 23));
        btnCancelar.setIcon(actualizarIco);

        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlAction.add(btnAceptar);
        pnlAction.add(btnCancelar);

        //**************************************************************************
        //Main Panel GUI
        //**************************************************************************

        this.setLayout(new BorderLayout());
        this.add(pnlFactura, BorderLayout.NORTH);
        this.add(pnlDatosPago, BorderLayout.CENTER);
        this.add(pnlAction, BorderLayout.SOUTH);

    }

    /**
     * Init Components Values
     */
    private void initComponentsValues() {

        txtFacturaNo.setText(controller.getFactura().getNoDocumento() + "");
        dtpFecha.setDate(controller.getFactura().getFechaAlta());
        txtCliente.setText(controller.getFactura().getNombreCliente());

        txtTotalFactura.setText(controller.getFactura().getMontoNeto().toString());

        cmbTipoPago.setModel(new TipoPagoComboBoxModel(TiposPago.values()));
        cmbTipoPago.setSelectedIndex(0);
        cmbTipoTarjeta.setModel(new TipoTarjetaComboBoxModel(TiposTarjeta.values()));
        cmbTipoTarjeta.setSelectedIndex(1);

        cmbTipoTarjeta2.setModel(new TipoTarjetaComboBoxModel(TiposTarjeta.values()));
        cmbTipoTarjeta2.setSelectedIndex(0);
    }

    /**
     * Init Components Action Listeners
     */
    private void initActionListeners() {

        //Para Tipo de Pago Tarjetas (Varias Tarjetas)
        txtImporteTarjetaPOS.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
         txtImporteTarjetaPOS.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
                //Obtener Monto Total de la Factura
                 BigDecimal montoNetoFactura = new BigDecimal(txtTotalFactura.getText());

                //Obtener el Pago del Segundo POS
                BigDecimal importePos = new BigDecimal(txtImporteTarjetaPOS.getText());


                if (importePos.doubleValue() > montoNetoFactura.doubleValue()) {
                     JOptionPane.showMessageDialog(frameParent, messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"),
                             messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"), JOptionPane.WARNING_MESSAGE);
                 }
                 else{
                    txtImporteTarjeta.setText(montoNetoFactura.subtract(importePos).doubleValue() + "");
                }
             }
         });

        //Para Pago en Efectivo y Mixto
        txtImporteRecibido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        txtImporteRecibido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer validarMonto = 1;
                //Get Monto Neto Factura
                BigDecimal montoNetoFactura = new BigDecimal(txtTotalFactura.getText());

                //Get Importe Recibido
                BigDecimal importeRecibido = new BigDecimal(txtImporteRecibido.getText());

                TiposPago tiposPago = (TiposPago) ((TipoPagoComboBoxModel) cmbTipoPago.getModel()).getSelectedItem().getObject();

                if (tiposPago.getValue() != TiposPago.MIXTO.getValue()) {

                if (importeRecibido.doubleValue() < montoNetoFactura.doubleValue()) {
                    JOptionPane.showMessageDialog(frameParent, messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"),
                            messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"), JOptionPane.WARNING_MESSAGE);
                }

                if (importeRecibido.doubleValue() >= montoNetoFactura.doubleValue()) {
                    txtCambio.setText(importeRecibido.subtract(montoNetoFactura).doubleValue() + "");
                }
                }

                else{
                    if (importeRecibido.doubleValue() >= montoNetoFactura.doubleValue()) {
                        JOptionPane.showMessageDialog(frameParent, messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"),
                                messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"), JOptionPane.WARNING_MESSAGE);
                      validarMonto = 2;
                    }
                if(validarMonto !=2){
                //Get Diferencia (Se utiliza para calcular el Pago Mixto).
                txtImporteTarjeta.setText(montoNetoFactura.subtract(importeRecibido).doubleValue() + "");
                }
                }
            }
        });

        cmbTipoPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TiposPago tiposPago = (TiposPago) ((TipoPagoComboBoxModel) cmbTipoPago.getModel()).getSelectedItem().getObject();

                if (tiposPago.getValue() == TiposPago.EFECTIVO.getValue()) {
                    txtCambio.setText("0");
                    txtImporteRecibido.setText("");
                    txtImporteTarjeta.setText("");
                    txtImporteTarjetaPOS.setText("");
                    txtImporteRecibido.setEditable(true);
                    cmbTipoTarjeta.setEnabled(false);
                    cmbTipoTarjeta2.setEnabled(false);
                    //cmbTipoTarjeta.setSelectedIndex(-1);
                    txtImporteRecibido.requestFocusInWindow();
                    txtImporteTarjeta.setEditable(false);
                    txtImporteTarjetaPOS.setEditable(false);
                    txtNumeroAut.setEditable(false);
                    txtNumeroAut.setText("");
                }
                  else if(tiposPago.getValue() == tiposPago.TARJETAS.getValue()){
                    txtCambio.setText("0");
                    txtImporteRecibido.setText(txtTotalFactura.getText());
                    cmbTipoTarjeta.setEnabled(true);
                    cmbTipoTarjeta2.setEnabled(true);
                    cmbTipoTarjeta.setSelectedIndex(1);
                    cmbTipoTarjeta2.setSelectedIndex(0);
                    txtImporteRecibido.setEditable(false);
                    txtImporteTarjeta.setEnabled(false);
                    txtImporteTarjetaPOS.setEnabled(true);
                    txtImporteTarjeta.setEditable(false);
                    txtImporteTarjetaPOS.setEditable(true);
                    txtNumeroAut.setEditable(true);
                    txtNumeroAut.setText("");
                }
                  else if(tiposPago.getValue() == TiposPago.MIXTO.getValue()){
                    txtCambio.setText("0");
                    cmbTipoTarjeta.setEnabled(true);
                    cmbTipoTarjeta2.setEnabled(false);
                    cmbTipoTarjeta.setSelectedIndex(1);
                    cmbTipoTarjeta2.setSelectedIndex(0);
                    txtImporteTarjeta.setText("");
                    txtImporteRecibido.setText("");
                    txtImporteTarjetaPOS.setText("");
                    txtImporteRecibido.setEditable(true);
                    txtImporteRecibido.requestFocusInWindow();
                    txtImporteTarjeta.setEditable(false);
                    txtImporteTarjetaPOS.setEditable(false);
                    txtNumeroAut.setEditable(true);
                    txtNumeroAut.setText("");
                }
                else if(tiposPago.getValue() == TiposPago.TARJETA.getValue())
                {
                    txtCambio.setText("0");
                    txtImporteRecibido.setText(txtTotalFactura.getText());
                    cmbTipoTarjeta.setEnabled(true);
                    cmbTipoTarjeta2.setEnabled(false);
                    cmbTipoTarjeta.setSelectedIndex(1);
                    cmbTipoTarjeta2.setSelectedIndex(0);
                    txtImporteRecibido.setEditable(false);
                    txtImporteTarjetaPOS.setText("");
                    txtNumeroAut.setEditable(true);
                    txtImporteTarjeta.setEditable(false);
                    txtImporteTarjetaPOS.setEditable(false);
                    txtImporteTarjeta.setText("");
                    txtImporteTarjetaPOS.setText("");
                    txtNumeroAut.setText("");
                }
                else if(tiposPago.getValue() == TiposPago.CHEQUE.getValue()){
                    txtCambio.setText("0");
                    cmbTipoTarjeta.setEnabled(false);
                    cmbTipoTarjeta2.setEnabled(false);
                    cmbTipoTarjeta.setSelectedIndex(1);
                    cmbTipoTarjeta2.setSelectedIndex(0);
                    txtImporteTarjeta.setText("");
                    txtImporteRecibido.setText(txtTotalFactura.getText());
                    txtImporteTarjetaPOS.setText("");
                    txtImporteRecibido.requestFocusInWindow();
                    txtImporteTarjeta.setEditable(false);
                    txtImporteTarjetaPOS.setEditable(false);
                    txtNumeroAut.setEditable(true);
                    txtNumeroAut.setText("");
                }
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    //Get tipo de pago
                    TiposPago tiposPago = (TiposPago) ((TipoPagoComboBoxModel) cmbTipoPago.getModel()).getSelectedItem().getObject();

                    //Get tipo tarjeta
                    TiposTarjeta tiposTarjeta = (TiposTarjeta) ((TipoTarjetaComboBoxModel) cmbTipoTarjeta.getModel()).getSelectedItem().getObject();

                    TiposTarjeta tiposPos2 = (TiposTarjeta) ((TipoTarjetaComboBoxModel) cmbTipoTarjeta2.getModel()).getSelectedItem().getObject();
                    //Get total factura
                    BigDecimal totalFactura = new BigDecimal(txtTotalFactura.getText());

                    //Get Importe Recibido
                    BigDecimal importeRecibido = new BigDecimal("0");

                    //Get Numero de Autorizacion
                    String numeroAut = new String(txtNumeroAut.getText());

                    if (!txtImporteRecibido.getText().equals("")) {
                        importeRecibido = new BigDecimal(txtImporteRecibido.getText());
                    }

                    //Get Recibido en Tarjeta (Pago Mixto)
                    BigDecimal importeRecibidoTarjeta;

                    if(txtImporteTarjeta.getText().equals("")){
                        importeRecibidoTarjeta = new BigDecimal(0);
                    }
                    else{
                        importeRecibidoTarjeta = new BigDecimal(txtImporteTarjeta.getText());
                    }

                    //Get Importe Recibido 2do POS(Pago TARJETAS)
                    BigDecimal importeRecibidoPOS2;

                    if(txtImporteTarjetaPOS.getText().equals("")){
                        importeRecibidoPOS2 = new BigDecimal(0);
                    }
                    else{
                        importeRecibidoPOS2 = new BigDecimal(txtImporteTarjetaPOS.getText());
                    }

                    /***************VALIDACION TIPO DE PAGO TARJETAS**************************************/
                    if(tiposPago.getValue() == TiposPago.TARJETAS.getValue() && txtImporteTarjeta.getText().equals("") ||
                       tiposPago.getValue() == TiposPago.TARJETAS.getValue() && txtImporteTarjetaPOS.getText().equals("")){
                        throw new Exception(messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"));
                    }
                    if(tiposPago.getValue() == TiposPago.TARJETAS.getValue() && tiposTarjeta.getValue() == tiposPos2.getValue()){
                        throw new Exception(messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.POS"));
                    }
                    /***************************************************************************************************/

                    if (tiposPago.getValue() != TiposPago.MIXTO.getValue()) {
                    if (importeRecibido.doubleValue() < totalFactura.doubleValue()) {
                        throw new Exception(messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"));
                    }
                        //Registrar Pago Factura
                        controller.registrarPagoFactura(tiposPago.getValue(), importeRecibido, importeRecibidoTarjeta, tiposTarjeta.getValue(), numeroAut, importeRecibidoPOS2, tiposPos2.getValue());
                        //Close Panel Pago Factura
                        dispose();
                    }
                    else{
                        if (importeRecibido.doubleValue() > totalFactura.doubleValue()) {
                            throw new Exception(messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"));
                        }
                       controller.registrarPagoFactura(tiposPago.getValue(), importeRecibido, importeRecibidoTarjeta, tiposTarjeta.getValue(), numeroAut, importeRecibidoPOS2, tiposPago.getValue());
                        dispose();
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                            e.getMessage());
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Close Panel Pago Factura
                dispose();
            }
        });

    }
}
