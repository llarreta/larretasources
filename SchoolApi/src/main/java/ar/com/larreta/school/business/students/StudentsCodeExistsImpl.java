package ar.com.larreta.school.business.students;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.school.messages.CodeExistData;
import ar.com.larreta.school.messages.CodeExistResult;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.StepImpl;

@Service(StudentsCodeExists.BUSINESS_NAME)
@Transactional
public class StudentsCodeExistsImpl extends StepImpl implements StudentsCodeExists {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		
		CodeExistData data = (CodeExistData) source;
		
		Select select = applicationContext.getBean(Select.class);
		select.addProjections("id");
		select.addMainEntity(Student.class.getName());
		select.addWhereEqual("code", data.getCode());
		
		Collection result = select.execute();
		
		CodeExistResult existResult = applicationContext.getBean(CodeExistResult.class);
		existResult.setExist(result!=null && !result.isEmpty());
		return existResult;
	}

}
