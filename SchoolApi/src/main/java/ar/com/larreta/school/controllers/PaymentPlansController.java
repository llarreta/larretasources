package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.larreta.school.business.paymentPlans.PaymentPlansCreateBusiness;
import ar.com.larreta.school.business.paymentPlans.PaymentPlansDeleteBusiness;
import ar.com.larreta.school.business.paymentPlans.PaymentPlansLoadBusiness;
import ar.com.larreta.school.business.paymentPlans.PaymentPlansUpdateBusiness;
import ar.com.larreta.school.messages.DetailData;
import ar.com.larreta.school.messages.LittleDetailData;
import ar.com.larreta.school.messages.LoadStudentData;
import ar.com.larreta.school.messages.ObligationData;
import ar.com.larreta.school.messages.UpdatePaymentPlansBody;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.controllers.HelpConfig;
import ar.com.larreta.stepper.controllers.ParentController;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.stepper.messages.Message;
import ar.com.larreta.stepper.messages.Request;

@RestController
@RequestMapping(value=PaymentPlansController.ROOT_MAP)
@Validated
public class PaymentPlansController extends ParentController<UpdatePaymentPlansBody, LoadBody<LoadStudentData>> {
	
	public static final String ROOT_MAP = "/paymentPlans";
	
	@Configuration
	public class Help extends HelpConfig<UpdatePaymentPlansBody, LoadBody<LoadStudentData>> {

		@Bean(name=ROOT_MAP + ParentController.CREATE)
		@Override
		public Message getCreateHelp() {
			return super.getCreateHelp();
		}

		private ObligationData getObligationData() {
			ObligationData obligationData = applicationContext.getBean(ObligationData.class);
			obligationData.setDetails(new JSONableCollection<DetailData>());
			obligationData.getDetails().add(getDetailData());
			obligationData.getDetails().add(getDetailData());
			return obligationData;
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
	public void setCreateBusiness(Step createBusiness) {
		this.createBusiness = createBusiness;
	}

	@Autowired @Qualifier(PaymentPlansUpdateBusiness.BUSINESS_NAME)
	@Override
	public void setUpdateBusiness(Step updateBusiness) {
		this.updateBusiness = updateBusiness;
	}

	@Autowired @Qualifier(PaymentPlansDeleteBusiness.BUSINESS_NAME)
	@Override
	public void setDeleteBusiness(Step deleteBusiness) {
		this.deleteBusiness = deleteBusiness;
	}

	@Autowired @Qualifier(PaymentPlansLoadBusiness.BUSINESS_NAME)
	@Override
	public void setLoadBusiness(Step loadBusiness) {
		this.loadBusiness = loadBusiness;
	}

}
