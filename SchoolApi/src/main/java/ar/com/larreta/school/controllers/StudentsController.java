package ar.com.larreta.school.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.annotations.PerformanceMonitor;
import ar.com.larreta.school.business.students.StudentsCodeExists;
import ar.com.larreta.school.business.students.StudentsCreateBusiness;
import ar.com.larreta.school.business.students.StudentsDeleteBusiness;
import ar.com.larreta.school.business.students.StudentsLoadBusiness;
import ar.com.larreta.school.business.students.StudentsUpdateBusiness;
import ar.com.larreta.school.messages.CodeExistData;
import ar.com.larreta.school.messages.CodeExistResult;
import ar.com.larreta.school.messages.StudentData;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.controllers.HelpConfig;
import ar.com.larreta.stepper.controllers.ParentController;
import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.stepper.messages.Message;
import ar.com.larreta.stepper.messages.Request;
import ar.com.larreta.stepper.messages.Response;

@RestController
@RequestMapping(value=StudentsController.ROOT_MAP)
@Validated
public class StudentsController extends ParentController<StudentData, LoadBody<StudentData>> {

	public static final String EXIST_CODE = "/existCode";

	public static final String ROOT_MAP = "/students";
	
	@Autowired
	private StudentsCodeExists codeExists;

	@Configuration
	public class Help extends HelpConfig<StudentData, LoadBody<StudentData>> {

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
		
		@Bean(name=ROOT_MAP + EXIST_CODE)
		public Message getExistCode() {
			Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
			CodeExistData existData = applicationContext.getBean(CodeExistData.class);
			instanceJsonables(existData);
			request.setBody(existData);
			return request;
		}
		
	}
	
	@Autowired @Qualifier(StudentsCreateBusiness.BUSINESS_NAME)
	@Override
	public void setCreateBusiness(Step createBusiness) {
		this.createBusiness = createBusiness;
	}

	@Autowired @Qualifier(StudentsUpdateBusiness.BUSINESS_NAME)
	@Override
	public void setUpdateBusiness(Step updateBusiness) {
		this.updateBusiness = updateBusiness;
	}

	@Autowired @Qualifier(StudentsDeleteBusiness.BUSINESS_NAME)
	@Override
	public void setDeleteBusiness(Step deleteBusiness) {
		this.deleteBusiness = deleteBusiness;
	}

	@Autowired @Qualifier(StudentsLoadBusiness.BUSINESS_NAME)
	@Override
	public void setLoadBusiness(Step loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

	
	@PerformanceMonitor
	@RequestMapping(value = EXIST_CODE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CodeExistResult> existCodePost(@Valid @RequestBody Request<CodeExistData> request, Errors errors) throws Exception{
		Response<CodeExistResult> response = applicationContext.getBean(Response.class);;
		CodeExistResult body = (CodeExistResult) codeExists.execute(request.getBody(), null, null);
		response.setBody(body);
		return response;
	}
	
}
