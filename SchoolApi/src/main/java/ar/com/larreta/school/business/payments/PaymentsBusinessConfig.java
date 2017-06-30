package ar.com.larreta.school.business.payments;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.persistence.model.Person;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.BusinessConfig;
import ar.com.larreta.rest.business.impl.EntityAsignBusinessListener;
import ar.com.larreta.rest.business.impl.IteratorListener;
import ar.com.larreta.rest.business.impl.LoadArgsAddInnerJoin;
import ar.com.larreta.rest.business.impl.LoadArgsWhereBusinessListener;
import ar.com.larreta.rest.business.impl.PropertyAsignBusinessListener;
import ar.com.larreta.rest.messages.TargetedBody;
import ar.com.larreta.school.persistence.PaymentDirection;
import ar.com.larreta.school.persistence.PaymentEntity;
import ar.com.larreta.school.persistence.PaymentUnit;
import ar.com.larreta.school.persistence.Product;

@Configuration
public class PaymentsBusinessConfig extends BusinessConfig {

	public static final String STUDENT 									= "student";
	public static final String PRODUCT_GROUP_ID 						= "productGroup.id";
	public static final String PRODUCT_GROUP 							= "productGroup";
	public static final String PAYMENT_PLAN_STUDENTS_ID 				= "paymentPlan.students.id";
	public static final String PAYMENT_PLAN_STUDENTS 					= "paymentPlan.students";
	public static final String PAYMENT_PLAN 							= "paymentPlan";
	public static final String PAYMENT_ENTITY 							= "paymentEntity";
	public static final String PAYMENT_DIRECTION	 					= "paymentDirection";
	public static final String PRODUCT 									= "product";
	public static final String PAYMENT_UNITS 							= "paymentUnits";
	public static final String PERSON_BENEFITING 						= "personBenefiting";
	public static final String ASIGN_PERSON_LISTENER 					= "asignPersonListener";
	public static final String PAY_UNITS 								= "payUnits";

	public static final String STUDENT_ASIGN_LISTENER 					= "studentAsignListener";
	public static final String OBLIGATION_STATUS_AFTER_LOAD_LISTENERS 	= "obligationStatusAfterLoadListeners";
	public static final String PRODUCT_GROUP_ASIGN_LISTENER 			= "productGroupAsignListener";
	public static final String PAYMENT_STUDENT_ID_WHERE_EQUAL_LISTENER 	= "paymentStudentIdWhereEqualListener";
	public static final String OBLIGATION_STATUS_BEFORE_LOAD_LISTENERS 	= "obligationStatusBeforeLoadListeners";
	public static final String PAYMENT_PLAN_STUDENT_INNER_JOIN_LISTENER = "paymentPlanStudentInnerJoinListener";
	public static final String PAYMENT_PLAN_INNER_JOIN_LISTENER 		= "paymentPlanInnerJoinListener";
	public static final String ASIGN_PAYMENT_ENTITY_LISTENER 			= "asignPaymentEntityListener";
	public static final String ASIGN_PAYMENT_DIRECTION_LISTENER 		= "asignPaymentDirectionListener";
	public static final String ASIGN_PRODUCT_LISTENER 					= "asignProductListener";
	public static final String PAYMENT_UNIT_BEFORE_PERIST_LISTENERS 	= "paymentUnitBeforePeristListeners";
	public static final String PAYMENT_BEFORE_PERSIST_LISTENERS 		= "paymentBeforePersistListener";
	public static final String PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE 	= "paymentUnitsListenerFrontToService";

	private EntityAsignBusinessListener<Person> 	  				asignPersonListener;
	private EntityAsignBusinessListener<Product> 	  				asignProductListener;
	private EntityAsignBusinessListener<PaymentDirection> 	  		asignPaymentDirectionListener;
	private EntityAsignBusinessListener<PaymentEntity> 	  			asignPaymentEntityListener;
	
	private IteratorListener<PaymentUnit> 							paymentUnitsListenerServiceToFront;
	
	private LoadArgsAddInnerJoin									paymentPlanInnerJoinListener;
	private LoadArgsAddInnerJoin									paymentPlanStudentInnerJoinListener;
	private LoadArgsWhereBusinessListener<Equal>					paymentStudentIdWhereEqualListener;

	private PropertyAsignBusinessListener							productGroupAsignListener;
	private PropertyAsignBusinessListener							studentAsignListener;
	
	@Bean(name=OBLIGATION_STATUS_AFTER_LOAD_LISTENERS) 
	@DependsOn(value={PRODUCT_GROUP_ASIGN_LISTENER, STUDENT_ASIGN_LISTENER})
	public Set<BusinessListener> obligationStatusAfterLoadListeners(){
		return getSet(productGroupAsignListener, studentAsignListener);
	}
	
