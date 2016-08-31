package co.com.directv.sdii.exceptions;

import java.util.List;

import javax.ejb.ApplicationException;

/**
 * Excepcion en capa de negocio en SmartDealer II
 * @author jalopez
 *
 */
@ApplicationException(rollback=true)
public class BusinessDetailException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 69388357315314720L;

	private List<String> parameters;
	private String response;
	
	public BusinessDetailException() {
		super();
	}

	public BusinessDetailException(String message, Throwable cause){
		super(message, cause);
	}
	
	public BusinessDetailException(String message) {
		super(message);
	}
        
    public BusinessDetailException(String codeMessage, String message, Throwable cause) {
        super(codeMessage,message, cause);
    }

	public BusinessDetailException(String codeMessage,String message) {
        super(codeMessage,message);
    }
	
	public BusinessDetailException(String codeMessage,String message,List<String> parameters) {
        super(codeMessage,message);
        this.parameters=parameters;
    }

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}	
}
