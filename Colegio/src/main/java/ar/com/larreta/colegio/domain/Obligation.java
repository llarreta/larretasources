package ar.com.larreta.colegio.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "obligation")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Obligation SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Obligation extends Product {
	
	private Date dueDate;
	private Set<Price> prices;
	private PaymentPlan paymentPlan;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=PaymentPlan.class)
	@JoinColumn (name="idPaymentPlan")
	public PaymentPlan getPaymentPlan() {
		return paymentPlan;
	}
	public void setPaymentPlan(PaymentPlan paymentPlan) {
		this.paymentPlan = paymentPlan;
	}
	
	@Basic @Column (name="dueDate")
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	@OneToMany (mappedBy="obligation", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Price.class)
	@Where(clause="deleted IS NULL")
	public Set<Price> getPrices() {
		return prices;
	}
	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}

	
}
