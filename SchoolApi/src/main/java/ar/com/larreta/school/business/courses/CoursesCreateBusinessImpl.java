package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;

@Service(CoursesCreateBusiness.BUSINESS_NAME)
@Transactional
public class CoursesCreateBusinessImpl extends CreateBusinessImpl<UpdateCourseBody, Course> implements CoursesCreateBusiness {

/*	@Override
	@Autowired @Qualifier(CoursesBusinessConfig.COURSES_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}
*/
}
