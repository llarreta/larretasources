package ar.com.larreta.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.business.DocumentTypeLoadBusiness;

@RestController
@RequestMapping(value="/documentTypes")
@Validated
public class DocumentTypeController extends LoadAllController {

	@Autowired @Qualifier(DocumentTypeLoadBusiness.BUSINESS_NAME)
	public void setLoadBusiness(Business loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

}
