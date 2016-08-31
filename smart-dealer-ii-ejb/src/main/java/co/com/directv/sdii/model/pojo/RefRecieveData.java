package co.com.directv.sdii.model.pojo;

import java.util.Date;

// default package


/**
 * RefRecieveData entity. @author MyEclipse Persistence Tools
 */

public class RefRecieveData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7510071228803424083L;
	private Long id;
	private Reference reference;
	private Date arrivalDate;
	private Double recieveQty;
	private String comments;
	private User userSend;

	// Constructors

	/** default constructor */
	public RefRecieveData() {
	}

	/** full constructor */
	public RefRecieveData(Long id, Reference reference,
			Date arrivalDate, Double recieveQty, String comments) {
		this.id = id;
		this.reference = reference;
		this.arrivalDate = arrivalDate;
		this.recieveQty = recieveQty;
		this.comments = comments;
	}

	// Property accessors

	

	public Date getArrivalDate() {
		return this.arrivalDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Double getRecieveQty() {
		return this.recieveQty;
	}

	public void setRecieveQty(Double recieveQty) {
		this.recieveQty = recieveQty;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public User getUserSend() {
		return userSend;
	}

	public void setUserSend(User userSend) {
		this.userSend = userSend;
	}

}
