package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.controllers.ParentController;
import ar.com.larreta.school.business.StudentsCreateBusiness;
import ar.com.larreta.school.business.StudentsDeleteBusiness;
import ar.com.larreta.school.business.StudentsLoadBusiness;
import ar.com.larreta.school.business.StudentsUpdateBusiness;
import ar.com.larreta.school.messages.LoadStudentsBody;
import ar.com.larreta.school.messages.UpdateStudentBody;

@RestController
@RequestMapping(value="/students")
@Validated
public class StudentsController extends ParentController<UpdateStudentBody, LoadStudentsBody> {

	@Autowired @Qualifier(StudentsCreateBusiness.BUSINESS_NAME)
	@Override
	public void setCreateBusiness(Business createBusiness) {
		this.createBusiness = createBusiness;
	}

	@Autowired @Qualifier(StudentsUpdateBusiness.BUSINESS_NAME)
	@Override
	public void setUpdateBusiness(Business updateBusiness) {
		this.updateBusiness = updateBusiness;
	}

	@Autowired @Qualifier(StudentsDeleteBusiness.BUSINESS_NAME)
	@Override
	public void setDeleteBusiness(Business deleteBusiness) {
		this.deleteBusiness = deleteBusiness;
	}

	@Autowired @Qualifier(StudentsLoadBusiness.BUSINESS_NAME)
	@Override
	public void setLoadBusiness(Business loadBusiness) {
		this.loadBusiness = loadBusiness;
	}


}
