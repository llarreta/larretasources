package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;

@Service(StudentsCreateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsCreateBusinessImpl extends CreateBusinessImpl<UpdateStudentBody, Student> implements StudentsCreateBusiness {

	/*@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}*/

}

