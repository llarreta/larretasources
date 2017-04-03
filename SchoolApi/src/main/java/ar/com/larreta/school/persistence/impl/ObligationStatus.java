package ar.com.larreta.school.persistence.impl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.persistence.model.impl.PersistenceEntityImpl;

@Entity
@Table(name = "obligationStatus")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE ObligationStatus SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class ObligationStatus extends PersistenceEntityImpl {

	private Obligation obligation;
	private Student student;
	private Boolean paidOff;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Obligation.class)
	@JoinColumn (name="idObligation")
	public Obligation getObligation() {
		return obligation;
	}
	public void setObligation(Obligation obligation) {
		this.obligation = obligation;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Student.class)
	@JoinColumn (name="idStudent")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@Basic @Column (name="paidOff")
	public Boolean getPaidOff() {
		return paidOff;
	}
	public void setPaidOff(Boolean paidOff) {
		this.paidOff = paidOff;
	}
	
	
}