	@Bean(name=STUDENT_ASIGN_LISTENER) 
	public PropertyAsignBusinessListener studentAsignListener(){
		studentAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public Object getValuedListener(Serializable source, Serializable target, Object... args) {
				TargetedBody body = (TargetedBody) args[0];
				return body.getTarget();
			}

			@Override
			public String getTargetProperty() {
				return STUDENT;
			}
			
			@Override
			public String getSourceProperty() {
				return null;
			}
		};
		return studentAsignListener;
	}
	
	@Bean(name=PRODUCT_GROUP_ASIGN_LISTENER) 
	public PropertyAsignBusinessListener productGroupAsignListener(){
		productGroupAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public String getTargetProperty() {
				return PRODUCT_GROUP;
			}
			
			@Override
			public String getSourceProperty() {
				return PRODUCT_GROUP_ID;
			}
		};
		return productGroupAsignListener;
	}
	
	@Bean(name=OBLIGATION_STATUS_BEFORE_LOAD_LISTENERS) 
	@DependsOn(value={PAYMENT_PLAN_STUDENT_INNER_JOIN_LISTENER, PAYMENT_PLAN_INNER_JOIN_LISTENER, PAYMENT_STUDENT_ID_WHERE_EQUAL_LISTENER})
	public Set<BusinessListener> obligationStatusBeforeLoadListeners(){
		return getSet(paymentUnitsListenerServiceToFront);
	}
	
	@Bean(name=PAYMENT_STUDENT_ID_WHERE_EQUAL_LISTENER)
	public LoadArgsWhereBusinessListener<Equal> paymentStudentIdWhereEqualListener(){
		paymentStudentIdWhereEqualListener = new LoadArgsWhereBusinessListener<Equal>() {
			@Override
			public Object getValuedListener(Serializable source, Serializable target, Object... args) {
				TargetedBody body = (TargetedBody) source;
				return body.getTarget();
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_PLAN_STUDENTS_ID;
			}
			
			@Override
			public String getSourceProperty() {
				return null;
			}
		};
		return paymentStudentIdWhereEqualListener;
	}
	

	@Bean(name=PAYMENT_PLAN_STUDENT_INNER_JOIN_LISTENER)
	public LoadArgsAddInnerJoin paymentPlanStudentInnerJoinListener(){
		paymentPlanStudentInnerJoinListener = new LoadArgsAddInnerJoin() {
			@Override
			public String getInnerJoin() {
				return PAYMENT_PLAN_STUDENTS;
			}
		};
		return paymentPlanStudentInnerJoinListener;
	}
	
	@Bean(name=PAYMENT_PLAN_INNER_JOIN_LISTENER)
	public LoadArgsAddInnerJoin paymentPlanInnerJoinListener(){
		paymentPlanInnerJoinListener = new LoadArgsAddInnerJoin() {
			@Override
			public String getInnerJoin() {
				return PAYMENT_PLAN;
			}
		};
		return paymentPlanInnerJoinListener;
	}
	
	@Bean(name=PAYMENT_BEFORE_PERSIST_LISTENERS) @DependsOn(value={PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> paymentBeforePersistListeners(){
		return getSet(paymentUnitsListenerServiceToFront);
	}
	
	@Bean(name=PAYMENT_UNIT_BEFORE_PERIST_LISTENERS) 
	@DependsOn(value={ASIGN_PERSON_LISTENER, ASIGN_PRODUCT_LISTENER, ASIGN_PAYMENT_DIRECTION_LISTENER, ASIGN_PAYMENT_ENTITY_LISTENER})
	public Set<BusinessListener> paymentUnitBeforePeristListeners(){
		return getSet(asignPersonListener, asignProductListener, asignPaymentDirectionListener, asignPaymentEntityListener);
	}

	@Bean(name=ASIGN_PAYMENT_ENTITY_LISTENER)
	public EntityAsignBusinessListener<PaymentEntity> asignPaymentEntityListener(){
		asignPaymentEntityListener = new EntityAsignBusinessListener<PaymentEntity>() {
			@Override
			public String getSourceProperty() {
				return PAYMENT_ENTITY;
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_ENTITY;
			}
		};
		return asignPaymentEntityListener;
	}
	
	@Bean(name=ASIGN_PAYMENT_DIRECTION_LISTENER)
	public EntityAsignBusinessListener<PaymentDirection> asignPaymentDirectionListener(){
		asignPaymentDirectionListener = new EntityAsignBusinessListener<PaymentDirection>() {
			@Override
			public String getSourceProperty() {
				return PAYMENT_DIRECTION;
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_DIRECTION;
			}
		};
		return asignPaymentDirectionListener;
	}
	
	@Bean(name=ASIGN_PRODUCT_LISTENER)
	public EntityAsignBusinessListener<Product> asignProductListener(){
		asignProductListener = new EntityAsignBusinessListener<Product>() {
			@Override
			public String getSourceProperty() {
				return PRODUCT;
			}

			@Override
			public String getTargetProperty() {
				return PRODUCT;
			}
		};
		return asignProductListener;
	}
	
	@Bean(name=ASIGN_PERSON_LISTENER)
	public EntityAsignBusinessListener<Person> asignPersonListener(){
		asignPersonListener = new EntityAsignBusinessListener<Person>() {
			@Override
			public String getSourceProperty() {
				return PERSON_BENEFITING;
			}

			@Override
			public String getTargetProperty() {
				return PERSON_BENEFITING;
			}
		};
		return asignPersonListener;
	}
	
	@Bean(name=PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<PaymentUnit> paymentUnitsListenerFrontToService(){
		paymentUnitsListenerServiceToFront =  new IteratorListener<PaymentUnit>() {
			@Override
			@Autowired @Qualifier(PaymentsBusinessConfig.PAYMENT_UNIT_BEFORE_PERIST_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}

			@Override
			public String getSourceProperty() {
				return PAY_UNITS;
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_UNITS;
			}
		};
		return paymentUnitsListenerServiceToFront;
	}
	
}
