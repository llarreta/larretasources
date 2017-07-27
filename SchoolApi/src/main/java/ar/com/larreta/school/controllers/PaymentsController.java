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

import ar.com.larreta.school.business.payments.ObligationsStatusBusiness;
import ar.com.larreta.school.business.payments.PaidObligationBuildReportBusiness;
import ar.com.larreta.school.business.payments.PayObligationBusiness;
import ar.com.larreta.school.business.payments.UnpaidObligationsBusiness;
import ar.com.larreta.school.messages.PayData;
import ar.com.larreta.stepper.messages.DownloadBody;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.stepper.messages.Request;
import ar.com.larreta.stepper.messages.Response;
import ar.com.larreta.stepper.messages.TargetedBody;

@RestController
@RequestMapping(value=PaymentsController.ROOT_MAP)
@Validated
public class PaymentsController {

	public static final String PAID_OBLIGATION_REPORT = "/paidObligationReport";
	public static final String OBLIGATIONS_STATUS 		= "/obligationsStatus";
	public static final String PAY 						= "/pay";
	public static final String UNPAID_OBLIGATIONS 		= "/unpaidObligations";
	public static final String ROOT_MAP 				= "/payments";
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private UnpaidObligationsBusiness unpaidObligationsBusiness;
	@Autowired
	private PayObligationBusiness payObligationBusiness;
	@Autowired
	private ObligationsStatusBusiness obligationsStatusBusiness;
	@Autowired
	private PaidObligationBuildReportBusiness paidObligationBuildReportBusiness;

	@RequestMapping(value = PAID_OBLIGATION_REPORT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE) 
	public Response<DownloadBody> paidObligationReport(@Valid @RequestBody Request<TargetedBody> request, Errors errors)  throws Exception{
		String fileToDownload = (String) paidObligationBuildReportBusiness.execute(request.getBody(), null, null);
		
		Response<DownloadBody> response = applicationContext.getBean(Response.class);
		DownloadBody responseBody = applicationContext.getBean(DownloadBody.class);;
		response.setBody(responseBody);
		responseBody.setFileContent(fileToDownload);
		
		return response;
	}
	
	@RequestMapping(value = OBLIGATIONS_STATUS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<LoadBody<JSONable>> obligationsStatusPost(@Valid @RequestBody Request<TargetedBody> request, Errors errors) throws Exception{
		Response<LoadBody<JSONable>> response = applicationContext.getBean(Response.class);
		LoadBody body = (LoadBody) obligationsStatusBusiness.execute(request.getBody(), null, null);
		response.setBody(body);
		return response;
	}
	
	@RequestMapping(value = UNPAID_OBLIGATIONS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<LoadBody<JSONable>> unpaidObligationsPost(@Valid @RequestBody Request<TargetedBody> request, Errors errors) throws Exception{
		Response<LoadBody<JSONable>> response = applicationContext.getBean(Response.class);
		LoadBody body = (LoadBody) unpaidObligationsBusiness.execute(request.getBody(), null, null);
		response.setBody(body);
		return response;
	}
	
	@RequestMapping(value = PAY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TargetedBody>  payPost(@Valid @RequestBody Request<PayData> request, Errors errors) throws Exception{
		Response<TargetedBody> response = applicationContext.getBean(Response.class);
		Long target = (Long) payObligationBusiness.execute(request.getBody(), null, null);
		TargetedBody responseBody = new TargetedBody();
		responseBody.setTarget(target);
		response.setBody(responseBody);
		return response;
	}
	
}
