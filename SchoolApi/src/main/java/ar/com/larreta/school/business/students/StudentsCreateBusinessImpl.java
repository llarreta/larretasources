package ar.com.larreta.school.business.students;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsCreateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsCreateBusinessImpl extends CreateBusinessImpl<UpdateStudentBody, Student> implements StudentsCreateBusiness {

	@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}

}

