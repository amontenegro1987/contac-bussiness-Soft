/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlAgenteVentas.java
 *
 * Created on 10-10-2012, 11:47:28 AM
 */
package contac.facturacion.app;

import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.models.comboBox.AlmacenComboBoxModel;
import contac.commons.models.comboBox.ComboBoxEmptySelectionRenderer;
import contac.commons.models.tables.BeanTableModel;
import contac.facturacion.controller.AgenteVentasController;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.AgenteVentas;
import contac.modelo.entity.Almacen;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author emortiz
 */
public class pnlAgenteVentas extends GenericPanel {

    //Apache Log4j
    private static final Logger logger = Logger.getLogger(pnlFacturaCliente.class);

    //Controller
    private AgenteVentasController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /** Creates new form pnlAgenteVentas */
    public pnlAgenteVentas(GenericFrame frame) {

        //Call super constructor
        super(frame, "agentesVentas", "Agentes de Ventas", true, "contac/facturacion/app/mensajes/Mensajes",
                new Locale("es", "NI"));

        //Init controller
        controller = new AgenteVentasController();

        //Init controller values
        controller.init();

        //Init components
        initComponents();

        //Init values
        initValues();

        //Register listeners
        registerListeners();

        //Editar registros
        controller.set_edit(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new org.jdesktop.swingx.JXHeader();
        frmAgenteVentas = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblAlmacen = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox();
        chkActivo = new javax.swing.JCheckBox();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        frmDetalleAgenteVentas = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        tblAgentesVentas = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        header.setScrollableTracksViewportWidth(false);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.AGENTEVENTAS.TITTLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        frmAgenteVentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText(bundle.getString("CONTAC.FORM.AGENTEVENTAS.CODIGO")); // NOI18N

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.setToolTipText(bundle.getString("CONTAC.FORM.AGENTEVENTAS.CODIGO")); // NOI18N
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(60, 30));

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText(bundle.getString("CONTAC.FORM.AGENTEVENTAS.NOMBRE")); // NOI18N

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setToolTipText(bundle.getString("CONTAC.FORM.AGENTEVENTAS.NOMBRE")); // NOI18N
        txtNombre.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombre.setPreferredSize(new java.awt.Dimension(60, 30));

        lblAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlmacen.setText(bundle.getString("CONTAC.FORM.FACTURACION.ALMACEN")); // NOI18N

        cmbAlmacen.setModel(new AlmacenComboBoxModel(controller.getAlmacenes()));
        cmbAlmacen.setToolTipText(bundle.getString("CONTAC.FORM.FACTURACION.ALMACEN")); // NOI18N

        chkActivo.setText(bundle.getString("CONTAC.FORM.AGENTEVENTAS.ESTADO")); // NOI18N
        chkActivo.setToolTipText(bundle.getString("CONTAC.FORM.AGENTEVENTAS.ESTADO")); // NOI18N

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setActionCommand(""); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.setActionCommand("");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmAgenteVentasLayout = new javax.swing.GroupLayout(frmAgenteVentas);
        frmAgenteVentas.setLayout(frmAgenteVentasLayout);
        frmAgenteVentasLayout.setHorizontalGroup(
            frmAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(chkActivo))
            .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        frmAgenteVentasLayout.setVerticalGroup(
            frmAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(frmAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(frmAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(frmAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmAgenteVentasLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(chkActivo)
                .addGap(27, 27, 27)
                .addGroup(frmAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar)))
        );

        lblCodigo.getAccessibleContext().setAccessibleName("");
        btnAceptar.getAccessibleContext().setAccessibleName("");

        add(frmAgenteVentas, java.awt.BorderLayout.CENTER);

        frmDetalleAgenteVentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        frmDetalleAgenteVentas.setPreferredSize(new java.awt.Dimension(250, 208));

        tblAgentesVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAgentesVentasMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(tblAgentesVentas);

        javax.swing.GroupLayout frmDetalleAgenteVentasLayout = new javax.swing.GroupLayout(frmDetalleAgenteVentas);
        frmDetalleAgenteVentas.setLayout(frmDetalleAgenteVentasLayout);
        frmDetalleAgenteVentasLayout.setHorizontalGroup(
            frmDetalleAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmDetalleAgenteVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        frmDetalleAgenteVentasLayout.setVerticalGroup(
            frmDetalleAgenteVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmDetalleAgenteVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(frmDetalleAgenteVentas, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void initValues() {
        
        try {

            //Almacen de asignacion
            ListCellRenderer rendererAlmacen = new ComboBoxEmptySelectionRenderer(cmbAlmacen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
            if (controller.getAlmacen() != null) {
                AlmacenComboBoxModel almacenModel = (AlmacenComboBoxModel) cmbAlmacen.getModel();
                cmbAlmacen.setRenderer(rendererAlmacen);
                cmbAlmacen.setSelectedItem(almacenModel.searchSelectedItem(controller.getAlmacen().getId()));
            } else {
                cmbAlmacen.setRenderer(rendererAlmacen);
                cmbAlmacen.setSelectedIndex(-1);
            }

            //Init listado de agentes de ventas registrados
            agenteVentasBeanTableModel = new BeanTableModel<AgenteVentas>(AgenteVentas.class, controller.getAgenteVentasList());
            agenteVentasBeanTableModel.setModelEditable(false);
            agenteVentasBeanTableModel.sortColumnNames();
            tblAgentesVentas.setModel(agenteVentasBeanTableModel);
            tblAgentesVentas.setRowSelectionAllowed(true);

            //Obteniendo table column model y removiendo columnas innecesarias
            TableColumnModel agentesVentasColumnModel = tblAgentesVentas.getColumnModel();
            String[] agentesVentasColumnsRemove = new String[]{"Id", "Almacen", "Ctime", "Cuser", "Mtime", "Muser"};

            for (String columnLabel : agentesVentasColumnsRemove) {
                agentesVentasColumnModel.removeColumn(agentesVentasColumnModel.getColumn(agentesVentasColumnModel.getColumnIndex(columnLabel)));
            }

            //Ordering table column
            agentesVentasColumnModel.moveColumn(0, 2);

            //Setting prefered size
            agentesVentasColumnModel.getColumn(0).setPreferredWidth(12);
            agentesVentasColumnModel.getColumn(2).setPreferredWidth(12);

            //Cargar datos
            this.cargarDatos();
        
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.INIT"),
                    e.getMessage());
        }
    }

    //Iniciar la carga de datos del controller
    private void cargarDatos() {

        //Evaluar edit datos para registrar
        if (!controller.is_edit()) {

            //Set editable true
            txtCodigo.setEditable(true);

            //Cambiar label del boton aceptar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));
        }

        if (controller.is_edit()) {

            //Set editable false
            txtCodigo.setEditable(false);

            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));
        }

        //Codigo de agente de ventas
        if (controller.getCodigo() > 0)
            txtCodigo.setText(controller.getCodigo()+"");
        else
            txtCodigo.setText("");

        //Nombre de agente
        txtNombre.setText(controller.getNombre());
        //Estado activo
        chkActivo.setSelected(controller.isActivo());
        //Almacen
        if (controller.getAlmacen() != null) {
            cmbAlmacen.setSelectedItem(((AlmacenComboBoxModel)cmbAlmacen.getModel()).searchSelectedItem(controller.getAlmacen().getId()));
        } else {
            cmbAlmacen.setSelectedIndex(-1);
        }

        //Actualizar tabla de agentes de ventas
        ((BeanTableModel) tblAgentesVentas.getModel()).fireTableDataChanged();

        //Setting focus
        txtCodigo.requestFocusInWindow();
    }

    //Register listeners
    private void registerListeners() {

        //*****************************************************************************
        //<Register listeners>
        //*****************************************************************************

        //<txtCodigo>
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtNombre.requestFocusInWindow();
                }
            }
        });

