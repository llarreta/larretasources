/**
 * Creado 17/08/2010 14:52:49
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 17/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class QualityControlNotSerializedVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8574324820312604475L;

	private Long whElementId;
	
	private Double faultyQuantity;
	
	private Double notFaultyQuantity;
	
	private boolean qualityControlRealized;
	
	private boolean faultDetected;

	public Long getWhElementId() {
		return whElementId;
	}

	public void setWhElementId(Long whElementId) {
		this.whElementId = whElementId;
	}

	public Double getFaultyQuantity() {
		return faultyQuantity;
	}

	public void setFaultyQuantity(Double faultyQuantity) {
		this.faultyQuantity = faultyQuantity;
	}

	public Double getNotFaultyQuantity() {
		return notFaultyQuantity;
	}

	public void setNotFaultyQuantity(Double notFaultyQuantity) {
		this.notFaultyQuantity = notFaultyQuantity;
	}

	public boolean isQualityControlRealized() {
		return qualityControlRealized;
	}

	public void setQualityControlRealized(boolean qualityControlRealized) {
		this.qualityControlRealized = qualityControlRealized;
	}

	public boolean isFaultDetected() {
		return faultDetected;
	}

	public void setFaultDetected(boolean faultDetected) {
		this.faultDetected = faultDetected;
	}
}
