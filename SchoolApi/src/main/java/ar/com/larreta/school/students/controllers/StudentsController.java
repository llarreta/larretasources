package ar.com.larreta.school.students.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.prototypes.ObjectJSONable;
import ar.com.larreta.prototypes.impl.PersonImpl;
import ar.com.larreta.rest.controllers.ParentController;
import ar.com.larreta.rest.exceptions.BadInputException;
import ar.com.larreta.rest.exceptions.RestException;
import ar.com.larreta.rest.messages.DeleteRequest;
import ar.com.larreta.rest.messages.DeleteResponse;
import ar.com.larreta.rest.messages.LoadRequest;
import ar.com.larreta.rest.messages.LoadResponse;
import ar.com.larreta.rest.messages.UpdateRequest;
import ar.com.larreta.rest.messages.UpdateResponse;
import ar.com.larreta.school.students.business.StudentsCreateBusiness;
import ar.com.larreta.school.students.business.StudentsDeleteBusiness;
import ar.com.larreta.school.students.business.StudentsLoadBusiness;
import ar.com.larreta.school.students.business.StudentsUpdateBusiness;
import ar.com.larreta.school.students.validators.StudentsCreateRequestValidator;

@RestController
@RequestMapping(value="/students")
@Validated
public class StudentsController extends ParentController<PersonImpl> {

	@Autowired
	private StudentsCreateBusiness createBusiness;

	@Autowired
	private StudentsUpdateBusiness updateBusiness;
	
	@Autowired
	private StudentsDeleteBusiness deleteBusiness;
	
	@Autowired
	private StudentsLoadBusiness loadBusiness;
	
	/*@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    binder.setValidator(new StudentsCreateRequestValidator());
	}*/
	
	
	@Override
	public UpdateResponse executeCreate(UpdateRequest<PersonImpl> request) throws RestException {
		UpdateResponse response = new UpdateResponse();
		
		PersonImpl person = request.getTarget();
		if (person==null){
			throw new BadInputException();
		}
		createBusiness.execute(person);
		
		response.setTargetId(person.getId());
		return response;
	}

	@Override
	public UpdateResponse executeUpdate(UpdateRequest<PersonImpl> request) throws RestException {
		UpdateResponse response = new UpdateResponse();
		
		PersonImpl person = request.getTarget();
		if (person==null){
			throw new BadInputException();
		}
		updateBusiness.execute(person);
		
		response.setTargetId(person.getId());
		return response;
	}

	@Override
	public DeleteResponse executeDelete(DeleteRequest request) throws RestException {
		DeleteResponse response = new DeleteResponse();
		
		Long targetId = request.getTargetId();
		if (targetId==null){
			throw new BadInputException();
		}
		
		deleteBusiness.execute(new ObjectJSONable(targetId));
		
		response.setTargetId(targetId);
		return response;
	}
	

	@Override
	public LoadResponse<PersonImpl> executeLoad(LoadRequest<PersonImpl> request) throws RestException {

		LoadResponse<PersonImpl> response = new LoadResponse<PersonImpl>();
		
		JSONable persons = loadBusiness.execute(request.getFilter());
		response.setResult((Collection<PersonImpl>) persons);
		
		return response;
	}


}
