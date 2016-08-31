/**
 * 
 */
package co.com.directv.sdii.exceptions;

/**
 * Clase de excepción que se lanza cuando falla la generación de un archivo de Excel.
 * 
 * Fecha: 11-02-2011
 * 
 * @author jvelezmu
 *
 */
public class ExcelGenerationException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501529254993074146L;

	/**
	 * 
	 */
	public ExcelGenerationException() {
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExcelGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param codeMessage
	 * @param message
	 * @param cause
	 */
	public ExcelGenerationException(String codeMessage, String message,
			Throwable cause) {
		super(codeMessage, message, cause);
	}

	/**
	 * @param message
	 */
	public ExcelGenerationException(String message) {
		super(message);
	}

	/**
	 * @param codeMessage
	 * @param message
	 */
	public ExcelGenerationException(String codeMessage, String message) {
		super(codeMessage, message);
	}
}
