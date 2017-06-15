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
import ar.com.larreta.rest.controllers.ParentController;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.rest.messages.LoadBody;
import ar.com.larreta.rest.messages.Message;
import ar.com.larreta.rest.messages.Request;
import ar.com.larreta.school.business.paymentPlans.PaymentPlansCreateBusiness;
import ar.com.larreta.school.business.students.StudentsDeleteBusiness;
import ar.com.larreta.school.business.students.StudentsLoadBusiness;
import ar.com.larreta.school.business.students.StudentsUpdateBusiness;
import ar.com.larreta.school.messages.DetailData;
import ar.com.larreta.school.messages.LittleDetailData;
import ar.com.larreta.school.messages.LoadStudentData;
import ar.com.larreta.school.messages.LoadStudentsData;
import ar.com.larreta.school.messages.ObligationData;
import ar.com.larreta.school.messages.PriceData;
import ar.com.larreta.school.messages.UpdatePaymentPlansBody;

@RestController
@RequestMapping(value=PaymentPlansController.ROOT_MAP)
@Validated
public class PaymentPlansController extends ParentController<UpdatePaymentPlansBody, LoadBody<LoadStudentData>> {
	
	public static final String ROOT_MAP = "/paymentPlans";
	
	@Configuration
	public class Help extends HelpConfig<UpdatePaymentPlansBody, LoadBody<LoadStudentsData>> {

		@Bean(name=ROOT_MAP + ParentController.CREATE)
		@Override
		public Message getCreateHelp() {
			Request<UpdatePaymentPlansBody> request = (Request<UpdatePaymentPlansBody>) super.getCreateHelp();
			JSONableCollection<ObligationData> obligations = new JSONableCollection<>();
			request.getBody().setObligations(new JSONableCollection<ObligationData>());
			request.getBody().getObligations().add(getObligationData());
			request.getBody().getObligations().add(getObligationData());
			return request;
		}

		private ObligationData getObligationData() {
			ObligationData obligationData = applicationContext.getBean(ObligationData.class);
			obligationData.setPrices(new JSONableCollection<PriceData>());
			obligationData.getPrices().add(getPriceData());
			obligationData.getPrices().add(getPriceData());
			return obligationData;
		}

		private PriceData getPriceData() {
			PriceData priceData = applicationContext.getBean(PriceData.class);
			priceData.setDetails(new JSONableCollection<DetailData>());
			priceData.getDetails().add(getDetailData());
			priceData.getDetails().add(getDetailData());
			return priceData;
		}

		private DetailData getDetailData() {
			DetailData detailData = applicationContext.getBean(DetailData.class);
			detailData.setLittleDetails(new JSONableCollection<LittleDetailData>());
			detailData.getLittleDetails().add(applicationContext.getBean(LittleDetailData.class));
			detailData.getLittleDetails().add(applicationContext.getBean(LittleDetailData.class));
			return detailData;
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

	@Autowired @Qualifier(PaymentPlansCreateBusiness.BUSINESS_NAME)
	@Override
	public void setCreateBusiness(Business createBusiness) {
		this.createBusiness = createBusiness;
	}

	@Autowired @Qualifier(StudentsUpdateBusiness.BUSINESS_NAME)
	@Override
	public void setUpdateBusiness(Business updateBusiness) {
		this.updateBusiness = updateBusiness;
	}

	@Autowired @Qualifier(StudentsDeleteBusiness.BUSINESS_NAME)
	@Override
	public void setDeleteBusiness(Business deleteBusiness) {
		this.deleteBusiness = deleteBusiness;
	}

	@Autowired @Qualifier(StudentsLoadBusiness.BUSINESS_NAME)
	@Override
	public void setLoadBusiness(Business loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

}
