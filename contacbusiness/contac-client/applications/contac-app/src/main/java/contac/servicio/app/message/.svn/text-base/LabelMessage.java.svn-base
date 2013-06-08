package contac.servicio.app.message;

import contac.commons.form.label.LabelBundle;

/**
 * Label message bundle for contac-app module
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-16-2010
 * Time: 11:00:24 PM
 */
public class LabelMessage extends LabelBundle {

    /**
     * Message URL
     */
    private static final String messageUrl = "contac.servicio.app.mensajes.Mensajes";

    /**
     * Instance class
     */
    private static LabelMessage instance;

    /**
     * Default constructor
     */
    public LabelMessage() {
        super(messageUrl);
    }

    /**
     * Return Label Message instance
     * @return LabelMessage
     */
    public static LabelMessage getInstance() {
        if (instance == null)
            instance = new LabelMessage();

        return instance;
    }
}
