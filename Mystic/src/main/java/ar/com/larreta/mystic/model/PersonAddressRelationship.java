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
@Table(name = "personAddress")
public class PersonAddressRelationship extends ar.com.larreta.mystic.model.Entity {

	private Person 		person;
	private Address 	address;
	private AddressType addressType;

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=AddressType.class)
	@JoinColumn (name="idAddressType")
	public AddressType getAddressType() {
		return addressType;
	}
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Person.class)
	@JoinColumn (name="idPerson")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Address.class)
	@JoinColumn (name="idAddress")
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
