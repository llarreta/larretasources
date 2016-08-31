package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * HistoDealerWeekCapacity entity. @author MyEclipse Persistence Tools
 */

public class HistoDealerWeekCapacity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1008804815662551373L;
	private Long id;
	private DealerWeekCapacity dealerWeekCapacity;
	private Double monCapacity;
	private Double tueCapacity;
	private Double wedCapacity;
	private Double thuCapacity;
	private Double friCapacity;
	private Double satCapacity;
	private Double sunCapacity;
	private Double userIdLastChange;
	private Date dateLastChange;

	// Constructors

	/** default constructor */
	public HistoDealerWeekCapacity() {
	}

	/** minimal constructor */
	public HistoDealerWeekCapacity(DealerWeekCapacity dealerWeekCapacity,
			Double userIdLastChange, Date dateLastChange) {
		this.dealerWeekCapacity = dealerWeekCapacity;
		this.userIdLastChange = userIdLastChange;
		this.dateLastChange = dateLastChange;
	}

	/** full constructor */
	public HistoDealerWeekCapacity(DealerWeekCapacity dealerWeekCapacity,
			Double monCapacity, Double tueCapacity, Double wedCapacity,
			Double thuCapacity, Double friCapacity, Double satCapacity,
			Double sunCapacity, Double userIdLastChange, Date dateLastChange) {
		this.dealerWeekCapacity = dealerWeekCapacity;
		this.monCapacity = monCapacity;
		this.tueCapacity = tueCapacity;
		this.wedCapacity = wedCapacity;
		this.thuCapacity = thuCapacity;
		this.friCapacity = friCapacity;
		this.satCapacity = satCapacity;
		this.sunCapacity = sunCapacity;
		this.userIdLastChange = userIdLastChange;
		this.dateLastChange = dateLastChange;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DealerWeekCapacity getDealerWeekCapacity() {
		return this.dealerWeekCapacity;
	}

	public void setDealerWeekCapacity(DealerWeekCapacity dealerWeekCapacity) {
		this.dealerWeekCapacity = dealerWeekCapacity;
	}

	public Double getMonCapacity() {
		return this.monCapacity;
	}

	public void setMonCapacity(Double monCapacity) {
		this.monCapacity = monCapacity;
	}

	public Double getTueCapacity() {
		return this.tueCapacity;
	}

	public void setTueCapacity(Double tueCapacity) {
		this.tueCapacity = tueCapacity;
	}

	public Double getWedCapacity() {
		return this.wedCapacity;
	}

	public void setWedCapacity(Double wedCapacity) {
		this.wedCapacity = wedCapacity;
	}

	public Double getThuCapacity() {
		return this.thuCapacity;
	}

	public void setThuCapacity(Double thuCapacity) {
		this.thuCapacity = thuCapacity;
	}

	public Double getFriCapacity() {
		return this.friCapacity;
	}

	public void setFriCapacity(Double friCapacity) {
		this.friCapacity = friCapacity;
	}

	public Double getSatCapacity() {
		return this.satCapacity;
	}

	public void setSatCapacity(Double satCapacity) {
		this.satCapacity = satCapacity;
	}

	public Double getSunCapacity() {
		return this.sunCapacity;
	}

	public void setSunCapacity(Double sunCapacity) {
		this.sunCapacity = sunCapacity;
	}

	public Double getUserIdLastChange() {
		return this.userIdLastChange;
	}

	public void setUserIdLastChange(Double userIdLastChange) {
		this.userIdLastChange = userIdLastChange;
	}

	public Date getDateLastChange() {
		return this.dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

}