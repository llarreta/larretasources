package co.com.directv.sdii.model.dto;

/**
 * 
 * IBS Not Serialize Elements mouvements Data Transfer Object
 * 
 * Fecha de Creacion: Aug 3, 2010
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class IBSSerElemMovementDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1704644157114177224L;
	
	private Long sourceWarehouseID;
	private Long targetWarehouseID;
	private Long elementID;
	private String elementSerialCode;
	private String ird;
	private String linkedElementSerial;
	private String mouvementTypeID;
	private String countryCode;
	
	public Long getSourceWarehouseID() {
		return sourceWarehouseID;
	}
	public void setSourceWarehouseID(Long sourceWarehouseID) {
		this.sourceWarehouseID = sourceWarehouseID;
	}
	public Long getTargetWarehouseID() {
		return targetWarehouseID;
	}
	public void setTargetWarehouseID(Long targetWarehouseID) {
		this.targetWarehouseID = targetWarehouseID;
	}
	public Long getElementID() {
		return elementID;
	}
	public void setElementID(Long elementID) {
		this.elementID = elementID;
	}
	public String getElementSerialCode() {
		return elementSerialCode;
	}
	public void setElementSerialCode(String elementSerialCode) {
		this.elementSerialCode = elementSerialCode;
	}
	public String getIrd() {
		return ird;
	}
	public void setIrd(String ird) {
		this.ird = ird;
	}
	public String getLinkedElementSerial() {
		return linkedElementSerial;
	}
	public void setLinkedElementSerial(String linkedElementSerial) {
		this.linkedElementSerial = linkedElementSerial;
	}
	public String getMouvementTypeID() {
		return mouvementTypeID;
	}
	public void setMouvementTypeID(String mouvementTypeID) {
		this.mouvementTypeID = mouvementTypeID;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@Override
	public String toString() {
		return "IBSSerElemMovementDTO [IRD=" + ird + ", countryCode="
				+ countryCode + ", elementID=" + elementID
				+ ", elementSerialCode=" + elementSerialCode
				+ ", linkedElementSerial=" + linkedElementSerial
				+ ", mouvementTypeID=" + mouvementTypeID
				+ ", sourceWarehouseID=" + sourceWarehouseID
				+ ", targetWarehouseID=" + targetWarehouseID + "]";
	}
}
