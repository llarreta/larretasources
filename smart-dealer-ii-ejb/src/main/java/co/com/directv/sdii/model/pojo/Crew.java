package co.com.directv.sdii.model.pojo;

// default package

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Crew entity. @author MyEclipse Persistence Tools
 */

public class Crew implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1397516609406120396L;

	private Long id;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private CrewType crewType;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Vehicle vehicle;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Dealer dealer;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private CrewStatus crewStatus;

    @BusinessRequired
	private Date initDate;
       
	private Date endDate;

	// Constructors

	/** default constructor */
	public Crew() {
	}
	
	public Crew(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public Crew(CrewType crewType, Vehicle vehicle, Dealer dealer,
			CrewStatus crewStatus, Date initDate) {
		this.crewType = crewType;
		this.vehicle = vehicle;
		this.dealer = dealer;
		this.crewStatus = crewStatus;
		this.initDate = initDate;
	}
	
	/** minimal constructor */
	public Crew(CrewType crewType, CrewStatus crewStatus, Date initDate, Date endDate, Long id) {
		this.crewType = crewType;
		this.crewStatus = crewStatus;
		this.initDate = initDate;
		this.endDate = endDate;
		this.id = id;
		
	}
	
	public Crew(long id, Date initDate, Date endDate ){
		this.id = id;
		this.initDate = initDate;
		this.endDate = endDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CrewType getCrewType() {
		return this.crewType;
	}

	public void setCrewType(CrewType crewType) {
		this.crewType = crewType;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public CrewStatus getCrewStatus() {
		return this.crewStatus;
	}

	public void setCrewStatus(CrewStatus crewStatus) {
		this.crewStatus = crewStatus;
	}

	public Date getInitDate() {
		return this.initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Crew [id=" + id + "]";
	}

}