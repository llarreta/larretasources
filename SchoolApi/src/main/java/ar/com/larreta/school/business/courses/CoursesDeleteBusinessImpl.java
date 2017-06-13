package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.DeleteBusinessImpl;
import ar.com.larreta.school.persistence.Course;

@Service(CoursesDeleteBusiness.BUSINESS_NAME)
@Transactional
public class CoursesDeleteBusinessImpl extends DeleteBusinessImpl<Course> implements CoursesDeleteBusiness {
}
