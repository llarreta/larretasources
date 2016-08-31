/**
 * Creado 10/08/2010 17:08:00
 */
package co.com.directv.sdii.model.dto;

/**
 * Encapsula la información de un movimiento de elemento no serializado
 * 
 * Fecha de Creación: 10/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
public class IBSMoveResourceBetweenCustAndDealerDTO {

	private String customerCode;
	private String dealerCode;
	private Long elementID;
	private String elementSerialCode;
	private String ird;
	private String linkedElementSerial;
	private String mouvementTypeID;
	private String countryCode;
	
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
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
	
	public IBSMoveResourceBetweenCustAndDealerDTO() {
		super();
	}
	
	public IBSMoveResourceBetweenCustAndDealerDTO(String customerCode,
			String dealerCode, Long elementID, String elementSerialCode,
			String ird, String linkedElementSerial, String mouvementTypeID,
			String countryCode) {
		super();
		this.customerCode = customerCode;
		this.dealerCode = dealerCode;
		this.elementID = elementID;
		this.elementSerialCode = elementSerialCode;
		this.ird = ird;
		this.linkedElementSerial = linkedElementSerial;
		this.mouvementTypeID = mouvementTypeID;
		this.countryCode = countryCode;
	}
	
	@Override
	public String toString() {
		return "IBSMoveResourceBetweenCustAndDealerDTO [IRD=" + ird
				+ ", countryCode=" + countryCode + ", customerCode="
				+ customerCode + ", dealerCode=" + dealerCode + ", elementID="
				+ elementID + ", elementSerialCode=" + elementSerialCode
				+ ", linkedElementSerial=" + linkedElementSerial
				+ ", mouvementTypeID=" + mouvementTypeID + "]";
	}
}
