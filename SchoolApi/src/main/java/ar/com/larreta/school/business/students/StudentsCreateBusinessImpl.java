package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.StudentData;
import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;

@Service(StudentsCreateBusiness.BUSINESS_NAME)
@Transactional
public class StudentsCreateBusinessImpl extends CreateBusinessImpl<StudentData, Student> implements StudentsCreateBusiness {

}

