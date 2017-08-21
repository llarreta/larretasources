package ar.com.larreta.stepper.messages;

import org.springframework.stereotype.Component;

@Component
public class EmailData extends JSONable {
	
	private Long 		id;
	private String 		address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
