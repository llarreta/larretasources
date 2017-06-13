package ar.com.larreta.school.business.courses;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.persistence.model.ParametricEntity;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.messages.CourseData;
import ar.com.larreta.school.persistence.Course;

@Component
public class CoursesAsignLevelAfterLoadBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException {
		CourseData data = (CourseData) json;
		Course course = (Course) entity;
		
		ParametricData parametricData = applicationContext.getBean(ParametricData.class);
		ParametricEntity parametricEntity = course.getLevel();
		if (parametricEntity!=null){
			parametricData.setId(parametricEntity.getId());
			parametricData.setDescription(parametricEntity.getDescription());
		}
		
		data.setLevel(parametricData);
		
		return null;
	}

}
