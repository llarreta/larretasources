package co.com.directv.sdii.exceptions;

import java.util.List;

import javax.ejb.ApplicationException;

/**
 * Excepcion en capa de negocio en SmartDealer II
 * 
 * @author Joan Lopez
 */
@ApplicationException(rollback=true)
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 258490590306159277L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause){
		super(message, cause);
	}
	
	public BusinessException(String message) {
		super(message);
	}
        
    public BusinessException(String codeMessage, String message, Throwable cause) {
        super(codeMessage,message, cause);
    }
    
    public BusinessException(String txErrorKey, String codeMessage, String message, Throwable cause) {
        super(txErrorKey, codeMessage,message, cause);
    }
    
    public BusinessException(String txErrorKey, String codeMessage, String message, Throwable cause,List<String> parameters) {
        super(txErrorKey, codeMessage,message, cause,parameters);
    }

	public BusinessException(String codeMessage,String message) {
        super(codeMessage,message);
    }
	
	public BusinessException(String txErrorKey, String codeMessage, String message) {
        super(txErrorKey, codeMessage, message);
    }
	
	public BusinessException(String codeMessage, String message, List<String> parameters) {
        super(codeMessage, message, parameters);
    }
}
