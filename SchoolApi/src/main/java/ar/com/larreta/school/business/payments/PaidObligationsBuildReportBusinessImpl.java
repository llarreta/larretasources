package ar.com.larreta.school.business.payments;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ar.com.larreta.reports.PDF;
import ar.com.larreta.rest.business.impl.BusinessImpl;

@Service(PaidObligationBuildReportBusiness.BUSINESS_NAME)
@Transactional
public class PaidObligationsBuildReportBusinessImpl extends BusinessImpl implements PaidObligationBuildReportBusiness {

	@Value("classpath:ar/com/larreta/school/reports/paidObligations.jrxml")
	private Resource paidObligationReportTemplate;
	
	@Override
	public Serializable execute(Serializable input) throws Exception {
		
		PDF pdf = applicationContext.getBean(PDF.class);
		ByteArrayOutputStream stream =  pdf.getOutputStream(paidObligationReportTemplate);
		
		return null;
	}

}
