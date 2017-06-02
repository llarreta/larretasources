package ar.com.larreta.school.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.business.StudentsCreateBusiness;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsCreateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsCreateBusinessImpl extends CreateBusinessImpl<UpdateStudentBody, Student> implements StudentsCreateBusiness {

	public StudentsCreateBusinessImpl(){
		addListener(new PersistStudentsListener());
	}

}
