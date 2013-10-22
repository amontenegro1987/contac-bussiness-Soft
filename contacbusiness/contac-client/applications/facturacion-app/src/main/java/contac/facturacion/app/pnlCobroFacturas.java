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

        try {

            //Iniciar registro de datos
            controller.initRegistrosFactura();

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
        searchPanel.setPreferredSize(new Dimension(305, 400));

        JPanel infoPanel = new JPanel(new XYLayout());
        infoPanel.setBorder(BorderFactory.createEtchedBorder());
        infoPanel.setPreferredSize(new Dimension(200, 400));

        JPanel pnlInfoFactura = new JPanel(new XYLayout());
        pnlInfoFactura.setBorder(BorderFactory.createEtchedBorder());
        pnlInfoFactura.setPreferredSize(new Dimension(195, 100));
        infoPanel.add(pnlInfoFactura, new XYConstraints(0, 35, 195, 111));



        lblRegistrosEncontrados = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROSENCONTRADOS.BUSQUEDA"));
        lblRegistrosEncontrados.setHorizontalAlignment(JLabel.CENTER);
        Font newLabelFont = new Font(lblRegistrosEncontrados.getFont().getName(),Font.BOLD,lblRegistrosEncontrados.getFont().getSize());
        lblRegistrosEncontrados.setFont(newLabelFont);
        lblRegistrosEncontrados.setForeground(Color.gray);
        lblRegistrosEncontrados.setSize(14,14);
        pnlInfoFactura.add(lblRegistrosEncontrados, new XYConstraints(10,10,175,25));

        lblRegistrosEncontradosLower = new JLabel(messageBundle.getString("CONTAC.FORM.REGISTROSENCONTRADOSLOWER.BUSQUEDA"));
        lblRegistrosEncontradosLower.setHorizontalAlignment(JLabel.CENTER);
        lblRegistrosEncontradosLower.setFont(newLabelFont);
        lblRegistrosEncontradosLower.setForeground(Color.gray);
        lblRegistrosEncontradosLower.setSize(17,17);
        pnlInfoFactura.add(lblRegistrosEncontradosLower, new XYConstraints(10,70,175,25));

        JPanel pnlTotalFacturado = new JPanel(new XYLayout());
        pnlTotalFacturado.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalFacturado.setPreferredSize(new Dimension(195,100));
        infoPanel.add(pnlTotalFacturado, new XYConstraints(0,146,195,111));

        lblTotalFacturado = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALFACTURADO.BUSQUEDA"));
        lblTotalFacturado.setHorizontalAlignment(JLabel.CENTER);
        lblTotalFacturado.setFont(newLabelFont);
        lblTotalFacturado.setForeground(Color.gray);
        lblTotalFacturado.setSize(14,14);
        pnlTotalFacturado.add(lblTotalFacturado, new XYConstraints(10,10,175,25));

        lblTotalFacturadoLower = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALFACTURADOLOWER.BUSQUEDA"));
        lblTotalFacturadoLower.setHorizontalAlignment(JLabel.CENTER);
        lblTotalFacturadoLower.setFont(newLabelFont);
        lblTotalFacturadoLower.setForeground(Color.gray);
        lblTotalFacturadoLower.setSize(17,17);
        pnlTotalFacturado.add(lblTotalFacturadoLower, new XYConstraints(10,70,175,25));

        JPanel pnlTotalCobrado = new JPanel(new XYLayout());
        pnlTotalCobrado.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalCobrado.setPreferredSize(new Dimension(195,100));
        infoPanel.add(pnlTotalCobrado, new XYConstraints(0,257,195,121));

        lblTotalCobrado = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALCOBRADO.BUSQUEDA"));
        lblTotalCobrado.setHorizontalAlignment(JLabel.CENTER);
        lblTotalCobrado.setFont(newLabelFont);
        lblTotalCobrado.setForeground(Color.gray);
        lblTotalCobrado.setSize(14,14);
        pnlTotalCobrado.add(lblTotalCobrado, new XYConstraints(10,10,175,25));

        lblTotalCobradoLower = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALCOBRADOLOWER.BUSQUEDA"));
        lblTotalCobradoLower.setHorizontalAlignment(JLabel.CENTER);
        lblTotalCobradoLower.setFont(newLabelFont);
        lblTotalCobradoLower.setForeground(Color.gray);
        lblTotalCobradoLower.setSize(17,17);
        pnlTotalCobrado.add(lblTotalCobradoLower, new XYConstraints(10,70,175,25));

        JPanel pnlTotalPendiente = new JPanel(new XYLayout());
        pnlTotalPendiente.setBorder(BorderFactory.createEtchedBorder());
        pnlTotalPendiente.setPreferredSize(new Dimension(195,100));
        infoPanel.add(pnlTotalPendiente, new XYConstraints(0,378,195,111));

        lblTotalPendiente = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALPENDIENTE.BUSQUEDA"));
        lblTotalPendiente.setHorizontalAlignment(JLabel.CENTER);
        lblTotalPendiente.setFont(newLabelFont);
        lblTotalPendiente.setForeground(Color.gray);
        lblTotalPendiente.setSize(14,14);
        pnlTotalPendiente.add(lblTotalPendiente, new XYConstraints(10,10,175,25));

        lblTotalPendienteLower = new JLabel(messageBundle.getString("CONTAC.FORM.TOTALPENDIENTELOWER.BUSQUEDA"));
        lblTotalPendienteLower.setHorizontalAlignment(JLabel.CENTER);
        lblTotalPendienteLower.setFont(newLabelFont);
        lblTotalPendienteLower.setForeground(Color.gray);
        lblTotalPendienteLower.setSize(17,17);
        pnlTotalPendiente.add(lblTotalPendienteLower, new XYConstraints(10,70,175,25));


        lblBusquedaFactura = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURAS.BUSQUEDA"));
        lblBusquedaFactura.setHorizontalAlignment(JLabel.LEFT);

        lblNoFactura = new JLabel(messageBundle.getString("CONTAC.FORM.COBROFACTURAS.NOFACTURA"));
        lblNoFactura.setHorizontalAlignment(JLabel.LEFT);

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

        ImageIcon agregarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/new.png"));
        ImageIcon editarIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/edit.png"));
        ImageIcon anularIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove2.png"));
        ImageIcon cancelIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/remove.png"));
        ImageIcon imprimirIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/print.png"));
        ImageIcon imprimirFacturaIco = new ImageIcon(getClass().getResource("/contac/resources/icons/actions/factura.png"));

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

        // Componentes de cobroToolBar

        btnImprimir = new JButton();
        btnImprimir.setPreferredSize(new Dimension(40, 32));
        btnImprimir.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIR"));
        btnImprimir.setIcon(imprimirIco);

        btnImprimirFactura = new JButton();
        btnImprimirFactura.setPreferredSize(new Dimension(40, 32));
        btnImprimirFactura.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIRFACTURA"));
        btnImprimirFactura.setIcon(imprimirFacturaIco);

        JButton btnCobrarFactura = new JButton("COBRAR"){
            @Override
            public void paintComponent(Graphics g)
            {
                g.setColor(Color.WHITE);
                g.fillRect(0,0, getSize().width, getSize().height);
                super.paintComponent(g);
            }
        };
        btnCobrarFactura.setContentAreaFilled(false);
        //btnCobrarFactura.setForeground(Color.WHITE);
                       /*
        btnCobrarFactura = new JButton(messageBundle.getString("CONTAC.FORM.BTNCOBRARFACTURA"));    */
        btnCobrarFactura.setPreferredSize(new Dimension(150,32));

        lblEstado = new JLabel(messageBundle.getString("CONTAC.FORM.COBROESTADO"));
        lblEstado.setPreferredSize(new Dimension(100,32));
        lblEstado.setHorizontalAlignment(JLabel.LEFT);

        btnTodasFacturas = new JButton(messageBundle.getString("CONTAC.FORM.TODASFACTURAS"));
        btnTodasFacturas.setBackground(Color.white);
        btnTodasFacturas.setPreferredSize(new Dimension(100,80));

        btnFacturasCobradas = new JButton(messageBundle.getString("CONTAC.FORM.FACTURASCOBRADAS"));
        btnFacturasCobradas.setBackground(Color.white);
        btnFacturasCobradas.setPreferredSize(new Dimension(100,80));

        btnFacturasEnCurso = new JButton(messageBundle.getString("CONTAC.FORM.FACTURASCURSO"));
        btnFacturasEnCurso.setBackground(Color.white);
        btnFacturasEnCurso.setPreferredSize(new Dimension(100,80));

        btnFacturasImpagadas = new JButton(messageBundle.getString("CONTAC.FORM.FACTURASIMPAGADAS"));
        btnFacturasImpagadas.setBackground(Color.white);
        btnFacturasImpagadas.setPreferredSize(new Dimension(100,80));

        /*btnImprimirFactura.setIcon(imprimirFacturaIco);
        btnImprimirFactura.setToolTipText(messageBundle.getString("CONTAC.FORM.BTNIMPRIMIRFACTURA"));
        btnImprimirFactura.setFocusable(false);
        btnImprimirFactura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimirFactura.setMaximumSize(new java.awt.Dimension(40,32));
        btnImprimirFactura.setMinimumSize(new java.awt.Dimension(40,32));
        btnImprimirFactura.setName(""); // NOI18N
        btnImprimirFactura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirFacturaActionPerformed(evt);
            }
        });*/

        JToolBar cobroToolBar = new JToolBar();
        cobroToolBar.setPreferredSize(new Dimension(500,32));

        cobroToolBar.add(lblEstado);
        //cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnTodasFacturas);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnFacturasCobradas);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnFacturasEnCurso);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnFacturasImpagadas);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnImprimir);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnImprimirFactura);
        cobroToolBar.add(new JToolBar.Separator());
        cobroToolBar.add(btnCobrarFactura);
        cobroToolBar.add(new JToolBar.Separator());

        JToolBar actionToolBar = new JToolBar();
        actionToolBar.setPreferredSize(new Dimension(200, 32));

        actionToolBar.add(btnAgregar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnEditar);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnAnular);
        actionToolBar.add(new JToolBar.Separator());
        actionToolBar.add(btnEliminar);
        actionToolBar.add(new JToolBar.Separator());

        JPanel facturasPanel = new JPanel(new BorderLayout());
        facturasPanel.setBorder(BorderFactory.createEtchedBorder());

        tblFacturasClientes = new JXTable();

        JScrollPane facturasScrollbar = new JScrollPane();
        facturasScrollbar.getViewport().add(tblFacturasClientes);

        facturasPanel.add(cobroToolBar, BorderLayout.NORTH);

        infoPanel.add(actionToolBar, BorderLayout.NORTH);
        facturasPanel.add(facturasScrollbar, BorderLayout.CENTER);

        //*********************************************************************
        //Create Main View
        //*********************************************************************
        this.setLayout(new BorderLayout());

        this.add(header, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.WEST);
        this.add(facturasPanel, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.EAST);

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

        btnImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirFacturaActionPerformed(evt);
            }
        });

        tblFacturasClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturasClientesMouseClicked(evt);
            }
        });
    }

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
                ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();
            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST: event_btnCancelarActionperformed
        //Init controller data
        controller.init();

        //Init formulario
        initValues();
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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {

            if (facturaSelected != null) {

                //Confirmation message
                boolean confirmation = JOptionMessagePane.showConfirmationInfo(null, messageBundle.
                        getString("CONTAC.FORM.MSG.ADVERTENCIA"), MessageFormat.format(messageBundle.
                        getString("CONTAC.FORM.FACTURACION.ELIMINAR.CONFIRMA"), new Object[]{facturaSelected.getNoDocumento()}));

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

                    //Obtener parametros de busqueda
                    Almacen almacen = ((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().
                            getObject());

                    TiposFactura tiposFactura = cmbTipoFactura.getModel().getSelectedItem() != null ?
                            ((TiposFactura) ((TipoFacturaComboBoxModel) cmbTipoFactura.getModel()).getSelectedItem().getObject()) :
                            null;

                    controller.buscarFacturasClientesPorFechas(fechaDesde, fechaHasta, almacen.getId(),
                            tiposFactura != null ? tiposFactura.getValue() : null);

                    //Actualizar listado de articulos ingresados
                    ((BeanTableModel) tblFacturasClientes.getModel()).fireTableDataChanged();
                }

            }

        } catch (Exception e) {
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        try {

            // Si no ha seleccionado ninguna Factura a Imprimir
            if (facturaSelected == null) {
                throw new Exception(messageBundle.getString("CONTAC.FORM.FACTURACION.VALIDA.REPORTEFACTURA"));
            }

            //Cambiar estado de factura a impresa.
            controller.imprimirFactura();

            JasperReport report = (JasperReport) JRLoader.loadObject(pnlRegistroFacturas.class.
                    getResourceAsStream("/contac/facturacion/app/reportes/Invoice_Garsa.jasper"));

            Map parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", getClass().getClassLoader().getResource("contac/facturacion/app/reportes") + "/");
            parameters.put("n_id_factura", facturaSelected.getId());

            //Generate Report
            JasperPrint jasperPrint = controller.getMgrReportesService().generateReport(parameters, report);

            //Print Report Preview
            JRPrintReport.printPreviewReport(getMDI(), jasperPrint);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show error message
            JOptionErrorPane.showMessageWarning(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"), e.getMessage());
        }

    }//GEN-LAST:event_btnImprimirFacturaActionPerformed

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
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarNoFactura;
    private javax.swing.JButton btnTodasFacturas;
    private javax.swing.JButton btnFacturasCobradas;
    private javax.swing.JButton btnFacturasEnCurso;
    private javax.swing.JButton btnFacturasImpagadas;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnImprimirFactura;
    private javax.swing.JButton btnCobrarFactura;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JComboBox cmbTipoFactura;
    private org.jdesktop.swingx.JXDatePicker dtpFechaDesde;
    private org.jdesktop.swingx.JXDatePicker dtpFechaHasta;
    private javax.swing.JTextField txtNoFactura;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblRegistrosEncontrados;
    private javax.swing.JLabel lblRegistrosEncontradosLower;
    private javax.swing.JLabel lblTotalPendiente;
    private javax.swing.JLabel lblTotalPendienteLower;
    private javax.swing.JLabel lblTotalFacturado;
    private javax.swing.JLabel lblTotalFacturadoLower;
    private javax.swing.JLabel lblTotalCobrado;
    private javax.swing.JLabel lblTotalCobradoLower;
    private javax.swing.JLabel lblBusquedaFactura;
    private javax.swing.JLabel lblNoFactura;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblFechaDesde;
    private javax.swing.JLabel lblFechaHasta;
    private javax.swing.JLabel lblTipoFactura;
    private org.jdesktop.swingx.JXTable tblFacturasClientes;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<Factura> facturaBeanTableModel;
    private Factura facturaSelected;
    private int rowSelected;
}
