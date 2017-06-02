package ar.com.larreta.school.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.DeleteBusinessImpl;
import ar.com.larreta.school.business.StudentsDeleteBusiness;
import ar.com.larreta.school.persistence.Student;

@Service(StudentsDeleteBusiness.BUSINESS_NAME)
@Transactional
public class StudentsDeleteBusinessImpl extends DeleteBusinessImpl<Student> implements StudentsDeleteBusiness {
}
