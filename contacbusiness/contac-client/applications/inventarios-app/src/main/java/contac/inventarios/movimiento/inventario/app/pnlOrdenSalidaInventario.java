/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlOrdenSalidaInventario.java
 *
 * Created on 12-17-2011, 10:53:58 PM
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
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenSalidaController;
import contac.modelo.entity.Almacen;
import contac.modelo.entity.Articulo;
import contac.modelo.entity.ArticuloSalida;
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
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlOrdenSalidaInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenSalidaInventario.class);

    //Resource Bundle internationalization
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es");

    //Controller
    private OrdenSalidaController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlOrdenSalidaInventario
     */
    public pnlOrdenSalidaInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "OrdenBajaInventario", "Orden de Baja en Almacen", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new OrdenSalidaController();

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
     * Creates new form pnlOrdenSalidaInventario
     *
     * @param frame,      GenericFrame
     * @param controller, BaseController
     */
    public pnlOrdenSalidaInventario(GenericFrame frame, BaseController controller) {

        //Call super constructor
        super(frame, "OrdenBajaInventario", "Orden de Baja en Almacen", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        this.controller = (OrdenSalidaController) controller;

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
    }

    @Override
    public void initValues() {

        //************************************************************
        //Init values
        //************************************************************

        //Init almacenes combo box data model
        cmbAlmacenSalida.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        if (controller.is_edit()) {

            //Change btnAceptar label
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

            cmbAlmacenSalida.setEnabled(false);
            dtpFechaAlta.setEnabled(false);
            txtPersonaAutoriza.setEditable(false);
            txtDescripcion.setEditable(false);
        }
        
        if (controller.getNoMovimiento() > 0) {
            txtNoSalida.setText(String.valueOf(controller.getNoMovimiento()));
        } else {
            txtNoSalida.setText("");
        }

        txtPersonaAutoriza.setText(controller.getPersonaAutoriza());
        txtDescripcion.setText(controller.getDescripcion());

        dtpFechaAlta.setFormats("dd/MM/yyyy");
        dtpFechaAlta.setDate(controller.getFechaAlta());

        if (controller.getAlmacenSalida() != null) {
            AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacenSalida.getModel();
            cmbAlmacenSalida.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacenSalida().getId()));
        }

        //*************************************************************
        //Init values registro articulos
        //*************************************************************
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtCostoUND.setText("");

        //*************************************************************
        //Init articulo bean table model
        //*************************************************************
        articuloBeanTableModel = new BeanTableModel<ArticuloSalida>(ArticuloSalida.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulos.setModel(articuloBeanTableModel);
        tblArticulos.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulos.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Orden Salida", "Movimiento Inventario", "Renglon",
                "No Documento", "Cantidad Anterior", "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update"};

        for (String columnLabel : articuloColumnRemove) {
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnLabel)));
        }

        //Ordering table columns
        columnModel.moveColumn(1, 0); //Codigo producto
        columnModel.moveColumn(5, 1); //Nombre producto
        columnModel.moveColumn(3, 2); //Codigo fabricante
        columnModel.moveColumn(6, 4); //Unidad de medida

        //Setting prefered sized
        columnModel.getColumn(0).setPreferredWidth(15);
        columnModel.getColumn(1).setPreferredWidth(250);

        //Adding cell renderer class
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(4).setCellRenderer(centerAlignment);

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(6).setCellRenderer(decimalFormatRenderer);

        //Ir ultimo registro tabla
        tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));
    }

    private void registerListeners() {

        //cmbAlmacen
        cmbAlmacenSalida.addKeyListener(new KeyAdapter() {
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
                    txtPersonaAutoriza.requestFocusInWindow();
                }
            }
        });

       //txtPersonaAutoriza
        txtPersonaAutoriza.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtDescripcion.requestFocusInWindow();
                }
            }
        });

        //txtDescripcion
        txtDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCantidad.requestFocusInWindow();
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
            public void keyPressed(KeyEvent event) {
                txtCodigoKeyPressed(event);
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
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtCantidadKeyPressed(e);
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
        });

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

    private void initComponents() {

        //Setting default Layout
        this.setLayout(new BorderLayout());

        //***************************************************************************************
        //Init Header Panel
        //***************************************************************************************
        header = new JXHeader();
        header.setTitle(resourceBundle.getString("CONTAC.FORM.ORDENSALIDA.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //Adding header to panel
        this.add(header, BorderLayout.NORTH);

        //***************************************************************************************
        //Init Header Components Panel
        //***************************************************************************************

        lblNoSalida = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENSALIDA.NOSALIDA"));
        lblNoSalida.setHorizontalAlignment(SwingConstants.LEFT);

        lblAlmacen = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(SwingConstants.LEFT);

        lblFechaAlta = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA"));
        lblFechaAlta.setHorizontalAlignment(SwingConstants.LEFT);

        lblPersonaAutoriza = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENSALIDA.PERSONAAUTORIZA"));
        lblPersonaAutoriza.setHorizontalAlignment(SwingConstants.LEFT);

        lblDescripcion = new JLabel(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION"));
        lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);

        txtNoSalida = new JTextField();
        txtNoSalida.setEditable(false);

        txtPersonaAutoriza = new JTextField();
        txtDescripcion = new JTextField();
        dtpFechaAlta = new JXDatePicker(new Date());
        cmbAlmacenSalida = new JComboBox();

        //Create Panel Header Component
        JPanel pnlHeaderComp = new JPanel(new XYLayout());
        pnlHeaderComp.add(lblNoSalida, new XYConstraints(5, 5, 140, 23));
        pnlHeaderComp.add(txtNoSalida, new XYConstraints(150, 5, 120, 23));
        pnlHeaderComp.add(lblAlmacen, new XYConstraints(275, 5, 90, 23));
        pnlHeaderComp.add(cmbAlmacenSalida, new XYConstraints(370, 5, 200, 23));
        pnlHeaderComp.add(lblFechaAlta, new XYConstraints(575, 5, 90, 23));
        pnlHeaderComp.add(dtpFechaAlta, new XYConstraints(670, 5, 120, 23));
        pnlHeaderComp.add(lblPersonaAutoriza, new XYConstraints(5, 33, 140, 23));
        pnlHeaderComp.add(txtPersonaAutoriza, new XYConstraints(150, 33, 270, 23));
        pnlHeaderComp.add(lblDescripcion, new XYConstraints(425, 33, 90, 23));
        pnlHeaderComp.add(txtDescripcion, new XYConstraints(520, 33, 270, 23));

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
        txtCodigo = new JTextField();
        txtCodigo.setToolTipText(resourceBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO"));
        txtCodigo.setPreferredSize(new Dimension(120, 23));

        txtNombre = new JTextField();
        txtNombre.setToolTipText(resourceBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE"));
        txtNombre.setPreferredSize(new Dimension(250, 23));

        txtCantidad = new JTextField();
        txtCantidad.setToolTipText(resourceBundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD"));
        txtCantidad.setPreferredSize(new Dimension(90, 23));

        txtCostoUND = new JTextField();
        txtCostoUND.setToolTipText(resourceBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND"));
        txtCostoUND.setPreferredSize(new Dimension(90, 23));
        txtCostoUND.setEditable(false);

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

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCantidad.getText().equals("")) {

                    //Obtener almacen de salida
                    Almacen almacenSalida = (Almacen) ((AlmacenComboBoxModel) cmbAlmacenSalida.getModel()).getSelectedItem().getObject();

                    //Agregar producto
                    controller.agregarArticulo(productoSelected, almacenSalida, Integer.parseInt(txtCantidad.getText()));
                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblArticulos.getModel()).fireTableDataChanged();

                    //Limpiar datos
                    txtCodigo.setText("");
                    txtNombre.setText("");
                    txtCostoUND.setText("");
                    txtCantidad.setText("");

                    //Ir ultimo registro tabla
                    tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));

                    //Request focus in window
                    txtCodigo.requestFocusInWindow();
                } else {
                    //Show error message
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ORDENgENTRADA.CANTIDAD.VALIDA"),
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

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {

            //Valida datos form
            validaDatosForm();

            //Setting values
            controller.setFechaAlta(dtpFechaAlta.getDate());
            controller.setPersonaAutoriza(txtPersonaAutoriza.getText());
            controller.setDescripcion(txtDescripcion.getText());
            controller.setAlmacenSalida((Almacen) ((AlmacenComboBoxModel) cmbAlmacenSalida.getModel()).getSelectedItem().getObject());

            if (!controller.is_edit()) {
                //Guardar orden de salida
                controller.crearOrdenSalida();

                //Actualizar numero de salida consecutivo generado
                txtNoSalida.setText(String.valueOf(controller.getOrdenSalida().getNoMovimiento()));

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"));

                //Change btnModificar label
                btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

                //Setting controller edit data
                controller.set_edit(true);

            } else {
                //Modificar orden de salida
                controller.modificarOrdenSalida();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"));
            }

            //Init values
            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.REGISTRO.FALLIDO"),
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
        if (cmbAlmacenSalida.getSelectedItem() == null) {
            //Request focus
            cmbAlmacenSalida.requestFocusInWindow();
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

        //Persona autoriza
        if (txtPersonaAutoriza.getText().equals("")) {
            //Request focus
            txtPersonaAutoriza.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENSALIDA.PERSONAAUTORIZA.VALIDA"));

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cmbAlmacenSalida;
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblNoSalida;
    private javax.swing.JLabel lblPersonaAutoriza;
    private javax.swing.JPanel pnlOrdenEntrada;
    private org.jdesktop.swingx.JXTable tblArticulos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCostoUND;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNoSalida;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPersonaAutoriza;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<ArticuloSalida> articuloBeanTableModel;
    private Producto productoSelected;
}
