package ar.com.larreta.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.business.Business;

@RestController
@RequestMapping(value="/helloworld")
public class HelloWorldController extends ar.com.larreta.rest.controllers.ParentController {

	@Override
	public void setCreateBusiness(Business createBusiness) {
	}

	@Override
	public void setUpdateBusiness(Business updateBusiness) {
	}

	@Override
	public void setDeleteBusiness(Business deleteBusiness) {
	}

	@Override
	public void setLoadBusiness(Business loadBusiness) {
	}
}
