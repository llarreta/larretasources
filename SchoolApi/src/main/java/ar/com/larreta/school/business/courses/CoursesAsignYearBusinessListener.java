package ar.com.larreta.school.business.courses;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.school.persistence.Year;

@Component
public class CoursesAsignYearBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException {
		UpdateCourseBody updateCourseBody = (UpdateCourseBody) json;
		Course course = (Course) entity;
		
		Year level = applicationContext.getBean(Year.class);
		level.setId(updateCourseBody.getYear().getId());
		
		return null;
	}

}
