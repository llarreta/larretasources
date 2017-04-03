package ar.com.larreta.school.students.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.prototypes.impl.EntityImpl;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.persistence.impl.Student;
import ar.com.larreta.school.students.business.StudentsUpdateBusiness;

@Service
@Transactional
public class StudentsUpdateBusinessImpl extends BusinessImpl implements StudentsUpdateBusiness {

	@Override
	public JSONable execute(JSONable input) {
		EntityImpl entity = (EntityImpl) input;
		Student student = new Student();
		beanUtils.copy(entity, student);
		
		standardDAO.update(student);
		entity.setId(student.getId());
		
		return entity;	
	}

}
