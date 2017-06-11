package ar.com.larreta.school.business.students;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.UpdateBusinessImpl;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsUpdateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsUpdateBusinessImpl extends UpdateBusinessImpl<UpdateStudentBody, Student> implements StudentsUpdateBusiness {
	@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_CREATE_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}
}
