package co.com.directv.sdii.exceptions;

import java.util.List;

/**
 * Excepción Base de SmartDealer II
 *
 * @author Jimmy Vélez Muñoz
 */

public class BaseException extends Exception {

   private static final long serialVersionUID = -1615734269806489647L;

   private String messageCode;
   private List<String> parameters;
   private String response;
   
	public BaseException() {
		super();
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(String codeMessage, String message, Throwable cause) {
		super(message, cause);
	    this.messageCode = codeMessage;
	}
	
	public BaseException(String txErrorKey, String codeMessage, String message, Throwable cause) {
		super(message, cause);
	    this.messageCode = codeMessage;
	    this.response = txErrorKey;
	}
	
	public BaseException(String txErrorKey, String codeMessage, String message, Throwable cause,List<String> parameters) {
		super(message, cause);
	    this.messageCode = codeMessage;
	    this.response = txErrorKey;
	    this.parameters = parameters;
	}
	
	public BaseException(String message) {
		 super(message);
	    this.messageCode = message;
	}
	
	public BaseException(String codeMessage,String message) {
	    super(message);
	    this.messageCode = codeMessage;
	}
	
	public BaseException(String txErrorKey, String codeMessage,String message) {
	    super(message);
	    this.messageCode = codeMessage;
	    this.response = txErrorKey;
	}
	
	public BaseException(String codeMessage,String message, List<String> parameters) {
	    super(message);
	    this.messageCode = codeMessage;
	    this.parameters = parameters;
	}
	
	public String getMessageCode() {
	    return messageCode;
	}
	
	public void setMessageCode(String messageCode) {
	    this.messageCode = messageCode;
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
