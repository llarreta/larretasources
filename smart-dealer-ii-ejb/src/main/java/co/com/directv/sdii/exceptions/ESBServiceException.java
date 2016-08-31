package co.com.directv.sdii.exceptions;

/**
 * Excepción en la capa de servicios de sistemas externos en SmartDealer II
 * 
 * @author Jimmy Vélez Muñoz
 */
public class ESBServiceException extends BaseException {

	private static final long serialVersionUID = -1155404784732672128L;

	public ESBServiceException() {
		super();
	}

	public ESBServiceException(String message, Throwable cause){
		super(message, cause);
	}

        public ESBServiceException(String message) {
		super(message);
	}

        public ESBServiceException(String codeMessage, String message, Throwable cause) {
            super(codeMessage,message, cause);
        }

	public ESBServiceException(String codeMessage,String message) {
            super(codeMessage,message);
        }
}
