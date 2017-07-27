package ar.com.larreta.school.business.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.school.persistence.Division;
import ar.com.larreta.school.persistence.Level;
import ar.com.larreta.school.persistence.Year;
import ar.com.larreta.stepper.impl.EntityAsignBusinessListener;
import ar.com.larreta.stepper.impl.JsonAsignBusinessListener;
import ar.com.larreta.stepper.messages.ParametricData;

@Configuration
public class CoursesBusinessConfig {

	public static final String JSON_ASIGN_DIVISION_LISTENER 			= "jsonAsignDivisionListener";
	public static final String JSON_ASIGN_YEAR_LISTENER 				= "jsonAsignYearListener";
	public static final String JSON_ASIGN_LEVEL_LISTENER 				= "jsonAsignLevelListener";
	public static final String ASIGN_LEVEL_LISTENER 					= "asignLevelListener";
	public static final String ASIGN_DIVISION_LISTENER 					= "asignDivisionListener";
	public static final String ASIGN_YEAR_LISTENER 						= "asignYearListener";
	
	public static final String LEVEL 									= "level";
	public static final String LEVEL_ID 								= "level.id";
	public static final String YEAR 									= "year";
	public static final String YEAR_ID 									= "year.id";
	public static final String DIVISION 								= "division";
	public static final String DIVISION_ID 								= "division.id";
	
	public static final String COURSES_BEFORE_PERSIST 					= "coursesBeforePerist";
	public static final String COURSES_AFTER_LOAD 						= "coursesAfterLoad";

	//@Autowired
	private CoursesCallCreateYearBusinessListener 			coursesCallCreateYearBusinessListener;
	//@Autowired
	private CoursesCallCreateDivisionBusinessListener 		coursesCallCreateDivisionBusinessListener;
	//@Autowired
	private CoursesCallCreateLevelBusinessListener 			coursesCallCreateLevelBusinessListener;
	
	private EntityAsignBusinessListener<Level> 	  				asignLevelListener;
	private EntityAsignBusinessListener<Year> 	  				asignYearListener;
	private EntityAsignBusinessListener<Division> 	  			asignDivisionListener;

	@Bean(name=JSON_ASIGN_LEVEL_LISTENER)
	public JsonAsignBusinessListener<ParametricData> jsonAsignLevelListener(){
		return new JsonAsignBusinessListener<ParametricData>() {
			@Override
			public String getSourceProperty() {
				return LEVEL;
			}

			@Override
			public String getTargetProperty() {
				return LEVEL;
			}
		};
	}
	
	@Bean(name=JSON_ASIGN_YEAR_LISTENER)
	public JsonAsignBusinessListener<ParametricData> jsonAsignYearListener(){
		return new JsonAsignBusinessListener<ParametricData>() {
			@Override
			public String getSourceProperty() {
				return YEAR;
			}

			@Override
			public String getTargetProperty() {
				return YEAR;
			}
		};
	}
	
	@Bean(name=JSON_ASIGN_DIVISION_LISTENER)
	public JsonAsignBusinessListener<ParametricData> jsonAsignDivisionListener(){
		return new JsonAsignBusinessListener<ParametricData>() {
			@Override
			public String getSourceProperty() {
				return DIVISION;
			}

			@Override
			public String getTargetProperty() {
				return DIVISION;
			}
		};
	}
	
	
	@Bean(name=ASIGN_DIVISION_LISTENER)
	public EntityAsignBusinessListener<Division> asignDivisionListener(){
		asignDivisionListener = new EntityAsignBusinessListener<Division>() {
			@Override
			public String getSourceProperty() {
				return DIVISION_ID;
			}

			@Override
			public String getTargetProperty() {
				return DIVISION;
			}
		};
		return asignDivisionListener;
	}
	
	@Bean(name=ASIGN_YEAR_LISTENER)
	public EntityAsignBusinessListener<Year> asignYearListener(){
		asignYearListener = new EntityAsignBusinessListener<Year>() {
			@Override
			public String getSourceProperty() {
				return YEAR_ID;
			}

			@Override
			public String getTargetProperty() {
				return YEAR;
			}
		};
		return asignYearListener;
	}
	
	@Bean(name=ASIGN_LEVEL_LISTENER)
	public EntityAsignBusinessListener<Level> asignLevelListener(){
		asignLevelListener = new EntityAsignBusinessListener<Level>() {
			@Override
			public String getSourceProperty() {
				return LEVEL_ID;
			}

			@Override
			public String getTargetProperty() {
				return LEVEL;
			}
		};
		return asignLevelListener;
	}
	
/*	@Bean(name=COURSES_BEFORE_PERSIST)
	@DependsOn(value={ASIGN_LEVEL_LISTENER, ASIGN_YEAR_LISTENER, ASIGN_DIVISION_LISTENER})
	public Set<BusinessListener> coursesBeforePerist(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(coursesCallCreateYearBusinessListener);
		businessListeners.add(coursesCallCreateDivisionBusinessListener);
		businessListeners.add(coursesCallCreateLevelBusinessListener);
		businessListeners.add(asignLevelListener);
		businessListeners.add(asignYearListener);
		businessListeners.add(asignDivisionListener);
		return businessListeners;
	}*/
	
/*	@Bean(name=COURSES_AFTER_LOAD)
	@DependsOn(value={JSON_ASIGN_LEVEL_LISTENER, JSON_ASIGN_YEAR_LISTENER, JSON_ASIGN_DIVISION_LISTENER})
	public Set<BusinessListener> coursesAfterLoad(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(jsonAsignLevelListener);
		businessListeners.add(jsonAsignYearListener);
		businessListeners.add(jsonAsignDivisionListener);
		return businessListeners;
	}
	*/
}
