package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.rest.messages.TargetedBody;
import ar.com.larreta.school.messages.ObligationData;
import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.school.persistence.Student;

@Service(UnpaidObligationsBusiness.BUSINESS_NAME)
@Transactional
public class UnpaidObligationsImpl extends LoadBusinessImpl<ObligationData, Obligation> implements UnpaidObligationsBusiness {

	@Override
	protected LoadArguments createLoadArgs(Class<?> entityType, Serializable input) {
		LoadArguments args = super.createLoadArgs(entityType, input);
		TargetedBody targetedBody = (TargetedBody) input;
		LoadArguments argsSubQuery = args.addWhereNotInSubquery("id", "obligationsStatus.obligation.id", Student.class);
		argsSubQuery.addInnerJoin("obligationsStatus");
		argsSubQuery.addWhere(new Equal(argsSubQuery, "obligationsStatus.paidOff", Boolean.TRUE));
		argsSubQuery.addWhere(new Equal(argsSubQuery, "obligationsStatus.student.id", targetedBody.getTarget()));
		return args;
	}

}
