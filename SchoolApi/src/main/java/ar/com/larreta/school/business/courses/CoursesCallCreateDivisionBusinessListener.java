package ar.com.larreta.school.business.courses;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.larreta.school.messages.UpdateCourseBody;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.impl.CallCreateBusinessListener;
import ar.com.larreta.stepper.messages.JSONable;

//FIXME: revisar si este componente puede estar directamente en el config de cursos
@Component
public class CoursesCallCreateDivisionBusinessListener extends CallCreateBusinessListener {

	@Autowired @Qualifier(DivisionsCreateBusiness.BUSINESS_NAME)
	@Override
	public void setBusiness(Step business) {
		super.setBusiness(business);
	}

	@Override
	public Serializable getParam(Serializable source, Serializable target, Object... args) {
		UpdateCourseBody body = (UpdateCourseBody) source;
		return body.getDivision();
	}

	@Override
	public Boolean isExecuteAvaiable(Serializable source, Serializable target, Object... args) {
		UpdateCourseBody body = (UpdateCourseBody) source;
		return !StringUtils.isEmpty(body.getDivision().getDescription());
	}

}
