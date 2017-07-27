package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.school.business.courses.LevelsLoadBusiness;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.controllers.HelpConfig;
import ar.com.larreta.stepper.controllers.LoadAllController;
import ar.com.larreta.stepper.controllers.ParentController;
import ar.com.larreta.stepper.messages.Message;

@RestController
@RequestMapping(value=LevelsController.ROOT_MAP)
@Validated
public class LevelsController extends LoadAllController {

	public static final String ROOT_MAP = "/levels";

	@Configuration
	public class Help extends HelpConfig {

		@Bean(name=ROOT_MAP + ParentController.LOAD)
		@Override
		public Message getNotRequestNeeded() {
			return super.getNotRequestNeeded();
		}
		
	}
	
	@Autowired @Qualifier(LevelsLoadBusiness.BUSINESS_NAME)
	public void setLoadBusiness(Step loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

}
