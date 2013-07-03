package contac.servicio.app.menu;

import contac.commons.form.menu.MenuModule;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.ribbon.RibbonTask;

import java.awt.*;

/**
 * Ribbon Menu de Facturacion
 *
 * Extiende de un MenuModule, se deben de programar primeramente las
 * tareas a ejecutarse dentro de este ribbon menu.
 *
 * @see org.pushingpixels.flamingo.ribbon.RibbonContextualTaskGroup
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 10:20:36 PM
 */
public class MenuFacturacion extends MenuModule {

    /**
     * Default constructor
     * @param style, StyleGenericFrame
     */
    public MenuFacturacion(StyleGenericFrame style){

        //Instantiation menu facturacion
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.FACTURACION"), Color.BLUE,
                new RibbonTask(LabelMessage.getInstance().getLabel("CONTAC.MENU.FACTURACION"), new MenuBandFacturacion(style)));
    }
}
