package ar.com.larreta.mystic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "personTelephone")
public class PersonTelephoneRelationship extends ar.com.larreta.mystic.model.Entity {

	private Person 				person;
	private Telephone			telephone;
	private TelephoneType 		telephoneType;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Person.class)
	@JoinColumn (name="idPerson")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Telephone.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idTelephone")
	public Telephone getTelephone() {
		return telephone;
	}
	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=TelephoneType.class)
	@JoinColumn (name="idTelephoneType")
	public TelephoneType getTelephoneType() {
		return telephoneType;
	}
	public void setTelephoneType(TelephoneType telephoneType) {
		this.telephoneType = telephoneType;
	}
	
}
