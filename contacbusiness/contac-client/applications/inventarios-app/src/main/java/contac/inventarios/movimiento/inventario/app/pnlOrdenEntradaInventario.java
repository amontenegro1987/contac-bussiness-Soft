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
public class pnlOrdenEntradaInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenEntradaInventario.class);

    //Controller
    private OrdenEntradaController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

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

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Orden Entrada", "Movimiento Inventario", "Renglon",
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
        columnModel.getColumn(0).setPreferredWidth(20);
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
        lblTipoEntrada = new javax.swing.JLabel();
        txtNoEntrada = new javax.swing.JTextField();
        lblFechaAlta = new javax.swing.JLabel();
        dtpFechaAlta = new org.jdesktop.swingx.JXDatePicker();
        lblAlmacen = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulosEntrada = new org.jdesktop.swingx.JXTable();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        txtCostoUND = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbTipoEntrada = new javax.swing.JComboBox();
        lblNoEntrada = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.ORDENENTRADA.TITLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        pnlOrdenEntrada.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTipoEntrada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoEntrada.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.TIPOENTRADA")); // NOI18N
        pnlOrdenEntrada.add(lblTipoEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 97, 20));

        txtNoEntrada.setEditable(false);
        txtNoEntrada.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNoEntrada.setToolTipText("");
        txtNoEntrada.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNoEntrada.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtNoEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 11, 160, 23));

        lblFechaAlta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaAlta.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA")); // NOI18N
        pnlOrdenEntrada.add(lblFechaAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(763, 12, 88, 20));
        pnlOrdenEntrada.add(dtpFechaAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 11, 154, 23));

        lblAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN")); // NOI18N
        pnlOrdenEntrada.add(lblAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 12, 80, 20));

        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
        pnlOrdenEntrada.add(cmbAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 11, 399, 23));

        lblDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDescripcion.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION")); // NOI18N
        pnlOrdenEntrada.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 40, 80, 20));

        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcion.setToolTipText("");
        txtDescripcion.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescripcion.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 40, 650, 23));

        jScrollPane1.setViewportView(tblArticulosEntrada);

        pnlOrdenEntrada.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 999, 330));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO")); // NOI18N
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });
        pnlOrdenEntrada.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 145, 23));

        txtNombre.setEditable(false);
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE")); // NOI18N
        txtNombre.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombre.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 365, 23));

        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        pnlOrdenEntrada.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 29, 23));

        txtCostoUND.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoUND.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND")); // NOI18N
        txtCostoUND.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoUND.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCostoUND.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoUNDKeyPressed(evt);
            }
        });
        pnlOrdenEntrada.add(txtCostoUND, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 420, 121, 23));

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidad.setToolTipText(bundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD")); // NOI18N
        txtCantidad.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCantidad.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlOrdenEntrada.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 118, 23));
        pnlOrdenEntrada.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 79, 1310, -1));

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setActionCommand(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        pnlOrdenEntrada.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 460, 80, -1));

        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.setActionCommand("");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pnlOrdenEntrada.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, 80, -1));

        cmbTipoEntrada.setModel(new TipoEntradaComboBoxModel(TiposEntrada.values()));
        pnlOrdenEntrada.add(cmbTipoEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 40, 160, 23));

        lblNoEntrada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoEntrada.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.NOENTRADA")); // NOI18N
        pnlOrdenEntrada.add(lblNoEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 97, 20));

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
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
