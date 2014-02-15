
/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlOrdenCompra.java
 *
 * Created on 01/02/2014, 10:23:52 AM
 */
package contac.administracion.proveedor.app;
import contac.administracion.controller.OrdenCompraController;
import contac.commons.app.BaseController;
import contac.commons.components.pnlBusquedaProveedor;
import contac.commons.components.pnlBusquedaProducto;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.models.tables.BeanTableModel;
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
public class pnlOrdenCompra extends GenericPanel {

    //Apache Log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenCompra.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/requisicion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private OrdenCompraController controller;

    /**
     * Creates new form pnlOrdenCompra
     */
    public pnlOrdenCompra(GenericFrame frame) {

        //Call super constructor
        super(frame, "ordenCompra", "Orden Compra", true, "contac/requisicion/app/mensajes/Mensajes",
                new Locale("es", "NI"));

        //Init controller
        controller = new OrdenCompraController();

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
     * Create a new formulario Orden de Compra
     *
     * @param frame,      GenericFrame
     * @param controller, BaseController
     */
    public pnlOrdenCompra(GenericFrame frame, BaseController controller) {

        //Call super constructor
        super(frame, "ordenCompra", "Orden Compra", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NI"));

        //Init controller
        this.controller = (OrdenCompraController) controller;

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
//        cmbTipoFactura.setModel(new TipoFacturaComboBoxModel(TiposFactura.values()));

        txtNoOrdenCompra.setEditable(false);
        dtpFechaAlta.setEnabled(false);

        if (!controller.is_edit()) {

           // cmbTipoFactura.setEnabled(true);
            //cmbAlmacen.setEnabled(false);
            txtCodigoProveedor.setEditable(true);

           // btnBuscarAgente.setEnabled(true);
            btnBuscarProveedor.setEnabled(true);
            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        }

        if (controller.is_edit()) {

            //cmbTipoFactura.setEnabled(true);
            txtCodigoProveedor.setEditable(false);

            btnBuscarProveedor.setEnabled(false);
            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));
        }

        //Fecha de alta Orden de Compra
        dtpFechaAlta.setFormats("dd/MM/yyyy");
        dtpFechaAlta.setDate(controller.getFechaAlta());

        dtpFechaRequerida.setFormats("dd/MM/yyyy");
        dtpFechaRequerida.setDate(controller.getFechaAlta());

        //Seleccionar Proveedor
        if (controller.getProveedor() != null) {
            txtCodigoProveedor.setText(controller.getProveedor().getCodigo() + "");
            txtNombreProveedor.setText(controller.getProveedor().getRazonSocial());
        } else {
            txtCodigoProveedor.setText("");
            txtNombreProveedor.setText("");
        }

        //Seleccionar Descripcion de la Compra
        if (controller.getDescripcionCompra() != null){
            txtDescripcionCompra.setText(controller.getDescripcionCompra());
        } else {
            txtDescripcionCompra.setText("");
        }
         //TODO crear un nuevo numero de factura
        //Numero de Orden de Compra
        /*if (controller.getOrdenCompra().getId() > 0) {
            txtNoOrdenCompra.setText(String.valueOf(controller.getOrdenCompra().getId()));
        } else {
            txtNoOrdenCompra.setText("");
        }*/


        //Seleccionar Número de Referencia
        if (controller.getNumeroReferencia() != null){
            txtReferenciaProveedor.setText(String.valueOf(controller.getNumeroReferencia()));
        } else {
            txtReferenciaProveedor.setText("");
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

        articuloBeanTableModel = new BeanTableModel<ArticuloOrdenCompra>(ArticuloOrdenCompra.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulosOrdenCompra.setModel(articuloBeanTableModel);
        tblArticulosOrdenCompra.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulosOrdenCompra.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Renglon",
                "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update", "Exento", "Costo", "Costo Total", "Retencion Fuente",
                "Cantidad Anterior", "Retencion Municipal", "Porc Retencion Fuente", "Porc Retencion Municipal",
                "Porc Iva", "Porc Descuento", "Precio Promocion", "Precio Neto", "Iva", "No Orden Compra", "Orden Compra"};

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

    /**
     * This method is called from within the constructor to enabled controls only for roles access
     * credential grant to users
     */
    public void initValuesAccess() {

        try {
             if (controller.checkUserInRole(Roles.ROLFACTURACIONADMIN.toString())) {
                txtNoOrdenCompra.setEnabled(true);
                //cmbAlmacen.setEnabled(true);
                dtpFechaAlta.setEnabled(true);
            }
        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    public void registerListeners() {

        txtCodigoProveedor.addKeyListener(new KeyAdapter() {
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

        tblArticulosOrdenCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Getting row selected
                int rowSelected = tblArticulosOrdenCompra.getSelectedRow();
                ArticuloOrdenCompra articuloOrdenCompra = (ArticuloOrdenCompra) ((BeanTableModel) tblArticulosOrdenCompra.getModel()).getRow(rowSelected);

                //Setting values articulo para modificar
                txtCodigo.setText(articuloOrdenCompra.getProducto().getCodigo());
                txtNombreProducto.setText(articuloOrdenCompra.getProducto().getNombre());
                txtCantidad.setText(String.valueOf(articuloOrdenCompra.getCantidad()));
                txtPrecio.setText(articuloOrdenCompra.getPrecioBruto().toString());
                txtDescuento.setText(articuloOrdenCompra.getPorcDescuento().toString());

                //Setting values to process
                productoSelected = articuloOrdenCompra.getProducto();
                renglonSelected = articuloOrdenCompra.getRenglon();

                //Setting codigo del producto editable
                txtCodigo.setEditable(false);

                //Setting request focus
                txtCantidad.requestFocusInWindow();
            }
        });

        btnBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarProductoActionPerformed(e);
            }
        });

        btnBuscarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarClienteActionPerformed(e);
            }
        });

        btnEditarFechaRequerida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEditarFechaRequeridaActionPerformed(e);
            }
        });

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCalcularActionPerformed(e);
            }
        });

        btnCompletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
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
        header.setTitle(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //Adding header to panel
        this.add(header, BorderLayout.NORTH);

        //***************************************************************************************
        //Init Header Components Panel
        //***************************************************************************************
        lblNoRequisicion = new JLabel(messageBundle.getString("CONTAC.FORM.PROVEEDOR.NOORDENCOMPRA"));
        lblNoRequisicion.setHorizontalAlignment(JLabel.LEFT);

        lblFechaAlta = new JLabel(messageBundle.getString("CONTAC.FORM.PROVEEDOR_FECHA_ORDENCOMPRA"));
        lblFechaAlta.setHorizontalAlignment(JLabel.LEFT);

        lblFechaRequerida = new JLabel(messageBundle.getString("CONTAC.FORM.PROVEEDOR_FECHA_REQUERIDA"));
        lblFechaRequerida.setHorizontalAlignment(JLabel.LEFT);

        lblFacturaAsociada = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA_FACTURA_PROVEEDOR"));
        lblFacturaAsociada.setHorizontalAlignment(JLabel.LEFT);

        lblProveedor = new JLabel(messageBundle.getString("CONTAC.FORM.PROVEEDOR_NOMBRE_ORDENCOMPRA"));
        lblProveedor.setHorizontalAlignment(JLabel.LEFT);

        lblDescripcion = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA_DESCRIPCION"));
        lblDescripcion.setHorizontalAlignment(JLabel.LEFT);

        txtNoOrdenCompra = new JTextField();
        txtNoOrdenCompra.setHorizontalAlignment(JLabel.RIGHT);
        txtNoOrdenCompra.setEditable(false);

        dtpFechaAlta = new JXDatePicker(new Date());
        dtpFechaAlta.setEnabled(false);

        dtpFechaRequerida = new JXDatePicker(new Date());

        txtCodigoProveedor = new JTextField();

        txtNombreProveedor = new JTextField();
        txtNombreProveedor.setEditable(false);

        txtReferenciaProveedor = new JTextField();

        txtDescripcionCompra = new JTextField();

        btnEditarFechaRegistro = new JButton();
        btnEditarFechaRegistro.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/edit.png")));

        btnEditarFechaRequerida = new JButton();
        btnEditarFechaRequerida.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/edit.png")));

        btnBuscarProveedor = new JButton();
        btnBuscarProveedor.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));

        JPanel pnlHeaderComp = new JPanel(new XYLayout());
        pnlHeaderComp.add(lblNoRequisicion, new XYConstraints(5, 5, 99, 23));
        pnlHeaderComp.add(txtNoOrdenCompra, new XYConstraints(105, 5, 100, 23));

        pnlHeaderComp.add(lblFacturaAsociada, new XYConstraints(215, 5, 125, 23));
        pnlHeaderComp.add(txtReferenciaProveedor, new XYConstraints(350, 5, 230, 23));

        pnlHeaderComp.add(lblFechaRequerida, new XYConstraints(630,5,100,23));
        pnlHeaderComp.add(dtpFechaRequerida, new XYConstraints(740,5,165,23));
        pnlHeaderComp.add(btnEditarFechaRequerida, new XYConstraints(915,5,30,23));

        pnlHeaderComp.add(lblFechaAlta, new XYConstraints(955,5,110,23));
        pnlHeaderComp.add(dtpFechaAlta, new XYConstraints(1030,5,140,23));
        pnlHeaderComp.add(btnEditarFechaRegistro, new XYConstraints(1184, 5, 30, 23));

        pnlHeaderComp.add(lblProveedor, new XYConstraints(5, 33, 90, 23));
        pnlHeaderComp.add(txtCodigoProveedor, new XYConstraints(105, 33, 100, 23));
        pnlHeaderComp.add(txtNombreProveedor, new XYConstraints(210, 33, 370, 23));
        pnlHeaderComp.add(btnBuscarProveedor, new XYConstraints(585, 33, 30, 23));
        pnlHeaderComp.add(lblDescripcion, new XYConstraints(630,33,100,23));
        pnlHeaderComp.add(txtDescripcionCompra, new XYConstraints(740,33,488,23));

        //***************************************************************************************
        //Init Articulos Table Panel
        //***************************************************************************************
        tblArticulosOrdenCompra = new org.jdesktop.swingx.JXTable();
        JScrollPane spArticulos = new JScrollPane();
        spArticulos.getViewport().add(tblArticulosOrdenCompra);

        JPanel pnlArticulos = new JPanel(new BorderLayout());
        pnlArticulos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlArticulos.add(spArticulos, BorderLayout.CENTER);

        //***************************************************************************************
        //Init Add Articulo Actions panel
        //***************************************************************************************
        txtCodigo = new JTextField();
        txtCodigo.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.CODIGO"));
        txtCodigo.setPreferredSize(new Dimension(120, 23));

        btnBuscarProducto = new JButton();
        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));

        txtNombreProducto = new JTextField();
        txtNombreProducto.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.NOMBREPRODUCTO"));
        txtNombreProducto.setPreferredSize(new Dimension(250, 23));

        txtCantidad = new JTextField();
        txtCantidad.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.CANTIDAD"));
        txtCantidad.setPreferredSize(new Dimension(90, 23));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);

        txtPrecio = new JTextField();
        txtPrecio.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.PRECIO"));
        txtPrecio.setPreferredSize(new Dimension(90, 23));
        txtPrecio.setHorizontalAlignment(JTextField.RIGHT);

        txtDescuento = new JTextField();
        txtDescuento.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.DESCUENTO"));
        txtDescuento.setPreferredSize(new Dimension(90, 23));
        txtDescuento.setHorizontalAlignment(JTextField.RIGHT);

        lblSubtotal = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.SUBTOTAL"));

        txtSubtotal = new JTextField();
        txtSubtotal.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.SUBTOTAL"));
        txtSubtotal.setPreferredSize(new Dimension(120, 23));
        txtSubtotal.setHorizontalAlignment(JTextField.RIGHT);
        txtSubtotal.setEditable(false);

        btnCalcular = new JButton();
        btnCalcular.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/calculator.png")));

        btnCompletar = new javax.swing.JButton(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA_COMPLETAR"));
        btnCompletar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/engranaje-rojo.png")));

        btnCompletar.setPreferredSize(new Dimension(160, 23));

        btnEnviarCorreo = new javax.swing.JButton(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA_ENVIAR_CORREO"));
        btnEnviarCorreo.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/enviarCorreo.png")));

        btnEnviarCorreo.setPreferredSize(new Dimension(154, 23));

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
        pnlAddProducto.add(btnCompletar);
        pnlAddProducto.add(btnEnviarCorreo);

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
            txtPrecio.setText(TextUtil.formatCurrency(producto.getPrecioESTANDAR().multiply(controller.getTasaCambio())
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
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

                    BigDecimal precioBruto = new BigDecimal(TextUtil.parseCurrency(txtPrecio.getText()));
                    BigDecimal porcDescuento = new BigDecimal(txtDescuento.getText());

                    //Agregar articulo
                    controller.agregarArticulo(productoSelected, renglonSelected, Integer.parseInt(txtCantidad.getText()),
                            precioBruto, porcDescuento);

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblArticulosOrdenCompra.getModel()).fireTableDataChanged();

                    //Limpiar datos
                    txtCodigo.setText("");
                    txtNombreProducto.setText("");
                    txtPrecio.setText("");
                    txtCantidad.setText("");
                    txtDescuento.setText("");
                    productoSelected = null;

                    //Ir ultimo registro tabla
                    tblArticulosOrdenCompra.scrollRectToVisible(tblArticulosOrdenCompra.getCellRect(tblArticulosOrdenCompra.getRowCount() - 1, 0, true));

                    //Mostrar Subtotal de la Orden de Compra
                    txtSubtotal.setText(TextUtil.formatCurrency(controller.getMontoAntesImpuesto().doubleValue()));

                    //Request focus in window
                    txtCodigo.setEditable(true);
                    txtCodigo.requestFocusInWindow();

                } else {
                    //Show error message
                    JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                            messageBundle.getString("CONTAC.FORM.MSG.COMPRAS_ERROR.DATOSARTICULO"));
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

    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        //Open pnl busqueda proveedores

        Proveedor proveedor = new pnlBusquedaProveedor(mdi, true).getProveedorSelected();

        if (proveedor != null) {
            txtCodigoProveedor.setText(String.valueOf(proveedor.getCodigo()));
            txtNombreProveedor.setText(proveedor.getNombreComercial());

            //Setting cliente selected
            proveedorSelected = proveedor;
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnEditarFechaRequeridaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarFechaRegistroActionPerformed

      /*  try {

            //Evaluar si usuario tiene permiso de edicion de fecha de Orden de Compra
            controller.editarDatosFactura();

            //Habilitar campo de fecha de Orden de Compra
            dtpFechaRequerida.setEnabled(true);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                    e.getMessage());
        }*/

    }//GEN-LAST:event_btnEditarFechaRegistroActionPerformed


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
            controller.setNoOrdenCompra(txtNoOrdenCompra.getText().equals("") ? 0 : Integer.parseInt(txtNoOrdenCompra.getText()));
            controller.setFechaAlta(dtpFechaAlta.getDate());
            controller.setFechaRequerida(dtpFechaRequerida.getDate());
            controller.setProveedor(proveedorSelected);
            controller.setDescripcionCompra(txtDescripcionCompra.getText());
            controller.setNumeroReferencia(Integer.parseInt((txtReferenciaProveedor.getText())));
            controller.setNombreProveedor(txtNombreProveedor.getText());
            if (tasaCambioSelected != null) {
                controller.setTasaCambio(tasaCambioSelected.getTasaConversion());
                controller.setMoneda(tasaCambioSelected.getMonedaConversion());
            }

            if (!controller.is_edit()) {

                //Guardar Orden de Compra
                controller.crearOrdenCompra();

                //Actualizar numero de Orden de Compra generado
                txtNoOrdenCompra.setText(String.valueOf(controller.getOrdenCompra().getId()));

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.COMPRASINGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.ORDENCOMPRA_GUARDA.INGRESO.EXITOSO"));

            } else {

                //Modificar Orden de Compra
                controller.modificarOrdenCompra();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.ORDENCOMPRA_MODIFICACION.EXITOSO"));

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void validaDatosForm() throws Exception { //Validate datos form Orden Compra
        //Fecha alta OrdenCompra
        if (dtpFechaAlta.getDate() == null) {
            //Request focus
            dtpFechaAlta.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.VALIDA.FECHAALTA"));
        }
        //Fecha Requerida OrdenCompra
        if (dtpFechaRequerida.getDate() == null) {
            //Request focus
            dtpFechaRequerida.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENCOMPRA.VALIDA.FECHAREQUERIDA"));
        }
        //Proveedor
        if (txtCodigoProveedor.getText().equals("") && txtCodigoProveedor.getText().equals("")) {
            //Request focus
            txtCodigoProveedor.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.COMPRA.VALIDA.CLIENTE"));
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCompletar;
    private javax.swing.JButton btnEnviarCorreo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditarFechaRegistro;
    private javax.swing.JButton btnEditarFechaRequerida;
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private org.jdesktop.swingx.JXDatePicker dtpFechaRequerida;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblFechaRequerida;
    private javax.swing.JLabel lblFacturaAsociada;
    private javax.swing.JLabel lblNoRequisicion;
    private javax.swing.JLabel lblSubtotal;
    private org.jdesktop.swingx.JXTable tblArticulosOrdenCompra;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoProveedor;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtNoOrdenCompra;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtReferenciaProveedor;
    private javax.swing.JTextField txtDescripcionCompra;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
    private Proveedor proveedorSelected;
    private TasaCambio tasaCambioSelected;
    private Producto productoSelected;
    private long renglonSelected;

    private BeanTableModel<ArticuloOrdenCompra> articuloBeanTableModel;
}