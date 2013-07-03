package contac.commons.form.panel;

import contac.commons.app.BaseController;
import org.noos.xing.mydoggy.ContentManager;
import org.noos.xing.mydoggy.ToolWindowManager;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-30-2010
 * Time: 10:27:03 AM
 */
public abstract class StyleGenericFrame {

    /**
     * Generic frame
     */
    protected GenericFrame frame;
    /**
     * Tool Window manager
      */
    protected ToolWindowManager toolWindowManager;
    /**
     * Internal desk
     */
    protected JDesktopPane internalDesk;
    /**
     * Principal desk
     */
    protected JDesktopPane principalDesk;
    /**
     * Content manager
     */
    protected ContentManager manager;
    /**
     * ShowPanel
     */
    protected ShowPanel showPanel;

    /**
     * Constructor
     */
    public StyleGenericFrame() {
        this.frame = null;
        this.toolWindowManager = null;
        this.principalDesk = null;
        this.internalDesk = null;
        this.showPanel = null;
    }

    /**
     * Constructor
     *
     * @param frame, GenericFrame
     */
    public StyleGenericFrame(GenericFrame frame) {
        this.frame = frame;        
        this.principalDesk = frame.getPrincipalDesk();
        this.internalDesk = new JDesktopPane();
        this.showPanel = new ShowPanel(this.frame);
    }

    /**
     * Add Panel to MyDoggy
     *
     * @param key,       String
     * @param clazzName, String
     */
    public void addPanel(String key, String clazzName) {
        this.showPanel.showPanel(key, clazzName);
    }

    /**
     * Add Panel to MyDoggy
     * @param key, String
     * @param clazzName, String
     * @param controller, BaseController
     */
    public void addPanel(String key, String clazzName, BaseController controller) {
        this.showPanel.showPanel(key, clazzName, controller);
    }

    /**
     * Remove panel to MyDoggy
     *
     * @param panel, GenericPanel
     */
    public void removePanel(GenericPanel panel) {
        this.showPanel.removePanel(panel);
    }

    /**
     * Return Generic show panel
     * @return ShowPanel
     */
    public ShowPanel getShowPanel() {
        return this.showPanel;
    }

    /**
     * Set Generic Frame
     *
     * @param frame, GenericFrame
     */
    public void setFrame(GenericFrame frame) {
        this.frame = frame;
    }

    /**
     * Return window anchor
     * @return int
     */
    public int getWindowAnchor() {
        return this.frame.getWidth();
    }

    /**
     * Get tools anchor abstract
     * @return int
     */
    public abstract int getToolAnchor();

    /**
     * Clean memory....call Garbage collector
     */
    private void cleanMemory() {
        System.gc();
    }
}
