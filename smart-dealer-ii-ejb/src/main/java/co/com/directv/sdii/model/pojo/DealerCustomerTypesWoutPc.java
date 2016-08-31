package co.com.directv.sdii.model.pojo;

import java.util.Date;

import org.hibernate.envers.Audited;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * DealerCustomerTypesWoutPc entity. @author MyEclipse Persistence Tools
 */

public class DealerCustomerTypesWoutPc implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7054472947688851533L;
	// Fields
	private Long 				id;
	private User 				user;
	private Dealer 				dealer;
	private CustomerClassType 	customerClassType;
	private Country 			country;
	private Date 				dateLastChange;
	private String 				status; //indica si esta configuracion debe estar chequeada o no en UI
	
	//Campos para realizar la auditoria
	private Long dealerId;
	private Long userId;
	private Long customerCodeTypeId;
	private Long countryId;
	
	// Constructors

	/** default constructor */
	public DealerCustomerTypesWoutPc() {
	}

	public DealerCustomerTypesWoutPc(
			CustomerClassType customerCodeType, Long countryId) throws PropertiesException {
			this.customerClassType = customerCodeType;
			this.status = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
			this.country = new Country(countryId);
	}
	
	/** minimal constructor */
	public DealerCustomerTypesWoutPc(User user, Dealer dealer,
			CustomerClassType customerCodeType, Country country,
			Date dateLastChange, String status) {
		this.user = user;
		this.dealer = dealer;
		this.customerClassType = customerCodeType;
		this.country = country;
		this.dateLastChange = dateLastChange;
		this.status = status;
	}

	// Property accessors
	@Audited
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public CustomerClassType getCustomerClassType() {
		return this.customerClassType;
	}

	public void setCustomerClassType(CustomerClassType customerCodeType) {
		this.customerClassType = customerCodeType;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Audited
	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	@Audited
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Audited
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Audited
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Audited
	public Long getCustomerCodeTypeId() {
		return customerCodeTypeId;
	}

	public void setCustomerCodeTypeId(Long customerCodeTypeId) {
		this.customerCodeTypeId = customerCodeTypeId;
	}

	@Audited
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
}