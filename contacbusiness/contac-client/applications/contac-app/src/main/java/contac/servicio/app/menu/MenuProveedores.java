/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.menu;

import contac.commons.form.menu.MenuModule;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.ribbon.RibbonTask;

import java.awt.*;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emontenegro
 * Date: 8/10/11
 * Time: 10:46 PM
 */
public class MenuProveedores extends MenuModule {

    /**
     * Default Constructor
     *
     * @param style, StyleGenericFrame
     */
    public MenuProveedores(StyleGenericFrame style) {

        //Instantiation menu proveedores
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.PROVEEDORES"), Color.GRAY,
                new RibbonTask(LabelMessage.getInstance().getLabel("CONTAC.MENU.PROVEEDORES"), new MenuBandProveedores(style)));
    }
}
