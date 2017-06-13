package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.controllers.ParentController;
import ar.com.larreta.rest.messages.LoadBody;
import ar.com.larreta.school.business.courses.CoursesCreateBusiness;
import ar.com.larreta.school.business.courses.CoursesDeleteBusiness;
import ar.com.larreta.school.business.courses.CoursesLoadBusiness;
import ar.com.larreta.school.business.courses.CoursesUpdateBusiness;
import ar.com.larreta.school.messages.LoadStudentsData;
import ar.com.larreta.school.messages.UpdateCourseBody;

@RestController
@RequestMapping(value="/courses")
@Validated
public class CoursesController extends ParentController<UpdateCourseBody, LoadBody<LoadStudentsData>> {

	@Autowired @Qualifier(CoursesCreateBusiness.BUSINESS_NAME)
	@Override
	public void setCreateBusiness(Business createBusiness) {
		this.createBusiness = createBusiness;
	}

	@Autowired @Qualifier(CoursesUpdateBusiness.BUSINESS_NAME)
	@Override
	public void setUpdateBusiness(Business updateBusiness) {
		this.updateBusiness = updateBusiness;
	}

	@Autowired @Qualifier(CoursesDeleteBusiness.BUSINESS_NAME)
	@Override
	public void setDeleteBusiness(Business deleteBusiness) {
		this.deleteBusiness = deleteBusiness;
	}

	@Autowired @Qualifier(CoursesLoadBusiness.BUSINESS_NAME)
	@Override
	public void setLoadBusiness(Business loadBusiness) {
		this.loadBusiness = loadBusiness;
	}
	
}
