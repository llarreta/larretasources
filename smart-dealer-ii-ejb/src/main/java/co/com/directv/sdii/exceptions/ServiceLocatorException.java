package co.com.directv.sdii.exceptions;

/**
 * Service Locator Exception
 * Maneja las exceptions que se puedan generar localizando
 * los servicios que consume SmartDealer de Aplicaciones 
 * externas.
 * 
 * Fecha de Creación: 13/10/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ServiceLocatorException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5350844217658343078L;

	public ServiceLocatorException() {
		super();
	}

	public ServiceLocatorException(String message, Throwable cause){
		super(message, cause);
	}
	
	public ServiceLocatorException(String message) {
		super(message);
	}
        
    public ServiceLocatorException(String codeMessage, String message, Throwable cause) {
        super(codeMessage,message, cause);
    }

	public ServiceLocatorException(String codeMessage,String message) {
        super(codeMessage,message);
    }
}
