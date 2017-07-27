package ar.com.larreta.school.business.students;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.mystic.model.DocumentType;
import ar.com.larreta.school.persistence.Course;
import ar.com.larreta.school.persistence.PaymentPlan;
import ar.com.larreta.stepper.impl.CollectionEntityAsignBusinessListener;
import ar.com.larreta.stepper.impl.EntityAsignBusinessListener;
import ar.com.larreta.stepper.impl.IdIteratorListener;
import ar.com.larreta.stepper.impl.PropertyAsignBusinessListener;

@Configuration
public class StudentsBusinessConfig {

	public static final String PAYMENT_PLANS_LISTENER = "paymentPlansListener";
	public static final String COURSE_ASIGN_LISTENER = "courseAsignListener";
	public static final String COURSE_ID = "course.id";
	public static final String STUDENT_AFTER_LOAD 				= "studentAfterLoad";
	public static final String PAYMENT_PLANS 					= "paymentPlans";
	public static final String COURSE 							= "course";
	public static final String DOCUMENT_NUMBER 					= "documentNumber";
	public static final String SURNAME 							= "surname";
	public static final String NAME 							= "name";
	public static final String DOCUMENT_TYPE 					= "documentType";
	public static final String DOCUMENT_TYPE_ID 				= "documentType.id";

	public static final String DOCUMENT_TYPE_ASIGN_LISTENER 	= "documentTypeAsignListener";
	public static final String WHERE_NAME_LISTENER 				= "whereNameListener";
	public static final String WHERE_SURNAME_LISTENER 			= "whereSurnameListener";
	public static final String WHERE_DOCUMENT_TYPE_LISTENER 	= "whereDocumentTypeListener";
	public static final String WHERE_DOCUMENT_NUMBER_LISTENER 	= "whereDocumentNumberListener";
	public static final String ASIGN_DOCUMENT_TYPE_LISTENER 	= "asignDocumentTypeListener";
	public static final String ASIGN_COURSE_LISTENER 			= "asignCourseListener";
	public static final String ASIGN_PAYMENT_PLAN_LISTENER 		= "asignPaymentPlansListener";
	
	public static final String STUDENT_BEFORE_LOAD 				= "studentBeforeLoad";
	public static final String STUDENT_BEFORE_PERSIST 			= "studentBeforePersist";
	
	/*@Autowired @Qualifier(BusinessListenerConfig.FIRST_AND_MAX_RESULTS)
	private Set<BusinessListener> firstAndMaxResultsListeners;
	
	private WhereBusinessListener<Like> whereNameListener;
	private WhereBusinessListener<Like> whereSurnameListener;
	private WhereBusinessListener<Like> whereDocumentTypeListener;
	private WhereBusinessListener<Like> whereDocumentNumberListener;*/
	
	private EntityAsignBusinessListener<DocumentType> asignDocumentTypeListener;
	private EntityAsignBusinessListener<Course> 	  asignCourseListener;
	
	private CollectionEntityAsignBusinessListener<PaymentPlan> asignPaymentPlansListener;
	
	private PropertyAsignBusinessListener documentTypeAsignListener;
	private PropertyAsignBusinessListener courseAsignListener;
	private IdIteratorListener 			  paymentPlansListener;
	
	/*@Bean(name=STUDENT_AFTER_LOAD)
	@DependsOn(value={DOCUMENT_TYPE_ASIGN_LISTENER, COURSE_ASIGN_LISTENER, PAYMENT_PLANS_LISTENER})
	public Set<BusinessListener> getStudentAfterLoad(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(documentTypeAsignListener);
		businessListeners.add(courseAsignListener);
		businessListeners.add(paymentPlansListener);
		return businessListeners;
	}*/

	@Bean(name=PAYMENT_PLANS_LISTENER)
	public IdIteratorListener paymentPlansListener(){
		paymentPlansListener = new IdIteratorListener() {
			@Override
			public String getTargetProperty() {
				return PAYMENT_PLANS;
			}
			@Override
			public String getSourceProperty() {
				return PAYMENT_PLANS;
			}
		};
		return paymentPlansListener;
	}
	
