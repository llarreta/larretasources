package ar.com.larreta.school.students.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.prototypes.impl.EntityImpl;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.persistence.impl.Student;
import ar.com.larreta.school.students.business.StudentsCreateBusiness;

@Service
@Transactional
public class StudentsCreateBusinessImpl extends BusinessImpl implements StudentsCreateBusiness {

	@Override
	public JSONable execute(JSONable input) {
		EntityImpl entity = (EntityImpl) input;
		Student student = new Student();
		beanUtils.copy(entity, student);
		
		standardDAO.save(student);
		entity.setId(student.getId());
		
		return entity;
	}

}
