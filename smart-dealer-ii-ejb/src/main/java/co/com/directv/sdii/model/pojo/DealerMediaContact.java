package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * DealerMediaContact entity. @author MyEclipse Persistence Tools
 */

public class DealerMediaContact implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -291683874274731506L;
	private Long id;
	private MediaContactType mediaContactType;
	private Dealer dealer;
	private String mediaContactValue;
	private Long postalCode;

	// Constructors

	/** default constructor */
	public DealerMediaContact() {
	}

	/** full constructor */
	public DealerMediaContact(MediaContactType mediaContactType, Dealer dealer,
			String mediaContactValue, Long postalCode) {
		this.mediaContactType = mediaContactType;
		this.dealer = dealer;
		this.mediaContactValue = mediaContactValue;
		this.postalCode = postalCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MediaContactType getMediaContactType() {
		return this.mediaContactType;
	}

	public void setMediaContactType(MediaContactType mediaContactType) {
		this.mediaContactType = mediaContactType;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getMediaContactValue() {
		return this.mediaContactValue;
	}

	public void setMediaContactValue(String mediaContactValue) {
		this.mediaContactValue = mediaContactValue;
	}

	public Long getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "DealerMediaContact [id=" + id + "]";
	}

}