package ar.com.larreta.school.business.courses;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;

@Service(CoursesCreateBusiness.BUSINESS_NAME)
@Transactional
public class CoursesCreateBusinessImpl extends CreateBusinessImpl<UpdateCourseBody, Course> implements CoursesCreateBusiness {

	@Override
	@Autowired @Qualifier(CoursesBusinessConfig.COURSES_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}

}
