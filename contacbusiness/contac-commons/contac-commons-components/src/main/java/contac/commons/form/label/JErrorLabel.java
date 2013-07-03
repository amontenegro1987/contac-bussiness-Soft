package contac.commons.form.label;

/**
 * Represents a Error Label
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-13-2010
 * Time: 03:19:20 PM
 */
public class JErrorLabel extends javax.swing.JLabel{

    /**
     * Constructor
     * @param errorMessage, String
     */
    public JErrorLabel(String errorMessage) {
        super("<HTML><FONT COLOR = Red>" + errorMessage + "</FONT></HTML>");
    }
}
