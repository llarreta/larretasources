package ar.com.larreta.school.business.courses;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.UpdateBusinessImpl;
import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;

@Service(CoursesUpdateBusiness.BUSINESS_NAME)
@Transactional
public class CoursesUpdateBusinessImpl extends UpdateBusinessImpl<UpdateCourseBody, Course> implements CoursesUpdateBusiness {

	@Override
	@Autowired @Qualifier(CoursesBusinessConfig.COURSES_BEFORE_PERSIST)
	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		super.setBeforePersistListeners(beforePersistListeners);
	}

}
