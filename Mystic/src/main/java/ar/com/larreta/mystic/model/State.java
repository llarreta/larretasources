package ar.com.larreta.mystic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "state")
public class State extends ParametricEntity {

	public Set<Location> locations;
	private Country country;

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Country.class)
	@JoinColumn (name="idCountry")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@OneToMany (mappedBy="state", fetch=FetchType.LAZY, targetEntity=Location.class)
	@Where(clause="deleted IS NULL")
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	
	
}
