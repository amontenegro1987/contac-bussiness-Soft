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


        rgCobroFactura = new org.jdesktop.swingx.JXRadioGroup();
        rbNoPagadas = new javax.swing.JRadioButton();
        rbPagadas = new javax.swing.JRadioButton();
        rbTodas = new javax.swing.JRadioButton();
        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.COBROFACTURAS.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //*********************************************************************
        //Create Search Panel
        //*********************************************************************
        dtpFechaDesde = new JXDatePicker();
        dtpFechaHasta = new JXDatePicker();

        cmbTipoFactura = new JComboBox();
        cmbAlmacen = new JComboBox();

        //*********************************************************************
        //Create Facturas table
        //*********************************************************************

        ImageIcon cobrarFactura = new ImageIcon(getClass().getResource("/contac/resources/icons/reward_refresh_256x48.png"));
        ImageIcon imprimirIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
        ImageIcon actualizarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/refresh.png"));


        rbNoPagadas.setSelected(true);
        rbNoPagadas.setText(messageBundle.getString("CONTAC.FORM.COBROFACTURA.NOPAGADA")); // NOI18N
        rbNoPagadas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbNoPagadasStateChanged(evt);
            }
        });

        rbPagadas.setText(messageBundle.getString("CONTAC.FORM.COBROFACTURA.PAGADA")); // NOI18N  \
        rbPagadas.setActionCommand(messageBundle.getString("CONTAC.FORM.COBROFACTURA.PAGADA")); // NOI18N  \
        rbPagadas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbPagadasStateChanged(evt);
            }
        });

        rbTodas.setText(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TODAS")); // NOI18N
        rbTodas.setActionCommand(messageBundle.getString("CONTAC.FORM.COBROFACTURA.TODAS")); // NOI18N
        rbTodas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbTodasStateChanged(evt);
            }
        });



        javax.swing.GroupLayout rgTipoPersonaLayout = new javax.swing.GroupLayout(rgCobroFactura);
        rgCobroFactura.setLayout(rgTipoPersonaLayout);
        rgTipoPersonaLayout.setHorizontalGroup(
                rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rgTipoPersonaLayout.createSequentialGroup()
                                .addComponent(rbNoPagadas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbPagadas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbTodas)
                                .addContainerGap(8, Short.MAX_VALUE))
        );

        rgTipoPersonaLayout.setVerticalGroup(
                rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rgTipoPersonaLayout.createSequentialGroup()
                                .addGroup(rgTipoPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rbNoPagadas, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rbPagadas, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rbTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        // Componentes de cobroToolBar

        btnImprimir = new JButton();
        btnImprimir.setPreferredSize(new Dimension(40, 32));
        btnImprimir.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIR"));
        btnImprimir.setIcon(imprimirIco);

        btnActualizar = new JButton(messageBundle.getString("CONTAC.FORM.ACTUALIZAR"));
        btnActualizar.setPreferredSize(new Dimension(130, 32));
        btnActualizar.setToolTipText(messageBundle.getString("CONTAC.FORM.ACTUALIZAR"));
        btnActualizar.setIcon(actualizarIco);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
    }
});


        btnCobrarFactura = new JButton(messageBundle.getString("CONTAC.FORM.BTNCOBRARFACTURA"));
        btnCobrarFactura.setPreferredSize(new Dimension(130,32));
        btnCobrarFactura.setIcon(cobrarFactura);
        btnCobrarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarFacturaActionPerformed(evt);
            }
        });

      JToolBar cobroToolBar = new JToolBar();
        cobroToolBar.setPreferredSize(new Dimension(500,32));

        cobroToolBar.add(rgCobroFactura);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnImprimir);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnActualizar);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnCobrarFactura);
        cobroToolBar.add(new JToolBar.Separator());

        JToolBar actionToolBar = new JToolBar();
        actionToolBar.setPreferredSize(new Dimension(200, 32));

        JPanel facturasPanel = new JPanel(new BorderLayout());
        facturasPanel.setBorder(BorderFactory.createEtchedBorder());

        tblFacturasClientes = new JXTable();

        JScrollPane facturasScrollbar = new JScrollPane();
        facturasScrollbar.getViewport().add(tblFacturasClientes);
        facturasPanel.add(cobroToolBar, BorderLayout.NORTH);
        facturasPanel.add(facturasScrollbar, BorderLayout.CENTER);

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(facturasPanel, BorderLayout.CENTER);
     }

       @Override
    public void initValues() {

        //Init campos de busqueda
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaDesde.setDate(new Date());

        dtpFechaHasta.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setDate(new Date());

        //Combo box tipo de factura
        cmbTipoFactura.setModel(new TipoFacturaComboBoxModel(TiposFactura.values()));

        ListCellRenderer rendererTipoFactura = new ComboBoxEmptySelectionRenderer(cmbTipoFactura, messageBundle.
                getString("CONTAC.FORM.MSG.SELECCIONE"));
        cmbTipoFactura.setRenderer(rendererTipoFactura);
        cmbTipoFactura.setSelectedIndex(-1);

        //Combo box almacen
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

    private void rbNoPagadasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
        Integer idEstadoPagado = 7;  //TODO :  OBTENER EL ESTADO DE LA ENTIDAD (NO LO HE HECHO)

        if (rbNoPagadas.isSelected()) {
            rbPagadas.setSelected(false);
            rbTodas.setSelected(false);

            try {
                controller.initRegistroFacturaCobros(idEstadoPagado);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        if (!rbNoPagadas.isSelected()) {
            rbPagadas.setSelected(false);
            rbTodas.setSelected(false);
        }
    }//GEN-LAST:event_rbNoPagadasStateChanged

    private void rbPagadasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
         Integer idEstadoPagado = 8;  //TODO :  OBTENER EL ESTADO DE LA ENTIDAD (NO LO HE HECHO)

        if (rbPagadas.isSelected()) {
            rbNoPagadas.setSelected(false);
            rbTodas.setSelected(false);

            try {
                controller.initRegistroFacturaCobros(idEstadoPagado);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        if (!rbPagadas.isSelected()) {
            rbNoPagadas.setSelected(false);
            rbTodas.setSelected(false);
        }
    }//GEN-LAST:event_rbPagadasStateChanged

    private void rbTodasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbNaturalStateChanged
        Integer idEstadoPagado = null;  //TODO :  OBTENER EL ESTADO DE LA ENTIDAD (NO LO HE HECHO)
        if (rbTodas.isSelected()) {
            rbNoPagadas.setSelected(false);
            rbPagadas.setSelected(false);

            try {
                controller.initRegistroFacturaCobros(idEstadoPagado);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        if (!rbTodas.isSelected()) {
            rbNoPagadas.setSelected(false);
            rbPagadas.setSelected(false);
        }
    }//GEN-LAST:event_rbTodasStateChanged

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

    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCobrarFactura;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JComboBox cmbTipoFactura;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;

    private javax.swing.JRadioButton rbNoPagadas;
    private javax.swing.JRadioButton rbPagadas;
    private javax.swing.JRadioButton rbTodas;
    private org.jdesktop.swingx.JXRadioGroup rgCobroFactura;
    private org.jdesktop.swingx.JXTable tblFacturasClientes;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<Factura> facturaBeanTableModel;
    private Factura facturaSelected;
    private int rowSelected;
}
