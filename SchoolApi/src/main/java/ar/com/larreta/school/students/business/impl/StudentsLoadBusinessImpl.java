package ar.com.larreta.school.students.business.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Like;
import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.prototypes.JSONableCollection;
import ar.com.larreta.prototypes.Person;
import ar.com.larreta.prototypes.impl.PersonImpl;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.school.persistence.impl.Student;
import ar.com.larreta.school.students.business.StudentsLoadBusiness;

@Service
@Transactional
public class StudentsLoadBusinessImpl extends BusinessImpl implements StudentsLoadBusiness {

	@Override
	public JSONable execute(JSONable input) {
		Person person = (Person) input;
		
		LoadArguments args = new LoadArguments(Student.class);
		
		if (StringUtils.isNotEmpty(person.getName())){
			Like like = new Like(args, "name", person.getName());
			args.addWhere(like);
		}

		if (StringUtils.isNotEmpty(person.getSurname())){
			Like like = new Like(args, "surname", person.getSurname());
			args.addWhere(like);
		}
		
		Collection result = standardDAO.load(args);
		JSONableCollection<Person> students = new JSONableCollection<Person>();
		if (result!=null){
			Iterator<Student> it = result.iterator();
			while (it.hasNext()) {
				Student student = (Student) it.next();
				Person actualStudent = new PersonImpl();
				beanUtils.copy(student, actualStudent);
				students.add(actualStudent);
			}
		}
		
		return students;
	}

}
