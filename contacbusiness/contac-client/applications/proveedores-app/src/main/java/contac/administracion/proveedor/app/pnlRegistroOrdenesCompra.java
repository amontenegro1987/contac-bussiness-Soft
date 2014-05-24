/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlRegistroOrdenesCompra.java
 *
 * Created on 13-02-2014, 11:12:19 PM
 */
package contac.administracion.proveedor.app;

import contac.administracion.controller.OrdenCompraController;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.render.*;
import contac.commons.form.panel.GenericPanel;
import contac.commons.models.tables.BeanTableModel;
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
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author AMontenegro
 */
public class pnlRegistroOrdenesCompra extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroOrdenesCompra.class);

    //Controller
    private OrdenCompraController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/requisicion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //Components
    private JXHeader header;

    /**
     * Creates new form pnlRegistroOrdenesCompra
     */
    public pnlRegistroOrdenesCompra(GenericFrame frame) {

        //Call super constructor
        super(frame, "RegistroOrdenesCompras", "Registro de Ordenes de Compra", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new OrdenCompraController();

        try {

            //Iniciar registro de datos
            controller.initRegistrosOrdenesCompra();

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
        header.setTitle(messageBundle.getString("CONTAC.FORM.REGISTROORDENESCOMPRA.TITTLE")); // NOI18N
        header.setForeground(new Color(255, 153, 0));
        header.setTitleForeground(new Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        //*********************************************************************
        //Create Search Panel
        //*********************************************************************

        JPanel searchPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.setPreferredSize(new Dimension(270, 400));

        lblFechaDesde = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROPROFORMAS.FECHADESDE"));
        lblFechaDesde.setHorizontalAlignment(JLabel.LEFT);

        lblFechaHasta = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROPROFORMAS.FECHAHASTA"));
        lblFechaHasta.setHorizontalAlignment(JLabel.LEFT);

        dtpFechaDesde = new JXDatePicker();
        dtpFechaHasta = new JXDatePicker();

        ImageIcon buscarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/search.png"));
        ImageIcon cancelarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnBuscar = new JButton(messageBundle.getString("CONTAC.FORM.BTNBUSCAR"));
        btnBuscar.setIcon(buscarIco);

        btnCancelar = new JButton(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.setIcon(cancelarIco);

        searchPanel.add(lblFechaDesde, new XYConstraints(5, 5, 120, 23));
        searchPanel.add(dtpFechaDesde, new XYConstraints(130, 5, 120, 23));
        searchPanel.add(lblFechaHasta, new XYConstraints(5, 33, 120, 23));
        searchPanel.add(dtpFechaHasta, new XYConstraints(130, 33, 120, 23));
        searchPanel.add(btnBuscar, new XYConstraints(25, 65, 90, 23));
        searchPanel.add(btnCancelar, new XYConstraints(125, 65, 90, 23));

        //*********************************************************************
        //Create Orden Compra table
        //*********************************************************************

        ImageIcon agregarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png"));
        ImageIcon editarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/edit.png"));
        ImageIcon anularIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));
        ImageIcon cancelIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove.png"));
        ImageIcon imprimirIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
        ImageIcon confirmarIcon = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/aplicar.png"));

        btnAgregar = new JButton();
        btnAgregar.setPreferredSize(new Dimension(40, 32));
        btnAgregar.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNNUEVO"));
        btnAgregar.setIcon(agregarIco);

        btnEditar = new JButton();
        btnEditar.setPreferredSize(new Dimension(40, 32));
        btnEditar.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNEDITAR"));
        btnEditar.setIcon(editarIco);

        btnAnular = new JButton();
        btnAnular.setPreferredSize(new Dimension(40, 32));
        btnAnular.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNANULAR"));
        btnAnular.setIcon(anularIco);

        btnEliminar = new JButton();
        btnEliminar.setPreferredSize(new Dimension(40, 32));
        btnEliminar.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNELIMINAR"));
        btnEliminar.setIcon(cancelIco);

        btnImprimir = new JButton();
        btnImprimir.setPreferredSize(new Dimension(40, 32));
        btnImprimir.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIR"));
        btnImprimir.setIcon(imprimirIco);

        btnConfirmar = new JButton();
        btnConfirmar.setPreferredSize(new Dimension(40,32));
        btnConfirmar.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNCONFIRMAR"));
        btnConfirmar.setIcon(confirmarIcon);

        JToolBar actionToolBar = new JToolBar();
        actionToolBar.setPreferredSize(new Dimension(500, 32));

        actionToolBar.add(btnAgregar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnEditar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnAnular);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnEliminar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnConfirmar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnImprimir);

        JPanel ordenesCompraPanel = new JPanel(new BorderLayout());
        ordenesCompraPanel.setBorder(BorderFactory.createEtchedBorder());

        tblRegistroOrdenCompra = new JXTable();

        JScrollPane ordenesCompraScrollBar = new JScrollPane();
        ordenesCompraScrollBar.getViewport().add(tblRegistroOrdenCompra);

        ordenesCompraPanel.add(actionToolBar, BorderLayout.NORTH);
        ordenesCompraPanel.add(ordenesCompraScrollBar, BorderLayout.CENTER);

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(ordenesCompraPanel, BorderLayout.CENTER);

    }

    @Override
    public void initValues() {

        //Init campos de busqueda
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaDesde.setDate(new Date());

        dtpFechaHasta.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setDate(new Date());

    }

    private void initResultTable() {

        ordenCompraBeanTableModel = new BeanTableModel<OrdenCompra>(OrdenCompra.class, DocumentoOrdenCompra.class,
                controller.getOrdenCompras());
        ordenCompraBeanTableModel.setModelEditable(false);
        tblRegistroOrdenCompra.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordenCompraBeanTableModel.sortColumnNames();
        tblRegistroOrdenCompra.setEditable(false);
        tblRegistroOrdenCompra.setModel(ordenCompraBeanTableModel);
        tblRegistroOrdenCompra.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias

        TableColumnModel tableColumnModel = tblRegistroOrdenCompra.getColumnModel();

        String[] columnsRemove = new String[]{/*"Agente Ventas", "Cliente",*/ "Direccion Entrega", "Fecha Requerida", "Monto IVA", "Retencion Fuente",
                "Proveedor", "Retencion Municipal", "Monto Bruto", "Monto Descuento", "Exonerada", "Excenta",/*, "Proforma",*/
                "Pago", "Porc Descuento", "Porc IVA", "Porc Ret Fuente", "Porc Ret Municipal", /*"Serie", */"No Documento", "Retencion F",
                "Retencion M", "Terminos Pago", "Tasa Cambio", "Ctime", "Cuser", "Mtime", "Muser"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }


        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableColumnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);

        //Ordering table columns

        tableColumnModel.moveColumn(3, 0); //No Documento
        tableColumnModel.moveColumn(3, 1); //Fecha alta
        tableColumnModel.moveColumn(6, 2); //Proveedor
        tableColumnModel.moveColumn(5, 4); //Moneda
        tableColumnModel.moveColumn(6, 5); //Monto Neto
        tableColumnModel.moveColumn(7, 6); //Numero Referencia

        //Setting prefered size
        //tableColumnModel.getColumn(1).setPreferredWidth(50);

        tblRegistroOrdenCompra.packAll();
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
                btnCancelarActionPerformed(e);
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAgregarActionPerformed(e);
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEditarActionPerformed(e);
            }
        });

        btnAnular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAnularActionPerformed(e);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelarActionPerformed(e);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEliminarActionPerformed(e);
            }
        });

        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnImprimirActionPerformed(e);
            }
        });

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConfirmarActionPerformed(e);
            }
        });

        tblRegistroOrdenCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRegistroOrdenCompraMouseClicked(evt);
            }
        });
    }

    private void btnAgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        //Open formulario de Orden de Compra
        getMDI().getStyle().addPanel("pnlOrdenCompra", "contac.administracion.proveedor.app.pnlOrdenCompra");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (ordenCompraSelected != null) {
            //Open formulario de Orden de Compra
            getMDI().getStyle().addPanel("pnlOrdenCompra", "contac.administracion.proveedor.app.pnlOrdenCompra",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAnularActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
       /* try {

            //Confirmation message
            boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.
                    format(messageBundle.getString("CONTAC.FORM.FACTURACION.ANULAR.CONFIRMA"),
                            new Object[]{ordenCompraSelected.getNoDocumento()}));

            if (confirmation) {

                //Setting factura seleccionada
                controller.setFactura(ordenCompraSelected);

                //Anular factura
                controller.anularFactura();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(this.getMDI(), messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.ANULACION.EXITOSO"));

                //Realizar busqueda de facturas nuevamente
                Date fechaDesde = dtpFechaDesde.getDate() != null ? dtpFechaDesde.getDate() : new Date();
                Date fechaHasta = dtpFechaHasta.getDate() != null ? dtpFechaHasta.getDate() : new Date();

                //Obtener parametros de busqueda
                Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                        getObject());

                TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                        ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                        null;

                controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta, almacen.getId(),
                        tiposFactura != null ? tiposFactura.getValue() : null);

                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblRegistroOrdenCompra.getModel()).fireTableDataChanged();
            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());*/
        //}
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnCancelarActionPerformed(ActionEvent evt) { //GEN-FIRST: event_btnCancelarActionperformed
        //Init controller data
        controller.init();

        //Init formulario
        initValues();
    }

    private void tblRegistroOrdenCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturasClientesMouseClicked

        //Getting factura cliente selected
        rowSelected = tblRegistroOrdenCompra.getSelectedRow();
        ordenCompraSelected = (OrdenCompra) ((BeanTableModel) tblRegistroOrdenCompra.getModel()).getRow(rowSelected);
        controller.setOrdenCompra(ordenCompraSelected);

    }//GEN-LAST:event_tblRegistroOrdenCompraMouseClicked

    private void btnBuscarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {

            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Consultando listado de Ordenes de Compra
            controller.buscarOrdenesComprasPorFechasRegistros(fechaDesde, fechaHasta);

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblRegistroOrdenCompra.getModel()).fireTableDataChanged();

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       /* try {

            if (ordenCompraSelected != null) {

                //Confirmation message
                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.
                        getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.format(messageBundle.
                        getString("CONTAC.FORM.FACTURACION.ELIMINAR.CONFIRMA"), new Object[]{ordenCompraSelected.getNoDocumento()}));

                if (confirmation) {

                    //Setting factura seleccionada
                    controller.setFactura(ordenCompraSelected);

                    //Anular factura
                    controller.eliminarFactura();

                    //Show confirmation message
                    JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                            messageBundle.getString("CONTAC.FORM.MSG.ELIMINACION.EXITOSO"));

                    //Realizar busqueda de facturas nuevamente
                    Date fechaDesde = dtpFechaDesde.getDate() != null ? dtpFechaDesde.getDate() : new Date();
                    Date fechaHasta = dtpFechaHasta.getDate() != null ? dtpFechaHasta.getDate() : new Date();

                    //Obtener parametros de busqueda
                    Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                            getObject());

                    TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                            ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                            null;

                    controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta, almacen.getId(),
                            tiposFactura != null ? tiposFactura.getValue() : null);

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblRegistroOrdenCompra.getModel()).fireTableDataChanged();
                }

            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }*/
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        try {

            //Validar datos del formulario para procesar
            if (dtpFechaDesde.getDate() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.REGISTROPROFORMAS.FECHADESDE"));
            }

            if (dtpFechaHasta.getDate() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.REGISTROPROFORMAS.FECHAHASTA"));
            }

            // Prepared Jasper Report
            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroOrdenesCompra.class
                    .getResourceAsStream("/contac/facturacion/app/reportes/orden_compra_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/facturacion/app/reportes") + "/");
            parameters.put("n_id_orden_compra", ordenCompraSelected.getId());

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

    private void btnConfirmarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

    }//GEN-LAST:event_btnConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAgregar;
    private JButton btnAnular;
    private JButton btnCancelar;
    private JButton btnBuscar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnConfirmar;
    private JButton btnImprimir;
    private JXDatePicker dtpFechaDesde;
    private JXDatePicker dtpFechaHasta;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JXTable tblRegistroOrdenCompra;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<OrdenCompra> ordenCompraBeanTableModel;
    private OrdenCompra ordenCompraSelected;
    private int rowSelected;
}
