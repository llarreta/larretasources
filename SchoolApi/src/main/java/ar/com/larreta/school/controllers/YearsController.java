package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.controllers.HelpConfig;
import ar.com.larreta.rest.controllers.LoadAllController;
import ar.com.larreta.rest.controllers.ParentController;
import ar.com.larreta.rest.messages.Message;
import ar.com.larreta.school.business.courses.YearsLoadBusiness;

@RestController
@RequestMapping(value=YearsController.ROOT_MAP)
@Validated
public class YearsController extends LoadAllController{

	public static final String ROOT_MAP = "/years";

	@Configuration
	public class Help extends HelpConfig {

		@Bean(name=ROOT_MAP + ParentController.LOAD)
		@Override
		public Message getNotRequestNeeded() {
			return super.getNotRequestNeeded();
		}
		
	}
	
	@Autowired @Qualifier(YearsLoadBusiness.BUSINESS_NAME)
	public void setLoadBusiness(Business loadBusiness) {
		this.loadBusiness = loadBusiness;
	}
	
}
