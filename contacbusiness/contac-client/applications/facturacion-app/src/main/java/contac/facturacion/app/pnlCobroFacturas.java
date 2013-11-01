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

    /**
     * Creates new form pnlCobroFacturas
     */
    public pnlCobroFacturas(GenericFrame frame) {

        //Call super constructor
        super(frame, "CobroFacturasClientes", "Cobro de Facturas", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller

        controller = new FacturaClienteController();
        Integer idEstadoPagado = 7;
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

        txtFacturaNo = new JTextField();

        dtpFechaDesde = new JXDatePicker();
        dtpFechaHasta = new JXDatePicker();

        cmbTipoFactura = new JComboBox();
        cmbAlmacen = new JComboBox();

        ImageIcon buscarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/search.png"));
        ImageIcon cancelarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnBuscar = new JButton(messageBundle.getString("CONTAC.FORM.BTNBUSCAR"));
        btnBuscar.setIcon(buscarIco);

        btnBuscarNoFactura = new JButton();
        btnBuscarNoFactura.setBackground(Color.white);
        btnBuscarNoFactura.setIcon(buscarIco);

        btnCancelar = new JButton(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setIcon(cancelarIco);

        searchPanel.add(lblBusquedaFactura, new XYConstraints(5, 5, 120,23));
        searchPanel.add(lblNoFactura, new XYConstraints(5,45,120,23));
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
        txtFacturaNo.setText("");

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
    }

    private void initActionListeners() {

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
            controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta, almacen.getId(),
                    tiposFactura != null ? tiposFactura.getValue() : null);

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();

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

//    private void rbNoPagadasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
//        Integer idEstadoPagado = 7;  //TODO :  OBTENER EL ESTADO DE LA ENTIDAD (NO LO HE HECHO)
//
//        if (rbNoPagadas.isSelected()) {
//            rbPagadas.setSelected(false);
//            rbTodas.setSelected(false);
//
//            try {
//                controller.initRegistroFacturaCobros(idEstadoPagado);
//            } catch (Exception e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//
//        if (!rbNoPagadas.isSelected()) {
//            rbPagadas.setSelected(false);
//            rbTodas.setSelected(false);
//        }
//    }//GEN-LAST:event_rbNoPagadasStateChanged
//
//    private void rbPagadasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
//        Integer idEstadoPagado = 8;  //TODO :  OBTENER EL ESTADO DE LA ENTIDAD (NO LO HE HECHO)
//
//        if (rbPagadas.isSelected()) {
//            rbNoPagadas.setSelected(false);
//            rbTodas.setSelected(false);
//
//            try {
//                controller.initRegistroFacturaCobros(idEstadoPagado);
//            } catch (Exception e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//
//        if (!rbPagadas.isSelected()) {
//            rbNoPagadas.setSelected(false);
//            rbTodas.setSelected(false);
//        }
//    }//GEN-LAST:event_rbPagadasStateChanged
//
//    private void rbTodasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
//        Integer idEstadoPagado = null;  //TODO :  OBTENER EL ESTADO DE LA ENTIDAD (NO LO HE HECHO)
//        if (rbTodas.isSelected()) {
//            rbNoPagadas.setSelected(false);
//            rbPagadas.setSelected(false);
//
//            try {
//                controller.initRegistroFacturaCobros(idEstadoPagado);
//            } catch (Exception e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//
//        if (!rbTodas.isSelected()) {
//            rbNoPagadas.setSelected(false);
//            rbPagadas.setSelected(false);
//        }
//    }//GEN-LAST:event_rbTodasStateChanged

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

    private JLabel lblFacturaNo;
    private JLabel lblAlmacen;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JLabel lblTipoFactura;

    private JTextField txtFacturaNo;

    private JXTable tblFacturasClientes;

    private BeanTableModel<Factura> facturaBeanTableModel;
    private Factura facturaSelected;
    private int rowSelected;
}
