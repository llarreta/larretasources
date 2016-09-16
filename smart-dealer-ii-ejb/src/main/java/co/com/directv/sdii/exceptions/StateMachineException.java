/**
 * 
 */
package co.com.directv.sdii.exceptions;

/**
 * Excepción en el cambio de estado de una WorkOrder
 * 
 * 
 * @author Jimmy Velez Munoz
 */
public class StateMachineException extends BaseException {

	private static final long serialVersionUID = -8430233857180466146L;

	public StateMachineException() {
		super();
	}

	public StateMachineException(String message, Throwable cause) {
		super(message, cause);
	}

	public StateMachineException(String message) {
		super(message);
	}

	public StateMachineException(String codeMessage, String message,
			Throwable cause) {
		super(codeMessage, message, cause);
	}

	public StateMachineException(String codeMessage, String message) {
		super(codeMessage, message);
	}
}
