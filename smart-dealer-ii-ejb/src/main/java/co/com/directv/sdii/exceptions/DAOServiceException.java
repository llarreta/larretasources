package co.com.directv.sdii.exceptions;

/**
 * Excepción en la capa de acceso a datos en SmartDealer II
 * 
 * @author Jimmy Vélez Muñoz
 */
public class DAOServiceException extends BaseException {

	private static final long serialVersionUID = 258490590306159277L;

	public DAOServiceException() {
		super();
	}

	public DAOServiceException(String message, Throwable cause){
		super(message, cause);
	}

         public DAOServiceException(String message) {
		super(message);
	}

        public DAOServiceException(String codeMessage, String message, Throwable cause) {
            super(codeMessage,message, cause);
        }

	public DAOServiceException(String codeMessage,String message) {
            super(codeMessage,message);
        }
}
