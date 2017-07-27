package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.LoadStudentsData;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;

@Service(StudentsLoadBusiness.BUSINESS_NAME)
@Transactional
public class StudentsLoadBusinessImpl extends LoadBusinessImpl<LoadStudentsData, Student> implements StudentsLoadBusiness {

	/*@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_AFTER_LOAD)
	public void setAfterLoadListeners(Set<BusinessListener> afterLoadListeners) {
		super.setAfterLoadListeners(afterLoadListeners);
	}

	@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_BEFORE_LOAD)
	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		super.setBeforeLoadListeners(beforeLoadListeners);
	}*/
	
}
