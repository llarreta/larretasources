package ar.com.larreta.school.business.payments;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.ObligationData;
import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;

@Service(UnpaidObligationsBusiness.BUSINESS_NAME)
@Transactional
public class UnpaidObligationsBusinessImpl extends LoadBusinessImpl<ObligationData, Obligation> implements UnpaidObligationsBusiness {

/*	@Override
	protected LoadArguments createLoadArgs(Class<?> entityType, Serializable input) {
		TargetedBody targetedBody = (TargetedBody) input;
		
		LoadArguments args = super.createLoadArgs(entityType, input);
		args.addInnerJoin("paymentPlan");
		
		LoadArguments queryObligationsInPlan = args.addWhereInSubquery("paymentPlan.id", "id", PaymentPlan.class);
		queryObligationsInPlan.addInnerJoin("students");
		queryObligationsInPlan.addWhereEqual("students.id", targetedBody.getTarget());
		
		LoadArguments queryPays = args.addWhereNotInSubquery("id", "obligation.id", ObligationStatus.class);
		queryPays.addInnerJoin("student").addInnerJoin("obligation");
		queryPays.addWhere(new Equal(queryPays, "paidOff", Boolean.TRUE));
		queryPays.addWhere(new Equal(queryPays, "student.id", targetedBody.getTarget()));
		
		return args;
	}
*/
}
