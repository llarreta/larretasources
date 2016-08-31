package co.com.directv.sdii.exceptions;

/**
 * Excepción en capa de negocio en SmartDealer II
 * 
 * @author Jimmy Vélez Muñoz
 */
public class Log4jLoggerException extends BaseException {

	private static final long serialVersionUID = 258490590306159277L;

	public Log4jLoggerException() {
		super();
	}

	public Log4jLoggerException(String message, Throwable cause){
		super(message, cause);
	}
	
	public Log4jLoggerException(String message) {
		super(message);
	}

        public Log4jLoggerException(String codeMessage, String message, Throwable cause) {
            super(codeMessage,message, cause);
        }

	public Log4jLoggerException(String codeMessage,String message) {
            super(codeMessage,message);
        }

}
