package ar.com.larreta.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.rest.controllers.ParentController;
import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.Message;
import ar.com.larreta.rest.messages.Request;
import ar.com.larreta.rest.messages.TargetedBody;
import ar.com.larreta.school.messages.LoadStudentBody;
import ar.com.larreta.school.messages.UpdateStudentBody;

@Configuration
public class StudentHelpConfig {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private UpdateStudentBody updateStudentBody;
	
	@Autowired
	private LoadStudentBody loadStudentRequest;
	
	@Autowired
	private TargetedBody targetedBody;
	
	@Bean(name=StudentsController.ROOT_MAP + ParentController.CREATE)
	public Message getStudentsCreateHelp(){
		Request<Body> response = applicationContext.getBean(Request.class);
		response.setBody(updateStudentBody);
		return response;
	}

	@Bean(name=StudentsController.ROOT_MAP + ParentController.UPDATE)
	public Message getStudentsUpdateHelp(){
		return getStudentsCreateHelp();
	}
	
	@Bean(name=StudentsController.ROOT_MAP + ParentController.LOAD)
	public Message getStudentsLoadHelp(){
		Request<Body> response = applicationContext.getBean(Request.class);
		response.setBody(loadStudentRequest);
		return response;
	}

	@Bean(name=StudentsController.ROOT_MAP + ParentController.DELETE)
	public Message getStudentsDeleteHelp(){
		Request<Body> response = applicationContext.getBean(Request.class);
		response.setBody(targetedBody);
		return response;
	}
	
}
