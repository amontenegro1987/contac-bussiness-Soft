/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.app.menu;

import contac.commons.form.events.ExpandActionListener;
import contac.commons.form.menu.MenuBand;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.common.JCommandButton;
import org.pushingpixels.flamingo.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import test.svg.transcoded.document_new;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Contac Business Software. All rights reserved 2012
 * User: EMontenegro
 * Date: 02-08-12
 * Time: 10:57 PM
 */
public class MenuBandClientes extends MenuBand {

    /**
     * MDI Style
     */
    private StyleGenericFrame style;

    /**
     * Constructor
     *
     * @param style, Style generic frame
     */
    public MenuBandClientes(StyleGenericFrame style) {

        //Call super constructor
        super(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.CLIENTES"), new document_new(), new ExpandActionListener());

        //Init MDI Style
        this.style = style;

        //Setting resizeble policies
        setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));

        //Call Init task menu ribbon band
        initRibbonBand();

    }

    @Override
    public void initRibbonBand() {

        //MENU REGISTRO DE CLIENTES
         JCommandButton menuRegistroProveedores = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.CLIENTES.REGISTRO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/folder-customer-icon.png"), new Dimension(32, 32)));
        menuRegistroProveedores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                style.addPanel("Administra Clientes", "contac.administracion.cliente.app.pnlAdmonCliente");
            }
        });

        //Star grouping
        startGroup();

        //Add menu to position
        this.addCommandButton(menuRegistroProveedores, RibbonElementPriority.TOP);

        //Call resize policies
        resizePolicies();
    }
}
