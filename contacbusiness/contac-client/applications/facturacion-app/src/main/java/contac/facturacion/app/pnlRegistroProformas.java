/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlRegistroProformas.java
 *
 * Created on 11-05-2012, 03:10:19 PM
 */
package contac.facturacion.app;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.tables.BeanTableModel;
import contac.facturacion.controller.ProformaClienteController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.reports.JRPrintReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHeader;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.*;
/*
* @autor AMontenegro
*/

public class pnlRegistroProformas extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroProformas.class);

    //Controller
    private ProformaClienteController controller;

    private JXHeader header;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/mensajes",
            LanguageLocale.getInstance().getLocale());

    /*
     * Creates new form pnlRegistroProformas
    */
    public pnlRegistroProformas(GenericFrame frame){

        //Call super constructor
        super(frame, "RegistroProformas", "Registro de Proformas", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NIC"));


        //Init controller
        controller = new ProformaClienteController();

        try {

            //Iniciar registro de datos
            controller.initRegistrosProformas();

            //Init components
            initComponents();

            //Init values
            initValues();

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }

    }

    @Override
    public void initValues(){
       //Init campos de busqueda
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setFormats("ddd/MM/yyyy");

        //Combo box almacen
        ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.
                getString("CONTAC.FORM.MSG.SELECCIONE"));
        AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacen.getModel();
        cmbAlmacen.setRenderer(rendererAlmacen);
        cmbAlmacen.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacen().getId()));
        cmbAlmacen.setEnabled(false);

        try {
            if(controller.checkUserInRole(Roles.ROLFACTURACIONADMIN.toString())) {
                cmbAlmacen.setEnabled(true);
            }
        } catch(Exception e){
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }

        //Config table model para Levantamiento Inventario Fisico
        proformaBeanTableModel = new BeanTableModel<Proforma>(Proforma.class, DocumentoComercial.class,
        controller.getProformas());
        proformaBeanTableModel.setModelEditable(false);
        tblProformasClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        proformaBeanTableModel.sortColumnNames();
        tblProformasClientes.setEditable(false);
        tblProformasClientes.setModel(proformaBeanTableModel);
        tblProformasClientes.setRowSelectionAllowed(true);

        TableColumnModel tableColumnModel = tblProformasClientes.getColumnModel();

        String[] columnsremove = new String[]{"Agente Ventas", "Cliente", "Direccion Entrega", "Exonerada",
                "Porc Descuento", "Porc IVA", "Porc Ret Fuente", "Porc Ret Municipal", "Serie", "Id", "Retencion F",
                "Retencion M", "Terminos Pago", "Tasa Cambio", "Ctime", "Cuser", "Mtime", "Muser", "Monto IVA",
                "Excenta", "Retencion Fuente", "Fecha Vencimiento", "Retencion Municipal", "Monto Descuento", "Correo", "Monto Bruto"};

        for(String columnLabel : columnsremove){
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tableColumnModel.getColumn(4).setCellRenderer((decimalFormatRenderer));       //MONTO NETO FORMATO DECIMALES

        //Ordering table columns
        tableColumnModel.moveColumn(5,0); //No Documento
        tableColumnModel.moveColumn(3,1); //Fecha Alta
        tableColumnModel.moveColumn(6,2); //Nombre Cliente
        tableColumnModel.moveColumn(5,4); //Moneda
        tableColumnModel.moveColumn(6,5); //Monto Neto

        //Setting prefered size
        //tableColumnModel.getcolumn(4).setPreferredWidth(200);
        tblProformasClientes.packAll();
    }
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    //<editor-fold defaultstate="collapsed" desc="Generated Code">//GEN_BEGIN:initComponents
    private void initComponents(){
        headerAlmacenes = new org.jdesktop.swingx.JXHeader();
        pnlRegistroProformas = new JPanel();
        pnlRegistroProformasWest = new JPanel();
        tbProformasClientes = new JToolBar();
        btnAgregar = new JButton();
        separatorOne = new JToolBar.Separator();
        btnEditar = new JButton();
        separatorTwo = new JToolBar.Separator();
        btnAnular = new JButton();
        btnCancelar = new JButton();
        separatorThree = new JToolBar.Separator();
        btnEliminar = new JButton();
        jSeparator1 = new JToolBar.Separator();
        btnImprimir = new JButton();
        lblFechaDesde = new JLabel();
        dtpFechaDesde = new org.jdesktop.swingx.JXDatePicker();
        lblFechaHasta = new JLabel();
        dtpFechaHasta = new org.jdesktop.swingx.JXDatePicker();
        lblAlmacen = new JLabel();
        cmbAlmacen = new JComboBox();
        btnBuscar = new JButton();
        scrollProformasClientes = new JScrollPane();
        scrollProformaWest = new JScrollPane();
        tblProformasClientes = new org.jdesktop.swingx.JXTable();

        //***************************************************************************************
        //Init Header Panel
        //***************************************************************************************
        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.REGISTROPROFORMAS.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        setLayout(new BorderLayout());

        ImageIcon imprimirFacturaIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/factura.png"));

        headerAlmacenes.setForeground(new Color(255, 153, 0));
        headerAlmacenes.setPreferredSize(new Dimension(51, 35));
        ResourceBundle bundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes_es"); // NOI18N
        headerAlmacenes.setTitle(bundle.getString("CONTAC.FORM.REGISTROPROFORMAS.TITTLE")); // NOI18N
        headerAlmacenes.setTitleForeground(new Color(255, 153, 0));
        add(headerAlmacenes, BorderLayout.PAGE_START);

        JPanel searchPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.setPreferredSize(new Dimension(340, 400));

        tbProformasClientes.setBorder(null);
        tbProformasClientes.setFloatable(false);
        tbProformasClientes.setMaximumSize(new Dimension(124, 32));
        tbProformasClientes.setMinimumSize(new Dimension(124, 32));
        tbProformasClientes.setPreferredSize(new Dimension(124, 32));
        tbProformasClientes.setRequestFocusEnabled(false);

        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png")));
        btnAgregar.setToolTipText(bundle.getString("CONTAC.FORM.BTNNUEVO")); //NOI18N
        btnAgregar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        btnAgregar.setFocusable(false);
        btnAgregar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnAgregar.setMaximumSize(new Dimension(40,32));
        btnAgregar.setMinimumSize(new Dimension(40,32));
        btnAgregar.setPreferredSize(new Dimension(70,20));
        btnAgregar.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/edit.png")));
        btnEditar.setToolTipText(bundle.getString("CONTAC.FORM.BTNEDITAR")); // NOI18N
        btnEditar.setFocusable(false);
        btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEditar.setMaximumSize(new Dimension(40, 32));
        btnEditar.setMinimumSize(new Dimension(40, 32));
        btnEditar.setName(""); // NOI18N
        btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnAnular.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png")));
        btnAnular.setToolTipText(bundle.getString("CONTAC.FORM.BTNANULAR")); // NOI18N
        btnAnular.setFocusable(false);
        btnAnular.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnAnular.setMaximumSize(new Dimension(40, 32));
        btnAnular.setMinimumSize(new Dimension(40, 32));
        btnAnular.setName(""); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        btnCancelar.setToolTipText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.setFocusable(false);
        btnCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnCancelar.setMaximumSize(new Dimension(80, 21));
        btnCancelar.setMinimumSize(new Dimension(80, 21));
        btnCancelar.setPreferredSize(new Dimension(80,21));
        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove.png")));
        btnEliminar.setToolTipText(bundle.getString("CONTAC.FORM.BTNELIMINAR")); // NOI18N
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEliminar.setMaximumSize(new Dimension(40, 32));
        btnEliminar.setMinimumSize(new Dimension(40, 32));
        btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png")));
        btnImprimir.setToolTipText(bundle.getString("CONTAC.FORM.BTNIMPRIMIR")); // NOI18N
        btnImprimir.setFocusable(false);
        btnImprimir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnImprimir.setMaximumSize(new Dimension(40, 32));
        btnImprimir.setMinimumSize(new Dimension(40, 32));
        btnImprimir.setName(""); // NOI18N
        btnImprimir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnImprimirProforma = new JButton();
        btnImprimirProforma.setPreferredSize(new Dimension(40, 32));
        btnImprimirProforma.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIRPROFORMA"));
        btnImprimirProforma.setIcon(imprimirFacturaIco);
        btnImprimirProforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirProformaActionPerformed(evt);
            }
        });

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
        actionToolBar.add(btnImprimir);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnImprimirProforma);

        JPanel facturasPanel = new JPanel(new BorderLayout());
        facturasPanel.setBorder(BorderFactory.createEtchedBorder());

        lblFechaDesde.setHorizontalAlignment(SwingConstants.LEFT);
        lblFechaDesde.setText(bundle.getString("CONTAC.FORM.REGISTROPROFORMAS.FECHADESDE")); //NOI18N
        lblFechaDesde.setMaximumSize(new Dimension(75, 22));
        lblFechaDesde.setMinimumSize(new Dimension(75,22));
        lblFechaDesde.setPreferredSize(new Dimension(75, 22));

        dtpFechaDesde.setMaximumSize(new Dimension(160,22));
        dtpFechaDesde.setMinimumSize(new Dimension(160,22));
        dtpFechaDesde.setPreferredSize(new Dimension(160,22));

        lblFechaHasta.setHorizontalAlignment(SwingConstants.LEFT);
        lblFechaHasta.setText(bundle.getString("CONTAC.FORM.REGISTROPROFORMAS.FECHAHASTA")); //NOI18N
        lblFechaHasta.setMaximumSize(new Dimension(75,22));
        lblFechaHasta.setMinimumSize(new Dimension(75,22));
        lblFechaHasta.setPreferredSize(new Dimension(75,22));

        dtpFechaHasta.setMaximumSize(new Dimension(160,22));
        dtpFechaHasta.setMinimumSize(new Dimension(160,22));
        dtpFechaHasta.setPreferredSize(new Dimension(160,22));

        lblAlmacen.setText(bundle.getString("CONTAC.FORM.PROFORMA.ALMACEN")); //NOI18N
        lblAlmacen.setMaximumSize(new Dimension(75,22));
        lblAlmacen.setMinimumSize(new Dimension(75,22));
        lblAlmacen.setPreferredSize(new Dimension(75,22));

        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
        cmbAlmacen.setLightWeightPopupEnabled(false);
        cmbAlmacen.setMaximumSize(new Dimension(160,22));
        cmbAlmacen.setMinimumSize(new Dimension(160,22));
        cmbAlmacen.setPreferredSize(new Dimension(160,22));

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/search.png")));
        btnBuscar.setText(bundle.getString("CONTAC.FORM.BTNBUSCAR")); // NOI18N
        btnBuscar.setFocusable(false);
        btnBuscar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnBuscar.setMaximumSize(new Dimension(80,21));
        btnBuscar.setMinimumSize(new Dimension(80,21));
        btnBuscar.setPreferredSize(new Dimension(80,21));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        searchPanel.add(lblFechaDesde, new XYConstraints(5, 5, 120, 23));
        searchPanel.add(dtpFechaDesde, new XYConstraints(130, 5, 120, 23));
        searchPanel.add(lblFechaHasta, new XYConstraints(5, 33, 120, 23));
        searchPanel.add(dtpFechaHasta, new XYConstraints(130, 33, 120, 23));
        searchPanel.add(lblAlmacen, new XYConstraints(5, 61, 90, 23));
        searchPanel.add(cmbAlmacen, new XYConstraints(130, 61, 200, 23));
        searchPanel.add(btnBuscar, new XYConstraints(75, 89, 90, 23));
        searchPanel.add(btnCancelar, new XYConstraints(175, 89, 90, 23));

        tblProformasClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProformasClientesMouseClicked(evt);
            }
        });
        scrollProformasClientes.setViewportView(tblProformasClientes);
        scrollProformaWest.setViewportView(pnlRegistroProformasWest);

        add(scrollProformaWest, BorderLayout.WEST);

        JScrollPane facturasScrollbar = new JScrollPane();
        facturasScrollbar.getViewport().add(tblProformasClientes);

        facturasPanel.add(actionToolBar, BorderLayout.NORTH);
        facturasPanel.add(facturasScrollbar, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(facturasPanel, BorderLayout.CENTER);

       }  // </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST: event_btnAgregarActionPerformed
        //Open formulario de Ingresar Proforma
        getMDI().getStyle().addPanel("pnlProformaCliente", "contac.facturacion.app.pnlProformaCliente");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnEditarActionPerformed
        if(proformaSelected != null) {
            //Open formulario de Ingresar Proforma
            getMDI().getStyle().addPanel("pnlProformaCliente", "contac.facturacion.app.pnlProformaCliente",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }//GEN-LAST: event_btnEditarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        try{
            //confirmation message
            boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.
                    format(messageBundle.getString("CONTAC.FORM.PROFORMA.ANULAR.CONFIRMA"),
                            new Object[]{proformaSelected.getNoDocumento()}));

            if(confirmation) {
                //Setting proforma seleccionada
                controller.setProformas(proformaSelected);

                //Anular proforma
                controller.anularProforma();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.ANULACION.EXITOSO"));

                //Realizar busqueda de proformas nuevamente
                Date fechaDesde = dtpFechaDesde.getDate() != null ? dtpFechaDesde.getDate() : new Date();
                Date fechaHasta = dtpFechaHasta.getDate() != null ? dtpFechaHasta.getDate() : new Date();

                //Obtener parametros de busqueda
                Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                        getObject());

                controller.buscarProformasPorFechas(fechaDesde, fechaHasta, almacen.getId());

                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblProformasClientes.getModel()).fireTableDataChanged();
            }
        } catch (Exception e){
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //TableColumnModel tableColumnModel = tblProformasClientes.getColumnModel();
        //Init controller data
        controller.init();
        //Init formulario
        initValues();
        //dtpFechaDesde.setDate(null);
        //dtpFechaHasta.setDate(null); TODO

    }//GEN-LAST:event_btnCancelarActionPerformed


    private void tblProformasClientesMouseClicked(java.awt.event.MouseEvent evt){ //GEN-FIRST:event_tblProformasClientesMouseClicked
        //Getting proforma cliente selected
        rowSelected = tblProformasClientes.getSelectedRow();
        proformaSelected = (Proforma) ((BeanTableModel) tblProformasClientes.getModel()).getRow(rowSelected);
        controller.setProformas(proformaSelected);
    }        //GEN-LAST:event_tblProformasClientesMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {

            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Obtener parametros de busqueda
            Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                    getObject());

            //Consultando listado de proformas de clientes por fecha
            controller.buscarProformasPorFechas(fechaDesde, fechaHasta, almacen.getId());

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblProformasClientes.getModel()).fireTableDataChanged();

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {

            if (proformaSelected != null) {

                //Confirmation message
                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.
                        getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.format(messageBundle.
                        getString("CONTAC.FORM.PROFORMA.ELIMINAR.CONFIRMA"), new Object[]{proformaSelected.getNoDocumento()}));

                if (confirmation) {

                    //Setting proforma seleccionada
                    controller.setProformas(proformaSelected);

                    //Anular proforma
                    controller.eliminarProforma();

                    //Show confirmation message
                    JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                            messageBundle.getString("CONTAC.FORM.MSG.PROFORMA.ELIMINACION.EXITOSO"));

                    //Realizar busqueda de proformas nuevamente
                    Date fechaDesde = dtpFechaDesde.getDate() != null ? dtpFechaDesde.getDate() : new Date();
                    Date fechaHasta = dtpFechaHasta.getDate() != null ? dtpFechaHasta.getDate() : new Date();

                    //Obtener parametros de busqueda
                    Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                            getObject());

                    controller.buscarProformasPorFechas(fechaDesde, fechaHasta, almacen.getId());

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblProformasClientes.getModel()).fireTableDataChanged();
                }

            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnImprimirProformaActionPerformed(java.awt.event.ActionEvent evt){ //GEN-FIRST:event.btnImprimirProformaActionPerformed
        try {
            // Si no ha seleccionado ninguna proforma a Imprimir
            if (proformaSelected == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.PROFORMA.VALIDA.REPORTEPROFORMA"));
            }

            //Cambiar estado de Proforma a impresa.
            /*controller.imprimirProforma();*/  //TODO : No se si se va a cambiar de estado luego de impresa la proforma

            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroFacturas.class.
                    getResourceAsStream("/contac/facturacion/app/reportes/proforma_Garsa.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/facturacion/app/reportes") + "/");
            parameters.put("n_id_proforma", proformaSelected.getId());

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }

    }//GEN-LAST:event_btnImprimirActionPerformed


    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        try {

            //Validar datos del formulario para procesar
            if (dtpFechaDesde.getDate() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.PROFORMA.VALIDA.FECHADESDE"));
            }

            if (dtpFechaHasta.getDate() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.PROFORMA.VALIDA.FECHAHASTA"));
            }

            if (cmbAlmacen.getSelectedItem() == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.ALMACEN"));
            }

            //Obtener parametros de busqueda
            Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                    getObject());

            // Prepared Jasper Report
            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroProformas.class
                    .getResourceAsStream("/contac/facturacion/app/reportes/proformas_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/facturacion/app/reportes") + "/");
            parameters.put("p_fecha_desde", dtpFechaDesde.getDate());
            parameters.put("p_fecha_hasta", dtpFechaHasta.getDate());
            parameters.put("p_codigo_almacen", almacen.getId());

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
private JButton btnAgregar;
private JButton btnAnular;
private JButton btnCancelar;
private JButton btnBuscar;
private JButton btnEditar;
private JButton btnEliminar;
private JButton btnImprimir;
private JButton btnImprimirProforma;
private JComboBox cmbAlmacen;
private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;
private org.jdesktop.swingx.JXHeader headerAlmacenes;
private JToolBar.Separator jSeparator1;
private JLabel lblAlmacen;
private JLabel lblFechaDesde;
private JLabel lblFechaHasta;
private JPanel pnlRegistroProformas;
private JPanel pnlRegistroProformasWest;
private JScrollPane scrollProformasClientes;
private JScrollPane scrollProformaWest;
private JToolBar.Separator separatorOne;
private JToolBar.Separator separatorThree;
private JToolBar.Separator separatorTwo;
private JToolBar tbProformasClientes;
private org.jdesktop.swingx.JXTable tblProformasClientes;
    // End of variables declaration//GEN-END:variables

private BeanTableModel<Proforma> proformaBeanTableModel;
private Proforma proformaSelected;
private int rowSelected;
}
