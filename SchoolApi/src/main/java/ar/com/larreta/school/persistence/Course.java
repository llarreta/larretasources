package ar.com.larreta.school.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.persistence.model.ParametricEntity;

@Entity
@Table(name = "course")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Course SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Course extends ar.com.larreta.persistence.model.Entity {
	
	private Level 		level;
	private Year 		year;
	private Division 	division;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Level.class)
	@JoinColumn (name="idLevel")
	public Level getLevel() {
		return level;
	}
	public void setLevel(Serializable level) {
		this.level = (Level) level;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Year.class)
	@JoinColumn (name="idYear")
	public Year getYear() {
		return year;
	}
	public void setYear(Serializable year) {
		this.year = (Year) year;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Division.class)
	@JoinColumn (name="idDivision")
	public Division getDivision() {
		return division;
	}
	public void setDivision(Serializable division) {
		this.division = (Division) division;
	}
	
	@Override
	public String toString() {
		StringBuffer text = new StringBuffer();
		addText(text, level);
		addText(text, year);
		addText(text, division);
		return text.toString();
	}

	protected void addText(StringBuffer text, ParametricEntity entity) {
		if (entity!=null){
			text.append(" ");
			text.append(entity.getDescription());
		}
	}	
}
