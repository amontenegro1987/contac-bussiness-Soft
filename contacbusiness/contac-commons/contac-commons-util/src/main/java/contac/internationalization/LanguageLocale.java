package contac.internationalization;

import java.util.Locale;

/**
 * Represent the Global locale of the software
 * User: Eddy
 * Date: 7/14/11
 * Time: 10:05 AM
 */
public class LanguageLocale {

    //Specific LanguageLocale Contry and Language
    private static LanguageLocale instance;

    //Locale especified
    private Locale locale;

    //Constructor
    public LanguageLocale() {
        //TODO: For development process only one lenguage support
        locale = new Locale("es", "NI");
    }

    /**
     * Return specific locale config
     * @return Locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Return LanguageLocale instance
     * @return LanguageLocale
     */
    public static final LanguageLocale getInstance() {
        if (instance == null)
            instance = new LanguageLocale();

        return instance;
    }
}
