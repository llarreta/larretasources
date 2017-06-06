package ar.com.larreta.school.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.larreta.persistence.dao.StandardDAO;
import ar.com.larreta.persistence.dao.impl.StandardDAOImpl;
import ar.com.larreta.persistence.model.DocumentType;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.PersistBusinessListener;
import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

public class PersistStudentsListener implements PersistBusinessListener {
	
	@Autowired
	@Qualifier(value=StandardDAOImpl.STANDAR_DAO)
	protected StandardDAO standardDAO;


	@Override
	public void beforePersist(Body body, Entity entity) {
		Student student = (Student) entity;
		UpdateStudentBody updateStudentBody = (UpdateStudentBody) body;
		if (updateStudentBody.getDocumentType()!=null){
			student.setDocumentType(standardDAO.getEntity(DocumentType.class, updateStudentBody.getDocumentType()));
		}
	}

	@Override
	public void afterPersist(Body body, Entity entity) {
	}

}
