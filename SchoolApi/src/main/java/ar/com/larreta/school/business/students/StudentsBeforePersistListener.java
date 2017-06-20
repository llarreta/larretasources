package ar.com.larreta.school.business.students;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.DocumentType;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Component
public class StudentsBeforePersistListener extends BusinessListenerImpl {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		Student student = (Student) target;
		UpdateStudentBody updateStudentBody = (UpdateStudentBody) source;
		if (updateStudentBody.getDocumentType()!=null){
			student.setDocumentType(standardDAO.getEntity(DocumentType.class, updateStudentBody.getDocumentType()));
		}
		return null;
	}

}
