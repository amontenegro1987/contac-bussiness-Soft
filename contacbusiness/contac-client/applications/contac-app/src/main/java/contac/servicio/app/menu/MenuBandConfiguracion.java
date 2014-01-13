package contac.servicio.app.menu;

import contac.commons.form.events.ExpandActionListener;
import contac.commons.form.menu.MenuBand;
import contac.commons.form.panel.StyleGenericFrame;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.common.JCommandButton;
import org.pushingpixels.flamingo.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.ribbon.RibbonElementPriority;
import test.svg.transcoded.applications_office;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-20-2010
 * Time: 03:37:44 PM
 */
public class MenuBandConfiguracion extends MenuBand {

    /**
     * MDI Style
     */
    private StyleGenericFrame style;

    /**
     * Default Constructor
     *
     * @param style, StyleGenericFrame
     */
    public MenuBandConfiguracion(StyleGenericFrame style) {

        //Call super constructor
        super("", new applications_office(), new ExpandActionListener());

        //Init MDI Style
        this.style = style;

        //Setting resizeble policies
        //setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));

        //Call Init task menu ribbon band
        initRibbonBand();
    }

    @Override
    public void initRibbonBand() {

        //**********************************************************
        //Grouping compania configuracion
        //**********************************************************

        //MENU DATOS EMPRESA
        JCommandButton menuCompania = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.SEGURIDAD.COMPANIA"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/images/bank_256x48.png"), new Dimension(32, 32)));

        menuCompania.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                style.addPanel("AdministraCompania", "contac.administracion.compania.app.pnlRegistroCompanias");
            }
        });

        //Add menu to position
        this.addCommandButton(menuCompania, RibbonElementPriority.TOP);

        //MENU ADMINISTRACION DE USUARIOS
        JCommandButton menuUsuarios = new JCommandButton(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.SEGURIDAD.USUARIO"),
                ImageWrapperResizableIcon.getIcon(getClass().getResource("/contac/resources/images/user_lock_256x48.png"), new Dimension(32, 32)));

        menuUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                style.addPanel("AdministraUsuarios", "contac.administracion.usuario.app.pnlAdmonUsuario");
         }
        });

        //Add menu to position
        this.addCommandButton(menuUsuarios, RibbonElementPriority.TOP);

        //Call resize policies
        resizePolicies();

    }
}
