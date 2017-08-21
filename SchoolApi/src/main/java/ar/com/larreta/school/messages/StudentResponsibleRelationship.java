package ar.com.larreta.school.messages;

import javax.validation.Valid;

import ar.com.larreta.school.persistence.ResponsibleType;
import ar.com.larreta.stepper.messages.AddressData;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;

public class StudentResponsibleRelationship extends JSONable {

	@Exist(message="responsibleType.inexistent", entityType=ResponsibleType.class)
	@NotNull(message="responsibleType.required")
	private Long 		responsibleType;

	@NotNull(message="address.required")
	@Valid
	private AddressData address;
	
	
}
