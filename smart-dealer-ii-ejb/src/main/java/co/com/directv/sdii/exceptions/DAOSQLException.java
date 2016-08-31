package co.com.directv.sdii.exceptions;

/**
 * Excepción en la capa de acceso a datos en SmartDealer II
 * 
 * @author Jimmy Vélez Muñoz
 */
public class DAOSQLException extends BaseException {

	private static final long serialVersionUID = 258490590306159277L;

	public DAOSQLException() {
		super();
	}

	public DAOSQLException(String message, Throwable cause){
		super(message, cause);
	}

        public DAOSQLException(String message) {
		super(message);
	}

        public DAOSQLException(String codeMessage, String message, Throwable cause) {
            super(codeMessage,message, cause);
        }

	public DAOSQLException(String codeMessage,String message) {
            super(codeMessage,message);
        }
}
