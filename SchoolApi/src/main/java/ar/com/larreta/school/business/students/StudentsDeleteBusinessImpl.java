package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.impl.DeleteBusinessImpl;

@Service(StudentsDeleteBusiness.BUSINESS_NAME)
@Transactional
public class StudentsDeleteBusinessImpl extends DeleteBusinessImpl<Student> implements StudentsDeleteBusiness {
}
