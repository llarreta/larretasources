package ar.com.larreta.school.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.LoadBody;
import ar.com.larreta.rest.messages.Request;
import ar.com.larreta.rest.messages.Response;
import ar.com.larreta.rest.messages.TargetedBody;
import ar.com.larreta.school.business.payments.PayObligationBusiness;
import ar.com.larreta.school.business.payments.UnpaidObligationsBusiness;
import ar.com.larreta.school.messages.PayData;

@RestController
@RequestMapping(value=PaymentsController.ROOT_MAP)
@Validated
public class PaymentsController {

	public static final String PAY = "/pay";

	public static final String UNPAID_OBLIGATIONS = "/unpaidObligations";

	public static final String ROOT_MAP = "/payments";
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private UnpaidObligationsBusiness unpaidObligationsBusiness;
	@Autowired
	private PayObligationBusiness payObligationBusiness;
	
	@RequestMapping(value = UNPAID_OBLIGATIONS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<LoadBody<JSONable>> unpaidObligationsPost(@Valid @RequestBody Request<TargetedBody> request, Errors errors) throws Exception{
		Response<LoadBody<JSONable>> response = applicationContext.getBean(Response.class);
		LoadBody body = (LoadBody) unpaidObligationsBusiness.execute(request.getBody());
		response.setBody(body);
		return response;
	}
	
	@RequestMapping(value = PAY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody>  payPost(@Valid @RequestBody Request<PayData> request, Errors errors) throws Exception{
		Response<TargetedBody> response = applicationContext.getBean(Response.class);
		Long target = (Long) payObligationBusiness.execute(request.getBody());
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(target);
		response.setBody(responseBody);
		return response;
	}
	
}
