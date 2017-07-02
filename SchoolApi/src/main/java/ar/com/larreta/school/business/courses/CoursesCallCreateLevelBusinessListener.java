package ar.com.larreta.school.business.courses;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.business.impl.CallCreateBusinessListener;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.messages.UpdateCourseBody;

//FIXME: revisar si este componente puede estar directamente en el config de cursos
@Component
public class CoursesCallCreateLevelBusinessListener extends CallCreateBusinessListener {

	@Autowired @Qualifier(LevelsCreateBusiness.BUSINESS_NAME)
	@Override
	public void setBusiness(Business business) {
		super.setBusiness(business);
	}

	@Override
	public JSONable getParam(Serializable source, Serializable target, Object... args) {
		UpdateCourseBody body = (UpdateCourseBody) source;
		return body.getLevel();
	}

	@Override
	public Boolean isExecuteAvaiable(Serializable source, Serializable target, Object... args) {
		UpdateCourseBody body = (UpdateCourseBody) source;
		return !StringUtils.isEmpty(body.getLevel().getDescription());
	}

}
