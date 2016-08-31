package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * CrewOff entity. @author MyEclipse Persistence Tools
 */

public class CrewOff implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1514476169488223226L;
	private Long id;
	private Crew crew;
	private Date offDate;
	private String offDescription;

	// Constructors

	/** default constructor */
	public CrewOff() {
	}

	/** full constructor */
	public CrewOff(Crew crew, Date offDate, String offDescription) {
		this.crew = crew;
		this.offDate = offDate;
		this.offDescription = offDescription;
	}
	
	/** minimal constructor */
	public CrewOff(Date offDate, String offDescription) {		
		this.offDate = offDate;
		this.offDescription = offDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Crew getCrew() {
		return this.crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	public Date getOffDate() {
		return this.offDate;
	}

	public void setOffDate(Date offDate) {
		this.offDate = offDate;
	}

	public String getOffDescription() {
		return this.offDescription;
	}

	public void setOffDescription(String offDescription) {
		this.offDescription = offDescription;
	}

	@Override
	public String toString() {
		return "CrewOff [id=" + id + "]";
	}

}