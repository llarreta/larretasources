package ar.com.larreta.school.messages;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import ar.com.larreta.school.persistence.ResponsibleType;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;

@Component
public class StudentResponsibleRelationshipData extends JSONable {

	@Exist(message="responsibleType.inexistent", entityType=ResponsibleType.class)
	@NotNull(message="responsibleType.required")
	private Long 		responsibleType;

	@NotNull(message="address.required")
	@Valid
	private ResponsibleData responsible;

	public Long getResponsibleType() {
		return responsibleType;
	}

	public void setResponsibleType(Long responsibleType) {
		this.responsibleType = responsibleType;
	}

	public ResponsibleData getResponsible() {
		return responsible;
	}

	public void setResponsible(ResponsibleData responsible) {
		this.responsible = responsible;
	}
	
	
}
