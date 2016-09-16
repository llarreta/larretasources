/**
 * 
 */
package co.com.directv.sdii.exceptions;

/**
 * Excepción lanzada al generar archivos PDF en SmartDealer II.
 * 
 * @author Jimmy Vélez Muñoz
 *
 */
public class PDFException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2124224087548419590L;

	/**
	 * 
	 */
	public PDFException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PDFException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param codeMessage
	 * @param message
	 * @param cause
	 */
	public PDFException(String codeMessage, String message, Throwable cause) {
		super(codeMessage, message, cause);
	}

	/**
	 * @param message
	 */
	public PDFException(String message) {
		super(message);
	}

	/**
	 * @param codeMessage
	 * @param message
	 */
	public PDFException(String codeMessage, String message) {
		super(codeMessage, message);
	}

}
