package ar.com.larreta.school.students.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.com.larreta.prototypes.impl.PersonImpl;
import ar.com.larreta.rest.messages.UpdateRequest;

public class StudentsCreateRequestValidator implements Validator {

	@Override
	public boolean supports(Class type) {
		return UpdateRequest.class.equals(type);
	}

	@Override
	public void validate(Object target, Errors paramErrors) {
		UpdateRequest<PersonImpl> request = (UpdateRequest<PersonImpl>) target;
		request.getTarget();
	}

}
