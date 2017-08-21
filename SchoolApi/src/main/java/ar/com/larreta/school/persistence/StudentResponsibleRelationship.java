package ar.com.larreta.school.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "studentResponsible")
public class StudentResponsibleRelationship extends ar.com.larreta.mystic.model.Entity {

	private Student student;
	
	private Responsible responsible;
	
	private ResponsibleType responsibleType;

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Student.class)
	@JoinColumn (name="idStudent")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Responsible.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idResponsible")
	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=ResponsibleType.class)
	@JoinColumn (name="idResponsibleType")
	public ResponsibleType getResponsibleType() {
		return responsibleType;
	}

	public void setResponsibleType(ResponsibleType responsibleType) {
		this.responsibleType = responsibleType;
	}
	
	
	
	
}
