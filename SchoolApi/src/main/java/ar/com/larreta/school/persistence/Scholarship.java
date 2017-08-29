package ar.com.larreta.school.persistence;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.ExtendedParametricEntity;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "scholarship")
public class Scholarship extends ExtendedParametricEntity {

	private Set<Student> students;

	@OneToMany (mappedBy="scholarship", fetch=FetchType.LAZY, targetEntity=Student.class)
	@Where(clause="deleted IS NULL")
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
}
