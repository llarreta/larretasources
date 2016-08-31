package co.com.directv.sdii.exceptions;

/**
 * Excepcion en capa de negocio en SmartDealer II
 * 
 * @author Joan Lopez
 */
public class PropertiesException extends BaseException {

	private static final long serialVersionUID = 258490590306159277L;

	public PropertiesException() {
		super();
	}

	public PropertiesException(String message, Throwable cause){
		super(message, cause);
	}
	
	public PropertiesException(String message) {
		super(message);
	}

        public PropertiesException(String codeMessage, String message, Throwable cause) {
            super(codeMessage,message, cause);
        }

	public PropertiesException(String codeMessage,String message) {
            super(codeMessage,message);
        }

}
