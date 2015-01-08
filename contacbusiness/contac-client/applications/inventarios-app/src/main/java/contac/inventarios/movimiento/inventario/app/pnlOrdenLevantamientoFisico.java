/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenLevantamientoController;
import contac.modelo.entity.Almacen;
import contac.modelo.entity.Articulo;
import contac.modelo.entity.ArticuloLevantamientoFisico;
import contac.modelo.entity.Producto;
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
 * @author Alex
 */
public class pnlOrdenLevantamientoFisico extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenLevantamientoFisico.class);

    //Resource Bundle internationalization
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es");

    //Controller
    private OrdenLevantamientoController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlOrdenLevantamientoFisico
     */
    public pnlOrdenLevantamientoFisico(GenericFrame frame) {

        //Call super constructor
        super(frame, "ordenLevantamientoFisico", "Orden de Levantamiento Físico", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init Controller
        controller = new OrdenLevantamientoController();

        //Controller init properties
        controller.init();

        //Init Components
        initComponents();

        //Init Values
        initValues();

        //RegisterListeners
        registerListeners();

        //Editar Registros
        controller.set_edit(false);
    }

    /**
     * Create new formulario de Levantamiento Físico de inventario
     *
     * @param frame,      GenericFrame
     * @param controller, BaseController
     */
    public pnlOrdenLevantamientoFisico(GenericFrame frame, BaseController controller) {

        //Call super controller
        super(frame, "OrdenIngresoLevantamientoFisico", "Orden de Levantamiento Físico", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init Controller
        this.controller = (OrdenLevantamientoController) controller;

        //Controller init properties
        this.controller.initModificacion();

        //Init components
        initComponents();

        //Editar registros
        controller.set_edit(true);

        //Init values
        initValues();

        //Register listeners
        registerListeners();

        //Change btnAceptar label
        btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));
    }

    @Override
    public void initValues() {

        //************************************************************
        //Init data values components
        //************************************************************

        //Init almacenes combo box data model




        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        //Evaluar edit datos para registrar
        if (!controller.is_edit()) {
            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));

            cmbAlmacen.setEnabled(true);
            dtpFechaAlta.setEnabled(true);
            txtDescripcion.setEditable(true);
        }
        if (controller.is_edit()) {
            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

            cmbAlmacen.setEnabled(false);
            dtpFechaAlta.setEnabled(false);
            txtDescripcion.setEditable(false);
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


        //Setting components not editable
        if (controller.is_edit()) {
            cmbAlmacen.setEnabled(false);
            dtpFechaAlta.setEnabled(false);
        }

        //*************************************************************
        //Init values registro productos
        //*************************************************************
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");

        //************************************************************
        //Init articulo bean table model
        //************************************************************
        articuloBeanTableModel = new BeanTableModel<ArticuloLevantamientoFisico>(ArticuloLevantamientoFisico.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulos.setModel(articuloBeanTableModel);
        tblArticulos.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulos.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Orden Levantamiento Fisico", "Movimiento Inventario", "Codigo Fabricante",
                "Renglon", "Cantidad Anterior", "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update", "Cantidad Ajuste", "Cantidad Existencia",
                "No Movimiento", "Monto Ajuste"};

        for (String columnLabel : articuloColumnRemove) {
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnLabel)));
        }

        //Ordering table columns
        columnModel.moveColumn(1, 0); //Codigo producto
        columnModel.moveColumn(5, 1); //Nombre producto
        //columnModel.moveColumn(3, 2); //Codigo fabricante
        columnModel.moveColumn(6, 4); //Unidad de medida

        //Setting prefered sized
        columnModel.getColumn(0).setPreferredWidth(15);
        columnModel.getColumn(1).setPreferredWidth(200);

        //Adding cell renderer class
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(4).setCellRenderer(centerAlignment);

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(3).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);

        //Ir ultimo registro tabla
        tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));
    }

    /**
     * Register Listener values
     */
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
                    txtCodigo.requestFocusInWindow();
                    txtCodigo.selectAll();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtCantidadKeyPressed(e);
            }

        });

        //Buscar producto action performed
        btnBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarProductoActionPerformed(e);
            }
        });

        //Aceptar action performed
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAceptarActionPerformed(e);
            }
        });

        //Cancelar action performed
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelarActionPerformed(e);
            }
        });
    }

    /**
     * Init Components design values
     */
    private void initComponents() {

        //Setting default Layout
        this.setLayout(new BorderLayout());

        //***************************************************************************************
        //Init Header Panel
        //***************************************************************************************
        header = new JXHeader();
        header.setTitle(resourceBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //Adding header to panel
        this.add(header, BorderLayout.NORTH);

        //***************************************************************************************
        //Init Header Components Panel
        //***************************************************************************************
        lblNoEntrada = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.NOENTRADA"));
        lblNoEntrada.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacen = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(JLabel.LEFT);

        lblFechaAlta = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA"));
        lblFechaAlta.setHorizontalAlignment(JLabel.LEFT);

        lblDescripcion = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION"));
        lblDescripcion.setHorizontalAlignment(JLabel.LEFT);

        txtNoEntrada = new JTextField();
        txtNoEntrada.setEditable(false);

        txtDescripcion = new JTextField();
        dtpFechaAlta = new JXDatePicker();
        cmbAlmacen = new JComboBox();

        //Create Panel Header Component
        JPanel pnlHeaderComp = new JPanel(new XYLayout());
        pnlHeaderComp.add(lblNoEntrada, new XYConstraints(5, 5, 90, 23));
        pnlHeaderComp.add(txtNoEntrada, new XYConstraints(98, 5, 120, 23));
        pnlHeaderComp.add(lblAlmacen, new XYConstraints(221, 5, 70, 23));
        pnlHeaderComp.add(cmbAlmacen, new XYConstraints(294, 5, 200, 23));
        pnlHeaderComp.add(lblFechaAlta, new XYConstraints(497, 5, 70, 23));
        pnlHeaderComp.add(dtpFechaAlta, new XYConstraints(570, 5, 120, 23));
        pnlHeaderComp.add(lblDescripcion, new XYConstraints(5, 33, 90, 23));
        pnlHeaderComp.add(txtDescripcion, new XYConstraints(98, 33, 592, 23));

        //***************************************************************************************
        //Init Articulos Table Panel
        //***************************************************************************************
        tblArticulos = new org.jdesktop.swingx.JXTable();
        JScrollPane spArticulos = new JScrollPane();
        spArticulos.getViewport().add(tblArticulos);

        JPanel pnlArticulos = new JPanel(new BorderLayout());
        pnlArticulos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlArticulos.add(spArticulos, BorderLayout.CENTER);

        //***************************************************************************************
        //Init Add Articulo Actions panel
        //***************************************************************************************
        txtCodigo = new javax.swing.JTextField();
        txtCodigo.setToolTipText(resourceBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO"));
        txtCodigo.setPreferredSize(new Dimension(120, 23));

        txtNombre = new javax.swing.JTextField();
        txtNombre.setToolTipText(resourceBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE"));
        txtNombre.setPreferredSize(new Dimension(250, 23));

        txtCantidad = new javax.swing.JTextField();
        txtCantidad.setToolTipText(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD"));
        txtCantidad.setPreferredSize(new Dimension(90, 23));

        btnBuscarProducto = new JButton(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.setPreferredSize(new Dimension(30, 23));

        JPanel pnlAddProducto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAddProducto.add(txtCodigo);
        pnlAddProducto.add(btnBuscarProducto);
        pnlAddProducto.add(txtNombre);
        pnlAddProducto.add(txtCantidad);

        //Adding to Main Panel
        JPanel pnlMainOrder = new JPanel(new BorderLayout());
        pnlMainOrder.add(pnlHeaderComp, BorderLayout.NORTH);
        pnlMainOrder.add(pnlArticulos, BorderLayout.CENTER);
        pnlMainOrder.add(pnlAddProducto, BorderLayout.SOUTH);

        this.add(pnlMainOrder, BorderLayout.CENTER);

        //***************************************************************************************
        //Init Actions panel
        //**************************************************************************************
        btnAceptar = new javax.swing.JButton(resourceBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        btnAceptar.setPreferredSize(new Dimension(90, 23));

        btnCancelar = new javax.swing.JButton(resourceBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setPreferredSize(new Dimension(90, 23));

        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlAction.setBorder(new EtchedBorder());
        pnlAction.add(btnAceptar);
        pnlAction.add(btnCancelar);

        this.add(pnlAction, BorderLayout.SOUTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {

        //--<Open Busqueda producto JDialog for selecting clasificador>
        Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            txtCantidad.requestFocusInWindow();

            //Updating producto selected
            productoSelected = producto;
        }
    }

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {

            //Valida datos form
            validaDatosForm();

            //Setting values
            controller.setFechaAlta(dtpFechaAlta.getDate());
            controller.setDescripcion(txtDescripcion.getText());
            controller.setAlmacen((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject());

            if (!controller.is_edit()) {
                //Guardar orden de entrada
                controller.crearOrdenLevantamiento();

                //Actualizar numero de entrada consecutivo generado
                txtNoEntrada.setText(String.valueOf(controller.getOrdenLevantamiento().getNoMovimiento()));

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.INGRESO.EXITOSO"));

                //Change btnModificar label
                btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

                //Setting controller edit data
                controller.set_edit(true);

            } else {
                //Modificar orden de entrada
                controller.modificarOrdenLevantantamientoFisico();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.MODIFICA.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.MODIFICA.EXITOSO"));
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

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCantidad.getText().equals("")) {

                    //Agregar articulo
                    controller.agregarArticulo(productoSelected, Integer.parseInt(txtCantidad.getText()));
                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblArticulos.getModel()).fireTableDataChanged();

                    //Limpiar datos
                    txtCodigo.setText("");
                    txtNombre.setText("");
                    txtCantidad.setText("");

                    //Ir al ultimo registro de la tabla
                    tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));

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
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {

        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCodigo.getText().equals("")) {

                    //Buscar producto por su codigo
                    Producto producto = controller.buscarProducto(txtCodigo.getText());

                    //Setting valores de producto
                    txtCodigo.setText(producto.getCodigo());
                    txtNombre.setText(producto.getNombre());
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
    }

    //Validate data form
    private void validaDatosForm() throws Exception {

        //Almacen
        if (cmbAlmacen.getSelectedIndex() == -1) {
            //Request focus
            cmbAlmacen.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN.VALIDA"));
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
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblNoEntrada;
    private org.jdesktop.swingx.JXTable tblArticulos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNoEntrada;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
    private BeanTableModel<ArticuloLevantamientoFisico> articuloBeanTableModel;
    private Producto productoSelected;

}
