package ar.com.larreta.school.persistence;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Person;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "responsible")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Responsible SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Responsible extends Person {

	private String cbu;
	private String cuil;
	private String profession;
	private Set<Student> students;

	@Basic @Column (name="profession")
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@Basic @Column (name="cbu")
	public String getCbu() {
		return cbu;
	}
	public void setCbu(String cbu) {
		this.cbu = cbu;
	}
	
	@Basic @Column (name="cuil")
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	
	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Student.class)
	@JoinTable(name = "studentResponsible", joinColumns = { @JoinColumn(name = "idResponsible") }, 
		inverseJoinColumns = { @JoinColumn(name = "idStudent") })
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	
}
