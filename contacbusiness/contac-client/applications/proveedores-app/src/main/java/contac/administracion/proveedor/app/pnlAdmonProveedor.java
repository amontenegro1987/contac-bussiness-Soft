/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlAdmonProveedor.java
 *
 * Created on Aug 10, 2011, 9:50:11 PM
 */
package contac.administracion.proveedor.app;

import contac.administracion.controller.AdministraProveedorController;
import contac.commons.components.pnlBusquedaProveedor;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.internationalization.LanguageLocale;
import contac.commons.models.comboBox.ActividadEconComboBoxModel;
import contac.commons.models.comboBox.PaisComboBoxModel;
import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Pais;
import contac.modelo.entity.Proveedor;
import contac.modelo.entity.TiposPersona;
import contac.text.TextUtil;
import contac.web.WebUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Eddy Montenegro
 */
public class pnlAdmonProveedor extends GenericPanel {

    //Logger
    private static final Logger logger = Logger.getLogger(pnlAdmonProveedor.class);

    //Message Resource Bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/proveedor/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private AdministraProveedorController controller;


    /**
     * Creates new form pnlAdmonProveedor
     */
    public pnlAdmonProveedor(GenericFrame frame) {

        //Call super constructor
        super(frame, "administraProveedores", "Administrar Proveedores", true, "contac/proveedor/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new AdministraProveedorController();

        //Init components
        initComponents();

        //Init controller
        controller.init();

        //Init values
        initValues();

        //Register listeners
        registerListeners();
    }

    @Override
    public void initValues() {

        try {

            //Cargar datos
            cargarDatos();

            //Setting cursor on txtCodigo
            txtCodigo.requestFocusInWindow();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show message error
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.ERROR.INIT"),
                    e.getMessage());
        }
    }

    //Cargar datos formulario
    private void cargarDatos() {

        //**************************************************
        //Init data values components
        //**************************************************
        if (!controller.is_edit()) {

            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
            
            //Setting codigo de proveedor editable
            txtCodigo.setEditable(true);
        }
        
        if (controller.is_edit()) {
            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));
            
