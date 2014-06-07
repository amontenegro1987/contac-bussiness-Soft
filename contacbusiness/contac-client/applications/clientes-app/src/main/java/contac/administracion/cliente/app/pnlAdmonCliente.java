/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlAdmonCliente.java
 *
 * Created on 02-05-2012, 01:09:47 PM
 */
package contac.administracion.cliente.app;

import contac.administracion.cliente.controller.AdministraClienteController;
import contac.commons.components.pnlBusquedaCliente;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.models.comboBox.ActividadEconComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.PaisComboBoxModel;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Cliente;
import contac.modelo.entity.Pais;
import contac.modelo.entity.TiposPersona;
import contac.text.TextUtil;
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
 * @author EMontenegro
 */
public class pnlAdmonCliente extends GenericPanel {

    //Logger
    private static final Logger logger = Logger.getLogger(pnlAdmonCliente.class);

    //Message Resource Bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/cliente/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private AdministraClienteController controller;

    /**
     * Creates new form pnlAdmonCliente
     */
    public pnlAdmonCliente(GenericFrame frame) {

        //Call super constructor
        super(frame, "administraClientes", "Administrar Clientes", true, "contac/cliente/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new AdministraClienteController();

        //Init components
        initComponents();

        //Init controller
        controller.init();

        //Init values
        initValues();

        //Register listeners
        registerListeners();

        //Request focus in windows
        txtNIT.requestFocusInWindow();
    }

    @Override
    public void initValues() {
        try {
            //Cargar datos
            cargarDatos();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show message error
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.INIT"),
                    e.getMessage());
        }
    }

    /**
     * Cargar datos generales del formulario
     */
    private void cargarDatos() {

        //*****************************************
        //Init data values components
        //*****************************************

        //Numero nit
        txtNIT.setText(controller.getNit());
        //Tipo de persona
        if (controller.getTipoPersona().getValue() == TiposPersona.PERSONA_NATURAL.getValue()) {
            rbNatural.setSelected(true);
            rbJuridica.setSelected(false);
        } else {
            rbJuridica.setSelected(true);
            rbNatural.setSelected(false);
        }
        //Nombre comercial
        txtNombreComercial.setText(controller.getNombreComercial());
        //Razon social
        txtRazonSocial.setText(controller.getRazonSocial());
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
        //Descuento
        txtDescuento.setText(TextUtil.formatCurrency(controller.getDescuento().doubleValue()));
        //Plazo credito
        txtPlazoCredito.setText(String.valueOf(controller.getPlazoCredito()));
        //Limite credito
        txtLimiteCredito.setText(TextUtil.formatCurrency(controller.getLimiteCredito().doubleValue()));
        //Pais
        ListCellRenderer rendererPais = new ComboBoxEmptySelectionRenderer(cmbPais, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getPais() != null) {
            PaisComboBoxModel paisModel = (PaisComboBoxModel) cmbPais.getModel();
            cmbPais.setRenderer(rendererPais);
            cmbPais.setSelectedItem(paisModel.searchSelectedItem(controller.getPais().getId()));
        } else {
            cmbPais.setRenderer(rendererPais);
            cmbPais.setSelectedIndex(153);
        }
        //Estado
        txtEstado.setText(controller.getEstado());
        //Ciudad
        txtCiudad.setText(controller.getCiudad());
        //Codigo postal
        txtCodigoPostal.setText(controller.getCodigoPostal());
        //Direccion
        txtDireccion.setText(controller.getDireccion());
        //Email
        txtEmail.setText(controller.getEmail());
        //web
        txtWeb.setText(controller.getWeb());
        //Telefono oficina
        txtTelefonoOficina.setText(controller.getTelefonoOficina());
        //Telefono movil
        txtTelefonoMovil.setText(controller.getTelefonoMovil());
        //Fax
        txtFax.setText(controller.getFax());
    }

