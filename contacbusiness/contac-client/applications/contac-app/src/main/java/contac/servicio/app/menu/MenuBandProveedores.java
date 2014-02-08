package contac.servicio.app.menu;

import contac.commons.form.events.ExpandActionListener;
import contac.commons.form.menu.MenuBand;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.common.JCommandButton;
import org.pushingpixels.flamingo.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import test.svg.transcoded.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Banda de menu para administrar datos de los proveedores
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 10:45:20 PM
 */
public class MenuBandProveedores extends MenuBand {

    /**
     * MDI Style
     */
    private StyleGenericFrame style;

    /**
     * Default constructor
     */
    public MenuBandProveedores(StyleGenericFrame style) {

        //Call super constructor
        super(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES"), new document_new(), new ExpandActionListener());

        //Init MDI Style
        this.style = style;

        //Setting resizeble policies
        setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));

        //Call Init task menu ribbon band
        initRibbonBand();
    }

    @Override
    public void initRibbonBand() {

        //MENU REGISTRO DE PROVEEDORES
        JCommandButton menuRegistroProveedores = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.REGISTRO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/proveedores_256x48.png"), new Dimension(32, 32)));
        menuRegistroProveedores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                style.addPanel("AdministraProveedores", "contac.administracion.proveedor.app.pnlAdmonProveedor");
            }
        });

        //MENU ORDENES DE COMPRA
        JCommandButton menuOrdenCompra = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.ORDENCOMPRA"),
                //getResizableIconFromResource("contac/servicio/app/images/salesman_refresh_256x32.png", new Dimension(32, 32)));
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/salesman_refresh_256x32.png"), new Dimension(32, 32)));

        menuOrdenCompra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                style.addPanel("ordenCompra", "contac.administracion.proveedor.app.pnlOrdenCompra");
            }
        });

        //MENU REGISTRO ORDENES DE COMPRA
        JCommandButton mnuRegistroOrdenCompra = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.REGISTROORDENCOMPRA"),
                //getResizableIconFromResource("contac/servicio/app/images/salesman_refresh_256x32.png", new Dimension(32, 32)));
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/registroOrdenCompra.png"), new Dimension(32, 32)));

        mnuRegistroOrdenCompra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                style.addPanel("ordenCompra", "contac.administracion.proveedor.app.pnlOrdenCompra");
            }
        });

        //MENU RECEPCION DE PRODUCTOS
        JCommandButton menuRecepcionProductos = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.RECEPCIONPRODUCTOS"),
                //getResizableIconFromResource("contac/servicio/app/images/salesman_refresh_256x32.png", new Dimension(32, 32)));
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/reciboProducto.png"), new Dimension(32, 32)));

        menuRecepcionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });

        //MENU BUSQUEDA DE PROVEEDORES
        JCommandButton menuBusquedaProveedores = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.BUSQUEDA"),
                new document_new());
        menuBusquedaProveedores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });


        //MENU PROVEEDORES ACTIVOS
        JCommandButton menuProveedoresActivos = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.ACTIVOS"),
                new appointment_new());
        menuProveedoresActivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });


        //MENU PROVEEDORES INACTIVOS
        JCommandButton menuProveedoresInactivos = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.INACTIVOS"),
                new bookmark_new());
        menuProveedoresInactivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });

        //MENU PROVEEDORES CUENTAS X PAGAR
        JCommandButton menuCuentasxPagar = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROVEEDORES.CUENTASXPAGAR"),
                new contact_new());
        menuCuentasxPagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });

        //Start grouping
        startGroup();

        //Add menu to position
        this.addCommandButton(menuRegistroProveedores, RibbonElementPriority.TOP);
        this.addCommandButton(menuBusquedaProveedores, RibbonElementPriority.TOP);
        //this.addCommandButton(menuRequisicion, RibbonElementPriority.TOP);
        this.addCommandButton(menuOrdenCompra, RibbonElementPriority.TOP);
        this.addCommandButton(mnuRegistroOrdenCompra, RibbonElementPriority.TOP);
        this.addCommandButton(menuRecepcionProductos, RibbonElementPriority.TOP);
        this.addCommandButton(menuProveedoresActivos, RibbonElementPriority.TOP);
        this.addCommandButton(menuProveedoresInactivos, RibbonElementPriority.TOP);
        this.addCommandButton(menuCuentasxPagar, RibbonElementPriority.TOP);

        //Call resize policies
        resizePolicies();
    }
}
