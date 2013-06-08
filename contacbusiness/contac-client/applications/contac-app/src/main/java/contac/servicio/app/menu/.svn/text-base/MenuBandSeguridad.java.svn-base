package contac.servicio.app.menu;

import contac.commons.form.events.ExpandActionListener;
import contac.commons.form.menu.MenuBand;
import contac.servicio.app.message.LabelMessage;
import org.pushingpixels.flamingo.common.JCommandButton;
import org.pushingpixels.flamingo.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import test.svg.transcoded.applications_office;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Banda de menu para administrar datos de seguridad
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-19-2010
 * Time: 10:50:58 PM
 */
public class MenuBandSeguridad extends MenuBand {

    /**
     * Default Constructor
     */
    public MenuBandSeguridad() {

        //Call super constructor
        super(LabelMessage.getInstance().getLabel("CONTAC.SUBMENU.SEGURIDAD"), new applications_office(), new ExpandActionListener());

        //Setting resizeble policies
        setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));

        //Call Init task menu ribbon band
        initRibbonBand();

    }

    @Override
    public void initRibbonBand() {

        //MENU ADMINISTRAR USUARIOS
        JCommandButton menuUsuarios = new JCommandButton("CONTAC.SUBMENU.SEGURIDAD.USUARIO",
                getResizableIconFromResource("/contac/resources/images/watchman_256x32.png", new Dimension(32, 32)));

        menuUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });

        //MENU ADMINISTRAR CONTRASENIA
        JCommandButton menuContrasenia = new JCommandButton("CONTAC.SUBMENU.SEGURIDAD.CONTRASENIA",
                getResizableIconFromResource("/contac/resources/images/salesman_refresh_256x32.png", new Dimension(32, 32)));

        menuContrasenia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement code here
            }
        });       

        //Add menu to position
        addCommandButton(menuUsuarios, RibbonElementPriority.MEDIUM);
        addCommandButton(menuContrasenia, RibbonElementPriority.MEDIUM);

        //Setting resizes policies
        setResizePolicies(CoreRibbonResizePolicies.getCorePoliciesRestrictive(this));
    }
}
