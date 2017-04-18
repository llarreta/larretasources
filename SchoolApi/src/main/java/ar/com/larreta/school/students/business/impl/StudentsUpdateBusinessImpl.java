package ar.com.larreta.school.students.business.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.school.students.business.StudentsUpdateBusiness;

@Service(StudentsUpdateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsUpdateBusinessImpl extends BusinessImpl implements StudentsUpdateBusiness {

	@Override
	public Serializable execute(Serializable input) {
		UpdateStudentBody body = (UpdateStudentBody) input;
		Student student = new Student();
		beanUtils.copy(body, student);
		
		standardDAO.update(student);
		
		return student.getId();	
	}

}
