package ar.com.larreta.mystic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "country")
public class Country extends ParametricEntity {

	private Set<State> 	states;
	private Set<Person> citizens;

	@OneToMany (mappedBy="nationality", fetch=FetchType.LAZY, targetEntity=Person.class)
	@Where(clause="deleted IS NULL")
	public Set<Person> getCitizens() {
		return citizens;
	}

	public void setCitizens(Set<Person> citizens) {
		this.citizens = citizens;
	}

	@OneToMany (mappedBy="country", fetch=FetchType.LAZY, targetEntity=State.class)
	@Where(clause="deleted IS NULL")
	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}
	
	
	
}
