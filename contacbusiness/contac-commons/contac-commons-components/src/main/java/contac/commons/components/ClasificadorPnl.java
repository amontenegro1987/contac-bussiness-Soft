/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * ClasificadorPnl.java
 *
 * Created on 02-01-2011, 02:25:28 PM
 */

package contac.commons.components;

import contac.commons.app.ClasificadorController;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.render.HighlightTreeCellRenderer;
import contac.commons.models.ClasificadorNode;
import contac.modelo.entity.Clasificador;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Eddy Montenegro
 */
public class ClasificadorPnl extends JDialog {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ClasificadorPnl.class);

    //Resource bundle de mensajes
    private static final String messageBundleUrl = "contac.commons.components.mensajes.Mensajes";

    //Conteo de doble click evento mouse
    private static final int DOUBLE_CLICK_MOUSE_EVENT = 2;

    //******************************************************************
    //Custom icons
    //******************************************************************
    private static final Icon customOpenIcon = new ImageIcon("");
    private static final Icon customClosedIcon = new ImageIcon("");
    private static final Icon customLeaftIcon = new ImageIcon("");

    //Controller
    private ClasificadorController controller;

    //Clasificador tree
    private javax.swing.JTree clasificadorTree;

    //Resource de mensajes
    private ResourceBundle messageBundle;

    //Clasificador selected
    private Clasificador clasificadorSelected;
    
    //Clasificador hightlight
    private Clasificador clasificadorHightlight;

    /**
     * Creates new form ClasificadorPnl
     */
    public ClasificadorPnl(GenericFrame frame, Clasificador clasificador, boolean modal) {

        //Constructor
        super(frame, modal);

        //Load bundle de mensajes
        messageBundle = ResourceBundle.getBundle(messageBundleUrl, new Locale("es", "NIC"));

        //Init controller
        controller = new ClasificadorController();
        
        //Init clasificador hightlight
        clasificadorHightlight = clasificador;

        //Init components
        initComponents();

        //Init listeners
        registerListeners();

        //Init clasificador components
        initClasificadorComponent();

        //Set visible
        this.setTitle(messageBundle.getString("CONTAC.FORM.CLASIFICADORPNL.TITULO"));
        this.setLocation(400, 300);
        this.setSize(new Dimension(600, 400));
        this.setVisible(true);
    }

    //<Register listeners>
    private void registerListeners() {

        //txtCodigo
        txtCodigoCBS.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    buscarClasificadores();
                }
                
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        //txtDescripcion
        txtDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    buscarClasificadores();
                }
            }
        });
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

        titleHeader = new org.jdesktop.swingx.JXHeader();
        scrollPane = new javax.swing.JScrollPane();
        pnlBusqueda = new javax.swing.JPanel();
        txtCodigoCBS = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblCodigo = new javax.swing.JLabel();
        lblNombreComercial = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(156, 200));

        titleHeader.setBackground(new java.awt.Color(204, 204, 204));
        titleHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        titleHeader.setPreferredSize(new java.awt.Dimension(51, 40));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/commons/components/mensajes/Mensajes_es"); // NOI18N
        titleHeader.setTitle(bundle.getString("CONTAC.FORM.CLASIFICADORPNL.TITULO")); // NOI18N
        titleHeader.setTitleForeground(new java.awt.Color(255, 153, 0));

        scrollPane.setBackground(new java.awt.Color(204, 204, 204));
        scrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        scrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        scrollPane.setEnabled(false);
        scrollPane.setMinimumSize(new java.awt.Dimension(25, 30));

        pnlBusqueda.setMaximumSize(new java.awt.Dimension(0, 0));
        pnlBusqueda.setMinimumSize(new java.awt.Dimension(10, 30));
        pnlBusqueda.setPreferredSize(new java.awt.Dimension(100, 75));

        txtCodigoCBS.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoCBS.setToolTipText("");
        txtCodigoCBS.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoCBS.setPreferredSize(new java.awt.Dimension(59, 30));

        btnBuscar.setText(bundle.getString("CONTAC.FORM.BTNBUSCAR")); // NOI18N
        btnBuscar.setActionCommand("");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodigo.setText(bundle.getString("CONTAC.FORM.CLASIFICADORPNL.CODIGOCBS")); // NOI18N

        lblNombreComercial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombreComercial.setText(bundle.getString("CONTAC.FORM.CLASIFICADORPNL.DESCRIPCIONCBS")); // NOI18N

        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcion.setToolTipText("");
        txtDescripcion.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescripcion.setPreferredSize(new java.awt.Dimension(59, 30));

        javax.swing.GroupLayout pnlBusquedaLayout = new javax.swing.GroupLayout(pnlBusqueda);
        pnlBusqueda.setLayout(pnlBusquedaLayout);
        pnlBusquedaLayout.setHorizontalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusquedaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBusquedaLayout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addGap(61, 61, 61)
                        .addComponent(lblNombreComercial))
                    .addGroup(pnlBusquedaLayout.createSequentialGroup()
                        .addComponent(txtCodigoCBS, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBuscar)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pnlBusquedaLayout.setVerticalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusquedaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigo)
                    .addComponent(lblNombreComercial))
                .addGap(6, 6, 6)
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigoCBS, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)))
        );

        btnBuscar.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
            .addComponent(titleHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarClasificadores();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void buscarClasificadores() {
        try {

            controller.setCbs(txtCodigoCBS.getText().equals("") ? 0 : Integer.parseInt(txtCodigoCBS.getText()));
            controller.setDescripcion(txtDescripcion.getText());

            //Buscar clasificador por parametros
            controller.buscarClasificadoresCBSPorParametros();

            //Cargar TreeView clasificadores
            renderizarTreeviewClasificadoresWithoutHierchy();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.CLASIFICADORPNL.ERROR"),
                    e.getMessage());
        }
    }

    //<Configurar clasificador>
    private void initClasificadorComponent() {
        
        try {
            //Obtener clasificador por tipos
            controller.buscarClasificadoresCBS();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.CLASIFICADORPNL.ERROR"),
                    e.getMessage());
        }

        //Renderizar TreeView clasificadores
        renderizarTreeViewClasificadores();
    }
    
    private void renderizarTreeviewClasificadoresWithoutHierchy() {

        //Init root node
        Clasificador root = new Clasificador();
        root.setDescripcion("Clasificador");
        ClasificadorNode rootNode = new ClasificadorNode(root);

        for (Clasificador clasificador : controller.getClasificadores()) {

            //Creando nodos para segmentos
            ClasificadorNode segmentoNode = new ClasificadorNode(clasificador);

            //Agregando al root node
            rootNode.add(segmentoNode);
        }

        //Instanciando arbol
        clasificadorTree = new JTree(rootNode);
        clasificadorTree.setCellRenderer(new HighlightTreeCellRenderer());
        clasificadorTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION
        );
        clasificadorTree.setToolTipText(messageBundle.getString("CONTAC.FORM.CLASIFICADORPNL.TOOLTIPTEXT"));

        //Configurando evento double click mouse
        clasificadorTree.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent event){
                if (event.getClickCount() == DOUBLE_CLICK_MOUSE_EVENT) {
                    getClasificadorSelectedEvent();
                }
            }
        });

        scrollPane.setViewportView(clasificadorTree);
        scrollPane.repaint();
    }

    //<Cargar TreeView clasificador
    private void renderizarTreeViewClasificadores() {

        //Init root node
        Clasificador root = new Clasificador();
        root.setId(0);
        root.setDescripcion("Clasificador");
        ClasificadorNode rootNode = new ClasificadorNode(root);

        java.util.List<Clasificador> segmentos = controller.getSegmentos();
        java.util.List<Clasificador> familias = controller.getFamilias();
        java.util.List<Clasificador> clases = controller.getClases();
        java.util.List<Clasificador> articulos = controller.getArticulos();

        //Iterando segmentos
        for (Clasificador segmento : segmentos) {

            //Creando nodos para segmentos
            ClasificadorNode segmentoNode = new ClasificadorNode(segmento);

            //Iterando familias
            for (Clasificador familia : familias) {

                //Creando nodos para familia
                ClasificadorNode familiaNode = new ClasificadorNode(familia);

                if (segmento.getId().equals(familia.getElementoPadre().getId())) {
                    segmentoNode.add(familiaNode);
                }

                //Iterando clases
                for (Clasificador clase : clases) {

                    //Creando nodos para clase
                    ClasificadorNode claseNode = new ClasificadorNode(clase);

                    if (familia.getId().equals(clase.getElementoPadre().getId())) {
                        familiaNode.add(claseNode);
                    }

                    //Iterando articulos
                    for (Clasificador articulo : articulos) {

                        //Creando nodos para articulo
                        ClasificadorNode articuloNode = new ClasificadorNode(articulo);

                        if (clase.getId().equals(articulo.getElementoPadre().getId())) {
                            claseNode.add(articuloNode);
                        }
                    }
                }
            }

            //Agregando al root node
            rootNode.add(segmentoNode);
        }

        //Instanciando arbol
        HighlightTreeCellRenderer treeCellRenderer = new HighlightTreeCellRenderer();
        clasificadorTree = new JTree(rootNode);
        clasificadorTree.setCellRenderer(treeCellRenderer);
        clasificadorTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION
        );
        clasificadorTree.setToolTipText(messageBundle.getString("CONTAC.FORM.CLASIFICADORPNL.TOOLTIPTEXT"));

        //Configurando evento double click mouse
        clasificadorTree.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent event){
                if (event.getClickCount() == DOUBLE_CLICK_MOUSE_EVENT) {
                    getClasificadorSelectedEvent();
                }
            }
        });
             
        scrollPane.setViewportView(clasificadorTree);
        
        //Search node selected
        if (clasificadorHightlight != null) {
            treeCellRenderer.setCbs(clasificadorHightlight.getCbs());
            TreePath path = clasificadorTree.getPathForRow(0);
            searchNode(path);
        }
    }

    /***
     * Search Tree node for hightlight
     */
    private void searchNode(TreePath path) {

        //TreePath path = clasificadorTree.getPathForRow(0);
        TreeNode node = (TreeNode)path.getLastPathComponent();

        if (node == null) return;

        ClasificadorNode clasificadorNode = (ClasificadorNode)node;
        if (clasificadorNode.getClasificador().getCbs() == clasificadorHightlight.getCbs()) {
            clasificadorTree.expandPath(path.getParentPath());
            clasificadorTree.scrollPathToVisible(path.getParentPath());
        }

        if (!clasificadorNode.isLeaf() && clasificadorNode.getChildCount() >= 0) {
            Enumeration e = node.children();
            while(e.hasMoreElements()) {
                searchNode(path.pathByAddingChild(e.nextElement()));
            }
        }
    }

    /**
     * Recuperar objeto clasificador
     */
    private void getClasificadorSelectedEvent() {

        //Obtener el nodo seleccionado
        ClasificadorNode clasificadorNode = (ClasificadorNode)clasificadorTree.getLastSelectedPathComponent();

        if (!clasificadorNode.isRoot()) {
            ClasificadorNode claseNode = (ClasificadorNode)clasificadorNode.getUserObject();

            //Setting clasificador selected in the visit
            clasificadorSelected = claseNode.getClasificador();

            //Closing this JDialog
            this.dispose();
        }
    }

    /**
     * Retorna el clasificador seleccionado
     * @return Clasificador
     */
    public Clasificador getClasificadorSelected() {
        return clasificadorSelected;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JPanel pnlBusqueda;
    private javax.swing.JScrollPane scrollPane;
    private org.jdesktop.swingx.JXHeader titleHeader;
    private javax.swing.JTextField txtCodigoCBS;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables

}
