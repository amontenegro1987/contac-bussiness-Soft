/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * pnlAdmonProducto.java
 *
 * Created on Aug 23, 2011, 9:46:48 PM
 */

package contac.inventarios.producto.app;

import contac.commons.components.ClasificadorPnl;
import contac.commons.components.pnlBusquedaProducto;
import contac.commons.form.label.JErrorLabel;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.panel.GenericFrame;
import contac.commons.form.panel.GenericPanel;
import contac.commons.form.render.DecimalFormatRenderer;
import contac.commons.models.comboBox.*;
import contac.commons.models.tables.BeanTableModel;
import contac.image.ImageFilter;
import contac.image.ImagePreview;
import contac.image.ImageUtils;
import contac.internationalization.LanguageLocale;
import contac.inventarios.controller.AdministraProductoController;
import contac.modelo.entity.*;
import contac.text.TextUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author emontenegro
 */
public class pnlAdmonProducto extends GenericPanel {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(pnlAdmonProducto.class);

    //Controller
    private AdministraProductoController controller;

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    /**
     * Creates new form pnlAdmonProducto
     */
    public pnlAdmonProducto(GenericFrame frame) {

        //Call super constructor
        super(frame, "administraProductos", "Cat\u00e1logo de Productos", true, "contac/inventarios/app/mensajes/Mensajes",
                new Locale("es", "NIC"));

        //Init controller
        controller = new AdministraProductoController();

        //Init components
        initComponents();

        //Init controller
        controller.init();

        //Init values
        initValues();

        //Registrar listeners
        registerListeners();

        //Request focus init
        txtCodigo.requestFocusInWindow();
    }

