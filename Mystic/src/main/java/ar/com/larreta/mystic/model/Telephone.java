package ar.com.larreta.mystic.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "telephone")
public class Telephone extends ar.com.larreta.mystic.model.Entity {
	
	private String 			number;
	
	@Basic @Column (name="number")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

}
