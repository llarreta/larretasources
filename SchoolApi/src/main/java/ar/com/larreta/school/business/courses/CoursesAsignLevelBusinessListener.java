package ar.com.larreta.school.business.courses;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.school.persistence.Level;

@Component
public class CoursesAsignLevelBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException {
		UpdateCourseBody updateCourseBody = (UpdateCourseBody) json;
		Course course = (Course) entity;
		
		Level level = applicationContext.getBean(Level.class);
		level.setId(updateCourseBody.getLevel().getId());
		
		return null;
	}

}
