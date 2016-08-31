package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * SaleChannelInstaller entity. @author MyEclipse Persistence Tools
 */

public class SaleChannelInstaller implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537463204397226126L;
	private Long id;
	private SaleChanel saleChanel;
	private Dealer dealer;
	private Date inclusionDate;
	private String status;

	// Constructors

	/** default constructor */
	public SaleChannelInstaller() {
	}

	/** minimal constructor */
	public SaleChannelInstaller(SaleChanel saleChanel, Dealer dealer) {
		this.saleChanel = saleChanel;
		this.dealer = dealer;
	}

	/** full constructor */
	public SaleChannelInstaller(SaleChanel saleChanel, Dealer dealer,
			Date inclusionDate, String status) {
		this.saleChanel = saleChanel;
		this.dealer = dealer;
		this.inclusionDate = inclusionDate;
		this.status = status;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SaleChanel getSaleChanel() {
		return this.saleChanel;
	}

	public void setSaleChanel(SaleChanel saleChanel) {
		this.saleChanel = saleChanel;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Date getInclusionDate() {
		return this.inclusionDate;
	}

	public void setInclusionDate(Date inclusionDate) {
		this.inclusionDate = inclusionDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}