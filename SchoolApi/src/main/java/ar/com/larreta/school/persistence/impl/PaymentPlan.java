package ar.com.larreta.school.persistence.impl;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.persistence.model.impl.PersistenceParametricEntityImpl;

@Entity
@Table(name = "paymentPlan")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE PaymentPlan SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class PaymentPlan extends PersistenceParametricEntityImpl {

	private Set<Obligation> obligations;
	
	@OneToMany (mappedBy="paymentPlan", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Obligation.class)
	@Where(clause="deleted IS NULL")
	public Set<Obligation> getObligations() {
		return obligations;
	}
	public void setObligations(Set<Obligation> obligations) {
		this.obligations = obligations;
	}
	
	

}
