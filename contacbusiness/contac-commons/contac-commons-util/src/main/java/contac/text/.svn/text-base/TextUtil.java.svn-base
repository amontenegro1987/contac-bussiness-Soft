package contac.text;

import contac.internationalization.LanguageLocale;
import org.apache.log4j.Logger;
import sun.text.resources.FormatData_el;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Format and validation for text
 * User: Eddy Montenegro
 * Date: 7/28/11
 * Time: 4:10 PM
 */
public final class TextUtil {

    //Apache Log4j
    private static final Logger logger = Logger.getLogger(TextUtil.class);

    /**
     * Evaluate if a char is a valid character
     *
     * @param value, char
     * @return boolean
     */
    public static boolean isValidCharacter(char value) {

        //Getting value
        char c = value;

        if (!((c >= 'a' && c <= 'z') || c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú' || c == 'ñ' || c == ' ' ||
                c == 'Ñ' || c == '.' || (c >= 'A' && c <= 'Z'))) {
            return false;
        }

        return true;
    }

    /**
     * Evaluate if a char is a valid digit
     *
     * @param value, char
     * @return boolean
     */
    public static boolean isValidDigit(char value) {
        char c = value;


        if (!((Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE))) {
            return false;
        }

        return true;
    }

    /**
     * Evaluate if a char is a valid digit and currency
     *
     * @param value, char
     * @return boolean
     */
    public static boolean isValidDigitCurrency(char value) {
        char c = value;

        if (Character.isDigit(c) || c == '.') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Format value to String currency with 100 unit
     *
     * @param value, double
     * @return String
     */
    public static String formatCurrency(double value) {

        //Obtanining number format
        NumberFormat nf = NumberFormat.getCurrencyInstance(LanguageLocale.getInstance().getLocale());
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#,##0.0000");

        return df.format(value);
    }

    /**
     * Return parsing value with 100 unit
     *
     * @param value, String
     * @return double
     */
    public static double parseCurrency(String value) {

        //Obtaining number format
        NumberFormat nf = NumberFormat.getCurrencyInstance(LanguageLocale.getInstance().getLocale());
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#,##0.0000");

        double currency = 0.0;

        try {

            //Parse value
            currency = nf.parse(value).doubleValue();

        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return currency;
    }

    /**
     * Return parsing value to String format using Locale config
     *
     * @param value, Date
     * @return String
     */
    public static String formatDate(Date value) {

        //TODO: Implement a date format specific for any locale.
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", LanguageLocale.getInstance().getLocale());

        return df.format(value);
    }
}
