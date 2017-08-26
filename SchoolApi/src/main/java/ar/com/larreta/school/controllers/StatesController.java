package ar.com.larreta.school.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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
import ar.com.larreta.school.business.courses.StatesLoadBusiness;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.controllers.HelpConfig;
import ar.com.larreta.stepper.controllers.ParentController;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.stepper.messages.Message;
import ar.com.larreta.stepper.messages.RelatedBody;
import ar.com.larreta.stepper.messages.Request;
import ar.com.larreta.stepper.messages.Response;
import ar.com.larreta.stepper.messages.TargetedBody;

@RestController
@RequestMapping(value=StatesController.ROOT_MAP)
@Validated
public class StatesController {
	
	public static final String ROOT_MAP = "/states";
	
	public static final String LOAD = "/load";
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	protected Step loadBusiness;
	
	public Step getLoadBusiness() {
		return loadBusiness;
	}

	@Configuration
	public class Help extends HelpConfig<TargetedBody, RelatedBody> {

		@Bean(name=ROOT_MAP + ParentController.LOAD)
		@Override
		public Message getLoadHelp() {
			return super.getLoadHelp();
		}
		
	}
	
	@Autowired @Qualifier(StatesLoadBusiness.BUSINESS_NAME)
	public void setLoadBusiness(Step loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

	@PerformanceMonitor
	@RequestMapping(value = LOAD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<LoadBody<JSONable>> relatedLoadPost(@Valid @RequestBody Request<RelatedBody> request, Errors errors) throws Exception{
		Response<LoadBody<JSONable>> response = applicationContext.getBean(Response.class);;
		LoadBody body = (LoadBody) loadBusiness.execute(request.getBody(), null, null);
		response.setBody(body);
		return response;
	}
	
}
