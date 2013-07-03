package contac.servicio.app.menu;

import contac.commons.form.events.ExpandActionListener;
import contac.commons.form.menu.MenuBand;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.common.JCommandButton;
import org.pushingpixels.flamingo.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import test.svg.transcoded.applications_office;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Banda de menu para administrar datos de los proveedores
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-17-2010
 * Time: 11:21:03 PM
 */
public class MenuBandInventario extends MenuBand {

    /**
     * MDI Style
     */
    private StyleGenericFrame style;


    /**
     * Default Constructor
     */
    public MenuBandInventario(StyleGenericFrame style) {

        //Call super constructor
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.INVENTARIO"), new applications_office(), new ExpandActionListener());

        //This MDI Style
        this.style = style;

        //Setting resizeble policies
        setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));

        //Call Init task menu ribbon band
        initRibbonBand();
    }

    @Override
    public void initRibbonBand() {

        //CATALOGO DE PRODUCTOS
        JCommandButton mnuCatalogoProducto = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.INVENTARIO.CATALOGOPRODUCTO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/images/avalaibity_256x32.png"), new Dimension(32, 32)));

        mnuCatalogoProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("AdministraCatalogoProductos", "contac.inventarios.producto.app.pnlAdmonProducto");
            }
        });

        //ORDEN ENTRADA INVENTARIO
        JCommandButton mnuEntradaInventario = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.INVENTARIO.ORDENENTRADA"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/orden_entrada.png"), new Dimension(32, 32)));

        mnuEntradaInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("OrdenEntradaAlmacen", "contac.inventarios.movimiento.inventario.app.pnlRegistroOrdenesEntradaInventario");
            }
        });

        //ORDEN TRASLADO INVENTARIO
        JCommandButton mnuTrasladoInventario = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.INVENTARIO.ORDENTRASLADO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/orden_traslado.png"), new Dimension(32, 32)));

        mnuTrasladoInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("OrdenTrasladoAlmacen", "contac.inventarios.movimiento.inventario.app.pnlRegistroOrdenesTrasladoInventario");
            }
        });

        //ORDEN LEVANTAMIENTO INVENTARIO
        JCommandButton mnuLevantamientoInventarioFisico = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.INVENTARIO.LEVANTAMIENTOINVENTARIOFISICO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/levantamiento_inventario.png"), new Dimension(32, 32)));

        mnuLevantamientoInventarioFisico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("OrdenLevantamientoFisico", "contac.inventarios.movimiento.inventario.app.pnlRegistroLevantamientoFisico");
            }
        });

        //ORDEN BAJA INVENTARIO
        JCommandButton mnuBajaInventario = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.INVENTARIO.ORDENBAJA"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/orden_baja.png"), new Dimension(32, 32)));

        mnuBajaInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("OrdenBajaAlmacen", "contac.inventarios.movimiento.inventario.app.pnlRegistroOrdenesSalidaInventario");
            }
        });

        //MOVIMIENTO INVENTARIO
        JCommandButton mnuMovimientoInventario = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.INVENTARIO.MOVIMIENTOINVENTARIO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/movimiento_inventario.png"), new Dimension(32, 32)));
        
        mnuMovimientoInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("MovimientoInventario", "contac.inventarios.movimiento.inventario.app.pnlMovimientoInventario");
            }
        });

        //Start grouping
        startGroup();

        //Add menu position
        addCommandButton(mnuCatalogoProducto, RibbonElementPriority.TOP);
        addCommandButton(mnuEntradaInventario, RibbonElementPriority.TOP);
        addCommandButton(mnuTrasladoInventario, RibbonElementPriority.TOP);
        addCommandButton(mnuBajaInventario, RibbonElementPriority.TOP);
        addCommandButton(mnuLevantamientoInventarioFisico, RibbonElementPriority.TOP);
        addCommandButton(mnuMovimientoInventario, RibbonElementPriority.TOP);

        //Call resize policies
        resizePolicies();

    }

}