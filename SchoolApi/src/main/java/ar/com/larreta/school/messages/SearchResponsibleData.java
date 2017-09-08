package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class SearchResponsibleData extends Body {
	
	private String name;
	private String surname;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
