/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlOrdenEntradaInventario.java
 *
 * Created on 12-17-2011, 10:53:44 PM
 */
package contac.inventarios.movimiento.inventario.app;

import contac.commons.app.BaseController;
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
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenEntradaController;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlOrdenEntradaInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenEntradaInventario.class);

    //Message resource bundle
    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private OrdenEntradaController controller;

    /**
     * Creates new form pnlOrdenEntradaInventario
     */
    public pnlOrdenEntradaInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "OrdenIngresoInventario", "Orden de Entrada en Almacen", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new OrdenEntradaController();

        //Controller init properties
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
     * Create new formulario de orden de entrada de inventario
     *
     * @param frame,      GenericFrame
     * @param controller, BaseController
     */
    public pnlOrdenEntradaInventario(GenericFrame frame, BaseController controller) {

        //Call super constructor
        super(frame, "OrdenIngresoInventario", "Orden de Entrada en Almacen", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        this.controller = (OrdenEntradaController) controller;

        //Controller init properties
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
        cmbTipoEntrada.setModel(new TipoEntradaComboBoxModel(TiposEntrada.values()));
        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        //Setting not editabled controls
        if (controller.is_edit()) {
            cmbAlmacen.setEnabled(false);
            dtpFechaAlta.setEnabled(false);
            cmbTipoEntrada.setEnabled(false);
            txtDescripcion.setEditable(false);
        }

        //Evaluar edit datos para registrar
        if (!controller.is_edit()) {
            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        }

        if (controller.is_edit()) {
            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));
        }

        if (controller.getNoMovimiento() > 0) {
            txtNoEntrada.setText(String.valueOf(controller.getNoMovimiento()));
        } else {
            txtNoEntrada.setText("");
        }
        txtDescripcion.setText(controller.getDescripcion());
        dtpFechaAlta.setFormats("dd/MM/yyyy");
        dtpFechaAlta.setDate(controller.getFechaAlta());

        //Seleccione almacen de ingreso
        ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getAlmacen() != null) {
            AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacen.getModel();
            cmbAlmacen.setRenderer(rendererAlmacen);
            cmbAlmacen.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacen().getId()));
        } else {
            cmbAlmacen.setRenderer(rendererAlmacen);
            cmbAlmacen.setSelectedIndex(-1);
        }

        //Seleccione tipo de entrada
        ListCellRenderer rendererTipoEntrada = new ComboBoxEmptySelectionRenderer(cmbTipoEntrada, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getTipoEntrada() != null) {
            TipoEntradaComboBoxModel tipoEntradaModel = (TipoEntradaComboBoxModel) cmbTipoEntrada.getModel();
            cmbTipoEntrada.setRenderer(rendererTipoEntrada);
            cmbTipoEntrada.setSelectedItem(tipoEntradaModel.searchSelectedItem(controller.getTipoEntrada().getValue()));
        } else {
            cmbTipoEntrada.setRenderer(rendererTipoEntrada);
            cmbTipoEntrada.setSelectedIndex(-1);
        }

        //*************************************************************
        //Init values registro productos
        //*************************************************************
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtCostoUND.setText("");

        //************************************************************
        //Init articulo bean table model
        //************************************************************
        articuloBeanTableModel = new BeanTableModel<ArticuloEntrada>(ArticuloEntrada.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulosEntrada.setModel(articuloBeanTableModel);
        tblArticulosEntrada.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulosEntrada.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Orden Entrada", "Movimiento Inventario", "Renglon", "Codigo Fabricante",
                "No Documento", "Cantidad Anterior", "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update"};

        for (String columnLabel : articuloColumnRemove) {
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnLabel)));
        }

        //Ordering table columns
        columnModel.moveColumn(1, 0); //Codigo producto
        columnModel.moveColumn(5, 1); //Nombre producto
        //columnModel.moveColumn(3, 2); //Codigo fabricante
        columnModel.moveColumn(6, 4); //Unidad de medida
        
        //Setting prefered sized
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(250);

        //Adding cell renderer class
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(4).setCellRenderer(centerAlignment);

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(3).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);

        //Ir ultimo registro tabla
        tblArticulosEntrada.scrollRectToVisible(tblArticulosEntrada.getCellRect(tblArticulosEntrada.getRowCount() - 1, 0, true));
    }

    private void registerListeners() {

        //cmbAlmacen
        cmbAlmacen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    dtpFechaAlta.requestFocusInWindow();
                }
            }
        });

        //dtpFechaAlta
        dtpFechaAlta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbTipoEntrada.requestFocusInWindow();
                }
            }
        });

        //cmbTipoEntrada
        cmbTipoEntrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtDescripcion.requestFocusInWindow();
                    txtDescripcion.selectAll();
                }
            }
        });

        txtDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCodigo.requestFocusInWindow();
                    txtCodigo.selectAll();
                }
            }
        });

        //txtCodigo
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtCodigoKeyPressed(e);
            }
        });

        //txtCantidad
        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCostoUND.requestFocusInWindow();
                    txtCostoUND.selectAll();
                }
            }
        });

        //txtCostoUND
        txtCostoUND.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtCostoUNDKeyPressed(e);
            }
        });

        //Buscar Producto
        btnBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarProductoActionPerformed(e);
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
        header.setTitle(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.TITLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //Adding header to panel
        this.add(header, BorderLayout.NORTH);

        //***************************************************************************************
        //Init Header Components Panel
        //***************************************************************************************
        lblNoEntrada = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.NOENTRADA"));
        lblNoEntrada.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacen = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(JLabel.LEFT);

        lblFechaAlta = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA"));
        lblFechaAlta.setHorizontalAlignment(JLabel.LEFT);

        lblTipoEntrada = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.TIPOENTRADA"));
        lblTipoEntrada.setHorizontalAlignment(JLabel.LEFT);

        lblDescripcion = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION"));
        lblDescripcion.setHorizontalAlignment(JLabel.LEFT);

        txtNoEntrada = new JTextField();
        txtNoEntrada.setEditable(false);

        txtDescripcion = new JTextField();
        dtpFechaAlta = new JXDatePicker();
        cmbAlmacen = new JComboBox();
        cmbTipoEntrada = new JComboBox();

        //Create Panel Header Component
        JPanel pnlHeaderComp = new JPanel(new XYLayout());
        pnlHeaderComp.add(lblNoEntrada, new XYConstraints(5, 5, 90, 23));
        pnlHeaderComp.add(txtNoEntrada, new XYConstraints(100, 5, 160, 23));
        pnlHeaderComp.add(lblAlmacen, new XYConstraints(265, 5, 90, 23));
        pnlHeaderComp.add(cmbAlmacen, new XYConstraints(360, 5, 200, 23));
        pnlHeaderComp.add(lblFechaAlta, new XYConstraints(565, 5, 90, 23));
        pnlHeaderComp.add(dtpFechaAlta, new XYConstraints(660, 5, 120, 23));
        pnlHeaderComp.add(lblTipoEntrada, new XYConstraints(5, 33, 90, 23));
        pnlHeaderComp.add(cmbTipoEntrada, new XYConstraints(100, 33, 160, 23));
        pnlHeaderComp.add(lblDescripcion, new XYConstraints(265, 33, 90, 23));
        pnlHeaderComp.add(txtDescripcion, new XYConstraints(360, 33, 420, 23));

        //***************************************************************************************
        //Init Articulos Table Panel
        //***************************************************************************************
        tblArticulosEntrada = new org.jdesktop.swingx.JXTable();
        JScrollPane spArticulos = new JScrollPane();
        spArticulos.getViewport().add(tblArticulosEntrada);

        JPanel pnlArticulos = new JPanel(new BorderLayout());
        pnlArticulos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlArticulos.add(spArticulos, BorderLayout.CENTER);

        //***************************************************************************************
        //Init Add Articulo Actions panel
        //***************************************************************************************
        txtCodigo = new JTextField();
        txtCodigo.setToolTipText(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO"));
        txtCodigo.setPreferredSize(new Dimension(120, 23));

        txtNombre = new JTextField();
        txtNombre.setToolTipText(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE"));
        txtNombre.setPreferredSize(new Dimension(250, 23));

        txtCantidad = new JTextField();
        txtCantidad.setToolTipText(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD"));
        txtCantidad.setPreferredSize(new Dimension(90, 23));

        txtCostoUND = new JTextField();
        txtCostoUND.setToolTipText(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND"));
        txtCostoUND.setPreferredSize(new Dimension(90, 23));

        btnBuscarProducto = new JButton(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.setPreferredSize(new Dimension(30, 23));

        JPanel pnlAddProducto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAddProducto.add(txtCodigo);
        pnlAddProducto.add(btnBuscarProducto);
        pnlAddProducto.add(txtNombre);
        pnlAddProducto.add(txtCantidad);
        pnlAddProducto.add(txtCostoUND);

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
                    txtNombre.setText(producto.getNombre());
                    txtCostoUND.setText(TextUtil.formatCurrency(producto.getCostoUND().doubleValue()));
                    txtCantidad.requestFocusInWindow();

                    //Actualizar producto selected
                    productoSelected = producto;
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed

        //--<Open Busqueda producto JDialog for selecting clasificador>
        Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            txtCostoUND.setText(TextUtil.formatCurrency(producto.getCostoUND().doubleValue()));
            txtCantidad.requestFocusInWindow();

            //Updating producto selected
            productoSelected = producto;
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void txtCostoUNDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoUNDKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCantidad.getText().equals("")) {

                    //Agregar producto
                    controller.agregarArticulo(productoSelected, Integer.parseInt(txtCantidad.getText()),
                            Double.parseDouble(txtCostoUND.getText()));

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblArticulosEntrada.getModel()).fireTableDataChanged();

                    //Limpiar datos
                    txtCodigo.setText("");
                    txtNombre.setText("");
                    txtCostoUND.setText("");
                    txtCantidad.setText("");

                    //Ir ultimo registro tabla
                    tblArticulosEntrada.scrollRectToVisible(tblArticulosEntrada.getCellRect(tblArticulosEntrada.getRowCount() - 1, 0, true));

                    //Request focus in window
                    txtCodigo.requestFocusInWindow();
                } else {
                    //Show error message
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD.VALIDA"),
                            messageBundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD.VALIDA"));
                    //Request focus in window
                    txtCantidad.requestFocusInWindow();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCostoUNDKeyPressed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {

            //Valida datos form
            validaDatosForm();

            //Setting values
            controller.setFechaAlta(dtpFechaAlta.getDate());
            controller.setDescripcion(txtDescripcion.getText());
            controller.setAlmacen((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject());
            controller.setTipoEntrada((TiposEntrada) ((TipoEntradaComboBoxModel) cmbTipoEntrada.getModel()).getSelectedItem().getObject());

            if (!controller.is_edit()) {
                //Guardar orden de entrada
                controller.crearOrdenEntrada();

                //Actualizar numero de entrada consecutivo generado
                txtNoEntrada.setText(String.valueOf(controller.getOrdenEntrada().getNoMovimiento()));

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ORDENENTRADA.INGRESO.EXITOSO"));

            } else {
                //Modificar orden de entrada
                controller.modificarOrdenEntrada();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.MODIFICA.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ORDENENTRADA.MODIFICA.EXITOSO"));
            }

            //Init values
            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Init formulario
        controller.init();
        //Init values formulario
        initValues();
    }//GEN-LAST:event_btnCancelarActionPerformed

    //Validate data form
    private void validaDatosForm() throws Exception {

        //Almacen
        if (cmbAlmacen.getSelectedIndex() == -1) {
            //Request focus
            cmbAlmacen.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN.VALIDA"));
        }

        //Tipo entrada
        if (cmbTipoEntrada.getSelectedIndex() == -1) {
            //Request focus
            cmbTipoEntrada.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.TIPOENTRADA.VALIDA"));
        }

        //Fecha de alta
        if (dtpFechaAlta.getDate() == null) {
            //Request focus
            dtpFechaAlta.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA.VALIDA"));
        }

        //Descripcion
        if (txtDescripcion.getText().equals("")) {
            //Request focus
            txtDescripcion.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION.VALIDA"));
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JComboBox cmbTipoEntrada;
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblNoEntrada;
    private javax.swing.JLabel lblTipoEntrada;
    private javax.swing.JPanel pnlOrdenEntrada;
    private org.jdesktop.swingx.JXTable tblArticulosEntrada;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCostoUND;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNoEntrada;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<ArticuloEntrada> articuloBeanTableModel;
    private Producto productoSelected;
}
