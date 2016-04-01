package ar.com.larreta.colegio.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "course")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Course SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Course extends ar.com.larreta.commons.domain.Entity {

	private Level level;
	private Year year;
	private Division division;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Level.class)
	@JoinColumn (name="idLevel")
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Level.class)
	@JoinColumn (name="idYear")
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Level.class)
	@JoinColumn (name="idDivision")
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	
	
}
