package co.com.directv.sdii.exceptions;

/**
 * Excepcion en capa de negocio en SmartDealer II
 * 
 * @author Joan Lopez
 */
public class HelperException extends BaseException {

	private static final long serialVersionUID = 258490590306159277L;

	public HelperException() {
		super();
	}

	public HelperException(String message, Throwable cause){
		super(message, cause);
	}
	
	public HelperException(String message) {
		super(message);
	}

        public HelperException(String codeMessage, String message, Throwable cause) {
            super(codeMessage,message, cause);
        }

	public HelperException(String codeMessage,String message) {
            super(codeMessage,message);
        }

}
