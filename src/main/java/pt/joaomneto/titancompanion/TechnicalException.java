package pt.joaomneto.titancompanion;

/**
 * Created by Joao Neto on 07-08-2017.
 */

public class TechnicalException extends RuntimeException {

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
}
