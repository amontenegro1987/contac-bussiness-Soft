package contac.servicio.app.menu;

import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.ribbon.RibbonTask;

/**
 * Ribbon Menu Administracion
 *
 * Extiende de un MenuModule, se deben de programar primeramente las
 * tareas a ejecutarse dentro de este ribbon menu.
 *
 * @see org.pushingpixels.flamingo.ribbon.RibbonTask
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 10:26:12 PM
 */
public class MenuAdministracion extends RibbonTask {

    /**
     * Default constructor
     * @param style, StyleGenericFrame
     */
    public MenuAdministracion(StyleGenericFrame style) {

        //Instantiation menu facturacion
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.ADMINISTRACION"), new MenuBandConfiguracion(style));
    }
}
