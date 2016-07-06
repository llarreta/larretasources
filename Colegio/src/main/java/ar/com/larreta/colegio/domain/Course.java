package ar.com.larreta.colegio.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.domain.ParametricEntity;


@Entity
@Table(name = "course")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Course SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Course extends ar.com.larreta.commons.domain.Entity {
	
	private static final Logger LOGGER = Logger.getLogger(Course.class);

	private Level level;
	private Year year;
	private Division division;
	
	// Se sobrescriben los dos metodos siguientes para que determine que son iguales mediante los tres elementos que componen la relacion
	@Transient
	@Override
	public Long getId() {
		try {
			if (id==null && level!=null && year!=null && division!=null){
				id = (level.getId() * 2 * THREE_IDS_PK) + (year.getId() * THREE_IDS_PK) + division.getId(); 
			}
			return id;
		} catch (Exception e){
			LOGGER.error("Ocurrio un error obteniendo id", e);
		}
		return null;
	}
	
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
