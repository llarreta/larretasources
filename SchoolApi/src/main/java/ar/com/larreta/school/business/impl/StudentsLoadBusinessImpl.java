package ar.com.larreta.school.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.persistence.dao.impl.Like;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.rest.messages.JSONableCollectionBody;
import ar.com.larreta.school.business.StudentsLoadBusiness;
import ar.com.larreta.school.messages.LoadStudentsBody;
import ar.com.larreta.school.messages.UpdateStudentBody;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsLoadBusiness.BUSINESS_NAME)
@Transactional
public class StudentsLoadBusinessImpl extends BusinessImpl implements StudentsLoadBusiness {

	@Override
	public Serializable execute(Serializable input) {
		LoadStudentsBody body = (LoadStudentsBody) input;
		
		LoadArguments args = new LoadArguments(Student.class);
		
		if (body!=null){
			if (body.getFirstResult()!=null){
				args.setFirstResult(body.getFirstResult());
			}
			
			if (body.getMaxResults()!=null){
				args.setMaxResults(body.getMaxResults());
			}
			
			if (StringUtils.isNotEmpty(body.getName())){
				Like like = new Like(args, "name", body.getName());
				args.addWhere(like);
			}
	
			if (StringUtils.isNotEmpty(body.getSurname())){
				Like like = new Like(args, "surname", body.getSurname());
				args.addWhere(like);
			}
			
			if (body.getDocumentType()!=null){
				Equal equal = new Equal(args, "documentType.id", body.getDocumentType());
				args.addWhere(equal);
			}
			
			if (StringUtils.isNotEmpty(body.getDocumentNumber())){
				Like like = new Like(args, "documentNumber", body.getDocumentNumber());
				args.addWhere(like);
			}
		}
		
		Collection result = standardDAO.load(args);
		JSONableCollectionBody<UpdateStudentBody> students = new JSONableCollectionBody<UpdateStudentBody>();
		if (result!=null){
			Iterator<Student> it = result.iterator();
			while (it.hasNext()) {
				Student student = (Student) it.next();
				UpdateStudentBody actualStudent = new UpdateStudentBody();
				
				actualStudent.setId(student.getId());
				actualStudent.setName(student.getName());
				actualStudent.setSurname(student.getSurname());
				if (student.getDocumentType()!=null){
					actualStudent.setDocumentType(student.getDocumentType().getId());
				}
				actualStudent.setDocumentNumber(student.getDocumentNumber());
				actualStudent.setPhoto(student.getPhoto());
				
				students.add(actualStudent);
			}
		}
		
		return students;
	}

}
