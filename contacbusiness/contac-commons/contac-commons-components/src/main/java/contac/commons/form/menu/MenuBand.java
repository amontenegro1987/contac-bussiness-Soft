package contac.commons.form.menu;

import org.apache.log4j.Logger;
import org.pushingpixels.flamingo.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.ribbon.resize.IconRibbonBandResizePolicy;
import org.pushingpixels.flamingo.ribbon.resize.RibbonBandResizePolicy;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu band menu
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 10:31:07 PM
 */
public abstract class MenuBand extends JRibbonBand {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(MenuBand.class);

    /**
     * Main constructor
     *
     * @param title,       title of menu band
     * @param icon,        ResizableIcon icon of menu band
     * @param expandEvent, Event of expand
     */
    public MenuBand(String title, ResizableIcon icon, ActionListener expandEvent) {
        super(title, icon, expandEvent);
    }

    /**
     * Resize policies
     */
    protected void resizePolicies() {

        //*********************************************************************
        //Setting Ribbon resize policies
        //*********************************************************************
        List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
        resizePolicies.add(new CoreRibbonResizePolicies.Mirror(getControlPanel()));
        resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(getControlPanel()));
        resizePolicies.add(new IconRibbonBandResizePolicy(getControlPanel()));
        setResizePolicies(resizePolicies);

    }

    /**
     * Resizable icon from resource
     * @param resource, String
     * @param dimension, Dimension
     * @return ResizableIcon
     */
    protected ResizableIcon getResizableIconFromResource(String resource, Dimension dimension) {

        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/contac/servicio/app/icons/" + resource));
        //Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(resource));
                
        return ImageWrapperResizableIcon.getIcon(image, dimension);
    }

    /**
     * Init Ribbon band members
     */
    public abstract void initRibbonBand();
}
