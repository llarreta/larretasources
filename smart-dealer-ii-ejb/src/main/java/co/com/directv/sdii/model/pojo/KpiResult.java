package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * KpiResult entity. @author MyEclipse Persistence Tools
 */

public class KpiResult implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6833677330891268367L;
	private Long id;
	private KpiConfiguration kpiConfiguration;
	private Dealer dealer;
	private Country country;
	private Date resultDate;
	private Double result;

	// Constructors

	/** default constructor */
	public KpiResult() {
	}

	/** full constructor */
	public KpiResult(KpiConfiguration kpiConfiguration, Dealer dealer,
			Country country, Date resultDate, Double result) {
		this.kpiConfiguration = kpiConfiguration;
		this.dealer = dealer;
		this.country = country;
		this.resultDate = resultDate;
		this.result = result;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public KpiConfiguration getKpiConfiguration() {
		return this.kpiConfiguration;
	}

	public void setKpiConfiguration(KpiConfiguration kpiConfiguration) {
		this.kpiConfiguration = kpiConfiguration;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Date getResultDate() {
		return this.resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public Double getResult() {
		return this.result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

}