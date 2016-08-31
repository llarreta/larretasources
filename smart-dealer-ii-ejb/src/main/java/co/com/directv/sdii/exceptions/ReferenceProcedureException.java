package co.com.directv.sdii.exceptions;

import java.util.List;

import javax.ejb.ApplicationException;

/**
 * Excepcion en capa de negocio en SmartDealer II
 * 
 * @author Joan Lopez
 */
@ApplicationException(rollback=true)
public class ReferenceProcedureException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1972476304792956099L;

	
	public ReferenceProcedureException(String message, Throwable cause){
		super(message, cause);
	}
	
	public ReferenceProcedureException(String message){
		super(message);
		
	}

}
