package ar.com.larreta.school.business.courses;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.business.impl.CallCreateBusinessListener;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.messages.UpdateCourseBody;

@Component
public class CoursesCallCreateLevelBusinessListener extends CallCreateBusinessListener {

	@Autowired @Qualifier(LevelsCreateBusiness.BUSINESS_NAME)
	@Override
	public void setBusiness(Business business) {
		super.setBusiness(business);
	}

	@Override
	public JSONable getParam() {
		UpdateCourseBody body = (UpdateCourseBody) json;
		return body.getLevel();
	}

	@Override
	public Boolean isExecuteAvaiable() {
		UpdateCourseBody body = (UpdateCourseBody) json;
		return !StringUtils.isEmpty(body.getLevel().getDescription());
	}

}
