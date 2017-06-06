package ar.com.larreta.school.business.impl;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.DocumentType;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Component
public class BeforePersistStudentsListener extends BusinessListenerImpl {

	@Override
	public void process(JSONable json, Entity entity, Object... args) {
		Student student = (Student) entity;
		UpdateStudentBody updateStudentBody = (UpdateStudentBody) json;
		if (updateStudentBody.getDocumentType()!=null){
			student.setDocumentType(standardDAO.getEntity(DocumentType.class, updateStudentBody.getDocumentType()));
		}
	}

}
