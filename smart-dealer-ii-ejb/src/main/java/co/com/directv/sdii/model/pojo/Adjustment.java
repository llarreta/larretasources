package co.com.directv.sdii.model.pojo;

import java.util.Date;


/**
 * Adjustment entity. @author MyEclipse Persistence Tools
 */

public class Adjustment  implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 470111272399884490L;
	// Fields    

	private Long id;
	private TransferReason transferReason;
	private AdjustmentStatus adjustmentStatus;
	private String coment;
	private User creationUser;
	private String authorized;
	private Date creationDate;
	private Country country;
	private String isSerialized; 


	// Constructors

	/** default constructor */
	public Adjustment() {
	}

	/**
	 * 
	 * @param id
	 * @param transferReason
	 * @param adjustmentStatus
	 * @param creationUser
	 * @param authorized
	 * @param creationDate
	 * @param country
	 */
	public Adjustment(Long id, TransferReason transferReason, AdjustmentStatus adjustmentStatus, User creationUser, String authorized, Date creationDate, Country country) {
		this.id = id;
		this.transferReason = transferReason;
		this.adjustmentStatus = adjustmentStatus;
		this.creationUser = creationUser;
		this.authorized = authorized;
		this.creationDate = creationDate;
		this.country = country;
	}

	/**
	 * 
	 * @param id
	 * @param transferReason
	 * @param adjustmentStatus
	 * @param coment
	 * @param creationUser
	 * @param authorized
	 * @param authorizedUser
	 * @param creationDate
	 * @param authorizationDate
	 */
	public Adjustment(Long id, TransferReason transferReason, AdjustmentStatus adjustmentStatus, String coment, User creationUser, String authorized, User authorizedUser, Date creationDate, Date authorizationDate) {
		this.id = id;
		this.transferReason = transferReason;
		this.adjustmentStatus = adjustmentStatus;
		this.coment = coment;
		this.creationUser = creationUser;
		this.authorized = authorized;
		this.creationDate = creationDate;
	}


	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TransferReason getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(TransferReason transferReason) {
		this.transferReason = transferReason;
	}

	public AdjustmentStatus getAdjustmentStatus() {
		return this.adjustmentStatus;
	}

	public void setAdjustmentStatus(AdjustmentStatus adjustmentStatus) {
		this.adjustmentStatus = adjustmentStatus;
	}

	public String getComent() {
		return this.coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}
	

	public String getAuthorized() {
		return this.authorized;
	}

	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(User creationUser) {
		this.creationUser = creationUser;
	}

	public String getIsSerialized() {
		return isSerialized;
	}

	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}
	
}