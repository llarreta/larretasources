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
	public static final String COURSES_AFTER_LOAD = "COURSES_AFTER_LOAD";

	@Autowired
	private CoursesCallCreateYearBusinessListener 			coursesCallCreateYearBusinessListener;
	@Autowired
	private CoursesCallCreateDivisionBusinessListener 		coursesCallCreateDivisionBusinessListener;
	@Autowired
	private CoursesCallCreateLevelBusinessListener 			coursesCallCreateLevelBusinessListener;
	@Autowired
	private CoursesAsignLevelBeforePersistBusinessListener 				coursesAsignLevelBusinessListener; 
	@Autowired
	private CoursesAsignYearBeforePersistBusinessListener 				coursesAsignYearBusinessListener;
	@Autowired
	private CoursesAsignDivisionBeforePersistBusinessListener 			coursesAsignDivisionBusinessListener;
	@Autowired
	private CoursesAsignLevelAfterLoadBusinessListener coursesAsignLevelAfterLoadBusinessListener;
	@Autowired
	private CoursesAsignYearAfterLoadBusinessListener coursesAsignYearAfterLoadBusinessListener;
	@Autowired
	private CoursesAsignDivisionAfterLoadBusinessListener coursesAsignDivisionAfterLoadBusinessListener;
	
	@Bean(name=COURSES_BEFORE_PERSIST)
	public Set<BusinessListener> getCoursesBeforePerist(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(coursesCallCreateYearBusinessListener);
		businessListeners.add(coursesCallCreateDivisionBusinessListener);
		businessListeners.add(coursesCallCreateLevelBusinessListener);
		businessListeners.add(coursesAsignLevelBusinessListener);
		businessListeners.add(coursesAsignYearBusinessListener);
		businessListeners.add(coursesAsignDivisionBusinessListener);
		return businessListeners;
	}
	
	@Bean(name=COURSES_AFTER_LOAD)
	public Set<BusinessListener> getCoursesAfterLoad(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(coursesAsignLevelAfterLoadBusinessListener);
		businessListeners.add(coursesAsignYearAfterLoadBusinessListener);
		businessListeners.add(coursesAsignDivisionAfterLoadBusinessListener);
		return businessListeners;
	}
	
	
}
