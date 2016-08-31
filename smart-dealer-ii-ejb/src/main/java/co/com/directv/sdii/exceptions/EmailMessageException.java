/**
 * 
 */
package co.com.directv.sdii.exceptions;

/**
 * Excepci�n generada por fallos en el env�o de Email.
 * 
 * @author Jimmy V�lez Mu�oz
 *
 */
public class EmailMessageException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8584406709494049570L;

	/**
	 * 
	 */
	public EmailMessageException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param codeMessage
	 * @param message
	 * @param cause
	 */
	public EmailMessageException(String codeMessage, String message,
			Throwable cause) {
		super(codeMessage, message, cause);
	}

	/**
	 * @param message
	 */
	public EmailMessageException(String message) {
		super(message);
	}

	/**
	 * @param codeMessage
	 * @param message
	 */
	public EmailMessageException(String codeMessage, String message) {
		super(codeMessage, message);
	}

}
