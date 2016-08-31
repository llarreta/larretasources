package co.com.directv.sdii.model.pojo;

import org.hibernate.envers.Audited;

import co.com.directv.sdii.audit.Auditable;

/**
 * DealerDeatail entity. @author MyEclipse Persistence Tools
 */

public class DealerDetail implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6712010899839889087L;
	private Dealer dealer;
	private Long technicianWoQtyDay;
	private Long companyWoQtyDay;
	private String attendBuildings;

	//Campos para realizar la auditoria
	private Long dealerId;
	private Long audUserId;
	
	private String attendTypeOddEven;

	// Constructors

	/** default constructor */
	public DealerDetail() {
	}

	/** minimal constructor */
	public DealerDetail(Dealer dealer) {
		this.dealer = dealer;
	}

	/** full constructor */
	public DealerDetail(Dealer dealer, Long technicianWoQtyDay,
			Long companyWoQtyDay, String attendBuildings) {
		this.dealer = dealer;
		this.technicianWoQtyDay = technicianWoQtyDay;
		this.companyWoQtyDay = companyWoQtyDay;
		this.attendBuildings = attendBuildings;
	}

	// Property accessors
	@Audited
	public Long getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	@Audited
	public Long getTechnicianWoQtyDay() {
		return this.technicianWoQtyDay;
	}

	public void setTechnicianWoQtyDay(Long technicianWoQtyDay) {
		this.technicianWoQtyDay = technicianWoQtyDay;
	}
	
	@Audited
	public Long getCompanyWoQtyDay() {
		return this.companyWoQtyDay;
	}

	public void setCompanyWoQtyDay(Long companyWoQtyDay) {
		this.companyWoQtyDay = companyWoQtyDay;
	}

	@Audited	
	public String getAttendBuildings() {
		return this.attendBuildings;
	}

	public void setAttendBuildings(String attendBuildings) {
		this.attendBuildings = attendBuildings;
	}
	@Audited	
	public Long getAudUserId() {
		return audUserId;
	}

	public void setAudUserId(Long audUserId) {
		this.audUserId = audUserId;
	}

	public String getAttendTypeOddEven() {
		return attendTypeOddEven;
	}

	public void setAttendTypeOddEven(String attendTypeOddEven) {
		this.attendTypeOddEven = attendTypeOddEven;
	}

}