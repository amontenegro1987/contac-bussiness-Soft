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

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlOrdenSalidaInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenSalidaInventario.class);

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
        if (controller.is_edit()) {

            //Change btnAceptar label
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

            cmbAlmacenSalida.setEnabled(false);
            dtpFechaAlta.setEnabled(false);
            txtPersonaAutoriza.setEditable(false);
            txtDescripcion.setEditable(false);
        }
        
        if (controller.getNoMovimiento() > 0)
            txtNoSalida.setText(String.valueOf(controller.getNoMovimiento()));
        else
            txtNoSalida.setText("");
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
                "No Documento", "Ctime", "Cuser", "Mtime", "Muser", "Create", "Update"};

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
        pnlOrdenEntrada = new javax.swing.JPanel();
        lblPersonaAutoriza = new javax.swing.JLabel();
        txtNoSalida = new javax.swing.JTextField();
        lblFechaAlta = new javax.swing.JLabel();
        dtpFechaAlta = new org.jdesktop.swingx.JXDatePicker();
        lblAlmacen = new javax.swing.JLabel();
        cmbAlmacenSalida = new javax.swing.JComboBox();
        lblDescripcion = new javax.swing.JLabel();
        txtPersonaAutoriza = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulos = new org.jdesktop.swingx.JXTable();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        txtCostoUND = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblNoSalida = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.ORDENSALIDA.TITTLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        pnlOrdenEntrada.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPersonaAutoriza.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPersonaAutoriza.setText(bundle.getString("CONTAC.FORM.ORDENSALIDA.PERSONAAUTORIZA")); // NOI18N
        pnlOrdenEntrada.add(lblPersonaAutoriza, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 97, 20));

        txtNoSalida.setEditable(false);
        txtNoSalida.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNoSalida.setToolTipText("");
        txtNoSalida.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNoSalida.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtNoSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 11, 160, 23));

        lblFechaAlta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaAlta.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA")); // NOI18N
        pnlOrdenEntrada.add(lblFechaAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(763, 12, 88, 20));
        pnlOrdenEntrada.add(dtpFechaAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 11, 154, 23));

        lblAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN")); // NOI18N
        pnlOrdenEntrada.add(lblAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 12, 80, 20));

        cmbAlmacenSalida.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
        pnlOrdenEntrada.add(cmbAlmacenSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 11, 399, 23));

        lblDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescripcion.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION")); // NOI18N
        pnlOrdenEntrada.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 80, 20));

        txtPersonaAutoriza.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPersonaAutoriza.setToolTipText("");
        txtPersonaAutoriza.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPersonaAutoriza.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtPersonaAutoriza, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 40, 360, 23));

        jScrollPane1.setViewportView(tblArticulos);

        pnlOrdenEntrada.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 999, 378));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO")); // NOI18N
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });
        pnlOrdenEntrada.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 471, 145, 23));

        txtNombre.setEditable(false);
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE")); // NOI18N
        txtNombre.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombre.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 471, 365, 23));

        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        pnlOrdenEntrada.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 471, 29, 23));

        txtCostoUND.setEditable(false);
        txtCostoUND.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoUND.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOPROM")); // NOI18N
        txtCostoUND.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoUND.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtCostoUND, new org.netbeans.lib.awtextra.AbsoluteConstraints(691, 471, 121, 23));

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidad.setToolTipText(bundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD")); // NOI18N
        txtCantidad.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCantidad.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
        });
        pnlOrdenEntrada.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 471, 118, 23));
        pnlOrdenEntrada.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 79, 1310, -1));

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setActionCommand(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        pnlOrdenEntrada.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 520, 80, -1));

        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.setActionCommand("");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pnlOrdenEntrada.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 80, -1));

        lblNoSalida.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoSalida.setText(bundle.getString("CONTAC.FORM.ORDENSALIDA.NOSALIDA")); // NOI18N
        pnlOrdenEntrada.add(lblNoSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 97, 20));

        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcion.setToolTipText("");
        txtDescripcion.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescripcion.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 40, 440, 23));

        add(pnlOrdenEntrada, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

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
