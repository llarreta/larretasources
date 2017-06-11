package ar.com.larreta.school.business.students;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.BusinessListenerConfig;
import ar.com.larreta.rest.business.impl.LoadArgsWhereLikeDocumentNumberBusinessListener;
import ar.com.larreta.rest.business.impl.LoadArgsWhereLikeNameBusinessListener;
import ar.com.larreta.rest.business.impl.LoadArgsWhereLikeSurnameBusinessListener;
import ar.com.larreta.school.business.LoadArgsWhereEqualDocumentTypeBusinessListener;

@Configuration
public class StudentsBusinessConfig {

	public static final String STUDENT_BEFORE_LOAD = "studentBeforeLoad";

	public static final String STUDENT_CREATE_BEFORE_PERSIST = "studentCreateBeforePersist";
	
	@Autowired
	private StudentsBeforePersistListener beforePersistStudentsListener;
	
	@Autowired @Qualifier(BusinessListenerConfig.FIRST_AND_MAX_RESULTS)
	private Set<BusinessListener> firstAndMaxResultsListeners;
	
	@Autowired
	private LoadArgsWhereLikeNameBusinessListener whereNameListener;
	
	@Autowired
	private LoadArgsWhereLikeSurnameBusinessListener whereSurnameListener;
	
	@Autowired
	private LoadArgsWhereEqualDocumentTypeBusinessListener whereDocumentTypeListener;
	
	@Autowired
	private LoadArgsWhereLikeDocumentNumberBusinessListener whereDocumentNumberListener;

	@Bean(name=STUDENT_BEFORE_LOAD)
	public Set<BusinessListener> getStudentBeforeLoadS(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.addAll(firstAndMaxResultsListeners);
		businessListeners.add(whereNameListener);
		businessListeners.add(whereSurnameListener);
		businessListeners.add(whereDocumentTypeListener);
		businessListeners.add(whereDocumentNumberListener);
		return businessListeners;
	}
	
	@Bean(name=STUDENT_CREATE_BEFORE_PERSIST)
	public Set<BusinessListener> getStudentCreateBeforePersist(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(beforePersistStudentsListener);
		return businessListeners;
	}
	
}
