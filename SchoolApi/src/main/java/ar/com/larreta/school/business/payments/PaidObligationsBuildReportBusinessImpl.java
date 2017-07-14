package ar.com.larreta.school.business.payments;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.ReferencedEqual;
import ar.com.larreta.persistence.query.Query;
import ar.com.larreta.persistence.query.Select;
import ar.com.larreta.persistence.query.SelectInstruction;
import ar.com.larreta.reports.PDF;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.rest.messages.TargetedBody;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.tools.Base64;

@Service(PaidObligationBuildReportBusiness.BUSINESS_NAME)
@Transactional
public class PaidObligationsBuildReportBusinessImpl extends BusinessImpl implements PaidObligationBuildReportBusiness {

	@Value("classpath:ar/com/larreta/school/reports/paidObligations.jrxml")
	private Resource paidObligationReportTemplate;
	
	@Autowired
	private Base64 base64;
	
	@Override
	public Serializable execute(Serializable input) throws Exception {
		TargetedBody body = (TargetedBody) input;
		
		Query query = applicationContext.getBean(Select.class);
		query.addMainEntity(Student.class.getName());
		query.addProjections("obligationsStatus.obligation.paymentUnits");
		
		query.execute();
		
		LoadArguments args = new LoadArguments(Student.class);

		args.addInnerJoin("obligationsStatus")
			.addInnerJoin("obligationsStatus.obligation")
			.addInnerJoin("obligationsStatus.obligation.paymentUnits");
		
		args.addWhere(new ReferencedEqual(args, "id", "obligationsStatus.obligation.paymentUnits.id"));
		
		//standardDAO.load(args);
		
		PDF pdf = applicationContext.getBean(PDF.class);
		ByteArrayOutputStream stream =  pdf.getOutputStream(paidObligationReportTemplate);
		
		return base64.encode(stream.toByteArray());
	}

}
