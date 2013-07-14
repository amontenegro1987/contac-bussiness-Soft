/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlFacturaCompra.java
 *
 * Created on 09-07-2012, 11:40:52 PM
 */
package contac.facturacion.app;

import contac.commons.app.BaseController;
import contac.commons.components.AgentesVentasPnl;
import contac.commons.components.TasasCambioPnl;
import contac.commons.components.pnlBusquedaCliente;
import contac.commons.components.pnlBusquedaProducto;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.TipoEntradaComboBoxModel;
import contac.commons.models.comboBox.TipoFacturaComboBoxModel;
import contac.commons.models.tables.BeanTableModel;
import contac.facturacion.controller.FacturaClienteController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlFacturaCliente extends GenericPanel {

    //Apache Log4j
    private static final Logger logger = Logger.getLogger(pnlFacturaCliente.class);

    //Controller
    private FacturaClienteController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlFacturaCompra
     */
    public pnlFacturaCliente(GenericFrame frame) {

        //Call super constructor
        super(frame, "facturaClientes", "Factura Clientes", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NI"));

        //Init controller
        controller = new FacturaClienteController();

        //Init controller values
        controller.init();

        //Init components
        initComponents();

        //Init values
        initValues();

        //Register listeners
        registerListeners();

        //Editar registros
        controller.set_edit(false);
    }

    /**
     * Create a new formulario factura cliente
     *
     * @param frame,      GenericFrame
     * @param controller, BaseController
     */
    public pnlFacturaCliente(GenericFrame frame, BaseController controller) {

        //Call super constructor
        super(frame, "facturaClientes", "Factura Clientes", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NI"));

        //Init controller
        this.controller = (FacturaClienteController) controller;

        //Init modificacion
        this.controller.initModificacion();

        //Init components
        initComponents();

        //Init values
        initValues();

        //Register listeners
        registerListeners();

        //Editar registros
        controller.set_edit(true);

        //Change btnAceptar label
        btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

    }

    @Override
    public void initValues() {

        //************************************************************
        //Init data values components
        //************************************************************
        if (!controller.is_edit()) {

            cmbTipoFactura.setEnabled(true);
            cmbAlmacen.setEnabled(true);
            txtCodigoCliente.setEditable(true);

            btnBuscarAgente.setEnabled(true);
            btnBuscarCliente.setEnabled(true);
            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        }

        if (controller.is_edit()) {

            cmbTipoFactura.setEnabled(false);
            cmbAlmacen.setEnabled(false);
            txtCodigoCliente.setEditable(false);

            btnBuscarAgente.setEnabled(false);
            btnBuscarCliente.setEnabled(false);
            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));
        }

        //Numero de factura
        if (controller.getNoFactura() > 0) {
            txtNoFactura.setText(String.valueOf(controller.getNoFactura()));
        } else {
            txtNoFactura.setText("");
        }

        //Seleccione tipo factura
        ListCellRenderer rendererTipoFactura = new ComboBoxEmptySelectionRenderer(cmbTipoFactura, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getTipoFactura() != null) {
            TipoFacturaComboBoxModel tipoFacturaModel = (TipoFacturaComboBoxModel) cmbTipoFactura.getModel();
            cmbTipoFactura.setRenderer(rendererTipoFactura);
            cmbTipoFactura.setSelectedItem(tipoFacturaModel.searchSelectedItem(controller.getTipoFactura().getValue()));
        } else {
            cmbTipoFactura.setRenderer(rendererTipoFactura);
            cmbTipoFactura.setSelectedIndex(-1);
        }

        //Seleccione almacen de egreso
        ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getAlmacen() != null) {
            AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacen.getModel();
            cmbAlmacen.setRenderer(rendererAlmacen);
            cmbAlmacen.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacen().getId()));
        } else {
            cmbAlmacen.setRenderer(rendererAlmacen);
            cmbAlmacen.setSelectedIndex(-1);
        }

        //Fecha de alta factura
        dtpFechaAlta.setFormats("dd/MM/yyyy");
        dtpFechaAlta.setDate(controller.getFechaAlta());

        //Seleccionar cliente
        if (controller.getCliente() != null) {
            txtCodigoCliente.setText(controller.getCliente().getCodigo() + "");
            txtNombreCliente.setText(controller.getCliente().getRazonSocial());
        } else {
            txtCodigoCliente.setText("");
            txtNombreCliente.setText("");
        }

        //Seleccionar agente
        if (controller.getAgenteVentas() != null) {
            txtAgente.setText(controller.getAgenteVentas().getNombre());
        } else {
            txtAgente.setText("");
        }

        //Tasa de cambio
        if (controller.getTasaCambio() != null) {
            txtTasaCambio.setText(controller.getTasaCambio().toString());
        } else {
            txtTasaCambio.setText("");
        }
        
        //Inicializar datos del producto
        txtCodigo.setText("");
        txtNombreProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtDescuento.setText("");
        txtSubtotal.setText(controller.getMontoAntesImpuesto().toString());

        //************************************************************
        //Init articulo bean table model
        //************************************************************
        articuloBeanTableModel = new BeanTableModel<ArticuloFactura>(ArticuloFactura.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulosFactura.setModel(articuloBeanTableModel);
        tblArticulosFactura.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulosFactura.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Factura", "Movimiento Inventario", "Renglon",
                "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update", "Exento", "Costo", "Costo Total", "Retencion Fuente",
                "Retencion Municipal", "Porc Retencion Fuente", "Porc Retencion Municipal", "Porc Iva", "Porc Descuento",
                "Precio Promocion", "Precio Neto", "Iva", "No Factura"};

        for (String columnLabel : articuloColumnRemove) {
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnLabel)));
        }

        //Ordering table columns
        columnModel.moveColumn(1, 0); //Codigo producto
        columnModel.moveColumn(4, 1); //Nombre producto
        columnModel.moveColumn(3, 2); //Codigo de fabricante
        columnModel.moveColumn(7, 4); //Unidad de medida
        columnModel.moveColumn(7, 5); //Precio bruto

        //Setting prefered sized
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(215);

        //Adding cell renderer class
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(4).setCellRenderer(centerAlignment);

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(6).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(7).setCellRenderer(decimalFormatRenderer);
    }

    public void registerListeners() {

        txtAgente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCantidad.requestFocusInWindow();
                    txtPrecio.selectAll();
                }
            }
        });

        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtPrecio.requestFocusInWindow();
                    txtPrecio.selectAll();
                }
            }
        });

        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtDescuento.requestFocusInWindow();
                    txtDescuento.selectAll();
                }
            }
        });

        txtDescuento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCodigo.requestFocusInWindow();
                    txtCodigo.selectAll();
                }
            }
        });
        
        tblArticulosFactura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Getting row selected
                int rowSelected = tblArticulosFactura.getSelectedRow();
                ArticuloFactura articuloFactura = (ArticuloFactura) ((BeanTableModel) tblArticulosFactura.getModel()).getRow(rowSelected);

                //Setting values articulo para modificar
                txtCodigo.setText(articuloFactura.getProducto().getCodigo());
                txtNombreProducto.setText(articuloFactura.getProducto().getNombre());
                txtCantidad.setText(String.valueOf(articuloFactura.getCantidad()));
                txtPrecio.setText(articuloFactura.getPrecioBruto().toString());
                txtDescuento.setText(articuloFactura.getPorcDescuento().toString());

                //Setting values to process
                productoSelected = articuloFactura.getProducto();
                renglonSelected = articuloFactura.getRenglon();

                //Setting codigo del producto editable
                txtCodigo.setEditable(false);

                //Setting request focus
                txtCantidad.requestFocusInWindow();
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

        frmFacturaCliente = new javax.swing.JPanel();
        lblNoFactura = new javax.swing.JLabel();
        txtNoFactura = new javax.swing.JTextField();
        lblAlmacen = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox();
        lblFechaAlta = new javax.swing.JLabel();
        dtpFechaAlta = new org.jdesktop.swingx.JXDatePicker();
        txtNombreCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        lblCliente = new javax.swing.JLabel();
        lblTipoFactura = new javax.swing.JLabel();
        cmbTipoFactura = new javax.swing.JComboBox();
        lblTasaCambio = new javax.swing.JLabel();
        txtTasaCambio = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosFactura = new org.jdesktop.swingx.JXTable();
        txtCodigoCliente = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        txtNombreProducto = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        lblTasaCambio1 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtAgente = new javax.swing.JTextField();
        lblAgente = new javax.swing.JLabel();
        btnCalcular = new javax.swing.JButton();
        btnBuscarAgente = new javax.swing.JButton();
        btnEditarFechaRegistro = new javax.swing.JButton();
        btnBuscarTasaCambio = new javax.swing.JButton();
        header = new org.jdesktop.swingx.JXHeader();

        setLayout(new java.awt.BorderLayout());

        lblNoFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes_es"); // NOI18N
        lblNoFactura.setText(bundle.getString("CONTAC.FORM.FACTURACION.NOFACTURA")); // NOI18N

        txtNoFactura.setEditable(false);
        txtNoFactura.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNoFactura.setToolTipText("");
        txtNoFactura.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNoFactura.setPreferredSize(new java.awt.Dimension(59, 30));

        lblAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen.setText(bundle.getString("CONTAC.FORM.FACTURACION.ALMACEN")); // NOI18N

        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        lblFechaAlta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaAlta.setText(bundle.getString("CONTAC.FORM.FACTURACION.FECHAALTA")); // NOI18N

        dtpFechaAlta.setEnabled(false);

        txtNombreCliente.setEditable(false);
        txtNombreCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreCliente.setToolTipText("");
        txtNombreCliente.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombreCliente.setPreferredSize(new java.awt.Dimension(59, 30));

        btnBuscarCliente.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCliente.setText(bundle.getString("CONTAC.FORM.FACTURACION.CLIENTE")); // NOI18N

        lblTipoFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoFactura.setText(bundle.getString("CONTAC.FORM.FACTURACION.TIPOFACTURA")); // NOI18N

        cmbTipoFactura.setModel(new TipoFacturaComboBoxModel(TiposFactura.values()));

        lblTasaCambio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTasaCambio.setText(bundle.getString("CONTAC.FORM.FACTURACION.TASACAMBIO")); // NOI18N

        txtTasaCambio.setEditable(false);
        txtTasaCambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTasaCambio.setToolTipText("");
        txtTasaCambio.setMinimumSize(new java.awt.Dimension(6, 25));
        txtTasaCambio.setPreferredSize(new java.awt.Dimension(59, 30));

        tblArticulosFactura.setEditable(false);
        jScrollPane1.setViewportView(tblArticulosFactura);

        txtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoClienteKeyPressed(evt);
            }
        });

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setToolTipText(bundle.getString("CONTAC.FORM.FACTURACION.CODIGO")); // NOI18N
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });

        txtNombreProducto.setEditable(false);
        txtNombreProducto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreProducto.setToolTipText(bundle.getString("CONTAC.FORM.FACTURACION.NOMBREPRODUCTO")); // NOI18N
        txtNombreProducto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombreProducto.setPreferredSize(new java.awt.Dimension(59, 30));

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidad.setToolTipText(bundle.getString("CONTAC.FORM.FACTURACION.CANTIDAD")); // NOI18N
        txtCantidad.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCantidad.setPreferredSize(new java.awt.Dimension(60, 30));

        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecio.setToolTipText(bundle.getString("CONTAC.FORM.FACTURACION.PRECIO")); // NOI18N
        txtPrecio.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecio.setPreferredSize(new java.awt.Dimension(60, 30));

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setToolTipText(bundle.getString("CONTAC.FORM.FACTURACION.DESCUENTO")); // NOI18N
        txtDescuento.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescuento.setPreferredSize(new java.awt.Dimension(60, 30));
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
        });

        lblTasaCambio1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTasaCambio1.setText(bundle.getString("CONTAC.FORM.TOTALFACTURACION.SUBTOTAL")); // NOI18N

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setToolTipText("");
        txtSubtotal.setMinimumSize(new java.awt.Dimension(6, 25));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(60, 30));
        txtSubtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSubtotalKeyPressed(evt);
            }
        });

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        btnAceptar.setActionCommand(bundle1.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
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

        txtAgente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAgenteKeyPressed(evt);
            }
        });

        lblAgente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAgente.setText(bundle.getString("CONTAC.FORM.FACTURACION.AGENTE")); // NOI18N

        btnCalcular.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/calculator.png")));
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnBuscarAgente.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarAgente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAgenteActionPerformed(evt);
            }
        });

        btnEditarFechaRegistro.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/edit.png")));
        btnEditarFechaRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarFechaRegistroActionPerformed(evt);
            }
        });

        btnBuscarTasaCambio.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarTasaCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTasaCambioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmFacturaClienteLayout = new javax.swing.GroupLayout(frmFacturaCliente);
        frmFacturaCliente.setLayout(frmFacturaClienteLayout);
        frmFacturaClienteLayout.setHorizontalGroup(
            frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                        .addComponent(txtNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbTipoFactura, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                        .addComponent(lblAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                        .addComponent(lblAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBuscarAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblFechaAlta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTasaCambio, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTasaCambio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dtpFechaAlta, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEditarFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBuscarTasaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTasaCambio1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                        .addGap(426, 426, 426)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        frmFacturaClienteLayout.setVerticalGroup(
            frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                        .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                        .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtpFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscarAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAgente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(frmFacturaClienteLayout.createSequentialGroup()
                        .addComponent(btnEditarFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscarTasaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTasaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTasaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTasaCambio1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(frmFacturaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        add(frmFacturaCliente, java.awt.BorderLayout.CENTER);

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        header.setTitle(bundle.getString("CONTAC.FORM.FACTURACION.TITTLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed

        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCodigo.getText().equals("")) {

                    //Buscar producto por su codigo
                    Producto producto = controller.buscarProducto(txtCodigo.getText());

                    //Setting valores de producto
                    txtCodigo.setText(producto.getCodigo());
                    txtNombreProducto.setText(producto.getNombre());
                    txtPrecio.setText(TextUtil.formatCurrency(producto.getCostoUND().multiply(controller.getTasaCambio())
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
                    txtCantidad.requestFocusInWindow();

                    //Actualizar producto selected
                    productoSelected = producto;
                    renglonSelected = 0;
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed

        //--<Open Busqueda producto JDialog for selecting clasificador>
        Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtNombreProducto.setText(producto.getNombre());
            txtPrecio.setText(TextUtil.formatCurrency(producto.getCostoUND().doubleValue()));
            txtCantidad.requestFocusInWindow();

            //Updating producto selected
            productoSelected = producto;
            renglonSelected = 0;
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyChar()) {

                if (!txtCantidad.getText().equals("") && !txtPrecio.getText().equals("") && productoSelected != null) {

                    //Setting descuento default si valor esta vacio
                    if (txtDescuento.getText().equals(""))
                        txtDescuento.setText(TextUtil.formatCurrency(0));

                    BigDecimal precioBruto = new BigDecimal(txtPrecio.getText());
                    BigDecimal porcDescuento = new BigDecimal(txtDescuento.getText());

                    //Agregar articulo
                    controller.agregarArticulo(productoSelected, renglonSelected, Integer.parseInt(txtCantidad.getText()), precioBruto, porcDescuento);

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblArticulosFactura.getModel()).fireTableDataChanged();

                    //Limpiar datos
                    txtCodigo.setText("");
                    txtNombreProducto.setText("");
                    txtPrecio.setText("");
                    txtCantidad.setText("");
                    txtDescuento.setText("");
                    productoSelected = null;

                    //Ir ultimo registro tabla
                    tblArticulosFactura.scrollRectToVisible(tblArticulosFactura.getCellRect(tblArticulosFactura.getRowCount() - 1, 0, true));

                    //Mostrar Subtotal de la factura
                    txtSubtotal.setText(TextUtil.formatCurrency(controller.getMontoAntesImpuesto().doubleValue()));

                    //Request focus in window
                    txtCodigo.setEditable(true);
                    txtCodigo.requestFocusInWindow();

                } else {
                    //Show error message
                    JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                            messageBundle.getString("CONTAC.FORM.MSG.ERROR.DATOSARTICULO"));
                    //Request focus in window
                    txtCantidad.requestFocusInWindow();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtDescuentoKeyPressed

    private void txtSubtotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubtotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalKeyPressed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        //Open pnl total de factura
        new pnlTotalFacturaCliente(mdi, controller, true);

        //Actualizar listado de articulos ingresados
        ((BeanTableModel) tblArticulosFactura.getModel()).fireTableDataChanged();

        //Mostrar Subtotal de la factura
        txtSubtotal.setText(TextUtil.formatCurrency(controller.getMontoAntesImpuesto().doubleValue()));

    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        //Open pnl busqueda de clientes
        Cliente cliente = new pnlBusquedaCliente(mdi, true).getClienteSelected();

        if (cliente != null) {
            txtCodigoCliente.setText(String.valueOf(cliente.getCodigo()));
            txtNombreCliente.setText(cliente.getNombreComercial());

            //Setting cliente selected
            clienteSelected = cliente;
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnBuscarAgenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAgenteActionPerformed

        //Open pnl busqueda agentes de ventas
        AgenteVentas agenteVentas = new AgentesVentasPnl(mdi, true).getAgenteVentasSelected();

        if (agenteVentas != null) {
            txtAgente.setText(agenteVentas.toString());
        }

        //Setting agente ventas selected
        agenteVentasSelected = agenteVentas;

    }//GEN-LAST:event_btnBuscarAgenteActionPerformed

    private void btnEditarFechaRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarFechaRegistroActionPerformed

        try {

            //Evaluar si usuario tiene permiso de edicion de fecha de factura
            controller.editarFechaFactura();

            //Habilitar campo de fecha de factura
            dtpFechaAlta.setEnabled(true);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                    e.getMessage());
        }

    }//GEN-LAST:event_btnEditarFechaRegistroActionPerformed

    private void btnBuscarTasaCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTasaCambioActionPerformed

        //Open pnl busqueda tasas de cambio
        TasaCambio tasaCambio = new TasasCambioPnl(mdi, true).getTasaCambioSelected();

        if (tasaCambio != null)
            txtTasaCambio.setText(tasaCambio.getTasaConversion().toString());

        //Setting tasa cambio selected
        tasaCambioSelected = tasaCambio;

    }//GEN-LAST:event_btnBuscarTasaCambioActionPerformed

    private void txtAgenteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAgenteKeyPressed

        try {

            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {

                if (txtAgente.getText().equals("")) {
                    btnBuscarAgente.doClick();
                }

                if (!txtAgente.getText().equals("")) {

                    //Buscar agente de ventas
                    AgenteVentas agenteVentas = controller.buscarAgenteVentasPorCodigo(Long.parseLong(txtAgente.getText()));

                    if (agenteVentas != null) {
                        agenteVentasSelected = agenteVentas;
                        txtAgente.setText(agenteVentas.toString());

                        //Request focus tasa de cambio
                        txtTasaCambio.requestFocusInWindow();
                    }
                }
            }

        } catch (NumberFormatException e) {
            //Do not show error message for Number format exception
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtAgenteKeyPressed

    private void txtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyPressed

        try {

            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {

                if (txtCodigoCliente.getText().equals("")) {
                    btnBuscarCliente.doClick();
                }

                if (!txtCodigoCliente.getText().equals("")) {

                    //Buscar cliente
                    Cliente cliente = controller.buscarClientePorCodigo(Long.parseLong(txtCodigoCliente.getText()));

                    if (cliente != null) {
                        clienteSelected = cliente;
                        txtNombreCliente.setText(cliente.getNombreComercial());

                        //Request focus
                        txtAgente.requestFocusInWindow();

                    }
                }
            }

        } catch (NumberFormatException e) {
            //Do not show error message for Number format exception
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }

    }//GEN-LAST:event_txtCodigoClienteKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Init controller data
        controller.init();
        //Init formulario
        initValues();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {

            //Valida datos formulario
            validaDatosForm();

            //Setting values
            controller.setTipoFactura((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject());
            controller.setAlmacen((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject());
            controller.setFechaAlta(dtpFechaAlta.getDate());
            controller.setCliente(clienteSelected);
            controller.setAgenteVentas(agenteVentasSelected);
            if (tasaCambioSelected != null) {
                controller.setTasaCambio(tasaCambioSelected.getTasaConversion());
                controller.setMoneda(tasaCambioSelected.getMonedaConversion());
            }

            if (!controller.is_edit()) {
                
                //Guardar factura comercial
                controller.crearFactura();
                
                //Actualizar numero de factura generado
                txtNoFactura.setText(controller.getFactura().getNoDocumento() + "");

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.FACTURACLIENTE.INGRESO.EXITOSO"));
                
            } else {
                
                //Modificar factura comercial
                controller.modificarFactura();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.FACTURACLIENTE.MODIFICACION.EXITOSO"));
                
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void validaDatosForm() throws Exception { //Validate datos form

        //Tipo de factura
        if (cmbTipoFactura.getSelectedIndex() == -1) {
            //Request focus
            cmbTipoFactura.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.TIPOFACTURA"));
        }
        
        //Almacen de registro
        if (cmbAlmacen.getSelectedIndex() == -1) {
            //Request focus
            cmbAlmacen.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.ALMACEN"));
        }
            
        //Fecha alta factura
        if (dtpFechaAlta.getDate() == null){
            //Request focus
            dtpFechaAlta.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.FECHAALTA"));
        }

        //Cliente
        if (txtCodigoCliente.getText().equals("") && txtNombreCliente.getText().equals("")) {
            //Request focus
            txtCodigoCliente.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.CLIENTE"));
        }

        //Agente de ventas
        if (txtAgente.getText().equals("")) {
            //Request focus
            txtAgente.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.AGENTE"));
        }

        //Tasa de cambio
        if (txtTasaCambio.getText().equals("")) {
            //Request focus
            txtTasaCambio.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.TASACAMBIO"));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarAgente;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnBuscarTasaCambio;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditarFechaRegistro;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JComboBox cmbTipoFactura;
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private javax.swing.JPanel frmFacturaCliente;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAgente;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblNoFactura;
    private javax.swing.JLabel lblTasaCambio;
    private javax.swing.JLabel lblTasaCambio1;
    private javax.swing.JLabel lblTipoFactura;
    private org.jdesktop.swingx.JXTable tblArticulosFactura;
    private javax.swing.JTextField txtAgente;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtNoFactura;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTasaCambio;
    // End of variables declaration//GEN-END:variables

    private Cliente clienteSelected;
    private AgenteVentas agenteVentasSelected;
    private TasaCambio tasaCambioSelected;
    private Producto productoSelected;
    private long renglonSelected;

    private BeanTableModel<ArticuloFactura> articuloBeanTableModel;
}
