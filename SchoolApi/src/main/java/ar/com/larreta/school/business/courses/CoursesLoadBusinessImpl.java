package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ar.com.larreta.school.messages.CourseData;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;
import ar.com.larreta.tools.Const;

@Service(CoursesLoadBusiness.BUSINESS_NAME) @Scope(Const.PROTOTYPE)
@Transactional 
public class CoursesLoadBusinessImpl extends LoadBusinessImpl<CourseData, Course> implements CoursesLoadBusiness {

}
