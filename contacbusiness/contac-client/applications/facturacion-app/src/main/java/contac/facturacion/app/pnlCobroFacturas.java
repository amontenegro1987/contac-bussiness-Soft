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
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.comboBox.TipoFacturaComboBoxModel;
import contac.commons.models.tables.BeanTableModel;
import contac.commons.models.tables.NotEditableTableModel;
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
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
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

    /**
     * Creates new form pnlCobroFacturas
     */
    public pnlCobroFacturas(GenericFrame frame) {

        //Call super constructor
        super(frame, "CobroFacturasClientes", "Cobro de Facturas", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new FacturaClienteController();

        try {

            //Iniciar registro de datos
            controller.initRegistroFacturaCobros();

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

        searchPanel.add(lblFacturaNo, new XYConstraints(5, 45, 120, 23));
        searchPanel.add(txtNoFactura, new XYConstraints(90, 45, 200, 23));
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

        ImageIcon cobrarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/payment.png"));
        ImageIcon imprimirIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
        ImageIcon actualizarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/refresh.png"));

        btnImprimir = new JButton();
        btnImprimir.setPreferredSize(new Dimension(40, 32));
        btnImprimir.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIR"));
        btnImprimir.setIcon(imprimirIco);

        btnActualizar = new JButton();
        btnActualizar.setPreferredSize(new Dimension(40, 32));
        btnActualizar.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNACTUALIZAR"));
        btnActualizar.setIcon(actualizarIco);

        btnCobrarFactura = new JButton();
        btnCobrarFactura.setPreferredSize(new Dimension(40, 32));
        btnCobrarFactura.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNCOBRARFACTURA"));
        btnCobrarFactura.setIcon(cobrarIco);

        JToolBar actionToolBar = new JToolBar();
        actionToolBar.setPreferredSize(new Dimension(200, 32));

        actionToolBar.add(btnActualizar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnImprimir);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnCobrarFactura);

        //*********************************************************************
        //Create Cantidad Total de Facturas
        //*********************************************************************

        JPanel pnlTotalFacturas = new JPanel(new VerticalLayout());
        pnlTotalFacturas.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalFacturas.setPreferredSize(new Dimension(140, 70));

        JLabel lblNumeroFacturas = new JLabel();
        lblNumeroFacturas.setText("No. Facturas");
        lblNumeroFacturas.setHorizontalAlignment(SwingConstants.CENTER);

        Font newLabelFont = new Font(lblNumeroFacturas.getFont().getName(), Font.BOLD, lblNumeroFacturas.getFont().getSize());
        lblNumeroFacturas.setFont(newLabelFont);

        lblCantFacturas = new JLabel();
        lblCantFacturas.setFont(new Font("Serif", Font.PLAIN, 18));
        lblCantFacturas.setHorizontalAlignment(SwingConstants.CENTER);

        Font newLabelFont2 = new Font(lblCantFacturas.getFont().getName(), Font.BOLD, lblCantFacturas.getFont().getSize());
        lblCantFacturas.setFont(newLabelFont2);

        JLabel lblNumeroFacturasResultado = new JLabel();
        lblNumeroFacturasResultado.setText("registros encontrados");
        lblNumeroFacturasResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumeroFacturasResultado.setFont(newLabelFont);

        pnlTotalFacturas.add(lblNumeroFacturas);
        pnlTotalFacturas.add(lblCantFacturas);
        pnlTotalFacturas.add(lblNumeroFacturasResultado);

        //*********************************************************************
        //Create Monto Total Facturado Panel
        //*********************************************************************

        JPanel pnlTotalFacturado = new JPanel(new VerticalLayout());
        pnlTotalFacturado.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalFacturado.setPreferredSize(new Dimension(140, 70));

        JLabel lblTotalFacturado = new JLabel();
        lblTotalFacturado.setText("Total Facturado");
        lblTotalFacturado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFacturado.setFont(newLabelFont);

        lblMontoFacturado = new JLabel();
        lblMontoFacturado.setHorizontalAlignment(SwingConstants.CENTER);
        lblMontoFacturado.setForeground(Color.blue);
        lblMontoFacturado.setFont(new Font("Serif", Font.PLAIN, 18));
        lblMontoFacturado.setFont(newLabelFont2);

        pnlTotalFacturado.add(lblTotalFacturado);
        pnlTotalFacturado.add(lblMontoFacturado);

        //*********************************************************************
        //Create Monto Total Pagado Panel
        //*********************************************************************

        JPanel pnlTotalPagado = new JPanel(new VerticalLayout());
        pnlTotalPagado.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalPagado.setPreferredSize(new Dimension(140, 70));

        JLabel lblTotalPagado = new JLabel();
        lblTotalPagado.setText("Total Pagado");
        lblTotalPagado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPagado.setFont(newLabelFont);

        lblMontoPagado = new JLabel();
        lblMontoPagado.setHorizontalAlignment(SwingConstants.CENTER);
        lblMontoPagado.setForeground(Color.green);
        lblMontoPagado.setFont(new Font("Serif", Font.PLAIN, 18));
        lblMontoPagado.setFont(newLabelFont2);

        pnlTotalPagado.add(lblTotalPagado);
        pnlTotalPagado.add(lblMontoPagado);

        //*********************************************************************
        //Create Monto Total Pendiente Panel
        //*********************************************************************

        JPanel pnlTotalPendiente = new JPanel(new VerticalLayout());
        pnlTotalPendiente.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalPendiente.setPreferredSize(new Dimension(140, 70));

        JLabel lblTotalPendiente = new JLabel();
        lblTotalPendiente.setText("Total Pendiente");
        lblTotalPendiente.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPendiente.setFont(newLabelFont);

        lblMontoPendiente = new JLabel();
        lblMontoPendiente.setText("C$ ");
        lblMontoPendiente.setForeground(Color.RED);
        lblMontoPendiente.setFont(new Font("Serif", Font.PLAIN, 18));
        lblMontoPendiente.setFont(newLabelFont2);
        lblMontoPendiente.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblTotalPendienteDatoResultado = new JLabel();
        lblTotalPendienteDatoResultado.setText("porcentaje pendiente ()");
        lblTotalPendienteDatoResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPendienteDatoResultado.setFont(newLabelFont);

        pnlTotalPendiente.add(lblTotalPendiente);
        pnlTotalPendiente.add(lblMontoPendiente);

        //*********************************************************************
        //Create Statistics Panel
        //*********************************************************************
        JPanel estadisticasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        estadisticasPanel.setPreferredSize(new Dimension(200, 80));

        estadisticasPanel.add(pnlTotalFacturas);
        estadisticasPanel.add(pnlTotalFacturado);
        estadisticasPanel.add(pnlTotalPagado);
        estadisticasPanel.add(pnlTotalPendiente);

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
        txtNoFactura.setEnabled(false);

        //Fecha desde
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaDesde.setDate(new Date());
        dtpFechaDesde.setEnabled(false);

        //Fecha hasta
        dtpFechaHasta.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setDate(new Date());
        dtpFechaHasta.setEnabled(false);

        //***************************************************************************
        //Combo box tipo de factura
        //***************************************************************************
        cmbTipoFactura.setModel(new TipoFacturaComboBoxModel(TiposFactura.values()));
        cmbTipoFactura.setSelectedIndex(0);
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
                txtNoFactura.setEnabled(true);
                dtpFechaDesde.setEnabled(true);
                dtpFechaHasta.setEnabled(true);
                cmbAlmacen.setEnabled(true);
            }
        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    private void initResultTable() {

        //Column names JTable
        String[] columnNames = {"Id", "No. Factura", "Fecha Alta", "Almac\u00e9n", "Nombre Cliente", "Monto Neto", "Estado"};
        NotEditableTableModel tableModel = new NotEditableTableModel(columnNames, 0);

        int totalFacturas = 0;

        BigDecimal montoTotalFacturas = new BigDecimal("0");
        BigDecimal montoTotalPagado = new BigDecimal("0");
        BigDecimal montoTotalPendiente = new BigDecimal("0");

        for (Factura factura : controller.getFacturas()) {

            //Create Facturas Model Object
            Object[] row = new Object[7];
            row[0] = factura.getId() + "";
            row[1] = factura.getNoDocumento() + "";
            row[2] = DateFormat.getDateInstance().format(factura.getFechaAlta());
            row[3] = factura.getAlmacen().getDescripcion();
            row[4] = factura.getNombreCliente();
            row[5] = factura.getMontoNeto();
            row[6] = factura.getEstadoMovimiento().getDescripcion();

            //Calcular total facturas
            totalFacturas += 1;

            //Calcular Monto total facturas
            montoTotalFacturas = montoTotalFacturas.add(factura.getMontoNeto());

            //Calcular Monto total pagado
            if (factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.PAGADO.getEstado())) {
                montoTotalPagado = montoTotalPagado.add(factura.getMontoNeto());
            }

            //Calcular Monto total pendiente
            if (factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.INGRESADO.getEstado()) ||
                    factura.getEstadoMovimiento().getAlias().equals(EstadosMovimiento.IMPRESO.getEstado())) {
                montoTotalPendiente = montoTotalPendiente.add(factura.getMontoNeto());
            }

            tableModel.addRow(row);
        }

        //Setting table model movimiento inventario
        tblFacturasClientes.setModel(tableModel);

        tblFacturasClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblFacturasClientes.setRowSelectionAllowed(true);

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);

        TableColumnModel columnModel = tblFacturasClientes.getColumnModel();
        columnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);

        //Hide column (0)
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);

        tblFacturasClientes.packAll();

        //Setting Default Selection Interval
        if (controller.getFacturas().size() > 0) {
            tblFacturasClientes.setRowSelectionInterval(0, 0);
        }

        //Update Datos facturacion
        lblCantFacturas.setText(totalFacturas + "");
        lblMontoFacturado.setText(montoTotalFacturas.toString());
        lblMontoPagado.setText(montoTotalPagado.toString());
        lblMontoPendiente.setText(montoTotalPendiente.toString());
    }

    private void initActionListeners() {

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarActionPerformed(e);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initValues();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reset search Facturas
                btnBuscarActionPerformed(e);
            }
        });

        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnImprimirActionPerformed(e);
            }
        });

        btnCobrarFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCobrarFacturaActionPerformed(e);
            }
        });

        tblFacturasClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturasClientesMouseClicked(evt);
            }
        });
    }

    private void tblFacturasClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturasClientesMouseClicked

        try {

            //Getting row selected
            int row = tblFacturasClientes.getSelectedRow();

            if (row >= 0) {

                //Codigo de almacen
                this.idFactura = Integer.parseInt((String) tblFacturasClientes.getValueAt(row, 0));


                //Setting Factura cliente
                controller.setFactura(controller.buscarFacturaCliente(this.idFactura));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }

    }//GEN-LAST:event_tblFacturasClientesMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        try {

            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Obteniendo tipo de factura
            TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                    ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                    null;

            if (txtNoFactura.getText().isEmpty()) {

                //Obtener parametros de busqueda
                Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                        getObject());

                //Consultando listado de facturas de clientes por fecha
                controller.buscarFacturasClientesCobrosPorFechas(fechaDesde, fechaHasta, almacen.getId()/*,
                        tiposFactura.getValue()*/);

            } else {

                //Getting numero de factura
                Long numeroFactura = Long.parseLong(txtNoFactura.getText());

                //Buscar facturas clientes por numero de factura
                controller.buscarFacturasClientesCobrosPorNo(numeroFactura, fechaDesde, fechaHasta);
            }

            //Actualizar listado de articulos ingresados
            initResultTable();

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCobrarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarFacturaActionPerformed

        try {


            if (controller.getFactura() != null) {

                if (controller.getFactura().getEstadoMovimiento().getAlias().equals(EstadosMovimiento.PAGADO.getEstado())) {
                    throw new Exception(messageBundle.getString("CONTAC.FORM.MSG.FACTURA.ESTADO.PAGADO"));
                }

                if (!controller.getFactura().getEstadoMovimiento().getAlias().equals(EstadosMovimiento.IMPRESO.getEstado())) {
                    throw new Exception(messageBundle.getString("CONTAC.FORM.MSG.ERROR.ESTADOIMPRESO.FALLIDO"));
                }

                //Open Panel Pago Factura
                new pnlPagoFactura(this.getMDI(), true, controller);

                //Reset search Facturas
                btnBuscarActionPerformed(evt);
            }


        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        try {

            // Si no ha seleccionado ninguna Factura a Imprimir
            if (idFactura == 0) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.REPORTEFACTURA"));
            }

            //Cambiar estado de factura a impresa.
            controller.imprimirFactura();

            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroFacturas.class.
                    getResourceAsStream("/contac/facturacion/app/reportes/Invoice_Garsa.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/facturacion/app/reportes") + "/");
            parameters.put("n_id_factura", this.idFactura);

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

            //Reset search Facturas
            btnBuscarActionPerformed(evt);

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

    private JLabel lblFacturaNo;
    private JLabel lblAlmacen;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JLabel lblTipoFactura;

    private JLabel lblCantFacturas;
    private JLabel lblMontoFacturado;
    private JLabel lblMontoPagado;
    private JLabel lblMontoPendiente;

    private JTextField txtNoFactura;

    private JXTable tblFacturasClientes;

    private Integer idFactura = 0;
}
