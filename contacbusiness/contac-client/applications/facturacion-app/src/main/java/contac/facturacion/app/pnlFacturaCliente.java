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
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
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
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXHeader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlFacturaCliente extends GenericPanel {

    //Apache Log4j
    private static final Logger logger = Logger.getLogger(pnlFacturaCliente.class);

    //Message resource bundle
    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private FacturaClienteController controller;

    /**
     * Creates new form pnlFacturaCompra
     */
    public pnlFacturaCliente(GenericFrame frame) throws Exception {

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
        cmbTipoFactura.setModel(new TipoFacturaComboBoxModel(TiposFactura.values()));
        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        txtNoFactura.setEditable(false);
        dtpFechaAlta.setEnabled(false);

        if (!controller.is_edit()) {

            cmbTipoFactura.setEnabled(true);
            cmbAlmacen.setEnabled(false);
            txtCodigoCliente.setEditable(true);

            btnBuscarAgente.setEnabled(true);
            btnBuscarCliente.setEnabled(true);
            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        }

        if (controller.is_edit()) {

            cmbTipoFactura.setEnabled(true);
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
                "Cantidad Anterior", "Retencion Municipal", "Porc Retencion Fuente", "Porc Retencion Municipal",
                "Porc Iva", "Porc Descuento", "Precio Promocion", "Precio Neto", "Iva", "No Factura", "Codigo Fabricante", "Descuento"};

        for (String columnLabel : articuloColumnRemove) {
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnLabel)));
        }

        //Ordering table columns
        columnModel.moveColumn(1, 0); //Codigo producto
        columnModel.moveColumn(3, 1); //Nombre producto
        columnModel.moveColumn(3, 2); //Codigo de fabricante
        columnModel.moveColumn(6, 3); //Unidad de medida
        columnModel.moveColumn(6, 5); //Precio bruto

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
        //columnModel.getColumn(7).setCellRenderer(decimalFormatRenderer);
    }

    /**
     * This method is called from within the constructor to enabled controls only for roles access
     * credential grant to users
     */
    public void initValuesAccess() {

        try {
            if (controller.checkUserInRole(Roles.ROLFACTURACIONADMIN.toString())) {
                txtNoFactura.setEnabled(true);
                cmbAlmacen.setEnabled(true);
                dtpFechaAlta.setEnabled(true);
            }
        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    public void registerListeners() {

        txtCodigoCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtCodigoClienteKeyPressed(e);
            }
        });

        txtAgente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtAgenteKeyPressed(e);
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

            @Override
            public void keyPressed(KeyEvent e) {
                txtCodigoKeyPressed(e);
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

            @Override
            public void keyPressed(KeyEvent e) {
                txtDescuentoKeyPressed(e);
            }
        });

        txtSubtotal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                txtSubtotalKeyPressed(e);
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
                /*txtPrecio.setEnabled(false);*/
                txtDescuento.setText(articuloFactura.getPorcDescuento().toString());

                //Setting values to process
                productoSelected = articuloFactura.getProducto();
                renglonSelected = articuloFactura.getRenglon();

                //Setting codigo del producto editable
                txtCodigo.setEditable(false);

                //Setting request focus
                txtCantidad.requestFocusInWindow();

                //Check if user has permission to update price
                boolean valid = false;
                try {
                    valid = controller.accessToEditPrice();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if(valid == true) {
                    txtPrecio.setEditable(true);
                }

            }
        });

        btnBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnBuscarProductoActionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarClienteActionPerformed(e);
            }
        });

        btnBuscarAgente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarAgenteActionPerformed(e);
            }
        });

        btnEditarFechaRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEditarFechaRegistroActionPerformed(e);
            }
        });

        btnEditarNoFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEditarNoFacturaActionPerformed(e);
            }
        });

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCalcularActionPerformed(e);
            }
        });

        btnRegistroRapido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRegistroRapidoActionPerformed(e);
            }
        });

        btnBuscarTasaCambio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarTasaCambioActionPerformed(e);
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAceptarActionPerformed(e);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelarActionPerformed(e);
            }
        });
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        //Setting default Layout
        this.setLayout(new BorderLayout());

        //***************************************************************************************
        //Init Header Panel
        //***************************************************************************************
        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.FACTURACION.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //Adding header to panel
        this.add(header, BorderLayout.NORTH);

        //***************************************************************************************
        //Init Header Components Panel
        //***************************************************************************************
        lblNoFactura = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.NOFACTURA"));
        lblNoFactura.setHorizontalAlignment(JLabel.LEFT);

        lblTipoFactura = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.TIPOFACTURA"));
        lblTipoFactura.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacen = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(JLabel.LEFT);

        lblFechaAlta = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.FECHAALTA"));
        lblFechaAlta.setHorizontalAlignment(JLabel.LEFT);

        lblCliente = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.CLIENTE"));
        lblCliente.setHorizontalAlignment(JLabel.LEFT);

        lblAgente = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.AGENTE"));
        lblAgente.setHorizontalAlignment(JLabel.LEFT);

        lblTasaCambio = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.TASACAMBIO"));
        lblTasaCambio.setHorizontalAlignment(JLabel.LEFT);

        txtNoFactura = new JTextField();
        txtNoFactura.setHorizontalAlignment(JLabel.RIGHT);
        txtNoFactura.setEditable(false);

        cmbTipoFactura = new JComboBox();
        cmbAlmacen = new JComboBox();

        dtpFechaAlta = new JXDatePicker(new Date());
        dtpFechaAlta.setEnabled(false);

        txtCodigoCliente = new JTextField();

        txtNombreCliente = new JTextField();
        txtNombreCliente.setEditable(false);

        txtAgente = new JTextField();

        txtTasaCambio = new JTextField();
        txtTasaCambio.setEditable(false);

        btnEditarNoFactura = new JButton();
        btnEditarNoFactura.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/edit.png")));

        btnEditarFechaRegistro = new JButton();
        btnEditarFechaRegistro.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/edit.png")));

        btnBuscarCliente = new JButton();
        btnBuscarCliente.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));

        btnBuscarAgente = new JButton();
        btnBuscarAgente.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));

        btnBuscarTasaCambio = new JButton();
        btnBuscarTasaCambio.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));

        //Create Panel Header Component

        JPanel pnlHeaderComp = new JPanel(new XYLayout());
        pnlHeaderComp.add(lblNoFactura, new XYConstraints(5, 5, 90, 23));
        pnlHeaderComp.add(txtNoFactura, new XYConstraints(100, 5, 160, 23));
        pnlHeaderComp.add(btnEditarNoFactura, new XYConstraints(265, 5, 30, 23));
        pnlHeaderComp.add(lblTipoFactura, new XYConstraints(300, 5, 90, 23));
        pnlHeaderComp.add(cmbTipoFactura, new XYConstraints(395, 5, 160, 23));
        pnlHeaderComp.add(lblAlmacen, new XYConstraints(560, 5, 90, 23));
        pnlHeaderComp.add(cmbAlmacen, new XYConstraints(655, 5, 200, 23));
        pnlHeaderComp.add(lblFechaAlta, new XYConstraints(860, 5, 90, 23));
        pnlHeaderComp.add(dtpFechaAlta, new XYConstraints(955, 5, 160, 23));
        pnlHeaderComp.add(btnEditarFechaRegistro, new XYConstraints(1120, 5, 30, 23));
        pnlHeaderComp.add(lblCliente, new XYConstraints(5, 33, 90, 23));
        pnlHeaderComp.add(txtCodigoCliente, new XYConstraints(100, 33, 100, 23));
        pnlHeaderComp.add(txtNombreCliente, new XYConstraints(205, 33, 315, 23));
        pnlHeaderComp.add(btnBuscarCliente, new XYConstraints(525, 33, 30, 23));
        pnlHeaderComp.add(lblAgente, new XYConstraints(560, 33, 90, 23));
        pnlHeaderComp.add(txtAgente, new XYConstraints(655, 33, 165, 23));
        pnlHeaderComp.add(btnBuscarAgente, new XYConstraints(825, 33, 30, 23));
        pnlHeaderComp.add(lblTasaCambio, new XYConstraints(860, 33, 90, 23));
        pnlHeaderComp.add(txtTasaCambio, new XYConstraints(955, 33, 160, 23));
        pnlHeaderComp.add(btnBuscarTasaCambio, new XYConstraints(1120, 33, 30, 23));

        //***************************************************************************************
        //Init Articulos Table Panel
        //***************************************************************************************
        tblArticulosFactura = new org.jdesktop.swingx.JXTable();
        JScrollPane spArticulos = new JScrollPane();
        spArticulos.getViewport().add(tblArticulosFactura);

        JPanel pnlArticulos = new JPanel(new BorderLayout());
        pnlArticulos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlArticulos.add(spArticulos, BorderLayout.CENTER);

        //***************************************************************************************
        //Init Add Articulo Actions panel
        //***************************************************************************************
        txtCodigo = new JTextField();
        txtCodigo.setToolTipText(messageBundle.getString("CONTAC.FORM.FACTURACION.CODIGO"));
        txtCodigo.setPreferredSize(new Dimension(120, 23));

        btnBuscarProducto = new JButton();
        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));

        txtNombreProducto = new JTextField();
        txtNombreProducto.setToolTipText(messageBundle.getString("CONTAC.FORM.FACTURACION.NOMBREPRODUCTO"));
        txtNombreProducto.setPreferredSize(new Dimension(250, 23));

        txtCantidad = new JTextField();
        txtCantidad.setToolTipText(messageBundle.getString("CONTAC.FORM.FACTURACION.CANTIDAD"));
        txtCantidad.setPreferredSize(new Dimension(90, 23));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);

        txtPrecio = new JTextField();
        txtPrecio.setToolTipText(messageBundle.getString("CONTAC.FORM.FACTURACION.PRECIO"));
        txtPrecio.setPreferredSize(new Dimension(90, 23));
        txtPrecio.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecio.setEditable(false);

        txtDescuento = new JTextField();
        txtDescuento.setToolTipText(messageBundle.getString("CONTAC.FORM.FACTURACION.DESCUENTO"));
        txtDescuento.setPreferredSize(new Dimension(90, 23));
        txtDescuento.setHorizontalAlignment(JTextField.RIGHT);

        lblSubtotal = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALFACTURACION.SUBTOTAL"));

        lblRegistroCliente = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROCLIENTE_FACTURA"));

        txtSubtotal = new JTextField();
        txtSubtotal.setToolTipText(messageBundle.getString("CONTAC.FORM.TOTALFACTURACION.SUBTOTAL"));
        txtSubtotal.setPreferredSize(new Dimension(120, 23));
        txtSubtotal.setHorizontalAlignment(JTextField.RIGHT);
        txtSubtotal.setEditable(false);

        btnCalcular = new JButton();
        btnCalcular.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/calculator.png")));

        btnRegistroRapido = new JButton();
        btnRegistroRapido.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/user_ok_256x16.png")));

        JPanel pnlAddProducto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAddProducto.add(txtCodigo);
        pnlAddProducto.add(btnBuscarProducto);
        pnlAddProducto.add(txtNombreProducto);
        pnlAddProducto.add(txtCantidad);
        pnlAddProducto.add(txtPrecio);
        pnlAddProducto.add(txtDescuento);
        pnlAddProducto.add(lblSubtotal);
        pnlAddProducto.add(txtSubtotal);
        pnlAddProducto.add(btnCalcular);
        pnlAddProducto.add(lblRegistroCliente);
        pnlAddProducto.add(btnRegistroRapido);

        //Adding to Main Panel
        JPanel pnlMainOrder = new JPanel(new BorderLayout());
        pnlMainOrder.add(pnlHeaderComp, BorderLayout.NORTH);
        pnlMainOrder.add(pnlArticulos, BorderLayout.CENTER);
        pnlMainOrder.add(pnlAddProducto, BorderLayout.SOUTH);

        this.add(pnlMainOrder, BorderLayout.CENTER);

        //***************************************************************************************
        //Init Actions panel
        //**************************************************************************************
        btnAceptar = new javax.swing.JButton(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        btnAceptar.setPreferredSize(new Dimension(90, 23));

        btnCancelar = new javax.swing.JButton(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setPreferredSize(new Dimension(90, 23));

        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlAction.setBorder(new EtchedBorder());
        pnlAction.add(btnAceptar);
        pnlAction.add(btnCancelar);

        this.add(pnlAction, BorderLayout.SOUTH);
    }

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed

        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCodigo.getText().equals("")) {

                    //Buscar producto por su codigo
                    Producto producto = controller.buscarProducto(txtCodigo.getText());

                    //Setting valores de producto
                    txtCodigo.setText(producto.getCodigo());
                    txtNombreProducto.setText(producto.getNombre());
                    txtPrecio.setText(TextUtil.formatCurrency(producto.getPrecioESTANDAR().multiply(controller.getTasaCambio())
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
                    txtCantidad.requestFocusInWindow();

                    //Actualizar producto selected
                    productoSelected = producto;
                    renglonSelected = 0;

                    //Check if user has permission to update price
                    boolean valid = false;
                    try {
                        valid = controller.accessToEditPrice();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    if(valid == true) {
                        txtPrecio.setEditable(true);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) throws Exception {//GEN-FIRST:event_btnBuscarProductoActionPerformed

        //--<Open Busqueda producto JDialog for selecting clasificador>
        Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtNombreProducto.setText(producto.getNombre());
            txtPrecio.setText(TextUtil.formatCurrency(producto.getPrecioESTANDAR().multiply(controller.getTasaCambio())
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
            txtCantidad.requestFocusInWindow();

            //Updating producto selected
            productoSelected = producto;
            renglonSelected = 0;

            //Check if user has permission to update price
            boolean valid = controller.accessToEditPrice();

            if(valid == true) {
                txtPrecio.setEditable(true);
            }
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyChar()) {

                if (!txtCantidad.getText().equals("") && !txtPrecio.getText().equals("") && productoSelected != null) {

                    //Setting max numbers of items per invoice -07-03-2015 A.Montenegro
                    if(articuloBeanTableModel.getRowCount() > 14){
                        JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                                messageBundle.getString("CONTAC.FORM.MSG.ERROR.MAXIMO"));
                    }
                    else{

                    //Setting descuento default si valor esta vacio
                    if (txtDescuento.getText().equals(""))
                        txtDescuento.setText(TextUtil.formatCurrency(0));

                    BigDecimal precioBruto = new BigDecimal(TextUtil.parseCurrency(txtPrecio.getText()));
                    BigDecimal porcDescuento = new BigDecimal(txtDescuento.getText());




                    //Agregar articulo

                    controller.agregarArticulo(productoSelected, renglonSelected, Integer.parseInt(txtCantidad.getText()),
                            precioBruto, porcDescuento);

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
                    }
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

    }//GEN-LAST:event_btnRegistroRapidoActionPerformed

    private void btnRegistroRapidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroRapidoActionPerformed
        //Open formulario de administracion de Cliente
        getMDI().getStyle().addPanel("pnlAdmonCliente", "contac.administracion.cliente.app.pnlAdmonCliente");

        //Remove this panel
        //getMDI().getStyle().removePanel(this);

    }//GEN-LAST:event_btnRegistroRapidoActionPerformed

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

    private void btnEditarNoFacturaActionPerformed(ActionEvent evt) {
        try {

            if (controller.is_edit()) {

                //Evaluar si usuario tiene permiso de edicion de no de factura
                controller.editarDatosFactura();

                //Habilitar campo de no de factura
                txtNoFactura.setEditable(true);
            }
            else{
                txtNoFactura.setEditable(true);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                    e.getMessage());
        }
    }

    private void btnEditarFechaRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarFechaRegistroActionPerformed
        try {

            //Evaluar si usuario tiene permiso de edicion de fecha de factura
            controller.editarDatosFactura();

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

        if (tasaCambio != null) {
            txtTasaCambio.setText(tasaCambio.getTasaConversion().toString());

            //Setting tasa cambio selected
            tasaCambioSelected = tasaCambio;

            //Setting tasa cambio controller
            controller.setTasaCambio(tasaCambioSelected.getTasaConversion());
        }
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
            controller.setNoFactura(txtNoFactura.getText().equals("") ? 0 : Integer.parseInt(txtNoFactura.getText()));
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
        if (dtpFechaAlta.getDate() == null) {
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
    private javax.swing.JButton btnRegistroRapido;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditarFechaRegistro;
    private javax.swing.JButton btnEditarNoFactura;
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
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblRegistroCliente;
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
