package ar.com.larreta.stepper.messages;

import org.springframework.stereotype.Component;

@Component
public class TelephoneData extends JSONable {
	
	private Long 		id;
	private String 		number;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
