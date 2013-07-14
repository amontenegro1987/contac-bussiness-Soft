package contac.commons.numbers;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Format Number Utils
 * User: Eddy Montenegro
 * Date: 7/15/11
 * Time: 10:38 AM
 */
public class FormatNumber {

    //Constants
    private static final int MAXIMUN_INTEGER_DIGITS = 5;
    private static final int MINIMUN_INTEGER_DIGITS = 5;

    /**
     * Format integer values
     * @param value, String
     * @return String
     */
    public static String formatInteger(String value) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(5);
        nf.setMinimumIntegerDigits(5);

        return nf.format(Integer.parseInt(value));
    }

    /**
     * Parse String value to Number
     * @param value, String
     * @return Number
     * @throws ParseException, Exception
     */
    public static Number parseIntegerValue(String value) throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(5);
        nf.setMinimumIntegerDigits(5);

        return nf.parse(value);
    }
}
