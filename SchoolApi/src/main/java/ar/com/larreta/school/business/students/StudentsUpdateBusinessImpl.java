package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.StudentData;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.impl.UpdateBusinessImpl;

@Service(StudentsUpdateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsUpdateBusinessImpl extends UpdateBusinessImpl<StudentData, Student> implements StudentsUpdateBusiness {
	/*@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}*/
}
