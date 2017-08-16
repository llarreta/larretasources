package ar.com.larreta.mystic.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "address")
public class Address extends ar.com.larreta.mystic.model.Entity {
	
	private String 		street;
	private Integer 	number;
	private Integer 	floor;
	private String 		department;
	private Country 	country;
	private State 		state;
	private Location	location;
	private String 		postalCode;
	
	@Basic @Column (name="postalCode")
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@Basic @Column (name="street")
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Basic @Column (name="number")
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Basic @Column (name="floor")
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
	@Basic @Column (name="department")
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Country.class)
	@JoinColumn (name="idCountry")
	public Country getCountry() {
		if (getState()!=null){
			country = getState().getCountry();
		}
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=State.class)
	@JoinColumn (name="idState")
	public State getState() {
		if (getLocation()!=null){
			state = getLocation().getState();
		}
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Location.class)
	@JoinColumn (name="idLocation")	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	

}
