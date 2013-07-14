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

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.form.render.TipoFacturaRenderer;
import contac.commons.models.tables.BeanTableModel;
import contac.facturacion.controller.FacturaClienteController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.DocumentoComercial;
import contac.modelo.entity.Factura;
import contac.modelo.entity.OrdenEntrada;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author EMontenegro
 */
public class pnlRegistroFacturas extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroFacturas.class);

    //Controller
    private FacturaClienteController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlRegistroFacturas
     */
    public pnlRegistroFacturas(GenericFrame frame) {

        //Call super constructor
        super(frame, "RegistroFacturasClientes", "Registro de Facturas", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new FacturaClienteController();

        try {

            //Iniciar registro de datos
            controller.initRegistrosFactura();

            //Init components
            initComponents();

            //Init values
            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }

    @Override
    public void initValues() {

        //Config table model para lavantamiento inventario fisico
        facturaBeanTableModel = new BeanTableModel<Factura>(Factura.class, DocumentoComercial.class,
                controller.getFacturas());
        facturaBeanTableModel.setModelEditable(false);
        tblFacturasClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        facturaBeanTableModel.sortColumnNames();
        tblFacturasClientes.setEditable(false);
        tblFacturasClientes.setModel(facturaBeanTableModel);
        tblFacturasClientes.setRowSelectionAllowed(true);

        //Init campos de busqueda
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setFormats("dd/MM/yyyy");

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel tableColumnModel = tblFacturasClientes.getColumnModel();
        String[] columnsRemove = new String[]{"Agente Ventas", "Cliente", "Direccion Entrega", "Exonerada", "Proforma",
                "Pago", "Porc Descuento", "Porc IVA", "Porc Ret Fuente", "Porc Ret Municipal", "Serie", "Id", "Retencion F",
                "Retencion M", "Terminos Pago", "Tasa Cambio", "Ctime", "Cuser", "Mtime", "Muser"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }

        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableColumnModel.getColumn(4).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(6).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(7).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(10).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(11).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(12).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(12).setCellRenderer(new TipoFacturaRenderer());

        //Ordering table columns
        tableColumnModel.moveColumn(8, 0); //No Documento
        tableColumnModel.moveColumn(3, 1); //Fecha alta
        tableColumnModel.moveColumn(12, 2); //Tipo de factura
        tableColumnModel.moveColumn(10, 4); //Nombre del cliente
        tableColumnModel.moveColumn(6, 5); //Moneda
        tableColumnModel.moveColumn(6, 12); //Estado factura
        tableColumnModel.moveColumn(10, 8); //Retencion fuente
        tableColumnModel.moveColumn(11, 9); //Retencion municipal

        //Setting prefered size
        tableColumnModel.getColumn(4).setPreferredWidth(200);
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

        headerAlmacenes = new org.jdesktop.swingx.JXHeader();
        pnlRegistroFacturas = new javax.swing.JPanel();
        tbFacturasClientes = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        separatorOne = new javax.swing.JToolBar.Separator();
        btnEditar = new javax.swing.JButton();
        separatorTwo = new javax.swing.JToolBar.Separator();
        btnAnular = new javax.swing.JButton();
        separatorThree = new javax.swing.JToolBar.Separator();
        btnEliminar = new javax.swing.JButton();
        btnSeparator = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dtpFechaDesde = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        dtpFechaHasta = new org.jdesktop.swingx.JXDatePicker();
        btnBuscar = new javax.swing.JButton();
        scrollFacturasClientes = new javax.swing.JScrollPane();
        tblFacturasClientes = new org.jdesktop.swingx.JXTable();

        setLayout(new java.awt.BorderLayout());

        headerAlmacenes.setForeground(new java.awt.Color(255, 153, 0));
        headerAlmacenes.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes_es"); // NOI18N
        headerAlmacenes.setTitle(bundle.getString("CONTAC.FORM.REGISTROFACTURAS.TITTLE")); // NOI18N
        headerAlmacenes.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(headerAlmacenes, java.awt.BorderLayout.PAGE_START);

        pnlRegistroFacturas.setPreferredSize(new java.awt.Dimension(693, 394));
        pnlRegistroFacturas.setLayout(new java.awt.BorderLayout());

        tbFacturasClientes.setBorder(null);
        tbFacturasClientes.setFloatable(false);
        tbFacturasClientes.setMaximumSize(new java.awt.Dimension(124, 32));
        tbFacturasClientes.setMinimumSize(new java.awt.Dimension(124, 32));
        tbFacturasClientes.setPreferredSize(new java.awt.Dimension(124, 32));
        tbFacturasClientes.setRequestFocusEnabled(false);

        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png")));
        btnAgregar.setToolTipText(bundle.getString("CONTAC.FORM.BTNNUEVO")); // NOI18N
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregar.setFocusable(false);
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgregar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnAgregar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnAgregar.setPreferredSize(new java.awt.Dimension(70, 20));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        tbFacturasClientes.add(btnAgregar);
        tbFacturasClientes.add(separatorOne);

        btnEditar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/edit.png")));
        btnEditar.setToolTipText(bundle.getString("CONTAC.FORM.BTNEDITAR")); // NOI18N
        btnEditar.setFocusable(false);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnEditar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnEditar.setName(""); // NOI18N
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        tbFacturasClientes.add(btnEditar);
        tbFacturasClientes.add(separatorTwo);

        btnAnular.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png")));
        btnAnular.setToolTipText(bundle.getString("CONTAC.FORM.BTNANULAR")); // NOI18N
        btnAnular.setFocusable(false);
        btnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAnular.setMaximumSize(new java.awt.Dimension(40, 32));
        btnAnular.setMinimumSize(new java.awt.Dimension(40, 32));
        btnAnular.setName(""); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        tbFacturasClientes.add(btnAnular);
        tbFacturasClientes.add(separatorThree);

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove.png")));
        btnEliminar.setToolTipText(bundle.getString("CONTAC.FORM.BTNELIMINAR")); // NOI18N
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setMaximumSize(new java.awt.Dimension(40, 32));
        btnEliminar.setMinimumSize(new java.awt.Dimension(40, 32));
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        tbFacturasClientes.add(btnEliminar);

        btnSeparator.setToolTipText("");
        btnSeparator.setEnabled(false);
        btnSeparator.setFocusable(false);
        btnSeparator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSeparator.setMaximumSize(new java.awt.Dimension(40, 32));
        btnSeparator.setMinimumSize(new java.awt.Dimension(40, 32));
        btnSeparator.setName(""); // NOI18N
        btnSeparator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbFacturasClientes.add(btnSeparator);

        jLabel1.setText(bundle.getString("CONTAC.FORM.REGISTROFACTURAS.FECHADESDE")); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(75, 14));
        jLabel1.setMinimumSize(new java.awt.Dimension(75, 14));
        jLabel1.setPreferredSize(new java.awt.Dimension(75, 14));
        tbFacturasClientes.add(jLabel1);
        tbFacturasClientes.add(dtpFechaDesde);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(bundle.getString("CONTAC.FORM.REGISTROFACTURAS.FECHAHASTA")); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(75, 14));
        jLabel2.setMinimumSize(new java.awt.Dimension(75, 14));
        jLabel2.setPreferredSize(new java.awt.Dimension(75, 14));
        tbFacturasClientes.add(jLabel2);
        tbFacturasClientes.add(dtpFechaHasta);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/search.png")));
        btnBuscar.setText(bundle.getString("CONTAC.FORM.BTNBUSCAR")); // NOI18N
        btnBuscar.setFocusable(false);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        tbFacturasClientes.add(btnBuscar);

        pnlRegistroFacturas.add(tbFacturasClientes, java.awt.BorderLayout.PAGE_START);

        tblFacturasClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturasClientesMouseClicked(evt);
            }
        });
        scrollFacturasClientes.setViewportView(tblFacturasClientes);

        pnlRegistroFacturas.add(scrollFacturasClientes, java.awt.BorderLayout.CENTER);

        add(pnlRegistroFacturas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        //Open formulario de administracion de compania
        getMDI().getStyle().addPanel("pnlFacturaCliente", "contac.facturacion.app.pnlFacturaCliente");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (facturaSelected != null) {
            //Open formulario de ingreso de inventario fisico
            getMDI().getStyle().addPanel("pnlFacturaCliente", "contac.facturacion.app.pnlFacturaCliente",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        try {

            //Confirmation message
            boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.
                    format(messageBundle.getString("CONTAC.FORM.FACTURACION.ANULAR.CONFIRMA"),
                            new Object[]{facturaSelected.getNoDocumento()}));

            if (confirmation) {

                //Setting factura seleccionada
                controller.setFactura(facturaSelected);

                //Anular factura
                controller.anularFactura();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.ANULACION.EXITOSO"));
                
                //Realizar busqueda de facturas nuevamente
                Date fechaDesde = dtpFechaDesde.getDate() != null ? dtpFechaDesde.getDate() : new Date();
                Date fechaHasta = dtpFechaHasta.getDate() != null ? dtpFechaHasta.getDate() : new Date();

                controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta);

                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();
            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnAnularActionPerformed

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

            //Consultando listado de facturas de clientes por fecha
            controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta);

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {

            //Confirmation message
            boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.
                    format(messageBundle.getString("CONTAC.FORM.FACTURACION.ELIMINAR.CONFIRMA"),
                            new Object[]{facturaSelected.getNoDocumento()}));

            if (confirmation) {

                //Setting factura seleccionada
                controller.setFactura(facturaSelected);

                //Anular factura
                controller.eliminarFactura();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.CONFIRMACION"),
                        messageBundle.getString("CONTAC.FORM.MSG.ELIMINACION.EXITOSO"));

                //Realizar busqueda de facturas nuevamente
                Date fechaDesde = dtpFechaDesde.getDate() != null ? dtpFechaDesde.getDate() : new Date();
                Date fechaHasta = dtpFechaHasta.getDate() != null ? dtpFechaHasta.getDate() : new Date();

                controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta);

                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();
            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnSeparator;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;
    private org.jdesktop.swingx.JXHeader headerAlmacenes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnlRegistroFacturas;
    private javax.swing.JScrollPane scrollFacturasClientes;
    private javax.swing.JToolBar.Separator separatorOne;
    private javax.swing.JToolBar.Separator separatorThree;
    private javax.swing.JToolBar.Separator separatorTwo;
    private javax.swing.JToolBar tbFacturasClientes;
    private org.jdesktop.swingx.JXTable tblFacturasClientes;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<Factura> facturaBeanTableModel;
    private Factura facturaSelected;
    private int rowSelected;
}
