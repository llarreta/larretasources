package ar.com.larreta.commons.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language extends ParametricEntity {

	private String abbreviation;

	@Basic
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
}
