package ar.com.larreta.mystic.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "location")
public class Location extends ParametricEntity {

	private State state;

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=State.class)
	@JoinColumn (name="idState")
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
