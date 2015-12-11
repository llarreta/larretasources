package ar.com.larreta.prode.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "team")
public class Team extends ar.com.larreta.commons.domain.Entity{
	
	private String name;
	private String library;
	private String shield;

	@Transient
	public Boolean getHaveLogo(){
		return ((library!=null) && (shield!=null));
	}
	
	@Basic
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the library
	 */
	@Basic
	public String getLibrary() {
		return library;
	}

	/**
	 * @param library the library to set
	 */
	public void setLibrary(String library) {
		this.library = library;
	}

	/**
	 * @return the shield
	 */
	@Basic
	public String getShield() {
		return shield;
	}

	/**
	 * @param shield the shield to set
	 */
	public void setShield(String shield) {
		this.shield = shield;
	}

	
	
	
	
}
