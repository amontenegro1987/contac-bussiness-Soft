/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import contac.inventarios.controller.OrdenLevantamientoController;
import contac.modelo.entity.Almacen;
import contac.modelo.entity.OrdenLevantamientoFisico;
import contac.modelo.entity.OrdenMovimiento;
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
import java.text.MessageFormat;
import java.util.*;

/**
 * @author Alex
 */
public class pnlRegistroLevantamientoFisico extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroLevantamientoFisico.class);

    //Controller
    private OrdenLevantamientoController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    private JXHeader header;

    /**
     * Creates new form pnlRegistroLevantamientoFisico
     */
    public pnlRegistroLevantamientoFisico(GenericFrame frame) {

        //Call super constructor
        super(frame, "registroLevantamientoFisico", "Levantamiento Inventario F\u00edsico", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Controller
        controller = new OrdenLevantamientoController();

        try {

            //Init registros de datos
            controller.initRegistrosOrdenesEntradaInventario();

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
      try{

        //Init campos de busqueda
        dtpFechaDesde.setFormats("dd/MM/yyyy");
        dtpFechaDesde.setDate(new Date());

        dtpFechaHasta.setFormats("dd/MM/yyyy");
        dtpFechaHasta.setDate(new Date());

        //Config table model para lavantamiento inventario fisico
        ordenLevantamientoFisicoBeanTableModel = new BeanTableModel<OrdenLevantamientoFisico>(OrdenLevantamientoFisico.class, OrdenMovimiento.class,
                controller.getOrdenesLevantamiento());
        ordenLevantamientoFisicoBeanTableModel.setModelEditable(false);
        tblOrdenesLevantamiento.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordenLevantamientoFisicoBeanTableModel.sortColumnNames();
        tblOrdenesLevantamiento.setEditable(false);
        tblOrdenesLevantamiento.setModel(ordenLevantamientoFisicoBeanTableModel);
        tblOrdenesLevantamiento.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel tableColumnModel = tblOrdenesLevantamiento.getColumnModel();
        String[] columnsRemove = new String[]{"Persona Entrega", "Fecha Solicitud", "Id", "Ctime", "Cuser", "Mtime", "Muser",
            "Monto Total Ajuste"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }
        //Adding cell rendering claass
        tableColumnModel.getColumn(0).setCellRenderer(new AlmacenFormatRenderer());
        tableColumnModel.getColumn(2).setCellRenderer(new EstadoFormatRenderer());
        tableColumnModel.getColumn(3).setCellRenderer(new FechaFormatRenderer());
        tableColumnModel.getColumn(4).setCellRenderer(new MonedaFormatRenderer());
        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableColumnModel.getColumn(5).setCellRenderer(decimalFormatRenderer);
        tableColumnModel.getColumn(7).setCellRenderer(new TipoActualizacionInventarioRenderer());

        //Ordering table columns
        tableColumnModel.moveColumn(6, 0); //No Movimiento
        tableColumnModel.moveColumn(3, 2); //Estado Movimiento

        //Setting prefered size
        tableColumnModel.getColumn(0).setPreferredWidth(15);
        tableColumnModel.getColumn(2).setPreferredWidth(200);

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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.TITTLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        pnlRegistroLevantamientoFisico = new javax.swing.JPanel();
        tbRegistroInventarios = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        separatorOne = new javax.swing.JToolBar.Separator();
        btnEditar = new javax.swing.JButton();
        separatorTwo = new javax.swing.JToolBar.Separator();
        btnAnular = new javax.swing.JButton();
        separatorThree = new javax.swing.JToolBar.Separator();
        btnAplicarAjuste = new javax.swing.JButton();
        btnImprimirAjuste = new javax.swing.JButton();
        scrollOrdenesEntrada = new javax.swing.JScrollPane();
        tblOrdenesLevantamiento = new org.jdesktop.swingx.JXTable();
        headerAlmacenes = new org.jdesktop.swingx.JXHeader();

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

        cmbAlmacen = new JComboBox();

        ImageIcon buscarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/search.png"));
        ImageIcon cancelarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));

        btnBuscar = new JButton(messageBundle.getString("CONTAC.FORM.BTNBUSCAR"));
        btnBuscar.setIcon(buscarIco);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
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

        setLayout(new java.awt.BorderLayout());
  /**/
        pnlRegistroLevantamientoFisico.setLayout(new java.awt.BorderLayout());

        tbRegistroInventarios.setBorder(null);
        tbRegistroInventarios.setFloatable(false);
        tbRegistroInventarios.setMaximumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setMinimumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setPreferredSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setRequestFocusEnabled(false);

        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png")));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
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
        tbRegistroInventarios.add(separatorOne);

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
        tbRegistroInventarios.add(separatorTwo);

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
        tbRegistroInventarios.add(btnAnular);
        tbRegistroInventarios.add(separatorThree);

        btnAplicarAjuste.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/aplicar.png")));
        btnAplicarAjuste.setToolTipText(bundle.getString("CONTAC.FORM.BTNAPLICAR")); // NOI18N
        btnAplicarAjuste.setFocusable(false);
        btnAplicarAjuste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAplicarAjuste.setMaximumSize(new java.awt.Dimension(40, 32));
        btnAplicarAjuste.setMinimumSize(new java.awt.Dimension(40, 32));
        btnAplicarAjuste.setName(""); // NOI18N
        btnAplicarAjuste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAplicarAjuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarAjusteActionPerformed(evt);
            }
        });
        tbRegistroInventarios.add(btnAplicarAjuste);

        btnImprimirAjuste.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png")));
        btnImprimirAjuste.setToolTipText(bundle.getString("CONTAC.FORM.BTNIMPRIMIRAJUSTE")); // NOI18N
        btnImprimirAjuste.setFocusable(false);
        btnImprimirAjuste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimirAjuste.setMaximumSize(new java.awt.Dimension(40, 32));
        btnImprimirAjuste.setMinimumSize(new java.awt.Dimension(40, 32));
        btnImprimirAjuste.setName(""); // NOI18N
        btnImprimirAjuste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimirAjuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirAjusteActionPerformed(evt);
            }
        });
        tbRegistroInventarios.add(btnImprimirAjuste);

        pnlRegistroLevantamientoFisico.add(tbRegistroInventarios, java.awt.BorderLayout.PAGE_START);

        tblOrdenesLevantamiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdenesLevantamientoMouseClicked(evt);
            }
        });
        scrollOrdenesEntrada.setViewportView(tblOrdenesLevantamiento);

        pnlRegistroLevantamientoFisico.add(scrollOrdenesEntrada, java.awt.BorderLayout.CENTER);

        //add(pnlRegistroLevantamientoFisico, java.awt.BorderLayout.CENTER);

        headerAlmacenes.setForeground(new java.awt.Color(255, 153, 0));
        headerAlmacenes.setPreferredSize(new java.awt.Dimension(51, 35));
        headerAlmacenes.setTitle(bundle.getString("CONTAC.FORM.REGISTROENTRADAINVENTARIO.TITLE")); // NOI18N
        headerAlmacenes.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(headerAlmacenes, java.awt.BorderLayout.PAGE_START);

        JScrollPane ordenesEntradaScrollbar = new JScrollPane();
        ordenesEntradaScrollbar.getViewport().add(tblOrdenesLevantamiento);

        pnlRegistroLevantamientoFisico.add(tbRegistroInventarios, BorderLayout.NORTH);
        pnlRegistroLevantamientoFisico.add(ordenesEntradaScrollbar, BorderLayout.CENTER);

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(pnlRegistroLevantamientoFisico, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Obtener parametros de busqueda
            Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                    getObject());

            //Consultando listado de Levantamiento Inventario Fisico

            controller.buscarOrdenesLevantamientoFisico(fechaDesde, fechaHasta, almacen.getId());

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblOrdenesLevantamiento.getModel()).fireTableDataChanged();

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

    private void btnImprimirAjusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirAjusteActionPerformed
        try {

            // Si no ha seleccionado ninguna Orden de Levantamiento de Inventario a Imprimir
            if (ordenLevantamientoSelected == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENLEVANTAMIENTO.IMPRIMIR.VALIDA"));
            }

            //Validar Estado de Orden de Levantamiento de Inventario
            controller.ordenLevantamientoImprimir();

            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroOrdenesTrasladoInventario.class
                    .getResourceAsStream("/contac/inventarios/app/reportes/ajuste_de_inventario_fisico.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");
            parameters.put("n_id_orden_levantamiento", ordenLevantamientoSelected.getId());

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }

    private void btnAplicarAjusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarAjusteActionPerformed
       try{

           //Buscar Estado Ajuste
           controller.estadoAjusteInventario();

        if (ordenLevantamientoSelected != null) {
            //Open formulario de aplicar ajuste de inventario fisico
            getMDI().getStyle().addPanel("pnlAplicarAjusteLevantamientoInventarioFisico",
                    "contac.inventarios.movimiento.inventario.app.pnlAplicarAjusteLevantamientoInventarioFisico",
                    controller);
            
            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
       } catch (Exception e) {
           logger.error(e.getMessage(), e);
           //Show error message
           JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
       }
    }//GEN-LAST:event_btnAplicarAjusteActionPerformed

    private void tblOrdenesLevantamientoMouseClicked(java.awt.event.MouseEvent evt) {

        // Getting orden de entrada selected
        rowSelected = tblOrdenesLevantamiento.getSelectedRow();
        ordenLevantamientoSelected = (OrdenLevantamientoFisico) ((BeanTableModel) tblOrdenesLevantamiento.getModel()).getRow(rowSelected);
        controller.setOrdenLevantamiento(ordenLevantamientoSelected);
    }                                                     

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        //Open formulario de Levantamiento InventarioFisico
        getMDI().getStyle().addPanel("pnlOrdenLevantamientoFisico", "contac.inventarios.movimiento.inventario.app.pnlOrdenLevantamientoFisico");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {

        if (ordenLevantamientoSelected != null) {
            //Open formulario de ingreso de inventario fisico
            getMDI().getStyle().addPanel("pnlOrdenLevantamientoFisico", "contac.inventarios.movimiento.inventario.app.pnlOrdenLevantamientoFisico",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {

        try {

            if (ordenLevantamientoSelected != null) {

                //Setting orden de entrada selected
                controller.setOrdenLevantamiento(ordenLevantamientoSelected);

                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null,
                        messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ADVERTENCIA"),
                        MessageFormat.format(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.CONFIRMA"),
                        new Object[]{ordenLevantamientoSelected.getNoMovimiento()}));

                if (confirmation) {
                    //Remover orden de entrada
                    controller.removerOrdenLevantamientoFisico();

                    //Show confirmation message
                    JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.EXITOSO"),
                            messageBundle.getString("CONTAC.FORM.ORDENENTRADA.ELIMINA.EXITOSO"));

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblOrdenesLevantamiento.getModel()).fireTableDataChanged();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnAplicarAjuste;
    private javax.swing.JButton btnImprimirAjuste;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnBuscar;
    private org.jdesktop.swingx.JXHeader headerAlmacenes;
    private javax.swing.JPanel pnlRegistroLevantamientoFisico;
    private javax.swing.JScrollPane scrollOrdenesEntrada;
    private javax.swing.JToolBar.Separator separatorOne;
    private javax.swing.JToolBar.Separator separatorThree;
    private javax.swing.JToolBar.Separator separatorTwo;
    private javax.swing.JToolBar tbRegistroInventarios;
    private org.jdesktop.swingx.JXTable tblOrdenesLevantamiento;

    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblFechaDesde;
    private javax.swing.JLabel lblFechaHasta;
    private javax.swing.JComboBox cmbAlmacen;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;

    // End of variables declaration//GEN-END:variables
    private BeanTableModel<OrdenLevantamientoFisico> ordenLevantamientoFisicoBeanTableModel;
    private OrdenLevantamientoFisico ordenLevantamientoSelected;
    private int rowSelected;
}