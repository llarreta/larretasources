package ar.com.larreta.school.students.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.rest.messages.JSONableCollectionBody;
import ar.com.larreta.school.messages.LoadStudentsBody;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.impl.Student;
import ar.com.larreta.school.students.business.StudentsLoadBusiness;

@Service(StudentsLoadBusiness.BUSINESS_NAME)
@Transactional
public class StudentsLoadBusinessImpl extends BusinessImpl implements StudentsLoadBusiness {

	@Override
	public Serializable execute(Serializable input) {
		LoadStudentsBody body = (LoadStudentsBody) input;
		
		LoadArguments args = new LoadArguments(Student.class);
		
		if (body!=null){
			/*if (StringUtils.isNotEmpty(person.getName())){
				Like like = new Like(args, "name", person.getName());
				args.addWhere(like);
			}
	
			if (StringUtils.isNotEmpty(person.getSurname())){
				Like like = new Like(args, "surname", person.getSurname());
				args.addWhere(like);
			}*/
		}
		
		Collection result = standardDAO.load(args);
		JSONableCollectionBody<UpdateStudentBody> students = new JSONableCollectionBody<UpdateStudentBody>();
		if (result!=null){
			Iterator<Student> it = result.iterator();
			while (it.hasNext()) {
				Student student = (Student) it.next();
				UpdateStudentBody actualStudent = new UpdateStudentBody();
				beanUtils.copy(student, actualStudent);
				students.add(actualStudent);
			}
		}
		
		return students;
	}

}
