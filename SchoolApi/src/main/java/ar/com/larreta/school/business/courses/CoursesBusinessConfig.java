package ar.com.larreta.school.business.courses;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.rest.business.BusinessListener;

@Configuration
public class CoursesBusinessConfig {

	public static final String COURSES_BEFORE_PERSIST = "COURSES_BEFORE_PERSIST";

	@Autowired
	private CoursesCallCreateYearBusinessListener 			coursesCallCreateYearBusinessListener;
	@Autowired
	private CoursesCallCreateDivisionBusinessListener 		coursesCallCreateDivisionBusinessListener;
	@Autowired
	private CoursesCallCreateLevelBusinessListener 			coursesCallCreateLevelBusinessListener;
	@Autowired
	private CoursesAsignLevelBusinessListener 				coursesAsignLevelBusinessListener; 
	@Autowired
	private CoursesAsignYearBusinessListener 				coursesAsignYearBusinessListener;
	@Autowired
	private CoursesAsignDivisionBusinessListener 			coursesAsignDivisionBusinessListener;
	
	@Bean(name=COURSES_BEFORE_PERSIST)
	public Set<BusinessListener> getStudentBeforeLoadS(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(coursesCallCreateYearBusinessListener);
		businessListeners.add(coursesCallCreateDivisionBusinessListener);
		businessListeners.add(coursesCallCreateLevelBusinessListener);
		businessListeners.add(coursesAsignLevelBusinessListener);
		businessListeners.add(coursesAsignYearBusinessListener);
		businessListeners.add(coursesAsignDivisionBusinessListener);
		return businessListeners;
	}
	
	
}
