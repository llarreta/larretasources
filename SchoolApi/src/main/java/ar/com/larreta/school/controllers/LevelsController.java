package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.controllers.LoadAllController;
import ar.com.larreta.school.business.courses.LevelsLoadBusiness;

@RestController
@RequestMapping(value="/levels")
@Validated
public class LevelsController extends LoadAllController {

	@Autowired @Qualifier(LevelsLoadBusiness.BUSINESS_NAME)
	public void setLoadBusiness(Business loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

}
