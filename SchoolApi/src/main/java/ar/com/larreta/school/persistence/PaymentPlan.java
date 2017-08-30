package ar.com.larreta.school.persistence;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.ParametricEntity;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "paymentPlan")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE PaymentPlan SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class PaymentPlan extends ParametricEntity {

	private Set<Obligation> obligations;
	private Set<Student> students;
	private Set<Discount> discounts;
	
	@OneToMany (mappedBy="paymentPlan", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Discount.class)
	@Where(clause="deleted IS NULL")
	public Set<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}
	
	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Student.class, cascade=CascadeType.ALL )
	@JoinTable(name = "studentPaymentPlan", joinColumns = { @JoinColumn(name = "idPaymentPlan") }, 
		inverseJoinColumns = { @JoinColumn(name = "idStudent") })
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	@OneToMany (mappedBy="paymentPlan", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Obligation.class)
	@Where(clause="deleted IS NULL")
	public Set<Obligation> getObligations() {
		return obligations;
	}
	public void setObligations(Set<Obligation> obligations) {
		this.obligations = obligations;
		writeToAll(obligations, "paymentPlan", this);
	}
	
	

}