            //Setting codigo de proveedor not editable
            txtCodigo.setEditable(false);
        }

        // Tipo de persona
        if (controller.getTipoPersona().getValue() == TiposPersona.PERSONA_NATURAL.getValue()) {
            rbNatural.setSelected(true);
            rbJuridica.setSelected(false);
        } else {
            rbJuridica.setSelected(true);
            rbNatural.setSelected(false);
        }

        //Codigo
        txtCodigo.setText(controller.getCodigo() == 0 ? "" : String.valueOf(controller.getCodigo()));
        //Actividad economica
        ListCellRenderer rendererActividadEconomica = new ComboBoxEmptySelectionRenderer(cmbActividadEconomica, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getActividadEconomica() != null) {
            ActividadEconComboBoxModel actividadEcon = (ActividadEconComboBoxModel) cmbActividadEconomica.getModel();
            cmbActividadEconomica.setRenderer(rendererActividadEconomica);
            cmbActividadEconomica.setSelectedItem(actividadEcon.searchSelectedItem(controller.getActividadEconomica().getId()));
        } else {
            cmbActividadEconomica.setRenderer(rendererActividadEconomica);
            cmbActividadEconomica.setSelectedIndex(-1);
        }
        //Nombre comercial
        txtNombreComercial.setText(controller.getNombreComercial());
        //Razon social
        txtRazonSocial.setText(controller.getRazonSocial());
        //NIT
        txtNIT.setText(controller.getNit());
        //No. cuenta
        txtNoCuenta.setText(controller.getNoCuenta());
        //Descuento
        txtDescuento.setText(TextUtil.formatCurrency(controller.getDescuento().doubleValue()));
        //Plazo credito
        txtPlazoCredito.setText(String.valueOf(controller.getPlazoCredito()));
        //Limite credito
        txtLimiteCredito.setText(TextUtil.formatCurrency(controller.getLimiteCredito().doubleValue()));
        //Datos direccion
        //Pais
        ListCellRenderer rendererPais = new ComboBoxEmptySelectionRenderer(cmbPais, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getPais() != null) {
            PaisComboBoxModel paisModel = (PaisComboBoxModel) cmbPais.getModel();
            cmbPais.setRenderer(rendererPais);
            cmbPais.setSelectedItem(paisModel.searchSelectedItem(controller.getPais().getId()));
        } else {
            cmbPais.setRenderer(rendererPais);
            cmbPais.setSelectedIndex(-1);
        }
        txtEstado.setText(controller.getEstado());
        txtCiudad.setText(controller.getCiudad());
        txtCodigoPostal.setText(controller.getCodigoPostal());
        txtDireccion.setText(controller.getDireccion());
        txtEmail.setText(controller.getEmail());
        txtWeb.setText(controller.getWeb());
        txtTelefonoOficina.setText(controller.getTelefonoOficina());
        txtTelefonoMovil.setText(controller.getTelefonoMovil());
        txtFax.setText(controller.getFax());
    }

    //Register Key listeners
    private void registerListeners() {

        //*****************************************************************************
        //<Register listeners>
        //*****************************************************************************

        //<TxtCodigo>
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    cmbActividadEconomica.requestFocusInWindow();
                }
            }
        });

        //<CmbActividadEconomica>
        cmbActividadEconomica.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtNombreComercial.requestFocusInWindow();
                    txtNombreComercial.selectAll();
                }
            }
        });

        //<TxtNombreComercial>
        txtNombreComercial.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtRazonSocial.requestFocusInWindow();
                    txtRazonSocial.selectAll();
                }
            }
        });

        //<txtRazonSocial>
        txtRazonSocial.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtNIT.requestFocusInWindow();
                    txtNIT.selectAll();
                }
            }
        });

        //<txtNIT>
        txtNIT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtNoCuenta.requestFocusInWindow();
                    txtNoCuenta.selectAll();
                }
            }
        });

        //<txtNoCuenta>
        txtNoCuenta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtDescuento.requestFocusInWindow();
                    txtDescuento.selectAll();
                }
            }
        });

        //<txtDescuento>
        txtDescuento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtPlazoCredito.requestFocusInWindow();
                    txtPlazoCredito.selectAll();
                }
            }
        });

        txtDescuento.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtDescuento.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!txtDescuento.getText().equals(""))
                    txtDescuento.setText(TextUtil.formatCurrency(Double.parseDouble(txtDescuento.getText())));
            }
        });

        //<txtPlazoCredito>
        txtPlazoCredito.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtLimiteCredito.requestFocusInWindow();
                    txtLimiteCredito.selectAll();
                }
            }
        });

        //<txtLimiteCredito>
        txtLimiteCredito.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    cmbPais.requestFocusInWindow();
                }
            }
        });

        txtLimiteCredito.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!txtLimiteCredito.getText().equals("")) {
                    txtLimiteCredito.setText(String.valueOf(TextUtil.parseCurrency(txtLimiteCredito.getText())));
                    txtLimiteCredito.selectAll();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!txtLimiteCredito.getText().equals("")) {
                    txtLimiteCredito.setText(TextUtil.formatCurrency(Double.parseDouble(txtLimiteCredito.getText())));
                }
            }
        });


        //<cmbPais>
        cmbPais.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.VK_ENTER == e.getKeyChar()) {
                    txtEstado.requestFocusInWindow();
                }
            }
        });

        //<txtEstado>
        txtEstado.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidCharacter(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtCiudad.requestFocusInWindow();
                    txtCiudad.selectAll();
                }
            }
        });

        //<txtCiudad>
        txtCiudad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidCharacter(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtCodigoPostal.requestFocusInWindow();
                    txtCodigoPostal.selectAll();
                }
            }
        });

        //<TxtCodigoPostal>
        txtCodigoPostal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtDireccion.requestFocusInWindow();
                    txtDireccion.selectAll();
                }
            }
        });

        //<txtDireccion>
        txtDireccion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtEmail.requestFocusInWindow();
                    txtEmail.selectAll();
                }
            }
        });

        //<txtEmail>
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.VK_ENTER == e.getKeyChar()) {
                    txtWeb.requestFocusInWindow();
                    txtWeb.selectAll();
                }
            }
        });

        //<txtWeb>
        txtWeb.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidCharacter(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtTelefonoOficina.requestFocusInWindow();
                    txtTelefonoOficina.selectAll();
                }
            }
        });

        //<txtTelefonoOficina>
        txtTelefonoOficina.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtTelefonoMovil.requestFocusInWindow();
                    txtTelefonoMovil.selectAll();
                }
            }
        });

        //<txtTelefonoMovil>
        txtTelefonoMovil.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtFax.requestFocusInWindow();
                    txtFax.selectAll();
                }
            }
        });

        //<txtFax>
        txtFax.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
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
        pnlDatosGenerales = new javax.swing.JPanel();
        frmDatosProveedor = new javax.swing.JPanel();
        lblTipoPersona = new javax.swing.JLabel();
        rgTipoPersona = new org.jdesktop.swingx.JXRadioGroup();
        rbNatural = new javax.swing.JRadioButton();
        rbJuridica = new javax.swing.JRadioButton();
        lblNombreComercial = new javax.swing.JLabel();
        lblRazonSocial = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        txtNombreComercial = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNIT = new javax.swing.JLabel();
        txtNIT = new javax.swing.JTextField();
        lblNoCuenta = new javax.swing.JLabel();
        txtNoCuenta = new javax.swing.JTextField();
        lblDescuento = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        lblPlazoCredito = new javax.swing.JLabel();
        txtPlazoCredito = new javax.swing.JTextField();
        lblLimiteCredito = new javax.swing.JLabel();
        txtLimiteCredito = new javax.swing.JTextField();
        lblActividadEconomica = new javax.swing.JLabel();
        cmbActividadEconomica = new javax.swing.JComboBox();
        cmbPais = new javax.swing.JComboBox();
        lblEmail = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        lblCiudad = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        pnlTelefonos = new javax.swing.JPanel();
        txtTelefonoOficina = new javax.swing.JTextField();
        txtTelefonoMovil = new javax.swing.JTextField();
        txtFax = new javax.swing.JTextField();
        lblTelefonoOficina = new javax.swing.JLabel();
        lblTelefonoMovil = new javax.swing.JLabel();
        lblFax = new javax.swing.JLabel();
        spDireccion = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        lblWeb = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        btnBuscarProveedor = new javax.swing.JButton();
        pnlAcciones = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/proveedor/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        pnlDatosGenerales.setLayout(new java.awt.BorderLayout());

        frmDatosProveedor.setPreferredSize(new java.awt.Dimension(848, 400));

        lblTipoPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoPersona.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.TIPOPERSONA")); // NOI18N

        rbNatural.setSelected(true);
        rbNatural.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.TIPOPERSONA.NATURAL")); // NOI18N
        rbNatural.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbNaturalStateChanged(evt);
            }
        });

        rbJuridica.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.TIPOPERSONA.JURIDICA")); // NOI18N
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("contac/administracion/app/mensajes/Mensajes_es"); // NOI18N
        rbJuridica.setActionCommand(bundle1.getString("CONTAC.FORM.ADMINISTRACOMPANIA.TIPOPERSONA.JURIDICA")); // NOI18N
        rbJuridica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbJuridicaStateChanged(evt);
            }
        });

        javax.swing.GroupLayout rgTipoPersonaLayout = new javax.swing.GroupLayout(rgTipoPersona);
        rgTipoPersona.setLayout(rgTipoPersonaLayout);
        rgTipoPersonaLayout.setHorizontalGroup(
            rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rgTipoPersonaLayout.createSequentialGroup()
                .addComponent(rbNatural)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbJuridica)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        rgTipoPersonaLayout.setVerticalGroup(
            rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rgTipoPersonaLayout.createSequentialGroup()
                .addGroup(rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblNombreComercial.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreComercial.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.NOMBRECOMERCIAL")); // NOI18N

        lblRazonSocial.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRazonSocial.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.RAZONSOCIAL")); // NOI18N

        txtNombreComercial.setToolTipText("");
        txtNombreComercial.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombreComercial.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.CODIGO")); // NOI18N

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.setToolTipText("");
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        lblNIT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNIT.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.NIT")); // NOI18N

        lblNoCuenta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoCuenta.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.NOCUENTA")); // NOI18N

        lblDescuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescuento.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.DESCUENTO")); // NOI18N

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblPlazoCredito.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlazoCredito.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.PLAZOCREDITO")); // NOI18N

        txtPlazoCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblLimiteCredito.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLimiteCredito.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.LIMITECREDITO")); // NOI18N

        txtLimiteCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblActividadEconomica.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblActividadEconomica.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.ACTIVIDADECONOMICA")); // NOI18N

        cmbActividadEconomica.setModel(new ActividadEconComboBoxModel(controller.getActividadesEconomicas()));

        cmbPais.setModel(new PaisComboBoxModel(controller.getPaises()));
        cmbPais.setOpaque(false);

        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEmail.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.EMAIL")); // NOI18N

        lblCiudad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCiudad.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.CIUDAD")); // NOI18N

        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.ESTADO")); // NOI18N

        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.DIRECCION")); // NOI18N

        lblPais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPais.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.PAIS")); // NOI18N

        pnlTelefonos.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("CONTAC.FORM.PROVEEDOR.TELEFONOS"))); // NOI18N
        pnlTelefonos.setToolTipText("");
        pnlTelefonos.setName(""); // NOI18N

        lblTelefonoOficina.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.OFICINA")); // NOI18N

        lblTelefonoMovil.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.MOVIL")); // NOI18N

        lblFax.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.FAX")); // NOI18N

        javax.swing.GroupLayout pnlTelefonosLayout = new javax.swing.GroupLayout(pnlTelefonos);
        pnlTelefonos.setLayout(pnlTelefonosLayout);
        pnlTelefonosLayout.setHorizontalGroup(
            pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTelefonosLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelefonoOficina, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoOficina))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoMovil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFax)
                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        pnlTelefonosLayout.setVerticalGroup(
            pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTelefonosLayout.createSequentialGroup()
                .addGroup(pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefonoOficina)
                    .addComponent(lblTelefonoMovil)
                    .addComponent(lblFax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(pnlTelefonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoOficina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        txtDireccion.setColumns(20);
        txtDireccion.setLineWrap(true);
        txtDireccion.setRows(5);
        txtDireccion.setWrapStyleWord(true);
        spDireccion.setViewportView(txtDireccion);

        lblWeb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWeb.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.WEB")); // NOI18N

        lblCodigoPostal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoPostal.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.CODIGOPOSTAL")); // NOI18N

        jLabel1.setText("%");

        btnBuscarProveedor.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProveedor.setBorder(null);
        btnBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmDatosProveedorLayout = new javax.swing.GroupLayout(frmDatosProveedor);
        frmDatosProveedor.setLayout(frmDatosProveedorLayout);
        frmDatosProveedorLayout.setHorizontalGroup(
            frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmDatosProveedorLayout.createSequentialGroup()
                            .addComponent(lblTipoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(rgTipoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmDatosProveedorLayout.createSequentialGroup()
                            .addComponent(lblDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblPlazoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPlazoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblLimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtLimiteCredito))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmDatosProveedorLayout.createSequentialGroup()
                            .addComponent(lblNIT, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtNIT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNoCuenta))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmDatosProveedorLayout.createSequentialGroup()
                            .addComponent(lblRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmDatosProveedorLayout.createSequentialGroup()
                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(lblActividadEconomica, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbActividadEconomica, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmDatosProveedorLayout.createSequentialGroup()
                            .addComponent(lblNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                        .addComponent(lblPais, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                        .addComponent(lblCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(spDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                        .addComponent(lblWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(pnlTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(394, Short.MAX_VALUE))
        );
        frmDatosProveedorLayout.setVerticalGroup(
            frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmDatosProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rgTipoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblActividadEconomica, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbActividadEconomica, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNIT, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNIT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPlazoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPlazoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtLimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPais, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(frmDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(pnlTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        lblTipoPersona.getAccessibleContext().setAccessibleName("");
        pnlTelefonos.getAccessibleContext().setAccessibleName("");

        pnlDatosGenerales.add(frmDatosProveedor, java.awt.BorderLayout.CENTER);

        pnlAcciones.setPreferredSize(new java.awt.Dimension(965, 140));

        java.util.ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        btnAceptar.setText(bundle2.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setActionCommand(bundle2.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setLabel(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.setActionCommand("");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAccionesLayout = new javax.swing.GroupLayout(pnlAcciones);
        pnlAcciones.setLayout(pnlAccionesLayout);
        pnlAccionesLayout.setHorizontalGroup(
            pnlAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccionesLayout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(576, Short.MAX_VALUE))
        );
        pnlAccionesLayout.setVerticalGroup(
            pnlAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        pnlDatosGenerales.add(pnlAcciones, java.awt.BorderLayout.PAGE_END);

        add(pnlDatosGenerales, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void rbNaturalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
        if (rbNatural.isSelected()) {
            rbJuridica.setSelected(false);
        }

        if (!rbNatural.isSelected()) {
            rbJuridica.setSelected(true);
        }
    }//GEN-LAST:event_rbNaturalStateChanged

    private void rbJuridicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbJuridicaStateChanged
        if (rbJuridica.isSelected()) {
            rbNatural.setSelected(false);
        }

        if (!rbJuridica.isSelected()) {
            rbNatural.setSelected(true);
        }
    }//GEN-LAST:event_rbJuridicaStateChanged

    private void btnBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProveedorActionPerformed

        //Open Busqueda proveedor JDialog for stelecting Proveedor
        Proveedor proveedor = new pnlBusquedaProveedor(mdi, true).getProveedorSelected();

        //Setting codigo proveedor
        if (proveedor != null)
            txtCodigo.setText(String.valueOf(proveedor.getCodigo()));

        //Setting focus
        txtCodigo.requestFocusInWindow();
    }//GEN-LAST:event_btnBuscarProveedorActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed

        try {

            if (evt.VK_ENTER == evt.getKeyChar()) {

                if (!txtCodigo.getText().equals("")) {

                    //Load controller codigo
                    controller.setCodigo(Long.parseLong(txtCodigo.getText()));

                    //<Buscar proveedor>
                    controller.buscarProveedor();

                    //<Load datas>
                    cargarDatos();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        try {

            //Check required datas
            validarDatosForm();

            //Setting values controller
            controller.setCodigo(Integer.parseInt(txtCodigo.getText()));
            controller.setNit(txtNIT.getText().toUpperCase());
            controller.setNoCuenta(txtNoCuenta.getText());
            if (rbNatural.isSelected())
                controller.setTipoPersona(TiposPersona.PERSONA_NATURAL);
            if (rbJuridica.isSelected())
                controller.setTipoPersona(TiposPersona.PERSONA_JURIDICA);
            controller.setNombreComercial(txtNombreComercial.getText().toUpperCase());
            controller.setRazonSocial(txtRazonSocial.getText().toUpperCase());
            controller.setActividadEconomica((ActividadEconomica) ((ActividadEconComboBoxModel) cmbActividadEconomica.getModel()).getSelectedItem().getObject());
            controller.setDescuento(new BigDecimal(TextUtil.parseCurrency(txtDescuento.getText())));
            controller.setPlazoCredito(Integer.parseInt(txtPlazoCredito.getText()));
            controller.setLimiteCredito(new BigDecimal(TextUtil.parseCurrency(txtLimiteCredito.getText())));
            controller.setPais((Pais) ((PaisComboBoxModel) cmbPais.getModel()).getSelectedItem().getObject());
            controller.setEstado(txtEstado.getText().toUpperCase());
            controller.setCiudad(txtCiudad.getText().toUpperCase());
            controller.setCodigoPostal(txtCodigoPostal.getText());
            controller.setDireccion(txtDireccion.getText().toUpperCase());
            controller.setEmail(txtEmail.getText().toLowerCase());
            controller.setWeb(txtWeb.getText().toLowerCase());
            controller.setTelefonoOficina(txtTelefonoOficina.getText());
            controller.setTelefonoMovil(txtTelefonoMovil.getText());
            controller.setFax(txtFax.getText());

            //Save data
            if (!controller.is_edit()) {

                //register proveedor
                controller.registrarProveedor();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"));
            }

            if (controller.is_edit()) {

                //modificar cliente
                controller.ModificarProveedor();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"));
            }

            //reload form
            cargarDatos();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        //Init properties values
        controller.init();

        //Reload datas
        cargarDatos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    //<Valida datos del formulario>
    private void validarDatosForm() throws Exception {

        //Codigo
        if (txtCodigo.getText().equals("")) {
            //Request focus
            txtCodigo.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.CODIGO.VALIDA"));
        }

        //Actividad economica
        if (cmbActividadEconomica.getSelectedIndex() <= -1) {
            //Request focus
            cmbActividadEconomica.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.ACTIVIDADECONOMONICA.VALIDA"));
        }

        //Razon social
        if (txtRazonSocial.getText().equals("")) {
            //Request focus
            txtRazonSocial.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.RAZONSOCIAL.VALIDA"));
        }

        //Plazo credito
        if (txtPlazoCredito.getText().equals("")) {
            //Request focus
            txtPlazoCredito.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.PLAZOCREDITO.VALIDA"));
        }

        //Limite credito
        if (txtLimiteCredito.getText().equals("")) {
            //Request focus
            txtLimiteCredito.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.LIMITECREDITO.VALIDA"));
        }

        //Pais
        if (cmbPais.getSelectedIndex() <= -1) {
            //Request focus
            cmbPais.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.PAIS.VALIDA"));
        }

        //Ciudad
        if (txtCiudad.getText().equals("")) {
            //Request focus
            txtCiudad.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.CIUDAD.VALIDA"));
        }


        //Direccion
        if (txtDireccion.getText().equals("")) {
            //Request focus
            txtDireccion.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.DIRECCION.VALIDA"));
        }

        //<Email>
        if (!txtEmail.getText().equals("")) {

            if (!WebUtil.isValidEmailAddress(txtEmail.getText())) {
                //Request focus
                txtEmail.requestFocusInWindow();

                //Throw error message
                throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPROVEEDOR.EMAIL.VALIDA"));
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cmbActividadEconomica;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JPanel frmDatosProveedor;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblActividadEconomica;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblLimiteCredito;
    private javax.swing.JLabel lblNIT;
    private javax.swing.JLabel lblNoCuenta;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPlazoCredito;
    private javax.swing.JLabel lblRazonSocial;
    private javax.swing.JLabel lblTelefonoMovil;
    private javax.swing.JLabel lblTelefonoOficina;
    private javax.swing.JLabel lblTipoPersona;
    private javax.swing.JLabel lblWeb;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlDatosGenerales;
    private javax.swing.JPanel pnlTelefonos;
    private javax.swing.JRadioButton rbJuridica;
    private javax.swing.JRadioButton rbNatural;
    private org.jdesktop.swingx.JXRadioGroup rgTipoPersona;
    private javax.swing.JScrollPane spDireccion;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtLimiteCredito;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNoCuenta;
    private javax.swing.JTextField txtNombreComercial;
    private javax.swing.JTextField txtPlazoCredito;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtTelefonoMovil;
    private javax.swing.JTextField txtTelefonoOficina;
    private javax.swing.JTextField txtWeb;
    // End of variables declaration//GEN-END:variables
}
