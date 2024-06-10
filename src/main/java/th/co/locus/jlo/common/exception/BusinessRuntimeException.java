package th.co.locus.jlo.common.exception;

/**
 * The BusinessRuntimeException wraps all runtime standard Java exception and enriches them with a custom error code
 * in case that any business logic / condition / flow exception happen.
 * 
 * @author Jason Cho
 */
public class BusinessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8460356990632230194L;

	public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable t) {
        super(message, t);
    }

    public BusinessRuntimeException(Throwable t) {
        super(t);
    }
}
