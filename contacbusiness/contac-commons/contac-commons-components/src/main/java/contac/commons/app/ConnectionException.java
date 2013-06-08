package contac.commons.app;

/**
 * Error launch while trying to connect to the server and exception arise
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-13-2010
 * Time: 02:33:41 PM
 */
public class ConnectionException extends RuntimeException{

    /**
     * Default constructor
     */
    public ConnectionException() {
        super("Error al intentar establecer una conexion al servidor.");
    }

    /**
     * Constructor with message
     * @param message, String
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message, String
     * @param cause, cause
     */
    public ConnectionException(String message, Throwable cause){
        super(message, cause);
    }
}
