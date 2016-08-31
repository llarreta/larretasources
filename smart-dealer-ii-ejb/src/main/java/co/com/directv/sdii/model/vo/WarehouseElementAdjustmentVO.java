/**
 * Creado 12/08/2010 11:01:08
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Encapsula la información de ajuste de un elemento de bodega
 * 
 * Fecha de Creación: 12/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WarehouseElementAdjustmentVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7341959337248667966L;

	/**
	 * identificador del elemento en bodega a ser ajustado
	 */
	private Long wareHouseElementId;
	
	/**
	 * Determina si el ajuste es porque el elemento no existe en la bodega
	 */
	private boolean elementDoesntExistInWh;
	
	/**
	 * identificador del estado del nuevo estado del elemento
	 */
	private Long elementStatusId;
	
	/**
	 * Si el ajuste corresponde al número del lote se debe enviar este campo
	 */
	private String loteNumber;
	
	/**
	 * Determina si se debe actualizar el serial del elemento vinculado
	 */
	private boolean attachElementSerialChange;
	
	private List<ElementSerialChangeVO> attachedElementSerialsChange;
	
	private Long causeAdjustmentId;
	
	
	public Long getWareHouseElementId() {
		return wareHouseElementId;
	}
	public void setWareHouseElementId(Long wareHouseElementId) {
		this.wareHouseElementId = wareHouseElementId;
	}
	public boolean isElementDoesntExistInWh() {
		return elementDoesntExistInWh;
	}
	public void setElementDoesntExistInWh(boolean elementDoesntExistInWh) {
		this.elementDoesntExistInWh = elementDoesntExistInWh;
	}
	public Long getElementStatusId() {
		return elementStatusId;
	}
	public void setElementStatusId(Long elementStatusId) {
		this.elementStatusId = elementStatusId;
	}
	public String getLoteNumber() {
		return loteNumber;
	}
	public void setLoteNumber(String loteNumber) {
		this.loteNumber = loteNumber;
	}
	public boolean isAttachElementSerialChange() {
		return attachElementSerialChange;
	}
	public void setAttachElementSerialChange(boolean attachElementSerialChange) {
		this.attachElementSerialChange = attachElementSerialChange;
	}
	public Long getCauseAdjustmentId() {
		return causeAdjustmentId;
	}
	public void setCauseAdjustmentId(Long causeAdjustmentId) {
		this.causeAdjustmentId = causeAdjustmentId;
	}
	public List<ElementSerialChangeVO> getAttachedElementSerialsChange() {
		return attachedElementSerialsChange;
	}
	public void setAttachedElementSerialsChange(
			List<ElementSerialChangeVO> attachedElementSerialsChange) {
		this.attachedElementSerialsChange = attachedElementSerialsChange;
	}
}
