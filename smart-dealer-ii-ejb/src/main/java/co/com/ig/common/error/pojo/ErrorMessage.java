/**
 * Creado 6/07/2010 15:51:58
 */
package co.com.ig.common.error.pojo;

import java.io.Serializable;

/**
 * Objeto que encapsula la información de errores
 * 
 * Fecha de Creación: 6/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
public class ErrorMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5197791018229018131L;
	private Long id;
	private Long componentId;
	private String errorKey;
	private String errorCode;
	private String errorMessage;
	
	
	
	/**
	 * Constructor: <Descripcion>
	 * @author
	 */
	public ErrorMessage() {
	}


	public ErrorMessage(Long id, Long componentId, String errorKey,
			String errorCode, String errorMessage) {
		super();
		this.id = id;
		this.componentId = componentId;
		this.errorKey = errorKey;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getComponentId() {
		return componentId;
	}



	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}



	public String getErrorKey() {
		return errorKey;
	}



	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}



	public String getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
