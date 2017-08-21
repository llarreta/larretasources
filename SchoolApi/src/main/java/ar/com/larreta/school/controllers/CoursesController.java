package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.school.business.courses.CoursesCreateBusiness;
import ar.com.larreta.school.business.courses.CoursesDeleteBusiness;
import ar.com.larreta.school.business.courses.CoursesLoadBusiness;
import ar.com.larreta.school.business.courses.CoursesUpdateBusiness;
import ar.com.larreta.school.messages.CourseData;
import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.controllers.HelpConfig;
import ar.com.larreta.stepper.controllers.ParentController;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.stepper.messages.Message;

@RestController
@RequestMapping(value=CoursesController.ROOT_MAP)
@Validated
public class CoursesController extends ParentController<UpdateCourseBody, LoadBody<CourseData>> {

	public static final String ROOT_MAP = "/courses";

	@Configuration
	public class Help extends HelpConfig<UpdateCourseBody, LoadBody<CourseData>> {

		@Bean(name=ROOT_MAP + ParentController.CREATE)
		@Override
		public Message getCreateHelp() {
			return super.getCreateHelp();
		}

		@Bean(name=ROOT_MAP + ParentController.UPDATE)
		@Override
		public Message getUpdateHelp() {
			return super.getUpdateHelp();
		}

		@Bean(name=ROOT_MAP + ParentController.DELETE)
		@Override
		public Message getTargetedRequest() {
			return super.getTargetedRequest();
		}

		@Bean(name=ROOT_MAP + ParentController.LOAD)
		@Override
		public Message getLoadHelp() {
			return super.getLoadHelp();
		}
		
	}
	
	@Autowired @Qualifier(CoursesCreateBusiness.BUSINESS_NAME)
	@Override
	public void setCreateBusiness(Step createBusiness) {
		this.createBusiness = createBusiness;
	}

	@Autowired @Qualifier(CoursesUpdateBusiness.BUSINESS_NAME)
	@Override
	public void setUpdateBusiness(Step updateBusiness) {
		this.updateBusiness = updateBusiness;
	}

	@Autowired @Qualifier(CoursesDeleteBusiness.BUSINESS_NAME)
	@Override
	public void setDeleteBusiness(Step deleteBusiness) {
		this.deleteBusiness = deleteBusiness;
	}

	@Autowired @Qualifier(CoursesLoadBusiness.BUSINESS_NAME)
	@Override
	public void setLoadBusiness(Step loadBusiness) {
		this.loadBusiness = loadBusiness;
	}
	
}
