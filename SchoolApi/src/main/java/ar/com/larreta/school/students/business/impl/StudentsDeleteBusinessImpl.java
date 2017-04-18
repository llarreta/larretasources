package ar.com.larreta.school.students.business.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.school.students.business.StudentsDeleteBusiness;

@Service(StudentsDeleteBusiness.BUSINESS_NAME)
@Transactional
public class StudentsDeleteBusinessImpl extends BusinessImpl implements StudentsDeleteBusiness {

	/**
	 * Generalizar esto, para solo tener que decir con que clase persistente se trabaja 
	 */
	@Override
	public Serializable execute(Serializable input) {
		Long id = (Long) input;
		Student student = new Student();
		student.setId(id);
		standardDAO.delete(student);
		return id;
	}

}
