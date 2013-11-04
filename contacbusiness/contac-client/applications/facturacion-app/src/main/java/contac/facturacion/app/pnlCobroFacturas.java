/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlRegistroFacturas.java
 *
 * Created on 11-05-2012, 03:10:19 PM
 */
package contac.facturacion.app;

import java.awt.event.ActionListener;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.form.render.TipoFacturaRenderer;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.TipoFacturaComboBoxModel;
import contac.commons.models.tables.BeanTableModel;
import contac.facturacion.controller.FacturaClienteController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.reports.JRPrintReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author EMontenegro
 */
public class pnlCobroFacturas extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlCobroFacturas.class);

    //Controller
    private FacturaClienteController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Components
    private JXHeader header;
    Integer numeroDeColumnas; // = facturaBeanTableModel.getRowCount();

    /**
     * Creates new form pnlCobroFacturas
     */
    public pnlCobroFacturas(GenericFrame frame) {

        //Call super constructor
        super(frame, "CobroFacturasClientes", "Cobro de Facturas", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new FacturaClienteController();

        //Integer idEstadoPagado = 7;
        int[] idEstadoPagado = new int[]{1,7,8};


        try {

           //Iniciar registro de datos
            controller.initRegistroFacturaCobros(idEstadoPagado);

            //Init components
            initComponents();

            //Init values
            initValues();

            //Init Result Table
            initResultTable();

            //Init Action Listeners
            initActionListeners();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }

    /**
     * Init Components UI
     */
    private void initComponents() {

        //***************************************************************************************
        //Init Header Panel
        //***************************************************************************************
        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.COBROFACTURAS.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //*********************************************************************
        //Create Search Panel
        //*********************************************************************
        JPanel searchPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.setPreferredSize(new Dimension(340, 400));

        lblFacturaNo = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.NOFACTURA"));
        lblFacturaNo.setHorizontalAlignment(JLabel.LEFT);

        lblFechaDesde = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROFACTURAS.FECHADESDE"));
        lblFechaDesde.setHorizontalAlignment(JLabel.LEFT);

        lblFechaHasta = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROFACTURAS.FECHAHASTA"));
        lblFechaHasta.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacen = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(JLabel.LEFT);

        lblTipoFactura = new JLabel(messageBundle.getString("CONTAC.FORM.FACTURACION.TIPOFACTURA"));
        lblTipoFactura.setHorizontalAlignment(JLabel.LEFT);

        txtNoFactura = new JTextField();

        dtpFechaDesde = new JXDatePicker();
        dtpFechaHasta = new JXDatePicker();

        cmbTipoFactura = new JComboBox();
        cmbAlmacen = new JComboBox();

        ImageIcon buscarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/search.png"));
        ImageIcon cancelarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnBuscar = new JButton(messageBundle.getString("CONTAC.FORM.BTNBUSCAR"));
        btnBuscar.setIcon(buscarIco);

        btnCancelar = new JButton(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setIcon(cancelarIco);

        searchPanel.add(lblFacturaNo, new XYConstraints(5,45,120,23));
        searchPanel.add(txtNoFactura, new XYConstraints(90,45,200,23));
        searchPanel.add(lblFechaDesde, new XYConstraints(5, 95, 120, 23));
        searchPanel.add(dtpFechaDesde, new XYConstraints(90, 95, 200, 23));
        searchPanel.add(lblFechaHasta, new XYConstraints(5, 123, 120, 23));
        searchPanel.add(dtpFechaHasta, new XYConstraints(90, 123, 200, 23));
        searchPanel.add(lblTipoFactura, new XYConstraints(5, 151, 120, 23));
        searchPanel.add(cmbTipoFactura, new XYConstraints(90, 151, 200, 23));
        searchPanel.add(lblAlmacen, new XYConstraints(5, 179, 120, 23));
        searchPanel.add(cmbAlmacen, new XYConstraints(90, 179, 200, 23));
        searchPanel.add(btnBuscar, new XYConstraints(55, 220, 90, 23));
        searchPanel.add(btnCancelar, new XYConstraints(155, 220, 90, 23));

        //*********************************************************************
        //Create Facturas table
        //*********************************************************************

        ImageIcon cobrarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/reward.png"));

        ImageIcon imprimirIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
        ImageIcon actualizarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/reload.png"));

        btnImprimir = new JButton();
        btnImprimir.setPreferredSize(new Dimension(40, 32));
        btnImprimir.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIR"));
        btnImprimir.setIcon(imprimirIco);

        btnActualizar = new JButton();
        btnActualizar.setPreferredSize(new Dimension(40, 32));
        btnActualizar.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNACTUALIZAR"));
        btnActualizar.setIcon(actualizarIco);

        btnCobrarFactura = new JButton();
        btnCobrarFactura.setPreferredSize(new Dimension(130, 32));
        btnCobrarFactura.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNCOBRARFACTURA"));
        btnCobrarFactura.setIcon(cobrarIco);

        //Integer numeroDeColumnas = facturaBeanTableModel.getRowCount();

        JPanel estadisticasPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        estadisticasPanel.setPreferredSize(new Dimension(200,100));
        /***********************************************************************************************/

        JPanel pnlTotalFacturas = new JPanel(new XYLayout());
        pnlTotalFacturas.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalFacturas.setPreferredSize(new Dimension(50,95));

        JLabel lblNumeroFacturas = new JLabel();
        lblNumeroFacturas.setText("No. Facturas");
        lblNumeroFacturas.setHorizontalAlignment(SwingConstants.CENTER);

        Font newLabelFont=new Font(lblNumeroFacturas.getFont().getName(),Font.BOLD,lblNumeroFacturas.getFont().getSize());
        lblNumeroFacturas.setFont(newLabelFont);

        JLabel lblNumeroFacturasDato = new JLabel();
        lblNumeroFacturasDato.setText(String.valueOf(numeroDeColumnas));
        lblNumeroFacturasDato.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNumeroFacturasDato.setHorizontalAlignment(SwingConstants.CENTER);

        Font newLabelFont2=new Font(lblNumeroFacturasDato.getFont().getName(),Font.BOLD,lblNumeroFacturasDato.getFont().getSize());
        lblNumeroFacturasDato.setFont(newLabelFont2);

        JLabel lblNumeroFacturasResultado = new JLabel();
        lblNumeroFacturasResultado.setText("registros encontrados");
        lblNumeroFacturasResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumeroFacturasResultado.setFont(newLabelFont);


        pnlTotalFacturas.add(lblNumeroFacturas, new XYConstraints(1,15,225,15));
        pnlTotalFacturas.add(lblNumeroFacturasDato, new XYConstraints(1,35,225,15));
        pnlTotalFacturas.add(lblNumeroFacturasResultado, new XYConstraints(1,55,225,15));

        /***********************************************************************************************/

        JPanel pnlTotalFacturado = new JPanel(new XYLayout());
        pnlTotalFacturado.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalFacturado.setPreferredSize(new Dimension(50,95));

        JLabel lblTotalFacturado = new JLabel();
        lblTotalFacturado.setText("Total Facturado");
        lblTotalFacturado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFacturado.setFont(newLabelFont);

        JLabel lblTotalFacturadoDato = new JLabel();
        lblTotalFacturadoDato.setText("C$ ");
        lblTotalFacturadoDato.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFacturadoDato.setForeground(Color.blue);
        lblTotalFacturadoDato.setFont(new Font("Serif", Font.PLAIN, 18));
        lblTotalFacturadoDato.setFont(newLabelFont2);

        JLabel lblTotalFacturadoDatoResultado = new JLabel();
        lblTotalFacturadoDatoResultado.setText("Media por factura ()");
        lblTotalFacturadoDatoResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFacturadoDatoResultado.setFont(newLabelFont);

        pnlTotalFacturado.add(lblTotalFacturado, new XYConstraints(1,15,225,15));
        pnlTotalFacturado.add(lblTotalFacturadoDato, new XYConstraints(1,35,225,15));
        pnlTotalFacturado.add(lblTotalFacturadoDatoResultado, new XYConstraints(1,55,225,15));

        /***********************************************************************************************/

        JPanel pnlTotalPagado = new JPanel(new XYLayout());
        pnlTotalPagado.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalPagado.setPreferredSize(new Dimension(50,95));

        JLabel lblTotalPagado = new JLabel();
        lblTotalPagado.setText("Total Pagado");
        lblTotalPagado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPagado.setFont(newLabelFont);

        JLabel lblTotalPagadoDato = new JLabel();
        lblTotalPagadoDato.setText("C$ ");
        lblTotalPagadoDato.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPagadoDato.setForeground(Color.green);
        lblTotalPagadoDato.setFont(new Font("Serif", Font.PLAIN, 18));
        lblTotalPagadoDato.setFont(newLabelFont2);

        JLabel lblTotalPagadoDatoResultado = new JLabel();
        lblTotalPagadoDatoResultado.setText("porcentaje cobrado ()");
        lblTotalPagadoDatoResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPagadoDatoResultado.setFont(newLabelFont);

        pnlTotalPagado.add(lblTotalPagado, new XYConstraints(1,15,225,15));
        pnlTotalPagado.add(lblTotalPagadoDato, new XYConstraints(1,35,225,15));
        pnlTotalPagado.add(lblTotalPagadoDatoResultado, new XYConstraints(1,55,225,15));

        /***********************************************************************************************/

        JPanel pnlTotalPendiente = new JPanel(new XYLayout());
        pnlTotalPendiente.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalPendiente.setPreferredSize(new Dimension(50,95));

        JLabel lblTotalPendiente = new JLabel();
        lblTotalPendiente.setText("Total Pendiente");
        lblTotalPendiente.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPendiente.setFont(newLabelFont);

        JLabel lblTotalPendienteDato = new JLabel();
        lblTotalPendienteDato.setText("C$ ");
        lblTotalPendienteDato.setForeground(Color.RED);
        lblTotalPendienteDato.setFont(new Font("Serif", Font.PLAIN, 18));
        lblTotalPendienteDato.setFont(newLabelFont2);
        lblTotalPendienteDato.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblTotalPendienteDatoResultado = new JLabel();
        lblTotalPendienteDatoResultado.setText("porcentaje pendiente ()");
        lblTotalPendienteDatoResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPendienteDatoResultado.setFont(newLabelFont);

        pnlTotalPendiente.add(lblTotalPendiente, new XYConstraints(1,15,220,15));
        pnlTotalPendiente.add(lblTotalPendienteDato, new XYConstraints(1,35,220,15));
        pnlTotalPendiente.add(lblTotalPendienteDatoResultado, new XYConstraints(1,55,220,15));

        /***********************************************************************************************/
        estadisticasPanel.add(pnlTotalFacturas, new XYConstraints(1,1,225,95));
        estadisticasPanel.add(pnlTotalFacturado, new XYConstraints(226,1,225,95));
        estadisticasPanel.add(pnlTotalPagado, new XYConstraints(451,1,225,95));
        estadisticasPanel.add(pnlTotalPendiente, new XYConstraints(676,1,220,95));

        JToolBar actionToolBar = new JToolBar();
        actionToolBar.setPreferredSize(new Dimension(200, 32));
        actionToolBar.setAlignmentX(JToolBar.RIGHT_ALIGNMENT);
        actionToolBar.add(btnActualizar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnImprimir);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnCobrarFactura);
        actionToolBar.add(new JToolBar.Separator());

        JPanel facturasPanel = new JPanel(new BorderLayout());
        facturasPanel.setBorder(BorderFactory.createEtchedBorder());
        tblFacturasClientes = new JXTable();

        JScrollPane facturasScrollbar = new JScrollPane();
        facturasScrollbar.getViewport().add(tblFacturasClientes);

        facturasPanel.add(actionToolBar, BorderLayout.NORTH);
        facturasPanel.add(facturasScrollbar, BorderLayout.CENTER);
        facturasPanel.add(estadisticasPanel, BorderLayout.SOUTH);

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(facturasPanel, BorderLayout.CENTER);
    }

    @Override
    public void initValues() {

        //****************************************************************************
        //Init campos de busqueda
        //****************************************************************************

        //Numero de factura
        txtNoFactura.setText("");

        //Fecha desde
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaDesde.setDate(new Date());

        //Fecha hasta
        dtpFechaHasta.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setDate(new Date());

        //***************************************************************************
        //Combo box tipo de factura
        //***************************************************************************
        cmbTipoFactura.setModel(new TipoFacturaComboBoxModel(TiposFactura.values()));

        ListCellRenderer rendererTipoFactura = new ComboBoxEmptySelectionRenderer(cmbTipoFactura, messageBundle.
                getString("CONTAC.FORM.MSG.SELECCIONE"));
        cmbTipoFactura.setRenderer(rendererTipoFactura);
        cmbTipoFactura.setSelectedIndex(-1);
        cmbTipoFactura.setEnabled(false);

        //***************************************************************************
        //Combo box almacen
        //***************************************************************************
        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));

        ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.
                getString("CONTAC.FORM.MSG.SELECCIONE"));
        AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacen.getModel();
        cmbAlmacen.setRenderer(rendererAlmacen);
        cmbAlmacen.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacen().getId()));
        cmbAlmacen.setEnabled(false);

        try {
            if (controller.checkUserInRole(Roles.ROLFACTURACIONADMIN.toString())) {
                cmbAlmacen.setEnabled(true);
            }
        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    private void initResultTable() {
        //Config table model para lavantamiento inventario fisico
        facturaBeanTableModel = new BeanTableModel<Factura>(Factura.class, DocumentoComercial.class,
                                controller.getFacturas());
        facturaBeanTableModel.setModelEditable(false);
        tblFacturasClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        facturaBeanTableModel.sortColumnNames();
        tblFacturasClientes.setEditable(false);
        tblFacturasClientes.setModel(facturaBeanTableModel);
        tblFacturasClientes.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel tableColumnModel = tblFacturasClientes.getColumnModel();
        String[] columnsRemove = new String[]{"Agente Ventas", "Cliente", "Direccion Entrega", "Exonerada", "Excenta", "Proforma",
                "Pago", "Porc Descuento", "Porc IVA", "Porc Ret Fuente", "Porc Ret Municipal", "Serie", "Id", "Retencion F",
                "Retencion M", "Terminos Pago", "Tasa Cambio", "Ctime", "Cuser", "Mtime", "Muser", "Almacen", "Monto Bruto",
                "Monto IVA", "Monto Descuento", "Retencion Fuente", "Retencion Municipal", "Tipo Factura", "Moneda"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableColumnModel.getColumn(2).setCellRenderer(decimalFormatRenderer);

        //Ordering table columns

        tableColumnModel.moveColumn(3, 0); //No Documento
        tableColumnModel.moveColumn(2, 1); //Fecha alta
        tableColumnModel.moveColumn(4, 2); //Cliente

        //Setting prefered size
//        tableColumnModel.getColumn(4).setPreferredWidth(200);
        tblFacturasClientes.packAll();
        numeroDeColumnas = facturaBeanTableModel.getRowCount();
    }

     private void initActionListeners() {

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            btnBuscarActionPerformed(e);
            }
        });

        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnImprimirActionPerformed(e);
            }
        });

        tblFacturasClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturasClientesMouseClicked(evt);
            }
        });
    }

    private void tblFacturasClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturasClientesMouseClicked
        //Getting factura cliente selected
        rowSelected = tblFacturasClientes.getSelectedRow();
        facturaSelected = (Factura) ((BeanTableModel) tblFacturasClientes.getModel()).getRow(rowSelected);
        controller.setFactura(facturaSelected);

    }//GEN-LAST:event_tblFacturasClientesMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            String numFacturaComparar;
            if(txtNoFactura.getText().isEmpty()){

                //Obteniendo fechas de busqueda
                Date fechaDesde = dtpFechaDesde.getDate();
                Date fechaHasta = dtpFechaHasta.getDate();

                //Obtener parametros de busqueda
                Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                        getObject());

                TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                        ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                        null;

                //Consultando listado de facturas de clientes por fecha
                controller.buscarFacturasClientesCobrosPorFechas(fechaDesde, fechaHasta, almacen.getId(),
                        tiposFactura != null ? 2 : 2);

                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();

            }
            else{
                Long numeroFactura = Long.parseLong(txtNoFactura.getText());

                TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                        ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                        null;

                controller.buscarFacturasClientesCobrosPorNo(numeroFactura,
                        tiposFactura != null ? 2 : 2);

                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();
                }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarFacturaActionPerformed
        try {
            //Iniciar registro de datos
            //controller.initRegistroFacturaCobros();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }

    private void btnCobrarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarFacturaActionPerformed
        try {

            // Si no ha seleccionado ninguna Factura a Cobrar
            if (facturaSelected == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.COBROFACTURA"));
            }
            //Cambiar estado de factura a PAGADO.
            controller.cobrarFactura();
            JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"),
                    messageBundle.getString("CONTAC.FORM.MSG.FACTURACLIENTE.COBRO.EXITOSO"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }

    }
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        try {

            //Validar datos del formulario para procesar
            if (dtpFechaDesde.getDate() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.FECHADESDE"));
            }

            if (dtpFechaHasta.getDate() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.FECHAHASTA"));
            }

            if (cmbAlmacen.getSelectedItem() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.ALMACEN"));
            }

            //Obtener parametros de busqueda
            Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                    getObject());

            TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                    ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                    null;

            // Prepared Jasper Report
            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroFacturas.class
                    .getResourceAsStream("/contac/facturacion/app/reportes/facturas_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/facturacion/app/reportes") + "/");
            parameters.put("p_fecha_desde", dtpFechaDesde.getDate());
            parameters.put("p_fecha_hasta", dtpFechaHasta.getDate());
            parameters.put("p_codigo_almacen", almacen.getId());
            parameters.put("p_tipo_factura", tiposFactura != null ? tiposFactura.getValue() : null);

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
    }//GEN-LAST:event_btnImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnBuscar;
    private JButton btnCancelar;
    private JButton btnImprimir;
    private JButton btnActualizar;
    private JButton btnCobrarFactura;

    private JComboBox cmbAlmacen;
    private JComboBox cmbTipoFactura;

    private JXDatePicker dtpFechaDesde;
    private JXDatePicker dtpFechaHasta;

    private JLabel lblNoFactura;
    private JLabel lblFacturaNo;
    private JLabel lblAlmacen;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JLabel lblTipoFactura;

    private JTextField txtNoFactura;

    private JXTable tblFacturasClientes;

    private BeanTableModel<Factura> facturaBeanTableModel;
    private Factura facturaSelected;
    private int rowSelected;
}
