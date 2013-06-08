/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlMovimientoInventario.java
 *
 * Created on 11-09-2012, 09:49:41 AM
 */
package contac.inventarios.movimiento.inventario.app;

import contac.commons.components.pnlBusquedaProducto;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.models.tables.NotEditableTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.MovimientoInventarioController;
import contac.modelo.entity.MovimientoInventario;
import contac.modelo.entity.Producto;
import contac.modelo.entity.ProductoExistencia;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlMovimientoInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlMovimientoInventario.class);

    //Controller
    private MovimientoInventarioController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlMovimientoInventario
     */
    public pnlMovimientoInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "MovimientoInventario", "Movimientos Inventario", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new MovimientoInventarioController();

        //Controller init properties
        controller.init();

        //Init components
        initComponents();

        //Init values
        initValues();

        //Register listeners
        registerListeners();
    }

    @Override
    public void initValues() {

        //Init campos de busqueda
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setFormats("dd/MM/yyyy");

        //Column names JTable
        String[] columnNames = {"C\u00f3digo", "Almac\u00e9n", "Cantidad Inicial", "Ingreso", "Salida", "Existencia"};
        NotEditableTableModel tableModel = new NotEditableTableModel(columnNames, 0);

        for (ProductoExistencia existencia : controller.getExistencias()) {
            Object[] row = new Object[6];
            row[0] = existencia.getId().getAlmacen().getCodigo();
            row[1] = existencia.getId().getAlmacen().getDescripcion();
            row[2] = existencia.getCantidad();
            row[3] = existencia.getIngreso();
            row[4] = existencia.getSalida();
            row[5] = existencia.getExistencia();

            tableModel.addRow(row);
        }

        //Setting table model
        existenciasTbl.setModel(tableModel);

        //Setting default focus
        txtCodigo.requestFocusInWindow();
    }

    public void initValuesMovimientosInventario() {

        //Column names JTable
        String[] columnNames = {"Almac\u00e9n", "Fecha Alta", "Tipo Movimiento", "No. Documento", "Cantidad", "Estado"};
        NotEditableTableModel tableModel = new NotEditableTableModel(columnNames, 0);

        for (MovimientoInventario movimiento : controller.getMovimientoInventarios()) {
            Object[] row = new Object[6];
            row[0] = movimiento.getAlmacen().getCodigo() + " - " + movimiento.getAlmacen().getDescripcion();
            row[1] = TextUtil.formatDate(movimiento.getFechaAlta());

            if (movimiento.getArticuloEntrada() != null) {
                row[2] = messageBundle.getString("CONTAC.FORM.MSG.MOVIMIENTOS.ENTRADAINVENTARIO");
                row[3] = movimiento.getArticuloEntrada().getNoDocumento();
            }
            if (movimiento.getArticuloFactura() != null) {
                row[2] = messageBundle.getString("CONTAC.FORM.MSG.MOVIMIENTOS.FACTURACOMERCIAL");
                row[3] = movimiento.getArticuloFactura().getNoFactura();
            }
            if (movimiento.getArticuloLevantamientoFisico() != null) {
                row[2] = messageBundle.getString("CONTAC.FORM.MSG.MOVIMIENTOS.AJUSTEINVENTARIO");
                row[3] = movimiento.getArticuloLevantamientoFisico().getNoMovimiento();
            }
            if (movimiento.getArticuloSalida() != null) {
                row[2] = messageBundle.getString("CONTAC.FORM.MSG.MOVIMIENTOS.SALIDAINVENTARIO");
                row[3] = movimiento.getArticuloSalida().getNoDocumento();
            }
            if (movimiento.getArticuloTraslado() != null) {
                row[2] = messageBundle.getString("CONTAC.FORM.MSG.MOVIMIENTOS.TRASLADOINVENTARIO");
                row[3] = movimiento.getArticuloTraslado().getNoDocumento();
            }

            row[4] = movimiento.getCantidad() * movimiento.getAfectacion();
            row[5] = movimiento.getEstado().getNombre();

            tableModel.addRow(row);
        }

        //Setting table model movimiento inventario
        movimientosTbl.setModel(tableModel);

        //Formatting column order and alignment
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer rightAlignment = new DefaultTableCellRenderer();
        rightAlignment.setHorizontalAlignment(JLabel.RIGHT);

        TableColumnModel columnModel = movimientosTbl.getColumnModel();
        columnModel.getColumn(1).setCellRenderer(centerAlignment);
        columnModel.getColumn(4).setCellRenderer(rightAlignment);
        columnModel.getColumn(5).setCellRenderer(centerAlignment);

    }

    private void registerListeners() {

        txtCodigo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtCodigo.selectAll();
            }
        });
        
        existenciasTbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                existenciasTbl_mouseClick(e);
            }
        });
    }

    private void existenciasTbl_mouseClick(MouseEvent event) {

        try {

            //Getting row selected
            int row = existenciasTbl.getSelectedRow();

            //Codigo de almacen
            int idAlmacen = (Integer) existenciasTbl.getValueAt(row, 0);

            //Setting codigo almacen
            controller.setIdAlmacen(idAlmacen);

            //Buscar movimientos inventario
            controller.buscarMovimientosInventario();

            //Init table registros de movimientos de inventario
            initValuesMovimientosInventario();

        } catch (Exception e) {
            //Show confirmation message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                    e.getMessage());
        }
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
        movimientoInventarioTab = new javax.swing.JTabbedPane();
        pnlExistenciaProducto = new javax.swing.JPanel();
        pnlMovimiento = new javax.swing.JPanel();
        pnlDatosGenerales = new javax.swing.JPanel();
        lblCodigoCompuesto = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        lblNombreCompuesto = new javax.swing.JLabel();
        txtNombreCompuesto = new javax.swing.JTextField();
        lblCodigoFabricanteCompuesto = new javax.swing.JLabel();
        txtCodigoFabricanteCompuesto = new javax.swing.JTextField();
        lblMarcaCompuesto = new javax.swing.JLabel();
        txtMarcaCompuesto = new javax.swing.JTextField();
        txtCodigoCBSCompuesto = new javax.swing.JTextField();
        lblCodigoCBSCompuesto = new javax.swing.JLabel();
        lblUnidadMedidaCompuesto = new javax.swing.JLabel();
        txtUnidadMedidaCompuesto = new javax.swing.JTextField();
        lblLineaProductoCompuesto = new javax.swing.JLabel();
        txtLineaProductoCompuesto = new javax.swing.JTextField();
        txtProveedorCompuesto = new javax.swing.JTextField();
        lblProveedorCompuesto = new javax.swing.JLabel();
        lblCostoUNDCompuesto = new javax.swing.JLabel();
        txtCostoUNDCompuesto = new javax.swing.JTextField();
        lblCostoPROMCompuesto = new javax.swing.JLabel();
        txtCostoPROMCompuesto = new javax.swing.JTextField();
        txtPrecioPromCompuesto = new javax.swing.JTextField();
        lblCostoPromCompuesto = new javax.swing.JLabel();
        txtPrecioEstandarCompuesto = new javax.swing.JTextField();
        lblPrecioEstandarCompuesto = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        existenciasTbl = new javax.swing.JTable();
        header1 = new org.jdesktop.swingx.JXHeader();
        pnlDetalleMovimientos = new javax.swing.JPanel();
        header2 = new org.jdesktop.swingx.JXHeader();
        jScrollPane2 = new javax.swing.JScrollPane();
        movimientosTbl = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        lblFechaDesde = new javax.swing.JLabel();
        dtpFechaDesde = new org.jdesktop.swingx.JXDatePicker();
        lblFechaHasta = new javax.swing.JLabel();
        dtpFechaHasta = new org.jdesktop.swingx.JXDatePicker();
        btnBuscar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.MOVIMIENTOINVETNARIO.TITLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        movimientoInventarioTab.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        pnlExistenciaProducto.setLayout(new java.awt.BorderLayout());

        pnlMovimiento.setLayout(new java.awt.BorderLayout());

        pnlDatosGenerales.setPreferredSize(new java.awt.Dimension(500, 538));
        pnlDatosGenerales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCodigoCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO")); // NOI18N
        pnlDatosGenerales.add(lblCodigoCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, 120, 20));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setToolTipText("");
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });
        pnlDatosGenerales.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 12, 104, 23));

        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        pnlDatosGenerales.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 11, 29, 23));

        lblNombreCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE")); // NOI18N
        pnlDatosGenerales.add(lblNombreCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 47, 120, 20));

        txtNombreCompuesto.setEditable(false);
        txtNombreCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreCompuesto.setToolTipText("");
        txtNombreCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombreCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtNombreCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 46, 350, 23));

        lblCodigoFabricanteCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoFabricanteCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOFABRICANTE")); // NOI18N
        pnlDatosGenerales.add(lblCodigoFabricanteCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 76, 120, 20));

        txtCodigoFabricanteCompuesto.setEditable(false);
        txtCodigoFabricanteCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoFabricanteCompuesto.setToolTipText("");
        txtCodigoFabricanteCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoFabricanteCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtCodigoFabricanteCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 75, 170, 23));

        lblMarcaCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMarcaCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MARCA")); // NOI18N
        pnlDatosGenerales.add(lblMarcaCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 76, 44, 20));

        txtMarcaCompuesto.setEditable(false);
        txtMarcaCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMarcaCompuesto.setToolTipText("");
        txtMarcaCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtMarcaCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtMarcaCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 75, 122, 23));

        txtCodigoCBSCompuesto.setEditable(false);
        txtCodigoCBSCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoCBSCompuesto.setToolTipText("");
        txtCodigoCBSCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoCBSCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtCodigoCBSCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 104, 350, 23));

        lblCodigoCBSCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoCBSCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOCBS")); // NOI18N
        pnlDatosGenerales.add(lblCodigoCBSCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 120, 20));

        lblUnidadMedidaCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnidadMedidaCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.UNIDADMEDIDA")); // NOI18N
        pnlDatosGenerales.add(lblUnidadMedidaCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 134, 120, 20));

        txtUnidadMedidaCompuesto.setEditable(false);
        txtUnidadMedidaCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUnidadMedidaCompuesto.setToolTipText("");
        txtUnidadMedidaCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtUnidadMedidaCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtUnidadMedidaCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 133, 104, 23));

        lblLineaProductoCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLineaProductoCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.LINEA")); // NOI18N
        pnlDatosGenerales.add(lblLineaProductoCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 134, 104, 20));

        txtLineaProductoCompuesto.setEditable(false);
        txtLineaProductoCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtLineaProductoCompuesto.setToolTipText("");
        txtLineaProductoCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtLineaProductoCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtLineaProductoCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 133, 134, 23));

        txtProveedorCompuesto.setEditable(false);
        txtProveedorCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtProveedorCompuesto.setToolTipText("");
        txtProveedorCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtProveedorCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtProveedorCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 162, 350, 23));

        lblProveedorCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProveedorCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PROVEEDOR")); // NOI18N
        pnlDatosGenerales.add(lblProveedorCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 163, 120, 20));

        lblCostoUNDCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCostoUNDCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND")); // NOI18N
        pnlDatosGenerales.add(lblCostoUNDCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 192, 130, 20));

        txtCostoUNDCompuesto.setEditable(false);
        txtCostoUNDCompuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoUNDCompuesto.setToolTipText("");
        txtCostoUNDCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoUNDCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtCostoUNDCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 191, 104, 23));

        lblCostoPROMCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoPROMCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOPROM")); // NOI18N
        pnlDatosGenerales.add(lblCostoPROMCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 192, 133, 20));

        txtCostoPROMCompuesto.setEditable(false);
        txtCostoPROMCompuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoPROMCompuesto.setToolTipText("");
        txtCostoPROMCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoPROMCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtCostoPROMCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 191, 105, 23));

        txtPrecioPromCompuesto.setEditable(false);
        txtPrecioPromCompuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioPromCompuesto.setToolTipText("");
        txtPrecioPromCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecioPromCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtPrecioPromCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 220, 105, 23));

        lblCostoPromCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCostoPromCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOPROMOCION")); // NOI18N
        pnlDatosGenerales.add(lblCostoPromCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 221, 133, 20));

        txtPrecioEstandarCompuesto.setEditable(false);
        txtPrecioEstandarCompuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioEstandarCompuesto.setToolTipText("");
        txtPrecioEstandarCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecioEstandarCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        pnlDatosGenerales.add(txtPrecioEstandarCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 104, 23));

        lblPrecioEstandarCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrecioEstandarCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOESTANDAR")); // NOI18N
        pnlDatosGenerales.add(lblPrecioEstandarCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 221, 120, 20));
        pnlDatosGenerales.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 249, 480, -1));

        jScrollPane1.setViewportView(existenciasTbl);

        pnlDatosGenerales.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 298, 480, 240));

        header1.setForeground(new java.awt.Color(255, 153, 0));
        header1.setPreferredSize(new java.awt.Dimension(51, 35));
        header1.setTitle(bundle.getString("CONTAC.FORM.MOVIMIENTOINVENTARIO.INVENTARIOALMACEN")); // NOI18N
        header1.setTitleForeground(new java.awt.Color(255, 153, 0));
        pnlDatosGenerales.add(header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 262, 480, -1));

        pnlMovimiento.add(pnlDatosGenerales, java.awt.BorderLayout.WEST);

        pnlDetalleMovimientos.setPreferredSize(new java.awt.Dimension(300, 538));
        pnlDetalleMovimientos.setLayout(new java.awt.BorderLayout());

        header2.setForeground(new java.awt.Color(255, 153, 0));
        header2.setPreferredSize(new java.awt.Dimension(51, 35));
        header2.setTitle(bundle.getString("CONTAC.FORM.MOVIMIENTOINVENTARIO.REGISTROMOVIMIENTO")); // NOI18N
        header2.setTitleForeground(new java.awt.Color(255, 153, 0));
        pnlDetalleMovimientos.add(header2, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 470));

        movimientosTbl.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        movimientosTbl.setMinimumSize(new java.awt.Dimension(60, 100));
        movimientosTbl.setPreferredSize(new java.awt.Dimension(300, 100));
        jScrollPane2.setViewportView(movimientosTbl);

        pnlDetalleMovimientos.add(jScrollPane2, java.awt.BorderLayout.PAGE_END);

        jToolBar1.setRollover(true);

        lblFechaDesde.setText(bundle.getString("CONTAC.FORM.MOVIMIENTOINVENTARIO.FECHADESDE")); // NOI18N
        lblFechaDesde.setMaximumSize(new java.awt.Dimension(75, 14));
        lblFechaDesde.setMinimumSize(new java.awt.Dimension(75, 14));
        lblFechaDesde.setPreferredSize(new java.awt.Dimension(75, 14));
        jToolBar1.add(lblFechaDesde);
        jToolBar1.add(dtpFechaDesde);

        lblFechaHasta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaHasta.setText(bundle.getString("CONTAC.FORM.MOVIMIENTOINVENTARIO.FECHAHASTA")); // NOI18N
        lblFechaHasta.setMaximumSize(new java.awt.Dimension(75, 14));
        lblFechaHasta.setMinimumSize(new java.awt.Dimension(75, 14));
        lblFechaHasta.setPreferredSize(new java.awt.Dimension(75, 14));
        jToolBar1.add(lblFechaHasta);
        jToolBar1.add(dtpFechaHasta);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/search.png")));
        btnBuscar.setText(bundle.getString("CONTAC.FORM.BTNBUSCAR")); // NOI18N
        btnBuscar.setFocusable(false);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBuscar);

        pnlDetalleMovimientos.add(jToolBar1, java.awt.BorderLayout.CENTER);

        pnlMovimiento.add(pnlDetalleMovimientos, java.awt.BorderLayout.CENTER);

        pnlExistenciaProducto.add(pnlMovimiento, java.awt.BorderLayout.PAGE_START);

        movimientoInventarioTab.addTab(bundle.getString("CONTAC.FORM.MOVIMIENTOINVENTARIO.TAB.EXISTENCIA"), pnlExistenciaProducto); // NOI18N

        add(movimientoInventarioTab, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed

        try {

            if (KeyEvent.VK_ENTER == evt.getKeyChar()) {

                //Buscar producto por codigo
                Producto producto = controller.buscarProducto(txtCodigo.getText());

                //Cargar datos producto compuesto
                cargarDatosProducto(producto);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed

        try {

            //--<Open Busqueda producto JDialog for selecting clasificador>
            Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

            //Setting codigo producto selected
            if (producto != null) {
                cargarDatosProducto(producto);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDA"));
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {

            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Consultando listado de facturas de clientes por fecha
            controller.setFechaDesde(fechaDesde);
            controller.setFechaHasta(fechaHasta);
            controller.buscarMovimientosInventario();

            //Iniciar tabla movimientos inventarios
            initValuesMovimientosInventario();

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    //Inicializar datos del producto
    private void cargarDatosProducto(Producto producto) {

        try {

            txtCodigo.setText(producto.getCodigo());
            txtNombreCompuesto.setText(producto.getNombre());
            txtCodigoFabricanteCompuesto.setText(producto.getCodigoFabricante());
            txtMarcaCompuesto.setText(producto.getMarca());
            txtCodigoCBSCompuesto.setText(producto.getCodigoCbs() + "-" + producto.getClasificador().getDescripcion());
            txtUnidadMedidaCompuesto.setText(producto.getUnidadMedida().getNombre());
            txtLineaProductoCompuesto.setText(producto.getLinea().getNombre());
            txtProveedorCompuesto.setText(producto.getProveedor().getCodigo() + "-" + producto.getProveedor().getRazonSocial());
            txtCostoUNDCompuesto.setText(producto.getCostoUND().toString());
            txtCostoPROMCompuesto.setText(producto.getCostoPROM().toString());
            txtPrecioEstandarCompuesto.setText(producto.getPrecioESTANDAR().toString());
            txtPrecioPromCompuesto.setText(producto.getPrecioPROMOCION() != null ? producto.getPrecioPROMOCION().toString() : "0.00");

            //Buscar existencias del producto
            controller.setCodigo(producto.getCodigo());
            //Buscar producto existencias
            controller.buscarProductoExistencias();

            //Init values
            initValues();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarProducto;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;
    private javax.swing.JTable existenciasTbl;
    private org.jdesktop.swingx.JXHeader header;
    private org.jdesktop.swingx.JXHeader header1;
    private org.jdesktop.swingx.JXHeader header2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCodigoCBSCompuesto;
    private javax.swing.JLabel lblCodigoCompuesto;
    private javax.swing.JLabel lblCodigoFabricanteCompuesto;
    private javax.swing.JLabel lblCostoPROMCompuesto;
    private javax.swing.JLabel lblCostoPromCompuesto;
    private javax.swing.JLabel lblCostoUNDCompuesto;
    private javax.swing.JLabel lblFechaDesde;
    private javax.swing.JLabel lblFechaHasta;
    private javax.swing.JLabel lblLineaProductoCompuesto;
    private javax.swing.JLabel lblMarcaCompuesto;
    private javax.swing.JLabel lblNombreCompuesto;
    private javax.swing.JLabel lblPrecioEstandarCompuesto;
    private javax.swing.JLabel lblProveedorCompuesto;
    private javax.swing.JLabel lblUnidadMedidaCompuesto;
    private javax.swing.JTabbedPane movimientoInventarioTab;
    private javax.swing.JTable movimientosTbl;
    private javax.swing.JPanel pnlDatosGenerales;
    private javax.swing.JPanel pnlDetalleMovimientos;
    private javax.swing.JPanel pnlExistenciaProducto;
    private javax.swing.JPanel pnlMovimiento;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCBSCompuesto;
    private javax.swing.JTextField txtCodigoFabricanteCompuesto;
    private javax.swing.JTextField txtCostoPROMCompuesto;
    private javax.swing.JTextField txtCostoUNDCompuesto;
    private javax.swing.JTextField txtLineaProductoCompuesto;
    private javax.swing.JTextField txtMarcaCompuesto;
    private javax.swing.JTextField txtNombreCompuesto;
    private javax.swing.JTextField txtPrecioEstandarCompuesto;
    private javax.swing.JTextField txtPrecioPromCompuesto;
    private javax.swing.JTextField txtProveedorCompuesto;
    private javax.swing.JTextField txtUnidadMedidaCompuesto;
    // End of variables declaration//GEN-END:variables

    private Producto productoSelected;
}
