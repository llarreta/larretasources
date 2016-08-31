package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerFinancialDTO implements Serializable{

	private String noCuenta;
	private Date terminationDate;
	private String state;
	private BigDecimal totalBalance;
	private BigDecimal overDue;
	private BigDecimal totalAmount;
	private BigDecimal last;

	public CustomerFinancialDTO() {
		super();
	}


	public CustomerFinancialDTO(String noCuenta, Date terminationDate,
			String state, BigDecimal totalBalance, BigDecimal overDue, BigDecimal totalAmount,
			BigDecimal last) {
		super();
		this.noCuenta = noCuenta;
		this.terminationDate = terminationDate;
		this.state = state;
		this.totalBalance = totalBalance;
		this.overDue = overDue;
		this.totalAmount = totalAmount;
		this.last = last;
	}


	public String getNoCuenta() {
		return noCuenta;
	}


	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getTerminationDate() {
		return terminationDate;
	}


	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public BigDecimal getTotalBalance() {
		return totalBalance;
	}


	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}


	public BigDecimal getOverDue() {
		return overDue;
	}


	public void setOverDue(BigDecimal overDue) {
		this.overDue = overDue;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


	public BigDecimal getLast() {
		return last;
	}


	public void setLast(BigDecimal last) {
		this.last = last;
	}


	private static final Long serialVersionUID = 6287143018842312729L;
	
}
