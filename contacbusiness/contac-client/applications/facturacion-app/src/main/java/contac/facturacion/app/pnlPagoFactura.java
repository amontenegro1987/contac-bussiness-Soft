package contac.facturacion.app;

import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.models.comboBox.TipoPagoComboBoxModel;
import contac.facturacion.controller.FacturaClienteController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.TiposPago;
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
    private JLabel lblCambio;
    private JLabel lblTipoPago;

    private JTextField txtFacturaNo;
    private JTextField txtCliente;
    private JTextField txtTotalFactura;
    private JTextField txtImporteRecibido;
    private JTextField txtCambio;

    private JXDatePicker dtpFecha;

    private JComboBox cmbTipoPago;

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
        this.setLocation(400, 400);
        this.setSize(new Dimension(350, 320));
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

        lblTotalFactura = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TOTAL"));
        lblTotalFactura.setHorizontalAlignment(JLabel.RIGHT);

        lblImporteRecibido = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.IMPORTE"));
        lblImporteRecibido.setHorizontalAlignment(JLabel.RIGHT);

        lblCambio = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURA.CAMBIO"));
        lblCambio.setHorizontalAlignment(JLabel.RIGHT);

        cmbTipoPago = new JComboBox();

        txtTotalFactura = new JTextField();
        txtTotalFactura.setHorizontalAlignment(JTextField.RIGHT);
        txtTotalFactura.setEditable(false);

        txtImporteRecibido = new JTextField();
        txtImporteRecibido.setHorizontalAlignment(JTextField.RIGHT);
        txtImporteRecibido.setEditable(false);

        txtCambio = new JTextField();
        txtCambio.setHorizontalAlignment(JTextField.RIGHT);
        txtCambio.setEditable(false);

        JPanel pnlDatosPago = new JPanel(new XYLayout());
        pnlDatosPago.add(lblTipoPago, new XYConstraints(5, 5, 100, 23));
        pnlDatosPago.add(cmbTipoPago, new XYConstraints(113, 5, 200, 23));
        pnlDatosPago.add(lblTotalFactura, new XYConstraints(5, 33, 100, 23));
        pnlDatosPago.add(txtTotalFactura, new XYConstraints(113, 33, 200, 23));
        pnlDatosPago.add(lblImporteRecibido, new XYConstraints(5, 61, 100, 23));
        pnlDatosPago.add(txtImporteRecibido, new XYConstraints(113, 61, 200, 23));
        pnlDatosPago.add(lblCambio, new XYConstraints(5, 89, 100, 23));
        pnlDatosPago.add(txtCambio, new XYConstraints(113, 89, 200, 23));

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
    }

    /**
     * Init Components Action Listeners
     */
    private void initActionListeners() {

        //txtCostoCIF
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

                //Get Monto Neto Factura
                BigDecimal montoNetoFactura = new BigDecimal(txtTotalFactura.getText());

                //Get Importe Recibido
                BigDecimal importeRecibido = new BigDecimal(txtImporteRecibido.getText());

                if (importeRecibido.doubleValue() < montoNetoFactura.doubleValue()) {
                    JOptionPane.showMessageDialog(frameParent, messageBundle.getString("CONTAC.FORM.COBROFACTURA.VALIDA.IMPORTERECIBIDO"),
                            messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"), JOptionPane.WARNING_MESSAGE);
                }

                if (importeRecibido.doubleValue() >= montoNetoFactura.doubleValue()) {
                    txtCambio.setText(importeRecibido.subtract(montoNetoFactura).doubleValue() + "");
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
                    txtImporteRecibido.setEditable(true);
                    txtImporteRecibido.requestFocusInWindow();

                } else {
                    txtCambio.setText("0");

                    txtImporteRecibido.setText(txtTotalFactura.getText());
                    txtImporteRecibido.setEditable(false);
                }
            }
        });

    }
}
