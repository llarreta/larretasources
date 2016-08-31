package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * DealerBulding entity. @author MyEclipse Persistence Tools
 */

public class DealerBulding implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5228496100709630775L;
	private Long id;
	private User user;
	private DealerCoverage dealerCoverage;
	private Building building;
	private Date dateLastChange;
	private String status;

	// Constructors

	/** default constructor */
	public DealerBulding() {
	}

	public DealerBulding(DealerBulding dealerBuilding) throws PropertiesException {
		if(dealerBuilding != null) {
			this.id = dealerBuilding.getId();
			this.user = dealerBuilding.getUser();
			this.building = dealerBuilding.getBuilding();
			this.dateLastChange = dealerBuilding.getDateLastChange();
			this.status = dealerBuilding.getStatus();
			//si el DealerBuilding no está activo, no se asigna el dealerCoverage para que se pueda reconfigurar
			if(this.status != null && this.status.equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())) {
				this.dealerCoverage = dealerBuilding.getDealerCoverage();
			}
		}
	}
	
	public DealerBulding(Building building) throws PropertiesException {
		this.building = building;
		this.status = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
	}
	
	/** minimal constructor */
	public DealerBulding(User user, DealerCoverage dealerCoverage,
			Building building, Date dateLastChange, String status) {
		this.user = user;
		this.dealerCoverage = dealerCoverage;
		this.building = building;
		this.dateLastChange = dateLastChange;
		this.status = status;
	}

	// Property accessors

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

	public DealerCoverage getDealerCoverage() {
		return this.dealerCoverage;
	}

	public void setDealerCoverage(DealerCoverage dealerCoverage) {
		this.dealerCoverage = dealerCoverage;
	}

	public Building getBuilding() {
		return this.building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}