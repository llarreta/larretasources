package ar.com.larreta.school.students.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.prototypes.ObjectJSONable;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.persistence.impl.Student;
import ar.com.larreta.school.students.business.StudentsDeleteBusiness;

@Service
@Transactional
public class StudentsDeleteBusinessImpl extends BusinessImpl implements StudentsDeleteBusiness {

	@Override
	public JSONable execute(JSONable input) {
		ObjectJSONable jsoNable = (ObjectJSONable) input;
		Student student = new Student();
		student.setId((Long) jsoNable.getTarget());
		standardDAO.delete(student);
		return jsoNable;
	}

}
