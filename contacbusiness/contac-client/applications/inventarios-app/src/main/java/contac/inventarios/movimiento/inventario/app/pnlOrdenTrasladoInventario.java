/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlOrdenTrasladoInventario.java
 *
 * Created on 12-17-2011, 10:54:09 PM
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
import contac.inventarios.controller.OrdenTrasladoController;
import contac.modelo.entity.Almacen;
import contac.modelo.entity.Articulo;
import contac.modelo.entity.ArticuloTraslado;
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
 * @author EMontenegro
 */
public class pnlOrdenTrasladoInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlOrdenTrasladoInventario.class);

    //Message resource bundle
    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private OrdenTrasladoController controller;

    /**
     * Creates new form pnlOrdenTrasladoInventario
     */
    public pnlOrdenTrasladoInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "OrdenTrasladoInventario", "Orden de Traslado entre Almacenes", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new OrdenTrasladoController();

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
     * Creates new form pnlOrdenTrasladoInventario
     *
     * @param frame,      GenericFrame
     * @param controller, BaseController
     */
    public pnlOrdenTrasladoInventario(GenericFrame frame, BaseController controller) {

        //Call super constructor
        super(frame, "OrdenTrasladoInventario", "Orden de Traslado entre Almacenes", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        this.controller = (OrdenTrasladoController) controller;

        //Controller init properties
        this.controller.initModificacion();

        //Init components
        initComponents();

        //Init values
        initValues();

        //Register Listeners
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
        cmbAlmacenIngreso.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        if (controller.is_edit()) {
            //Change btnAceptar label
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

            cmbAlmacenSalida.setEnabled(false);
            cmbAlmacenIngreso.setEnabled(false);
            dtpFechaAlta.setEnabled(false);
            txtPersonaEntrega.setEditable(false);
            txtPersonaRecibe.setEditable(false);
            txtDescripcion.setEditable(false);
        }


        if (controller.getNoMovimiento() > 0)
            txtNoTraslado.setText(String.valueOf(controller.getNoMovimiento()));
        else
            txtNoTraslado.setText("");

        //<Persona entrega>
        txtPersonaEntrega.setText(controller.getPersonaEntrega());

        //<Persona recibe>
        txtPersonaRecibe.setText(controller.getPersonaRecibe());

        //<Descripcion>
        txtDescripcion.setText(controller.getDescripcion());

        //<Fecha de alta>
        dtpFechaAlta.setFormats("dd/MM/yyyy");
        dtpFechaAlta.setDate(controller.getFechaAlta());

        //<Almacen de salida>
        ListCellRenderer almacenSalidaRenderer = new ComboBoxEmptySelectionRenderer(cmbAlmacenSalida, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getAlmacenSalida() != null) {
            AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacenSalida.getModel();
            cmbAlmacenSalida.setRenderer(almacenSalidaRenderer);
            cmbAlmacenSalida.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacenSalida().getId()));
        } else {
            cmbAlmacenSalida.setRenderer(almacenSalidaRenderer);
            cmbAlmacenSalida.setSelectedIndex(-1);
        }

        //<Almacen de ingreso>
        ListCellRenderer almacenIngresoRenderer = new ComboBoxEmptySelectionRenderer(cmbAlmacenIngreso, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getAlmacenIngreso() != null) {
            AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacenIngreso.getModel();
            cmbAlmacenIngreso.setRenderer(almacenIngresoRenderer);
            cmbAlmacenIngreso.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacenIngreso().getId()));
        } else {
            cmbAlmacenIngreso.setRenderer(almacenIngresoRenderer);
            cmbAlmacenIngreso.setSelectedIndex(-1);
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

        articuloBeanTableModel = new BeanTableModel<ArticuloTraslado>(ArticuloTraslado.class, Articulo.class, controller.getArticulos());
        articuloBeanTableModel.setModelEditable(false);
        articuloBeanTableModel.sortColumnNames();
        tblArticulos.setModel(articuloBeanTableModel);
        tblArticulos.setRowSelectionAllowed(true);
        tblArticulos.getTableHeader().setReorderingAllowed(false);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblArticulos.getColumnModel();

        String[] articuloColumnRemove = new String[]{"Id", "Producto", "Orden Traslado", "Renglon", "Ctime", "Cuser", "Codigo Fabricante",
                "No Documento", "Cantidad Anterior", "Mtime", "Muser", "Create", "Update"};

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
        tblArticulos.scrollRectToVisible(tblArticulos.getCellRect(tblArticulos.getRowCount() - 1, 0, true));

    }

    private void registerListeners() {

        //cmbAlmacenSalida
        cmbAlmacenSalida.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbAlmacenIngreso.requestFocusInWindow();
                }
            }
        });

        //cmbAlmacenIngreso
        cmbAlmacenIngreso.addKeyListener(new KeyAdapter() {
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
                    txtPersonaEntrega.requestFocusInWindow();
                }
            }
        });

        //txtPersonaEntrega
        txtPersonaEntrega.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtPersonaRecibe.requestFocusInWindow();
                }
            }
        });

        //txtPersonaEntrega
        txtPersonaRecibe.addKeyListener(new KeyAdapter() {
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
                    txtCodigo.requestFocusInWindow();
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
        header.setTitle(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //Adding header to panel
        this.add(header, BorderLayout.NORTH);

        //***************************************************************************************
        //Init Header Components Panel
        //***************************************************************************************
        lblNoTraslado = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.NOTRASLADO"));
        lblNoTraslado.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacenSalida = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.ALMACENSALIDA"));
        lblAlmacenSalida.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacenIngreso = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.ALMACENINGRESO"));
        lblAlmacenIngreso.setHorizontalAlignment(JLabel.LEFT);

        lblFechaAlta = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA"));
        lblFechaAlta.setHorizontalAlignment(JLabel.LEFT);

        lblPersonaEntrega = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.PERSONAENTREGA"));
        lblPersonaEntrega.setHorizontalAlignment(JLabel.LEFT);

        lblPersonaRecibe = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.PERSONARECIBE"));
        lblPersonaRecibe.setHorizontalAlignment(JLabel.LEFT);

        lblDescripcion = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION"));
        lblDescripcion.setHorizontalAlignment(JLabel.LEFT);

        txtNoTraslado = new JTextField();
        txtNoTraslado.setEditable(false);

        txtPersonaEntrega = new JTextField();
        txtPersonaRecibe = new JTextField();
        txtDescripcion = new JTextField();
        dtpFechaAlta = new JXDatePicker();
        cmbAlmacenSalida = new JComboBox();
        cmbAlmacenIngreso = new JComboBox();

        //Create Panel Header Component
        JPanel pnlHeaderComp = new JPanel(new XYLayout());
        pnlHeaderComp.add(lblNoTraslado, new XYConstraints(5, 5, 120, 23));
        pnlHeaderComp.add(txtNoTraslado, new XYConstraints(130, 5, 120, 23));
        pnlHeaderComp.add(lblAlmacenSalida, new XYConstraints(255, 5, 120, 23));
        pnlHeaderComp.add(cmbAlmacenSalida, new XYConstraints(380, 5, 200, 23));
        pnlHeaderComp.add(lblAlmacenIngreso, new XYConstraints(585, 5, 120, 23));
        pnlHeaderComp.add(cmbAlmacenIngreso, new XYConstraints(710, 5, 200, 23));
        pnlHeaderComp.add(lblFechaAlta, new XYConstraints(915, 5, 70, 23));
        pnlHeaderComp.add(dtpFechaAlta, new XYConstraints(990, 5, 120, 23));
        pnlHeaderComp.add(lblPersonaEntrega, new XYConstraints(5, 33, 120, 23));
        pnlHeaderComp.add(txtPersonaEntrega, new XYConstraints(130, 33, 450, 23));
        pnlHeaderComp.add(lblPersonaRecibe, new XYConstraints(585, 33, 120, 23));
        pnlHeaderComp.add(txtPersonaRecibe, new XYConstraints(710, 33, 400, 23));
        pnlHeaderComp.add(lblDescripcion, new XYConstraints(5, 61, 120, 23));
        pnlHeaderComp.add(txtDescripcion, new XYConstraints(130, 61, 980, 23));

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

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        header = new org.jdesktop.swingx.JXHeader();
//        pnlOrdenEntrada = new javax.swing.JPanel();
//        lblPersonaRecibe = new javax.swing.JLabel();
//        txtNoTraslado = new javax.swing.JTextField();
//        lblFechaAlta = new javax.swing.JLabel();
//        dtpFechaAlta = new org.jdesktop.swingx.JXDatePicker();
//        lblAlmacenIngreso = new javax.swing.JLabel();
//        cmbAlmacenIngreso = new javax.swing.JComboBox();
//        lblDescripcion = new javax.swing.JLabel();
//        txtPersonaRecibe = new javax.swing.JTextField();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        tblArticulos = new org.jdesktop.swingx.JXTable();
//        txtCodigo = new javax.swing.JTextField();
//        txtNombre = new javax.swing.JTextField();
//        btnBuscarProducto = new javax.swing.JButton();
//        txtCostoUND = new javax.swing.JTextField();
//        txtCantidad = new javax.swing.JTextField();
//        jSeparator3 = new javax.swing.JSeparator();
//        btnAceptar = new javax.swing.JButton();
//        btnCancelar = new javax.swing.JButton();
//        lblNoTraslado = new javax.swing.JLabel();
//        txtDescripcion = new javax.swing.JTextField();
//        lblAlmacenSalida = new javax.swing.JLabel();
//        cmbAlmacenSalida = new javax.swing.JComboBox();
//        lblPersonaEntrega = new javax.swing.JLabel();
//        txtPersonaEntrega = new javax.swing.JTextField();
//
//        setLayout(new java.awt.BorderLayout());
//
//        header.setForeground(new java.awt.Color(255, 153, 0));
//        header.setPreferredSize(new java.awt.Dimension(51, 35));
//        header.setScrollableTracksViewportWidth(false);
//        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
//        header.setTitle(bundle.getString("CONTAC.FORM.ORDENTRASLADO.TITTLE")); // NOI18N
//        header.setTitleForeground(new java.awt.Color(255, 153, 0));
//        add(header, java.awt.BorderLayout.PAGE_START);
//
//        pnlOrdenEntrada.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        lblPersonaRecibe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblPersonaRecibe.setText(bundle.getString("CONTAC.FORM.ORDENTRASLADO.PERSONARECIBE")); // NOI18N
//        pnlOrdenEntrada.add(lblPersonaRecibe, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 97, 20));
//
//        txtNoTraslado.setEditable(false);
//        txtNoTraslado.setHorizontalAlignment(javax.swing.JTextField.LEFT);
//        txtNoTraslado.setToolTipText("");
//        txtNoTraslado.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtNoTraslado.setPreferredSize(new java.awt.Dimension(59, 30));
//        pnlOrdenEntrada.add(txtNoTraslado, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 11, 110, 23));
//
//        lblFechaAlta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblFechaAlta.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.FECHAALTA")); // NOI18N
//        pnlOrdenEntrada.add(lblFechaAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 80, 20));
//        pnlOrdenEntrada.add(dtpFechaAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(889, 11, 120, 23));
//
//        lblAlmacenIngreso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblAlmacenIngreso.setText(bundle.getString("CONTAC.FORM.ORDENTRASLADO.ALMACENINGRESO")); // NOI18N
//        pnlOrdenEntrada.add(lblAlmacenIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 100, 20));
//
//        cmbAlmacenIngreso.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
//        pnlOrdenEntrada.add(cmbAlmacenIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 170, 23));
//
//        lblDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblDescripcion.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.DESCRIPCION")); // NOI18N
//        pnlOrdenEntrada.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 97, 20));
//
//        txtPersonaRecibe.setHorizontalAlignment(javax.swing.JTextField.LEFT);
//        txtPersonaRecibe.setToolTipText("");
//        txtPersonaRecibe.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtPersonaRecibe.setPreferredSize(new java.awt.Dimension(59, 30));
//        pnlOrdenEntrada.add(txtPersonaRecibe, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 380, 23));
//
//        jScrollPane1.setViewportView(tblArticulos);
//
//        pnlOrdenEntrada.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 115, 999, 350));
//
//        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
//        txtCodigo.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO")); // NOI18N
//        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
//        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyPressed(java.awt.event.KeyEvent evt) {
//                txtCodigoKeyPressed(evt);
//            }
//        });
//        pnlOrdenEntrada.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 471, 145, 23));
//
//        txtNombre.setEditable(false);
//        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
//        txtNombre.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE")); // NOI18N
//        txtNombre.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtNombre.setPreferredSize(new java.awt.Dimension(59, 30));
//        pnlOrdenEntrada.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 471, 365, 23));
//
//        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
//        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnBuscarProductoActionPerformed(evt);
//            }
//        });
//        pnlOrdenEntrada.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 471, 29, 23));
//
//        txtCostoUND.setEditable(false);
//        txtCostoUND.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
//        txtCostoUND.setToolTipText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND")); // NOI18N
//        txtCostoUND.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtCostoUND.setPreferredSize(new java.awt.Dimension(59, 30));
//        pnlOrdenEntrada.add(txtCostoUND, new org.netbeans.lib.awtextra.AbsoluteConstraints(691, 471, 121, 23));
//
//        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
//        txtCantidad.setToolTipText(bundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD")); // NOI18N
//        txtCantidad.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtCantidad.setPreferredSize(new java.awt.Dimension(59, 30));
//        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyPressed(java.awt.event.KeyEvent evt) {
//                txtCantidadKeyPressed(evt);
//            }
//        });
//        pnlOrdenEntrada.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 471, 118, 23));
//        pnlOrdenEntrada.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 1310, -1));
//
//        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
//        btnAceptar.setActionCommand(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
//        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnAceptarActionPerformed(evt);
//            }
//        });
//        pnlOrdenEntrada.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 520, 80, -1));
//
//        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
//        btnCancelar.setActionCommand("");
//        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnCancelarActionPerformed(evt);
//            }
//        });
//        pnlOrdenEntrada.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 80, -1));
//
//        lblNoTraslado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblNoTraslado.setText(bundle.getString("CONTAC.FORM.ORDENTRASLADO.NOTRASLADO")); // NOI18N
//        pnlOrdenEntrada.add(lblNoTraslado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 97, 20));
//
//        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
//        txtDescripcion.setToolTipText("");
//        txtDescripcion.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtDescripcion.setPreferredSize(new java.awt.Dimension(59, 30));
//        pnlOrdenEntrada.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 70, 892, 23));
//
//        lblAlmacenSalida.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblAlmacenSalida.setText(bundle.getString("CONTAC.FORM.ORDENTRASLADO.ALMACENSALIDA")); // NOI18N
//        pnlOrdenEntrada.add(lblAlmacenSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 90, 20));
//
//        cmbAlmacenSalida.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
//        pnlOrdenEntrada.add(cmbAlmacenSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 170, 23));
//
//        lblPersonaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        lblPersonaEntrega.setText(bundle.getString("CONTAC.FORM.ORDENTRASLADO.PERSONAENTREGA")); // NOI18N
//        pnlOrdenEntrada.add(lblPersonaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 97, 20));
//
//        txtPersonaEntrega.setHorizontalAlignment(javax.swing.JTextField.LEFT);
//        txtPersonaEntrega.setToolTipText("");
//        txtPersonaEntrega.setMinimumSize(new java.awt.Dimension(6, 25));
//        txtPersonaEntrega.setPreferredSize(new java.awt.Dimension(59, 30));
//        pnlOrdenEntrada.add(txtPersonaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 40, 380, 23));
//
//        add(pnlOrdenEntrada, java.awt.BorderLayout.CENTER);
//    }// </editor-fold>//GEN-END:initComponents

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

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyCode()) {

                if (!txtCantidad.getText().equals("")) {

                    //Obtener almacen de salida
                    Almacen almacenSalida = (Almacen) ((AlmacenComboBoxModel) cmbAlmacenSalida.getModel()).getSelectedItem().getObject();
                    controller.setAlmacenSalida(almacenSalida);

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

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {

            //Valida datos form
            validaDatosForm();

            //Setting values
            controller.setFechaAlta(dtpFechaAlta.getDate());
            controller.setPersonaEntrega(txtPersonaEntrega.getText());
            controller.setPersonaRecibe(txtPersonaRecibe.getText());
            controller.setDescripcion(txtDescripcion.getText());
            controller.setAlmacenSalida((Almacen) ((AlmacenComboBoxModel) cmbAlmacenSalida.getModel()).getSelectedItem().getObject());
            controller.setAlmacenIngreso((Almacen) ((AlmacenComboBoxModel) cmbAlmacenIngreso.getModel()).getSelectedItem().getObject());

            if (!controller.is_edit()) {
                //Guardar orden de traslado
                controller.crearOrdenTraslado();

                //Actualizar numero de traslado consecutivo generado
                txtNoTraslado.setText(String.valueOf(controller.getOrdenTraslado().getNoMovimiento()));

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"));

                //Change btnModificar label
                btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

                //Setting controller edit data
                controller.set_edit(true);

            } else {
                //Modificar orden de traslado
                controller.modificarOrdenTraslado();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.MODIFICACION.EXITOSO"));
            }

            //Setting disable fields
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

        //Almacen Salida
        if (cmbAlmacenSalida.getSelectedItem() == null) {
            //Request focus
            cmbAlmacenSalida.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN.VALIDA"));
        }

        //Almacen Ingreso
        if (cmbAlmacenIngreso.getSelectedItem() == null) {
            //Request focus
            cmbAlmacenIngreso.requestFocusInWindow();
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

        //Persona entrega
        if (txtPersonaEntrega.getText().equals("")) {
            //Request focus
            txtPersonaEntrega.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.PERSONAENTREGA.VALIDA"));

        }

        //Persona recibe
        if (txtPersonaRecibe.getText().equals("")) {
            //Request focus
            txtPersonaRecibe.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.PERSONARECIBE.VALIDA"));
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cmbAlmacenIngreso;
    private javax.swing.JComboBox cmbAlmacenSalida;
    private org.jdesktop.swingx.JXDatePicker dtpFechaAlta;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel lblAlmacenIngreso;
    private javax.swing.JLabel lblAlmacenSalida;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFechaAlta;
    private javax.swing.JLabel lblNoTraslado;
    private javax.swing.JLabel lblPersonaEntrega;
    private javax.swing.JLabel lblPersonaRecibe;
    private org.jdesktop.swingx.JXTable tblArticulos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCostoUND;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNoTraslado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPersonaEntrega;
    private javax.swing.JTextField txtPersonaRecibe;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<ArticuloTraslado> articuloBeanTableModel;
    private Producto productoSelected;
}
