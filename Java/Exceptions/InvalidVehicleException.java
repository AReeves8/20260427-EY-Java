
/**
 * CUSTOM EXCEPTION
 *      the last word in the class name should be Exception
 * 
 *      extends RuntimeException if you want to create an unchecked exception
 *      extends Exception if you want it to be checked
 * 
 *      could extend most other exception classes, but those are more circumstantial
 * 
 */
public class InvalidVehicleException extends Exception {

    // Add construtors that pass parameters up to the parent class

    public InvalidVehicleException() {
    }

    public InvalidVehicleException(String message) {
        super(message);
    }

    public InvalidVehicleException(Throwable cause) {
        super(cause);
    }

    public InvalidVehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVehicleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
