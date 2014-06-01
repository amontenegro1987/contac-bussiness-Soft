/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.inventarios.movimiento.inventario.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.LineaComboBoxModel;
import contac.commons.models.comboBox.ProveedorComboBoxModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.AdministrarInventarioController;
import contac.modelo.entity.*;
import contac.reports.JRPrintReport;
import contac.text.TextUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 *
 * @author Eddy Montenegro
 * @version 2.0.8
 *          Date: 07-29-13
 *          Time: 10:57 PM
 */
public class pnlAdmonInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlMovimientoInventario.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Controller
    private AdministrarInventarioController controller;

    //Components
    private JXHeader header;

    private JLabel lblCodigoDesde;
    private JLabel lblCodigoHasta;
    private JLabel lblLinea;
    private JLabel lblProveedor;
    private JLabel lblAlmacen;

    private JTextField txtCodigoDesde;
    private JTextField txtCodigoHasta;
    private JComboBox cmbLinea;
    private JComboBox cmbProveedor;
    private JComboBox cmbAlmacen;

    private JXTable tblProductos;

    private JCheckBox chkMostrarCostos;

    private JButton btnImprimir;
    private JButton btnAnular;

    private JButton btnBuscar;
    private JButton btnCancelar;

    /**
     * Administrat Inventario Constructor
     *
     * @param frame, GenericFrame
     */
    public pnlAdmonInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "AdmonInventario", "Administrar Inventarios", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new AdministrarInventarioController();

        //Controller init properties
        controller.init();

        //Init components
        initComponents();

        //Init values
        initValues();

        //Register listeners
        registerListeners();
    }

    /**
     * Init Components UI
     */
    public void initComponents() {
        //***************************************************************************************
        //Init Header Panel
        //***************************************************************************************
        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.ADMINISTRAINVENTARIO.TITLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //*********************************************************************
        //Create Search Panel
        //*********************************************************************
        JPanel searchPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.setPreferredSize(new Dimension(340, 400));

        lblCodigoDesde = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAINVENTARIO.CODIGODESDE"));
        lblCodigoDesde.setHorizontalAlignment(JLabel.LEFT);

        lblCodigoHasta = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAINVENTARIO.CODIGOHASTA"));
        lblCodigoHasta.setHorizontalAlignment(JLabel.LEFT);

        lblLinea = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.LINEA"));
        lblLinea.setHorizontalAlignment(JLabel.LEFT);

        lblProveedor = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PROVEEDOR"));
        lblProveedor.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacen = new JLabel(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(JLabel.LEFT);

        txtCodigoDesde = new JTextField();
        txtCodigoHasta = new JTextField();

        cmbLinea = new JComboBox();
        cmbProveedor = new JComboBox();
        cmbAlmacen = new JComboBox();

        chkMostrarCostos = new JCheckBox();
        chkMostrarCostos.setText(messageBundle.getString("CONTAC.FORM.BTNMOSTRARCOSTOS"));
        chkMostrarCostos.setSelected(false);

        ImageIcon buscarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/search.png"));
        ImageIcon cancelarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnBuscar = new JButton(messageBundle.getString("CONTAC.FORM.BTNBUSCAR"));
        btnBuscar.setIcon(buscarIco);

        btnCancelar = new JButton(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setIcon(cancelarIco);

        searchPanel.add(lblCodigoDesde, new XYConstraints(5, 5, 120, 23));
        searchPanel.add(txtCodigoDesde, new XYConstraints(130, 5, 120, 23));
        searchPanel.add(lblCodigoHasta, new XYConstraints(5, 33, 120, 23));
        searchPanel.add(txtCodigoHasta, new XYConstraints(130, 33, 120, 23));
        searchPanel.add(lblLinea, new XYConstraints(5, 61, 120, 23));
        searchPanel.add(cmbLinea, new XYConstraints(130, 61, 200, 23));
        searchPanel.add(lblProveedor, new XYConstraints(5, 89, 120, 23));
        searchPanel.add(cmbProveedor, new XYConstraints(130, 89, 200, 23));
        searchPanel.add(lblAlmacen, new XYConstraints(5, 117, 90, 23));
        searchPanel.add(cmbAlmacen, new XYConstraints(130, 117, 200, 23));
        searchPanel.add(chkMostrarCostos, new XYConstraints(130, 145, 200, 23));
        searchPanel.add(btnBuscar, new XYConstraints(75, 185, 90, 23));
        searchPanel.add(btnCancelar, new XYConstraints(175, 185, 90, 23));

        //*********************************************************************
        //Create Productos Table
        //*********************************************************************

        ImageIcon printIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
        ImageIcon anularIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnImprimir = new JButton();
        btnImprimir.setPreferredSize(new Dimension(40, 32));
        btnImprimir.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIR"));
        btnImprimir.setIcon(printIco);

        btnAnular = new JButton();
        btnAnular.setPreferredSize(new Dimension(40, 32));
        btnAnular.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNANULAR"));
        btnAnular.setIcon(anularIco);

        JPanel productosPanel = new JPanel(new BorderLayout());
        productosPanel.setBorder(BorderFactory.createEtchedBorder());

        JToolBar actionToolBar = new JToolBar();
        actionToolBar.setPreferredSize(new Dimension(500, 32));

        actionToolBar.add(btnAnular);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnImprimir);

        tblProductos = new JXTable();

        JScrollPane productosScrollbar = new JScrollPane();
        productosScrollbar.getViewport().add(tblProductos);

        productosPanel.add(actionToolBar, BorderLayout.NORTH);
        productosPanel.add(productosScrollbar, BorderLayout.CENTER);

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(productosPanel, BorderLayout.CENTER);
    }

    @Override
    public void initValues() {

        try {

            txtCodigoDesde.setText("");
            txtCodigoHasta.setText("");

            chkMostrarCostos.setSelected(false);

            cmbLinea.setModel(new LineaComboBoxModel(controller.getLineasProducto()));
            ListCellRenderer rendererLinea = new ComboBoxEmptySelectionRenderer(cmbLinea, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
            cmbLinea.setRenderer(rendererLinea);
            cmbLinea.setSelectedIndex(-1);

            cmbProveedor.setModel(new ProveedorComboBoxModel(controller.getProveedores()));
            ListCellRenderer rendererProveedor = new ComboBoxEmptySelectionRenderer(cmbProveedor, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
            cmbProveedor.setRenderer(rendererProveedor);
            cmbProveedor.setSelectedIndex(-1);

            cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.buscarAlmacenes()));
            ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
            cmbAlmacen.setRenderer(rendererAlmacen);
            cmbAlmacen.setSelectedIndex(-1);

            controller.setCodigoDesde("");
            controller.setCodigoHasta("");
            controller.setLinea(null);
            controller.setProveedor(null);
            controller.setAlmacen(null);

            controller.setProductos(new ArrayList<Producto>());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }

    /**
     * Init Result Table Values
     */
    public void initResultTable() {

        if (chkMostrarCostos.isSelected()) {

            //Column names JTable
            String[] columnNames = {"Id", "Codigo", "Nombre", "Codigo Fabricante", "Existencia", "Costo PROM", "Costo UND", "Precio"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (Producto producto : controller.getProductos()) {
                Object[] row = new Object[8];
                row[0] = producto.getId();
                row[1] = producto.getCodigo();
                row[2] = producto.getNombre();
                row[3] = producto.getCodigoFabricante();

                long _cant_existencia = 0;
                for (ProductoExistencia existencia : producto.getExistencias()) {
                    _cant_existencia += existencia.getExistencia();
                }

                row[4] = _cant_existencia;
                row[5] = producto.getCostoPROM();
                row[6] = producto.getCostoUND();
                row[7] = producto.getPrecioESTANDAR();

                tableModel.addRow(row);
            }

            tblProductos.setModel(tableModel);

            tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
            tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            tblProductos.getColumnModel().getColumn(0).setWidth(0);

            DefaultTableCellRenderer rightAligment = new DefaultTableCellRenderer();
            rightAligment.setHorizontalAlignment(JLabel.RIGHT);
            tblProductos.getColumn(4).setCellRenderer(rightAligment);

            DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
            decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
            tblProductos.getColumn(5).setCellRenderer(decimalFormatRenderer);
            tblProductos.getColumn(6).setCellRenderer(decimalFormatRenderer);
            tblProductos.getColumn(7).setCellRenderer(decimalFormatRenderer);

        }

        if (!chkMostrarCostos.isSelected()) {

            //Column names JTable
            String[] columnNames = {"Id", "Codigo", "Nombre", "Codigo Fabricante", "Existencia", "Precio"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (Producto producto : controller.getProductos()) {
                Object[] row = new Object[6];
                row[0] = producto.getId();
                row[1] = producto.getCodigo();
                row[2] = producto.getNombre();
                row[3] = producto.getCodigoFabricante();

                long _cant_existencia = 0;
                for (ProductoExistencia existencia : producto.getExistencias()) {
                    _cant_existencia += existencia.getExistencia();
                }

                row[4] = _cant_existencia;
                row[5] = producto.getPrecioESTANDAR();

                tableModel.addRow(row);
            }

            tblProductos.setModel(tableModel);

            tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
            tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            tblProductos.getColumnModel().getColumn(0).setWidth(0);

            DefaultTableCellRenderer rightAligment = new DefaultTableCellRenderer();
            rightAligment.setHorizontalAlignment(JLabel.RIGHT);
            tblProductos.getColumn(4).setCellRenderer(rightAligment);

            DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
            decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
            tblProductos.getColumn(5).setCellRenderer(decimalFormatRenderer);
        }


        tblProductos.setEditable(false);
        tblProductos.setCellSelectionEnabled(false);
        tblProductos.setRowSelectionAllowed(true);
        tblProductos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        tblProductos.packAll();
    }

    /**
     * Register Components Listeners
     */
    public void registerListeners() {

        txtCodigoDesde.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

            }
        });

        txtCodigoHasta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        btnImprimir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImprimirPopupMenu menu = new ImprimirPopupMenu();
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        btnAnular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAnularActionPerformed(e);
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    controller.setCodigoDesde(txtCodigoDesde.getText());
                    controller.setCodigoHasta(txtCodigoHasta.getText());
                    controller.setLinea(cmbLinea.getSelectedIndex() != -1 ?
                            (Linea) ((LineaComboBoxModel) cmbLinea.getModel()).getSelectedItem().getObject() : null);
                    controller.setAlmacen(cmbAlmacen.getSelectedIndex() != -1 ?
                            (Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject() : null);
                    controller.setProveedor(cmbProveedor.getSelectedIndex() != -1 ?
                            (Proveedor) ((ProveedorComboBoxModel) cmbProveedor.getModel()).getSelectedItem().getObject() : null);
                    controller.setExistencias(true);

                    //Buscar Productos
                    controller.buscarProductos();

                    //Init Table Model
                    initResultTable();

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                            e.getMessage());
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initValues();
                initResultTable();
            }
        });
    }

    /**
     * Print Inventory Report
     *
     * @param event, ActionEvent
     */
    private void btnImprimirSolicitudInventarioActionPerformed(ActionEvent event) {

        try {

            //Param codigo desde
            controller.setCodigoDesde(txtCodigoDesde.getText().equals("") ? "-1" : txtCodigoDesde.getText());
            controller.setCodigoHasta(txtCodigoHasta.getText().equals("") ? "-1" : txtCodigoHasta.getText());
            controller.setLinea(cmbLinea.getSelectedIndex() != -1 ?
                    (Linea) ((LineaComboBoxModel) cmbLinea.getModel()).getSelectedItem().getObject() : null);
            controller.setAlmacen(cmbAlmacen.getSelectedIndex() != -1 ?
                    (Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject() : null);
            controller.setProveedor(cmbProveedor.getSelectedIndex() != -1 ?
                    (Proveedor) ((ProveedorComboBoxModel) cmbProveedor.getModel()).getSelectedItem().getObject() : null);
            controller.setExistencias(true);

            // Prepared Jasper Report
            JasperReport report = (JasperReport) JRLoader.loadObject(pnlAdmonInventario.class
                        .getResourceAsStream("/contac/inventarios/app/reportes/solicitud_inventario_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");
            parameters.put("p_codigo_desde", controller.getCodigoDesde());
            parameters.put("p_codigo_hasta", controller.getCodigoHasta());
            parameters.put("p_id_linea", controller.getLinea() != null ? controller.getLinea().getId() : -1);
            parameters.put("p_codigo_proveedor", controller.getProveedor() != null ? controller.getProveedor().getCodigo() : -1);
            parameters.put("p_id_almacen", controller.getAlmacen() != null ? controller.getAlmacen().getId() : -1);

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (JRException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    /**
     * Print Inventory Report
     *
     * @param event, ActionEvent
     */
    private void btnImprimirExistenciasActionPerformed(ActionEvent event) {

        try {

            //Param codigo desde
            controller.setCodigoDesde(txtCodigoDesde.getText().equals("") ? "-1" : txtCodigoDesde.getText());
            controller.setCodigoHasta(txtCodigoHasta.getText().equals("") ? "-1" : txtCodigoHasta.getText());
            controller.setLinea(cmbLinea.getSelectedIndex() != -1 ?
                    (Linea) ((LineaComboBoxModel) cmbLinea.getModel()).getSelectedItem().getObject() : null);
            controller.setAlmacen(cmbAlmacen.getSelectedIndex() != -1 ?
                    (Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject() : null);
            controller.setProveedor(cmbProveedor.getSelectedIndex() != -1 ?
                    (Proveedor) ((ProveedorComboBoxModel) cmbProveedor.getModel()).getSelectedItem().getObject() : null);
            controller.setExistencias(true);

            // Prepared Jasper Report
            JasperReport report;
            if (chkMostrarCostos.isSelected()) {
                report = (JasperReport) JRLoader.loadObject(pnlAdmonInventario.class
                        .getResourceAsStream("/contac/inventarios/app/reportes/inventario_report.jasper"));
            } else {
                report = (JasperReport) JRLoader.loadObject(pnlAdmonInventario.class
                        .getResourceAsStream("/contac/inventarios/app/reportes/catalogo_report.jasper"));
            }

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");
            parameters.put("p_codigo_desde", controller.getCodigoDesde());
            parameters.put("p_codigo_hasta", controller.getCodigoHasta());
            parameters.put("p_id_linea", controller.getLinea() != null ? controller.getLinea().getId() : -1);
            parameters.put("p_codigo_proveedor", controller.getProveedor() != null ? controller.getProveedor().getCodigo() : -1);
            parameters.put("p_id_almacen", controller.getAlmacen() != null ? controller.getAlmacen().getId() : -1);

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (JRException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    /**
     * Print Inventory Report
     *
     * @param event, ActionEvent
     */
    private void btnImprimirExistenciasAlmacenActionPerformed(ActionEvent event) {

        try {

            //Param codigo desde
            controller.setCodigoDesde(txtCodigoDesde.getText().equals("") ? "-1" : txtCodigoDesde.getText());
            controller.setCodigoHasta(txtCodigoHasta.getText().equals("") ? "-1" : txtCodigoHasta.getText());
            controller.setProveedor(cmbProveedor.getSelectedIndex() != -1 ?
                    (Proveedor) ((ProveedorComboBoxModel) cmbProveedor.getModel()).getSelectedItem().getObject() : null);

            //controller.setExistencias(true);

            // Prepared Jasper Report
            JasperReport report;

                report = (JasperReport) JRLoader.loadObject(pnlAdmonInventario.class
                        .getResourceAsStream("/contac/inventarios/app/reportes/existencia_almacenes_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");
            parameters.put("p_codigo_desde", controller.getCodigoDesde());
            parameters.put("p_codigo_hasta", controller.getCodigoHasta());
            parameters.put("p_codigo_proveedor", controller.getProveedor() != null ? controller.getProveedor().getCodigo() : -1);

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (JRException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    /**
     * Print Inventory Report
     *
     * @param event, ActionEvent
     */
    private void btnImprimirExistenciasInventarioLineasActionPerformed(ActionEvent event) {

        try {

            // Prepared Jasper Report
            JasperReport report = (JasperReport) JRLoader.loadObject(pnlAdmonInventario.class
                    .getResourceAsStream("/contac/inventarios/app/reportes/existencias_inventario_mensuales_por_linea_dep.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (JRException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }
    /**
     * Anular Inventory Products
     *
     * @param event, ActionEvent
     */
    private void btnAnularActionPerformed(ActionEvent event) {

        try {

            // Getting selected rows
            int[] rows = tblProductos.getSelectedRows();

            if (rows.length > 0) {

                List<Integer> productos = new ArrayList<Integer>();
                for (int i = 0; i < rows.length; i++) {
                    Integer idProducto = (Integer) tblProductos.getModel().getValueAt(rows[i], 0);
                    productos.add(idProducto);
                }

                //Prepared for delete products
                Object options[] = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(this, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.DARDEBAJA"),
                        "Warning", 0, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                if (n == 0) {
                    //Anular Listado de Productos
                    controller.anularProductos(productos);

                    //Buscar Productos
                    controller.buscarProductos();

                    //Init Table Model
                    initResultTable();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }

    /**
     * Imprimir Popup Menu
     */
    public class ImprimirPopupMenu extends JPopupMenu {

        /**
         * Menu Reporte Existencias
         */
        JMenuItem mnuReporteExistencias;

        /**
         * Menu Reporte Solicitud Inventario
         */
        JMenuItem mnuReporteSolicitudInventario;

        /**
         * Menu Reporte Solicitud Inventario
         */
        JMenuItem mnuReporteExistenciaInventarioLinea;

        /**
         * Menu Reporte Existencias Almacen Consolidado
         */
        JMenuItem mnuReporteExistenciasAlmacen;

        /**
         * Imprimir Popup Menu Constructor
         */
        public ImprimirPopupMenu() {

            ImageIcon mnuIcon_existencias = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
            ImageIcon mnuIcon_inventario = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
            ImageIcon menuIcon_existencias_almacen = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));

            mnuReporteExistencias = new JMenuItem(messageBundle.getString("CONTAC.FORM.MNUREPORTEEXISTENCIAS"));
            mnuReporteExistencias.setIcon(mnuIcon_existencias);

            mnuReporteSolicitudInventario = new JMenuItem(messageBundle.getString("CONTAC.FORM.MNUREPORTEINVENTARIO"));
            mnuReporteSolicitudInventario.setIcon(mnuIcon_inventario);

            mnuReporteExistenciasAlmacen = new JMenuItem(messageBundle.getString("CONTAC.FORM.MNUREPORTEEXISTENCIASALMACEN"));
            mnuReporteExistenciasAlmacen.setIcon(menuIcon_existencias_almacen);

            mnuReporteExistenciaInventarioLinea = new JMenuItem(messageBundle.getString("CONTAC.FORM.MNUREPORTEEXISTENCIASALMACENPORLINEA"));
            mnuReporteExistenciaInventarioLinea.setIcon(menuIcon_existencias_almacen);

            add(mnuReporteExistencias);
            add(mnuReporteSolicitudInventario);
            add(mnuReporteExistenciasAlmacen);
            add(mnuReporteExistenciaInventarioLinea);

            //Init Components Listeners
            initComponentsListeners();
        }

        /**
         * Init Components Listeners
         */
        public void initComponentsListeners() {

            mnuReporteExistenciasAlmacen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnImprimirExistenciasAlmacenActionPerformed(e);
                }
            });

            mnuReporteExistencias.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnImprimirExistenciasActionPerformed(e);
                }
            });

            mnuReporteSolicitudInventario.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnImprimirSolicitudInventarioActionPerformed(e);
                }
            });

            mnuReporteExistenciaInventarioLinea.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnImprimirExistenciasInventarioLineasActionPerformed(e);
                }
            });
        }

    }
}