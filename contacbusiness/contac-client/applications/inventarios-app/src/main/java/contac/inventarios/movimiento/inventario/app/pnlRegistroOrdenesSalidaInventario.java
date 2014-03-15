/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlRegistroOrdenesSalidaInventario.java
 *
 * Created on 12-17-2011, 10:53:18 PM
 */
package contac.inventarios.movimiento.inventario.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.*;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenSalidaController;
import contac.modelo.entity.Almacen;
import contac.modelo.entity.OrdenMovimiento;
import contac.modelo.entity.OrdenSalida;
import contac.reports.JRPrintReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXHeader;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author EMontenegro
 */
public class pnlRegistroOrdenesSalidaInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroOrdenesSalidaInventario.class);

    //Controller
    private OrdenSalidaController controller;
    private JXHeader header;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlRegistroOrdenesSalidaInventario
     */
    public pnlRegistroOrdenesSalidaInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "OrdenSalidaAlmacen", "Orden de Baja en Almac\u00e9n", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Controller
        controller = new OrdenSalidaController();


        try {

            //Init registros de datos
            controller.initRegistroOrdenesSalida();

            //Init components
            initComponents();

            //Init values
            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }

    @Override
    public void initValues() {
        try {
            //Init campos de busqueda
            dtpFechaDesde.setFormats("dd/MM/yyyy");
            dtpFechaDesde.setDate(new Date());

            dtpFechaHasta.setFormats("dd/MM/yyyy");
            dtpFechaHasta.setDate(new Date());

            //Config table model para salida inventario fisico
        ordenSalidaBeanTableModel = new BeanTableModel<OrdenSalida>(OrdenSalida.class, OrdenMovimiento.class,
                                    controller.getOrdenesSalida());
        ordenSalidaBeanTableModel.setModelEditable(false);
        tblOrdenesSalida.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordenSalidaBeanTableModel.sortColumnNames();
        tblOrdenesSalida.setEditable(false);
        tblOrdenesSalida.setModel(ordenSalidaBeanTableModel);
        tblOrdenesSalida.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel tableColumnModel = tblOrdenesSalida.getColumnModel();
        String[] columnsRemove = new String[]{"Persona Autoriza", "Persona Entrega", "Id", "Ctime", "Cuser", "Mtime", "Muser"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }

        //Adding cell rendering class
        tblOrdenesSalida.getColumn(0).setCellRenderer(new AlmacenFormatRenderer());
        tblOrdenesSalida.getColumn(2).setCellRenderer(new EstadoFormatRenderer());
        tblOrdenesSalida.getColumn(3).setCellRenderer(new FechaFormatRenderer());
        tblOrdenesSalida.getColumn(4).setCellRenderer(new MonedaFormatRenderer());
        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblOrdenesSalida.getColumn(5).setCellRenderer(decimalFormatRenderer);

        //Ordering table columns
        tblOrdenesSalida.moveColumn(6, 0); //No Movimiento
        tblOrdenesSalida.moveColumn(3, 6); //Estado movimiento
        
        //Setting preferred size
        tblOrdenesSalida.getColumn(0).setPreferredWidth(15);
        tblOrdenesSalida.getColumn(2).setPreferredWidth(250);

            cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.buscarAlmacenes()));
            ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
            cmbAlmacen.setRenderer(rendererAlmacen);
            //cmbAlmacen.setSelectedIndex(-1);

            controller.setAlmacen(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
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

        headerAlmacenes = new org.jdesktop.swingx.JXHeader();
        pnlRegistroInventario = new javax.swing.JPanel();
        pnlRegistroInventario.setBorder(BorderFactory.createEtchedBorder());
        pnlRegistroProformasWest = new JPanel();
        scrollOrdenesSalida = new javax.swing.JScrollPane();
        tblOrdenesSalida = new org.jdesktop.swingx.JXTable();
        tbRegistroInventarios = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        separatorOne = new javax.swing.JToolBar.Separator();
        separatorTwo = new javax.swing.JToolBar.Separator();
        btnEditar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnRemover = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        scrollProformasClientes = new JScrollPane();
        scrollProformaWest = new JScrollPane();

        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.ORDENSALIDA.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));


        setLayout(new java.awt.BorderLayout());

        headerAlmacenes.setForeground(new java.awt.Color(255, 153, 0));
        headerAlmacenes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        headerAlmacenes.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        headerAlmacenes.setTitle(bundle.getString("CONTAC.FORM.ORDENSALIDA.TITTLE")); // NOI18N
        headerAlmacenes.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(headerAlmacenes, java.awt.BorderLayout.NORTH);

        //*********************************************************************
        //Create Search Panel
        //*********************************************************************

        JPanel searchPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.setPreferredSize(new Dimension(340, 400));

        lblFechaDesde = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.FECHADESDE"));


        lblFechaDesde.setHorizontalAlignment(JLabel.LEFT);

        lblFechaHasta = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.FECHAHASTA"));
        lblFechaHasta.setHorizontalAlignment(JLabel.LEFT);

        lblAlmacen = new JLabel(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ALMACEN"));
        lblAlmacen.setHorizontalAlignment(JLabel.LEFT);

        dtpFechaDesde = new JXDatePicker();
        dtpFechaHasta = new JXDatePicker();

        //cmbTipoFactura = new JComboBox();
        cmbAlmacen = new JComboBox();

        ImageIcon buscarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/search.png"));
        ImageIcon cancelarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnBuscar = new JButton(messageBundle.getString("CONTAC.FORM.BTNBUSCAR"));
        btnBuscar.setIcon(buscarIco);
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBuscarActionPerformed(e);
            }
        });


        btnCancelar = new JButton(messageBundle.getString("CONTAC.FORM.BTNCANCELAR"));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.setIcon(cancelarIco);

        searchPanel.add(lblFechaDesde, new XYConstraints(5, 5, 120, 23));
        searchPanel.add(dtpFechaDesde, new XYConstraints(130, 5, 120, 23));
        searchPanel.add(lblFechaHasta, new XYConstraints(5, 33, 120, 23));
        searchPanel.add(dtpFechaHasta, new XYConstraints(130, 33, 120, 23));
        searchPanel.add(lblAlmacen, new XYConstraints(5, 61, 90, 23));
        searchPanel.add(cmbAlmacen, new XYConstraints(130, 61, 200, 23));
        searchPanel.add(btnBuscar, new XYConstraints(75, 89, 90, 23));
        searchPanel.add(btnCancelar, new XYConstraints(175, 89, 90, 23));

        //*********************************************************************
        //Create Facturas table
        //*********************************************************************


        pnlRegistroInventario.setLayout(new java.awt.BorderLayout());

        tblOrdenesSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdenesSalidaMouseClicked(evt);
            }
        });
        scrollOrdenesSalida.setViewportView(tblOrdenesSalida);

        pnlRegistroInventario.add(scrollOrdenesSalida, java.awt.BorderLayout.CENTER);

        tbRegistroInventarios.setBorder(null);
        tbRegistroInventarios.setMaximumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setMinimumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setPreferredSize(new java.awt.Dimension(124, 32));

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
        tbRegistroInventarios.add(btnAgregar);
        tbRegistroInventarios.add(jSeparator1);

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
        tbRegistroInventarios.add(btnEditar);
        tbRegistroInventarios.add(jSeparator2);

        btnRemover.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png")));
        btnRemover.setToolTipText(bundle.getString("CONTAC.FORM.BTNANULAR")); // NOI18N
        btnRemover.setFocusable(false);
        btnRemover.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRemover.setMaximumSize(new java.awt.Dimension(40, 32));
        btnRemover.setMinimumSize(new java.awt.Dimension(40, 32));
        btnRemover.setName(""); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        tbRegistroInventarios.add(btnRemover);

        JScrollPane ordenesEntradaScrollbar = new JScrollPane();
        ordenesEntradaScrollbar.getViewport().add(tblOrdenesSalida);

        pnlRegistroInventario.add(tbRegistroInventarios, BorderLayout.NORTH);
        pnlRegistroInventario.add(ordenesEntradaScrollbar, BorderLayout.CENTER);

        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png")));
        btnImprimir.setToolTipText(bundle.getString("CONTAC.FORM.BTNIMPRIMIR")); // NOI18N
        btnImprimir.setFocusable(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnImprimir.setMaximumSize(new Dimension(40, 32));
        btnImprimir.setMinimumSize(new Dimension(40, 32));
        btnImprimir.setName(""); // NOI18N
        tbRegistroInventarios.add(btnImprimir);

        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(pnlRegistroInventario, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrdenesSalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdenesSalidaMouseClicked

        // Getting orden de entrada selected
        rowSelected = tblOrdenesSalida.getSelectedRow();
        ordenSalidaSelected = (OrdenSalida) ((BeanTableModel) tblOrdenesSalida.getModel()).getRow(rowSelected);
        controller.setOrdenSalida(ordenSalidaSelected);
    }//GEN-LAST:event_tblOrdenesSalidaMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        //Open formulario de administracion de compania
        getMDI().getStyle().addPanel("pnlOrdenSalidaInventario", "contac.inventarios.movimiento.inventario.app.pnlOrdenSalidaInventario");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }//GEN-LAST:event_btnAgregarActionPerformed
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Obtener parametros de busqueda
            Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                    getObject());

            //Consultando listado de Ordenes de Salidas
            controller.buscarOrdenesSalida(fechaDesde, fechaHasta, almacen.getId());

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblOrdenesSalida.getModel()).fireTableDataChanged();

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Init controller data
        controller.init();

        //Init formulario
        initValues();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (ordenSalidaSelected != null) {
            //Open formulario de ingreso de inventario fisico
            getMDI().getStyle().addPanel("pnlOrdenSalidaInventario", "contac.inventarios.movimiento.inventario.app.pnlOrdenSalidaInventario",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        try {

            // Si no ha seleccionado ninguna Orden de Salida a Imprimir
            if (ordenSalidaSelected == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENBAJAALMACEN.IMPRIMIR.VALIDA"));
            }

            //Validar Estado de Orden de Salida
            controller.ordenBajaImprimir();

                JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroOrdenesTrasladoInventario.class
                        .getResourceAsStream("/contac/inventarios/app/reportes/orden_baja_almacen_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");
            parameters.put("n_id_orden_salida", ordenSalidaSelected.getId());

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

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed

        try {

            if (ordenSalidaSelected != null) {

                //Setting orden de entrada selected
                //controller.setOrdenEntrada(ordenEntradaSelected);

                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ADVERTENCIA"), MessageFormat.
                        format(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.CONFIRMA"),
                                new Object[]{ordenSalidaSelected.getNoMovimiento()}));

                if (confirmation) {
                    //Remover orden de entrada
                    //controller.removerOrdenEntrada();

                    //Show confirmation message
                    JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.EXITOSO"),
                            messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.EXITOSO"));

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblOrdenesSalida.getModel()).fireTableDataChanged();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnRemover;
    private org.jdesktop.swingx.JXHeader headerAlmacenes;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator separatorOne;
    private javax.swing.JToolBar.Separator separatorTwo;
    private javax.swing.JPanel pnlRegistroInventario;
    private javax.swing.JScrollPane scrollOrdenesSalida;
    private JScrollPane scrollProformasClientes;
    private JScrollPane scrollProformaWest;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblFechaDesde;
    private javax.swing.JLabel lblFechaHasta;
    private javax.swing.JComboBox cmbAlmacen;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;
    private JPanel pnlRegistroProformasWest;
    private javax.swing.JToolBar tbRegistroInventarios;
    private org.jdesktop.swingx.JXTable tblOrdenesSalida;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<OrdenSalida> ordenSalidaBeanTableModel;
    private OrdenSalida ordenSalidaSelected;
    private int rowSelected;
    ;
}
