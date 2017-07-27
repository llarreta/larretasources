package ar.com.larreta.school.business.payments;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Query;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.reports.PDF;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.StepImpl;
import ar.com.larreta.stepper.messages.TargetedBody;
import ar.com.larreta.tools.Base64;

@Service(PaidObligationBuildReportBusiness.BUSINESS_NAME)
@Transactional
public class PaidObligationsBuildReportBusinessImpl extends StepImpl implements PaidObligationBuildReportBusiness {

	@Value("classpath:ar/com/larreta/school/reports/paidObligations.jrxml")
	private Resource paidObligationReportTemplate;
	
	@Autowired
	private Base64 base64;
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException {
		TargetedBody body = (TargetedBody) source;
		
		Query query = applicationContext.getBean(Select.class);
		query.addMainEntity(Student.class.getName());
		query.addProjections("obligationsStatus.obligation.paymentUnits");
		query.addWhereEqual("course.id", body.getTarget());
		query.addWhereEqualYear("obligationsStatus.obligation.dueDate", 2017);
		query.execute();
		
		/*LoadArguments args = new LoadArguments(Student.class);

		args.addInnerJoin("obligationsStatus")
			.addInnerJoin("obligationsStatus.obligation")
			.addInnerJoin("obligationsStatus.obligation.paymentUnits");
		
		args.addWhere(new ReferencedEqual(args, "id", "obligationsStatus.obligation.paymentUnits.id"));
		*/
		//standardDAO.load(args);
		
		PDF pdf = applicationContext.getBean(PDF.class);
		ByteArrayOutputStream stream = null;
		try {
			stream = pdf.getOutputStream(paidObligationReportTemplate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return base64.encode(stream.toByteArray());
	}

}
