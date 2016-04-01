package ar.com.larreta.colegio.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "paymentPlan")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE PaymentPlan SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class PaymentPlan extends ar.com.larreta.commons.domain.Entity {

	private String name;
	private Set<Obligation> obligations;
	
	@Basic @Column (name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany (mappedBy="paymentPlan", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Obligation.class)
	@Where(clause="deleted IS NULL")
	public Set<Obligation> getObligations() {
		return obligations;
	}
	public void setObligations(Set<Obligation> obligations) {
		this.obligations = obligations;
	}
	
	

}
