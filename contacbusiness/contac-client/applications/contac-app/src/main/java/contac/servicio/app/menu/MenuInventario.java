package contac.servicio.app.menu;

import contac.commons.form.menu.MenuModule;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.ribbon.RibbonTask;

import java.awt.*;

/**
 * Ribbon Menu de Inventario
 *
 * Extiende de un MenuModule, se deben de programar primeramente las
 * tareas a ejecutarse dentro de este ribbon menu.
 *
 * @see org.pushingpixels.flamingo.ribbon.RibbonContextualTaskGroup
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 10:23:58 PM
 */
public class MenuInventario extends MenuModule {

    /**
     * Default Constructor 
     * @param style, StyleGenericFrame
     */
    public MenuInventario(StyleGenericFrame style) {

        //Instantiation menu facturacion
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.INVENTARIO"), Color.RED,
                new RibbonTask(LabelMessage.getInstance().getLabel("CONTAC.MENU.INVENTARIO"), new MenuBandInventario(style)));
    }
}
