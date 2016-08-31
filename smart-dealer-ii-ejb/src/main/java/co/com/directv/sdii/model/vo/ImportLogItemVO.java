/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.pojo.ImportLogItem;

/**
 * Objeto que encapsula la información de un ImportLogItem
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ImportLogItem    
 */
public class ImportLogItemVO extends ImportLogItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7977751732276577501L;
	/*
	 * Atributos correspondientes a un ItemSerializable
	 */
	private String codeSerilizedElement;
	private String serialSerializedElement;
	private String ridSerializedElement;
	private String serialLinkedElementSerializedElement;
	//jjimenezh se agrega para registrar la cantidad de elementos no serializados confirmados para el item desde la vista
	private Double confirmedQuantity;
	//jalopez CU INV 04 se agrega para almacenar la lista de confirmaciones
	private ImpLogConfirmation impLogConfirmation;
	private List<ImpLogConfirmationVO> impLogConfirmations;
	//jalopez CU INV 04 se agrega para encapsular un ElementVO que contiene los serializados
	private ElementVO elementVO;
	//gfandino CU INV01 se agrega para encapsular los objetos serializados y no serializados de un registro de importación
	private SerializedVO serializedVO;
	private NotSerializedVO notSerializedVO;
	
	
	
	
	
	public SerializedVO getSerializedVO() {
		return serializedVO;
	}
	public void setSerializedVO(SerializedVO serializedVO) {
		this.serializedVO = serializedVO;
	}
	public NotSerializedVO getNotSerializedVO() {
		return notSerializedVO;
	}
	public void setNotSerializedVO(NotSerializedVO notSerializedVO) {
		this.notSerializedVO = notSerializedVO;
	}
	public String getCodeSerilizedElement() {
		return codeSerilizedElement;
	}
	public void setCodeSerilizedElement(String codeSerilizedElement) {
		this.codeSerilizedElement = codeSerilizedElement;
	}
	public String getSerialSerializedElement() {
		return serialSerializedElement;
	}
	public void setSerialSerializedElement(String serialSerializedElement) {
		this.serialSerializedElement = serialSerializedElement;
	}
	public String getRidSerializedElement() {
		return ridSerializedElement;
	}
	public void setRidSerializedElement(String ridSerializedElement) {
		this.ridSerializedElement = ridSerializedElement;
	}
	public String getSerialLinkedElementSerializedElement() {
		return serialLinkedElementSerializedElement;
	}
	public void setSerialLinkedElementSerializedElement(
			String serialLinkedElementSerializedElement) {
		this.serialLinkedElementSerializedElement = serialLinkedElementSerializedElement;
	}
	public Double getConfirmedQuantity() {
		return confirmedQuantity;
	}
	public void setConfirmedQuantity(Double confirmedQuantity) {
		this.confirmedQuantity = confirmedQuantity;
	}	
	public ElementVO getElementVO() {
		return elementVO;
	}
	public void setElementVO(ElementVO elementVO) {
		this.elementVO = elementVO;
	}
	public ImpLogConfirmation getImpLogConfirmation() {
		return impLogConfirmation;
	}
	public void setImpLogConfirmation(ImpLogConfirmation impLogConfirmation) {
		this.impLogConfirmation = impLogConfirmation;
	}
	public List<ImpLogConfirmationVO> getImpLogConfirmations() {
		return impLogConfirmations;
	}
	public void setImpLogConfirmations(List<ImpLogConfirmationVO> impLogConfirmations) {
		this.impLogConfirmations = impLogConfirmations;
	}
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();

		return buff.toString();
	}
	
	
}
