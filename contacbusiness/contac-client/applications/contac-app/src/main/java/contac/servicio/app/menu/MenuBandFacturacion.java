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
 * Banda de menu para administrar datos de facturacion
 * User: EMontenegro
 * Date: 09-09-12
 * Time: 12:35 PM
 */
public class MenuBandFacturacion extends MenuBand {

    /**
     * MDI Style
     */
    private StyleGenericFrame style;

    /**
     * Default constructor
     * @param style, StyleGenericFrame
     */
    public MenuBandFacturacion(StyleGenericFrame style) {

        //Call super constructor
        super(LabelMessage.getInstance().getLabel("CONTAC.MENU.FACTURACION"), new document_new(), new ExpandActionListener());

        //This MDI Style
        this.style = style;

        //Setting resizeble policies
        setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));

        //Call Init task menu ribbon band
        initRibbonBand();

    }

    @Override
    public void initRibbonBand() {
        
        //NUEVA FACTURA
        JCommandButton mnuNuevaFactura = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.FACTURACION.NUEVAFACTURA"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/images/factura_clientes.png"), new Dimension(32, 32)));

        mnuNuevaFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("FacturaClientes", "contac.facturacion.app.pnlFacturaCliente");
            }
        });

        //REGISTRO FACTURAS
        JCommandButton mnuRegistroFacturas = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.FACTURACION.REGISTROFACTURAS"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/registro_facturas.png"), new Dimension(32, 32)));

        mnuRegistroFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("FacturaClientes", "contac.facturacion.app.pnlRegistroFacturas");
            }
        });

        //REGISTRO DE PROFORMAS

        JCommandButton mnuRegistroProforma = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.PROFORMA.REGISTROPROFORMAS"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/folder-customer-icon.png"), new Dimension(32, 32)));

        mnuRegistroProforma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("proformaClientes", "contac.facturacion.app.pnlRegistroProformas");
            }
        });

        //NUEVA PROFORMA
        JCommandButton mnuRegistroProformas = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.FACTURACION.PROFORMAS"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/proforma.png"), new Dimension(32, 32)));


        mnuRegistroProformas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("proformaClientes", "contac.facturacion.app.pnlProformaCliente");
            }
        });

        //DEVOLUCIONES
        JCommandButton mnuDevoluciones = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.FACTURACION.DEVOLUCIONES"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/images/payment_back_256x32.png"), new Dimension(32, 32)));
        
        mnuDevoluciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("Devoluciones", "contac.facturacion.app.pnlFacturaCliente");
            }
        });
        
        //AGENTES DE VENTAS
        JCommandButton mnuAgentesVentes = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.FACTURACION.AGENTEVENTAS"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/icons/salesman_refresh_256x32.png"), new Dimension(32, 32)));

        mnuAgentesVentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                style.addPanel("AgentesVentas", "contac.facturacion.app.pnlAgenteVentas");
            }
        }) ;
        
        //Start grouping
        startGroup();

        //Add menu position
        addCommandButton(mnuNuevaFactura, RibbonElementPriority.TOP);
        addCommandButton(mnuRegistroFacturas, RibbonElementPriority.TOP);
        addCommandButton(mnuRegistroProformas, RibbonElementPriority.TOP);
        addCommandButton(mnuRegistroProforma, RibbonElementPriority.TOP);
        addCommandButton(mnuDevoluciones, RibbonElementPriority.TOP);
        addCommandButton(mnuAgentesVentes, RibbonElementPriority.TOP);

        //Call resize policies
        resizePolicies();
    }
}
