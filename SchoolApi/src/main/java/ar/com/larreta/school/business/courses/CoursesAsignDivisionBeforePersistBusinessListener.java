package ar.com.larreta.school.business.courses;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.school.persistence.Division;

@Component
public class CoursesAsignDivisionBeforePersistBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		UpdateCourseBody updateCourseBody = (UpdateCourseBody) source;
		Course course = (Course) target;
		
		Division division = applicationContext.getBean(Division.class);
		division.setId(updateCourseBody.getYear().getId());
		course.setDivision(division);
		
		return null;
	}

}
