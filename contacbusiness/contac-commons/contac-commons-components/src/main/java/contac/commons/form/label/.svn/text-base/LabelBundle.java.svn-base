package contac.commons.form.label;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Label resource bundle
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 10:51:21 PM
 */
public abstract class LabelBundle {

    /**
     * Resource Bundle
     */
    protected ResourceBundle resource;

    /**
     * Label Bundle constructor
     * @param resourceUrl, String
     */
    public LabelBundle(String resourceUrl) {

        //Getting default internationalization
        Locale locale = new Locale("es", "NIC");

        //Getting resource bundle
        resource = ResourceBundle.getBundle(resourceUrl, locale);
    }

    /**
     * Getting the label name for specific internationalization
     * @param key, String key
     * @return String
     */
    public String getLabel(String key) {
        return resource.getString(key);
    }
}