    //Register listeners
    private void registerListeners() {

        //<txtNIT>
        txtNIT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    rbNatural.requestFocusInWindow();
                }
            }
        });

        //rbNatural
        rbNatural.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    rbJuridica.requestFocusInWindow();
                }
            }
        });

        //rbJuridica
        rbJuridica.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtNombreComercial.requestFocusInWindow();
                    txtNombreComercial.selectAll();
                }
            }
        });

        //txtNombreComercial
        txtNombreComercial.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtRazonSocial.requestFocusInWindow();
                    txtRazonSocial.selectAll();
                }
            }
        });

        //txtRazonSocial
        txtRazonSocial.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    cmbActividadEconomica.requestFocusInWindow();
                }
            }
        });

        //Actividad economica
        cmbActividadEconomica.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.VK_ENTER == e.getKeyChar()) {
                    txtDescuento.requestFocusInWindow();
                }
            }
        });

        //Descuento
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
        frmDatosCliente = new javax.swing.JPanel();
        lblTipoPersona = new javax.swing.JLabel();
        rgTipoPersona = new org.jdesktop.swingx.JXRadioGroup();
        rbNatural = new javax.swing.JRadioButton();
        rbJuridica = new javax.swing.JRadioButton();
        btnBuscarCliente = new javax.swing.JButton();
        lblActividadEconomica = new javax.swing.JLabel();
        cmbActividadEconomica = new javax.swing.JComboBox();
        txtNombreComercial = new javax.swing.JTextField();
        lblNombreComercial = new javax.swing.JLabel();
        lblRazonSocial = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        txtNIT = new javax.swing.JTextField();
        lblDescuento = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblPlazoCredito = new javax.swing.JLabel();
        txtPlazoCredito = new javax.swing.JTextField();
        lblLimiteCredito = new javax.swing.JLabel();
        txtLimiteCredito = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblPais = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox();
        lblEstado = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        txtCodigoPostal = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        lblCiudad = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        spDireccion = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblWeb = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        pnlTelefonos = new javax.swing.JPanel();
        txtTelefonoOficina = new javax.swing.JTextField();
        txtTelefonoMovil = new javax.swing.JTextField();
        txtFax = new javax.swing.JTextField();
        lblTelefonoOficina = new javax.swing.JLabel();
        lblTelefonoMovil = new javax.swing.JLabel();
        lblFax = new javax.swing.JLabel();
        lblNIT1 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        pnlAcciones = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRegistroRapido = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/cliente/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.TITLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        pnlDatosGenerales.setLayout(new java.awt.BorderLayout());

        frmDatosCliente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTipoPersona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoPersona.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.TIPOPERSONA")); // NOI18N
        frmDatosCliente.add(lblTipoPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 37, 83, 20));

        rbNatural.setSelected(true);
        rbNatural.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.TIPOPERSONA.NATURAL")); // NOI18N
        rbNatural.setActionCommand(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.TIPOPERSONA.NATURAL")); // NOI18N
        rbNatural.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbNaturalStateChanged(evt);
            }
        });

        rbJuridica.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.TIPOPERSONA.JURIDICA")); // NOI18N
        rbJuridica.setActionCommand("");
        rbJuridica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbJuridicaStateChanged(evt);
            }
        });

        javax.swing.GroupLayout rgTipoPersonaLayout = new javax.swing.GroupLayout(rgTipoPersona);
        rgTipoPersona.setLayout(rgTipoPersonaLayout);
        rgTipoPersonaLayout.setHorizontalGroup(
            rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rgTipoPersonaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbNatural)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbJuridica)
                .addContainerGap())
        );
        rgTipoPersonaLayout.setVerticalGroup(
            rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rgTipoPersonaLayout.createSequentialGroup()
                .addGroup(rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        frmDatosCliente.add(rgTipoPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(412, 37, -1, 20));

        btnBuscarCliente.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarCliente.setBorder(null);
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });
        frmDatosCliente.add(btnBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 37, 25, 23));

        lblActividadEconomica.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblActividadEconomica.setText(bundle.getString("CONTAC.FORM.PROVEEDOR.ACTIVIDADECONOMICA")); // NOI18N
        frmDatosCliente.add(lblActividadEconomica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 126, 120, 20));

        cmbActividadEconomica.setModel(new ActividadEconComboBoxModel(controller.getActividadesEconomicas()));
        frmDatosCliente.add(cmbActividadEconomica, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 125, 212, 23));

        txtNombreComercial.setToolTipText("");
        txtNombreComercial.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombreComercial.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosCliente.add(txtNombreComercial, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 66, 430, 23));

        lblNombreComercial.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreComercial.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.NOMBRECOMERCIAL")); // NOI18N
        frmDatosCliente.add(lblNombreComercial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, 120, 20));

        lblRazonSocial.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRazonSocial.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.RAZONSOCIAL")); // NOI18N
        frmDatosCliente.add(lblRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 96, 120, 20));
        frmDatosCliente.add(txtRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 96, 430, 23));

        txtNIT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNITKeyPressed(evt);
            }
        });
        frmDatosCliente.add(txtNIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 37, 150, 23));

        lblDescuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescuento.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.DESCUENTO")); // NOI18N
        frmDatosCliente.add(lblDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 157, 120, 20));

        jLabel1.setText("%");
        frmDatosCliente.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 154, -1, 23));

        lblPlazoCredito.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlazoCredito.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.PLAZOCREDITO")); // NOI18N
        frmDatosCliente.add(lblPlazoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 155, 81, 20));

        txtPlazoCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        frmDatosCliente.add(txtPlazoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 154, 55, 23));

        lblLimiteCredito.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLimiteCredito.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.LIMITECREDITO")); // NOI18N
        frmDatosCliente.add(lblLimiteCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 155, 87, 20));

        txtLimiteCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        frmDatosCliente.add(txtLimiteCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 154, 124, 23));
        frmDatosCliente.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 188, 556, 5));

        lblPais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPais.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.PAIS")); // NOI18N
        frmDatosCliente.add(lblPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 199, 120, 20));

        cmbPais.setModel(new PaisComboBoxModel(controller.getPaises()));
        cmbPais.setOpaque(false);
        frmDatosCliente.add(cmbPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 199, 190, 23));

        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.ESTADO")); // NOI18N
        frmDatosCliente.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 199, 50, 20));
        frmDatosCliente.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(421, 199, 150, 23));
        frmDatosCliente.add(txtCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 228, 150, 23));

        lblCodigoPostal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoPostal.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.CODIGOPOSTAL")); // NOI18N
        frmDatosCliente.add(lblCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 228, 70, 20));
        frmDatosCliente.add(txtCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 228, 190, 23));

        lblCiudad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCiudad.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.CIUDAD")); // NOI18N
        frmDatosCliente.add(lblCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 228, 120, 20));

        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.DIRECCION")); // NOI18N
        frmDatosCliente.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 258, 120, 20));

        txtDireccion.setColumns(20);
        txtDireccion.setLineWrap(true);
        txtDireccion.setRows(5);
        txtDireccion.setWrapStyleWord(true);
        spDireccion.setViewportView(txtDireccion);

        frmDatosCliente.add(spDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 258, 430, 50));

        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEmail.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.EMAIL")); // NOI18N
        frmDatosCliente.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 318, 120, 20));
        frmDatosCliente.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 318, 430, 23));

        lblWeb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWeb.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.WEB")); // NOI18N
        frmDatosCliente.add(lblWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 348, 120, 20));
        frmDatosCliente.add(txtWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 348, 430, 23));

        pnlTelefonos.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.TELEFONOS"))); // NOI18N
        pnlTelefonos.setToolTipText("");
        pnlTelefonos.setName(""); // NOI18N

        lblTelefonoOficina.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.OFICINA")); // NOI18N

        lblTelefonoMovil.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.MOVIL")); // NOI18N

        lblFax.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.FAX")); // NOI18N

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
                    .addComponent(txtTelefonoOficina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,                                                      javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        frmDatosCliente.add(pnlTelefonos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 378, -1, -1));
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("contac.cliente.app.mensajes/Mensajes_es"); // NOI18N
        pnlTelefonos.getAccessibleContext().setAccessibleName(bundle1.getString("CONTAC.FORM.ADMINISTRACLIENTES.TELEFONOS")); // NOI18N

        lblNIT1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNIT1.setText(bundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.NIT")); // NOI18N
        frmDatosCliente.add(lblNIT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 38, 120, 20));

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        frmDatosCliente.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 154, 51, 23));

        pnlDatosGenerales.add(frmDatosCliente, java.awt.BorderLayout.CENTER);

        pnlAcciones.setPreferredSize(new java.awt.Dimension(965, 140));

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setActionCommand("");
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

        btnRegistroRapido.setText(bundle.getString("CONTAC.FORM.REGISTRORAPIDO")); // NOI18N
        btnRegistroRapido.setActionCommand("");
        btnRegistroRapido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroRapidoActionPerformed(evt);
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
                    .addGap(10, 10, 10)
                    .addComponent(btnRegistroRapido, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(511, Short.MAX_VALUE))
        );
        pnlAccionesLayout.setVerticalGroup(
            pnlAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar)
                    .addComponent(btnRegistroRapido))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        btnAceptar.getAccessibleContext().setAccessibleName("");

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

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed

        //Open customer searching JDialog for selecting
        Cliente cliente = new pnlBusquedaCliente(mdi, true).getClienteSelected();

        //Setting nit customer
        if (cliente != null)
            txtNIT.setText(String.valueOf(cliente.getNit()));

        //Setting focus
        txtNIT.requestFocusInWindow();
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void txtNITKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNITKeyPressed

        try {

            if (evt.VK_ENTER == evt.getKeyChar()) {

                if (!txtNIT.getText().equals("")) {

                    //Load controller NIT
                    controller.setNit(txtNIT.getText());

                    //<Buscar cliente>
                    controller.buscarCliente();

                    //<Cargar datos>
                    cargarDatos();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtNITKeyPressed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        try {

            //Check required datas
            validarDatosForm();

            //Setting values controller
            controller.setNit(txtNIT.getText().toUpperCase());
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

                //register cliente
                controller.registrarCliente();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"));
            }

            if (controller.is_edit()) {

                //modificar cliente
                controller.modificarCliente();

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

    private void btnRegistroRapidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:btnRegistroRapidoActionPerformed
        txtEstado.setText("Managua");
        txtCiudad.setText("Managua");
        txtDireccion.setText("Managua");
        cmbActividadEconomica.setSelectedIndex(3);
    }//GEN-LAST:event_btnRegistroRapidoActionPerformed

    //<Valida datos del formulario>
    private void validarDatosForm() throws Exception {

        //<Nit>
        /*if (txtNIT.getText().equals("")) {
            //Request focus
            txtNIT.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.NIT.VALIDA"));
        }*/

        //<Razon Social>
        if (txtRazonSocial.getText().equals("")) {
            //Request focus
            txtRazonSocial.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.RAZONSOCIAL.VALIDA"));
        }

        //<Actividad Economica>
        if (cmbActividadEconomica.getSelectedIndex() <= -1) {
            //Request focus
            cmbActividadEconomica.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.ACTIVIDADECONOMICA.VALIDA"));
        }

        //<Plazo credito>
        if (txtPlazoCredito.getText().equals("")) {
            //Request focus
            txtPlazoCredito.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.PLAZOCREDITO.VALIDA"));
        }

        //<Limite credito>
        if (txtLimiteCredito.getText().equals("")) {
            //Request focus
            txtLimiteCredito.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.LIMITECREDITO.VALIDA"));
        }

        //<Pais>
        if (cmbPais.getSelectedIndex() <= -1) {
            //Request focus
            cmbPais.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.PAIS.VALIDA"));
        }

        //<Estado>
        if (txtEstado.getText().equals("")) {
            requestFocusInWindow();
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.ESTADO.VALIDA"));
        }

        //<Ciudad>
        if (txtCiudad.getText().equals("")) {
            requestFocusInWindow();
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINSITRACLIENTES.CIUDAD.VALIDA"));
        }

        //<Direccion>
        if (txtDireccion.getText().equals("")) {
            requestFocusInWindow();
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRACLIENTES.DIRECCION.VALIDA"));
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistroRapido;
    private javax.swing.JComboBox cmbActividadEconomica;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JPanel frmDatosCliente;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblActividadEconomica;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblLimiteCredito;
    private javax.swing.JLabel lblNIT1;
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
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtLimiteCredito;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNombreComercial;
    private javax.swing.JTextField txtPlazoCredito;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtTelefonoMovil;
    private javax.swing.JTextField txtTelefonoOficina;
    private javax.swing.JTextField txtWeb;
    // End of variables declaration//GEN-END:variables
}
