package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.rules.BusinessRequired;

public class TransferReason  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;	
	@BusinessRequired
	private String transferReasonName;
	@BusinessRequired
	private String transferReasonDescription;
	@BusinessRequired
	private String isActive;	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private MovementType movTypeIn;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private MovementType movTypeOut;
	@BusinessRequired
	private String transferReasonAuthorized;
	@BusinessRequired
	private String transferReasonAutomatic;
	@BusinessRequired
	private AdjustmentType adjustmentType;
	
	
	
	
	public TransferReason() {	
	}
	
	
	public TransferReason(String transferName, String transferReasonDescription,
			String isActive, MovementType movTypeIn, MovementType movTypeOut) {
		super();
		this.transferReasonName = transferName;
		this.transferReasonDescription= transferReasonDescription;
		this.isActive = isActive;
		this.movTypeIn = movTypeIn;
		this.movTypeOut = movTypeOut;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransferReasonName() {
		return transferReasonName;
	}
	public void setTransferReasonName(String transferName) {
		this.transferReasonName = transferName;
	}
	
	
	public String getTransferReasonDescription() {
		return transferReasonDescription;
	}


	public void setTransferReasonDescription(String transferReasonDescription) {
		this.transferReasonDescription = transferReasonDescription;
	}


	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public MovementType getMovTypeIn() {
		return movTypeIn;
	}
	public void setMovTypeIn(MovementType movTypeIn) {
		this.movTypeIn = movTypeIn;
	}
	public MovementType getMovTypeOut() {
		return movTypeOut;
	}
	public void setMovTypeOut(MovementType movTypeOut) {
		this.movTypeOut = movTypeOut;
	}


	public String getTransferReasonAuthorized() {
		return transferReasonAuthorized;
	}


	public void setTransferReasonAuthorized(String transferReasonAuthorized) {
		this.transferReasonAuthorized = transferReasonAuthorized;
	}


	public String getTransferReasonAutomatic() {
		return transferReasonAutomatic;
	}


	public void setTransferReasonAutomatic(String transferReasonAutomatic) {
		this.transferReasonAutomatic = transferReasonAutomatic;
	}


	public AdjustmentType getAdjustmentType() {
		return adjustmentType;
	}


	public void setAdjustmentType(AdjustmentType adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	
	

}
