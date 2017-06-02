package ar.com.larreta.school.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.school.business.StudentsUpdateBusiness;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsUpdateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsUpdateBusinessImpl extends CreateBusinessImpl<UpdateStudentBody, Student> implements StudentsUpdateBusiness {

	public StudentsUpdateBusinessImpl(){
		addListener(new PersistStudentsListener());
	}

}
