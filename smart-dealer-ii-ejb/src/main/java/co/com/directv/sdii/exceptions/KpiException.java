package co.com.directv.sdii.exceptions;

import java.util.List;

/**
 * 
 * Excepcion en la capa de kpi 
 * 
 * Fecha de Creación: 1/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class KpiException extends BusinessException{

	private static final long serialVersionUID = -9064730072954269545L;
	
	public KpiException() {
		super();
	}

	public KpiException(String message, Throwable cause){
		super(message, cause);
	}
	
	public KpiException(String message) {
		super(message);
	}
        
    public KpiException(String codeMessage, String message, Throwable cause) {
        super(codeMessage,message, cause);
    }

	public KpiException(String codeMessage,String message) {
        super(codeMessage,message);
    }
	
	public KpiException(String codeMessage, String message, List<String> parameters) {
        super(codeMessage, message, parameters);
    }
	
	public KpiException(BusinessException exception){
		super(exception.getMessageCode(),exception.getMessage());
	}

}
