package ar.com.larreta.school.business.students;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.school.messages.LoadStudentsData;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsLoadBusiness.BUSINESS_NAME)
@Transactional
public class StudentsLoadBusinessImpl extends LoadBusinessImpl<LoadStudentsData, Student> implements StudentsLoadBusiness {

	@Override
	@Autowired @Qualifier(StudentsBusinessConfig.STUDENT_BEFORE_LOAD)
	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		super.setBeforeLoadListeners(beforeLoadListeners);
	}


/*	if (student.getDocumentType()!=null){
		actualStudent.setDocumentType(student.getDocumentType().getId());
	}
*/
	
}
