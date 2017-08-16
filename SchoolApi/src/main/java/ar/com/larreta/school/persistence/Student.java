package ar.com.larreta.school.persistence;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Person;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "student")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Student SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Student extends Person {
	
	private Course course;
	private Set<PaymentPlan> paymentPlans;
	private Set<ObligationStatus> obligationsStatus;
	private Set<Responsible> responsibles;
	private String code;
	
	@Basic @Column (name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Course.class)
	@JoinColumn (name="idCourse")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	@ManyToMany (fetch=FetchType.LAZY, targetEntity=PaymentPlan.class, cascade=CascadeType.PERSIST )
	@JoinTable(name = "studentPaymentPlan", joinColumns = { @JoinColumn(name = "idStudent") }, 
		inverseJoinColumns = { @JoinColumn(name = "idPaymentPlan") })
	public Set<PaymentPlan> getPaymentPlans() {
		return paymentPlans;
	}
	public void setPaymentPlans(Set<PaymentPlan> paymentPlans) {
		this.paymentPlans = paymentPlans;
	}
	
	@OneToMany (mappedBy="student", fetch=FetchType.LAZY, targetEntity=ObligationStatus.class)
	@Where(clause="deleted IS NULL")
	public Set<ObligationStatus> getObligationsStatus() {
		return obligationsStatus;
	}
	public void setObligationsStatus(Set<ObligationStatus> obligationsStatus) {
		this.obligationsStatus = obligationsStatus;
	}
	
	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Responsible.class)
	@JoinTable(name = "studentResponsible", joinColumns = { @JoinColumn(name = "idStudent") }, 
		inverseJoinColumns = { @JoinColumn(name = "idResponsible") })
	public Set<Responsible> getResponsibles() {
		return responsibles;
	}
	public void setResponsibles(Set<Responsible> responsibles) {
		this.responsibles = responsibles;
	}

}
