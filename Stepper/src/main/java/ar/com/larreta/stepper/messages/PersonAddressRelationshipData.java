package ar.com.larreta.stepper.messages;

import javax.validation.Valid;

import ar.com.larreta.mystic.model.AddressType;
import ar.com.larreta.mystic.model.Person;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;

public class PersonAddressRelationshipData extends JSONable {

	@Exist(message="person.inexistent", entityType=Person.class)
	@NotNull(message="person.required")
	private Long 		person;
	
	@Exist(message="addressType.inexistent", entityType=AddressType.class)
	@NotNull(message="addressType.required")
	private Long 		addressType;

	@NotNull(message="address.required")
	@Valid
	private AddressData address;
	
	public AddressData getAddress() {
		return address;
	}
	public void setAddress(AddressData address) {
		this.address = address;
	}
	public Long getPerson() {
		return person;
	}
	public void setPerson(Long person) {
		this.person = person;
	}
	public Long getAddressType() {
		return addressType;
	}
	public void setAddressType(Long addressType) {
		this.addressType = addressType;
	}
}
