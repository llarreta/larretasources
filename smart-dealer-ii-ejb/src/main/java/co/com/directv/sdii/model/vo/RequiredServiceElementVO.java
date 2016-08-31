package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.RequiredServiceElement;

/**
 * 
 * RequiredServiceElement Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.RequiredServiceElement
 */
public class RequiredServiceElementVO extends RequiredServiceElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8577509055688771977L;

	private List<RequiredServiceElement> serializedElements;
	private List<RequiredServiceElement> notSerializedElements;
	
	public List<RequiredServiceElement> getSerializedElements() {
		return serializedElements;
	}
	public void setSerializedElements(
			List<RequiredServiceElement> serializedElements) {
		this.serializedElements = serializedElements;
	}
	public List<RequiredServiceElement> getNotSerializedElements() {
		return notSerializedElements;
	}
	public void setNotSerializedElements(
			List<RequiredServiceElement> notSerializedElements) {
		this.notSerializedElements = notSerializedElements;
	}
	
	
}
