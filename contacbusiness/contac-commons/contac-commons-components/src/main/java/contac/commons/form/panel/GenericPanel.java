package contac.commons.form.panel;

import contac.commons.app.BaseController;
import org.pushingpixels.flamingo.ribbon.JRibbonFrame;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a Generic Panel
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-14-2010
 * Time: 10:48:23 PM
 */
public abstract class GenericPanel extends javax.swing.JPanel {

    /**
     * MDI Application form reference
     */
    public GenericFrame mdi;

    /**
     * Key of panel
     */
    private String key;

    /**
     * Title of panel
     */
    private String title;

    /**
     * Title bar of panel
     */
    private String titleBar;

    /**
     * Close enable panel
     */
    private boolean closeEnabled;

    /**
     * Resource message
     */
    private ResourceBundle resourceMessage;

    /**
     * Get MDI Panel
     *
     * @return JRibbonFrame
     */
    public GenericFrame getMDI() {
        return mdi;
    }

    /**
     * Set MDI Panel
     *
     * @param MDI, JRibbonFrame
     */
    public void setMDI(JRibbonFrame MDI) {
        this.mdi = mdi;
    }

    /**
     * Get Panel key
     *
     * @return String
     */
    public String getKey() {
        return key;
    }

    /**
     * Setting Panel key
     *
     * @param key, String
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get Panel title
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setting Panel title
     *
     * @param title, String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Panel title bar
     *
     * @return String
     */
    public String getTitleBar() {
        return titleBar;
    }

    /**
     * Setting Panel title bar
     *
     * @param titleBar, String
     */
    public void setTitleBar(String titleBar) {
        this.titleBar = titleBar;
    }

    /**
     * Get Panel close enabled
     *
     * @return boolean
     */
    public boolean isCloseEnabled() {
        return closeEnabled;
    }

    /**
     * Setting Panel close enabled
     *
     * @param closeEnabled, boolean
     */
    public void setCloseEnabled(boolean closeEnabled) {
        this.closeEnabled = closeEnabled;
    }

    /**
     * Getting resource label from key value
     * @param key, String
     * @return String
     */
    public String getResourceLabel(String key) {
        return resourceMessage.getString(key);
    }

    /**
     * Generic Panel Constructor Default
     */
    public GenericPanel() {
        this.mdi = null;
        this.key = "";
        this.title = "";
        this.titleBar = "";
        this.closeEnabled = true;
        this.resourceMessage = ResourceBundle.getBundle("contac.commons.util.messages.Mensajes", getDefaultLocale());
    }

    /**
     * Generic Panel Constructor
     *
     * @param mdi, GenericPanel
     */
    public GenericPanel(GenericFrame mdi) {
        this.mdi = mdi;
        this.key = "";
        this.title = "";
        this.titleBar = "";
        this.closeEnabled = true;
        this.resourceMessage = ResourceBundle.getBundle("contac.commons.util.messages.Mensajes", getDefaultLocale());
    }

    /**
     * Generic Panel Constructor
     * @param mdi, GenericPanel
     * @param controller, BaseController
     */
    public GenericPanel(GenericFrame mdi, BaseController controller) {
        this.mdi = mdi;
        this.key = "";
        this.title = "";
        this.titleBar = "";
        this.closeEnabled = true;
        this.resourceMessage = ResourceBundle.getBundle("contac.commons.util.messages.Mensajes", getDefaultLocale());
    }

    /**
     * Generic Panel Constructor
     *
     * @param mdi,          GenericFrame
     * @param key,          String
     * @param title,        String
     * @param closeEnabled, boolean
     */
    public GenericPanel(GenericFrame mdi, String key, String title, boolean closeEnabled) {
        this.mdi = mdi;
        this.key = key;
        this.title = title;
        this.titleBar = title;
        this.closeEnabled = closeEnabled;
    }

    public GenericPanel(GenericFrame mdi, String key, String title, boolean closeEnabled, String messageUrl,
                        Locale locale) {
        this.mdi = mdi;
        this.key = key;
        this.title = title;
        this.titleBar = title;
        this.closeEnabled = closeEnabled;
        this.resourceMessage = ResourceBundle.getBundle(messageUrl, locale);
    }

    /**
     * Init value components for GUI
     */
    public abstract void initValues();
}
