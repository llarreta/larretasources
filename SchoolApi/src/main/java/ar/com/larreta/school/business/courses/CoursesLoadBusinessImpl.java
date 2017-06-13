package ar.com.larreta.school.business.courses;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.school.messages.CourseData;
import ar.com.larreta.school.persistence.Course;

@Service(CoursesLoadBusiness.BUSINESS_NAME)
@Transactional
public class CoursesLoadBusinessImpl extends LoadBusinessImpl<CourseData, Course> implements CoursesLoadBusiness {
	@Override
	@Autowired @Qualifier(CoursesBusinessConfig.COURSES_AFTER_LOAD)
	public void setAfterLoadListeners(Set<BusinessListener> afterLoadListeners) {
		super.setAfterLoadListeners(afterLoadListeners);
	}
}
