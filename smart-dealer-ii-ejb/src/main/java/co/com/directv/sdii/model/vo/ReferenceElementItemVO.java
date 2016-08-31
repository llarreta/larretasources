/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;

/**
 * Objeto que encapsula la información de un ReferenceElementItem
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReferenceElementItem    
 */
public class ReferenceElementItemVO extends ReferenceElementItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6185147871240663123L;
	/**
	 * Lista usada para cuando se trata de un elemento no serializado
	 */
	private List<RefConfirmationVO> refConfirmations;
	/**
	 * Suma de las confirmaciones
	 */
	private Double refConfirmationSum;
	
	
	private Double refQuantityPartial;
	

	/**
	 * Objeto que se usa cuando se necesita enviar la informacion de elemento serializado
	 */
	private SerializedVO serializeElement;
	
	private NotSerializedVO notSerializedElement;
	
	/**
	 * CU INV 31 consulta o modificación de una remisión
	 * determina si el elemento fué removido de la remisión
	 */
	private boolean removeElement = false;
	
	/**
	 * CU INV 31 consulta o modificación de una remisión
	 * determina si el usuario desea especificar la cantidad de elemento a remitir
	 * el usuario la especificará en el elemento que envía en la propiedad refQuantity del pojo
	 */
	private boolean useCustomQuantity = false;
	
	/*
	 * Variable utilizada para indicar la cantidad del elemento en la bodega origen de la remision
	 * */
	private Double warehouseQuantity;

	/**
	 * cantidad de unidades agregadas a una remisión a causa de una inconsistencia de mas elementos físicos
	 */
	private Double refIncAddedQuantity;
	
	public Double getRefConfirmationSum() {
		return refConfirmationSum;
	}

	public void setRefConfirmationSum(Double refConfirmationSum) {
		this.refConfirmationSum = refConfirmationSum;
	}
	
	public List<RefConfirmationVO> getRefConfirmations() {
		return refConfirmations;
	}

	public void setRefConfirmations(List<RefConfirmationVO> refConfirmations) {
		this.refConfirmations = refConfirmations;
	}

	public SerializedVO getSerializeElement() {
		return serializeElement;
	}

	public void setSerializeElement(SerializedVO serializeElement) {
		this.serializeElement = serializeElement;
	}

	public NotSerializedVO getNotSerializedElement() {
		return notSerializedElement;
	}

	public void setNotSerializedElement(NotSerializedVO notSerializedElement) {
		this.notSerializedElement = notSerializedElement;
	}

	public boolean isRemoveElement() {
		return removeElement;
	}

	public void setRemoveElement(boolean removeElement) {
		this.removeElement = removeElement;
	}

	public boolean isUseCustomQuantity() {
		return useCustomQuantity;
	}

	public void setUseCustomQuantity(boolean useCustomQuantity) {
		this.useCustomQuantity = useCustomQuantity;
	}
	
	public boolean isSerialized() throws PropertiesException {
		return getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
	}

	public Double getWarehouseQuantity() {
		return warehouseQuantity;
	}

	public void setWarehouseQuantity(Double warehouseQuantity) {
		this.warehouseQuantity = warehouseQuantity;
	}

	public Double getRefQuantityPartial() {
		return refQuantityPartial;
	}

	public void setRefQuantityPartial(Double refQuantityPartial) {
		this.refQuantityPartial = refQuantityPartial;
	}

	public Double getRefIncAddedQuantity() {
		return refIncAddedQuantity;
	}

	public void setRefIncAddedQuantity(Double refIncAddedQuantity) {
		this.refIncAddedQuantity = refIncAddedQuantity;
	}

}
