/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlRegistroOrdenesTrasladoInventario.java
 *
 * Created on 12-17-2011, 10:53:00 PM
 */
package contac.inventarios.movimiento.inventario.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.label.JOptionMessagePane;
import contac.commons.form.layout.XYConstraints;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.form.layout.XYLayout;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.*;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.tables.BeanTableModel;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.OrdenTrasladoController;
import contac.modelo.entity.OrdenMovimiento;
import contac.modelo.entity.OrdenTraslado;
import contac.modelo.entity.Almacen;
import contac.reports.JRPrintReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHeader;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author EMontenegro
 */
public class pnlRegistroOrdenesTrasladoInventario extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlRegistroOrdenesTrasladoInventario.class);

    //Controller
    private OrdenTrasladoController controller;

    private JXHeader header;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());


    /**
     * Creates new form pnlRegistroOrdenesTrasladoInventario
     */
    public pnlRegistroOrdenesTrasladoInventario(GenericFrame frame) {

        //Call super constructor
        super(frame, "OrdenTrasladoAlmacen", "Orden de Traslado entre Almac\u00e9nes", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Controller
        controller = new OrdenTrasladoController();

        try {

            //Init registros de datos
            controller.initRegistroOrdenesTraslado();

            //Init components
            initComponents();

            //Init registros de datos
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

        //Combo box almacen

        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.buscarAlmacenes()));
        ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        cmbAlmacen.setRenderer(rendererAlmacen);
        //cmbAlmacen.setSelectedIndex(-1);

        cmbAlmacenSalida.setModel(new AlmacenComboBoxModel(controller.buscarAlmacenes()));
        ListCellRenderer rendererAlmacenRecibe = new ComboBoxEmptySelectionRenderer(cmbAlmacenSalida, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        cmbAlmacenSalida.setRenderer(rendererAlmacenRecibe);
        //cmbAlmacenSalida.setSelectedIndex(-1);

            controller.setAlmacen(null);

        ordenTrasladoBeanTableModel = new BeanTableModel<OrdenTraslado>(OrdenTraslado.class, OrdenMovimiento.class,
              controller.getOrdenesTraslado());
        ordenTrasladoBeanTableModel.setModelEditable(false);
        tblOrdenesTraslado.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordenTrasladoBeanTableModel.sortColumnNames();
        tblOrdenesTraslado.setEditable(false);
        tblOrdenesTraslado.setModel(ordenTrasladoBeanTableModel);
        tblOrdenesTraslado.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel tableColumnModel = tblOrdenesTraslado.getColumnModel();
        String[] columnsRemove = new String[]{"Persona Recibe", "Persona Entrega", "Id", "Ctime", "Cuser", "Mtime", "Muser"};

        for (String columnLabel : columnsRemove) {
            tableColumnModel.removeColumn(tableColumnModel.getColumn(tableColumnModel.getColumnIndex(columnLabel)));
        }

        //Adding cell rendering class
        tblOrdenesTraslado.getColumn(0).setCellRenderer(new AlmacenFormatRenderer());
        tblOrdenesTraslado.getColumn(1).setCellRenderer(new AlmacenFormatRenderer());
        tblOrdenesTraslado.getColumn(3).setCellRenderer(new EstadoFormatRenderer());
        tblOrdenesTraslado.getColumn(4).setCellRenderer(new FechaFormatRenderer());
        tblOrdenesTraslado.getColumn(5).setCellRenderer(new MonedaFormatRenderer());
        tblOrdenesTraslado.getColumn(6).setCellRenderer(new DecimalFormatRenderer());

        //Ordering table columns
        tableColumnModel.moveColumn(7, 0); //No Movimiento
        tableColumnModel.moveColumn(4, 7); //Estado movimiento

        //Setting prefered size
        tableColumnModel.getColumn(0).setPreferredWidth(10);
        tableColumnModel.getColumn(3).setPreferredWidth(200);

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
        pnlRegistroInventarioWest = new javax.swing.JPanel();
        scrollOrdenesEntrada = new javax.swing.JScrollPane();
        scrollOrdenesEntradaWest = new JScrollPane();
        tblOrdenesTraslado = new org.jdesktop.swingx.JXTable();
        tbRegistroInventarios = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnEditar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnAplicarTraslado = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAnular = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnCancelar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        lblAlmacen = new javax.swing.JLabel();
        lblAlmacenSalida = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox();
        cmbAlmacenSalida = new javax.swing.JComboBox();

        lblFechaDesde = new JLabel();
        dtpFechaDesde = new org.jdesktop.swingx.JXDatePicker();
        lblFechaHasta = new JLabel();
        dtpFechaHasta = new org.jdesktop.swingx.JXDatePicker();

        header = new JXHeader();
        header.setTitle(messageBundle.getString("CONTAC.FORM.ORDENENTRADA.TITLE")); // NOI18N
        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new Dimension(50, 35));

        setLayout(new java.awt.BorderLayout());

        headerAlmacenes.setForeground(new java.awt.Color(255, 153, 0));
        headerAlmacenes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        headerAlmacenes.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        headerAlmacenes.setTitle(bundle.getString("CONTAC.FORM.ORDENTRASLADO.TITTLE")); // NOI18N
        headerAlmacenes.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(headerAlmacenes, java.awt.BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new XYLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.setPreferredSize(new Dimension(340, 400));

        tbRegistroInventarios.setBorder(null);
        tbRegistroInventarios.setMaximumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setMinimumSize(new java.awt.Dimension(124, 32));
        tbRegistroInventarios.setPreferredSize(new java.awt.Dimension(124, 32));

        lblFechaDesde.setHorizontalAlignment(SwingConstants.LEFT);
        lblFechaDesde.setText(bundle.getString("CONTAC.FORM.REGISTROTRASLADOS.FECHADESDE")); //NOI18N
        lblFechaDesde.setMaximumSize(new Dimension(75, 22));
        lblFechaDesde.setMinimumSize(new Dimension(75, 22));
        lblFechaDesde.setPreferredSize(new Dimension(75, 22));

        dtpFechaDesde.setMaximumSize(new Dimension(160, 22));
        dtpFechaDesde.setMinimumSize(new Dimension(160, 22));
        dtpFechaDesde.setPreferredSize(new Dimension(160, 22));

        lblFechaHasta.setHorizontalAlignment(SwingConstants.LEFT);
        lblFechaHasta.setText(bundle.getString("CONTAC.FORM.REGISTROTRASLADOS.FECHAHASTA")); //NOI18N
        lblFechaHasta.setMaximumSize(new Dimension(75, 22));
        lblFechaHasta.setMinimumSize(new Dimension(75, 22));
        lblFechaHasta.setPreferredSize(new Dimension(75, 22));

        dtpFechaHasta.setMaximumSize(new Dimension(160, 22));
        dtpFechaHasta.setMinimumSize(new Dimension(160, 22));
        dtpFechaHasta.setPreferredSize(new Dimension(160, 22));

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
        tbRegistroInventarios.add(jSeparator3);

        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png")));
        btnImprimir.setToolTipText(bundle.getString("CONTAC.FORM.BTNIMPRIMIR")); // NOI18N
        btnImprimir.setFocusable(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnImprimir.setMaximumSize(new Dimension(40, 32));
        btnImprimir.setMinimumSize(new Dimension(40, 32));
        btnImprimir.setName(""); // NOI18N

        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        //tbRegistroInventarios.add(btnImprimir);

        btnAplicarTraslado.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/trasladar16.png")));
        btnAplicarTraslado.setToolTipText(bundle.getString("CONTAC.FORM.BTNAPLICARTRASLADO")); // NOI18N
        btnAplicarTraslado.setFocusable(false);
        btnAplicarTraslado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAplicarTraslado.setMaximumSize(new Dimension(40, 32));
        btnAplicarTraslado.setMinimumSize(new Dimension(40, 32));
        btnAplicarTraslado.setName(""); // NOI18N

        btnAplicarTraslado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarTrasladoActionPerformed(evt);
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
        actionToolBar.add(btnImprimir);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnAplicarTraslado);

        JPanel trasladoPanel = new JPanel(new BorderLayout());
        trasladoPanel.setBorder(BorderFactory.createEtchedBorder());

        lblFechaDesde.setHorizontalAlignment(SwingConstants.LEFT);
        lblFechaDesde.setText(bundle.getString("CONTAC.FORM.REGISTROTRASLADOS.FECHADESDE")); //NOI18N
        lblFechaDesde.setMaximumSize(new Dimension(75, 22));
        lblFechaDesde.setMinimumSize(new Dimension(75, 22));
        lblFechaDesde.setPreferredSize(new Dimension(75, 22));

        dtpFechaDesde.setMaximumSize(new Dimension(160, 22));
        dtpFechaDesde.setMinimumSize(new Dimension(160, 22));
        dtpFechaDesde.setPreferredSize(new Dimension(160, 22));

        lblFechaHasta.setHorizontalAlignment(SwingConstants.LEFT);
        lblFechaHasta.setText(bundle.getString("CONTAC.FORM.REGISTROTRASLADOS.FECHAHASTA")); //NOI18N
        lblFechaHasta.setMaximumSize(new Dimension(75, 22));
        lblFechaHasta.setMinimumSize(new Dimension(75, 22));
        lblFechaHasta.setPreferredSize(new Dimension(75, 22));

        dtpFechaHasta.setMaximumSize(new Dimension(160, 22));
        dtpFechaHasta.setMinimumSize(new Dimension(160, 22));
        dtpFechaHasta.setPreferredSize(new Dimension(160, 22));

        lblAlmacen.setText(bundle.getString("CONTAC.FORM.REGISTROTRASLADOS.ALMACEN")); //NOI18N
        lblAlmacen.setMaximumSize(new Dimension(75, 22));
        lblAlmacen.setMinimumSize(new Dimension(75, 22));
        lblAlmacen.setPreferredSize(new Dimension(75, 22));

        lblAlmacenSalida.setText(bundle.getString("CONTAC.FORM.REGISTROTRASLADOS.ALMACEN.SALIDA")); //NOI18N
        lblAlmacenSalida.setMaximumSize(new Dimension(75, 22));
        lblAlmacenSalida.setMinimumSize(new Dimension(75, 22));
        lblAlmacenSalida.setPreferredSize(new Dimension(75, 22));

        //cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
        //cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
        cmbAlmacen.setLightWeightPopupEnabled(false);
        cmbAlmacen.setMaximumSize(new Dimension(160, 22));
        cmbAlmacen.setMinimumSize(new Dimension(160, 22));
        cmbAlmacen.setPreferredSize(new Dimension(160, 22));

        cmbAlmacenSalida.setLightWeightPopupEnabled(false);
        cmbAlmacenSalida.setMaximumSize(new Dimension(160, 22));
        cmbAlmacenSalida.setMinimumSize(new Dimension(160, 22));
        cmbAlmacenSalida.setPreferredSize(new Dimension(160, 22));

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

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/search.png")));
        btnBuscar.setText(bundle.getString("CONTAC.FORM.BTNBUSCAR.TRASLADO")); // NOI18N
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
        pnlRegistroInventario.setLayout(new java.awt.BorderLayout());

        searchPanel.add(lblFechaDesde, new XYConstraints(5, 5, 120, 23));
        searchPanel.add(dtpFechaDesde, new XYConstraints(130, 5, 120, 23));
        searchPanel.add(lblFechaHasta, new XYConstraints(5, 33, 120, 23));
        searchPanel.add(dtpFechaHasta, new XYConstraints(130, 33, 120, 23));
        searchPanel.add(lblAlmacen, new XYConstraints(5, 61, 100, 23));
        searchPanel.add(cmbAlmacen, new XYConstraints(130, 61, 200, 23));
        searchPanel.add(lblAlmacenSalida, new XYConstraints(5,89,100,23));
        searchPanel.add(cmbAlmacenSalida, new XYConstraints(130, 89, 200, 23));
        searchPanel.add(btnBuscar, new XYConstraints(75, 117, 90, 23));
        searchPanel.add(btnCancelar, new XYConstraints(175, 117, 90, 23));

        tblOrdenesTraslado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdenesTrasladoMouseClicked(evt);
            }
        });
        scrollOrdenesEntrada.setViewportView(tblOrdenesTraslado);
        scrollOrdenesEntradaWest.setViewportView(pnlRegistroInventarioWest);

        add(scrollOrdenesEntradaWest, BorderLayout.WEST);

        //pnlRegistroInventario.add(scrollOrdenesEntrada, java.awt.BorderLayout.CENTER);

        JScrollPane TrasladosScrollbar = new JScrollPane();
        TrasladosScrollbar.getViewport().add(tblOrdenesTraslado);

        trasladoPanel.add(actionToolBar, BorderLayout.NORTH);
        trasladoPanel.add(TrasladosScrollbar, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(trasladoPanel, BorderLayout.CENTER);
        //pnlRegistroInventario.add(tbRegistroInventarios, java.awt.BorderLayout.PAGE_START);

        //add(pnlRegistroInventario, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrdenesTrasladoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdenesTrasladoMouseClicked

        // Getting orden de entrada selected
        rowSelected = tblOrdenesTraslado.getSelectedRow();
        ordenTrasladoSelected = (OrdenTraslado) ((BeanTableModel) tblOrdenesTraslado.getModel()).getRow(rowSelected);
        controller.setOrdenTraslado(ordenTrasladoSelected);
    }//GEN-LAST:event_tblOrdenesTrasladoMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            //Obteniendo fechas de busqueda
            Date fechaDesde = dtpFechaDesde.getDate();
            Date fechaHasta = dtpFechaHasta.getDate();

            //Obtener parametros de busqueda
            Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                    getObject());

            Almacen almacenSalida = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacenSalida.getModel()).getSelectedItem().
                    getObject());

            //Consultando listado de Ordenes de Traslado

            controller.buscarOrdenesTraslado(fechaDesde, fechaHasta, almacen.getId(), almacenSalida.getId());

            //Actualizar listado de articulos ingresados
            ((BeanTableModel) tblOrdenesTraslado.getModel()).fireTableDataChanged();

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

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        //Open formulario de administracion de compania
        getMDI().getStyle().addPanel("pnlOrdenTrasladoInventario", "contac.inventarios.movimiento.inventario.app.pnlOrdenTrasladoInventario");

        //Remove this panel
        getMDI().getStyle().removePanel(this);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (ordenTrasladoSelected != null) {
            //Open formulario de ingreso de inventario fisico
            getMDI().getStyle().addPanel("pnlOrdenTrasladoInventario", "contac.inventarios.movimiento.inventario.app.pnlOrdenTrasladoInventario",
                    controller);

            //Remove this panel
            getMDI().getStyle().removePanel(this);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAplicarTrasladoActionPerformed(java.awt.event.ActionEvent evt) {  //GEN-FIRST:event_btnAplicarTrasladoActionPerformed
        try {

            // Si no ha seleccionado ningún Traslado a Imprimir
            if (ordenTrasladoSelected != null) {
               //Cambiar estado a Traslado y MI
                controller.aplicarOrdenTraslado();
                //Actualizar listado de articulos ingresados
                ((BeanTableModel) tblOrdenesTraslado.getModel()).fireTableDataChanged();
                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.INGRESO.EXITOSO"));
            }else{
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"),
                        messageBundle.getString("CONTAC.FORM.MSG.SELECCIONAR.TRASLADO.VALIDACION"));
            }

    } catch (Exception e) {
        logger.error(e.getMessage(), e);
        JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.REGISTRO.FALLIDO"),
                e.getMessage());
    }
   }

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnImprimirActionPerformed
        try {

            // Si no ha seleccionado ninguna Orden de Traslado a Imprimir
            if (ordenTrasladoSelected == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.IMPRIMIR.VALIDA"));
            }

            //Validar Estado de Orden de Traslado
            controller.ordenTrasladoImprimir();

                JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroOrdenesTrasladoInventario.class
                  .getResourceAsStream("/contac/inventarios/app/reportes/traslado_sucursales_report.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/inventarios/app/reportes") + "/");
            parameters.put("n_id_traslado", ordenTrasladoSelected.getId());

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

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed

        try {

            if (ordenTrasladoSelected != null) {

                //Setting orden de entrada selected
                controller.setOrdenTraslado(ordenTrasladoSelected);

                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.
                        format(messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.ANULA.CONFIRMA"),
                                new Object[]{ordenTrasladoSelected.getNoMovimiento()}));

                if (confirmation) {
                    //Remover orden de entrada
                    controller.anularOrdenTraslado();

                    //Show confirmation message
                    JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.ANULA.EXITOSO"),
                            messageBundle.getString("CONTAC.FORM.ORDENTRASLADO.ANULA.EXITOSO"));

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblOrdenesTraslado.getModel()).fireTableDataChanged();
                }
            }

            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnAplicarTraslado;
    private JButton btnCancelar;
    private JButton btnBuscar;
    private org.jdesktop.swingx.JXHeader headerAlmacenes;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JLabel lblAlmacen;
    private JLabel lblAlmacenSalida;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JComboBox cmbAlmacenSalida;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPanel pnlRegistroInventario;
    private javax.swing.JPanel pnlRegistroInventarioWest;
    private javax.swing.JScrollPane scrollOrdenesEntrada;
    private javax.swing.JScrollPane scrollOrdenesEntradaWest;
    private javax.swing.JToolBar tbRegistroInventarios;
    private org.jdesktop.swingx.JXTable tblOrdenesTraslado;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<OrdenTraslado> ordenTrasladoBeanTableModel;
    private OrdenTraslado ordenTrasladoSelected;
    private int rowSelected;
}