	@Bean(name=COURSE_ASIGN_LISTENER)
	public PropertyAsignBusinessListener courseAsignListener(){
		courseAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public String getTargetProperty() {
				return COURSE;
			}
			@Override
			public String getSourceProperty() {
				return COURSE_ID;
			}
		};
		return courseAsignListener;
	}
	
	@Bean(name=DOCUMENT_TYPE_ASIGN_LISTENER)
	public PropertyAsignBusinessListener documentTypeAsignListener(){
		documentTypeAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public String getTargetProperty() {
				return DOCUMENT_TYPE;
			}
			@Override
			public String getSourceProperty() {
				return DOCUMENT_TYPE_ID;
			}
		};
		return documentTypeAsignListener;
	}
	
	/*@Bean(name=STUDENT_BEFORE_LOAD)
	@DependsOn(value={WHERE_NAME_LISTENER, WHERE_SURNAME_LISTENER, WHERE_DOCUMENT_TYPE_LISTENER, WHERE_DOCUMENT_NUMBER_LISTENER})
	public Set<BusinessListener> getStudentBeforeLoadS(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.addAll(firstAndMaxResultsListeners);
		businessListeners.add(whereNameListener);
		businessListeners.add(whereSurnameListener);
		businessListeners.add(whereDocumentTypeListener);
		businessListeners.add(whereDocumentNumberListener);
		return businessListeners;
	}
	
	@Bean(name=STUDENT_BEFORE_PERSIST) 
	@DependsOn(value={ASIGN_DOCUMENT_TYPE_LISTENER, ASIGN_COURSE_LISTENER, ASIGN_PAYMENT_PLAN_LISTENER})
	public Set<BusinessListener> getStudentCreateBeforePersist(){
		Set<BusinessListener> businessListeners = new HashSet<>();
		businessListeners.add(asignDocumentTypeListener);
		businessListeners.add(asignCourseListener);
		businessListeners.add(asignPaymentPlansListener);
		return businessListeners;
	}*/
	
	@Bean(name=ASIGN_DOCUMENT_TYPE_LISTENER)
	public EntityAsignBusinessListener<DocumentType> asignDocumentTypeListener(){
		asignDocumentTypeListener = new EntityAsignBusinessListener<DocumentType>() {
			@Override
			public String getSourceProperty() {
				return DOCUMENT_TYPE;
			}

			@Override
			public String getTargetProperty() {
				return DOCUMENT_TYPE;
			}
		};
		return asignDocumentTypeListener;
	}
	
	/*@Bean(name=WHERE_NAME_LISTENER)
	public WhereBusinessListener<Like> whereNameListener(){
		whereNameListener = new  WhereBusinessListener<Like>() {
			@Override
			public String getSourceProperty() {
				return NAME;
			}
			@Override
			public String getTargetProperty() {
				return NAME;
			}
		};
		return whereNameListener;
	}*/
	
	/*@Bean(name=WHERE_SURNAME_LISTENER)
	public WhereBusinessListener<Like> whereSurnameListener(){
		whereSurnameListener = new  WhereBusinessListener<Like>() {
			@Override
			public String getSourceProperty() {
				return SURNAME;
			}
			@Override
			public String getTargetProperty() {
				return SURNAME;
			}
		};
		return whereSurnameListener;
	}*/
	
	/*@Bean(name=WHERE_DOCUMENT_TYPE_LISTENER)
	public WhereBusinessListener<Like> whereDocumentTypeListener(){
		whereDocumentTypeListener = new  WhereBusinessListener<Like>() {
			@Override
			public String getSourceProperty() {
				return DOCUMENT_TYPE;
			}
			@Override
			public String getTargetProperty() {
				return DOCUMENT_TYPE_ID;
			}
		};
		return whereDocumentTypeListener;
	}*/
	
	/*@Bean(name=WHERE_DOCUMENT_NUMBER_LISTENER)
	public WhereBusinessListener<Like> whereDocumentNumberListener(){
		whereDocumentNumberListener = new  WhereBusinessListener<Like>() {
			@Override
			public String getSourceProperty() {
				return DOCUMENT_NUMBER;
			}
			@Override
			public String getTargetProperty() {
				return DOCUMENT_NUMBER;
			}
		};
		return whereDocumentNumberListener;
	}*/

	@Bean(name=ASIGN_COURSE_LISTENER)
	public EntityAsignBusinessListener<Course> asignCourseListener(){
		asignCourseListener = new EntityAsignBusinessListener<Course>() {
			@Override
			public String getSourceProperty() {
				return COURSE;
			}

			@Override
			public String getTargetProperty() {
				return COURSE;
			}
		};
		return asignCourseListener;
	}

	@Bean(name=ASIGN_PAYMENT_PLAN_LISTENER)
	public CollectionEntityAsignBusinessListener<PaymentPlan> asignPaymentPlansListener(){
		asignPaymentPlansListener = new CollectionEntityAsignBusinessListener<PaymentPlan>() {
			@Override
			public String getSourceProperty() {
				return PAYMENT_PLANS;
			}
			@Override
			public String getTargetProperty() {
				return PAYMENT_PLANS;
			}
		};
		return asignPaymentPlansListener;
	}

}
