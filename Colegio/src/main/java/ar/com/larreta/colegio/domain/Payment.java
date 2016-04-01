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
@Table(name = "payment")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Payment SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Payment extends ar.com.larreta.commons.domain.Entity {

	private Double value;
	private Person personWhoPays;
	private Set<PaymentUnit> paymentUnits;
	private Date paymentDate;
	
	@Basic @Column (name="value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Product.class)
	@JoinColumn (name="idPersonWhoPays")
	public Person getPersonWhoPays() {
		return personWhoPays;
	}
	public void setPersonWhoPays(Person personWhoPays) {
		this.personWhoPays = personWhoPays;
	}

	@OneToMany (mappedBy="payment", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PaymentUnit.class)
	@Where(clause="deleted IS NULL")
	public Set<PaymentUnit> getPaymentUnits() {
		return paymentUnits;
	}
	public void setPaymentUnits(Set<PaymentUnit> paymentUnits) {
		this.paymentUnits = paymentUnits;
	}
	
	@Basic @Column (name="paymentDate")
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