        //<txtNombre>
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidCharacter(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbAlmacen.requestFocusInWindow();
                }
            }
        });
        
        //<cmbAlmacen>
        cmbAlmacen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    btnAceptar.requestFocusInWindow();
                }
            }
        });
    }

    private void tblAgentesVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAgentesVentasMouseClicked
        try {

            //Getting user selected
            int rowSelected = tblAgentesVentas.getSelectedRow();
            controller.setAgenteVentas((AgenteVentas) ((BeanTableModel) tblAgentesVentas.getModel()).getRow(rowSelected));

            //Init modificacion value
            controller.initModificacion();

            //Set editable true
            controller.set_edit(true);
            
            //Cargar datos
            this.cargarDatos();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR.INIT"),
                    e.getMessage());
        }
    }//GEN-LAST:event_tblAgentesVentasMouseClicked

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
        try {
            
            //Validar datos 
            validarDatosForm();
            
            //Setting values
            controller.setCodigo(Integer.valueOf(txtCodigo.getText()));
            controller.setNombre(txtNombre.getText());
            controller.setAlmacen((Almacen) ((AlmacenComboBoxModel) cmbAlmacen.getModel()).getSelectedItem().getObject());
            controller.setActivo(chkActivo.isSelected());
            
            if (!controller.is_edit()) {

                //Guardar agente de ventas
                controller.registrarAgenteVentas();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.AGENTEVENTAS.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.AGENTEVENTAS.INGRESO.EXITOSO"));
            } else {

                //Modificar agente de ventas
                controller.modificarAgenteVentas();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.MSG.AGENTEVENTAS.MODIFICACION.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.MSG.AGENTEVENTAS.MODIFICACION.EXITOSO"));      
                
            }
            
            //limpiar datos
            controller.init();

            //cargar datos
            initValues();
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show confirmation message
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.MSG.ERROR"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //limpiar datos
        controller.init();

        //cargar datos
        cargarDatos();
    }//GEN-LAST:event_btnCancelarActionPerformed
    
    //Validar datos form
    private void validarDatosForm() throws Exception {
        
        if (txtCodigo.getText().equals("")) {
            //Request focus
            txtCodigo.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.AGENTEVENTAS.CODIGO.VALIDA"));
        }
        
        if (txtNombre.getText().equals("")) {
            //Request focus
            txtNombre.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.AGENTEVENTAS.NOMBRE.VALIDA"));
        }

        if (cmbAlmacen.getSelectedIndex() == -1) {
            //Request focus
            cmbAlmacen.requestFocusInWindow();
            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.AGENTEVENTAS.ALMACEN.VALIDA"));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JPanel frmAgenteVentas;
    private javax.swing.JPanel frmDetalleAgenteVentas;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tblAgentesVentas;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    private BeanTableModel<AgenteVentas> agenteVentasBeanTableModel;

}