    @Override
    public void initValues() {
        try {

            //Cargar datos init
            cargarDatos();

            //Request focus
            txtCodigo.requestFocusInWindow();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //Show message error
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.INIT"),
                    e.getMessage());
        }
    }

    /**
     * Cargar datos formulario
     */
    private void cargarDatos() {

        //*************************************************
        //Init data values components
        //*************************************************

        //Evaluar edit datos para registrar
        if (!controller.is_edit()) {
            //Cambiar label del boton registrar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));

            //Setting codigo producto not editable
            txtCodigo.setEditable(true);

            //Habilitar opciones
            btnEliminar.setEnabled(false);
            btnRecodificar.setEnabled(false);
        }

        //Evaluar edit datos para modificar
        if (controller.is_edit()) {
            //Cambiar label del boton modificar
            btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

            //Setting codigo producto editable
            txtCodigo.setEditable(false);

            //Habilitar opciones
            btnEliminar.setEnabled(true);
            btnRecodificar.setEnabled(true);
        }

        //Codigo
        txtCodigo.setText(controller.getCodigo());

        //Producto compuesto
        chkCompuesto.setSelected(false);
        if (controller.isCompuesto())
            chkCompuesto.setSelected(true);

        //Nombre
        txtNombre.setText(controller.getNombre());

        //Codigo fabricante
        txtCodigoFabricante.setText(controller.getCodigoFabricante());

        //Marca
        txtMarca.setText(controller.getMarca());

        //Modelo
        txtModelo.setText(controller.getModelo());

        //Alias
        txtAlias.setText(controller.getAlias());

        //Codigo CBS
        txtCodigoCBS.setText("");
        txtDescripcionCBS.setText("");
        if (controller.getClasificador() != null) {
            txtCodigoCBS.setText(String.valueOf(controller.getClasificador().getCbs()));
            txtDescripcionCBS.setText(controller.getClasificador().getDescripcion());
        }

        //Unidad medida
        ListCellRenderer rendererUnidadMedida = new ComboBoxEmptySelectionRenderer(cmbUnidadMedida, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getUnidadMedida() != null) {
            UnidadMedidaComboBoxModel model = (UnidadMedidaComboBoxModel) cmbUnidadMedida.getModel();
            cmbUnidadMedida.setRenderer(rendererUnidadMedida);
            cmbUnidadMedida.setSelectedItem(model.searchSelectedItem(controller.getUnidadMedida().getId()));
        } else {
            cmbUnidadMedida.setRenderer(rendererUnidadMedida);
            cmbUnidadMedida.setSelectedIndex(-1);
        }

        //Proveedor
        ListCellRenderer rendererProveedor = new ComboBoxEmptySelectionRenderer(cmbProveedor, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getProveedor() != null) {
            ProveedorComboBoxModel model = (ProveedorComboBoxModel) cmbProveedor.getModel();
            cmbProveedor.setRenderer(rendererProveedor);
            cmbProveedor.setSelectedItem(model.searchSelectedItem(controller.getProveedor().getId()));
        } else {
            cmbProveedor.setRenderer(rendererProveedor);
            cmbProveedor.setSelectedIndex(-1);
        }

        //Linea de producto
        ListCellRenderer rendererLineaProducto = new ComboBoxEmptySelectionRenderer(cmbLinea, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getLinea() != null) {
            LineaComboBoxModel model = (LineaComboBoxModel) cmbLinea.getModel();
            cmbLinea.setRenderer(rendererLineaProducto);
            cmbLinea.setSelectedItem(model.searchSelectedItem(controller.getLinea().getId()));
        } else {
            cmbLinea.setRenderer(rendererLineaProducto);
            cmbLinea.setSelectedIndex(-1);
        }

        //Exento de impuesto
        chkExento.setSelected(false);
        if (controller.isExento()) {
            chkExento.setSelected(true);
        }

        //Observaciones
        txtObservaciones.setText(controller.getObservaciones());

        //Pais Origen
        ListCellRenderer rendererPais = new ComboBoxEmptySelectionRenderer(cmbPaisOrigen, messageBundle.getString("CONTAC.FORM.MSG.SELECCIONE"));
        if (controller.getPaisOrigen() != null) {
            PaisComboBoxModel model = (PaisComboBoxModel) cmbPaisOrigen.getModel();
            cmbPaisOrigen.setRenderer(rendererPais);
            cmbPaisOrigen.setSelectedItem(model.searchSelectedItem(controller.getPaisOrigen().getId()));
        } else {
            cmbPaisOrigen.setRenderer(rendererPais);
            cmbPaisOrigen.setSelectedIndex(-1);
        }

        //Cantidad minimo existencia
        txtMinimo.setText("");
        if (controller.getMinimo() != -1)
            txtMinimo.setText(String.valueOf(controller.getMinimo()));

        //Cantidad maximo existencia
        txtMaximo.setText("");
        if (controller.getMaximo() != -1)
            txtMaximo.setText(String.valueOf(controller.getMaximo()));

        //Costo FOB Factura
        txtCostoFOB.setText("");
        if (controller.getCostoFOB() != null)
            txtCostoFOB.setText(TextUtil.formatCurrency(controller.getCostoFOB().doubleValue()));

        //Costo CIF
        txtCostoCIF.setText("");
        if (controller.getCostoCIF() != null)
            txtCostoCIF.setText(TextUtil.formatCurrency(controller.getCostoCIF().doubleValue()));

        //Costo UND
        txtCostoUND.setText("");
        if (controller.getCostoUND() != null)
            txtCostoUND.setText(TextUtil.formatCurrency(controller.getCostoUND().doubleValue()));

        //Costo PROM
        txtCostoPROM.setText("");
        if (controller.getCostoPROM() != null)
            txtCostoPROM.setText(TextUtil.formatCurrency(controller.getCostoPROM().doubleValue()));

        //Precio estandar
        txtPrecioEstandar.setText("");
        if (controller.getPrecioEstandar() != null)
            txtPrecioEstandar.setText(TextUtil.formatCurrency(controller.getPrecioEstandar().doubleValue()));

        //Descuento maximo
        txtDescuento.setText("");
        if (controller.getDescuento() != null)
            txtDescuento.setText(TextUtil.formatCurrency(controller.getDescuento().doubleValue()));

        //Precio promocion
        txtPrecioPromocion.setText("");
        if (controller.getPrecioPromocion() != null)
            txtPrecioPromocion.setText(TextUtil.formatCurrency(controller.getPrecioPromocion().doubleValue()));

        //Fotografia
        if (controller.getFotografia() != null) {
            ImageIcon imageIcon = new ImageIcon();
            imageIcon.setImage(ImageUtils.convertToImage(controller.getFotografia()));
            lblFotografia.setIcon(imageIcon);
        } else {
            lblFotografia.setIcon(null);
            imageIcon = null;
        }

        //Productos
        productoBeanTableModel = new BeanTableModel<AdministraProductoController.ProductoCompuestoFacade>
                (AdministraProductoController.ProductoCompuestoFacade.class, controller.getProductosFacade());
        productoBeanTableModel.setModelEditable(false);
        productoBeanTableModel.sortColumnNames();
        tblProductos.setModel(productoBeanTableModel);
        tblProductos.setRowSelectionAllowed(true);

        //Obteniendo table column model y removiendo columnas innecesarias
        TableColumnModel columnModel = tblProductos.getColumnModel();

        //Remove Columns
        String columnRemove = "Producto Compuesto";
        columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex(columnRemove)));

        //Adding cell renderer class to currency price values
        DecimalFormatRenderer decimalFormatRenderer = new DecimalFormatRenderer();
        decimalFormatRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(3).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(4).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(6).setCellRenderer(decimalFormatRenderer);
        columnModel.getColumn(7).setCellRenderer(decimalFormatRenderer);

        //Ordenar columnas de la tabla
        columnModel.moveColumn(1, 0); //Codigo del producto
        columnModel.moveColumn(5, 1); //Nombre del producto
        columnModel.moveColumn(3, 2); //Codigo del fabricante

        //Reset productoCompuestoSelected
        productoCompuestoSelected = null;

    }

    private void registerListeners() { //BEGIN: Register Listeners

        //*****************************************************************************
        //<Register listeners>
        //*****************************************************************************

        //txtCodigo
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    chkCompuesto.requestFocusInWindow();
                }
            }
        });

        //chkCompuesto
        chkCompuesto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtNombre.requestFocusInWindow();
                    txtNombre.selectAll();
                }
            }
        });

        //txtNombre
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCodigoFabricante.requestFocusInWindow();
                    txtCodigoFabricante.selectAll();
                }
            }
        });

        //txtCodigoFabricante
        txtCodigoFabricante.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtMarca.requestFocusInWindow();
                    txtMarca.selectAll();
                }
            }
        });

        //txtMarca
        txtMarca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtModelo.requestFocusInWindow();
                    txtModelo.selectAll();
                }
            }
        });

        //txtModelo
        txtModelo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtAlias.requestFocusInWindow();
                    txtAlias.selectAll();
                }
            }
        });

        //txtAlias
        txtAlias.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCodigoCBS.requestFocusInWindow();
                    txtCodigoCBS.selectAll();
                }
            }
        });

        //txtCodigoCBS
        txtCodigoCBS.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbUnidadMedida.requestFocusInWindow();
                }
            }
        });

        //cmbUnidadMedida
        cmbUnidadMedida.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbProveedor.requestFocusInWindow();
                }
            }
        });

        //cmbProveedor
        cmbProveedor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbLinea.requestFocusInWindow();
                }
            }
        });

        //cmbLinea
        cmbLinea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    chkExento.requestFocusInWindow();
                }
            }
        });

        //chkExento
        chkExento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtObservaciones.requestFocusInWindow();
                    txtObservaciones.selectAll();
                }
            }
        });

        //txtObservaciones
        txtObservaciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    cmbPaisOrigen.requestFocusInWindow();
                }
            }
        });

        //cmbPaisOrigen
        cmbPaisOrigen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtMinimo.requestFocusInWindow();
                    txtMinimo.selectAll();
                }
            }
        });

        //txtMinimo
        txtMinimo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtMaximo.requestFocusInWindow();
                    txtMaximo.selectAll();
                }
            }
        });

        //txtMaximo
        txtMaximo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCostoFOB.requestFocusInWindow();
                    txtCostoFOB.selectAll();
                }
            }
        });

        //txtCostoFOB
        txtCostoFOB.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCostoCIF.requestFocusInWindow();
                    txtCostoCIF.selectAll();
                }
            }
        });

        //txtCostoCIF
        txtCostoCIF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtCostoUND.requestFocusInWindow();
                    txtCostoUND.selectAll();
                }
            }
        });

        //txtCostoUND
        txtCostoUND.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtPrecioEstandar.requestFocusInWindow();
                    txtPrecioEstandar.selectAll();
                }
            }
        });

        //txtPrecioEstandar
        txtPrecioEstandar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtDescuento.requestFocusInWindow();
                    txtDescuento.selectAll();
                }
            }
        });

        //txtDescuento
        txtDescuento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    btnAceptar.requestFocusInWindow();
                }
            }
        });

        //txtCantidadCompuesto
        txtCantidadCompuesto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    txtNuevoPrecioEstandarCompuesto.requestFocusInWindow();
                    txtNuevoPrecioEstandarCompuesto.selectAll();
                }
            }
        });

        //txtNuevoPrecioEstandarCompuesto
        txtNuevoPrecioEstandarCompuesto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!TextUtil.isValidDigitCurrency(e.getKeyChar())) {
                    e.consume();
                }

                if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                    btnAgregarCompuesto.requestFocusInWindow();
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

        header = new org.jdesktop.swingx.JXHeader();
        productoTab = new javax.swing.JTabbedPane();
        pnlDatosGenerales = new javax.swing.JPanel();
        frmDatosGenerales = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        txtCodigoFabricante = new javax.swing.JTextField();
        lblCodigoFabricante = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        lblAlias = new javax.swing.JLabel();
        txtAlias = new javax.swing.JTextField();
        lblCodigoCBS = new javax.swing.JLabel();
        txtCodigoCBS = new javax.swing.JTextField();
        txtDescripcionCBS = new javax.swing.JTextField();
        lblUnidadMedida = new javax.swing.JLabel();
        cmbUnidadMedida = new javax.swing.JComboBox();
        lblProveedor = new javax.swing.JLabel();
        cmbProveedor = new javax.swing.JComboBox();
        chkExento = new javax.swing.JCheckBox();
        pnlPrecios = new javax.swing.JPanel();
        txtCostoPROM = new javax.swing.JTextField();
        lblCostoPROM = new javax.swing.JLabel();
        lblCostoUND = new javax.swing.JLabel();
        txtCostoUND = new javax.swing.JTextField();
        txtCostoCIF = new javax.swing.JTextField();
        lblCostoCIF = new javax.swing.JLabel();
        txtCostoFOB = new javax.swing.JTextField();
        lblCostoFOB = new javax.swing.JLabel();
        cmbLinea = new javax.swing.JComboBox();
        lblUnidadMedida1 = new javax.swing.JLabel();
        lblPaisOrigen = new javax.swing.JLabel();
        spDireccion = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        chkCompuesto = new javax.swing.JCheckBox();
        btnBuscarProducto = new javax.swing.JButton();
        lblMinimo = new javax.swing.JLabel();
        txtMinimo = new javax.swing.JTextField();
        lblMaximo = new javax.swing.JLabel();
        txtMaximo = new javax.swing.JTextField();
        pnlPrecio = new javax.swing.JPanel();
        lblPrecioEstandar = new javax.swing.JLabel();
        txtPrecioEstandar = new javax.swing.JTextField();
        lblDescuento = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        lblPorcentaje = new javax.swing.JLabel();
        txtPrecioPromocion = new javax.swing.JTextField();
        lblCostoPROM2 = new javax.swing.JLabel();
        btnBuscarClasificador1 = new javax.swing.JButton();
        lblUnidadMedida3 = new javax.swing.JLabel();
        cmbPaisOrigen = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnRecodificar = new javax.swing.JButton();
        pnlAcciones = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        pnlFotografia = new javax.swing.JPanel();
        lblFotografia = new javax.swing.JLabel();
        btnAgregarImagen = new javax.swing.JButton();
        pnlProductosCompuestos = new javax.swing.JPanel();
        pnlProductoCompuesto = new javax.swing.JPanel();
        lblCodigoCompuesto = new javax.swing.JLabel();
        txtCodigoCompuesto = new javax.swing.JTextField();
        btnBuscarProductoCompuesto = new javax.swing.JButton();
        lblNombreCompuesto = new javax.swing.JLabel();
        txtNombreCompuesto = new javax.swing.JTextField();
        lblCodigoFabricanteCompuesto = new javax.swing.JLabel();
        txtCodigoFabricanteCompuesto = new javax.swing.JTextField();
        lblNuevoPrecioEstandarCompuesto = new javax.swing.JLabel();
        txtMarcaCompuesto = new javax.swing.JTextField();
        txtCantidadCompuesto = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblCantidadCompuesto = new javax.swing.JLabel();
        lblMarcaCompuesto = new javax.swing.JLabel();
        txtNuevoPrecioEstandarCompuesto = new javax.swing.JTextField();
        lblCodigoCBSCompuesto = new javax.swing.JLabel();
        txtCodigoCBSCompuesto = new javax.swing.JTextField();
        lblUnidadMedidaCompuesto = new javax.swing.JLabel();
        txtUnidadMedidaCompuesto = new javax.swing.JTextField();
        lblLineaProductoCompuesto = new javax.swing.JLabel();
        txtLineaProductoCompuesto = new javax.swing.JTextField();
        lblProveedorCompuesto = new javax.swing.JLabel();
        txtProveedorCompuesto = new javax.swing.JTextField();
        lblPrecioEstandarCompuesto = new javax.swing.JLabel();
        txtPrecioEstandarCompuesto = new javax.swing.JTextField();
        lblCostoPromCompuesto = new javax.swing.JLabel();
        txtPrecioPromCompuesto = new javax.swing.JTextField();
        lblCostoUNDCompuesto = new javax.swing.JLabel();
        txtCostoUNDCompuesto = new javax.swing.JTextField();
        lblCostoPROMCompuesto = new javax.swing.JLabel();
        txtCostoPROMCompuesto = new javax.swing.JTextField();
        btnAgregarCompuesto = new javax.swing.JButton();
        btnCancelarCompuesto = new javax.swing.JButton();
        pnlRegistrosProductosCompuestos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new org.jdesktop.swingx.JXTable();

        setPreferredSize(new java.awt.Dimension(1252, 600));
        setLayout(new java.awt.BorderLayout());

        header.setForeground(new java.awt.Color(255, 153, 0));
        header.setPreferredSize(new java.awt.Dimension(51, 35));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes_es"); // NOI18N
        header.setTitle(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.TITTLE")); // NOI18N
        header.setTitleForeground(new java.awt.Color(255, 153, 0));
        add(header, java.awt.BorderLayout.PAGE_START);

        productoTab.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        productoTab.setFocusable(false);
        productoTab.setRequestFocusEnabled(false);

        pnlDatosGenerales.setPreferredSize(new java.awt.Dimension(800, 540));
        pnlDatosGenerales.setLayout(new java.awt.BorderLayout());

        frmDatosGenerales.setPreferredSize(new java.awt.Dimension(600, 420));
        frmDatosGenerales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO")); // NOI18N
        frmDatosGenerales.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 12, 120, 20));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setToolTipText("");
        txtCodigo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });
        frmDatosGenerales.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 11, 104, 23));

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE")); // NOI18N
        frmDatosGenerales.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 41, 120, 20));

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setToolTipText("");
        txtNombre.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombre.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 40, 486, 23));

        lblMarca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMarca.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MARCA")); // NOI18N
        frmDatosGenerales.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 70, 58, 20));

        txtCodigoFabricante.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoFabricante.setToolTipText("");
        txtCodigoFabricante.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoFabricante.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtCodigoFabricante, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 69, 212, 23));

        lblCodigoFabricante.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoFabricante.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOFABRICANTE")); // NOI18N
        frmDatosGenerales.add(lblCodigoFabricante, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 70, 120, 20));

        txtMarca.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMarca.setToolTipText("");
        txtMarca.setMinimumSize(new java.awt.Dimension(6, 25));
        txtMarca.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(443, 69, 202, 23));

        lblModelo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblModelo.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MODELO")); // NOI18N
        frmDatosGenerales.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 99, 120, 20));

        txtModelo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtModelo.setToolTipText("");
        txtModelo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtModelo.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 98, 212, 23));

        lblAlias.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAlias.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ALIAS")); // NOI18N
        frmDatosGenerales.add(lblAlias, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 99, 58, 20));

        txtAlias.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtAlias.setToolTipText("");
        txtAlias.setMinimumSize(new java.awt.Dimension(6, 25));
        txtAlias.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtAlias, new org.netbeans.lib.awtextra.AbsoluteConstraints(443, 98, 202, 23));

        lblCodigoCBS.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoCBS.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOCBS")); // NOI18N
        frmDatosGenerales.add(lblCodigoCBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 128, 120, 20));

        txtCodigoCBS.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoCBS.setToolTipText("");
        txtCodigoCBS.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoCBS.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigoCBS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoCBSFocusLost(evt);
            }
        });
        frmDatosGenerales.add(txtCodigoCBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 127, 103, 23));

        txtDescripcionCBS.setEditable(false);
        txtDescripcionCBS.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcionCBS.setToolTipText("");
        txtDescripcionCBS.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescripcionCBS.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtDescripcionCBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 127, 342, 23));

        lblUnidadMedida.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnidadMedida.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.UNIDADMEDIDA")); // NOI18N
        frmDatosGenerales.add(lblUnidadMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 157, 120, 20));

        cmbUnidadMedida.setModel(new UnidadMedidaComboBoxModel(controller.getUnidadesMedida()));
        frmDatosGenerales.add(cmbUnidadMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 156, 103, 23));

        lblProveedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProveedor.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PROVEEDOR")); // NOI18N
        frmDatosGenerales.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 157, 83, 20));

        cmbProveedor.setModel(new ProveedorComboBoxModel(controller.getProveedores()));
        frmDatosGenerales.add(cmbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 156, 284, 23));

        chkExento.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.EXENTO")); // NOI18N
        frmDatosGenerales.add(chkExento, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 185, 200, -1));

        pnlPrecios.setBorder(javax.swing.BorderFactory.createTitledBorder("Costo"));
        pnlPrecios.setPreferredSize(new java.awt.Dimension(635, 90));

        txtCostoPROM.setEditable(false);
        txtCostoPROM.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoPROM.setToolTipText("");
        txtCostoPROM.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoPROM.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoPROM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoPROM.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOPROM")); // NOI18N

        lblCostoUND.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoUND.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND")); // NOI18N

        txtCostoUND.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoUND.setToolTipText("");
        txtCostoUND.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoUND.setPreferredSize(new java.awt.Dimension(59, 30));

        txtCostoCIF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoCIF.setToolTipText("");
        txtCostoCIF.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoCIF.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoCIF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoCIF.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOCIF")); // NOI18N

        txtCostoFOB.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCostoFOB.setToolTipText("");
        txtCostoFOB.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoFOB.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoFOB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoFOB.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOFOB")); // NOI18N

        org.jdesktop.layout.GroupLayout pnlPreciosLayout = new org.jdesktop.layout.GroupLayout(pnlPrecios);
        pnlPrecios.setLayout(pnlPreciosLayout);
        pnlPreciosLayout.setHorizontalGroup(
            pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlPreciosLayout.createSequentialGroup()
                .add(25, 25, 25)
                .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, txtCostoFOB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .add(lblCostoFOB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblCostoCIF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(txtCostoCIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblCostoUND, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(txtCostoUND, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(txtCostoPROM, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblCostoPROM))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        pnlPreciosLayout.setVerticalGroup(
            pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlPreciosLayout.createSequentialGroup()
                .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(pnlPreciosLayout.createSequentialGroup()
                            .add(lblCostoCIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(txtCostoCIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(pnlPreciosLayout.createSequentialGroup()
                            .add(lblCostoUND, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(txtCostoUND, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(pnlPreciosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(pnlPreciosLayout.createSequentialGroup()
                            .add(lblCostoPROM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(txtCostoPROM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(pnlPreciosLayout.createSequentialGroup()
                            .add(lblCostoFOB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(txtCostoFOB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(1, 1, 1))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        frmDatosGenerales.add(pnlPrecios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 326, 635, 90));
        pnlPrecios.getAccessibleContext().setAccessibleName("Costo y Precio");

        cmbLinea.setModel(new LineaComboBoxModel(controller.getLineasProducto()));
        frmDatosGenerales.add(cmbLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 185, 263, 23));

        lblUnidadMedida1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnidadMedida1.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.LINEA")); // NOI18N
        frmDatosGenerales.add(lblUnidadMedida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 186, 120, 20));

        lblPaisOrigen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPaisOrigen.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PAISORIGEN")); // NOI18N
        frmDatosGenerales.add(lblPaisOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 120, 20));

        txtObservaciones.setColumns(20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setRows(5);
        txtObservaciones.setWrapStyleWord(true);
        spDireccion.setViewportView(txtObservaciones);

        frmDatosGenerales.add(spDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 214, 485, 50));

        chkCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COMPUESTO")); // NOI18N
        chkCompuesto.setRolloverEnabled(false);
        chkCompuesto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkCompuestoStateChanged(evt);
            }
        });
        frmDatosGenerales.add(chkCompuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        frmDatosGenerales.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 29, 23));

        lblMinimo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMinimo.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MINIMO")); // NOI18N
        frmDatosGenerales.add(lblMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 149, 20));

        txtMinimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMinimo.setToolTipText("");
        txtMinimo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtMinimo.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 144, 23));

        lblMaximo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaximo.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MAXIMO")); // NOI18N
        frmDatosGenerales.add(lblMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 152, 20));

        txtMaximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMaximo.setToolTipText("");
        txtMaximo.setMinimumSize(new java.awt.Dimension(6, 25));
        txtMaximo.setPreferredSize(new java.awt.Dimension(59, 30));
        frmDatosGenerales.add(txtMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 300, 133, 23));

        pnlPrecio.setBorder(javax.swing.BorderFactory.createTitledBorder("Precio"));

        lblPrecioEstandar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPrecioEstandar.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOESTANDAR")); // NOI18N

        txtPrecioEstandar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioEstandar.setToolTipText("");
        txtPrecioEstandar.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecioEstandar.setPreferredSize(new java.awt.Dimension(59, 30));

        lblDescuento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDescuento.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.DESCUENTO")); // NOI18N

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setToolTipText("");
        txtDescuento.setMinimumSize(new java.awt.Dimension(6, 25));
        txtDescuento.setPreferredSize(new java.awt.Dimension(59, 30));

        lblPorcentaje.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPorcentaje.setText("%");

        txtPrecioPromocion.setEditable(false);
        txtPrecioPromocion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioPromocion.setToolTipText("");
        txtPrecioPromocion.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecioPromocion.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoPROM2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoPROM2.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOPROMOCION")); // NOI18N

        org.jdesktop.layout.GroupLayout pnlPrecioLayout = new org.jdesktop.layout.GroupLayout(pnlPrecio);
        pnlPrecio.setLayout(pnlPrecioLayout);
        pnlPrecioLayout.setHorizontalGroup(
            pnlPrecioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlPrecioLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlPrecioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, txtPrecioPromocion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblCostoPROM2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblPrecioEstandar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, txtPrecioEstandar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblDescuento, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, txtDescuento, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblPorcentaje)
                .add(46, 46, 46))
        );
        pnlPrecioLayout.setVerticalGroup(
            pnlPrecioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlPrecioLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblPrecioEstandar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtPrecioEstandar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblDescuento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlPrecioLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtDescuento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblPorcentaje, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(lblCostoPROM2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtPrecioPromocion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        frmDatosGenerales.add(pnlPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 180, -1));
        frmDatosGenerales.add(pnlFotografia, new org.netbeans.lib.awtextra.AbsoluteConstraints(850,40,320,390));

        btnBuscarClasificador1.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarClasificador1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClasificadorActionPerformed(evt);
            }
        });
        frmDatosGenerales.add(btnBuscarClasificador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(616, 127, 29, 23));

        lblUnidadMedida3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnidadMedida3.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.OBSERVACIONES")); // NOI18N
        frmDatosGenerales.add(lblUnidadMedida3, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 214, 120, 20));

        cmbPaisOrigen.setModel(new PaisComboBoxModel(controller.getPaises()));
        frmDatosGenerales.add(cmbPaisOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 260, 23));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Administrar"));

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/delete.png")));
        btnEliminar.setText(bundle.getString("CONTAC.FORM.BTNELIMINAR")); // NOI18N
        btnEliminar.setActionCommand(bundle.getString("CONTAC.FORM.BTNELIMINAR")); // NOI18N
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRecodificar.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/actions/refactor.png")));
        btnRecodificar.setText(bundle.getString("CONTAC.FORM.BTNRECODIFICAR")); // NOI18N
        btnRecodificar.setActionCommand(bundle.getString("CONTAC.FORM.BTNRECODIFICAR")); // NOI18N
        btnRecodificar.setEnabled(false);
        btnRecodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecodificarActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnEliminar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .add(btnRecodificar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(btnEliminar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnRecodificar)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        frmDatosGenerales.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 300, 180, 115));

        pnlDatosGenerales.add(frmDatosGenerales, java.awt.BorderLayout.NORTH);

        pnlAcciones.setPreferredSize(new java.awt.Dimension(755, 75));

        btnAceptar.setText(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.setActionCommand(bundle.getString("CONTAC.FORM.BTNACEPTAR")); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlAccionesLayout = new org.jdesktop.layout.GroupLayout(pnlAcciones);
        pnlAcciones.setLayout(pnlAccionesLayout);
        pnlAccionesLayout.setHorizontalGroup(
            pnlAccionesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlAccionesLayout.createSequentialGroup()
                .add(267, 267, 267)
                .add(btnAceptar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancelar)
                .addContainerGap(511, Short.MAX_VALUE))
        );
        pnlAccionesLayout.setVerticalGroup(
            pnlAccionesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlAccionesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnAceptar)
                    .add(btnCancelar))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        pnlDatosGenerales.add(pnlAcciones, java.awt.BorderLayout.CENTER);

        productoTab.addTab("Datos Generales", pnlDatosGenerales);

        lblFotografia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFotografia.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAgregarImagen.setText("...");
        btnAgregarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarImagenActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlFotografiaLayout = new org.jdesktop.layout.GroupLayout(pnlFotografia);
        pnlFotografia.setLayout(pnlFotografiaLayout);
        pnlFotografiaLayout.setHorizontalGroup(
            pnlFotografiaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlFotografiaLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlFotografiaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btnAgregarImagen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblFotografia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(455, Short.MAX_VALUE))
        );
        pnlFotografiaLayout.setVerticalGroup(
            pnlFotografiaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlFotografiaLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblFotografia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 340, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnAgregarImagen)
                .addContainerGap(93, Short.MAX_VALUE))
        );

       // productoTab.addTab(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.TAB.FOTOGRAFIA"), pnlFotografia); // NOI18N

        pnlProductosCompuestos.setLayout(new java.awt.BorderLayout());

        pnlProductoCompuesto.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Producto Compuesto"));
        pnlProductoCompuesto.setPreferredSize(new java.awt.Dimension(500, 546));

        lblCodigoCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO")); // NOI18N

        txtCodigoCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoCompuesto.setToolTipText("");
        txtCodigoCompuesto.setEnabled(false);
        txtCodigoCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));
        txtCodigoCompuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoCompuestoKeyPressed(evt);
            }
        });

        btnBuscarProductoCompuesto.setIcon(new ImageIcon(getClass().getResource("/contac/resources/icons/folder_find.png")));
        btnBuscarProductoCompuesto.setEnabled(false);
        btnBuscarProductoCompuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoCompuestoActionPerformed(evt);
            }
        });

        lblNombreCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE")); // NOI18N

        txtNombreCompuesto.setEditable(false);
        txtNombreCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreCompuesto.setToolTipText("");
        txtNombreCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNombreCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCodigoFabricanteCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoFabricanteCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOFABRICANTE")); // NOI18N

        txtCodigoFabricanteCompuesto.setEditable(false);
        txtCodigoFabricanteCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoFabricanteCompuesto.setToolTipText("");
        txtCodigoFabricanteCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoFabricanteCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblNuevoPrecioEstandarCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNuevoPrecioEstandarCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOESTANDAR")); // NOI18N

        txtMarcaCompuesto.setEditable(false);
        txtMarcaCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMarcaCompuesto.setToolTipText("");
        txtMarcaCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtMarcaCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        txtCantidadCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCantidadCompuesto.setToolTipText("");
        txtCantidadCompuesto.setEnabled(false);
        txtCantidadCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCantidadCompuesto.setPreferredSize(new java.awt.Dimension(60, 30));

        lblCantidadCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidadCompuesto.setText(bundle.getString("CONTAC.FORM.ORDENENTRADA.CANTIDAD")); // NOI18N

        lblMarcaCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMarcaCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MARCA")); // NOI18N

        txtNuevoPrecioEstandarCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNuevoPrecioEstandarCompuesto.setToolTipText("");
        txtNuevoPrecioEstandarCompuesto.setEnabled(false);
        txtNuevoPrecioEstandarCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtNuevoPrecioEstandarCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCodigoCBSCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoCBSCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOCBS")); // NOI18N

        txtCodigoCBSCompuesto.setEditable(false);
        txtCodigoCBSCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoCBSCompuesto.setToolTipText("");
        txtCodigoCBSCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCodigoCBSCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblUnidadMedidaCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnidadMedidaCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.UNIDADMEDIDA")); // NOI18N

        txtUnidadMedidaCompuesto.setEditable(false);
        txtUnidadMedidaCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUnidadMedidaCompuesto.setToolTipText("");
        txtUnidadMedidaCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtUnidadMedidaCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblLineaProductoCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLineaProductoCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.LINEA")); // NOI18N

        txtLineaProductoCompuesto.setEditable(false);
        txtLineaProductoCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtLineaProductoCompuesto.setToolTipText("");
        txtLineaProductoCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtLineaProductoCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblProveedorCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProveedorCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PROVEEDOR")); // NOI18N

        txtProveedorCompuesto.setEditable(false);
        txtProveedorCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtProveedorCompuesto.setToolTipText("");
        txtProveedorCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtProveedorCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblPrecioEstandarCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrecioEstandarCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOESTANDAR")); // NOI18N

        txtPrecioEstandarCompuesto.setEditable(false);
        txtPrecioEstandarCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPrecioEstandarCompuesto.setToolTipText("");
        txtPrecioEstandarCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecioEstandarCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoPromCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCostoPromCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIOPROMOCION")); // NOI18N

        txtPrecioPromCompuesto.setEditable(false);
        txtPrecioPromCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPrecioPromCompuesto.setToolTipText("");
        txtPrecioPromCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtPrecioPromCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoUNDCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCostoUNDCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND")); // NOI18N

        txtCostoUNDCompuesto.setEditable(false);
        txtCostoUNDCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCostoUNDCompuesto.setToolTipText("");
        txtCostoUNDCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoUNDCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        lblCostoPROMCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCostoPROMCompuesto.setText(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOPROM")); // NOI18N

        txtCostoPROMCompuesto.setEditable(false);
        txtCostoPROMCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCostoPROMCompuesto.setToolTipText("");
        txtCostoPROMCompuesto.setMinimumSize(new java.awt.Dimension(6, 25));
        txtCostoPROMCompuesto.setPreferredSize(new java.awt.Dimension(59, 30));

        btnAgregarCompuesto.setText(bundle.getString("CONTAC.FORM.BTNAGREGAR")); // NOI18N
        btnAgregarCompuesto.setEnabled(false);
        btnAgregarCompuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCompuestoActionPerformed(evt);
            }
        });

        btnCancelarCompuesto.setText(bundle.getString("CONTAC.FORM.BTNCANCELAR")); // NOI18N
        btnCancelarCompuesto.setEnabled(false);
        btnCancelarCompuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCompuestoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlProductoCompuestoLayout = new org.jdesktop.layout.GroupLayout(pnlProductoCompuesto);
        pnlProductoCompuesto.setLayout(pnlProductoCompuestoLayout);
        pnlProductoCompuestoLayout.setHorizontalGroup(
            pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlProductoCompuestoLayout.createSequentialGroup()
                        .add(29, 29, 29)
                        .add(lblCantidadCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(txtCantidadCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblNuevoPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(txtNuevoPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(pnlProductoCompuestoLayout.createSequentialGroup()
                        .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                .add(lblCodigoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(10, 10, 10)
                                .add(txtCodigoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(7, 7, 7)
                                .add(btnBuscarProductoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                .add(lblNombreCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(txtNombreCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlProductoCompuestoLayout.createSequentialGroup()
                                .add(lblCodigoFabricanteCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(10, 10, 10)
                                .add(txtCodigoFabricanteCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(lblMarcaCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(txtMarcaCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                .add(lblCodigoCBSCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(txtCodigoCBSCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                .add(lblPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(txtPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(1, 1, 1)
                                .add(lblCostoPromCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(3, 3, 3)
                                .add(txtPrecioPromCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(lblCostoUNDCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, lblProveedorCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, lblUnidadMedidaCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                        .add(txtUnidadMedidaCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(lblLineaProductoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(txtLineaProductoCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                                    .add(txtProveedorCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .add(pnlProductoCompuestoLayout.createSequentialGroup()
                                        .add(txtCostoUNDCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(lblCostoPROMCompuesto)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(txtCostoPROMCompuesto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlProductoCompuestoLayout.createSequentialGroup()
                        .add(157, 157, 157)
                        .add(btnAgregarCompuesto)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnCancelarCompuesto)
                        .add(141, 141, 141))))
        );
        pnlProductoCompuestoLayout.setVerticalGroup(
            pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlProductoCompuestoLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlProductoCompuestoLayout.createSequentialGroup()
                        .add(2, 2, 2)
                        .add(lblCodigoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(pnlProductoCompuestoLayout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(txtCodigoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(btnBuscarProductoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblNombreCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtNombreCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlProductoCompuestoLayout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(lblCodigoFabricanteCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(txtCodigoFabricanteCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(txtMarcaCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(lblMarcaCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblCodigoCBSCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCodigoCBSCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblUnidadMedidaCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtUnidadMedidaCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblLineaProductoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtLineaProductoCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblProveedorCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtProveedorCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblCostoUNDCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCostoUNDCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCostoPROMCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblCostoPROMCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblCostoPromCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtPrecioPromCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblNuevoPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtNuevoPrecioEstandarCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblCantidadCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCantidadCompuesto, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(57, 57, 57)
                .add(pnlProductoCompuestoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnAgregarCompuesto)
                    .add(btnCancelarCompuesto))
                .addContainerGap(154, Short.MAX_VALUE))
        );

        pnlProductosCompuestos.add(pnlProductoCompuesto, java.awt.BorderLayout.EAST);

        pnlRegistrosProductosCompuestos.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Productos"));
        pnlRegistrosProductosCompuestos.setPreferredSize(new java.awt.Dimension(200, 546));
        pnlRegistrosProductosCompuestos.setLayout(new java.awt.BorderLayout());

        tblProductos.setEnabled(false);
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        pnlRegistrosProductosCompuestos.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlProductosCompuestos.add(pnlRegistrosProductosCompuestos, java.awt.BorderLayout.CENTER);

        productoTab.addTab(bundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.TAB.COMPUESTO"), pnlProductosCompuestos); // NOI18N

        add(productoTab, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed

        //--<Open Busqueda producto JDialog for selecting clasificador>
        Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

        //Setting codigo producto selected
        if (producto != null)
            txtCodigo.setText(producto.getCodigo());

        //Setting focus
        txtCodigo.requestFocusInWindow();
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        try {

            //Valida datos form
            validarDatosForm();

            //Registrar datos del producto
            controller.setCodigo(txtCodigo.getText());
            controller.setCompuesto(chkCompuesto.isSelected());
            controller.setNombre(txtNombre.getText().toUpperCase());
            controller.setCodigoFabricante(txtCodigoFabricante.getText().toUpperCase());
            controller.setMarca(txtMarca.getText().toUpperCase());
            controller.setModelo(txtModelo.getText().toUpperCase());
            controller.setAlias(txtAlias.getText().toUpperCase());
            controller.setCodigoCBS(Long.parseLong(txtCodigoCBS.getText()));
            controller.setUnidadMedida((UnidadMedida) ((UnidadMedidaComboBoxModel) cmbUnidadMedida.getModel()).getSelectedItem().getObject());
            controller.setProveedor((Proveedor) ((ProveedorComboBoxModel) cmbProveedor.getModel()).getSelectedItem().getObject());
            controller.setLinea((Linea) ((LineaComboBoxModel) cmbLinea.getModel()).getSelectedItem().getObject());
            controller.setPaisOrigen((Pais) ((PaisComboBoxModel) cmbPaisOrigen.getModel()).getSelectedItem().getObject());
            controller.setExento(chkExento.isSelected());
            controller.setObservaciones(txtObservaciones.getText().toUpperCase());
            controller.setMinimo(Integer.parseInt(txtMinimo.getText()));
            controller.setMaximo(Integer.parseInt(txtMaximo.getText()));
            controller.setCostoFOB(new BigDecimal(txtCostoFOB.getText()));
            controller.setCostoCIF(new BigDecimal(txtCostoCIF.getText()));
            controller.setCostoUND(new BigDecimal(txtCostoUND.getText()));
            controller.setPrecioEstandar(new BigDecimal(txtPrecioEstandar.getText()));
            controller.setDescuento(new BigDecimal(txtDescuento.getText()));

            //Setting image
            if (this.imageIcon != null) {
                Image image = imageIcon.getImage();
                controller.setFotografia(ImageUtils.convertoToByteArray(image));
            }

            //Registrar nuevo producto
            if (!controller.is_edit()) {

                //Registrar producto
                controller.crearProducto();

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.INGRESO.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.INGRESO.EXITOSO"));
            }

            if (controller.is_edit()) {

                //Modificar proveedor
                controller.modificarProducto();

                //Cambiar label boton aceptar
                btnAceptar.setText(messageBundle.getString("CONTAC.FORM.BTNACEPTAR"));

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MODIFICA.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MODIFICA.EXITOSO"));
            }

            //Init values formulario
            initValues();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Init formulario
        controller.init();
        //Init values formularui
        initValues();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarImagenActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.addChoosableFileFilter(new ImageFilter());
        jfc.setAcceptAllFileFilterUsed(true);
        jfc.setAccessory(new ImagePreview(jfc));

        int returnVal = jfc.showOpenDialog(this);

        //Obtaining a file selected
        File file;

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();

            //Load image into JLabel
            ImageIcon imageIcon = new ImageIcon(file.getPath());
            Image image = imageIcon.getImage().getScaledInstance(310, 340, Image.SCALE_DEFAULT);
            imageIcon.setImage(image);
            lblFotografia.setIcon(imageIcon);

            //Setting imageicon
            this.imageIcon = imageIcon;
        }
    }//GEN-LAST:event_btnAgregarImagenActionPerformed

    private void btnBuscarClasificadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClasificadorActionPerformed
        //Open clasificador JDialog for selecting clasificador
        Clasificador clasificador = null;
        if (!txtCodigoCBS.getText().equals("") && !txtDescripcionCBS.getText().equals("") && controller.getProducto() != null)
            clasificador = controller.getProducto().getClasificador();

        controller.setClasificador(new ClasificadorPnl(mdi, clasificador, true).getClasificadorSelected());

        //********************************************************
        //Getting the clasificador selected
        //********************************************************
        //<Evaluar que el tipo de clasificador seleccionado sea un ARTICULO>
        if (controller.getClasificador() != null) {
            if (controller.getClasificador().getTipoClasificador() != TiposClasificador.ARTICULO.getValue()) {

                //Show error message and rollback clasificador
                controller.setClasificador(null);
                txtCodigoCBS.setText("");
                txtDescripcionCBS.setText("");
                JOptionPane.showMessageDialog(this, new JErrorLabel("El clasificador debe ser un ARTICULO valido."));
            } else {
                //Show values in UI Form
                txtCodigoCBS.setText(String.valueOf(controller.getClasificador().getCbs()));
                txtDescripcionCBS.setText(controller.getClasificador().getDescripcion());
            }
        }
    }//GEN-LAST:event_btnBuscarClasificadorActionPerformed

    private void txtCodigoCBSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoCBSFocusLost

        if (!txtCodigoCBS.getText().equals("")) {
            controller.setCodigoCBS(Long.parseLong(txtCodigoCBS.getText()));

            try {

                //Buscar clasificador
                controller.buscarClasificador();
                txtCodigoCBS.setText(String.valueOf(controller.getClasificador().getCbs()));
                txtDescripcionCBS.setText(controller.getClasificador().getDescripcion());

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDACLASIFICADOR"),
                        e.getMessage());
            }
        }
    }//GEN-LAST:event_txtCodigoCBSFocusLost

    private void chkCompuestoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkCompuestoStateChanged

        if (chkCompuesto.isSelected()) {
            tblProductos.setEnabled(true); //Habilitar tabla de productos compuestos
            txtCodigoCompuesto.setEnabled(true); //Codigo compuesto
            txtNuevoPrecioEstandarCompuesto.setEnabled(true); //Nuevo precio estandar
            txtCantidadCompuesto.setEnabled(true); //Cantidad producto compuesto
            btnBuscarProductoCompuesto.setEnabled(true); //Boton busqueda de producto
            btnAgregarCompuesto.setEnabled(true); //Boton agregar producto compuesto
            btnCancelarCompuesto.setEnabled(true); //Boton cancelar producto compuesto

            txtCostoFOB.setEnabled(false);
            txtCostoCIF.setEnabled(false);
            txtCostoUND.setEnabled(false);
            txtPrecioEstandar.setEnabled(false);
            txtPrecioPromocion.setEnabled(false);
            txtCostoFOB.setText("0.00");
            txtCostoCIF.setText("0.00");
            txtCostoUND.setText("0.00");
            txtCostoPROM.setText("0.00");
            txtPrecioEstandar.setText("0.00");
        }

        if (!chkCompuesto.isSelected()) {
            tblProductos.setEnabled(false); //Habilitar tabla de productos compuestos
            txtCodigoCompuesto.setEnabled(false); //Codigo compuesto
            txtNuevoPrecioEstandarCompuesto.setEnabled(false); //Nuevo precio estandar
            txtCantidadCompuesto.setEnabled(false); //Cantidad producto compuesto
            btnBuscarProductoCompuesto.setEnabled(false); //Boton busqueda de producto
            btnAgregarCompuesto.setEnabled(false); //Boton agregar producto compuesto
            btnCancelarCompuesto.setEnabled(false); //Boton cancelar producto compuesto

            txtCostoFOB.setEnabled(true);
            txtCostoCIF.setEnabled(true);
            txtCostoUND.setEnabled(true);
            txtPrecioEstandar.setEnabled(true);
            txtPrecioPromocion.setEnabled(true);

            //Cargar datos originales
            cargarDatos();

        }

    }//GEN-LAST:event_chkCompuestoStateChanged

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        try {

            if (KeyEvent.VK_ENTER == evt.getKeyChar()) {

                if (!txtCodigo.getText().equals("")) {

                    //--<Setting codigo>
                    controller.setCodigo(txtCodigo.getText());

                    //--<Buscar producto>
                    if (!controller.buscarProducto())
                        cargarDatos();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDA"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void txtCodigoCompuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCompuestoKeyPressed

        try {

            if (KeyEvent.VK_ENTER == evt.getKeyChar()) {

                //Buscar producto por codigo
                Producto producto = controller.buscarProducto(txtCodigoCompuesto.getText());

                //Cargar datos producto compuesto
                cargarDatosProductoCompuesto(producto);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_txtCodigoCompuestoKeyPressed

    private void btnBuscarProductoCompuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoCompuestoActionPerformed

        try {

            //--<Open Busqueda producto JDialog for selecting clasificador>
            Producto producto = new pnlBusquedaProducto(mdi, true).getProductoSelected();

            //Setting codigo producto selected
            if (producto != null) {

                //Validar producto seleccionado
                if (producto.getCodigo().equalsIgnoreCase(txtCodigo.getText())) {
                    JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                            messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOCOMPUESTO.VALIDA"));
                } else {

                    //Cargando datos producto si no se encontro en el listado
                    cargarDatosProductoCompuesto(producto);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.BUSQUEDA"));
        }
    }//GEN-LAST:event_btnBuscarProductoCompuestoActionPerformed

    private void btnAgregarCompuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCompuestoActionPerformed

        try {

            //Validar datos formulario ingreso producto compuesto
            validarDatosFormCompuesto();

            //Agregar producto compuesto
            controller.agregarProductoCompuesto(productoCompuestoSelected, Double.parseDouble(txtNuevoPrecioEstandarCompuesto.getText()),
                    Integer.parseInt(txtCantidadCompuesto.getText()));

            //Actualizar table de productos compuestos
            ((BeanTableModel) tblProductos.getModel()).fireTableDataChanged();

            //Limpiar formulario de carga de producto
            limpiarDatosProductoCompuesto();

            //Request focus in window
            txtCodigoCompuesto.requestFocusInWindow();

            //Setting label default
            btnAgregarCompuesto.setText(messageBundle.getString("CONTAC.FORM.BTNAGREGAR"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnAgregarCompuestoActionPerformed

    private void btnCancelarCompuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCompuestoActionPerformed

        //Limpiar datos producto compuesto
        limpiarDatosProductoCompuesto();

        //Request focus
        txtCodigoCompuesto.requestFocusInWindow();

        //Setting default btnRegistrarCompuesto
        btnAgregarCompuesto.setText(messageBundle.getString("CONTAC.FORM.BTNAGREGAR"));

    }//GEN-LAST:event_btnCancelarCompuestoActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked

        try {

            //Getting producto selected
            int rowSelected = tblProductos.getSelectedRow();
            AdministraProductoController.ProductoCompuestoFacade productoCompuestoFacade = (AdministraProductoController.ProductoCompuestoFacade)
                    ((BeanTableModel) tblProductos.getModel()).getRow(rowSelected);

            //Cargar datos del producto para modificar
            cargarDatosProductoCompuesto(productoCompuestoFacade.getProductoCompuesto());

            //Actualizar label btnRegistrar
            btnAgregarCompuesto.setText(messageBundle.getString("CONTAC.FORM.BTNMODIFICAR"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {

            //Eliminar producto
            controller.eliminarProducto();

            //Show confirmation message
            JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ELIMINA.EXITOSO"),
                    messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ELIMINA.EXITOSO"));

            //Ejecutar cancelar action performed
            btnCancelarActionPerformed(evt);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRecodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecodificarActionPerformed

        try {

            //Solicitando nuevo codigo para recodificar
            String codigo = JOptionPane.showInputDialog(this, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.RECODIFICA"));

            //--<Buscar producto>
            if (codigo != null && !codigo.trim().equals("")) {
                controller.recodificarProducto(codigo);

                //Show confirmation message
                JOptionErrorPane.showMessageInfo(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.RECODIFICA.EXITOSO"),
                        messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.RECODIFICA.EXITOSO"));

                //Ejecutar cancelar action performed
                btnCancelarActionPerformed(evt);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.ERROR.REGISTRO"),
                    e.getMessage());
        }

    }//GEN-LAST:event_btnRecodificarActionPerformed

    //--<Carga datos producto compuesto>
    private void cargarDatosProductoCompuesto(Producto producto) {

        //Setting producto compuesto
        this.productoCompuestoSelected = producto;

        //Cargando datos producto
        txtCodigoCompuesto.setText(producto.getCodigo());
        txtNombreCompuesto.setText(producto.getNombre());
        txtCodigoFabricanteCompuesto.setText(producto.getCodigoFabricante());
        txtMarcaCompuesto.setText(producto.getMarca());
        txtCodigoCBSCompuesto.setText(String.valueOf(producto.getCodigoCbs()));
        txtUnidadMedidaCompuesto.setText(producto.getUnidadMedida().getNombre());
        txtLineaProductoCompuesto.setText(producto.getLinea().getNombre());
        txtProveedorCompuesto.setText(producto.getProveedor().getRazonSocial());
        txtCostoUNDCompuesto.setText(TextUtil.formatCurrency(producto.getCostoUND().doubleValue()));
        txtCostoPROMCompuesto.setText(TextUtil.formatCurrency(producto.getCostoPROM().doubleValue()));
        txtPrecioEstandarCompuesto.setText(TextUtil.formatCurrency(producto.getPrecioESTANDAR().doubleValue()));
        if (producto.getPrecioPROMOCION() != null)
            txtPrecioPromCompuesto.setText(TextUtil.formatCurrency(producto.getPrecioPROMOCION().doubleValue()));

        //Request focus
        txtCantidadCompuesto.requestFocusInWindow();
        txtCantidadCompuesto.selectAll();
    }

    //--<Carga datos producto compuesto seleccionado>
    private void cargarDatosProductoCompuesto(ProductoCompuesto producto) {

        //Setting producto compuesto
        this.productoCompuestoSelected = producto.getProducto();

        //Cargando datos producto
        txtCodigoCompuesto.setText(productoCompuestoSelected.getCodigo());
        txtNombreCompuesto.setText(productoCompuestoSelected.getNombre());
        txtCodigoFabricanteCompuesto.setText(productoCompuestoSelected.getCodigoFabricante());
        txtMarcaCompuesto.setText(productoCompuestoSelected.getMarca());
        txtCodigoCBSCompuesto.setText(String.valueOf(productoCompuestoSelected.getCodigoCbs()));
        txtUnidadMedidaCompuesto.setText(productoCompuestoSelected.getUnidadMedida().getNombre());
        txtLineaProductoCompuesto.setText(productoCompuestoSelected.getLinea().getNombre());
        txtProveedorCompuesto.setText(productoCompuestoSelected.getProveedor().getRazonSocial());
        txtCostoUNDCompuesto.setText(TextUtil.formatCurrency(productoCompuestoSelected.getCostoUND().doubleValue()));
        txtCostoPROMCompuesto.setText(TextUtil.formatCurrency(productoCompuestoSelected.getCostoPROM().doubleValue()));
        txtPrecioEstandarCompuesto.setText(TextUtil.formatCurrency(productoCompuestoSelected.getPrecioESTANDAR().doubleValue()));
        if (productoCompuestoSelected.getPrecioPROMOCION() != null)
            txtPrecioPromCompuesto.setText(TextUtil.formatCurrency(productoCompuestoSelected.getPrecioPROMOCION().doubleValue()));
        txtNuevoPrecioEstandarCompuesto.setText(TextUtil.formatCurrency(producto.getPrecio().doubleValue()));
        txtCantidadCompuesto.setText(String.valueOf(producto.getCantidad()));
    }

    //--<Limpiar datos producto compuesto>
    private void limpiarDatosProductoCompuesto() {

        //Limpiar datos de producto
        txtCodigoCompuesto.setText("");
        txtNombreCompuesto.setText("");
        txtCodigoFabricanteCompuesto.setText("");
        txtMarcaCompuesto.setText("");
        txtCodigoCBSCompuesto.setText("");
        txtUnidadMedidaCompuesto.setText("");
        txtLineaProductoCompuesto.setText("");
        txtProveedorCompuesto.setText("");
        txtCostoUNDCompuesto.setText("");
        txtCostoPROMCompuesto.setText("");
        txtPrecioEstandarCompuesto.setText("");
        txtPrecioPromocion.setText("");
        txtNuevoPrecioEstandarCompuesto.setText("");
        txtCantidadCompuesto.setText("");
    }

    //--<Valida datos del formulario producto compuesto>
    private void validarDatosFormCompuesto() throws Exception {

        //Precio Estandar
        if (txtNuevoPrecioEstandarCompuesto.getText().equals("")) {
            //Request focus
            txtPrecioEstandarCompuesto.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PRECIO.VALIDA"));
        }

        //Cantidad
        if (txtCantidadCompuesto.getText().equals("")) {
            //Request focus
            txtCantidadCompuesto.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.INGRESO.EXITOSO"));
        }
    }

    //--<Valida datos del formulario>
    private void validarDatosForm() throws Exception {

        //Codigo
        if (txtCodigo.getText().equals("")) {
            //Request focus
            txtCodigo.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGO.VALIDA"));
        }

        //Nombre
        if (txtNombre.getText().equals("")) {
            //Request focus
            txtNombre.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.NOMBRE.VALIDA"));
        }

        //Codigo fabricante
        if (txtCodigoFabricante.getText().equals("")) {
            //Request focus
            txtCodigoFabricante.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOFABRICANTE.VALIDA"));
        }

        //Codigo CBS
        if (txtCodigoCBS.getText().equals("")) {
            //Request focus
            txtCodigoCBS.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOCBS.VALIDA"));
        }

        //Descripcion CBS
        if (txtDescripcionCBS.getText().equals("")) {
            //Request focus
            txtDescripcionCBS.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.CODIGOCBS.VALIDA"));
        }

        //Unidad de medida
        if (cmbUnidadMedida.getSelectedIndex() == -1) {
            //Request focus
            cmbUnidadMedida.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.UNIDADMEDIDA.VALIDA"));
        }

        //Proveedor
        if (cmbProveedor.getSelectedIndex() == -1) {
            //Request focus
            cmbProveedor.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.PROVEEDOR.VALIDA"));
        }

        //Linea producto
        if (cmbLinea.getSelectedItem() == null) {
            //Request focus
            cmbLinea.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.LINEA.VALIDA"));
        }

        //Cantidad minima existencia
        if (txtMinimo.getText().equals("")) {
            //Request focus
            txtMinimo.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MINIMO.VALIDA"));
        }

        //Cantidad maxima existencia
        if (txtMaximo.getText().equals("")) {
            //Request focus
            txtMaximo.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.MAXIMO.VALIDA"));
        }

        //Costo FOB
        if (txtCostoFOB.getText().equals("")) {
            //Request focus
            txtCostoFOB.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOFOB.VALIDA"));
        }

        //Costo CIF
        if (txtCostoCIF.getText().equals("")) {
            //Request focus
            txtCostoCIF.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOCIF.VALIDA"));
        }

        //Costo UND
        if (txtCostoUND.getText().equals("")) {
            //Request focus
            txtCostoUND.requestFocusInWindow();

            //Throw error message
            throw new Exception(messageBundle.getString("CONTAC.FORM.ADMINISTRAPRODUCTO.COSTOUND.VALIDA"));
        }

        //Descuento
        if (txtDescuento.getText().equals("")) {
            //Setting default value to zero
            txtDescuento.setText("0.00");
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarCompuesto;
    private javax.swing.JButton btnAgregarImagen;
    private javax.swing.JButton btnBuscarClasificador1;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnBuscarProductoCompuesto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarCompuesto;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRecodificar;
    private javax.swing.JCheckBox chkCompuesto;
    private javax.swing.JCheckBox chkExento;
    private javax.swing.JComboBox cmbLinea;
    private javax.swing.JComboBox cmbPaisOrigen;
    private javax.swing.JComboBox cmbProveedor;
    private javax.swing.JComboBox cmbUnidadMedida;
    private javax.swing.JPanel frmDatosGenerales;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAlias;
    private javax.swing.JLabel lblCantidadCompuesto;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoCBS;
    private javax.swing.JLabel lblCodigoCBSCompuesto;
    private javax.swing.JLabel lblCodigoCompuesto;
    private javax.swing.JLabel lblCodigoFabricante;
    private javax.swing.JLabel lblCodigoFabricanteCompuesto;
    private javax.swing.JLabel lblCostoCIF;
    private javax.swing.JLabel lblCostoFOB;
    private javax.swing.JLabel lblCostoPROM;
    private javax.swing.JLabel lblCostoPROM2;
    private javax.swing.JLabel lblCostoPROMCompuesto;
    private javax.swing.JLabel lblCostoPromCompuesto;
    private javax.swing.JLabel lblCostoUND;
    private javax.swing.JLabel lblCostoUNDCompuesto;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblFotografia;
    private javax.swing.JLabel lblLineaProductoCompuesto;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMarcaCompuesto;
    private javax.swing.JLabel lblMaximo;
    private javax.swing.JLabel lblMinimo;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreCompuesto;
    private javax.swing.JLabel lblNuevoPrecioEstandarCompuesto;
    private javax.swing.JLabel lblPaisOrigen;
    private javax.swing.JLabel lblPorcentaje;
    private javax.swing.JLabel lblPrecioEstandar;
    private javax.swing.JLabel lblPrecioEstandarCompuesto;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblProveedorCompuesto;
    private javax.swing.JLabel lblUnidadMedida;
    private javax.swing.JLabel lblUnidadMedida1;
    private javax.swing.JLabel lblUnidadMedida3;
    private javax.swing.JLabel lblUnidadMedidaCompuesto;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlDatosGenerales;
    private javax.swing.JPanel pnlFotografia;
    private javax.swing.JPanel pnlPrecio;
    private javax.swing.JPanel pnlPrecios;
    private javax.swing.JPanel pnlProductoCompuesto;
    private javax.swing.JPanel pnlProductosCompuestos;
    private javax.swing.JPanel pnlRegistrosProductosCompuestos;
    private javax.swing.JTabbedPane productoTab;
    private javax.swing.JScrollPane spDireccion;
    private org.jdesktop.swingx.JXTable tblProductos;
    private javax.swing.JTextField txtAlias;
    private javax.swing.JTextField txtCantidadCompuesto;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCBS;
    private javax.swing.JTextField txtCodigoCBSCompuesto;
    private javax.swing.JTextField txtCodigoCompuesto;
    private javax.swing.JTextField txtCodigoFabricante;
    private javax.swing.JTextField txtCodigoFabricanteCompuesto;
    private javax.swing.JTextField txtCostoCIF;
    private javax.swing.JTextField txtCostoFOB;
    private javax.swing.JTextField txtCostoPROM;
    private javax.swing.JTextField txtCostoPROMCompuesto;
    private javax.swing.JTextField txtCostoUND;
    private javax.swing.JTextField txtCostoUNDCompuesto;
    private javax.swing.JTextField txtDescripcionCBS;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtLineaProductoCompuesto;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtMarcaCompuesto;
    private javax.swing.JTextField txtMaximo;
    private javax.swing.JTextField txtMinimo;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCompuesto;
    private javax.swing.JTextField txtNuevoPrecioEstandarCompuesto;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtPrecioEstandar;
    private javax.swing.JTextField txtPrecioEstandarCompuesto;
    private javax.swing.JTextField txtPrecioPromCompuesto;
    private javax.swing.JTextField txtPrecioPromocion;
    private javax.swing.JTextField txtProveedorCompuesto;
    private javax.swing.JTextField txtUnidadMedidaCompuesto;
    // End of variables declaration//GEN-END:variables

    //Begin variables execution
    private BeanTableModel<AdministraProductoController.ProductoCompuestoFacade> productoBeanTableModel;
    private ImageIcon imageIcon;
    private Producto productoCompuestoSelected;

}
