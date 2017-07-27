package ar.com.larreta.school.persistence;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.ParametricEntity;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "course")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Course SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Course extends ar.com.larreta.mystic.model.Entity {
	
	private Level 		level;
	private Year 		year;
	private Division 	division;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Level.class)
	@JoinColumn (name="idLevel")
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Year.class)
	@JoinColumn (name="idYear")
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Division.class)
	@JoinColumn (name="idDivision")
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
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
