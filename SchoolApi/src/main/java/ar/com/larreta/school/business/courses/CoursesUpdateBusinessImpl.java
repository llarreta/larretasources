package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.stepper.impl.UpdateBusinessImpl;

@Service(CoursesUpdateBusiness.BUSINESS_NAME)
@Transactional
public class CoursesUpdateBusinessImpl extends UpdateBusinessImpl<UpdateCourseBody, Course> implements CoursesUpdateBusiness {

/*	@Override
	@Autowired @Qualifier(CoursesBusinessConfig.COURSES_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}*/

}
