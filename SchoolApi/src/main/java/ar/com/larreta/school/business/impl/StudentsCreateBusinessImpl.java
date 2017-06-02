package ar.com.larreta.school.students.business.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.model.DocumentType;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.school.students.business.StudentsCreateBusiness;

@Service(StudentsCreateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsCreateBusinessImpl extends BusinessImpl implements StudentsCreateBusiness {

	@Override
	public Serializable execute(Serializable input) throws Exception {
		UpdateStudentBody body = (UpdateStudentBody) input;
		Student student = new Student();
		beanUtils.copy(body, student);
		
		if (body.getDocumentType()!=null){
			student.setDocumentType(standardDAO.getEntity(DocumentType.class, body.getDocumentType()));
		}
		
		standardDAO.save(student);
		
		return student.getId();
	}

}
