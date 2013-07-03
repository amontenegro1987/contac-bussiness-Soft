package contac.security;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: sgonzalez
 * Date: Dec 7, 2006
 * Time: 8:32:46 AM
 */
public class Security {

    public static final Logger logger = Logger.getLogger(Security.class);

    /**
     * Este metodo realiza un HASH MD5 de la clave recibida y luego hace un econding BASE16.
     *
     * @param password
     * @return
     */
    public static String createPasswordHash(String password) throws NoSuchAlgorithmException {

        if (logger.isDebugEnabled())
            logger.debug("Clave : " + password);

        // convert password to byte data
        byte[] passBytes = password.getBytes();

        // Hash bytes
        byte[] hash = MessageDigest.getInstance("MD5").digest(passBytes);

        // Encode base64
        // byte[] enc = Base64.encodeBase64(hash);
        // String passwordHash = new String(enc, "ISO-8859-1");

        // Encode base16
        String passwordHash = encodeBase16(hash);

        if (logger.isDebugEnabled())
            logger.debug("Hash : " + passwordHash);

        return passwordHash;

    }

    /**
     * Base16 encoding (HEX).
     */
    public static String encodeBase16(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            // top 4 bits
            char c = (char) ((b >> 4) & 0xf);
            if (c > 9)
                c = (char) ((c - 10) + 'a');
            else
                c = (char) (c + '0');
            sb.append(c);
            // bottom 4 bits
            c = (char) (b & 0xf);
            if (c > 9)
                c = (char) ((c - 10) + 'a');
            else
                c = (char) (c + '0');
            sb.append(c);
        }
        return sb.toString();
    }
}
