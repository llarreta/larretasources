package co.com.directv.sdii.exceptions;

/**
 * MessageDrivenBean Locator Exception
 * Maneja las exceptions que se puedan generar localizando
 * los servicios que consume SmartDealer de Aplicaciones 
 * externas.
 * 
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class JMSLocatorException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5350844217658343078L;

	public JMSLocatorException() {
		super();
	}

	public JMSLocatorException(String message, Throwable cause){
		super(message, cause);
	}
	
	public JMSLocatorException(String message) {
		super(message);
	}
        
    public JMSLocatorException(String codeMessage, String message, Throwable cause) {
        super(codeMessage,message, cause);
    }

	public JMSLocatorException(String codeMessage,String message) {
        super(codeMessage,message);
    }
}
