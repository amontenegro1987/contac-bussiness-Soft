/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.menu;

import contac.commons.form.menu.MenuModule;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.ribbon.RibbonTask;

import java.awt.*;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-08-12
 * Time: 10:58 PM
 */
public class MenuClientes extends MenuModule {

    /**
     * Constructor
     * @param style, Style Frame panel
     */
    public MenuClientes(StyleGenericFrame style) {

        //Instantiation customers menu
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.CLIENTES"), Color.ORANGE,
                new RibbonTask(LabelMessage.getInstance().getLabel("CONTAC.MENU.CLIENTES"), new MenuBandClientes(style)));
    }
}
